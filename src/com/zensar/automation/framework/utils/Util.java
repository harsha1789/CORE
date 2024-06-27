package com.zensar.automation.framework.utils;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Base64.Decoder;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.TimeZone;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.zensar.automation.framework.library.PropertyReader;
import com.zensar.automation.framework.model.DeviceInfo;
import com.zensar.automation.framework.model.Display;

import jcifs.smb.NtlmPasswordAuthentication;
import jcifs.smb.SmbFile;
import jcifs.smb.SmbFileOutputStream;
import net.lightbody.bmp.BrowserMobProxyServer;
import net.lightbody.bmp.core.har.Har;
import net.lightbody.bmp.core.har.HarEntry;
import net.lightbody.bmp.core.har.HarLog;

public class Util {

	
	public static final String UTF8_BOM = "\uFEFF";
	Logger log = Logger.getLogger(Util.class.getName());
	
	/**
	 * Date: 03/04/2017 Author: Kanchan Badhiye Description: clickAt() use for
	 * getting user data and click on particular button Parameter:
	 * @param nodeValue
	 * @param attribute1
	 * @param xmlData
	 * @return arrayList
	 */
	public ArrayList<String> getXMLDataInArray(String nodeValue, String attribute1, String xmlData)
	{
		List<String> a1=new ArrayList<>();
		try
		{
			File inputFile = new File(xmlData );
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			/*dbFactory.setAttribute(XMLConstants.ACCESS_EXTERNAL_DTD, ""); 
			dbFactory.setAttribute(XMLConstants.ACCESS_EXTERNAL_SCHEMA, ""); 
*/
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(inputFile);
			doc.getDocumentElement().normalize();
			TransformerFactory tf = TransformerFactory.newInstance();
			/*tf.setAttribute(XMLConstants.ACCESS_EXTERNAL_DTD, ""); 
			tf.setAttribute(XMLConstants.ACCESS_EXTERNAL_STYLESHEET, ""); 
*/
			javax.xml.transform.Transformer transformer;
			transformer = tf.newTransformer();
			StringWriter writer = new StringWriter();
			//transform document to string
			transformer.transform(new DOMSource(doc), new StreamResult(writer));
			String xmlString = writer.getBuffer().toString(); 
			int length=xmlLength(xmlData,"SymbolSet");
			for(int i=0;i<length;i++)
			{
				String dataList=parseXMLByIndex(nodeValue,attribute1,"", "", xmlString,i);
				a1.add(dataList);
			}
			Thread.sleep(10);
		}
		catch(Exception e)
		{
			log.error(e);
		}

		return (ArrayList<String>) a1;
	}
	/**
	 * This method will click at the point specify in parameter
	 * @param userdata
	 * @param webdriver
	 * @param canVasXpath
	 * @return String
	 */
	public String clickAt(String userdata, WebDriver webdriver, String canVasXpath) {
		int x;
		int y;
		int userElementY;
		double userwidth;
		double userheight;
		double userElementX;

		String[] coordinates1 = userdata.split(",");
		userwidth = Integer.parseInt(coordinates1[0]);
		userheight = Integer.parseInt(coordinates1[1]);
		userElementX = Integer.parseInt(coordinates1[2]);
		userElementY = Integer.parseInt(coordinates1[3]);
		Actions act = new Actions(webdriver);
		WebElement ele1 = webdriver.findElement(By.xpath(canVasXpath));
		double deviceHeight = ele1.getSize().height;
		double deviceWidth = ele1.getSize().width;
		x = (int) ((deviceWidth / userwidth) * userElementX);
		y = (int) ((deviceHeight / userheight) * userElementY);
		act.moveToElement(ele1, x, y).click().build().perform();
		return x + "," + y;
	}
	/**
	 * This methode will read the xml file 
	 * @param nodeValue
	 * @param attribute1
	 * @param attribute2
	 * @param attribute3
	 * @param xmlData
	 * @param length
	 * @param index
	 * @return string
	 */
	public String readXML(String nodeValue, String attribute1, String attribute2, String attribute3, String xmlData,int length,int index)
	{
		try
		{
			File inputFile = new File(xmlData );
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			/*dbFactory.setAttribute(XMLConstants.ACCESS_EXTERNAL_DTD, ""); 
			dbFactory.setAttribute(XMLConstants.ACCESS_EXTERNAL_SCHEMA, ""); */
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(inputFile);
			doc.getDocumentElement().normalize();
			TransformerFactory tf = TransformerFactory.newInstance();
			/*tf.setAttribute(XMLConstants.ACCESS_EXTERNAL_DTD, ""); 
			tf.setAttribute(XMLConstants.ACCESS_EXTERNAL_STYLESHEET, "");*/ 
			javax.xml.transform.Transformer transformer;
			transformer = tf.newTransformer();
			StringWriter writer = new StringWriter();
			//transform document to string
			transformer.transform(new DOMSource(doc), new StreamResult(writer));
			String xmlString = writer.getBuffer().toString(); 
			if(length>=0)
			{
				return parseXMLByIndex(nodeValue,attribute1,attribute2, attribute3, xmlString,index); 
			}
		}
		catch (Exception e) {
			log.error(e.getMessage());
		}
		return null;
	}
	/**
	 * This methode will return the XML file lenght
	 * @param xmlData
	 * @param nodeVAlue
	 * @return int
	 */
	public int xmlLength(String xmlData,String nodeVAlue)
	{
		int length=0;
		int lengthnew=0;
		try
		{
			File inputFile = new File(xmlData );
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			/*dbFactory.setAttribute(XMLConstants.ACCESS_EXTERNAL_DTD, ""); 
			dbFactory.setAttribute(XMLConstants.ACCESS_EXTERNAL_SCHEMA, ""); 
			*/
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(inputFile);
			doc.getDocumentElement().normalize();
			TransformerFactory tf = TransformerFactory.newInstance();
			/*tf.setAttribute(XMLConstants.ACCESS_EXTERNAL_DTD, ""); 
			tf.setAttribute(XMLConstants.ACCESS_EXTERNAL_STYLESHEET, "");*/ 
			javax.xml.transform.Transformer transformer;

			transformer = tf.newTransformer();
			StringWriter writer = new StringWriter();
			//transform document to string
			transformer.transform(new DOMSource(doc), new StreamResult(writer));
			NodeList nList = doc.getElementsByTagName(nodeVAlue);
			Node nNode = nList.item(0);

			NodeList childnodes=nNode.getChildNodes();
			length= nNode.getChildNodes().getLength();

			for(int i=0;i<length-1;i++)
			{
				if(childnodes.item(i).hasAttributes())
					lengthnew++;
			}
		}
		catch (Exception e) {
			log.error(e.getMessage());
		}
		return lengthnew;
	}

	/**
	 * Description: This
	 * function is used for filter Data from the Har
	 * @param nodeValue
	 * @param attribute1
	 * @param attribute2
	 * @param attribute3
	 * @param proxy
	 * @return String
	 */
	public String getData(String nodeValue, String attribute1, String attribute2, String attribute3,
			BrowserMobProxyServer proxy) {
		Har nhar;
		HarLog hardata;
		List<HarEntry> entries;
		Iterator<HarEntry> itr;
		
		nhar = proxy.getHar();
		hardata = nhar.getLog();
		entries = hardata.getEntries();
		itr = entries.iterator();
		String data = "";
		log.debug(itr);
		while (itr.hasNext()) {
			try {
				HarEntry entry = itr.next();
				String requestUrl = entry.getRequest().getUrl();
				if (requestUrl.contains("x.x?")) {
					String reloadData = entry.getResponse().getContent().getText();
					log.debug("Response Data is==" + reloadData);
					if (reloadData.contains(nodeValue)) {
						data = parseXML(nodeValue, attribute1, attribute2, attribute3, reloadData);
					}
				}
			} catch (Exception e) {
				log.error(e.getStackTrace());
			}
		}
		return data;
	}

	/**
	 *  This function is used for get Data from the Har
	 * @param nodeValue
	 * @param attribute1
	 * @param attribute2
	 * @param attribute3
	 * @param xmlData
	 * @return String
	 * @throws SAXException
	 * @throws IOException
	 * @throws ParserConfigurationException
	 */
	public String parseXML(String nodeValue, String attribute1, String attribute2, String attribute3, String xmlData)
			throws SAXException, IOException, ParserConfigurationException {
		String dataList = "";
		Node nNode;
		NodeList nList;
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		/*dbFactory.setAttribute(XMLConstants.ACCESS_EXTERNAL_DTD, ""); 
		dbFactory.setAttribute(XMLConstants.ACCESS_EXTERNAL_SCHEMA, ""); 
		*/
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		InputSource is = new InputSource();
		is.setCharacterStream(new StringReader(xmlData));
		Document doc = dBuilder.parse(is);
		doc.getDocumentElement().normalize();
		log.debug("Root element :" + doc.getDocumentElement().getNodeName());
		nList = doc.getElementsByTagName(nodeValue);
		nNode = nList.item(0);
		if (nNode.getNodeType() == Node.ELEMENT_NODE) {
			Element eElement = (Element) nNode;
			dataList += eElement.getAttribute(attribute1) + ",";
			dataList += eElement.getAttribute(attribute2) + ",";
			dataList += eElement.getAttribute(attribute3);
		}
		return dataList;
	}
	
	/**
	 * this function is used for get Data from the Har by index
	 * @param nodeValue
	 * @param attribute1
	 * @param attribute2
	 * @param attribute3
	 * @param xmlData
	 * @param index
	 * @return String
	 * @throws SAXException
	 * @throws IOException
	 * @throws ParserConfigurationException
	 */
	public String parseXMLByIndex(String nodeValue, String attribute1, String attribute2, String attribute3, String xmlData,int index)
			throws SAXException, IOException, ParserConfigurationException {
		String dataList = "";
		Node nNode;
		NodeList nList;
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		/*dbFactory.setAttribute(XMLConstants.ACCESS_EXTERNAL_DTD, ""); 
		dbFactory.setAttribute(XMLConstants.ACCESS_EXTERNAL_SCHEMA, ""); */
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		InputSource is = new InputSource();
		is.setCharacterStream(new StringReader(xmlData));
		Document doc = dBuilder.parse(is);
		doc.getDocumentElement().normalize();
		nList = doc.getElementsByTagName(nodeValue);
		nNode = nList.item(index);
		if (nNode.getNodeType() == Node.ELEMENT_NODE) {
			Element eElement = (Element) nNode;
			dataList += eElement.getAttribute(attribute1) + ",";
			dataList += eElement.getAttribute(attribute2) + ",";
			dataList += eElement.getAttribute(attribute3);
		}
		return dataList;
	}
	/**
	 * Date: 25/10/2017 Author: Kamal Kumar Vishwakarma Description: This
	 * function is used for removing BOM data from the file
	 */

	public String removeUTF8BOM(String s) {
		if (s.startsWith(UTF8_BOM)) {
			s = s.substring(1);
		}
		return s;
	}

	/**
	 * Date: 25/10/2017 Author: Kamal Kumar Vishwakarma Description: This
	 * function is used for get the language.json file from the Build
	 */

	public String getResponseLanguageFile(BrowserMobProxyServer proxy) {
		Har nhar;
		nhar = proxy.getHar();
		HarLog hardata;
		List<HarEntry> entries;
		Iterator<HarEntry> itr;
		String responseLanguageContent = null;
		
		hardata = nhar.getLog();
		entries = hardata.getEntries();
		itr = entries.iterator();

		while(itr.hasNext()) {
			HarEntry entry = itr.next();
			String requestUrl = entry.getRequest().getUrl();

			if(requestUrl.matches("(.*)resources/language(.*).json") || requestUrl.contains("rpt.cedexis.com") ) {
				responseLanguageContent = removeUTF8BOM(entry.getResponse().getContent().getText());
			}
		}

		return responseLanguageContent;
	}  

	
	
	/**
	 * This function copy  Test Data to CasinoAS1
	 * @param src
	 * @param dest
	 * @throws IOException
	 */
	public void copyFolder(File src, File dest) throws IOException {

		if (src.isDirectory()) {
			if (!dest.exists()) {
				dest.mkdir();
			}

			String[] files = src.list();
			for (String file : files) {

				File srcFile = new File(src, file);
				File destFile = new File(dest, file);
				copyFolder(srcFile, destFile);
			}
		} else {
			try(InputStream in = new FileInputStream(src)){
				try(OutputStream out = new FileOutputStream(dest)){
					byte[] buffer = new byte[1024];

					int length;
					while ((length = in.read(buffer)) > 0) {
						out.write(buffer, 0, length);
					}
				}
			}
		}
		log.debug("copying the source xml file to "+dest);
	}

	/**
	 * This method read the languages configured in language sheet 
	 * @return
	 */
	public List<Map> readLangList(){

		List<Map> langList=new ArrayList<>();
		ExcelDataPoolManager excelpoolmanager = new ExcelDataPoolManager();
		Map<String, String> currentRow = null;
		try {
			String testDataExcelPath=PropertyReader.getInstance().getProperty("TestData_Excel_Path");
			int totalRows = excelpoolmanager.rowCount(testDataExcelPath, Constant.LANG_XL_SHEET_NAME);
			for(int count =0 ;count<totalRows;count++){
				currentRow	= excelpoolmanager.readExcelByRow(testDataExcelPath, Constant.LANG_XL_SHEET_NAME,count);
				String languageDescription = currentRow.get(Constant.LANGUAGE).trim();
				String languageCode = currentRow.get(Constant.LANG_CODE).trim();
				Map<String,String> langrow=new HashMap<>();
				langrow.put("Language",languageDescription);
				langrow.put("Language Code",languageCode);
				langList.add(langrow);
			}
		} catch (IOException e) {
			log.error(e.getMessage(),e);
		}
		return langList;
	}


	/**
	 * Method read the supported device list from device list sheet
	 * @return
	 */

	public List<DeviceInfo> readDeviceList(){

		List<DeviceInfo> deviceList=new ArrayList<>();
		ExcelDataPoolManager excelpoolmanager = new ExcelDataPoolManager();
		Map<String, String> currentRow = null;
		try {
			String testDataExcelPath=PropertyReader.getInstance().getProperty("TestData_Excel_Path");
			int totalRows = excelpoolmanager.rowCount(testDataExcelPath, "DeviceList");
			for(int count =1 ;count<totalRows;count++){
				currentRow	= excelpoolmanager.readExcelByRow(testDataExcelPath, "DeviceList",count);
				String osPlatform=currentRow.get("OSPlatform").trim();
				String devicename = currentRow.get("Device").trim();
				String size = currentRow.get("Display Size").trim();
				String size1=size.replace("pixels","" ).trim();
				String[] sizenew=size1.split("x");
				DeviceInfo device=new DeviceInfo();
				Display display= new Display();
				display.setWidth(Integer.parseInt(sizenew[0].trim()));
				display.setheight(Integer.parseInt(sizenew[1].trim()));
				//
				//device.setModel(devicename);
				//
				device.setMarketName(devicename);
				device.setDisplay(display);
				device.setOsPlatform(osPlatform);
				deviceList.add(device);
			}
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		}
		log.info("Total no of devices in xls = "+deviceList.size());
		return deviceList;
	}

	/**
	 * This method copy file to server 
	 * @param src
	 * @param destpath
	 * @throws IOException
	 */
	public void copyFolder(File src, String destpath) throws IOException {
		ByteArrayInputStream inputStream = null;
		SmbFileOutputStream sfos= null;
		try {
			final byte[] buffer =new byte[10 * 8024];
			

			byte[] data =FileUtils.readFileToByteArray(src);
			inputStream = new ByteArrayInputStream(data);
			Decoder decoder=Base64.getDecoder();

			String domain=new String(decoder.decode(PropertyReader.getInstance().getProperty("Domain")));
			String domainUserName=new String(decoder.decode(PropertyReader.getInstance().getProperty("DomainUserName")));
			String domainPassword=new String(decoder.decode(PropertyReader.getInstance().getProperty("DomainPassword")));


			NtlmPasswordAuthentication auth = new NtlmPasswordAuthentication(domain, domainUserName, domainPassword);
			SmbFile remoteFile = new SmbFile(destpath, auth);
			sfos= new SmbFileOutputStream(remoteFile);
			int count =0;
			while((count = inputStream.read(buffer)) >0){
				sfos.write(buffer,0,count);
			}

			sfos.flush();
			inputStream.close();

		} catch (Exception e) {
			log.error("Exception while copying the file",e);
		}finally {
			if(sfos!=null)
				sfos.close();
		}
	}

	/**
	 * This method deletethe file from server
	 * @param filePath
	 * @throws IOException
	 */
	public void deleteFile(String filePath) throws IOException {

		try {
			Decoder decoder=Base64.getDecoder();
			String domain=new String(decoder.decode(PropertyReader.getInstance().getProperty("Domain")));
			String domainUserName=new String(decoder.decode(PropertyReader.getInstance().getProperty("DomainUserName")));
			String domainPassword=new String(decoder.decode(PropertyReader.getInstance().getProperty("DomainPassword")));


			NtlmPasswordAuthentication auth = new NtlmPasswordAuthentication(domain, domainUserName, domainPassword);

			SmbFile remoteFile = new SmbFile(filePath,auth);
			if (remoteFile.isFile() && remoteFile.exists()) {
				remoteFile.delete();
				log.info("file is removed"+ remoteFile);
			}

		} catch (Exception e) {
			log.error("Exception while deleting the file",e);
		}
	}


	/**
	 * This method update the player name in xml file
	 * @param playerName
	 * @param xmlFilePath
	 */
	public void changePlayerName(String playerName, String xmlFilePath) {
		try {
			DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
			/*documentBuilderFactory.setAttribute(XMLConstants.ACCESS_EXTERNAL_DTD, ""); 
			documentBuilderFactory.setAttribute(XMLConstants.ACCESS_EXTERNAL_SCHEMA, "");
			*/
			DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
			Document document = documentBuilder.parse(xmlFilePath);

			Node playerInfo = document.getElementsByTagName("Key").item(0);

			// update login Name
			NamedNodeMap attribute = playerInfo.getAttributes();
			Node nodeAttr = attribute.getNamedItem("loginName");
			nodeAttr.setTextContent(playerName);

			// append a new node to the first employee
			// write the DOM object to the file
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			/*transformerFactory.setAttribute(XMLConstants.ACCESS_EXTERNAL_DTD, ""); 
			transformerFactory.setAttribute(XMLConstants.ACCESS_EXTERNAL_STYLESHEET, "");*/
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource domSource = new DOMSource(document);

			StreamResult streamResult = new StreamResult(new File(xmlFilePath));
			transformer.transform(domSource, streamResult);
			log.debug("Changing the player name in xml with username created randomly");
		} catch (ParserConfigurationException pce) {
			log.error(pce.getMessage(),pce);
		} catch (TransformerException tfe) {
			log.error(tfe.getMessage(),tfe);
		} catch (IOException ioe) {
			log.error(ioe.getMessage(),ioe);
		} catch (SAXException sae) {
			log.error(sae.getMessage(),sae);
		}
	}

	/**
	 * THis method delete file having the name in wildcard format specified
	 * @param folderPath
	 * @param wildCard
	 */
	public void deleteFiles(String folderPath, String wildCard) {

		try {
			Decoder decoder=Base64.getDecoder();
			String domain=new String(decoder.decode(PropertyReader.getInstance().getProperty("Domain")));
			String domainUserName=new String(decoder.decode(PropertyReader.getInstance().getProperty("DomainUserName")));
			String domainPassword=new String(decoder.decode(PropertyReader.getInstance().getProperty("DomainPassword")));
			NtlmPasswordAuthentication auth = new NtlmPasswordAuthentication(domain, domainUserName, domainPassword);

			SmbFile remoteFolder = new SmbFile(folderPath, auth);
			if (remoteFolder.exists() && remoteFolder.isDirectory()) {
				SmbFile[] smbfiles = remoteFolder.listFiles(wildCard);
				for (SmbFile smbfile : smbfiles) {
					if (smbfile.isFile() && smbfile.exists()) {
						log.info("file is going to delete" + smbfile);
						smbfile.delete();
					}
				}
			}

		} catch (Exception e) {
			log.error("Exception while deleting the files", e);
		}
	}

	/**
	 * this method delete the list of copied files
	 * @param folderPath
	 * @param copiedFiles
	 */
	public void deleteFiles(String folderPath, List<String> copiedFiles) {
		try {

			String gameName=PropertyReader.getInstance().getProperty("GameName");
			String fileName=null;
			Decoder decoder=Base64.getDecoder();
			String domain=new String(decoder.decode(PropertyReader.getInstance().getProperty("Domain")));
			String domainUserName=new String(decoder.decode(PropertyReader.getInstance().getProperty("DomainUserName")));
			String domainPassword=new String(decoder.decode(PropertyReader.getInstance().getProperty("DomainPassword")));
			NtlmPasswordAuthentication auth = new NtlmPasswordAuthentication(domain, domainUserName, domainPassword);
			SmbFile remoteFolder = new SmbFile(folderPath, auth);

			if (remoteFolder.exists() && remoteFolder.isDirectory()) {
				SmbFile[] smbfiles = remoteFolder.listFiles();
				for (SmbFile smbfile : smbfiles) {
					fileName=smbfile.getCanonicalPath();
					if(fileName.contains(gameName))
					{
						String filetobedelete=fileName.substring(smbfile.getCanonicalPath().indexOf(gameName));	
						if (smbfile.isFile() && smbfile.exists()&& copiedFiles.contains(filetobedelete)&& copiedFiles.size()!=0) 
						{
							log.info("file is going to delete" + smbfile);
							smbfile.delete();
						}
					}
				}

			}

		} catch (Exception e) {
			log.error("Exception while deleting the files", e);
		}
	}
	

	

	/**
	 * Method will create the folder at specified path
	 * @param folderPath
	 * @return
	 */
	public boolean createFolder(String folderPath)
	{
		boolean isFolderCreated=false;
		File file = new File(folderPath);
		if (!file.isDirectory()) {
			if (file.mkdir()) {
				log.debug("Directory is created!");
				isFolderCreated= true;
			} else {
				log.debug("Failed to create directory!");
				isFolderCreated= false;
			}
		}else
		{
			log.info("Directory already exists: "+ folderPath);
			isFolderCreated=true;
		}
		return  isFolderCreated;
	}

	/**
	 * This function read the liat of supported currency list for progressive game from SupportedCurrenciesList sheet
	 * @return list 
	 */

	public     ArrayList<String>  readSupportedCurrenciesList(){
		ArrayList<String> values = new ArrayList<>();
		ExcelDataPoolManager excelpoolmanager = new ExcelDataPoolManager();
		Map<String, String> currentRow = null;
		Map<String,String> currRow=new HashMap<>();
		try {
			String testDataExcelPath=PropertyReader.getInstance().getProperty("TestData_Excel_Path");
			int totalRows = excelpoolmanager.rowCount(testDataExcelPath, Constant.CURRENCY_XL_SHEET_NAME);
			for(int count =1 ;count<totalRows;count++){
				currentRow    = excelpoolmanager.readExcelByRow(testDataExcelPath, Constant.CURRENCY_XL_SHEET_NAME,count);
				String curID = currentRow.get("ID").toString().trim().replace("\u00A0", "");
				String curISOCode = currentRow.get("ISOCode").trim().replace("\u00A0", "");
				currRow.put("ID",curID.trim());
				currRow.put("ISOCode",StringUtils.trim(curISOCode));
				values.addAll(currRow.values());
			}
		} catch (IOException e) {
			log.error(e.getMessage(),e);
		}

		return values;
	}

	

	/**
	 * Create  System time stamp
	 * @return String
	 */

	public String createTimeStampStr()  {
		Calendar mycalendar = Calendar.getInstance();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd_hhmmss");

		return formatter.format(mycalendar.getTime());
	}

	/**
	 * This method read currency list from sheet
	 * @return
	 */
	public List<Map<String,String>> readCurrList(){

		List<Map<String,String>> curList=new ArrayList<>();
		ExcelDataPoolManager excelpoolmanager = new ExcelDataPoolManager();
		Map<String, String> currentRow = null;
		try {
			String testDataExcelPath=PropertyReader.getInstance().getProperty("TestData_Excel_Path");
			int totalRows = excelpoolmanager.rowCount(testDataExcelPath, Constant.CURRENCY_XL_SHEET_NAME);
			for(int count =1 ;count<totalRows;count++){
				currentRow	= excelpoolmanager.readExcelByRow(testDataExcelPath, Constant.CURRENCY_XL_SHEET_NAME,count);
				String id = currentRow.get(Constant.ID).trim();
				String isoCode = currentRow.get(Constant.ISOCODE).trim();
				String isoName = currentRow.get(Constant.ISONAME).trim();
				String languageCurrency = currentRow.get(Constant.LANGUAGECURRENCY).trim();
				String currencyFormat=currentRow.get(Constant.DISPALYFORMAT).trim();
				String regExpression=currentRow.get(Constant.REGEXPRESSION).trim();
				String regExpressionNoSymbol=null;
				if(currentRow.get(Constant.REGEXPRESSION_NOSYMBOL)!=null) {
				 regExpressionNoSymbol=currentRow.get(Constant.REGEXPRESSION_NOSYMBOL).trim();
				}
				Map<String,String> currow=new HashMap<>();
				currow.put(Constant.ID,id);
				currow.put(Constant.ISOCODE,isoCode);
				currow.put(Constant.ISONAME,isoName);
				currow.put(Constant.LANGUAGECURRENCY,languageCurrency);
				currow.put(Constant.DISPALYFORMAT,currencyFormat);
				currow.put(Constant.REGEXPRESSION,regExpression);
				currow.put(Constant.REGEXPRESSION_NOSYMBOL,regExpressionNoSymbol);
				curList.add(currow);
			}
		} catch (IOException e) {
			log.error(e.getMessage(),e);
		}
		return curList;
	}

	
	
	 /*
	  * Method Description: this method returns the current system date
	  * Return : String
	  * */
	public   static String getCurrentDate()
	{
		//Create a Calendar Objec
		Calendar calendar=Calendar.getInstance(TimeZone.getDefault());
		int todaysDate=calendar.get(Calendar.DATE);
		return Integer.toString(todaysDate);
	}
	
	
	
	/**
	 * This method read Language Translations list from excel sheet
	 * @return
	 */
	public List<Map<String, String>> readLangTransList_poker() {

		List<Map<String, String>> langList = new ArrayList<>();
		ExcelDataPoolManager excelpoolmanager = new ExcelDataPoolManager();
		Map<String, String> currentRow = null;
		try {
			String testDataExcelPath = PropertyReader.getInstance().getProperty("TestData_Excel_Path");
			int totalRows = excelpoolmanager.rowCount(testDataExcelPath, Constant.LANG_XL_SHEET_NAME);
			for (int count = 1; count < totalRows; count++) {
				currentRow = excelpoolmanager.readExcelByRow(testDataExcelPath, Constant.LANG_XL_SHEET_NAME, count);
				String languageDescription = currentRow.get(Constant.LANGUAGE).trim();
				String languageCode = currentRow.get(Constant.LANG_CODE).trim();

				String Home = null;
				if (currentRow.get(Constant.Home) != null) {
					Home = currentRow.get(Constant.Home).trim();
				}
				String Banking = null;
				if (currentRow.get(Constant.Banking) != null) {
					Banking = currentRow.get(Constant.Banking).trim();
				}
				String Settings = null;
				if (currentRow.get(Constant.Settings) != null) {
					Settings = currentRow.get(Constant.Settings).trim();
				}
				String Sounds = null;
				if (currentRow.get(Constant.Sounds) != null) {
					Sounds = currentRow.get(Constant.Sounds).trim();
				}
				String Double = null;
				if (currentRow.get(Constant.Double) != null) {
					Double = currentRow.get(Constant.Double).trim();
				}
				String QuickDeal = null;
				if (currentRow.get(Constant.QuickDeal) != null) {
					QuickDeal = currentRow.get(Constant.QuickDeal).trim();
				}
				String PoweredByMicrogaming = null;
				if (currentRow.get(Constant.PoweredByMicrogaming) != null) {
					PoweredByMicrogaming = currentRow.get(Constant.PoweredByMicrogaming).trim();
				}
				String CoinsInPaytable = null;
				if (currentRow.get(Constant.CoinsInPaytable) != null) {
					CoinsInPaytable = currentRow.get(Constant.CoinsInPaytable).trim();
				}
				String Credits = null;
				if (currentRow.get(Constant.Credits) != null) {
					Credits = currentRow.get(Constant.Credits).trim();
				}
				String BetPlus1 = null;
				if (currentRow.get(Constant.BetPlus1) != null) {
					BetPlus1 = currentRow.get(Constant.BetPlus1).trim();
				}
				String BetMax = null;
				if (currentRow.get(Constant.BetMax) != null) {
					BetMax = currentRow.get(Constant.BetMax).trim();
				}
				String Deal = null;
				if (currentRow.get(Constant.Deal) != null) {
					Deal = currentRow.get(Constant.Deal).trim();
				}
				String Held = null;
				if (currentRow.get(Constant.Held) != null) {
					Held = currentRow.get(Constant.Held).trim();
				}
				String Draw = null;
				if (currentRow.get(Constant.Draw) != null) {
					Draw = currentRow.get(Constant.Draw).trim();
				}
				String Collect = null;
				if (currentRow.get(Constant.Collect) != null) {
					Collect = currentRow.get(Constant.Collect).trim();
				}
				String Bet = null;
				if (currentRow.get(Constant.Bet) != null) {
					Bet = currentRow.get(Constant.Bet).trim();
				}
				String TotalBet = null;
				if (currentRow.get(Constant.TotalBet) != null) {
					TotalBet = currentRow.get(Constant.TotalBet).trim();
				}
				String CoinsInBet = null;
				if (currentRow.get(Constant.CoinsInBet) != null) {
					CoinsInBet = currentRow.get(Constant.CoinsInBet).trim();
				}
				String CoinSize = null;
				if (currentRow.get(Constant.CoinSize) != null) {
					CoinSize = currentRow.get(Constant.CoinSize).trim();
				}
				String DoubleTo = null;
				if (currentRow.get(Constant.DoubleTo) != null) {
					DoubleTo = currentRow.get(Constant.DoubleTo).trim();
				}
				String PickCard = null;
				if (currentRow.get(Constant.PickCard) != null) {
					PickCard = currentRow.get(Constant.PickCard).trim();
				}
				String Congratulations = null;
				if (currentRow.get(Constant.Congratulations) != null) {
					Congratulations = currentRow.get(Constant.Congratulations).trim();
				}
				String YouWin = null;
				if (currentRow.get(Constant.YouWin) != null) {
					YouWin = currentRow.get(Constant.YouWin).trim();
				}
				String DoubleLimitReached = null;
				if (currentRow.get(Constant.DoubleLimitReached) != null) {
					DoubleLimitReached = currentRow.get(Constant.DoubleLimitReached).trim();
				}
				
				String regExpression = null;
				if (currentRow.get(Constant.REGEXPRESSION) != null) {
					regExpression = currentRow.get(Constant.REGEXPRESSION).trim();
				}
				String statistics = null;
				if (currentRow.get(Constant.Statistics) != null) {
					statistics = currentRow.get(Constant.Statistics).trim();
				}
				
				String totalWin = null;
				if (currentRow.get(Constant.TOTALWIN) != null) {
					totalWin = currentRow.get(Constant.TOTALWIN).trim();
				}
				
				String general = null;
				if (currentRow.get(Constant.General) != null) {
					general = currentRow.get(Constant.General).trim();
				}
				String handResults = null;
				if (currentRow.get(Constant.HandResults) != null) {
					handResults = currentRow.get(Constant.HandResults).trim();
				}
				String handsPlayed = null;
				if (currentRow.get(Constant.HandsPlayed) != null) {
					handsPlayed = currentRow.get(Constant.HandsPlayed).trim();
				}
				String sessionTime = null;
				if (currentRow.get(Constant.SessionTime) != null) {
					sessionTime = currentRow.get(Constant.SessionTime).trim();
				}
				String handsPerHour = null;
				if (currentRow.get(Constant.HandsPerHour) != null) {
					handsPerHour = currentRow.get(Constant.HandsPerHour).trim();
				}
				String highestWins = null;
				if (currentRow.get(Constant.HighestWins) != null) {
					highestWins = currentRow.get(Constant.HighestWins).trim();
				}
				String first = null;
				if (currentRow.get(Constant.First) != null) {
					first = currentRow.get(Constant.First).trim();
				}
				String second = null;
				if (currentRow.get(Constant.Second) != null) {
					second = currentRow.get(Constant.Second).trim();
				}
				String third = null;
				if (currentRow.get(Constant.Third) != null) {
					third = currentRow.get(Constant.Third).trim();
				}
				String statsDisclaimer = null;
				if (currentRow.get(Constant.StatsDisclaimer) != null) {
					statsDisclaimer = currentRow.get(Constant.StatsDisclaimer).trim();
				}
				String reset = null;
				if (currentRow.get(Constant.Reset) != null) {
					reset = currentRow.get(Constant.Reset).trim();
				}
				String ok = null;
				if (currentRow.get(Constant.Ok) != null) {
					ok = currentRow.get(Constant.Ok).trim();
				}
				String winSummary = null;
				if (currentRow.get(Constant.WinSummary) != null) {
					winSummary = currentRow.get(Constant.WinSummary).trim();
				}
				String handNames = null;
				if (currentRow.get(Constant.HandNames) != null) {
					handNames = currentRow.get(Constant.HandNames).trim();
				}
				String noOfHands = null;
				if (currentRow.get(Constant.NoOfHands) != null) {
					noOfHands = currentRow.get(Constant.NoOfHands).trim();
				}
				String percentage = null;
				if (currentRow.get(Constant.Percentage) != null) {
					percentage = currentRow.get(Constant.Percentage).trim();
				}
				
				
				
				Map<String, String> langrow = new HashMap<>();
				langrow.put("Language", languageDescription);
				langrow.put("Language Code", languageCode);
				langrow.put("Home", Home);
				langrow.put("Banking", Banking);
				langrow.put("Settings", Settings);
				langrow.put("Sounds", Sounds);
				langrow.put("Double", Double);
				langrow.put("QuickDeal", QuickDeal);
				langrow.put("PoweredByMicrogaming", PoweredByMicrogaming);
				langrow.put("CoinsInPaytable", CoinsInPaytable);
				langrow.put("Credits", Credits);
				langrow.put("BetPlus1", BetPlus1);
				langrow.put("BetMax", BetMax);
				langrow.put("Deal", Deal);
				langrow.put("Held", Held);
				langrow.put("Draw", Draw);
				langrow.put("Collect", Collect);
				langrow.put("Bet", Bet);
				langrow.put("TotalBet", TotalBet);
				langrow.put("CoinsInBet", CoinsInBet);
				langrow.put("CoinSize", CoinSize);
				langrow.put("DoubleTo", DoubleTo);
				langrow.put("PickCard", PickCard);
				langrow.put("Congratulations", Congratulations);
				langrow.put("YouWin", YouWin);
				langrow.put("DoubleLimitReached", DoubleLimitReached);
				langrow.put("Statistics", statistics);
				langrow.put("RegExpression", regExpression);
				langrow.put("General", general);
				langrow.put("HandResults", handResults);
				langrow.put("HandsPlayed", handsPlayed);
				langrow.put("SessionTime", sessionTime);
				langrow.put("HandsPerHour", handsPerHour);
				langrow.put("HighestWins", highestWins);
				langrow.put("First", first);
				langrow.put("Second", second);
				langrow.put("Third", third);
				langrow.put("StatsDisclaimer", statsDisclaimer);
				langrow.put("Reset", reset);
				langrow.put("Ok", ok);
				langrow.put("WinSummary", winSummary);
				langrow.put("HandNames", handNames);
				langrow.put("NoOfHands", noOfHands);
				langrow.put("Percentage", percentage);
				langrow.put("TOTALWIN", totalWin);
				langList.add(langrow);
			}
		} catch (IOException e) {
			log.error(e.getMessage(), e);
		}
		return langList;
	}






/**
 * This method read paytable Language Translations list from excel sheet by HT67091
 * @return
 */
public List<Map<String, String>> readPaytableTransList_poker() {

	List<Map<String, String>> paytableList = new ArrayList<>();
	ExcelDataPoolManager excelpoolmanager = new ExcelDataPoolManager();
	Map<String, String> currentRow = null;
	try {
		String testDataExcelPath = PropertyReader.getInstance().getProperty("TestData_Excel_Path");
		int totalRows = excelpoolmanager.rowCount(testDataExcelPath, Constant.PAYTABLE_XL_SHEET_NAME);
		for (int count = 1; count < totalRows; count++) {
			currentRow = excelpoolmanager.readExcelByRow(testDataExcelPath, Constant.PAYTABLE_XL_SHEET_NAME, count);
			String languageDescription = currentRow.get(Constant.LANGUAGE).trim();
			String languageCode = currentRow.get(Constant.LANG_CODE).trim();
				

			String PayOut1 = null;
			if (currentRow.get(Constant.PayOut1) != null) {
				PayOut1 = currentRow.get(Constant.PayOut1).trim();
			}
			String PayOut2 = null;
			if (currentRow.get(Constant.PayOut2) != null) {
				PayOut2 = currentRow.get(Constant.PayOut2).trim();
			}
			String PayOut3 = null;
			if (currentRow.get(Constant.PayOut3) != null) {
				PayOut3 = currentRow.get(Constant.PayOut3).trim();
			}
			String PayOut4 = null;
			if (currentRow.get(Constant.PayOut4) != null) {
				PayOut4 = currentRow.get(Constant.PayOut4).trim();
			}
			String PayOut5 = null;
			if (currentRow.get(Constant.PayOut5) != null) {
				PayOut5 = currentRow.get(Constant.PayOut5).trim();
			}
			String PayOut6 = null;
			if (currentRow.get(Constant.PayOut6) != null) {
				PayOut6 = currentRow.get(Constant.PayOut6).trim();
			}
			String PayOut7 = null;
			if (currentRow.get(Constant.PayOut7) != null) {
				PayOut7 = currentRow.get(Constant.PayOut7).trim();
			}
			
			//			
			String PayOut8 = null;
			if (currentRow.get(Constant.PayOut8) != null) {
				PayOut8 = currentRow.get(Constant.PayOut8).trim();
			}
			String PayOut9 = null;
			if (currentRow.get(Constant.PayOut9) != null) {
				PayOut9 = currentRow.get(Constant.PayOut9).trim();
			}
			String PayOut10 = null;
			if (currentRow.get(Constant.PayOut10) != null) {
				PayOut10 = currentRow.get(Constant.PayOut10).trim();
			}
			String PayOut11 = null;
			if (currentRow.get(Constant.PayOut11) != null) {
				PayOut11 = currentRow.get(Constant.PayOut11).trim();
			}
			
			
			Map<String, String> paytablerow = new HashMap<>();
			paytablerow.put("PayOut1", PayOut1);
			paytablerow.put("PayOut2", PayOut2);
			paytablerow.put("PayOut3", PayOut3);
			paytablerow.put("PayOut4", PayOut4);
			paytablerow.put("PayOut5", PayOut5);
			paytablerow.put("PayOut6", PayOut6);
			paytablerow.put("PayOut7", PayOut7);
			paytablerow.put("PayOut8", PayOut8);
			paytablerow.put("PayOut9", PayOut9);
			paytablerow.put("PayOut10", PayOut10);
			paytablerow.put("PayOut11", PayOut11);
						
			paytableList.add(paytablerow);
		
				}
	} catch (IOException e) {
		log.error(e.getMessage(), e);
	}
	
	return paytableList;
}


/*
* This method added by Harsha read play.next Language Translations list from excel sheet
* @return
*/


public List<Map<String, String>> readPlayNext_LangTransList() {

	List<Map<String, String>> LangList = new ArrayList<>();
	ExcelDataPoolManager excelpoolmanager = new ExcelDataPoolManager();
	Map<String, String> currentRow = null;
	try {
		String testDataExcelPath = PropertyReader.getInstance().getProperty("TestData_Excel_Path");
		int totalRows = excelpoolmanager.rowCount(testDataExcelPath, Constant.LANG_XL_SHEET_NAME);
		for (int count = 1; count < totalRows; count++) {
			currentRow = excelpoolmanager.readExcelByRow(testDataExcelPath, Constant.LANG_XL_SHEET_NAME, count);
			String Language = currentRow.get(Constant.Language).trim();
			String languageCode = currentRow.get(Constant.LanguageCode).trim();
				

			String Credits = null;
			if (currentRow.get(Constant.Credits) != null) {
				Credits = currentRow.get(Constant.Credits).trim();
			}
			String Bet1 = null;
			if (currentRow.get(Constant.Bet1) != null) {
				Bet1 = currentRow.get(Constant.Bet1).trim();
			}
			String TotalBet1 = null;
			if (currentRow.get(Constant.TotalBet1) != null) {
				TotalBet1 = currentRow.get(Constant.TotalBet1).trim();
			}
			String Banking1 = null;
			if (currentRow.get(Constant.Banking1) != null) {
				Banking1 = currentRow.get(Constant.Banking1).trim();
			}
			String Lobby = null;
			if (currentRow.get(Constant.Lobby) != null) {
				Lobby = currentRow.get(Constant.Lobby).trim();
			}
			String Settings1 = null;
			if (currentRow.get(Constant.Settings1) != null) {
				Settings1 = currentRow.get(Constant.Settings1).trim();
			}
																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																											
			String newPay = null;
			if (currentRow.get(Constant.newPay) != null) {
				newPay = currentRow.get(Constant.newPay).trim();
			}
					
			String CoinSize1= null;
			if (currentRow.get(Constant.CoinSize1) != null) {
				CoinSize1 = currentRow.get(Constant.CoinSize1).trim();
			}
			
			String Sounds1 = null;
			if (currentRow.get(Constant.Sounds1) != null) {
				Sounds1 = currentRow.get(Constant.Sounds1).trim();
			}
			
			String QuickDeal1 = null;
			if (currentRow.get(Constant.QuickDeal1) != null) {
				QuickDeal1 = currentRow.get(Constant.QuickDeal1).trim();
			}
			
			String Spin = null;
			if (currentRow.get(Constant.Spin) != null) {
				Spin = currentRow.get(Constant.Spin).trim();
			}
			
			String newbigwin = null;
			if (currentRow.get(Constant.newbigwin) != null) {
				newbigwin = currentRow.get(Constant.newbigwin).trim();
			}
			
			String AMAZING = null;
			if (currentRow.get(Constant.AMAZING) != null) {
				AMAZING = currentRow.get(Constant.AMAZING).trim();
			}
			
			String TOTALWIN = null;
			if (currentRow.get(Constant.TOTALWIN) != null) {
				TOTALWIN = currentRow.get(Constant.TOTALWIN).trim();
			}
			
			String FREESPINS = null;
			if (currentRow.get(Constant.FREESPINS) != null) {
				FREESPINS = currentRow.get(Constant.FREESPINS).trim();
			}
			
			
			String Help = null;
			if (currentRow.get(Constant.Help) != null) {
				Help = currentRow.get(Constant.Help).trim();
			}
			
			
			String BaseMaxbet = null;
			if (currentRow.get(Constant.BaseMaxbet) != null) {
				BaseMaxbet = currentRow.get(Constant.BaseMaxbet).trim();
			}
			
			String QuickBet = null;
			if (currentRow.get(Constant.QuickBet) != null) {
				QuickBet = currentRow.get(Constant.QuickBet).trim();
			}
			
			String QuickSpin = null;
			if (currentRow.get(Constant.QuickSpin) != null) {
				QuickSpin = currentRow.get(Constant.QuickSpin).trim();
			}
			
			
			
			String Autoplay = null;
			if (currentRow.get(Constant.Autoplay) != null) {
				Autoplay = currentRow.get(Constant.Autoplay).trim();
			}
			
			String BaseNewCoinsPerLine = null;
			if (currentRow.get(Constant.BaseNewCoinsPerLine) != null) {
				BaseNewCoinsPerLine = currentRow.get(Constant.BaseNewCoinsPerLine).trim();
			}
			
			String YouHaveWon = null;
			if (currentRow.get(Constant.YouHaveWon) != null) {
				YouHaveWon = currentRow.get(Constant.YouHaveWon).trim();
			}
			
			String FreeGames = null;
			if (currentRow.get(Constant.FreeGames) != null) {
				FreeGames = currentRow.get(Constant.FreeGames).trim();
			}
			
			String FreeSpins = null;
			if (currentRow.get(Constant.FreeSpins) != null) {
				FreeSpins = currentRow.get(Constant.FreeSpins).trim();
			}
			
			String Scatter = null;
			if (currentRow.get(Constant.Scatter) != null) {
				Scatter = currentRow.get(Constant.Scatter).trim();
			}
			
			
			//Added on  16th Jan 2023 by HT67091
			String Freegames = null;
			if (currentRow.get(Constant.Freegames) != null) {
				Freegames = currentRow.get(Constant.Freegames).trim();
			}
			
			String PlayNow = null;
			if (currentRow.get(Constant.PlayNow) != null) {
				PlayNow = currentRow.get(Constant.PlayNow).trim();
			}
			
			String PlayLater = null;
			if (currentRow.get(Constant.PlayLater) != null) {
				PlayLater = currentRow.get(Constant.PlayLater).trim();
			}
			
			String KeepIt = null;
			if (currentRow.get(Constant.KeepIt) != null) {
				KeepIt = currentRow.get(Constant.KeepIt).trim();
			}
			
			String DiscardOffer = null;
			if (currentRow.get(Constant.DiscardOffer) != null) {
				DiscardOffer = currentRow.get(Constant.DiscardOffer).trim();
			}
			
			String Discard = null;
			if (currentRow.get(Constant.Discard) != null) {
				Discard = currentRow.get(Constant.Discard).trim();
			}
			
			String FGCongratulations = null;
			if (currentRow.get(Constant.FGCongratulations) != null) {
				FGCongratulations = currentRow.get(Constant.FGCongratulations).trim();
			}
			
		
			if (currentRow.get(Constant.KeepIt) != null) {
				FGCongratulations = currentRow.get(Constant.KeepIt).trim();
			}
			
			if (currentRow.get(Constant.Lobby) != null) {
				FGCongratulations = currentRow.get(Constant.KeepIt).trim();
			}
			
			
			
			Map<String, String> langrow = new HashMap<>();
			langrow.put("LanguageCode", languageCode);
			langrow.put("Language", Language);
			langrow.put("Credits", Credits);
			langrow.put("Bet1", Bet1);
			langrow.put("TotalBet1", TotalBet1);
			langrow.put("Banking1", Banking1);
			langrow.put("Settings1", Settings1);
			langrow.put("newPay", newPay);
			langrow.put("CoinSize1", CoinSize1);
			langrow.put("BaseNewCoinsPerLine", BaseNewCoinsPerLine);//updated on 19th Jan 2023 HT67091
			langrow.put("Sounds1", Sounds1);
			langrow.put("QuickDeal1", QuickDeal1);
			langrow.put("Spin", Spin);
			langrow.put("newbigwin", newbigwin);
			langrow.put("AMAZING", AMAZING);
			langrow.put("TOTALWIN", TOTALWIN);
			langrow.put("FREESPINS", FREESPINS);
			langrow.put("Help", Help);
			
			langrow.put("BaseMaxbet", BaseMaxbet);//updated on 19th Jan 2023 HT67091
			langrow.put("QuickBet", QuickBet);
			langrow.put("QuickSpin", QuickSpin);
			langrow.put("Autoplay", Autoplay);
			langrow.put("YouHaveWon", YouHaveWon);
			langrow.put("FreeGames", FreeGames);
			langrow.put("FreeSpins", FreeSpins);
			langrow.put("Scatter", Scatter);
			langrow.put("Lobby", Lobby);
			
			
			
			
			langrow.put("Freegames", Freegames);
			langrow.put("PlayNow", PlayNow);
			langrow.put("PlayLater", PlayLater);
			langrow.put("KeepIt", KeepIt);
			langrow.put("DiscardOffer", DiscardOffer);
			langrow.put("Discard", Discard);
			langrow.put("FGCongratulations", FGCongratulations);//updated on 19th Jan 2023 HT67091
			
			LangList.add(langrow);
		
				}
	} catch (IOException e) {
		log.error(e.getMessage(), e);
	}
	
	return LangList;
}


/**
 * This method read Language Translations list from excel sheet
 * @return
 * @author rk61073
 */
public List<Map<String, String>> readLangTransList() 
{
	List<Map<String, String>> langList = new ArrayList<>();
	ExcelDataPoolManager excelpoolmanager = new ExcelDataPoolManager();
	Map<String, String> currentRow = null;
	try {
		String testDataExcelPath = PropertyReader.getInstance().getProperty("TestData_Excel_Path");
		int totalRows = excelpoolmanager.rowCount(testDataExcelPath, Constant.LANG_STRINGS_XL_SHEET_NAME);
		for (int count = 1; count < totalRows; count++) {
			currentRow = excelpoolmanager.readExcelByRow(testDataExcelPath, Constant.LANG_STRINGS_XL_SHEET_NAME, count);
			String languageDescription = currentRow.get(Constant.LANGUAGE).trim();
			String languageCode = currentRow.get(Constant.LANG_CODE).trim();

			String Home = null;
			if (currentRow.get(Constant.Home) != null) 
			{
				Home = currentRow.get(Constant.Home).trim();
			}
			String Banking = null;
			if (currentRow.get(Constant.Banking) != null)
			{
				Banking = currentRow.get(Constant.Banking).trim();
			}
			String Settings = null;
			if (currentRow.get(Constant.Settings) != null) 
			{
				Settings = currentRow.get(Constant.Settings).trim();
			}
			String Sounds = null;
			if (currentRow.get(Constant.Sounds) != null)
			{
				Sounds = currentRow.get(Constant.Sounds).trim();
			}
			String Double = null;
			if (currentRow.get(Constant.Double) != null) 
			{
				Double = currentRow.get(Constant.Double).trim();
			}
			String QuickDeal = null;
			if (currentRow.get(Constant.QuickDeal) != null) 
			{
				QuickDeal = currentRow.get(Constant.QuickDeal).trim();
			}
			String PoweredByMicrogaming = null;
			if (currentRow.get(Constant.PoweredByMicrogaming) != null)
			{
				PoweredByMicrogaming = currentRow.get(Constant.PoweredByMicrogaming).trim();
			}
			String AGamesGlobalStudio = null;
			if (currentRow.get(Constant.AGamesGlobalStudio) != null) 
			{
				AGamesGlobalStudio = currentRow.get(Constant.AGamesGlobalStudio).trim();
			}
			String CoinsInPaytable = null;
			if (currentRow.get(Constant.CoinsInPaytable) != null)
			{
				CoinsInPaytable = currentRow.get(Constant.CoinsInPaytable).trim();
			}
			String Credits = null;
			if (currentRow.get(Constant.Credits) != null) 
			{
				Credits = currentRow.get(Constant.Credits).trim();
			}
			String BetPlus1 = null;
			if (currentRow.get(Constant.BetPlus1) != null) 
			{
				BetPlus1 = currentRow.get(Constant.BetPlus1).trim();
			}
			String BetMax = null;
			if (currentRow.get(Constant.BetMax) != null) 
			{
				BetMax = currentRow.get(Constant.BetMax).trim();
			}
			String Deal = null;
			if (currentRow.get(Constant.Deal) != null) 
			{
				Deal = currentRow.get(Constant.Deal).trim();
			}
			String Held = null;
			if (currentRow.get(Constant.Held) != null) 
			{
				Held = currentRow.get(Constant.Held).trim();
			}
			String Draw = null;
			if (currentRow.get(Constant.Draw) != null)
			{
				Draw = currentRow.get(Constant.Draw).trim();
			}
			String Collect = null;
			if (currentRow.get(Constant.Collect) != null) 
			{
				Collect = currentRow.get(Constant.Collect).trim();
			}
			String Bet = null;
			if (currentRow.get(Constant.Bet) != null) 
			{
				Bet = currentRow.get(Constant.Bet).trim();
			}
			String TotalBet = null;
			if (currentRow.get(Constant.TotalBet) != null)
			{
				TotalBet = currentRow.get(Constant.TotalBet).trim();
			}
			String CoinsInBet = null;
			if (currentRow.get(Constant.CoinsInBet) != null) 
			{
				CoinsInBet = currentRow.get(Constant.CoinsInBet).trim();
			}
			String CoinSize = null;
			if (currentRow.get(Constant.CoinSize) != null)
			{
				CoinSize = currentRow.get(Constant.CoinSize).trim();
			}
			String DoubleTo = null;
			if (currentRow.get(Constant.DoubleTo) != null)
			{
				DoubleTo = currentRow.get(Constant.DoubleTo).trim();
			}
			String PickCard = null;
			if (currentRow.get(Constant.PickCard) != null)
			{
				PickCard = currentRow.get(Constant.PickCard).trim();
			}
			String Congratulations = null;
			if (currentRow.get(Constant.Congratulations) != null) 
			{
				Congratulations = currentRow.get(Constant.Congratulations).trim();
			}
			String YouWin = null;
			if (currentRow.get(Constant.YouWin) != null) 
			{
				YouWin = currentRow.get(Constant.YouWin).trim();
			}
			String DoubleLimitReached = null;
			if (currentRow.get(Constant.DoubleLimitReached) != null)
			{
				DoubleLimitReached = currentRow.get(Constant.DoubleLimitReached).trim();
			}
			
			String regExpression = null;
			if (currentRow.get(Constant.REGEXPRESSION) != null) 
			{
				regExpression = currentRow.get(Constant.REGEXPRESSION).trim();
			}

			String totalWin = null;
			if (currentRow.get(Constant.TotalWin) != null) 
			{
				totalWin = currentRow.get(Constant.TotalWin).trim();
			}
			
			String statistics = null;
			if (currentRow.get(Constant.Statistics) != null) 
			{
				statistics = currentRow.get(Constant.Statistics).trim();
			}
			String general = null;
			if (currentRow.get(Constant.General) != null) 
			{
				general = currentRow.get(Constant.General).trim();
			}
			String handResults = null;
			if (currentRow.get(Constant.HandResults) != null) 
			{
				handResults = currentRow.get(Constant.HandResults).trim();
			}
			String handsPlayed = null;
			if (currentRow.get(Constant.HandsPlayed) != null)
			{
				handsPlayed = currentRow.get(Constant.HandsPlayed).trim();
			}
			String sessionTime = null;
			if (currentRow.get(Constant.SessionTime) != null) 
			{
				sessionTime = currentRow.get(Constant.SessionTime).trim();
			}
			String handsPerHour = null;
			if (currentRow.get(Constant.HandsPerHour) != null)
			{
				handsPerHour = currentRow.get(Constant.HandsPerHour).trim();
			}
			String highestWins = null;
			if (currentRow.get(Constant.HighestWins) != null)
			{
				highestWins = currentRow.get(Constant.HighestWins).trim();
			}
			String first = null;
			if (currentRow.get(Constant.First) != null) 
			{
				first = currentRow.get(Constant.First).trim();
			}
			String second = null;
			if (currentRow.get(Constant.Second) != null) 
			{
				second = currentRow.get(Constant.Second).trim();
			}
			String third = null;
			if (currentRow.get(Constant.Third) != null) 
			{
				third = currentRow.get(Constant.Third).trim();
			}
			String statsDisclaimer = null;
			if (currentRow.get(Constant.StatsDisclaimer) != null) 
			{
				statsDisclaimer = currentRow.get(Constant.StatsDisclaimer).trim();
			}
			String reset = null;
			if (currentRow.get(Constant.Reset) != null)
			{
				reset = currentRow.get(Constant.Reset).trim();
			}
			String ok = null;
			if (currentRow.get(Constant.Ok) != null) 
			{
				ok = currentRow.get(Constant.Ok).trim();
			}
			String winSummary = null;
			if (currentRow.get(Constant.WinSummary) != null) 
			{
				winSummary = currentRow.get(Constant.WinSummary).trim();
			}
			String handNames = null;
			if (currentRow.get(Constant.HandNames) != null) 
			{
				handNames = currentRow.get(Constant.HandNames).trim();
			}
			String noOfHands = null;
			if (currentRow.get(Constant.NoOfHands) != null) 
			{
				noOfHands = currentRow.get(Constant.NoOfHands).trim();
			}
			String percentage = null;
			if (currentRow.get(Constant.Percentage) != null)
			{
				percentage = currentRow.get(Constant.Percentage).trim();
			}
			
			
			Map<String, String> langrow = new HashMap<>();
			langrow.put("Language", languageDescription);
			langrow.put("Language Code", languageCode);
			langrow.put("Home", Home);
			langrow.put("Banking", Banking);
			langrow.put("Settings", Settings);
			langrow.put("Sounds", Sounds);
			langrow.put("Double", Double);
			langrow.put("QuickDeal", QuickDeal);
			langrow.put("PoweredByMicrogaming", PoweredByMicrogaming);
			langrow.put("AGamesGlobalStudio", AGamesGlobalStudio);
			langrow.put("CoinsInPaytable", CoinsInPaytable);
			langrow.put("Credits", Credits);
			langrow.put("BetPlus1", BetPlus1);
			langrow.put("BetMax", BetMax);
			langrow.put("Deal", Deal);
			langrow.put("Held", Held);
			langrow.put("Draw", Draw);
			langrow.put("Collect", Collect);
			langrow.put("Bet", Bet);
			langrow.put("TotalBet", TotalBet);
			langrow.put("CoinsInBet", CoinsInBet);
			langrow.put("CoinSize", CoinSize);
			langrow.put("DoubleTo", DoubleTo);
			langrow.put("PickCard", PickCard);
			langrow.put("Congratulations", Congratulations);
			langrow.put("YouWin", YouWin);
			langrow.put("DoubleLimitReached", DoubleLimitReached);
			langrow.put("TotalWin", totalWin);
			langrow.put("Statistics", statistics);
			langrow.put("RegExpression", regExpression);
			langrow.put("General", general);
			langrow.put("HandResults", handResults);
			langrow.put("HandsPlayed", handsPlayed);
			langrow.put("SessionTime", sessionTime);
			langrow.put("HandsPerHour", handsPerHour);
			langrow.put("HighestWins", highestWins);
			langrow.put("First", first);
			langrow.put("Second", second);
			langrow.put("Third", third);
			langrow.put("StatsDisclaimer", statsDisclaimer);
			langrow.put("Reset", reset);
			langrow.put("Ok", ok);
			langrow.put("WinSummary", winSummary);
			langrow.put("HandNames", handNames);
			langrow.put("NoOfHands", noOfHands);
			langrow.put("Percentage", percentage);
			langList.add(langrow);
		}
	} catch (IOException e) {
		log.error(e.getMessage(), e);
	}
	return langList;
}
/**
 * This method read paytable payouts list from excel sheet
 * @return
 * @author rk61073
 */
public List<Map<String, String>> readPaytableTransList() {

	List<Map<String, String>> langList = new ArrayList<>();
	ExcelDataPoolManager excelpoolmanager = new ExcelDataPoolManager();
	Map<String, String> currentRow = null;
	try {
		String testDataExcelPath = PropertyReader.getInstance().getProperty("TestData_Excel_Path");
		int totalRows = excelpoolmanager.rowCount(testDataExcelPath, Constant.PAYTABLE_XL_SHEET_NAME);
		for (int count = 1; count < totalRows; count++) {
			currentRow = excelpoolmanager.readExcelByRow(testDataExcelPath, Constant.PAYTABLE_XL_SHEET_NAME, count);
			String languageDescription = currentRow.get(Constant.LANGUAGE).trim();
			String languageCode = currentRow.get(Constant.LANG_CODE).trim();

			
			String PayOut1 = null;
			if (currentRow.get(Constant.PAYOUT1) != null) {
				PayOut1 = currentRow.get(Constant.PAYOUT1).trim();
			}
			String PayOut2 = null;
			if (currentRow.get(Constant.PAYOUT2) != null) {
				PayOut2 = currentRow.get(Constant.PAYOUT2).trim();
			}
			String PayOut3 = null;
			if (currentRow.get(Constant.PAYOUT3) != null) {
				PayOut3 = currentRow.get(Constant.PAYOUT3).trim();
			}
			String PayOut4 = null;
			if (currentRow.get(Constant.PAYOUT4) != null) {
				PayOut4 = currentRow.get(Constant.PAYOUT4).trim();
			}
			String PayOut5 = null;
			if (currentRow.get(Constant.PAYOUT5) != null) {
				PayOut5 = currentRow.get(Constant.PAYOUT5).trim();
			}
			String PayOut6 = null;
			if (currentRow.get(Constant.PAYOUT6) != null) {
				PayOut6 = currentRow.get(Constant.PAYOUT6).trim();
			}
			String PayOut7 = null;
			if (currentRow.get(Constant.PAYOUT7) != null) {
				PayOut7 = currentRow.get(Constant.PAYOUT7).trim();
			}
			String PayOut8 = null;
			if (currentRow.get(Constant.PAYOUT8) != null) {
				PayOut8 = currentRow.get(Constant.PAYOUT8).trim();
			}
			String PayOut9 = null;
			if (currentRow.get(Constant.PAYOUT9) != null) {
				PayOut9 = currentRow.get(Constant.PAYOUT9).trim();
			}
			String PayOut10 = null;
			if (currentRow.get(Constant.PAYOUT10) != null) {
				PayOut10 = currentRow.get(Constant.PAYOUT10).trim();
			}
			String PayOut11 = null;
			if (currentRow.get(Constant.PAYOUT11) != null) {
				PayOut11 = currentRow.get(Constant.PAYOUT11).trim();
			}
			String PayOut12 = null;
			if (currentRow.get(Constant.PAYOUT12) != null) {
				PayOut12 = currentRow.get(Constant.PAYOUT12).trim();
			}
			String PayOut13 = null;
			if (currentRow.get(Constant.PAYOUT13) != null) {
				PayOut13 = currentRow.get(Constant.PAYOUT13).trim();
			}
			
			Map<String, String> langrow = new HashMap<>();
			langrow.put("Language", languageDescription);
			langrow.put("Language Code", languageCode);
			langrow.put("PayOut1", PayOut1);
			langrow.put("PayOut2", PayOut2);
			langrow.put("PayOut3", PayOut3);
			langrow.put("PayOut4", PayOut4);
			langrow.put("PayOut5", PayOut5);
			langrow.put("PayOut6", PayOut6);
			langrow.put("PayOut7", PayOut7);
			langrow.put("PayOut8", PayOut8);
			langrow.put("PayOut9", PayOut9);
			langrow.put("PayOut10", PayOut10);
			langrow.put("PayOut11", PayOut11);
			langrow.put("PayOut12", PayOut12);
			langrow.put("PayOut13", PayOut13);
			langList.add(langrow);
		}
	} catch (IOException e) {
		log.error(e.getMessage(), e);
	}
	return langList;
}
/**
 * This method read market details with respective to the sheet 
 * @return
 * @author rk61073
 */
public List<Map> readMarketLists(String marketXLSheetName)
{
	List<Map> langListForMarket=new ArrayList<>();
	ExcelDataPoolManager excelpoolmanager = new ExcelDataPoolManager();
	Map<String, String> currentRow = null;
	try {
		String testDataExcelPath=PropertyReader.getInstance().getProperty("TestData_Excel_Path");
		int totalRows = excelpoolmanager.rowCount(testDataExcelPath, marketXLSheetName);
		for(int count =1 ;count<totalRows;count++) 
		{
			currentRow	= excelpoolmanager.readExcelByRow(testDataExcelPath,marketXLSheetName,count);
			
			String languageDescription = currentRow.get(Constant.LANGUAGE).trim();
			String languageCode = currentRow.get(Constant.LANG_CODE).trim();
			String regMarketName =currentRow.get(Constant.REG_MARKET_NAME).trim();
			String productId =currentRow.get(Constant.PRODUCT_ID).trim();
			String productId_Mobile = null;
			if(currentRow.get(Constant.PRODUCT_ID_MOBILE)!=null)
			{
		      productId_Mobile =currentRow.get(Constant.PRODUCT_ID_MOBILE).trim();
			}
			String currencyIsoCode =currentRow.get(Constant.CURRENCY_ISOCODE).trim();
			String marketTypeID =currentRow.get(Constant.MARKET_TYPEID).trim();
			String balance =currentRow.get(Constant.BALANCE).trim();
			String regExpression=null;
			if(currentRow.get(Constant.REGEXPRESSION)!=null) 
			{
			  regExpression=currentRow.get(Constant.REGEXPRESSION).trim();
			}
			String regExpressionNoSymbol=null;
			if(currentRow.get(Constant.REGEXPRESSION_NOSYMBOL)!=null) 
			{
			 regExpressionNoSymbol=currentRow.get(Constant.REGEXPRESSION_NOSYMBOL).trim();
			}
			
			Map<String,String> currow=new HashMap<>();
			currow.put(Constant.LANGUAGE, languageDescription);
			currow.put(Constant.LANG_CODE, languageCode);
			currow.put(Constant.REG_MARKET_NAME, regMarketName);
			currow.put(Constant.PRODUCT_ID, productId);
			currow.put(Constant.PRODUCT_ID_MOBILE, productId_Mobile);
			currow.put(Constant.CURRENCY_ISOCODE, currencyIsoCode);
			currow.put(Constant.MARKET_TYPEID, marketTypeID);
			currow.put(Constant.BALANCE, balance);
			currow.put(Constant.REGEXPRESSION, regExpression);
			currow.put(Constant.REGEXPRESSION_NOSYMBOL, regExpressionNoSymbol);
			langListForMarket.add(currow);
		}
	} catch (IOException e) {
		log.error(e.getMessage(),e);
	}
	return langListForMarket;
}

/**
 * This method read market details with respective to the sheet 
 * @return
 * @author rk61073
 */
public List<Map> readMarketList(String flag)
{
	List<Map> langListForMarket=new ArrayList<>();
	ExcelDataPoolManager excelpoolmanager = new ExcelDataPoolManager();
	Map<String, String> currentRow = null;
	try {
		String testDataExcelPath=PropertyReader.getInstance().getProperty("TestData_Excel_Path");
		int totalRows = excelpoolmanager.rowCount(testDataExcelPath, Constant.MARKET_XL_SHEET_NAME);
		for(int count =1 ;count<totalRows;count++)
		{
			currentRow	= excelpoolmanager.readExcelByRow(testDataExcelPath,Constant.MARKET_XL_SHEET_NAME,count);
			String languageDescription = currentRow.get(Constant.LANGUAGE).trim();
			String languageCode = currentRow.get(Constant.LANG_CODE).trim();
			String regMarketName =currentRow.get(Constant.REG_MARKET_NAME).trim();
			String productId =currentRow.get(Constant.PRODUCT_ID).trim();
			String productId_Mobile = null;
			if(currentRow.get(Constant.PRODUCT_ID_MOBILE)!=null)
			{
		      productId_Mobile =currentRow.get(Constant.PRODUCT_ID_MOBILE).trim();
			}
			String currencyIsoCode =currentRow.get(Constant.CURRENCY_ISOCODE).trim();
			String marketTypeID =currentRow.get(Constant.MARKET_TYPEID).trim();
			String balance =currentRow.get(Constant.BALANCE).trim();
			String regExpression=null;
			if(currentRow.get(Constant.REGEXPRESSION)!=null) 
			{
			  regExpression=currentRow.get(Constant.REGEXPRESSION).trim();
			}
			String regExpressionNoSymbol=null;
			if(currentRow.get(Constant.REGEXPRESSION_NOSYMBOL)!=null) 
			{
			 regExpressionNoSymbol=currentRow.get(Constant.REGEXPRESSION_NOSYMBOL).trim();
			}
			String runFlag =currentRow.get(Constant.RUNFLAG).trim();
			
			Map<String,String> currow=new HashMap<>();
			currow.put(Constant.LANGUAGE, languageDescription);
			currow.put(Constant.LANG_CODE, languageCode);
			currow.put(Constant.REG_MARKET_NAME, regMarketName);
			currow.put(Constant.PRODUCT_ID, productId);
			currow.put(Constant.PRODUCT_ID_MOBILE, productId_Mobile);
			currow.put(Constant.CURRENCY_ISOCODE, currencyIsoCode);
			currow.put(Constant.MARKET_TYPEID, marketTypeID);
			currow.put(Constant.BALANCE, balance);
			currow.put(Constant.REGEXPRESSION, regExpression);
			currow.put(Constant.REGEXPRESSION_NOSYMBOL, regExpressionNoSymbol);
			currow.put(Constant.RUNFLAG, runFlag);
			if(runFlag.equalsIgnoreCase(flag))
			{
				langListForMarket.add(currow);
			}
		}
	} catch (IOException e) {
		log.error(e.getMessage(),e);
	}
	return langListForMarket;
}






}





