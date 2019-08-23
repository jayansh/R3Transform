IMPORTANT: The project is deprecated, its put here only for reference. We don't guarantee, it will work for you, please use it at your own risk. There is no support available for same.

R3Transform is eclipse based desktop application for migrating Oracle forms to Java EE.

This is repository for R3Transform's eclipse plugin.

Release Notes: R3Transform 6.0M3 06 May 2013(for Eclipse Helios)
1. Premigration process fixed for Oracle Forms 11g.

Release Notes:  R3Transform 6.0 M2 29 April 2013(for Eclipse Helios)
1. Support added for 11g Dev Suite.

Release Notes:  R3Transform 6.0 M1 7 March 2013(for Eclipse Juno)
1. Release for Eclipse juno 64 bits

Release Notes:  R3Transform 5.6M1 19Feb2013
1. workspaceCombo on preference page fixed

Release Notes: R3Transform 5.5.1 M2 12 Sep 2012
1. License management changed for TF Free source Edition. Product based licensing, not the form based.
2. PlSqlAnalyis menu removed.

Release Notes: R3Transform 5.5 M1 27 Aug 2012
1. Use SQLWays Preference - to allow user to enable SQLWays.
2. Parameter type in/out when loaded during Load Parameter under Helper(Customization) were uppercase. where as lowercase is disarable.
3. Item level trigger "validator" removed from Helper(Customization).
4. TriggerList.xml updated.
5. Auto customization of WHEN-VALIDATE-ITEM trigger during process form pl/sql logic.
6. Added SQLWays License Check error messages.
7. LOVHandling WizardPage has been removed.

Release Notes: R3Transform 5.3M1 24 May 2012
1. Generating CRUD/NON-CRUD and ProgramUnit as text file including Procedure tags generation for NON-CRUD in as text file.
2. PLL migration support added(Under premigration tab.)
3. Support for Programunit to Java added in Process Form Logic under Migration Tab. (User need to check respective 
option in Window->Preferences->Tranform Preference)
4. Support added for passing value from one item to another in Helper for Canvas switcher.
5. Fixed – Under analysis in Premigration tab :warning if windowName==CanvasName==BlockName .
6. Fixed - license error not showing message
7. Fixed – Under Analysis warning if Formname not equal to module name.
8. Create XML Map for all form, block, item, triggers names (before and after)
9. Added support for item level trigger  -  whenValidateItem.
10. Removed f:validateLength child from h:inputText and added a tag to maxlength to inputText .Release Notes: R3Transform 5.2SR1 30 March 2012
1. Added - confirmation box for user  for  writing of package/procedure to database during Process Form Logic
Under Migration Tab. 
2. Search for package/procedure/function in database if not found in program unit
3. Initial Value for dbblock items.
4. Fixed - heap size error due to size of Migration log.
5. Removed - create view option from premigration process.

Release Notes: R3Transform 5.2M3 27 March 2012
1. Added support for migrating Buttons in grid



Release Notes: R3Transform 5.2M2 20 March 2012

1. Fixed – migrating program unit to java methods in ViewController/src.


Release Notes: R3Transform 5.2M1 19 March 2012
1. Fixed - label not migrated for checkbox
2. Helper – Passing parameter value option added to customization. (passing only value if block and item is null.)
3. Fixed - ListBox/Checkbox initial value doesn’t populate in entity
4. No of Records displayed in grid (generating rows as per form in grid.)
5. Panel header generating in Window now will be generated in WindowPanel(inside jspx)
6. Label migration for selectBooleanCheckbox
7. Removed width migration for selectBooleancheckbox
8. Convert h:commandButton to a4j:commandButton with reRender="parent Form Id". (rerender only part not whole page.)
9. Fixed - Block/Item filter issue in helper
10. Fixed - Toolkit hangs during migration if display item exist in form
11. Initialize items on create new object( in create method of control).
12. Templates fixes for Builtins/NonCrud generation.
13. Clear/Exit button on premigration.
14. EJB LOV query – Now supports LOV migration with query without a table or view.
15. Fixed - Cancel button not working- formLogic Dialog
16. Automatically generate pojo/actionlistener calls for noncrud

Release Notes: R3Transform5.1SR1 22 Feb 2012

1. Up/Down/delete button in helper
2. plsql/customization generation order of inLocal/out/msg etc
3. Post-Insert,Post-Update triggers support in R3Transform (Eclipse based only)
4. Save on OK button when press in Helper
5. Program Unit Handling in Process Forms Logic
6. Helper - load paramter if package specified (with dot) ignore default package
7. clear Console automatically
8. Alert migrations
