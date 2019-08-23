package com.release3.tf.javasql;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;

import com.release3.customization.builtin.BuiltinType;
import com.release3.javasql.JavaPlSqlType;
import com.release3.javasql.R3JavaSqlGen;
import com.release3.programunitgen.R3ProgramUnit;
import com.release3.programunitgen.R3ProgramUnitGen;
import com.release3.r3coreplsqlgen.R3CorePlSql;
import com.release3.r3coreplsqlgen.R3CorePlSqlGen;
import com.release3.tf.core.Utils;
import com.release3.tf.core.form.AbstractForm;
import com.release3.tf.core.form.Settings;
import com.release3.tf.javagen.PlSqlParser;

public class JavaSqlModelGen {

	HashMap<String, List<BuiltinType>> builtinMap = new HashMap<String, List<BuiltinType>>();
	HashMap<String, String> nonCrudMethodsMap = new HashMap<String, String>();
	HashMap<String, String> crudMethodsMap = new HashMap<String, String>();

	public void setItemModelGeneration(R3JavaSqlGen r3JavaSqlGen) {
		List<JavaPlSqlType> javaSqlList = r3JavaSqlGen.getJavaSql();
		for (JavaPlSqlType javaPlSqlType : javaSqlList) {
			BuiltinType builtin = (BuiltinType) javaPlSqlType.getBuiltin();
			// String itemName = builtin.getItemName();
			// String propertyName = builtin.getPropertyName();
			String blockName = builtin.getBlockName();
			if (builtinMap.containsKey(blockName)) {
				List<BuiltinType> builtinList = (List<BuiltinType>) builtinMap
						.get(blockName);
				builtinList.add(builtin);
				builtinMap.put(blockName, builtinList);

			} else {
				List<BuiltinType> builtinList = new ArrayList<BuiltinType>();
				builtinList.add(builtin);
				builtinMap.put(blockName, builtinList);

			}
		}
	}

	public void generateJavaClass(R3JavaSqlGen r3JavaSqlGen,
			R3CorePlSqlGen r3CorePlSqlGen, R3ProgramUnitGen r3ProgramUnitGen,
			AbstractForm form) {

		VelocityEngine ve = new VelocityEngine();
		ve.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
		ve.setProperty("classpath.resource.loader.class",
				ClasspathResourceLoader.class.getName());
		ve.setProperty("input.encoding", "utf-8");
		// ve.setProperty(RuntimeConstants.RESOURCE_LOADER, "file");
		// ve.setProperty(RuntimeConstants.FILE_RESOURCE_LOADER_PATH,
		// file.getAbsolutePath());
		ve.setProperty(RuntimeConstants.FILE_RESOURCE_LOADER_CACHE, "true");
		ve.init();
		VelocityContext context = new VelocityContext();
		context.put("applicationName", Settings.getSettings()
				.getApplicationName());

		if (r3JavaSqlGen != null) {
			try {
				generateNonCRUD(context, ve, r3JavaSqlGen, r3ProgramUnitGen,
						form);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		if (r3CorePlSqlGen != null) {
			try {
				generateCRUD(context, ve, r3CorePlSqlGen);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		builtinMap = removeDuplicates(builtinMap);
		context.put("builtinMap", builtinMap);
		// Template template = ve.getTemplate("./templates/SetItemBuiltin.vm");
		Set<String> builtinKeySet = builtinMap.keySet();
		for (String builtinKey : builtinKeySet) {
			try {
				setItemGen(context, builtinKey, r3JavaSqlGen, ve);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

	}

	public void generateNonCRUD(VelocityContext context, VelocityEngine ve,
			R3JavaSqlGen r3JavaSqlGen, R3ProgramUnitGen r3ProgramUnitGen,
			AbstractForm form) throws IOException {
		context.put("formName", r3JavaSqlGen.getName());
		List<JavaPlSqlType> javaSqlList = r3JavaSqlGen.getJavaSql();
		for (JavaPlSqlType javaPlSqlType : javaSqlList) {
			if (javaPlSqlType.isToBeMigrate()
					&& (javaPlSqlType.isMoveToDB() == null || !javaPlSqlType
							.isMoveToDB())) {
				PlSqlParser sqlToJavaParser = new PlSqlParser();
				javaPlSqlType.setJavaPlSql(sqlToJavaParser.migrateToJava(
						javaPlSqlType.getJavaPlSql(), form.getFormName(),
						form.getModule()));
				String argument = "";
				if ("actionListener".equals(javaPlSqlType.getJavaMethod())) {
					argument = "ActionEvent event";
				}
				nonCrudMethodsMap.put(
						Utils.getJavaName(javaPlSqlType.getJavaPlSql()) + "("
								+ argument + ")",
						javaPlSqlType.getJavaPlSqlText());

				List<BuiltinType> builtins = javaPlSqlType.getBuiltin();
				for (BuiltinType builtin : builtins) {
					//
					// String propertyName = builtin.getPropertyName();
					String blockName = builtin.getBlockName();
					String itemName = builtin.getItemName();
					// only for block having item not equals to null
					if (blockName != null && itemName != null) {
						if (builtinMap.containsKey(blockName)) {

							List<BuiltinType> builtinList = (List<BuiltinType>) builtinMap
									.get(blockName);
							builtinList.add(builtin);
							builtinMap.put(blockName, builtinList);

						} else {
							List<BuiltinType> builtinList = new ArrayList<BuiltinType>();
							builtinList.add(builtin);
							builtinMap.put(blockName, builtinList);

						}
					}
				}
			}
		}

		List<R3ProgramUnit> r3ProgramUnitList = r3ProgramUnitGen
				.getR3ProgramUnit();
		for (R3ProgramUnit r3ProgramUnit : r3ProgramUnitList) {
			if (r3ProgramUnit.isToBeMigrate() != null
					&& r3ProgramUnit.isToBeMigrate()
					&& (r3ProgramUnit.isMoveToDB() == null || !r3ProgramUnit
							.isMoveToDB())) {
				// PlSqlParser sqlToJavaParser = new PlSqlParser();
				// r3ProgramUnit.getR3ProgramUnit().setName(sqlToJavaParser.migrateToJava(
				// r3ProgramUnit.getR3ProgramUnit().getName(),
				// form.getFormName(),
				// form.getModule()));
				String puText = r3ProgramUnit.getR3ProgramUnit()
						.getProgramUnitText().replace("&#10;", "\n");
				PlSqlParser parser = new PlSqlParser();
				puText = parser.migrateToJava(puText,
						r3ProgramUnitGen.getFormName(), null);
				r3ProgramUnit.getR3ProgramUnit().setProgramUnitText(puText);
				nonCrudMethodsMap.put(
						Utils.getJavaName(r3ProgramUnit.getR3ProgramUnit()
								.getName().replace('_', '-'))
								+ "()", r3ProgramUnit.getR3ProgramUnit()
								.getProgramUnitText());

			}
		}

		context.put("nonCrudMethodsMap", nonCrudMethodsMap);
		// String destDirPath = Settings.getSettings().getBaseDir()
		// + File.separator + "Model" + File.separator + "ejbModule"
		// + File.separator + Settings.getSettings().getApplicationName()
		// + File.separator + r3JavaSqlGen.getName() + File.separator
		// + "model";
		String destDirPath = Settings.getSettings().getBaseDir()
				+ File.separator + "ViewController" + File.separator + "src"
				+ File.separator + Settings.getSettings().getApplicationName()
				+ File.separator + r3JavaSqlGen.getName() + File.separator
				+ "control";
		File destDir = new File(destDirPath);
		Utils.makeDirs(destDir);
		String destPath = destDirPath + File.separator + r3JavaSqlGen.getName();

		// Template nonCrudTemplate = ve.getTemplate("./templates/NonCrud.vm");
		Template nonCrudTemplate = getTemplate(ve, "templates/NonCrud.vm");
		OutputStreamWriter nonCrudWriter = new OutputStreamWriter(
				new FileOutputStream(destPath + "_NonCrud.java"));
		nonCrudTemplate.merge(context, nonCrudWriter);
		nonCrudWriter.flush();
		nonCrudWriter.close();

		OutputStreamWriter intfsWriter = new OutputStreamWriter(
				new FileOutputStream(destPath + "_NonCrudIntfs.java"));
		// Template nonCrudIntsTemplate = ve
		// .getTemplate("./templates/NonCrudIntfs.vm");
		Template nonCrudIntsTemplate = getTemplate(ve,
				"templates/NonCrudIntfs.vm");

		nonCrudIntsTemplate.merge(context, intfsWriter);
		intfsWriter.flush();
		intfsWriter.close();
	}

	public void generateCRUD(VelocityContext context, VelocityEngine ve,
			R3CorePlSqlGen r3CorePlSqlGen) throws IOException {
		context.put("formName", r3CorePlSqlGen.getName());
		List<R3CorePlSql> r3CoreSqlList = r3CorePlSqlGen.getR3CorePlSql();
		for (R3CorePlSql r3CorePlSqlType : r3CoreSqlList) {
			if (r3CorePlSqlType.isToBeMigrate()) {
				List<BuiltinType> builtins = r3CorePlSqlType.getBuiltin();
				for (BuiltinType builtin : builtins) {
					//
					String builtinName = builtin.getBuiltinName();
					String blockName = builtin.getBlockName();
					String itemName = builtin.getItemName();
					// only for block having item not equals to null
					if (blockName != null && itemName != null) {
						if (builtinMap.containsKey(blockName)) {

							List<BuiltinType> builtinList = (List<BuiltinType>) builtinMap
									.get(blockName);
							builtinList.add(builtin);
							builtinMap.put(blockName, builtinList);

						} else {
							List<BuiltinType> builtinList = new ArrayList<BuiltinType>();
							builtinList.add(builtin);
							builtinMap.put(blockName, builtinList);

						}
					}
				}
			}
		}

	}

	public void setItemGen(VelocityContext context, String builtinKey,
			R3JavaSqlGen r3JavaSqlGen, VelocityEngine ve) throws IOException {

		List<BuiltinType> builtinList = (List<BuiltinType>) builtinMap
				.get(builtinKey);

		context.put("blockName", builtinKey);
		context.put("builtinList", builtinList);
		String setItemDestPath = Settings.getSettings().getBaseDir()
				+ File.separator + "Model" + File.separator + "ejbModule"
				+ File.separator + Settings.getSettings().getApplicationName()
				+ File.separator + r3JavaSqlGen.getName() + File.separator
				+ "model" + File.separator + r3JavaSqlGen.getName() + "_"
				+ builtinKey;

		OutputStreamWriter setItemWriter = new OutputStreamWriter(
				new FileOutputStream(setItemDestPath + "_UI.java"));
		// StringWriter swriter = new StringWriter();
		// Template setItemTemplate = ve
		// .getTemplate("./templates/SetItemBuiltin.vm");
		Template setItemTemplate = getTemplate(ve,
				"templates/SetItemBuiltin.vm");
		setItemTemplate.merge(context, setItemWriter);
		// setItemTemplate.merge(context, swriter);

		// System.out.println(swriter.toString());
		// Template setItemIntsTemplate = ve
		// .getTemplate("./templates/SetItemBuiltinIntfs.vm");
		Template setItemIntsTemplate = getTemplate(ve,
				"templates/SetItemBuiltinIntfs.vm");
		OutputStreamWriter intfsWriter = new OutputStreamWriter(
				new FileOutputStream(setItemDestPath + "_UIIntfs.java"));
		setItemIntsTemplate.merge(context, intfsWriter);
		// System.out.println(swriter.toString());
		setItemWriter.flush();
		setItemWriter.close();
		intfsWriter.flush();
		intfsWriter.close();
	}

	// /**
	// * Only for strings contains in Non-Crud Triggers like
	// WHEN-BUTTON-PRESSED,
	// * ON-CLEAR-DETAILS. (containing char '-') The char next to '-' will be
	// * appear in upper case, char '-' will be removed. (containing char '.')
	// The
	// * char next to '.' will be appear in upper case, char '.' will be
	// removed.
	// *
	// * @param name
	// */
	// public String getJavaName(String name) {
	// char[] splitters = { '-', '.' };
	// String javaName = getJavaName(name, splitters);
	// return javaName;
	// }
	//
	// public String getJavaName(String name, char[] splitters) {
	// for (char splitter : splitters) {
	// int index = name.indexOf(splitter);
	// if (index > 0) {
	// String oldSplitterStr = String.valueOf(splitter);
	// String newSplitterStr = String.valueOf('-');
	// name = name.replace(oldSplitterStr, newSplitterStr);
	//
	// }
	// }
	// int index = name.indexOf('-');
	//
	// StringBuilder javaName = new StringBuilder();
	// if (index > 0) {
	// String splitterStr = "\\" + String.valueOf('-');
	// String[] javaStrArr = name.split(splitterStr);
	//
	// for (int i = 0; i < javaStrArr.length; i++) {
	// String javaSubStr = javaStrArr[i];
	// if (i == 0) {
	// javaName.append(javaSubStr.toLowerCase());
	// } else {
	// javaName.append(
	// Character.toUpperCase(javaStrArr[i].charAt(0)))
	// .append(javaStrArr[i].substring(1).toLowerCase());
	//
	// }
	// }
	//
	// }
	// if (javaName.length() == 0) {
	// return name;
	// } else {
	// return javaName.toString();
	// }
	// }

	private HashMap<String, List<BuiltinType>> removeDuplicates(
			HashMap<String, List<BuiltinType>> builtinMap) {
		HashMap<String, List<BuiltinType>> newBuiltinListMap = new HashMap<String, List<BuiltinType>>();

		Set<Entry<String, List<BuiltinType>>> builtinsSet = builtinMap
				.entrySet();
		for (Entry<String, List<BuiltinType>> builtinEntry : builtinsSet) {
			String blockName = builtinEntry.getKey();
			List<BuiltinType> builtinList = builtinEntry.getValue();
			HashMap<String, BuiltinType> newBuiltinTypeMap = new HashMap<String, BuiltinType>();
			for (BuiltinType builtinType : builtinList) {
				String key = builtinType.getPropertyName();
				newBuiltinTypeMap.put(key, builtinType);
			}

			ArrayList<BuiltinType> newBuiltinList = new ArrayList<BuiltinType>();
			Set<Entry<String, BuiltinType>> builtins = newBuiltinTypeMap
					.entrySet();
			for (Entry<String, BuiltinType> entry : builtins) {
				BuiltinType value = entry.getValue();
				newBuiltinList.add(value);
			}
			newBuiltinListMap.put(blockName, newBuiltinList);
		}
		return newBuiltinListMap;
	}

	/**
	 * get a template file, copy it to tmp Dir returns its absolute path
	 * 
	 * @param templateFileName
	 * @return
	 * @throws IOException
	 */
	private String copyTemplateToTmpDirIfInsideJAR(String templateFileName)
			throws IOException {
		File tempDir = new File(System.getProperty("java.io.tmpdir"));
		File temporaryFile = new File(tempDir, templateFileName);
		InputStream templateStream = getClass().getResourceAsStream(
				templateFileName);
		try {
			Utils.copy(templateStream, new FileOutputStream(temporaryFile));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String absolutePath = temporaryFile.getAbsolutePath();
		return absolutePath;
	}

	private Template getTemplate(VelocityEngine ve, String template)
			throws IOException {
		Template nonCrudTemplate = null;
		try {
			nonCrudTemplate = ve.getTemplate("./" + template);
			return nonCrudTemplate;
		} catch (Exception ex) {
			if (nonCrudTemplate == null) {
				// String absolutePath =
				// copyTemplateToTmpDirIfInsideJAR(template);
				String absolutePath = Settings.getSettings().getTransformHome()
						+ "/" + template;
				nonCrudTemplate = ve.getTemplate(absolutePath);
				return nonCrudTemplate;
			}
		}
		return nonCrudTemplate;
	}
}
