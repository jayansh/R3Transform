package com.release3.transform.ui;

import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.TableEditor;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;

import com.converter.toolkit.ui.control.BlocksControl;
import com.converter.toolkit.ui.control.ItemsControl;
import com.converter.toolkit.ui.control.ParametersControl;
import com.converter.toolkit.ui.control.RecordGroupsControl;
import com.converter.toolkit.ui.custom.Parameter;
import com.converter.toolkit.ui.custom.RecordGroup;
import com.oracle.xmlns.forms.Block;
import com.oracle.xmlns.forms.Item;
import com.release3.tf.core.form.Settings;
import com.release3.tf.core.form.UISettings;
import com.release3.transform.constants.UIConstants;

/**
 * 
 * @author jayansh
 *	replaced with ParameterCustomizationGroup
 */
@Deprecated
public class CustomParameterGroup extends Group {
	private final Table table = new Table(this, SWT.BORDER);
	private Button btnNewParam;
	private Button btnSaveParam;
	public Button btnLoadParameters;
	private TableColumn tblclmnBlock;
	private TableColumn tblclmnItem;
	private TableColumn tblclmnParamType;
	private TableColumn tblclmnName;

	/**
	 * Create the composite.
	 * 
	 * @param parent
	 * @param style
	 */
	public CustomParameterGroup(final Composite parent, int style) {
		super(parent, style);
		this.setText("Parameter");

		table.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				UISettings.getUISettingsBean().getParametersControl()
						.rowSelection(table.getSelectionIndex());
			}
		});
		table.setBounds(10, 20, 409, 212);
		table.setHeaderVisible(true);
		table.setLinesVisible(true);

		tblclmnBlock = new TableColumn(table, SWT.NONE);
		tblclmnBlock.setWidth(100);
		tblclmnBlock.setText("Block");

		tblclmnItem = new TableColumn(table, SWT.NONE);
		tblclmnItem.setWidth(107);
		tblclmnItem.setText("Item");

		tblclmnParamType = new TableColumn(table, SWT.NONE);
		tblclmnParamType.setWidth(108);
		tblclmnParamType.setText("Param Type");

		tblclmnName = new TableColumn(table, SWT.NONE);
		tblclmnName.setWidth(87);
		tblclmnName.setText("Name");

		btnNewParam = new Button(this, SWT.NONE);
		btnNewParam.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {

				try {

					UISettings.getUISettingsBean().getParametersControl()
							.create();
					Parameter param = ((Parameter) UISettings
							.getUISettingsBean().getParametersControl()
							.getCurrentRow());
					fillTable(param);
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			}
		});
		btnNewParam.setBounds(277, 238, 68, 23);
		btnNewParam.setText("New");

		btnSaveParam = new Button(this, SWT.NONE);
		btnSaveParam.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {

				if (parent instanceof RecordGroupCustomization) {

					RecordGroupCustomization rgCust = ((RecordGroupCustomization) parent);
					RecordGroupsControl recordGrpControl = UISettings
							.getUISettingsBean().getRecordGroupsControl();
					((RecordGroup) recordGrpControl.getObject())
							.setTable(rgCust.txtTableName.getText());
					((RecordGroup) recordGrpControl.getObject())
							.setSchema(rgCust.txtSchemaName.getText());

				}
				
				
				
				ParametersControl paramControl = UISettings.getUISettingsBean()
						.getParametersControl();

				UISettings.getUISettingsBean().save();
			}
		});
		btnSaveParam.setBounds(351, 238, 68, 23);
		btnSaveParam.setText("Save");

		btnLoadParameters = new Button(this, SWT.NONE);
		btnLoadParameters.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				Object obj = UISettings.getUISettingsBean()
						.getTriggersControl().getObject();
				String formName = Settings.getSettings().getFmbFile();
				formName = formName.substring(0, formName.indexOf('.'));
				UISettings.getUISettingsBean().getParametersControl()
						.loadParameters(obj, formName);
				
				
				loadTable();
			}
		});
		btnLoadParameters.setBounds(299, 270, 120, 23);
		btnLoadParameters.setText("Load Parameters");

	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}

	public void setEnabled(boolean value) {
		super.setEnabled(value);
		table.setEnabled(value);
		btnNewParam.setEnabled(value);
		btnSaveParam.setEnabled(value);
		btnLoadParameters.setEnabled(value);
	}

	public void loadTable() {
		ParametersControl paramControl = UISettings.getUISettingsBean()
				.getParametersControl();
		List<Parameter> params = paramControl.getIterator();
		for (Parameter parameter : params) {
			fillTable(parameter);
		}
	}

	public void fillTable(final Parameter parameter) {
		TableItem tableItem = new TableItem(table, SWT.NONE);
		TableEditor editor = new TableEditor(table);

		// Button checkButton = new Button(table, SWT.CHECK);
		// checkButton.pack();
		//
		// editor.minimumWidth = checkButton.getSize().x;
		// editor.horizontalAlignment = SWT.CENTER;
		// editor.setEditor(checkButton, tableItem, 0);
		// editor = new TableEditor(table);

		final Combo comboBlock = new Combo(table, SWT.NONE);
		comboBlock.pack();
		BlocksControl blocksControl = UISettings.getUISettingsBean()
				.getBlocksConrol();
		List<Block> blockList = blocksControl.getIterator();
		String[] blockNameArray = UISettings.getUISettingsBean()
				.getBlockNamesArray(blockList);
		comboBlock.setItems(blockNameArray);
		comboBlock.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {

				try {
					int index = table.getSelectionIndex();
					UISettings.getUISettingsBean().getParametersControl()
							.rowSelection(index);
					// Parameter param = ((Parameter) UISettings
					// .getUISettingsBean().getParametersControl()
					// .getCurrentRow());
					parameter.setBlock(comboBlock.getItem(comboBlock
							.getSelectionIndex()));
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			}
		});
		int indexBlock = UISettings.getUISettingsBean().indexOf(blockNameArray,
				parameter.getBlock());
		if (indexBlock > -1) {
			comboBlock.select(indexBlock);
		} else {
			comboBlock.select(0);
		}

		editor.minimumWidth = comboBlock.getSize().x;
		editor.horizontalAlignment = SWT.CENTER;
		editor.setEditor(comboBlock, tableItem, 0);
		editor = new TableEditor(table);

		final Combo comboItem = new Combo(table, SWT.NONE);
		comboItem.pack();
		ItemsControl itemsControl = UISettings.getUISettingsBean()
				.getItemsControl();
		List<Item> itemList = itemsControl.getIterator();
		String[] itemNameArray = UISettings.getUISettingsBean()
				.getItemNamesArray(itemList);
		comboItem.setItems(itemNameArray);
		comboItem.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {

				try {
					// Parameter param = ((Parameter) UISettings
					// .getUISettingsBean().getParametersControl()
					// .getCurrentRow());
					parameter.setItem((comboItem.getItem(comboItem
							.getSelectionIndex())));
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			}
		});

		int indexItem = UISettings.getUISettingsBean().indexOf(itemNameArray,
				parameter.getItem());
		if (indexItem > -1) {
			comboItem.select(indexItem);
		} else {
			comboItem.select(0);
		}
		editor.minimumWidth = comboItem.getSize().x;
		editor.horizontalAlignment = SWT.CENTER;
		editor.setEditor(comboItem, tableItem, 1);
		editor = new TableEditor(table);

		// comboBlock.addSelectionListener(new SelectionAdapter() {
		// @Override
		// public void widgetSelected(SelectionEvent e) {
		// String currentComboBlock =
		// comboBlock.getItem(comboBlock.getSelectionIndex());
		// String currentComboItem =
		// comboItem.getItem(comboItem.getSelectionIndex());
		// }
		// });
		final Combo comboParamType = new Combo(table, SWT.NONE);
		comboParamType.pack();
		comboParamType.setItems(UIConstants.TRIGGER_PARAMETER_TYPE_LABELS);
		comboParamType.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {

				try {
					// Parameter param = ((Parameter) UISettings
					// .getUISettingsBean().getParametersControl()
					// .getCurrentRow());
					parameter.setType((comboParamType.getItem(comboParamType
							.getSelectionIndex())));
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			}
		});
		editor.minimumWidth = comboParamType.getSize().x;
		editor.horizontalAlignment = SWT.CENTER;
		editor.setEditor(comboParamType, tableItem, 2);
		editor = new TableEditor(table);

		int indexParamType = UISettings.getUISettingsBean().indexOf(
				UIConstants.TRIGGER_PARAMETER_TYPE_LABELS, parameter.getType());
		if (indexParamType != -1) {
			comboParamType.select(indexParamType);
		} else {
			comboParamType.select(0);
		}

		final Text txtName = new Text(table, SWT.NONE);
		txtName.pack();
		if (parameter.getName() != null) {
			txtName.setText(parameter.getName());
		}
		txtName.addKeyListener(new KeyListener() {
			String name = "";

			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub

			}

			public void keyReleased(KeyEvent e) {
				name = txtName.getText();
				parameter.setName(name);

			}
		});

		editor.minimumWidth = txtName.getSize().x;
		editor.horizontalAlignment = SWT.CENTER;
		editor.setEditor(txtName, tableItem, 3);

	}
}
