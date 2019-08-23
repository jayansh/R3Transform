package com.release3.postmigrate;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Vector;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.release3.tf.core.form.Settings;

public class MenuConverter {

	DocumentBuilderFactory dbFactory;
	DocumentBuilder dBuilder;
	Document icefacesDoc;
	private static String REGEX = "&";

	public MenuConverter() {
		dbFactory = DocumentBuilderFactory.newInstance();
		try {
			dBuilder = dbFactory.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		icefacesDoc = dBuilder.newDocument();
	}

	public Document getDocument(){
		return icefacesDoc;
	}
	public void convertToXML(File mmbFile) throws ParserConfigurationException,
			SAXException {
		String oracleDevSuiteHome = Settings.getSettings()
				.getOracleDeveloperSuite();
		String command = oracleDevSuiteHome + File.separator + "bin"
				+ File.separator + "frmf2xml.bat OVERWRITE=YES " + mmbFile;
		System.out.println("Command::: " + command);
		
		try {
			Process p = Runtime.getRuntime().exec("cmd.exe /C " + command);
			BufferedReader in = new BufferedReader(new InputStreamReader(p
					.getInputStream()));
			String line = in.readLine();
			while (line != null) {
				line = in.readLine();
				System.out.println(line);
			}
			in.close();
			p.waitFor();
			CleanupProcess cleanupTask = new CleanupProcess();
			String xmlFilePath = mmbFile.getParent() + File.separator
			+ mmbFile.getName().replaceFirst(".mmb", "_mmb") + ".xml";
			File xmlFile = new File(xmlFilePath);
			cleanupTask.setFileUrl(xmlFilePath);
			cleanupTask.execute();
			loadMenuDocument(xmlFile);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	private MenuModule menuModule = new MenuModule();
	private Vector<MenuItem> menuItemList = new Vector<MenuItem>();
	private HashMap<String, MenuItem> menuItemMap = new HashMap<String, MenuItem>();
	private Vector<Menu> menuList = new Vector<Menu>();
	private HashMap<String, Menu> menuMap = new HashMap<String, Menu>();

	public void loadMenuDocument(File fXmlFile) throws ParserConfigurationException,
			SAXException, IOException {

		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		Document doc = dBuilder.parse(fXmlFile);
		doc.getDocumentElement().normalize();

		System.out.println("Root element :"
				+ doc.getDocumentElement().getNodeName());
		NodeList nList = doc.getElementsByTagName("MenuModule");
		System.out.println("-----------------------");

		for (int temp = 0; temp < nList.getLength(); temp++) {

			Node nNode = nList.item(temp);
			if (nNode.getNodeType() == Node.ELEMENT_NODE) {

				Element eElement = (Element) nNode;
				String name = eElement.getAttribute("Name").replaceAll(REGEX, "");
				String mainMenu = eElement.getAttribute("MainMenu").replaceAll(REGEX, "");;
				String startupCode = eElement.getAttribute("StartupCode").replaceAll(REGEX, "");;
				boolean sharedLibrary = Boolean.getBoolean(eElement
						.getAttribute("ShareLibrary"));
				String menuFilename = eElement.getAttribute("MenuFilename").replaceAll(REGEX, "");;

				menuModule.setName(name);
				menuModule.setSharedLibrary(sharedLibrary);
				menuModule.setStartupCode(startupCode);
				menuModule.setMenuFilename(menuFilename);
				createMenus(doc);
				generateJSPX();

			}
		}
	}

	public Vector<MenuItem> createMenuItems(Element parentNode) {
		NodeList menuItemNodeList = parentNode.getChildNodes();
		Vector<MenuItem> menuVector = new Vector<MenuItem>();
		for (int i = 0; i < menuItemNodeList.getLength(); i++) {
			Node nNode = menuItemNodeList.item(i);
			if (nNode.getNodeType() == Node.ELEMENT_NODE) {
				Element eElement = (Element) nNode;

				String name = eElement.getAttribute("Name").replaceAll(REGEX, "");;
				String subMenuName = eElement.getAttribute("SubMenuName").replaceAll(REGEX, "");;
				String commandType = eElement.getAttribute("CommandType").replaceAll(REGEX, "");;
				String visualAttributeName = eElement
						.getAttribute("VisualAttributeName").replaceAll(REGEX, "");;
				String label = eElement.getAttribute("Label").replaceAll(REGEX, "");;
				String menuItemCode = eElement.getAttribute("MenuItemCode").replaceAll(REGEX, "");;
				MenuItem menuItem = new MenuItem();
				menuItem.setName(name);
				menuItem.setCommandType(commandType);
				menuItem.setLabel(label);
				menuItem.setMenuItemCode(menuItemCode);
				menuItem.setSubMenuName(subMenuName);
				menuItem.setVisualAttributeName(visualAttributeName);
				menuItemMap.put(name, menuItem);
				menuItemList.add(menuItem);
				menuVector.add(menuItem);
			}
		}
		return menuVector;
	}

	public void createMenus(Document doc) {
		NodeList menuNodeList = doc.getElementsByTagName("Menu");
		for (int i = 0; i < menuNodeList.getLength(); i++) {
			Node nNode = menuNodeList.item(i);
			if (nNode.getNodeType() == Node.ELEMENT_NODE) {
				Element eElement = (Element) nNode;

				String name = eElement.getAttribute("Name").replaceAll(REGEX, "");;
				Menu menu = new Menu();
				menu.setMenuName(name);

				menu.setMenuItems(createMenuItems(eElement));
				menuList.add(menu);
				menuMap.put(name, menu);
				if (menuItemMap.containsKey(name)) {
					((MenuItem) menuItemMap.get(name)).setSubMenu(menu);
				}
			}
		}

	}

	private void generateJSPX() {
		System.out.println(Settings.getSettings().getBaseDir() + File.separator
				+ "ViewController" + File.separator + "WebContent"
				+ File.separator + Settings.getSettings().getApplicationName()
				+ "Main.jspx");
		String menuJSPXPath = Settings.getSettings().getBaseDir()
				+ File.separator + "ViewController" + File.separator
				+ "WebContent" + File.separator + "Menu" + File.separator
				+ Settings.getSettings().getApplicationName() + "_MENU.jspx";
		System.out.println("menuJSPXPath " + menuJSPXPath);
		File menuFile = new File(menuJSPXPath);
		if (!menuFile.getParentFile().exists()) {
			menuFile.getParentFile().mkdirs();
		}
		if (!menuFile.exists()) {
			try {
				menuFile.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		try {
			// Create file
			FileWriter fstream = new FileWriter(menuJSPXPath);
			BufferedWriter out = new BufferedWriter(fstream);
			generateMenuJSPX2(out);
			// Close the output stream
			out.close();
		} catch (Exception e) {// Catch exception if any
			System.err.println("Error: " + e.getMessage());
		}

	}

	private void generateMenuJSPX(BufferedWriter out) throws IOException {

		out.write("<?xml version=\"1.0\" encoding=\"windows-1252\"?>");
		out
				.write("<ice:panelGroup xmlns:frm=\"http://xmlns.oracle.com/Forms\" xmlns:a=\"jelly:ant\" xmlns:html=\"jelly:html\" xmlns:u=\"jelly:util\" xmlns:j=\"jelly:core\" xmlns:ice=\"http://www.icesoft.com/icefaces/component\" xmlns:ui=\"http://java.sun.com/jsf/facelets\" xmlns:x=\"jelly:xml\" styleClass=\"exampleBox menuBarContainer\">");
		out.write("<ice:menuBar orientation=\"horizontal\">");

		for (Menu menu : menuList) {
			for (MenuItem menuItem : menu.getMenuItems()) {
				out
						.write("<ice:menuItem xmlns:f=\"http://java.sun.com/jsf/core\" ");
				out.write("value =\"" + menuItem.getLabel() + " \">");
				out.write("</ice:menuItem> ");
			}
		}

		out.write("</ice:menuBar>");
		out.write("</ice:panelGroup>");

	}

	private void generateMenuJSPX2(BufferedWriter out)
			throws  TransformerException {

		Element rootElement = icefacesDoc.createElement("ice:panelGroup");
		rootElement.setAttribute("xmlns:frm", "http://xmlns.oracle.com/Forms");
		rootElement.setAttribute("xmlns:a", "jelly:ant");
		rootElement.setAttribute("xmlns:html", "jelly:html");
		rootElement.setAttribute("xmlns:u", "jelly:util");
		rootElement.setAttribute("xmlns:j", "jelly:core");
		rootElement.setAttribute("xmlns:ice",
				"http://www.icesoft.com/icefaces/component");
		rootElement
				.setAttribute("xmlns:ui", "http://java.sun.com/jsf/facelets");
		rootElement.setAttribute("xmlns:x", "jelly:xml");
		rootElement.setAttribute("styleClass", "exampleBox menuBarContainer");
		
		Element menuBarElement = icefacesDoc.createElement("ice:menuBar");
		menuBarElement.setAttribute("orientation", "horizontal");
		
		int depth = 0;
		for (Menu menu : menuList) {
			depth++;
			if (depth == 1) {
				for (MenuItem menuItem : menu.getMenuItems()) {

					Element menuElement = icefacesDoc.createElement("ice:menuItem");
					menuElement.setAttribute("xmlns:f",
							"http://java.sun.com/jsf/core");
					menuElement.setAttribute("value", menuItem.getLabel());
					
					appendChildMenuItem(menuElement,menuItem);
					menuBarElement.appendChild(menuElement);
				}
			}
			if (depth > 1) {
				break;
			}
		}
		rootElement.appendChild(menuBarElement);
		icefacesDoc.appendChild(rootElement);
		TransformerFactory tFactory = TransformerFactory.newInstance();
		Transformer transformer = tFactory.newTransformer();

		DOMSource source = new DOMSource(icefacesDoc);
		StreamResult result = new StreamResult(out);
		transformer.transform(source, result);

	}

	public void appendChildMenuItem(Element parentElement, MenuItem menuItem) {
		if (menuMap.containsKey(menuItem.getSubMenuName())) {
			
			Menu newMenu =  menuMap.get(menuItem.getSubMenuName());
			for (MenuItem menuItem2 : newMenu.getMenuItems()) {
				Element menuElement = icefacesDoc.createElement("ice:menuItem");
				menuElement.setAttribute("xmlns:f", "http://java.sun.com/jsf/core");
				menuElement.setAttribute("value",menuItem2.getLabel());
				
				appendChildMenuItem(menuElement, menuItem2);
				parentElement.appendChild(menuElement);
			}
		}

	}

}
