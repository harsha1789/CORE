package Modules.Regression.TestScript;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.zensar.automation.api.RestAPILibrary;
import com.zensar.automation.framework.model.ScriptParameters;
import com.zensar.automation.framework.report.Desktop_HTML_Report;
import com.zensar.automation.framework.utils.Constant;
import com.zensar.automation.framework.utils.ExcelDataPoolManager;
import com.zensar.automation.library.CommonUtil;
import com.zensar.automation.library.TestPropReader;

import Modules.Regression.FunctionLibrary.CFNLibrary_Desktop;
import Modules.Regression.FunctionLibrary.factory.DesktopFunctionLibraryFactory;
import net.lightbody.bmp.BrowserMobProxyServer;
/**
 * 
 * 
 * This Script is for Language verification
 * ===========================================================================
 * 1.Base Game(By changing the dimensions of the Screen )
 * ======================================================
 * 1.1.Game Logo
 * 1.2.Stats - general , win summary - before card selection 
 * 1.3.Max Bet 
 * 1.5.HamburgerMenu (Settings)
 * 1.7.Paytable payouts
 * 1.8.Gamble limit reached
 * 1.9.Stats - general , win summary - after card selection
 * 
 * 2.TestData
 * ============
 * 2.1 Min win (Where it takes 1 to 2 iterations to reach gamble limit in double)
 * 
 * @author rk61073
 *
 */

public class Desktop_Regression_Language_VideoPoker_BaseScene
{
	
	Logger log = Logger.getLogger(Desktop_Regression_Language_VideoPoker_BaseScene.class.getName());
	public ScriptParameters scriptParameters;
	
	public void script() throws Exception
	{
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
		String status=null;
		int mintDetailCount=0;
		int mintPassed=0;
		int mintFailed=0;
		int mintWarnings=0;
		int mintStepNo=0;
		
		ExcelDataPoolManager excelpoolmanager= new ExcelDataPoolManager();
		Desktop_HTML_Report languageReport = new Desktop_HTML_Report(webdriver,browserName,filePath,startTime,mstrTCName,mstrTCDesc,mstrModuleName,mintDetailCount,mintPassed,mintFailed,mintWarnings,mintStepNo,status,gameName);
		DesktopFunctionLibraryFactory desktopFunctionLibraryFactory=new DesktopFunctionLibraryFactory();
		CFNLibrary_Desktop cfnlib=desktopFunctionLibraryFactory.getFunctionLibrary(framework, webdriver, proxy,languageReport, gameName);
		CommonUtil util = new CommonUtil();RestAPILibrary apiObj = new RestAPILibrary();
		
		List<String> copiedFiles=new ArrayList<>();
		int mid=Integer.parseInt(TestPropReader.getInstance().getProperty("MID"));
		System.out.println("MID : " +mid);log.debug("MID : " +mid);
		int cid=Integer.parseInt(TestPropReader.getInstance().getProperty("CIDDesktop"));
		System.out.println("CID : " +cid);log.debug("CID : " +cid);
		
		try
		{	
		if(webdriver!=null)
		{	
			Map<String, String> rowData2 = null;
			Map<String, String> rowData3 = null;
			String strFileName=TestPropReader.getInstance().getProperty("LanguageTestDataPath");
			System.out.println(strFileName);log.debug(strFileName);
			String testDataExcelPath=TestPropReader.getInstance().getProperty("TestData_Excel_Path");//testdata.xls path(Excel)
			String urlNew=null;
				
			File testDataFile=new File(strFileName);	
			//userName="roshi";
			userName=util.randomStringgenerator();
			System.out.println("UserName : "+userName);
			if(	util.copyFilesToTestServer(mid, cid, testDataFile, userName, copiedFiles))
			{	
			String url = cfnlib.XpathMap.get("ApplicationURL");
			String launchURL = url.replaceFirst("\\busername=.*?(&|$)", "username="+userName+"$1");
			log.info("URL = " +launchURL);System.out.println("URL  = " +launchURL);
				
			
			if(	cfnlib.loadGame(launchURL))
			{
			System.out.println("Game Launched Successfully");log.info("Game Launched Successfully");
			
			if(framework.equalsIgnoreCase("CS"))
			{
				cfnlib.setNameSpace();
			}
			
			//wait for the draw button
			cfnlib.waitForDealButton();
			Thread.sleep(1000);	
			
			List<Map> list= util.readLangList();
			int rowCount2 = excelpoolmanager.rowCount(testDataExcelPath, Constant.LANG_XL_SHEET_NAME);
			for(int j=1;j<rowCount2;j++)
			{
			//Step 2: To get the languages in MAP and load the language specific url
			rowData2 = list.get(j);
			languageDescription = rowData2.get(Constant.LANGUAGE).trim();
			languageCode = rowData2.get(Constant.LANG_CODE).trim();
			languageReport.detailsAppendFolder("Game Loaded ","Language : "+languageCode,"Language : "+languageCode,"PASS",""+languageCode);	
			//languageReport.detailsAppendNoScreenshot("Game loaded successfully ", "Language : "+languageCode, "Language : "+languageCode, "");
			System.out.println("****** LANGUAGE NAME   : "+languageCode+ " ******");log.debug("*** Language  : "+languageCode+ " ***");	
		
			
			/*********game logo *****************/
			cfnlib.gameLogo(languageReport, languageCode);
			
			/*****************Click on paytable close button **********/
			
			if(cfnlib.XpathMap.get("isPaytableButtonAvailable").equalsIgnoreCase("yes"))
			{
				if(cfnlib.checkAvilabilityofElement("PaytableClose"))
				{
				if(cfnlib.payTableClose())
				{
					log.debug("Click on paytable close button : PASS");
				}}
			else 
			{	
				System.out.println("Click on paytable close button : FAIL");log.debug("Click on paytable close button : FAIL");
			}}
			
			
			/****************************** PAYTABLE ******************************/
			
			if(cfnlib.XpathMap.get("isPaytableButtonAvailable").equalsIgnoreCase("yes")) 
			{
		  languageReport.detailsAppendNoScreenshot("Scenario : Verify paytable", "Click on paytable button", "", "");
			boolean paytable =	cfnlib.fun_Click("PaytableIcon");
			if(paytable)
			{Thread.sleep(2000);
				languageReport.detailsAppendFolder("Verify paytable", "Clicked on paytable button", "Clicked on paytable button", "PASS", languageCode);
				cfnlib.resizeBrowser(600, 800);
				Thread.sleep(2000);
				languageReport.detailsAppendFolder("Verify paytable", "Resizing the browser to 600 X 800", "Resizing the browser to 600 X 800", "PASS", languageCode);
				cfnlib.resizeBrowser(400,600);
				Thread.sleep(2000);
				languageReport.detailsAppendFolder("Verify paytable", "Resizing the browser to 400 X 600", "Resizing the browser to 400 X 600", "PASS", languageCode);
				webdriver.manage().window().maximize();Thread.sleep(2000);
				log.debug("Clicked on paytable button : PASS");
	            if(cfnlib.fun_Click("PaytableClose"))
	            {
	            	log.debug("Clicked on paytable close button : PASS");
	            }
	            else
	            {
	            	System.out.println("Clicked on paytable close button : FAIL");log.debug("Clicked on paytable close button : FAIL");
	            	languageReport.detailsAppendFolder("Verify paytable close", "Clicked on paytable close button", "Clicked on paytable close button", "FAIL", languageCode);
	            }
			}
			else
			{
				System.out.println("Clicked on paytable button : FAIL");log.debug("Clicked on paytable button : FAIL");
				languageReport.detailsAppendFolder("Verify paytable", "Clicked on paytable button", "Clicked on paytable button", "FAIL", languageCode);
			}}
			

	/***************************************** set max Bet pannel **************************************/
			languageReport.detailsAppendNoScreenshot("Scenario : Verify maximum bet", "Slide the coins and coin size to maximum bet", "", "");
			if(cfnlib.fun_Click("BetButton"))
			{
			boolean coinsize = cfnlib.slideTheCoinSliderToMax();
			boolean coins = cfnlib.slideTheCoinsToMax();
			
			String maxbet = "CoinSize slided to max : " + coinsize  + "  Coins slided to max : " + coins ;
			if(coinsize && coins )
			{
			languageReport.detailsAppendFolder("Verify maximum bet", "Slide the coins and coin size to maximum bet", "Slide the coins and coin size to maximum bet", "PASS", languageCode);
			cfnlib.resizeBrowser(600, 800);
			Thread.sleep(2000);
			languageReport.detailsAppendFolder("Verify maximum bet", "Resizing the browser to 600 X 800", "Resizing the browser to 600 X 800", "PASS", languageCode);
			cfnlib.resizeBrowser(400,600);
			Thread.sleep(2000);
			languageReport.detailsAppendFolder("Verify maximum bet", "Resizing the browser to 400 X 600", "Resizing the browser to 400 X 600", "PASS", languageCode);
			webdriver.manage().window().maximize();Thread.sleep(2000);
			log.debug("Slide the coins and coin size to maximum bet : PASS");
			}
			else 
			{
			System.out.println("Slide the coins and coin size to maximum bet : FAIL");log.debug("Slide the coins and coin size to maximum bet : FAIL");
			languageReport.detailsAppendFolder("Verify maximum bet", "Slide the coins and coin size to maximum bet", ""+maxbet, "FAIL", languageCode);
			}cfnlib.closeOverlay();Thread.sleep(2000);		
			}
			else
			{
				System.out.println("Clicked ob bet button : FAIL");log.debug("Clicked ob bet button : FAIL");
				languageReport.detailsAppendFolder("Verify bet button","Clicked on bet button","Clicked on bet button","FAIL",""+languageCode);	
			}		
		
			
		/******Hamburger menu*********/
		languageReport.detailsAppendNoScreenshot("Scenario : Verify hamburger menu", "Click on hamburger menu and naivagate to settings  ", "", "");
	    boolean hamburgermenu = cfnlib.fun_Click("HamburgerMenu");
		if(hamburgermenu)
		{Thread.sleep(2000);
			languageReport.detailsAppendFolder("Verify hamburger menu ", "Clicked on hamburger menu button ", "Clicked on hamburger menu button", "PASS", languageCode);
			cfnlib.resizeBrowser(600, 800);
			Thread.sleep(2000);
			languageReport.detailsAppendFolder("Verify hamburger menu ", "Resizing the browser to 600 X 800", "Resizing the browser to 600 X 800", "PASS", languageCode);
			cfnlib.resizeBrowser(400,600);
			Thread.sleep(2000);
			languageReport.detailsAppendFolder("Verify hamburger menu ", "Resizing the browser to 400 X 600", "Resizing the browser to 400 X 600", "PASS", languageCode);
			webdriver.manage().window().maximize();Thread.sleep(2000);log.debug("Clicked on hamburger menu button : PASS");
		}
		else
		{
		  System.out.println("Clicked on hamburger menu button: PASS : FAIL ");log.debug("Clicked on hamburger menu button: PASS : FAIL ");
		  languageReport.detailsAppendFolder("Verify hamburger menu ", "Clicked on hamburger menu button: PASS", "Clicked on hamburger menu button: PASS", "FAIL", languageCode);
		}

		boolean settingsIconFromMenu = cfnlib.fun_Click("SettingsIconfromHamburgerMenu");
		if(settingsIconFromMenu)
		{Thread.sleep(2000);
	        languageReport.detailsAppendFolder("Verify settings from hamburger menu","Clicked on settings button from menu","Clicked on settings button from menu","PASS",""+languageCode);
			cfnlib.resizeBrowser(600, 800);
			Thread.sleep(2000);
			languageReport.detailsAppendFolder("Verify settings from hamburger menu", "Resizing the browser to 600 X 800", "Resizing the browser to 600 X 800", "PASS", languageCode);
			cfnlib.resizeBrowser(400,600);
			Thread.sleep(2000);
			languageReport.detailsAppendFolder("Verify settings from hamburger menu", "Resizing the browser to 400 X 600", "Resizing the browser to 400 X 600", "PASS", languageCode);
			webdriver.manage().window().maximize();Thread.sleep(3000); log.debug("Clicked on settings button from menu : PASS");
			boolean close  = cfnlib.fun_Click("CloseSettings");
				if(close)
				{
					log.debug("Clicked on close settings button  : PASS");
				}
				else
				{
					System.out.println("Clicked on close settings button : FAIL");log.debug("Clicked on close settings button : FAIL");
					languageReport.detailsAppendFolder("Verify settings close form hamburger menu","Clicked on settings close button from menu","Clicked on settings close button from menu","FAIL",""+languageCode);
				}
		}//closing if loop of close
		else
		{
		System.out.println("Clicked on settings button from menu : FAIL");log.debug("Clicked on settings button from menu  : FAIL");
		languageReport.detailsAppendFolder("Verify settings form hamburger menu","Clicked on settings button from menu","Clicked on settings button from menu","FAIL",""+languageCode);
		}
	
				
		/***************** Click on deal and verify draw button *****************/
	    languageReport.detailsAppendNoScreenshot("Scenario : Verify draw button", "Click on deal and verify draw button", "", "");
		if(cfnlib.dealBtn())
		{
			languageReport.detailsAppendFolder("Verify draw button", "Clicked on deal and verify draw button", "Clicked on deal and verify draw button", "PASS", languageCode);
			cfnlib.resizeBrowser(600, 800);
			Thread.sleep(2000);
			languageReport.detailsAppendFolder("Verify draw button", "Resizing the browser to 600 X 800", "Resizing the browser to 600 X 800", "PASS", languageCode);
			cfnlib.resizeBrowser(400,600);
			Thread.sleep(2000);
			languageReport.detailsAppendFolder("Verify draw button", "Resizing the browser to 400 X 600", "Resizing the browser to 400 X 600", "PASS", languageCode);
			webdriver.manage().window().maximize();Thread.sleep(2000);
			log.debug("Click on deal and verify draw button : PASS");
		} 
		else 
		{
			System.out.println("Click on deal and verify draw button : FAIL");log.debug("Click on deal and verify draw button : FAIL");
			languageReport.detailsAppendFolder("Verify draw button", "Clicked on deal and verify draw button", "Clicked on deal and verify draw button", "FAIL", languageCode);
		}
		
		/***************** Click on draw and verify collect button *****************/
		languageReport.detailsAppendNoScreenshot("Scenario : Verify collect button", "Click on draw and verify collect button", "", "");
		if(cfnlib.drawBtn())
		{
			languageReport.detailsAppendFolder("Verify collect button", "Clicked on draw and verify collect button", "Clicked on draw and verify collect button", "PASS", languageCode);
			cfnlib.resizeBrowser(600, 800);
			Thread.sleep(2000);
			languageReport.detailsAppendFolder("Verify collect button", "Resizing the browser to 600 X 800", "Resizing the browser to 600 X 800", "PASS", languageCode);
			cfnlib.resizeBrowser(400,600);
			Thread.sleep(2000);
			languageReport.detailsAppendFolder("Verify collect button", "Resizing the browser to 400 X 600", "Resizing the browser to 400 X 600", "PASS", languageCode);
			webdriver.manage().window().maximize();Thread.sleep(2000);
			log.debug("Click on draw and verify collect button : PASS");
		} 
		else 
		{
			System.out.println("Click on draw and verify collect button : FAIL");log.debug("Click on draw and verify collect button : FAIL");	
			languageReport.detailsAppendFolder("Verify collect button", "Click on draw and verify collect button", "Click on draw and verify collect button", "FAIL", languageCode);
		}
	
		
	/************************ VERIFY GAMBLE LIMIT REACH  ************************/
	languageReport.detailsAppendNoScreenshot("Scenario : Verify double to screen before the card selection", "Move to double to screen", "", ""); 
	     if(cfnlib.doubleBtn())
	     { 
	    languageReport.detailsAppendFolder("Verify double to screen before the  card selection", "Moved to double to screen", "Moved to double to screen", "PASS", languageCode);
		cfnlib.resizeBrowser(600, 800);
		Thread.sleep(2000);
		languageReport.detailsAppendFolder("Verify double to screen before the  card selection", "Resizing the browser to 600 X 800", "Resizing the browser to 600 X 800", "PASS", languageCode);
		cfnlib.resizeBrowser(400,600);
		Thread.sleep(2000);
		languageReport.detailsAppendFolder("Verify double to screen before card selection", "Resizing the browser to 400 X 600", "Resizing the browser to 600 X 800", "PASS", languageCode);
		webdriver.manage().window().maximize();Thread.sleep(2000);
		log.debug("Clicked on double button and moved to double to screen : PASS");Thread.sleep(2000);
		
	    /***Click on double to button screen untill gamble limit is reached***/
	 languageReport.detailsAppendNoScreenshot("Scenario : Click on double to , untill the gamble limit reached", "Click on double to button , untill gamble limit is reached", "", "");
    while(!cfnlib.checkAvilabilityofElement("GambleLimitReached"))
	{
   if(cfnlib.checkAvilabilityofElement("DefaultDoubleCard"))
	{	
	 if(cfnlib.fun_Click("SelectDoubleCard"))
	 {
		log.debug("Card Selection in double tO : PASS"); 
		 if(cfnlib.checkAvilabilityofElement("GambleLimitReached")) 
		 {
			break; 
		 }
	 }
	 else 
	 {
		 System.out.println("Card Selection in double to : FAIL");log.debug("Card Selection in double to : FAIL");
		 languageReport.detailsAppendFolder("Verify card selection in double to ", "Card selection in double to", "Card selection in double to", "FAIL", languageCode);
	 }}
   else 
   {
	   System.out.println("Card Selection in double to : FAIL");log.debug("Card Selection in double to : FAIL");
   }
   languageReport.detailsAppendFolder("Clicked on double to , untill the gamble limit reached", "Card selection in double to until gamble limit is reached ", "Card selection in double to until gamble limit is reached ", "PASS", languageCode);
	cfnlib.resizeBrowser(600, 800);
	Thread.sleep(2000);
	languageReport.detailsAppendFolder("Clicked on double to , untill the gamble limit reached", "Resizing the browser to 600 X 800", "Resizing the browser to 600 X 800", "PASS", languageCode);
	cfnlib.resizeBrowser(400,600);
	Thread.sleep(2000);
	languageReport.detailsAppendFolder("Clicked on double to , untill the gamble limit reached", "Resizing the browser to 400 X 600", "Resizing the browser to 400 X 600", "PASS", languageCode);
	webdriver.manage().window().maximize();Thread.sleep(2000);
	
	  if(cfnlib.fun_Click("DoubleButton"))
	  {
	  log.debug("Click on double to button .Until you reach gamble limit : PASS");
	  }
	  else 
	  {
		  System.out.println("Click on double to button in double to screen until gamble limit is reached : FAIL"); log.debug("Click on double to button in double to screen until gamble limit is reached : FAIL");
		  languageReport.detailsAppendFolder("Verify double to button in double to screen", "Clicked on double to button in double to screen until gamble limit is reached", "Clicked on double to button in double to screen until gamble limit is reached", "FAIL", languageCode);
	  }
		}//closing while loop 
    
   if(cfnlib.checkAvilabilityofElement("GambleLimitReached"))
   {
	   if(cfnlib.checkAvilabilityofElement("IsBackToBaseGameButtonEnabled"))
	   {
		   if (cfnlib.fun_GetText("GambleLimitReached") != null) 
			{
				log.debug("Gamble limitis reached : PASS");
				languageReport.detailsAppendFolder("Verify gamble limit reached", "Gamble limit is reached", "Gamble limit is reached", "PASS", languageCode);
				cfnlib.resizeBrowser(600, 800);
				Thread.sleep(2000);
				languageReport.detailsAppendFolder("Verify gamble limit reached", "Resizing the browser to 600 X 800", "Resizing the browser to 600 X 800", "PASS", languageCode);
				cfnlib.resizeBrowser(400,600);
				Thread.sleep(2000);
				languageReport.detailsAppendFolder("Verify gamble limit reached", "Resizing the browser to 400 X 600", "Resizing the browser to 400 X 600", "PASS", languageCode);
				webdriver.manage().window().maximize();Thread.sleep(2000);
			} 
		   else
		   {
				System.out.println("Gamble limitis reached  : FAIL");System.out.println("Gamble limitis reached  : FAIL");
				languageReport.detailsAppendFolder("Verify gamble limit reached", "Gamble limitis reached", "Gamble limitis reached", "FAIL", languageCode);
			}  
		   
		   languageReport.detailsAppendNoScreenshot("Scenario : Back to base game from double to screen ", "Click on back to base game button in double to screen and moved to base game", "", ""); 
		   boolean isbacktobasebuttonVisible =	cfnlib.checkAvilabilityofElement("IsBackToBaseGameButtonEnabled");
		   boolean isbacktobasebuttonBtn = cfnlib.fun_Click("BackToBaseGame");
		   if (isbacktobasebuttonVisible &&  isbacktobasebuttonBtn)
			{
				log.debug("Back to Base Game : PASS");
				/***************verify base - scene once , back from double to feature ***************/
				if(cfnlib.checkAvilabilityofElement("DealButton"))
				{
				languageReport.detailsAppendFolder("Back to base from double to screen", "Clicked on back to base game button in double to screen and moved to base game", "Clicked on back to base game button in double to screen and moved to base game", "PASS", languageCode);
				cfnlib.resizeBrowser(600, 800);
				Thread.sleep(2000);
				languageReport.detailsAppendFolder("Back to base from double to screen", "Resizing the browser to 600 X 800", "Resizing the browser to 600 X 800", "PASS", languageCode);
				cfnlib.resizeBrowser(400,600);
				Thread.sleep(2000);
				languageReport.detailsAppendFolder("Back to base game from double to screen", "Resizing the browser to 400 X 600", "Resizing the browser to 400 X 600", "PASS", languageCode);
				webdriver.manage().window().maximize();Thread.sleep(2000);
				
				}//closing deal button availability 
			}//closing back to base game 
			else 
			{
				System.out.println("Back to Base Game : FAIL");log.debug("Back to Base Game : FAIL");
				languageReport.detailsAppendFolder("Verify back to base game from double to screen", "Clicked on back to base game button in double to screen", "Clicked on back to base game button in double to screen", "FAIL", languageCode);
			}	   
	   }//closing checkAvilabilityofElement of an BackToBaseGame
	   else
		 {
		   log.debug("Gamble limitis reached : FAIL");System.out.println("GambleLimitReached : FAIL");
			languageReport.detailsAppendFolder("Verify gamble limit reached", "Gamble limit is reached", "Gamble limit is reached", "FAIL", languageCode);
		 }
   }//closing checkAvilabilityofElement of an GambleLimitReached
   else
	 {
	   System.out.println("GambleLimitReached : FAIL"); log.debug("Gamble limitis reached : FAIL");
		languageReport.detailsAppendFolder("Verify back to base game from double to screen", "Clicked on back to base game button in double to screen", "Clicked on back to base game button in double to screen", "FAIL", languageCode);
	 }	
		
	 }//closing if condition of double to  
	 else
	 {
		 log.debug("Clicked on double button and moved to double to screen : FAIL");System.out.println("Clicked on double button and moved to double to screen : FAIL");
		 languageReport.detailsAppendFolder("Verify double to", "Moved to double to screen", "Moved to double to screen", "FAIL", languageCode);
	 }
 
	     /**********verify stats button - general  symbol********/
			languageReport.detailsAppendNoScreenshot("Scenario : Verify stats - general after moving back  from double to screen  ","Click on stats- general after moving back  from double to screen","","");
			if(cfnlib.fun_Click("StatsButton"))
			{Thread.sleep(2000);
				languageReport.detailsAppendFolder("Verify stats - general after moving back  from double to screen  ", "Clicked on stats - general after moving back from double to screen", "Stats - general after moving back from double to screen", "PASS", languageCode);
				cfnlib.resizeBrowser(600, 800);
				Thread.sleep(2000);
				languageReport.detailsAppendFolder("Verify stats - general after moving back  from double to screen  ", "Resizing the browser to 600 X 800", "Resizing the browser to 600 X 800", "PASS", languageCode);
				cfnlib.resizeBrowser(400,600);
				Thread.sleep(2000);
				languageReport.detailsAppendFolder("Verify stats - general after moving back  from double to screen  ", "Resizing the browser to 400 X 600", "Resizing the browser to 400 X 600", "PASS", languageCode);
				webdriver.manage().window().maximize();Thread.sleep(2000);
				log.debug("Clicked stats - general after coming back from double to screen   : PASS");
			}
			else
         {
         	System.out.println("Clicked stats - general after coming back from double to screen  : FAIL");log.debug("Clicked stats - general after coming back from double to screen  : FAIL");
         	languageReport.detailsAppendFolder("Verify stats - general after moving back  from double to screen  ", "Clicked stats - general after moving back from double to screen", "Stats - general after moving back from double to screen", "FAIL", languageCode);
         }
			
			/**********verify stats button - Win summary symbol********/
			languageReport.detailsAppendNoScreenshot("Scenario : Verify stats - win summary after moving back from double to screen  ","Click on  stats- winsummary after coming back from double to screen","","");
			if(cfnlib.fun_Click("WinSummaryButtonInStats"))
			{
				languageReport.detailsAppendFolder("Verify stats - win summary after moving back from double to screen  ", "Clicked on  stats - win summary after moving back from double to screen", "Stats - win summary after moving back from double to screen", "PASS", languageCode);
				cfnlib.resizeBrowser(600, 800);
				Thread.sleep(2000);
				languageReport.detailsAppendFolder("Verify stats - win summary after moving back from double to screen  ", "Resizing the browser to 600 X 800", "Resizing the browser to 600 X 800", "PASS", languageCode);
				cfnlib.resizeBrowser(400,600);
				Thread.sleep(2000);
				languageReport.detailsAppendFolder("Verify stats - win summary after moving back from double to screen  ", "Resizing the browser to 400 X 600", "Resizing the browser to 400 X 600", "PASS", languageCode);
				webdriver.manage().window().maximize();Thread.sleep(2000);
				log.debug("Clicked stats - winsummary after coming back from double to screen : PASS");
				if(cfnlib.fun_Click("GenaeralButtonInStats") && cfnlib.fun_Click("StatsOkButton"))
				{Thread.sleep(2000);
				   log.debug("Clicked on stats general and ok button : PASS");
				}
				else
				{   cfnlib.fun_Click("StatsOkButton");
					System.out.println("Clicked on stats general and ok button : FAIL");log.debug("Clicked on stats general and ok button : FAIL");
					languageReport.detailsAppendFolder("Verify stats - ok button ", "Clicked on stats general and ok button", "Clicked on stats general and ok button", "FAIL", languageCode);
				}
			}
			else
         {   cfnlib.fun_Click("StatsOkButton");
         	System.out.println("Clicked stats - winsummary after coming back from double to screen : FAIL");log.debug("Clicked stats - winsummary after coming back from double to screen : FAIL");
         	languageReport.detailsAppendFolder("Verify stats - winsummary after coming back from double to screen  ", "Clicked stats - winsummary after coming back from double to screen", "Stats - winsummary after coming back from double to screen", "FAIL", languageCode);
         }
	/*************Language Change logic:: for updating language in URL and then Refresh ****************/
	if (j + 1 != rowCount2){
		 rowData3 = list.get(j+1);
		String languageCode2 = rowData3.get(Constant.LANG_CODE).trim();

		String currentUrl = webdriver.getCurrentUrl();
		
		if(currentUrl.contains("LanguageCode"))
			urlNew= currentUrl.replaceAll("LanguageCode="+languageCode, "LanguageCode="+languageCode2);
		else if(currentUrl.contains("languagecode"))
			urlNew = currentUrl.replaceAll("languagecode="+languageCode, "languagecode="+languageCode2);
		else if(currentUrl.contains("languageCode"))
			urlNew = currentUrl.replaceAll("languageCode="+languageCode, "languageCode="+languageCode2);
		
		cfnlib.loadGame(urlNew);
		/*languageReport.detailsAppendFolder("Game Loaded ","Language : "+languageCode,"Language : "+languageCode,"PASS",""+languageCode);				
		System.out.println("Language : "+languageCode);log.debug("Language : "+languageCode);*/
		
		String error=cfnlib.XpathMap.get("Error");
		if(cfnlib.isElementPresent(error))
		{
		languageReport.detailsAppend("Verify the Language code is " +languageCode2+" ", " Application window should be displayed in " +languageDescription+"", "", "");
		languageReport.detailsAppend("Verify that any error is coming","error must not come","error is coming", "fail");
		if (j + 2 != rowCount2){
		 rowData3 = list.get(j+2);
		String languageCode3 = rowData3.get(Constant.LANG_CODE).trim();

		currentUrl = webdriver.getCurrentUrl();
		if(currentUrl.contains("LanguageCode"))
			urlNew= currentUrl.replaceAll("LanguageCode="+languageCode2, "LanguageCode="+languageCode3);
		else if(currentUrl.contains("languagecode"))
			urlNew = currentUrl.replaceAll("languagecode="+languageCode2, "languagecode="+languageCode3);
		else if(currentUrl.contains("languageCode"))
			urlNew = currentUrl.replaceAll("languageCode="+languageCode2, "languageCode="+languageCode3);
		
		cfnlib.loadGame(urlNew);
			}
				j++;
			}
		}
	} 
	}
	else
	{
		System.out.println("Unable to load the game");
		languageReport.detailsAppendFolder("Game Loaded ","Language : "+languageCode,"Language : "+languageCode,"FAIL",""+languageCode);
	}
	}}}	    
		//-------------------Handling the exception---------------------//
		catch (Exception e) 
		{
			log.error(e.getMessage(),e);
			cfnlib.evalException(e);
		}
		//-------------------Closing the connections---------------//
		finally
		{
			languageReport.endReport();
			if(!copiedFiles.isEmpty())
			{
				if(TestPropReader.getInstance().getProperty("EnvironmentName").equalsIgnoreCase("Bluemesa"))
						util.deleteBluemesaTestDataFiles(mid,cid,copiedFiles);
				else
					apiObj.deleteAxiomTestDataFiles(copiedFiles);
			}
			webdriver.close();
			webdriver.quit();
			//proxy.abort();
			Thread.sleep(1000);
		}	
	}
}
