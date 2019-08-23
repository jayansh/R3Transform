package com.release3.dependencyChecker;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.List;

import oracle.forms.jdapi.Alert;
import oracle.forms.jdapi.AttachedLibrary;
import oracle.forms.jdapi.Block;
import oracle.forms.jdapi.Canvas;
import oracle.forms.jdapi.FormModule;
import oracle.forms.jdapi.Item;
import oracle.forms.jdapi.Jdapi;
import oracle.forms.jdapi.JdapiIterator;
import oracle.forms.jdapi.JdapiModule;
import oracle.forms.jdapi.JdapiObject;
import oracle.forms.jdapi.LOV;
import oracle.forms.jdapi.Menu;
import oracle.forms.jdapi.ObjectGroup;
import oracle.forms.jdapi.ProgramUnit;
import oracle.forms.jdapi.RecordGroup;
import oracle.forms.jdapi.Trigger;
import oracle.forms.jdapi.VisualAttribute;
import oracle.forms.jdapi.Window;

public class DependencyChecker {
	public static String[] libs = new String[100]; // 100 as
	public static ArrayList<String> missingLibs = new ArrayList<String>();
	public static ArrayList<String> dependentlibs = new ArrayList<String>();
	public static File sourceFrmPath;

	public static void dependencyCheck(String frmFilePath) {
		libs = Jdapi.getSubclassingDependencies(frmFilePath);

		// assume all the libraries are missing.
		for (String lib : libs) {
			dependentlibs.add(lib);
		}
		missingLibs = (ArrayList<String>) dependentlibs.clone();
		

	}

	public static ArrayList<String> getMissingDependencies() {
		String[] str = { "fmb", "olb" };
		FileFilter filter = new FileFilterExt("*.fmb,*.olb", str);
		File[] fileArr = sourceFrmPath.getParentFile().listFiles(filter);
		for (String lib : libs) {
			for (File file : fileArr) {
				// remove libraries which are present in source directory.
				if (file.getName().equalsIgnoreCase(lib)
						|| file.getName() == lib) {
					missingLibs.remove(lib);
					continue;
				}
			}
		}
		return missingLibs;
	}

	public static void main(String args[]) {
		try {
			if (args.length == 1) {
				sourceFrmPath = new File(args[0]);
				dependencyCheck(sourceFrmPath.getAbsolutePath());
				copySubclass(sourceFrmPath.getAbsolutePath());
				getMissingDependencies();
				for (String string : missingLibs) {
					System.out.println(string);
				}
			}else if(args.length > 1 && (args[1]=="dependent" || args[1].equalsIgnoreCase("dependent"))){
				sourceFrmPath = new File(args[0]);
				dependencyCheck(sourceFrmPath.getAbsolutePath());
				for (String string : dependentlibs) {
					
					System.out.println(string);
				}
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	private static void copySubclass(String frmFilePath) {
		JdapiModule jdapmod = JdapiModule.openModule(frmFilePath);
		if (jdapmod instanceof FormModule) {
			FormModule frmModule = (FormModule) jdapmod;
			// Form Triggers
			JdapiIterator triggerItr = frmModule.getTriggers();
			ArrayList<Trigger> triggerList = new ArrayList<Trigger>();
			while (triggerItr.hasNext()) {

				Trigger trigger = (Trigger) triggerItr.next();
				if (trigger.isSubclassed()) {
					triggerList.add(trigger);
				}
			}
			for (int i = 0; i < triggerList.size(); i++) {
				Trigger trigger = triggerList.get(i);
				if (missingLibs.contains(trigger.getParentFilename())) {
					String triggerName = trigger.getName();
					Boolean fireInQuery = trigger.isFireInQuery();
					Boolean displayInKeyboard = trigger
							.isDisplayInKeyboardHelp();
					Integer executeHierarchy = trigger.getExecuteHierarchy();
					String keyboardHelpText = trigger.getKeyboardHelpText();

					System.out.println("before getParentFilename::: "
							+ trigger.getParentFilename());
					System.out.println("before getParentFilepath::: "
							+ trigger.getParentFilepath());
					System.out.println("before getParentModule::: "
							+ trigger.getParentModule());
					System.out.println("before getParentModuleType::: "
							+ trigger.getParentModuleType());
					System.out.println("before getParentName::: "
							+ trigger.getParentName());
					String triggerText = trigger.getTriggerText();
					System.out.println("before triggerText::: " + triggerText);
					System.out.println("before getSubclassParent::: "
							+ trigger.getSubclassParent());
					System.out.println("before isSubclassObjectGroup::: "
							+ trigger.isSubclassObjectGroup());
					System.out.println("before isSubclassSubObject::: "
							+ trigger.isSubclassSubObject());
					System.out.println("before isSubclassed::: "
							+ trigger.isSubclassed());
					System.out.println("before getOwner::: "
							+ trigger.getOwner());
					System.out.println("before getClassName::: "
							+ trigger.getClassName());
					System.out
							.println("before getName::: " + trigger.getName());

					Trigger trigger2 = new Trigger(frmModule, triggerName);
					triggerList.add(i, trigger2);
					triggerList.remove(trigger);
					trigger2.move(trigger);
					trigger.destroy();

					trigger2.setName(triggerName);
					trigger2.setTriggerText(triggerText);
					trigger2.setDirtyInfo(true);

					if (fireInQuery != null) {
						trigger2.setFireInQuery(fireInQuery);
					}
					if (displayInKeyboard != null) {
						trigger2.setDisplayInKeyboardHelp(displayInKeyboard);
					}
					if (executeHierarchy != null) {
						trigger2.setExecuteHierarchy(executeHierarchy);
					}
					if (keyboardHelpText != null) {
						trigger2.setKeyboardHelpText(keyboardHelpText);
					}

				}
			}

			// Alerts

			JdapiIterator alertItr = frmModule.getAlerts();
			ArrayList<Alert> alertList = new ArrayList<Alert>();
			while (alertItr.hasNext()) {

				Alert alert = (Alert) alertItr.next();
				if (alert.isSubclassed()) {
					alertList.add(alert);
				}
			}

			for (int i = 0; i < alertList.size(); i++) {
				Alert alert = alertList.get(i);
				if (missingLibs.contains(alert.getParentFilename())) {

					String alertName = alert.getName();
					// functional
					String title = alert.getTitle();
					String message = alert.getAlertMessage();
					Integer alertStyle = alert.getAlertStyle();
					String button1Label = alert.getButton1Label();
					String button2Label = alert.getButton2Label();
					String button3Label = alert.getButton3Label();
					Integer defaultAlertButton = alert.getDefaultAlertButton();

					// Visaul Attribute
					String visaulAttributeName = alert.getVisualAttributeName();
					VisualAttribute visualAttributeObject = alert
							.getVisualAttributeObject();

					// Color

					String foregroundColor = alert.getForegroundColor();
					String backColor = alert.getBackColor();
					String fillPattern = alert.getFillPattern();

					// Font

					String fontName = alert.getFontName();
					Integer fontSize = alert.getFontSize();
					Integer fontWeight = alert.getFontWeight();
					Integer fontStyle = alert.getFontStyle();
					Integer fontSpacing = alert.getFontSpacing();

					// International

					Integer languageDirection = alert.getLanguageDirection();

					Alert alert2 = new Alert(frmModule, alertName);
					alertList.add(i, alert2);
					alertList.remove(alert);
					alert2.move(alert);
					alert.destroy();

					alert2.setName(alertName);

					if (title != null && title.length() > 0) {
						alert2.setTitle(title);
					}

					if (message != null && message.length() > 0) {
						alert2.setAlertMessage(message);
					}

					if (alertStyle != null) {
						alert2.setAlertStyle(alertStyle);
					}

					if (button1Label != null && button1Label.length() > 0) {
						alert2.setButton1Label(button1Label);
					}

					if (button2Label != null && button2Label.length() > 0) {
						alert2.setButton2Label(button2Label);
					}

					if (button3Label != null && button3Label.length() > 0) {
						alert2.setButton3Label(button3Label);
					}

					if (defaultAlertButton != null) {
						alert2.setDefaultAlertButton(defaultAlertButton);
					}
					if (visaulAttributeName != null
							&& visaulAttributeName.length() > 0) {
						alert2.setVisualAttributeName(visaulAttributeName);
					}
					if (visualAttributeObject != null) {
						alert2.setVisualAttributeObject(visualAttributeObject);
					}
					if (foregroundColor != null && foregroundColor.length() > 0) {
						alert2.setForegroundColor(foregroundColor);
					}
					if (backColor != null && backColor.length() > 0) {
						alert2.setBackColor(backColor);
					}

					if (fillPattern != null && fillPattern.length() > 0) {
						alert2.setFillPattern(fillPattern);
					}
					if (fontName != null && fontName.length() > 0) {
						alert2.setFontName(fontName);
					}
					if (fontSize != null) {
						alert2.setFontSize(fontSize);
					}
					if (fontWeight != null) {
						alert2.setFontWeight(fontWeight);
					}
					if (fontStyle != null) {
						alert2.setFontStyle(fontStyle);
					}
					if (fontSpacing != null) {
						alert2.setFontSpacing(fontSpacing);
					}

					if (languageDirection != null) {
						alert2.setLanguageDirection(languageDirection);
					}

				}
			}

			// Canvas

			JdapiIterator canvasItr = frmModule.getCanvases();
			ArrayList<Canvas> canvasList = new ArrayList<Canvas>();
			while (canvasItr.hasNext()) {

				Canvas canvas = (Canvas) canvasItr.next();
				if (canvas.isSubclassed()) {
					canvasList.add(canvas);
				}
			}

			for (int i = 0; i < canvasList.size(); i++) {
				Canvas canvas = canvasList.get(i);
				if (missingLibs.contains(canvas.getParentFilename())) {

					// General
					String canvasName = canvas.getName();
					Integer canvasType = canvas.getCanvasType();
					String comment = canvas.getComment();
					String helpBookTopic = canvas.getHelpBookTopic();

					// functional
					Boolean raiseOnEnter = canvas.isRaiseOnEnter();
					String popupMenuName = canvas.getPopupMenuName();
					Menu popupMenuObject = canvas.getPopupMenuObject();

					// Physical
					Boolean visible = canvas.isVisible();
					String windowName = canvas.getWindowName();
					Window winObject = canvas.getWindowObject();

					Integer viewportXPositionOnCanvas = canvas
							.getViewportXPositionOnCanvas();
					Integer viewportYPositionOnCanvas = canvas
							.getViewportYPositionOnCanvas();
					Integer viewportHeight = canvas.getViewportHeight();
					Integer viewportWidth = canvas.getViewportWidth();
					Integer bevel = canvas.getBevel();

					// Visaul Attribute
					String visaulAttributeName = canvas
							.getVisualAttributeName();
					VisualAttribute visualAttributeObject = canvas
							.getVisualAttributeObject();

					// Color

					String foregroundColor = canvas.getForegroundColor();
					String backColor = canvas.getBackColor();
					String fillPattern = canvas.getFillPattern();

					// Font

					String fontName = canvas.getFontName();
					Integer fontSize = canvas.getFontSize();
					Integer fontWeight = canvas.getFontWeight();
					Integer fontStyle = canvas.getFontStyle();
					Integer fontSpacing = canvas.getFontSpacing();

					// International

					Integer languageDirection = canvas.getLanguageDirection();

					Canvas canvas2 = new Canvas(frmModule, canvasName);
					canvasList.add(i, canvas2);
					canvasList.remove(canvas);
					canvas2.move(canvas);
					canvas.destroy();

					canvas2.setName(canvasName);

					if (canvasType != null) {
						canvas2.setCanvasType(canvasType);
					}

					if (comment != null && comment.length() > 0) {
						canvas2.setComment(comment);
					}

					if (helpBookTopic != null) {
						canvas2.setHelpBookTopic(helpBookTopic);
					}

					if (raiseOnEnter != null) {
						canvas2.setRaiseOnEnter(raiseOnEnter);
					}

					if (popupMenuName != null && popupMenuName.length() > 0) {
						canvas2.setPopupMenuName(popupMenuName);
					}

					if (popupMenuObject != null) {
						canvas2.setPopupMenuObject(popupMenuObject);
					}

					if (visible != null) {
						canvas2.setVisible(visible);
					}

					if (windowName != null && windowName.length() > 0) {
						canvas2.setWindowName(windowName);
					}

					if (winObject != null) {
						canvas2.setWindowObject(winObject);
					}

					if (viewportXPositionOnCanvas != null) {
						canvas2
								.setViewportXPositionOnCanvas(viewportXPositionOnCanvas);
					}

					if (viewportYPositionOnCanvas != null) {
						canvas2
								.setViewportYPositionOnCanvas(viewportYPositionOnCanvas);
					}

					if (viewportHeight != null) {
						canvas2.setViewportHeight(viewportHeight);
					}

					if (viewportWidth != null) {
						canvas2.setViewportWidth(viewportWidth);
					}

					if (bevel != null) {
						canvas2.setBevel(bevel);
					}

					if (visaulAttributeName != null
							&& visaulAttributeName.length() > 0) {
						canvas2.setVisualAttributeName(visaulAttributeName);
					}
					if (visualAttributeObject != null) {
						canvas2.setVisualAttributeObject(visualAttributeObject);
					}
					if (foregroundColor != null && foregroundColor.length() > 0) {
						canvas2.setForegroundColor(foregroundColor);
					}
					if (backColor != null && backColor.length() > 0) {
						canvas2.setBackColor(backColor);
					}

					if (fillPattern != null && fillPattern.length() > 0) {
						canvas2.setFillPattern(fillPattern);
					}
					if (fontName != null && fontName.length() > 0) {
						canvas2.setFontName(fontName);
					}
					if (fontSize != null) {
						canvas2.setFontSize(fontSize);
					}
					if (fontWeight != null) {
						canvas2.setFontWeight(fontWeight);
					}
					if (fontStyle != null) {
						canvas2.setFontStyle(fontStyle);
					}
					if (fontSpacing != null) {
						canvas2.setFontSpacing(fontSpacing);
					}

					if (languageDirection != null) {
						canvas2.setLanguageDirection(languageDirection);
					}

				}
			}
		}
		jdapmod.save("C:\\Cleanup\\subclasssample\\new1\\JAYBMG005.fmb");
		Jdapi.shutdown();
	}

	private static void copy(FormModule fmod, String filename) {
		JdapiIterator librs, ftrgs, btrgs, itrgs, progunits, blks, itms;

		ObjectGroup objgr, obg;
		LOV lov;
		RecordGroup rgr;
		Trigger ftrg, btrg, itrg;
		Block blk;
		Item itm;
		ProgramUnit progunit;
		Canvas cnvs;
		Window wndw;
		Alert alrt;

		AttachedLibrary lib, newlib;

		AttachedLibrary[] libs;

		int num_libs;

		JdapiModule jdapmod = JdapiModule.openModule(filename);
		// OBJECT GROUPS
		for (JdapiIterator obgs = fmod.getObjectGroups(); obgs.hasNext();) {
			obg = (ObjectGroup) obgs.next();
			if (obg.isSubclassed()) {
				if (obg.getParentFilename().indexOf('.') > -1)
					obg.setParentFilename(obg.getParentFilename().replaceAll(
							obg.getParentFilename().substring(0,
									obg.getParentFilename().indexOf('.')),
							obg.getParentFilename().substring(0,
									obg.getParentFilename().indexOf('.'))
									.toUpperCase()));
			}
		}
		// LOVS
		for (JdapiIterator lovs = fmod.getLOVs(); lovs.hasNext();) {
			lov = (LOV) lovs.next();
			if (lov.isSubclassed()) {
				if (lov.getParentFilename().indexOf('.') > -1)
					lov.setParentFilename(lov.getParentFilename().replaceAll(
							lov.getParentFilename().substring(0,
									lov.getParentFilename().indexOf('.')),
							lov.getParentFilename().substring(0,
									lov.getParentFilename().indexOf('.'))
									.toUpperCase()));
			}
		}
		// RECORDGROUPS
		for (JdapiIterator rgrs = fmod.getRecordGroups(); rgrs.hasNext();) {
			rgr = (RecordGroup) rgrs.next();
			if (rgr.isSubclassed()) {
				if (rgr.getParentFilename().indexOf('.') > -1)
					rgr.setParentFilename(rgr.getParentFilename().replaceAll(
							rgr.getParentFilename().substring(0,
									rgr.getParentFilename().indexOf('.')),
							rgr.getParentFilename().substring(0,
									rgr.getParentFilename().indexOf('.'))
									.toUpperCase()));
			}
		}
		// Canvas cnvs;
		for (JdapiIterator cnvss = fmod.getCanvases(); cnvss.hasNext();) {
			cnvs = (Canvas) cnvss.next();
			if (cnvs.isSubclassed()) {
				if (cnvs.getParentFilename().indexOf('.') > -1)
					cnvs.setParentFilename(cnvs.getParentFilename().replaceAll(
							cnvs.getParentFilename().substring(0,
									cnvs.getParentFilename().indexOf('.')),
							cnvs.getParentFilename().substring(0,
									cnvs.getParentFilename().indexOf('.'))
									.toUpperCase()));
			}
		}
		// Window wndw;
		for (JdapiIterator wndws = fmod.getWindows(); wndws.hasNext();) {
			wndw = (Window) wndws.next();
			if (wndw.isSubclassed()) {
				if (wndw.getParentFilename().indexOf('.') > -1)
					wndw.setParentFilename(wndw.getParentFilename().replaceAll(
							wndw.getParentFilename().substring(0,
									wndw.getParentFilename().indexOf('.')),
							wndw.getParentFilename().substring(0,
									wndw.getParentFilename().indexOf('.'))
									.toUpperCase()));
			}
		}
		// Alert alrt;
		for (JdapiIterator alrts = fmod.getAlerts(); alrts.hasNext();) {
			alrt = (Alert) alrts.next();
			if (alrt.isSubclassed()) {
				if (alrt.getParentFilename().indexOf('.') > -1)
					alrt.setParentFilename(alrt.getParentFilename().replaceAll(
							alrt.getParentFilename().substring(0,
									alrt.getParentFilename().indexOf('.')),
							alrt.getParentFilename().substring(0,
									alrt.getParentFilename().indexOf('.'))
									.toUpperCase()));
			}
		}

		// FORM TRIGGERS
		ftrgs = fmod.getTriggers();
		while (ftrgs.hasNext()) {
			ftrg = (Trigger) ftrgs.next();
			if (!ftrg.isSubclassed()) {
				UpdateTrigger(ftrg);
			} else {
				{
					if (ftrg.getParentFilename().indexOf('.') > -1)
						ftrg.setParentFilename(ftrg.getParentFilename()
								.replaceAll(
										ftrg.getParentFilename().substring(
												0,
												ftrg.getParentFilename()
														.indexOf('.')),
										ftrg.getParentFilename().substring(
												0,
												ftrg.getParentFilename()
														.indexOf('.'))
												.toUpperCase()));
				}

			}
		}
		// BLOCKS FOR BLOCK TRIGGERS
		blks = fmod.getBlocks();
		while (blks.hasNext()) {
			blk = (Block) blks.next();
			if (!blk.isSubclassed()) {
				btrgs = blk.getTriggers();
				while (btrgs.hasNext()) {
					btrg = (Trigger) btrgs.next();
					if (btrg.isSubclassed()) {
						if (btrg.getParentFilename().indexOf('.') > -1)
							btrg.setParentFilename(btrg.getParentFilename()
									.replaceAll(
											btrg.getParentFilename().substring(
													0,
													btrg.getParentFilename()
															.indexOf('.')),
											btrg.getParentFilename().substring(
													0,
													btrg.getParentFilename()
															.indexOf('.'))
													.toUpperCase()));
					}
					UpdateTrigger(btrg);
				}
				itms = blk.getItems();
				while (itms.hasNext()) {
					itm = (Item) itms.next();
					if (!itm.isSubclassed()) {
						itrgs = itm.getTriggers();
						while (itrgs.hasNext()) {
							itrg = (Trigger) itrgs.next();
							if (itrg.isSubclassed()) {
								if (itrg.getParentFilename().indexOf('.') > -1)
									itrg
											.setParentFilename(itrg
													.getParentFilename()
													.replaceAll(
															itrg
																	.getParentFilename()
																	.substring(
																			0,
																			itrg
																					.getParentFilename()
																					.indexOf(
																							'.')),
															itrg
																	.getParentFilename()
																	.substring(
																			0,
																			itrg
																					.getParentFilename()
																					.indexOf(
																							'.'))
																	.toUpperCase()));
							}
							UpdateTrigger(itrg);
						}
					} else {
						if (itm.getParentFilename().indexOf('.') > -1)
							itm.setParentFilename(itm.getParentFilename()
									.replaceAll(
											itm.getParentFilename().substring(
													0,
													itm.getParentFilename()
															.indexOf('.')),
											itm.getParentFilename().substring(
													0,
													itm.getParentFilename()
															.indexOf('.'))
													.toUpperCase()));
					}
				}
			} else {
				if (blk.getParentFilename().indexOf('.') > -1)
					blk.setParentFilename(blk.getParentFilename().replaceAll(
							blk.getParentFilename().substring(0,
									blk.getParentFilename().indexOf('.')),
							blk.getParentFilename().substring(0,
									blk.getParentFilename().indexOf('.'))
									.toUpperCase()));
			}
		}
		// PROGRAM UNITS
		progunits = fmod.getProgramUnits();
		while (progunits.hasNext()) {
			progunit = (ProgramUnit) progunits.next();
			if (!progunit.isSubclassed()) {
				UpdateProgramunit(progunit);

			} else {
				if (progunit.getParentFilename().indexOf('.') > -1)
					progunit.setParentFilename(progunit.getParentFilename()
							.replaceAll(
									progunit.getParentFilename().substring(
											0,
											progunit.getParentFilename()
													.indexOf('.')),
									progunit.getParentFilename().substring(
											0,
											progunit.getParentFilename()
													.indexOf('.'))
											.toUpperCase()));
			}
		}
		// Save the Oracle Forms Form module

		System.out.println("Form is saved as " + fmod.getAbsolutePath());
		fmod.save(fmod.getAbsolutePath());
		jdapmod.destroy();
	}

	private static void UpdateTrigger(Trigger trg) {

	}

	private static void UpdateProgramunit(ProgramUnit progu) {

	}
}
