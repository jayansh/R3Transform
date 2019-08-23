package com.release3.tf.plsql.process;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBException;

import org.eclipse.core.runtime.Platform;

import com.converter.toolkit.ui.JAXBXMLReader;
import com.converter.toolkit.ui.JAXBXMLWriter;
import com.release3.customization.R3;
import com.release3.customization.R3.Trigger;
import com.release3.javasql.JavaPlSqlType;
import com.release3.javasql.R3JavaSqlGen;
import com.release3.plsqlgen.R3PlSqlFactory;
import com.release3.r3coreplsqlgen.R3CorePlSql;
import com.release3.r3coreplsqlgen.R3CorePlSqlGen;
import com.release3.tf.migration.model.MigrationForm;

public class CustomizationAnalysis {
	private R3 r3PreCustomization;
	private MigrationForm migrationForm;
	private JAXBXMLReader xmlReader = new JAXBXMLReader();
	private String destinationXmlPath;
	private List<Trigger> tobeRemoveTriggerList;

	public CustomizationAnalysis(MigrationForm form, File r3CustomizationFile,
			String destinationXmlPath) {
		this.migrationForm = form;
		this.r3PreCustomization = (R3) xmlReader.init(r3CustomizationFile,
				R3.class);
		this.destinationXmlPath = destinationXmlPath;
		this.tobeRemoveTriggerList = new ArrayList<Trigger>();
		// if (this.r3PreCustomization != null) {
		// tobeRemoveTriggerList.addAll(this.r3PreCustomization.getTrigger());
		// }
	}

	public void analysis() {
		File crudXmlFile = migrationForm.getR3CRUDXmlFile();
		R3CorePlSqlGen r3CorePlSqlGen = (R3CorePlSqlGen) xmlReader.init(
				crudXmlFile, R3CorePlSqlGen.class);
		List<R3CorePlSql> r3CorePlSqlist = r3CorePlSqlGen.getR3CorePlSql();

		for (R3CorePlSql r3CorePlSql : r3CorePlSqlist) {
			/* remove crud based triggers from customization file */
			if (!r3CorePlSql.isToBeMigrate() || !r3CorePlSql.isMoveToDB()) {
				analysis(r3CorePlSql);
			}

		}

		File nonCrudXmlFile = migrationForm.getR3NonCRUDXmlFile();
		R3JavaSqlGen r3JavaSqlGen = (R3JavaSqlGen) xmlReader.init(
				nonCrudXmlFile, R3JavaSqlGen.class);
		List<JavaPlSqlType> javaSqlList = r3JavaSqlGen.getJavaSql();
		for (JavaPlSqlType javaPlSqlType : javaSqlList) {
			if ( javaPlSqlType.getJavaPlSql().indexOf(
							"WHEN-VALIDATE-ITEM") > -1) {
				String jsfAttr = "whenValidateItem";
				String blockName = javaPlSqlType.getBlockName();
				String itemName = javaPlSqlType.getItemName();
				Trigger trigger = new Trigger();
				trigger.setBlock(blockName);
				trigger.setItem(itemName);
				trigger.setJsfattr(jsfAttr);
				trigger.setMethodType("none");
				r3PreCustomization.getTrigger().add(trigger);
			}
		}

		save();
	}

	private void save() {
		r3PreCustomization.getTrigger().removeAll(tobeRemoveTriggerList);
		try {
			JAXBXMLWriter.writeXmlWithOutNS(destinationXmlPath,
					r3PreCustomization);
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void analysis(R3CorePlSql r3CorePlSql) {
		List<Trigger> TriggerList = r3PreCustomization.getTrigger();
		for (Trigger trigger : TriggerList) {
			String jsfAttr = trigger.getJsfattr();
			String trigBlockName = trigger.getBlock();
			String blockName = r3CorePlSql.getBlockName();
			String plsqlName = r3CorePlSql.getPlSqlName();
			if (plsqlName != null && blockName != null) {
				if (plsqlName.indexOf(".") > -1) {
					plsqlName = plsqlName.split("\\.")[1];
				}
				plsqlName = getJavaName(plsqlName);
				// if (plsqlName.equals("preDelete")) {
				// plsqlName = "preRemove";
				// } else if (plsqlName.equals("postDelete")) {
				// plsqlName = "postRemove";
				// }
			}
			if (jsfAttr != null
					&& (jsfAttr == plsqlName || jsfAttr
							.equalsIgnoreCase(plsqlName))) {
				if (trigBlockName != null
						&& (trigBlockName == blockName || trigBlockName
								.equalsIgnoreCase(blockName))) {
					tobeRemoveTriggerList.add(trigger);
				} else if (blockName == "na"
						|| blockName.equalsIgnoreCase("na")) {
					tobeRemoveTriggerList.add(trigger);
				}
			}

			if (jsfAttr == null && trigBlockName == null
					&& trigger.getMethodType() == null) {
				tobeRemoveTriggerList.add(trigger);

			}
		}

	}

	/**
	 * Only for strings contains in TRIGGER_PLSQL like POST-QUERY,PRE-QUERY.
	 * (containing char '-')
	 * 
	 * @param plsqlName
	 */
	public String getJavaName(String plsqlName) {
		int index = plsqlName.indexOf('-');

		StringBuilder javaName = new StringBuilder();
		if (index > 0) {
			if (plsqlName == "PRE=DELETE"
					|| plsqlName.equalsIgnoreCase("PRE-DELETE")) {
				return "preDelete";
			}
			String[] javaStrArr = plsqlName.split("-");

			for (int i = 0; i < javaStrArr.length; i++) {
				String javaSubStr = javaStrArr[i];
				if (i == 0) {
					javaName.append(javaSubStr.toLowerCase());
				} else {
					javaName.append(
							Character.toUpperCase(javaStrArr[i].charAt(0)))
							.append(javaStrArr[i].substring(1).toLowerCase());

				}
			}

		}
		if (javaName.length() == 0) {
			return plsqlName;
		} else {
			return javaName.toString();
		}
	}

}
