package com.release3.transform.pref;

import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Table;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

import com.release3.tf.triggerlist.model.SupportedTriggerListContentProvider;
import com.release3.tf.triggerlist.model.SupportedTriggerListEditingSupport;
import com.release3.tf.triggerlist.model.SupportedTriggerListLabelProvider;
import com.release3.tf.triggerlist.model.SupportedTriggerListModelProvider;
import org.eclipse.swt.widgets.Button;

public class TriggerListPreferencePage extends PreferencePage implements
		IWorkbenchPreferencePage {
	private Group mainGroup;
	private Table table;
	private TableViewer tableViewer;

	/**
	 * @wbp.parser.constructor
	 */
	public TriggerListPreferencePage() {
		// TODO Auto-generated constructor stub
	}

	public TriggerListPreferencePage(String title) {
		super(title);
		// TODO Auto-generated constructor stub
	}

	public TriggerListPreferencePage(String title, ImageDescriptor image) {
		super(title, image);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void init(IWorkbench workbench) {
		// TODO Auto-generated method stub

	}

	@Override
	protected Control createContents(Composite parent) {
		Composite container = new TriggerListPrefComposite(parent, SWT.LEFT);

		setControl(container);
//		Composite container = new Composite(parent, SWT.NULL);
//
//		setControl(container);
//		mainGroup = new Group(container, SWT.NONE);
//		mainGroup.setBounds(10, 0, 583, 450);
//		tableViewer = new TableViewer(container, SWT.BORDER
//				| SWT.FULL_SELECTION);
//		createColumns(tableViewer);
		return container;
	}

//	private void createColumns(TableViewer viewer) {
//
//		int[] bounds = { 180, 90 };
//
//		for (int i = 0; i < PreferencePlugin.TRIGGERLIST_PAGE_TITLE.length; i++) {
//			final TableViewerColumn column = new TableViewerColumn(viewer,
//					SWT.NONE);
//			column.getColumn().setText(
//					PreferencePlugin.TRIGGERLIST_PAGE_TITLE[i]);
//			column.getColumn().setWidth(bounds[i]);
//			column.getColumn().setResizable(true);
//			column.getColumn().setMoveable(true);
//			column.setEditingSupport(new SupportedTriggerListEditingSupport(
//					viewer, i));
//
//		}
//		table = viewer.getTable();
//		table.removeAll();
//		table.setBounds(0, 55, 553, 345);
//		table.setHeaderVisible(true);
//		table.setLinesVisible(true);
//
//		tableViewer
//				.setContentProvider(new SupportedTriggerListContentProvider());
//		SupportedTriggerListLabelProvider labelProvider = new SupportedTriggerListLabelProvider();
//		tableViewer.setLabelProvider(labelProvider);
//		tableViewer.setInput(SupportedTriggerListModelProvider.getInstance()
//				.getSupportedTriggerList());
//	}
}
