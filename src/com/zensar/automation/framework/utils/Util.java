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
	 * This method read currency list from sheet @HT67091 -Added by Harsha Toshniwal on 11 th Nov 2022
	 * @return
	 */
	public List<Map> readLanguageList(){

		List<Map> curList=new ArrayList<>();
		ExcelDataPoolManager excelpoolmanager = new ExcelDataPoolManager();
		Map<String, String> currentRow = null;
		try {
			String testDataExcelPath=PropertyReader.getInstance().getProperty("TestData_Excel_Path");
			int totalRows = excelpoolmanager.rowCount(testDataExcelPath, Constant.LANG_XL_SHEET_NAME);
			for(int count =0 ;count<totalRows;count++){
				currentRow	= excelpoolmanager.readExcelByRow(testDataExcelPath, Constant.LANG_XL_SHEET_NAME,count);
				String LanguageCode = currentRow.get(Constant.LanguageCode).trim();
				String Language = currentRow.get(Constant.Language).trim();
				String Home = currentRow.get(Constant.Home).trim();
				String Banking = currentRow.get(Constant.Banking).trim();
				String Settings = currentRow.get(Constant.Settings).trim();
				String Sounds = currentRow.get(Constant.Sounds).trim();
				String Double = currentRow.get(Constant.Double).trim();
				String QuickDeal = currentRow.get(Constant.QuickDeal).trim();
				String PoweredByMicrogaming = currentRow.get(Constant.PoweredByMicrogaming).trim();
				String CoinsInPaytable = currentRow.get(Constant.CoinsInPaytable).trim();
				String Credits = currentRow.get(Constant.Credits).trim();
				String BETPlus1 = currentRow.get(Constant.BETPlus1).trim();
				String BetMax = currentRow.get(Constant.BetMax).trim();
				String Deal = currentRow.get(Constant.Deal).trim();
				String Held = currentRow.get(Constant.Held).trim();
				String Draw = currentRow.get(Constant.Draw).trim();
				String Collect = currentRow.get(Constant.Collect).trim();
				String Bet = currentRow.get(Constant.Bet).trim();
				String TotalBet = currentRow.get(Constant.TotalBet).trim();
				String CoinsInBet = currentRow.get(Constant.CoinsInBet).trim();
				String CoinSize = currentRow.get(Constant.CoinSize).trim();
				String DoubleTo = currentRow.get(Constant.DoubleTo).trim();
				String PickCard = currentRow.get(Constant.PickCard).trim();
				String Congratulations = currentRow.get(Constant.Congratulations).trim();
				String YouWin = currentRow.get(Constant.YouWin).trim();
				String DoubleLimitReached = currentRow.get(Constant.DoubleLimitReached).trim();
				
				Map<String,String> currow=new HashMap<>();
				currow.put("LanguageCode",LanguageCode);
				currow.put("Home",Home);
				currow.put("Banking",Banking);
				currow.put("Settings",Settings);
				currow.put("Sounds",Sounds);
				currow.put("Double",Double);
				currow.put("QuickDeal",QuickDeal);
				currow.put("PoweredByMicrogaming",PoweredByMicrogaming);
				currow.put("CoinsInPaytable",CoinsInPaytable);
				currow.put("Credits",Credits);
				currow.put("BETPlus1",BETPlus1);
				currow.put("BetMax",BetMax);
				currow.put("Deal",Deal);
				currow.put("Held",Held);
				currow.put("Draw",Draw);
				currow.put("Collect",Collect);
				currow.put("Bet",Bet);
				currow.put("TotalBet",TotalBet);
				currow.put("CoinsInBet",CoinsInBet);
				currow.put("CoinSize",CoinSize);
				currow.put("DoubleTo",DoubleTo);
				currow.put("PickCard",PickCard);
				currow.put("Congratulations",Congratulations);
				currow.put("YouWin",YouWin);
				currow.put("DoubleLimitReached",DoubleLimitReached);
				
				
				curList.add(currow);
			}
		} catch (IOException e) {
			log.error(e.getMessage(),e);
		}
		return curList;
	}
	
}
