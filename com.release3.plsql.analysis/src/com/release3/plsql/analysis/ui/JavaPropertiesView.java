package com.release3.plsql.analysis.ui;

import java.io.IOException;
import java.util.StringTokenizer;

import javax.xml.bind.JAXBException;

import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.DropTarget;
import org.eclipse.swt.dnd.DropTargetAdapter;
import org.eclipse.swt.dnd.DropTargetEvent;
import org.eclipse.swt.dnd.TextTransfer;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.ui.part.ViewPart;

import com.converter.toolkit.ui.JAXBXMLWriter;
import com.oracle.xmlns.forms.Module;
import com.release3.plsql.analysis.property.model.FormProperties;
import com.release3.plsql.analysis.property.model.PropertiesContentProvider;
import com.release3.plsql.analysis.property.model.PropertiesLabelProvider;
import com.release3.plsql.analysis.property.model.PropertiesModelProvider;
import com.release3.tf.core.form.Settings;

public class JavaPropertiesView extends ViewPart {
	public static final String ID = "com.release3.plsql.analysis.JavaPropertiesView";

	public JavaPropertiesView() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void createPartControl(Composite parent) {
		try {

			ScrolledComposite scrolledCompositeTopLeft = new ScrolledComposite(
					parent, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
			scrolledCompositeTopLeft.setSize(863, 571);
			scrolledCompositeTopLeft.setExpandHorizontal(true);
			scrolledCompositeTopLeft.setExpandVertical(true);
			final TreeViewer treeViewer = new TreeViewer(
					scrolledCompositeTopLeft, SWT.BORDER);
			final Tree tree = treeViewer.getTree();

			Transfer[] types = new Transfer[] { TextTransfer.getInstance() };
			int operations = DND.DROP_MOVE;

			DropTarget targetItemsTree = new DropTarget(tree, operations);
			targetItemsTree.setTransfer(types);
			final Display display = parent.getShell().getDisplay();

			targetItemsTree.addDropListener(new DropTargetAdapter() {
				public void dragOver(DropTargetEvent event) {
					System.out.println(display.readAndDispatch());
					System.out.println(event.dataTypes[0].getClass());
					event.feedback = DND.FEEDBACK_EXPAND | DND.FEEDBACK_SCROLL;
					if (event.item != null) {
						TreeItem item = (TreeItem) event.item;
						if (item.getParentItem() != null) {
							if (item.getParentItem().getParentItem() == null) {
								event.feedback |= DND.FEEDBACK_NONE;
							} else {
								event.feedback |= DND.FEEDBACK_SELECT;
							}
						} else {
							event.feedback |= DND.FEEDBACK_NONE;
						}
					}
				}

				public void drop(DropTargetEvent event) {

					if (event.data == null) {
						event.detail = DND.DROP_NONE;
						return;
					}
					String text = (String) event.data;

					if (event.item == null) {

						TreeItem item = new TreeItem(tree, SWT.NONE);
						item.setText(text);
//						migrateToJava(text);
//						migrate(text);

					} else {
						TreeItem item = (TreeItem) event.item;
						TreeItem parent = item.getParentItem();
						if (parent != null) {
							if (parent.getParentItem() != null) {
								item.setText(text);
//								migrateToJava(text);
//								migrate(text);
							} else {
								event.detail = DND.DROP_NONE;
								return;
							}
						} else {
							event.detail = DND.DROP_NONE;
							return;
						}
					}
				}
			});

			scrolledCompositeTopLeft.setContent(tree);
			scrolledCompositeTopLeft.setMinSize(tree.computeSize(SWT.DEFAULT,
					SWT.DEFAULT));
			// treeViewer.setContentProvider(new PropertiesContentProvider());
			// treeViewer.setLabelProvider(new PropertiesLabelProvider());
			// treeViewer.setInput(getInput());
			// treeViewer.expandAll();

		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}

	@Override
	public void setFocus() {
		// TODO Auto-generated method stub

	}

	
}
