package com.zensar.automation.executors;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Row.MissingCellPolicy;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.testng.TestNG;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.beust.jcommander.internal.Lists;
import com.zensar.automation.framework.library.UnableToLoadPropertiesException;
import com.zensar.automation.framework.model.DeviceInfo;
import com.zensar.automation.framework.utils.Constant;
import com.zensar.automation.library.CommonUtil;
import com.zensar.automation.library.TestPropReader;

import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateNotFoundException;
import freemarker.core.ParseException;
import freemarker.template.Configuration;
import freemarker.template.MalformedTemplateNameException;
import freemarker.template.Template;
import freemarker.template.Version;

public class OBATRegressionSuiteGTP {
	
	
	@Test
	(dataProvider ="readInputParameters")
	public  void runSuites(String platform,String gameName,String browserName,String derivcoUsername,String derivcoPassword) 
	{
		
		
		//1)  generate the xml file
		
		// 2) run the xml file
		String testNGFile=null;
		//System.setProperty("logfilename","./"+gameName+"/Selenium");
		//System.setProperty("logfilename",gameName+"/Selenium");
		System.setProperty("logfilename",gameName+"/"+"Selenium");
		//System.setProperty("TestData_Excel_Path","./GameTechnology/GameConfig"+Constant.TESTDATA_EXCEL_PATH);
		//System.setProperty("Regression_Excel_Path", "./GameTechnology/GameConfig"+Constant.REGRESSION_EXCEL_PATH);
		//System.setProperty("Suit_Excel_Path", "./GameTechnology/GameConfig"+Constant.SUIT_EXCEL_PATH);
		
		if(platform.equalsIgnoreCase(Constant.DESKTOP)) {
			testNGFile= desktopConfigGenerator(gameName,browserName);
			
		}/*else if(platform.equalsIgnoreCase(Constant.MOBILE)) {
			List<DeviceInfo> finalDeviceList= new ArrayList<>();
			
			DeviceInfo deviceinfo =new DeviceInfo();
			/*deviceinfo.setTestName(gameName+"Test");
			deviceinfo.setCheckedOutDeviceNum(1);
			deviceinfo.setSerial(serial);
			deviceinfo.setVersion(osVersion);
			deviceinfo.setTestName(gameName+"Test");
			deviceinfo.setUsername(username);
			deviceinfo.setMarketName(deviceName);
			deviceinfo.setOsPlatform(osplatform);
			deviceinfo.setBrowserName(browserName);
			finalDeviceList.add(deviceinfo);
			*/
			//testNGFile= generateMobileXMLFile(gameName,finalDeviceList);}
		else {
			//Flow skipped
		}

		TestNG testng = new TestNG();
		List<String> suites = Lists.newArrayList();
		
		Logger log = Logger.getLogger(OBATRegressionSuiteGTP.class.getName());
		if(gameName != null )
		{
			try{

				log.debug("Input parameters:");
				log.debug("platform: "+ platform);
				log.debug("gamename: "+gameName);
				//log.debug("envName: "+envName);
				TestPropReader.getInstance().loadAllProperties("GameTechnology/GameConfig","gtp");
				//TestPropReader.getInstance().setProperty("GameName", gameName);
				System.setProperty("derivcoUser",derivcoUsername);
				System.setProperty("derivcoPassword",derivcoPassword);
				System.setProperty("OrgGameName", gameName);
				
				suites.add(testNGFile);
				/*if( platform.equalsIgnoreCase(Constant.DESKTOP) ){
					suites.add("./"+gameName+"/Config/Desktop_Suite.xml");
				} else if (platform.equalsIgnoreCase(Constant.MOBILE)){
					suites.add("./"+gameName+"/Config/Mobile_Suite.xml");	
				}*/

				testng.setTestSuites(suites);
				testng.run();
				//TestPropReader.clearAllProperties();
			}catch(Exception e)
			{
				log.error(e);
			}
		}else
		{
			System.out.println("Excution skipped as mandatory parameter missing, plase provide game name and env ID ,mid,cid Mobile and cid Desktop parametes");
		}

	}
	/**
	 * This will read the input test parameter for regression suite from xlsx file 
	 * @return
	 * @throws IOException
	 */
	@DataProvider
	public  Object[][] readInputParameters() throws IOException
	{
		DataFormatter formatter= new DataFormatter();
		Object[][] data = null;
		String path=System.getProperty("user.dir");
		System.out.println(path);
		try(Workbook workbook= WorkbookFactory.create(new FileInputStream("./GameTechnology/GameConfig/Config/OBATTestInputs.xls")))
		{
			workbook.setMissingCellPolicy(MissingCellPolicy.RETURN_BLANK_AS_NULL);
			Sheet worksheet=workbook.getSheet("Inputs");
			Row row = worksheet.getRow(0);
			int colNum = row.getLastCellNum();
			int rowNum = worksheet.getPhysicalNumberOfRows();// count my number of Rows

			data= new Object[rowNum-1][colNum]; // pass my  count data in array

			for(int i=0; i<rowNum-1; i++) //Loop work for Rows
			{  
				Row newRow= worksheet.getRow(i+1);
				for (int j=0; j<colNum; j++) //Loop work for colNum
				{
					if(newRow==null)
						data[i][j]= "";
					else
					{
						Cell cell= newRow.getCell(j);
						if(cell==null)
							data[i][j]= ""; //if it get Null value it pass no data 
						else
						{
							String value=formatter.formatCellValue(cell);
							data[i][j]=value; //This formatter get my all values as string i.e integer, float all type data value
						}
					}
				}
			}
		}catch(IOException | InvalidFormatException ioException)
		{
			System.out.println(ioException.getMessage());
		}
		return data;
	}
	
public String desktopConfigGenerator(String gameName,String browserName) {
		
		Configuration cfg =  new Configuration(new Version("2.3.0"));
		String desktopTestNGPath= null;
	
		try {

			
			// Load template
			Template template = cfg.getTemplate("./GameTechnology/GameConfig/Config/TestNGDesktopXMLTemplate.ftl");
//			log.info("Read the Desktop xml template");
			// Create data for template
			Map<String, Object> templateData = new HashMap<>();
			 
			templateData.put("browserName", browserName);
			templateData.put("gameName", gameName);

			// Write output on console example 1
			StringWriter out = new StringWriter();
			template.process(templateData, out);
	//		log.info(out.getBuffer().toString());
			out.flush();
			
			desktopTestNGPath="./GameTechnology/GameConfig/Config/"+gameName+"DesktopTestNG.xml";
			// Write data to the file
			Writer file = new FileWriter(new File(desktopTestNGPath));
			template.process(templateData, file);
			file.flush();
			file.close();
			TestPropReader.getInstance().setProperty("DesktopTestNGPath", desktopTestNGPath);
		//	log.info("Mobile Test Ng file path="+desktopTestNGPath);
			
		} catch (Exception e) {
			e.printStackTrace();
		} 
		
		return desktopTestNGPath;
	}

	
	

}
