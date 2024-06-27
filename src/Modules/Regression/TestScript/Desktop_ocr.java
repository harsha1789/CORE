package Modules.Regression.TestScript;

import static org.testng.Assert.assertEquals;

import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;

import com.zensar.automation.framework.model.ScriptParameters;
import com.zensar.automation.framework.report.Desktop_HTML_Report;
import com.zensar.automation.framework.utils.Constant;
import com.zensar.automation.framework.utils.ExcelDataPoolManager;
import com.zensar.automation.framework.utils.Util;
import com.zensar.automation.library.CommonUtil;
import com.zensar.automation.library.TestPropReader;

import Modules.Regression.FunctionLibrary.CFNLibrary_Desktop;
import Modules.Regression.FunctionLibrary.factory.DesktopFunctionLibraryFactory;
import net.lightbody.bmp.BrowserMobProxyServer;

public class Desktop_ocr{
	
	Logger log = Logger.getLogger(Desktop_ocr.class.getName());
	public ScriptParameters scriptParameters;
	


	public void script() throws Exception{
		
		String mstrTCName=scriptParameters.getMstrTCName();
		String mstrTCDesc=scriptParameters.getMstrTCDesc();
		String mstrModuleName=scriptParameters.getMstrModuleName();
		WebDriver webdriver=scriptParameters.getDriver();
		BrowserMobProxyServer proxy=scriptParameters.getProxy();
		String startTime=scriptParameters.getStartTime();
		String filePath=scriptParameters.getFilePath();
		String userName=scriptParameters.getUserName();
		String browserName=scriptParameters.getBrowserName();
		String framework=scriptParameters.getFramework();
		String gameName=scriptParameters.getGameName();
		String languageDescription=null;
		String languageCode=null;
		String Settings=null;
		String status=null;
		int mintDetailCount=0;
		int mintPassed=0;
		int mintFailed=0;
		int mintWarnings=0;
		int mintStepNo=0;
		
		ExcelDataPoolManager excelpoolmanager= new ExcelDataPoolManager();
		Desktop_HTML_Report language = new Desktop_HTML_Report(webdriver,browserName,filePath,startTime,mstrTCName,mstrTCDesc,mstrModuleName,mintDetailCount,mintPassed,mintFailed,mintWarnings,mintStepNo,status,gameName);
		DesktopFunctionLibraryFactory desktopFunctionLibraryFactory=new DesktopFunctionLibraryFactory();
		CFNLibrary_Desktop cfnlib=desktopFunctionLibraryFactory.getFunctionLibrary(framework, webdriver, proxy, language, gameName);

		try{
			// Step 1 
			if(webdriver!=null)
			{
				
				Map<String, String> rowData2 = null;
				Map<String, String> rowData3 = null;
				CommonUtil util = new CommonUtil();
				String testDataExcelPath=TestPropReader.getInstance().getProperty("TestData_Excel_Path");
				String urlNew=null;
			
				String url = cfnlib.XpathMap.get("ApplicationURL");	
				
							

				webdriver.navigate().to(url);
				Thread.sleep(10000);	
			     
				List<Map<String, String>> list= ((Util) util).readLangTransList();
				
				int rowCount2 = excelpoolmanager.rowCount(testDataExcelPath, Constant.LANG_XL_SHEET_NAME);
				//row count2 10, repeats for 10 langs
				for(int j=1;j<rowCount2;j++){
					//Step 2: To get the languages in MAP and load the language specific url
					rowData2 = list.get(j);
					languageDescription = rowData2.get(Constant.LANGUAGE).trim();
					languageCode = rowData2.get(Constant.LANG_CODE).trim();
					Settings = rowData2.get(Constant.Settings).trim();
				
					System.out.println(rowData2);
					log.debug(rowData2);
					
					String gameSettingsText = "return " + cfnlib.XpathMap.get("getSettingsText");
					
					String consoleSettings = cfnlib.getConsoleText(gameSettingsText);
					
					System.out.println("Excel text --"+Settings);
					System.out.println("Console text-- "+consoleSettings);
					
						System.out.println(Settings.equals(consoleSettings));
					PrintStream out = new PrintStream(System.out, true, "UTF-8");
					out.println("Excel text "+Settings);
					out.println("Console text "+consoleSettings);
					
					
					String text=new String(Settings.getBytes(), "iso-8859-1");
					System.out.println("encoded text "+text);
					
					String text1=new String(Settings.getBytes(), "UTF-8");
					System.out.println("encoded text "+text1);
					
					String value="Настройки";
					out.println("value "+value);
					System.out.println(value);
				
					String rawString = "Entwickeln Sie mit Vergnügen";
					byte[] bytes = Settings.getBytes(StandardCharsets.UTF_8);

					String utf8EncodedString = new String(bytes, StandardCharsets.UTF_8);

					assertEquals(Settings, utf8EncodedString);
					System.out.println(utf8EncodedString);
					System.out.println(rawString);
					if(Settings.equals(utf8EncodedString))
					{
						
						System.out.println("true");
					}
					language.detailsAppend("utf8EncodedString "+utf8EncodedString,"value "+value,"Settings "+Settings+"consoleSettings "+consoleSettings,"");
					
				
					
					//Language Change logic:: for updating language in URL and then Refresh 
					if (j + 1 != rowCount2)
					{
						 rowData3 = list.get(j+1);
						String languageCode2 = rowData3.get(Constant.LANG_CODE).trim();

						String currentUrl = webdriver.getCurrentUrl();
						System.out.println(rowData3);
						log.debug(rowData3);
						if(currentUrl.contains("LanguageCode"))
							urlNew= currentUrl.replaceAll("LanguageCode="+languageCode, "LanguageCode="+languageCode2);
						else if(currentUrl.contains("languagecode"))
							urlNew = currentUrl.replaceAll("languagecode="+languageCode, "languagecode="+languageCode2);
						else if(currentUrl.contains("languageCode"))
							urlNew = currentUrl.replaceAll("languageCode="+languageCode, "languageCode="+languageCode2);
						
						webdriver.navigate().to(urlNew);
						Thread.sleep(10000);
												
					}
				}   
			
			}
		}	    
		//-------------------Handling the exception---------------------//
		catch (Exception e) {
			log.error(e.getMessage(),e);
			cfnlib.evalException(e);
		}
		//-------------------Closing the connections---------------//
		finally
		{
			language.endReport();
			webdriver.close();
			webdriver.quit();
			proxy.abort();
			Thread.sleep(1000);	   
		}	
	}
}
