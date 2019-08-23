package r3transform;

import java.io.File;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.release3.postmigrate.IcefacesNamespaceContext;
import com.release3.postmigrate.MenuConverter;

public class PostMigrationComposite extends Composite {
	private Text txtMenuFilePath;
	private Text txtAttrValue;
	private Text txtParaValue;
	private Combo cmbAttrName ;
	Combo cmbParameter;
	Tree tree;
	MenuConverter converter = new MenuConverter();
	public static TreeItem currentItem;

	/**
	 * Create the composite.
	 * 
	 * @param parent
	 * @param style
	 */
	public PostMigrationComposite(final Composite parent, int style) {
		super(parent, style);

		Group grpMenubarGeneration = new Group(this, SWT.NONE);
		grpMenubarGeneration.setText("MenuBar Generation");
		grpMenubarGeneration.setBounds(10, 10, 423, 86);

		txtMenuFilePath = new Text(grpMenubarGeneration, SWT.BORDER);
		txtMenuFilePath.setBounds(56, 15, 290, 19);

		Button btnSelectMenuFiles = new Button(grpMenubarGeneration, SWT.NONE);
		btnSelectMenuFiles.setBounds(352, 13, 68, 23);
		btnSelectMenuFiles.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				FileDialog fileDialog = new FileDialog(parent.getShell());
				String[] extensions = { "*.mmb" };
				fileDialog.setFilterExtensions(extensions);
				String filePath = fileDialog.open();
				txtMenuFilePath.setText(filePath);

			}
		});
		btnSelectMenuFiles.setText("Browse...");

		Label lblMenuFile = new Label(grpMenubarGeneration, SWT.NONE);
		lblMenuFile.setBounds(3, 18, 49, 13);
		lblMenuFile.setText("Menu File:");

		Button btnGenerateMenuBar = new Button(grpMenubarGeneration, SWT.NONE);
		btnGenerateMenuBar.setBounds(352, 53, 68, 23);

		Group grpEditMenubar = new Group(this, SWT.NONE);
		grpEditMenubar.setText("Edit MenuBar");
		grpEditMenubar.setBounds(441, 10, 423, 211);

		Label lblActionlistener = new Label(grpEditMenubar, SWT.NONE);
		lblActionlistener.setBounds(10, 28, 79, 13);
		lblActionlistener.setText("Attribute Name:");

		txtAttrValue = new Text(grpEditMenubar, SWT.BORDER);
		txtAttrValue.setBounds(112, 54, 277, 19);

		Label lblAttributeValue = new Label(grpEditMenubar, SWT.NONE);
		lblAttributeValue.setBounds(10, 57, 79, 13);
		lblAttributeValue.setText("Attribute Value:");

		cmbAttrName = new Combo(grpEditMenubar, SWT.NONE);
		cmbAttrName.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				
			}
		});
		cmbAttrName.setBounds(112, 24, 277, 21);

		Group grpAddParameter = new Group(grpEditMenubar, SWT.NONE);
		grpAddParameter.setBounds(3, 86, 410, 114);
		grpAddParameter.setText("Add Parameter");

		Label lblParameterName = new Label(grpAddParameter, SWT.NONE);
		lblParameterName.setBounds(10, 49, 107, 13);
		lblParameterName.setText("Parameter AttrName:");

		Combo cmbParaName = new Combo(grpAddParameter, SWT.NONE);
		cmbParaName.setBounds(123, 45, 277, 21);

		Label lblParamaterValue = new Label(grpAddParameter, SWT.NONE);
		lblParamaterValue.setBounds(10, 80, 107, 13);
		lblParamaterValue.setText("Paramater AttrValue:");

		txtParaValue = new Text(grpAddParameter, SWT.BORDER);
		txtParaValue.setBounds(123, 77, 277, 19);

		Label lblSelectMenuitem = new Label(grpAddParameter, SWT.NONE);
		lblSelectMenuitem.setBounds(10, 18, 96, 13);
		lblSelectMenuitem.setText("Select Parameter:");

		cmbParameter = new Combo(grpAddParameter, SWT.NONE);
		cmbParameter.setBounds(123, 14, 277, 21);
		cmbParameter.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {

			}
		});

		btnGenerateMenuBar.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {

				try {
					converter.convertToXML(new File(txtMenuFilePath.getText()));

					TreeItem treeItemForm = new TreeItem(tree, SWT.NONE);
					treeItemForm.setText(converter.getDocument()
							.getDocumentElement().getNodeName());
					addTreeItem(treeItemForm, converter.getDocument()
							.getDocumentElement());

					Element rootElement = converter.getDocument()
							.getDocumentElement();
//					addMenuItemToCombo(rootElement, rootElement.getNodeName());

				} catch (ParserConfigurationException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (SAXException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnGenerateMenuBar.setText("Generate");

		tree = new Tree(this, SWT.BORDER);
		tree.setBounds(10, 101, 423, 530);

		Button btnSave = new Button(this, SWT.NONE);
		btnSave.setBounds(796, 237, 68, 23);
		btnSave.setText("Save");
		tree.addListener(SWT.Selection, new Listener() {

			public void handleEvent(Event event) {
				if (event.detail == SWT.CHECK) {
					System.out.println(event.item + " was checked.");
				} else {
					System.out.println(event.item + " was selected");
					TreeItem treeItem = (TreeItem) event.item;
					parentHierarchy(treeItem, treeItem.getText());
				}

			}
		});
	}

	private void addTreeItem(TreeItem treeItem, Element defaultTreeNode) {
		NodeList list = defaultTreeNode.getChildNodes();
		for (int i = 0; i < list.getLength(); i++) {
			TreeItem childTreeItem = new TreeItem(treeItem, SWT.NONE);
			Element childNode = (Element) defaultTreeNode.getChildNodes().item(
					i);
			if (childNode.getNodeName() == "ice:menuItem") {
				childTreeItem.setText(childNode.getNodeName() + "[@name='"
						+ childNode.getAttribute("value") + "']");
			} else {
				childTreeItem.setText(childNode.getNodeName());
			}
			addTreeItem(childTreeItem, childNode);
		}

	}

//	public void addMenuItemToCombo(Element element, String xPath) {
//		NodeList list = element.getChildNodes();
//		for (int i = 0; i < list.getLength(); i++) {
//			Element childNode = (Element) list.item(i);
//			if (childNode.getNodeName() == "ice:menuItem") {
//				xPath = xPath + "/" + childNode.getNodeName() + "[@name='"
//						+ childNode.getAttribute("value") + "']";
//			} else {
//				xPath = xPath + "/" + childNode.getNodeName();
//			}
//			if (childNode.getChildNodes().getLength() == 0) {
//				cmbParameter.add(xPath);
//			} else {
//				addMenuItemToCombo(childNode, xPath);
//			}
//		}
//	}

	private String currentXPathStr;

	private void parentHierarchy(TreeItem treeItem, String xPathStr) {

		// System.out.println("----" + treeItem.getParentItem());
		if (treeItem.getParentItem() != null) {
			System.out.println();
			TreeItem treeParentItem = treeItem.getParentItem();
			currentItem = treeItem;
			if (treeItem.getText().indexOf("ice:menuItem") > -1) {
				parentHierarchy(treeParentItem, treeParentItem.getText() + "//"
						+ xPathStr);
				System.out.print("----" + treeItem.toString());
			} else if (treeParentItem.getText().indexOf("ice:panelGroup") > -1
					&& treeItem.getText().indexOf("ice:menuBar") > -1) {
//				xPathStr = treeParentItem.getText() + "//" + xPathStr;
				xPathStr = treeParentItem.getText() + "/" + xPathStr;
				currentXPathStr = xPathStr;
				System.out.print("currentXPathStr:: "+xPathStr);
				getElementByName();
//				try {
//					Element element = getElement(xPathStr);
//					
//					NamedNodeMap map = element.getAttributes();
//					for(int i = 0 ; i < map.getLength() ; i++){
//						cmbAttrName.add(map.item(i).getNodeName());
//					}
//					
//					
//				} catch (XPathExpressionException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
				
			}
		}
	}

	private Element getElement(String xPathStr) throws XPathExpressionException {

		XPath xpath = XPathFactory.newInstance().newXPath();
		xpath.setNamespaceContext(new IcefacesNamespaceContext());
		// XPath Query for showing all nodes value
		XPathExpression expr= xpath.compile(xPathStr);
		
		Object result = expr.evaluate(converter.getDocument(),
				XPathConstants.NODESET);
		
		NodeList nodes = (NodeList) result;
		System.out.println(nodes.getLength());
		Element element = (Element) nodes.item(0);
		return element;

	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}
	private void getElementByName(){
		String xPath = "//ice:panelGroup//ice:menuBar//ice:menuItem[@value='DocumentViewer']";
		
		try {
			Element element = getElement(xPath);
			System.out.println("Element::: "+element.getNodeName());
		} catch (XPathExpressionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
}
