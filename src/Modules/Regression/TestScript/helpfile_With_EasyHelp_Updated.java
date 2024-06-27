package Modules.Regression.TestScript;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;

import com.zensar.automation.framework.model.ScriptParameters;
import com.zensar.automation.framework.report.Desktop_HTML_Report;
import com.zensar.automation.framework.utils.Constant;
import com.zensar.automation.framework.utils.ExcelDataPoolManager;
import com.zensar.automation.library.CommonUtil;
import com.zensar.automation.library.TestPropReader;

import Modules.Regression.FunctionLibrary.CFNLibrary_Desktop;
import Modules.Regression.FunctionLibrary.factory.DesktopFunctionLibraryFactory;
import net.lightbody.bmp.BrowserMobProxyServer;

public class helpfile_With_EasyHelp_Updated 
{
	/**
	 *This sample script of markets with supported languages
	 * @author rk61073
	 *
	 */
	public ScriptParameters scriptParameters;

	Logger log ;//= Logger.getLogger(Helpfile_withEasyHelp.class.getName()); // To get Logs

	// -------------------Main script defination---------------------//
	public void script() throws Exception 
	{
		String mstrTCName = scriptParameters.getMstrTCName();
		String mstrTCDesc = scriptParameters.getMstrTCDesc();
		String mstrModuleName = scriptParameters.getMstrModuleName();
		WebDriver webdriver = scriptParameters.getDriver();
		BrowserMobProxyServer proxy = scriptParameters.getProxy();
		String startTime = scriptParameters.getStartTime();
		String filePath = scriptParameters.getFilePath();
		String browserName = scriptParameters.getBrowserName();
		String framework = scriptParameters.getFramework();
		String gameName = scriptParameters.getGameName();
		String status = null;
		int mintDetailCount = 0;
		int mintSubStepNo=0;
		int mintPassed = 0;
		int mintFailed = 0;
		int mintWarnings = 0;
		int mintStepNo = 0;
		String userName ;
		String urlNew =null;
		String languageDescription ;
		String languageCode;
		String balance;
		List<Map> langListForMarket;

		Desktop_HTML_Report markets = new Desktop_HTML_Report(webdriver, browserName, filePath, startTime, mstrTCName,mstrTCDesc, mstrModuleName, mintDetailCount, mintPassed, mintFailed, mintWarnings, mintStepNo, status,gameName);
		DesktopFunctionLibraryFactory desktopFunctionLibraryFactory=new DesktopFunctionLibraryFactory();
		CFNLibrary_Desktop cfnlib=desktopFunctionLibraryFactory.getFunctionLibrary(framework, webdriver, proxy,markets, gameName);
		CommonUtil util = new CommonUtil();
		
		
		try {
			
			
			
			if (webdriver != null) 
			{
			
				
				/*********************** Malta Market **********************/
				
		      if(cfnlib.XpathMap.get("malta_Market").equalsIgnoreCase("yes"))
				 { 
		    	  System.out.println("/*********************** Malta Market **********************/");
		    	   langListForMarket = util.readMarketLists(Constant.LANG_XL_SHEET_NAME_For_MALTA);
		    	   String url = cfnlib.XpathMap.get("Malta_URL");
		    	   webdriver.navigate().to(url);
		    	   String helpMenubutton = cfnlib.XpathMap.get("HelpMenu");
		    	   String HelpButton = cfnlib.XpathMap.get("Help");
		    	  cfnlib.marketHelpfilevalidation(webdriver, url , markets, cfnlib, util,langListForMarket,helpMenubutton,HelpButton);
				}
		      
		      
		      	/*********************** Italy Market **********************/
		     
		      if(cfnlib.XpathMap.get("Italy_Market").equalsIgnoreCase("yes"))
				{  System.out.println("/*********************** Italy Market **********************/");
				 langListForMarket = util.readMarketLists(Constant.LANG_XL_SHEET_NAME_For_ITALY);
		    	   String url = cfnlib.XpathMap.get("Italy_URL");
		    	   webdriver.navigate().to(url);
		    	   String helpMenubutton = cfnlib.XpathMap.get("HelpMenu");
		    	   String HelpButton = cfnlib.XpathMap.get("ItalyHelp");
		    	  cfnlib.marketHelpfilevalidation(webdriver, url , markets, cfnlib, util,langListForMarket,helpMenubutton,HelpButton);
				}
		      
		    /*********************** Romania Market **********************/
		      
		      if(cfnlib.XpathMap.get("Romania_Market").equalsIgnoreCase("yes"))
				{  
		    	  System.out.println("/*********************** Romania Market **********************/");
		    	   langListForMarket = util.readMarketLists(Constant.LANG_XL_SHEET_NAME_For_ROMANIA);
		    	   String url = cfnlib.XpathMap.get("Romania_URL");
		    	   webdriver.navigate().to(url);
		    	   String helpMenubutton = cfnlib.XpathMap.get("HelpMenu");
		    	   String HelpButton = cfnlib.XpathMap.get("Romania_Help");
		    	   
		    	  cfnlib.marketHelpfilevalidation(webdriver, url , markets, cfnlib, util,langListForMarket,helpMenubutton,HelpButton);
				}
		      
		      
		     /*********************** Croatia Market **********************/
		      
		      if(cfnlib.XpathMap.get("Croatia_Market").equalsIgnoreCase("yes"))
				{  System.out.println(" /*********************** Croatia Market **********************/");
				langListForMarket = util.readMarketLists(Constant.LANG_XL_SHEET_NAME_For_CROATIA);
		    	   String url = cfnlib.XpathMap.get("Croatia_URL");
		    	   webdriver.navigate().to(url);
		    	   String helpMenubutton = cfnlib.XpathMap.get("HelpMenu");
		    	   String HelpButton = cfnlib.XpathMap.get("Romania_Help");
		    	   
		    	  cfnlib.marketHelpfilevalidation(webdriver, url , markets, cfnlib, util,langListForMarket,helpMenubutton,HelpButton);
				}
		      
		      
		      /*********************** Switzerland Market **********************/
		      
		      if(cfnlib.XpathMap.get("Switzerland_Market").equalsIgnoreCase("yes"))
				{  System.out.println("/*********************** Switzerland Market **********************/");
				langListForMarket = util.readMarketLists(Constant.LANG_XL_SHEET_NAME_For_SWITZERLAND);
		    	   String url = cfnlib.XpathMap.get("Switzerland_URL");
		    	   webdriver.navigate().to(url);
		    	   String helpMenubutton = cfnlib.XpathMap.get("HelpMenu");
		    	   String HelpButton = cfnlib.XpathMap.get("Romania_Help");
		    	   
		    	  cfnlib.marketHelpfilevalidation(webdriver, url , markets, cfnlib, util,langListForMarket,helpMenubutton,HelpButton);
				}
		      
		      
		    /*********************** UK Market **********************/
		      
		      if(cfnlib.XpathMap.get("UK_Market").equalsIgnoreCase("yes"))
				{  
		    	  System.out.println("/*********************** UK Market **********************/");
		    	  // langListForMarket = util.readLangListForUK();
		    	   langListForMarket = util.readMarketLists(Constant.LANG_XL_SHEET_NAME_For_ITALY);
		    	   String url = cfnlib.XpathMap.get("UK_URL");
		    	   webdriver.navigate().to(url);
		    	   String helpMenubutton = cfnlib.XpathMap.get("HelpMenu");
		    	   String HelpButton = cfnlib.XpathMap.get("Help");
		    	   
		    	  cfnlib.marketHelpfilevalidation(webdriver, url , markets, cfnlib, util,langListForMarket,helpMenubutton,HelpButton);
				}


		      /*********************** Germany Market **********************/
		      
		      if(cfnlib.XpathMap.get("Germany_Market").equalsIgnoreCase("yes"))
				{  
		    	  System.out.println("/*********************** Germany Market **********************/");
		    	  langListForMarket = util.readMarketLists(Constant.LANG_XL_SHEET_NAME_For_GERMANY);
		    	   String url = cfnlib.XpathMap.get("Germany_URL");
		    	   webdriver.navigate().to(url);
		    	   String helpMenubutton = cfnlib.XpathMap.get("HelpMenu");
		    	   String HelpButton = cfnlib.XpathMap.get("Romania_Help");
		    	   
		    	  cfnlib.marketHelpfilevalidation(webdriver, url , markets, cfnlib, util,langListForMarket,helpMenubutton,HelpButton);
				}
	
		      
		      
		      
		      
		      
		      
		/********************************************************End****************************************************************/
			}//if
		}//try
		// TRY
			// -------------------Handling the exception---------------------//

				catch (Exception e) {
					log.error(e.getMessage(), e);
					cfnlib.evalException(e);
				}

				finally {

					// Global.browserproxy.abort();
					// ---------- Closing the report----------//

					markets.endReport();
					// ---------- Closing the webdriver ----------//
					webdriver.close();

					webdriver.quit();

					// Global.appiumService.stop();
				}
		
		
		
		
		
		
		
		
	
		
	}

	
	

	

}
