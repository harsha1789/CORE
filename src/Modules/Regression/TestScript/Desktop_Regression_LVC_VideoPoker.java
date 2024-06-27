package Modules.Regression.TestScript;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;

import com.zensar.automation.api.RestAPILibrary;
import com.zensar.automation.framework.model.ScriptParameters;
import com.zensar.automation.framework.report.Desktop_HTML_Report;
import com.zensar.automation.framework.utils.Constant;
import com.zensar.automation.library.CommonUtil;
import com.zensar.automation.library.TestPropReader;

import Modules.Regression.FunctionLibrary.CFNLibrary_Desktop;
import Modules.Regression.FunctionLibrary.factory.DesktopFunctionLibraryFactory;
import net.lightbody.bmp.BrowserMobProxyServer;
/**
 * 
 * 
 * This Script is for Low Value Currency - Base Game
 * ===========================================================================
 * 1.Base Game
 * ===========
 * 1.1.Validate stats all three highest wins before game play
 * 1.2.Validate credit and bet amts
 * 1.3.Validate total bet and coinssize amts from bet panel
 * 1.4.Validate payatable payouts by changing coin size 
 * 1.5.Validate the max win and min win amts 
 * 1.6.Validate the  credit , bet , win, congrats , double to, collect amts from double 
 * 1.7.Gamble limit reached 
 * 1.8.Validate stats all three highest wins after game play
 * 1.9 All the above scenarios by resizing the screen (when ever required) 
 * 1.10 Game Logo
 * 
 * 
 * 2.TestData
 * ============
 * 2.1 Max Win 
 * 2.2 Min win (Where it takes 1 to 2 iterations to reach gamble limit in double)
 * 
 * @author rk61073
 *
 */
public class Desktop_Regression_LVC_VideoPoker
{
	Logger log = Logger.getLogger(Desktop_Regression_LVC_VideoPoker.class.getName()); 
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
		String status1=null;
		int mintDetailCount=0;
		int mintPassed=0;
		int mintFailed=0;
		int mintWarnings=0;
		int mintStepNo=0;	
			
	Desktop_HTML_Report lvcReport = new Desktop_HTML_Report(webdriver,browserName,filePath,startTime,mstrTCName,mstrTCDesc,mstrModuleName,mintDetailCount,mintPassed,mintFailed,mintWarnings,mintStepNo,status1,gameName);
	DesktopFunctionLibraryFactory desktopFunctionLibraryFactory=new DesktopFunctionLibraryFactory();
	CFNLibrary_Desktop cfnlib=desktopFunctionLibraryFactory.getFunctionLibrary(framework, webdriver, proxy,lvcReport, gameName);
	CommonUtil util = new CommonUtil();
	RestAPILibrary apiObj = new RestAPILibrary();
	
	List<String> copiedFiles=new ArrayList<>();
	int mid=Integer.parseInt(TestPropReader.getInstance().getProperty("MID"));
	System.out.println("MID : " +mid);log.debug("MID : " +mid);
	int cid=Integer.parseInt(TestPropReader.getInstance().getProperty("CIDDesktop"));
	System.out.println("CID : " +cid);log.debug("CID : " +cid);
     	
	try
	{
		
	// Step 1 
	if(webdriver!=null)
	{
	//Implement code for test data copy depending on the env
	String strFileName=TestPropReader.getInstance().getProperty("LVCTestDataPath");
	File testDataFile=new File(strFileName);
	
	List<Map<String, String>> currencyList= util.readCurrList();// mapping
	
	for (Map<String, String> currencyMap:currencyList) 
	{
	userName=util.randomStringgenerator();
		
	//Step 2: To get the languages in MAP and load the language specific url
		
	String currencyID = currencyMap.get(Constant.ID).trim();
	String isoCode = currencyMap.get(Constant.ISOCODE).trim();
	log.debug(isoCode);System.out.println(isoCode);
	String CurrencyName = currencyMap.get(Constant.ISONAME).trim();
	String languageCurrency = currencyMap.get(Constant.LANGUAGECURRENCY).trim();
	String regExpr=currencyMap.get(Constant.REGEXPRESSION).trim();
	String regExprNoSymbol=currencyMap.get(Constant.REGEXPRESSION_NOSYMBOL).trim();
			
	if(	util.copyFilesToTestServer(mid, cid, testDataFile, userName, copiedFiles,currencyID,isoCode))
    System.out.println("Username : "+userName); log.debug("CurrencyName : "+CurrencyName);		
			
   if( util.migrateUser(userName))
   {
    log.debug("User Migration : PASS");
    System.out.println("User Migration : PASS");

    String balance=cfnlib.XpathMap.get("BalanceUpdationForLVC");
    Thread.sleep(60000);
    
    if(util.updateUserBalance(userName,balance))
    {
    log.debug("Balance Updated as "+balance );
    System.out.println("Balance Updated as "+balance ); 
    
    String url = cfnlib.XpathMap.get("ApplicationURL"); 
    log.debug(url);
	String launchURl = url.replaceFirst("\\busername=.*?(&|$)", "username="+userName+"$1" );
	launchURl = launchURl.replaceAll("\\blanguageCode=.*?(&|$)", "languageCode="+languageCurrency+"$1");
	System.out.println(launchURl); log.info("url = " +launchURl);

				
	if(	cfnlib.loadGame(launchURl))
	{		
		lvcReport.detailsAppendFolder("CURRENCY NAME :: "+CurrencyName,"CURRENCY ID :: "+currencyID,"ISO CODE :: "+isoCode,"PASS",CurrencyName);		
	
	if(framework.equalsIgnoreCase("CS"))
	{
		cfnlib.setNameSpace();
	}
	   Thread.sleep(2000);
									
	//wait for the draw button
	cfnlib.waitForDealButton();
	Thread.sleep(1000);	
	
	
	/*********game logo *****************/
	cfnlib.gameLogo(lvcReport, CurrencyName);
	 
	/*****************Click on paytable close button **********//*
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
		log.debug("Click on paytable close button : FAIL");
	}}
	 */
	
	/**************Stats highest wins************/
	lvcReport.detailsAppendNoScreenshot("Scenario : Verify stats highest wins before game play", "Validate all three highest wins from stats", "", "");
	cfnlib.validateStatsForLVC(lvcReport, regExprNoSymbol, CurrencyName);
	
	
	/************Credit Value Validation  	*************/	
	lvcReport.detailsAppendNoScreenshot("Scenario : Verify credit and bet","Validate credit and bet amt","","");
	boolean credits = cfnlib.verifyRegularExpression(lvcReport,regExpr,cfnlib.fun_GetText("CreditAmount"));
	boolean betAmt = cfnlib.verifyRegularExpression(lvcReport,regExpr,cfnlib.fun_GetText("BetAmount"));
	
	String creditAndBetAmts = "Credit Amount : "+ credits+" Bet Amount : "+betAmt;
	
    if(credits)
    {
    	log.debug("Validate credit and bet amt in base scene : PASS");
    	lvcReport.detailsAppendFolder("Validate credit and bet amts","Credit and bet amt in base scene ","Credit and bet amt in base scene ","PASS",""+CurrencyName);
    	cfnlib.resizeBrowser(600, 800);
    	Thread.sleep(2000);
    	lvcReport.detailsAppendFolder("Validate credit and bet amts", "Resizing the browser to 600 X 800", "Resizing the browser to 600 X 800", "PASS", CurrencyName);
    	cfnlib.resizeBrowser(400,600);
    	Thread.sleep(2000);
    	lvcReport.detailsAppendFolder("Validate credit and bet amts", "Resizing the browser to 400 X 600", "Resizing the browser to 400 X 600", "PASS", CurrencyName);
    	webdriver.manage().window().maximize();Thread.sleep(2000);
	}
	else
	{
		System.out.println("Verify credit amt in base scene : FAIL  "+creditAndBetAmts);log.debug("Verify credit amt in base scene: FAIL  "+creditAndBetAmts);
		lvcReport.detailsAppendFolder("Verify Credit and bet amts ","Credit and bet amt in base scene ",""+creditAndBetAmts,"FAIL",""+CurrencyName);
	}
	
    lvcReport.detailsAppendNoScreenshot("Scenario : Verify paytable","Validate paytable payouts by changing the coins","","");
	 /*************Paytable open  ******************/
	if(cfnlib.XpathMap.get("isPaytableButtonAvailable").equalsIgnoreCase("yes"))
	{
	if(cfnlib.paytable(lvcReport,CurrencyName))
	{
		log.debug("Clicked on paytable icon : PASS");
	}
	else
	{	
		System.out.println("Clicked on paytable icon : FAIL");log.debug("Clicked on paytable icon : FAIL");
		lvcReport.detailsAppendFolder("Verify paytable button","Click on paytable button","Click on paytable button","FAIL",""+CurrencyName);
	}}
	

	/********** paytable payouts**************/
	boolean paytablepayouts = cfnlib.paytablePayoutsByChangingTheCoinSizes(lvcReport,regExpr,CurrencyName);
	if(paytablepayouts)
	{
	     log.debug("Valiadte paytable payouts : PASS");
	}
	else
	{
		System.out.println("Validate paytable payouts : FAIL");log.debug("Validate paytable payouts : FAIL");	
		lvcReport.detailsAppendFolder("Validate paytable payouts","Paytable payouts by chaning the coin size ","Paytable Payouts by changing the coin sizes","FAIL",""+CurrencyName);
	}
	
	 /******* Set max bet ************/
	lvcReport.detailsAppendNoScreenshot("Scenario : Verify maximum bet","Set conis and coin size to max bet and validate total bet and coinsize amts ","","");
	boolean bet = cfnlib.fun_Click("BetButton");
	boolean coinsize = cfnlib.slideTheCoinSliderToMax();
	boolean coins = cfnlib.slideTheCoinsToMax();
	String maxbet = "Click on bet :  "+bet +"CoinSize slided to max : " + coinsize  + "  Coins slided to max : " + coins ;
	if( bet && coinsize && coins )
	{	
		log.debug("Set bet to maximum : PASS");
		
		boolean validateTotalBet = cfnlib.verifyRegularExpression(lvcReport,regExpr,cfnlib.fun_GetText("TotalBet"));
		boolean validateCoinSize = cfnlib.verifyRegularExpression(lvcReport,regExpr,cfnlib.fun_GetText("CoinSize"));
		
		String validateTotalBetAndCoinSize = "Total bet amount from bet pannel  : "+ credits+"  Coinsize amount from bet pannel : "+betAmt;
	
		if(validateTotalBet && validateCoinSize )
		{
		    log.debug("Validate total bet and coinsize amts from bet panel : PASS");
			lvcReport.detailsAppendFolder("Validate total bet and coinsize amts from bet panel","Total bet and coinsize amts from bet panel","Total bet and coinsize amts from bet panel","PASS",""+CurrencyName);
		}
		else
		{
			System.out.println("Validate total bet from bet panel : FAIL  "+ validateTotalBetAndCoinSize  );log.debug("Validate total bet from bet panel : FAIL  "+validateTotalBetAndCoinSize);
			lvcReport.detailsAppendFolder("Validate total bet and coinsize amts from bet panel","Total bet and coinsize amts from bet panel",""+validateTotalBetAndCoinSize,"FAIL",""+CurrencyName);
		}
		
		cfnlib.resizeBrowser(600, 800);
		Thread.sleep(2000);
		lvcReport.detailsAppendFolder("Validate total bet and coinsize amts from bet panel", "Resizing the browser to 600 X 800", "Resizing the browser to 600 X 800", "PASS", CurrencyName);
		cfnlib.resizeBrowser(400,600);
		Thread.sleep(2000);
		lvcReport.detailsAppendFolder("Validate total bet and coinsize amts from bet panel", "Resizing the browser to 400 X 600", "Resizing the browser to 400 X 600", "PASS", CurrencyName);
		webdriver.manage().window().maximize();Thread.sleep(2000);
		cfnlib.closeOverlay();
	}
	else
	{
		log.debug(maxbet);System.out.println(maxbet);
		lvcReport.detailsAppendFolder("Verify the maximum bet","Set the coins and coin size to maximum ","Set the coins and coin size to maximum ","FAIL",""+CurrencyName);
	}
	
	
	/*************deal , draw , validate the total win & collect *********/
	
	/********Validate max win with max bet **********/
	lvcReport.detailsAppendNoScreenshot("Scenario : Verify Maximum win amt with maximum bet  ", "Validate max win amt ", "", ""); 
	boolean dealbtn = cfnlib.dealBtn();
	boolean drawbtn = cfnlib.fun_Click("DrawButton"); 
	cfnlib.checkAvilabilityofElement("TotalWinAmount");
    cfnlib.fun_ClickByID("moduleContent");
	String dealAnddrawbtn = "Click on deal : "+dealbtn +"Click on draw : "+drawbtn;
	if(dealbtn && drawbtn)
	{
		log.debug("Clicked on deal and draw buttons : PASS");
		boolean totalwin = cfnlib.verifyRegularExpression(lvcReport,regExpr,cfnlib.fun_GetText("TotalWinAmount"));
		if(totalwin)
		{
			log.debug("Max Total win amount : PASS");
			lvcReport.detailsAppendFolder("Validate the maximum total win","Max win with max bet","Max win with max bet","PASS",""+CurrencyName);Thread.sleep(1000);
		}
		else
		{
			log.debug("Max total win amount : FAIL");System.out.println("Max total win amount : FAIL");
			lvcReport.detailsAppendFolder("Validate the maximum win amt","Max win with max bet","Max win with max bet","FAIL",""+CurrencyName);
		}
		
		cfnlib.resizeBrowser(600, 800);
		Thread.sleep(2000);
		lvcReport.detailsAppendFolder("Validate the maximum win amt", "Resizing the browser to 600 X 800", "Resizing the browser to 600 X 800", "PASS", CurrencyName);
		cfnlib.resizeBrowser(400,600);
		Thread.sleep(2000);
		lvcReport.detailsAppendFolder("Validate the maximum win amt", "Resizing the browser to 400 X 600", "Resizing the browser to 400 X 600", "PASS", CurrencyName);
		webdriver.manage().window().maximize();Thread.sleep(2000);	
		
		if(cfnlib.XpathMap.get("isPaytableButtonAvailable").equalsIgnoreCase("yes"))
		{
			if(cfnlib.payTableClose())
			{
				log.debug("Click on paytable close button : PASS");
			}
		else 
		{	
			System.out.println("Click on paytable close button : FAIL");log.debug("Click on paytable close button : FAIL");
		}}
	   }
	
	else 
	{
		log.debug(dealAnddrawbtn);System.out.println(dealAnddrawbtn);
		lvcReport.detailsAppendFolder("Click deal and draw","Clicked on deal and draw buttons",""+dealAnddrawbtn,"PASS",""+CurrencyName);
		
	}
	
	/********validate min win with max bet and click on double  *********/
	lvcReport.detailsAppendNoScreenshot("Scenario : Verify Minimum win amt with maximum bet ", "Validate min win amt ", "", ""); 
	boolean deal = cfnlib.dealBtn();
	boolean draw = cfnlib.drawButton();
	String dealAnddraw = "Click on deal : "+deal +"Click on draw : "+draw;
	if(deal && draw)
	{
		log.debug("Clicked on deal and draw buttons : PASS");
		boolean totalwin = cfnlib.verifyRegularExpression(lvcReport,regExpr,cfnlib.fun_GetText("TotalWinAmount"));
		if(totalwin)
		{
			log.debug("Valiadte Min Total win amount : PASS");
			lvcReport.detailsAppendFolder("Validate the minimum win","Min win with max bet","Min win with max bet","PASS",""+CurrencyName);Thread.sleep(1000);
			cfnlib.resizeBrowser(600, 800);
			Thread.sleep(2000);
			lvcReport.detailsAppendFolder("Valiadte the minimum win", "Resizing the browser to 600 X 800", "Resizing the browser to 600 X 800", "PASS", CurrencyName);
			cfnlib.resizeBrowser(400,600);
			Thread.sleep(2000);
			lvcReport.detailsAppendFolder("Valiadte the minimum win", "Resizing the browser to 400 X 600", "Resizing the browser to 400 X 600", "PASS", CurrencyName);
			webdriver.manage().window().maximize();Thread.sleep(2000);
			
			/******if paytable available*********/
			if(cfnlib.XpathMap.get("isPaytableButtonAvailable").equalsIgnoreCase("yes"))
			{
				if(cfnlib.payTableClose())
				{
					log.debug("Click on paytable close button : PASS");
				}
			else 
			{	
				System.out.println("Click on paytable close button : FAIL");log.debug("Click on paytable close button : FAIL");
			}}
		   
			
			/*******click on double button ********/
			if(cfnlib.doubleBtn())
			{
				log.debug("Clicked on double buttons : PASS");
			}
			else
			{
				log.debug("Clicked on double buttons : FAIL");System.out.println("Clicked on double buttons : FAIL");
				lvcReport.detailsAppendFolder("Verify double ","Clicked on double buttons","Clicked on double buttons","FAIL",""+CurrencyName);
			}
		}
		else
		{
			log.debug("Validate min  win amount  : FAIL");System.out.println("Validate min  win amount  : FAIL");
			lvcReport.detailsAppendFolder("Validate the minimum win","Min win with max bet","Min win with max bet","FAIL",""+CurrencyName);
		}
		
	}
	else 
	{
		log.debug(dealAnddraw);System.out.println(dealAnddraw);
		lvcReport.detailsAppendFolder("Click deal and draw","Clicked on deal and draw buttons",""+dealAnddraw,"PASS",""+CurrencyName);
		
	}
	
	
/********************** Moving to Double screen  *************************************************/
lvcReport.detailsAppendNoScreenshot("Scenario : Navigate to double and validate credit, bet and double to amts before card selection", "Validate credit, bet and double to amts before card selection", "", ""); 
	/******************* check the double  ***********************/
	if(cfnlib.checkAvilabilityofElement("DefaultDoubleCard"))	
	{
		log.debug("Moved to double screen : PASS");
		/******************* validate credit , bet and double to from header in double ***********************/
		boolean creditAmtInDouble = cfnlib.verifyRegularExpression(lvcReport,regExpr,cfnlib.fun_GetText("CreditAmountFromDoubleTo"));
		boolean betAmtInDouble = cfnlib.verifyRegularExpression(lvcReport,regExpr,cfnlib.fun_GetText("BetAmountFromDoubleTo"));
		boolean doubleToAmtInDouble = cfnlib.verifyRegularExpression(lvcReport,regExpr,cfnlib.fun_GetText("DoubleToTextFromDoubleTo"));
		
		String creditBetAndDoubleToAmt = "Credit amt in double : "+creditAmtInDouble+"  Bet amt in double : "+betAmtInDouble+"  Double To amt in double from header  : "+doubleToAmtInDouble;
		
		if(creditAmtInDouble && betAmtInDouble && doubleToAmtInDouble ) 
		{
			log.debug("Validate credit, bet and double to amts before card selection : PASS");
			lvcReport.detailsAppendFolder("Validate credit, bet and double to amts before card selection","Credit, bet and double to amts before card selection","Credit, bet and double to amts before card selection","PASS",""+CurrencyName);
		}
		else
		{
			log.debug("Verify credit amt in double : FAIL");System.out.println("Verify credit amt in double : FAIL");
			lvcReport.detailsAppendFolder("Validate credit, bet and double to amts before card selection","Credit, bet and double to amts before card selection",""+creditBetAndDoubleToAmt,"FAIL",""+CurrencyName);
			
		}
		cfnlib.resizeBrowser(600, 800);
		Thread.sleep(2000);
		lvcReport.detailsAppendFolder("Validate credit, bet and double to amts before card selection", "Resizing the browser to 600 X 800", "Resizing the browser to 600 X 800", "PASS", CurrencyName);
		cfnlib.resizeBrowser(400,600);
		Thread.sleep(2000);
		lvcReport.detailsAppendFolder("Validate credit, bet and double to amts before card selection", "Resizing the browser to 400 X 600", "Resizing the browser to 400 X 600", "PASS", CurrencyName);
		webdriver.manage().window().maximize();Thread.sleep(2000);
		
	}//closing if loop
	else
	{
		log.debug("Moved to double to screen : FAIL");System.out.println("Moved to sdouble to scrren : FAIL");
		lvcReport.detailsAppendFolder("Verify double","Moved to double to screen","Moved to double to screen","FAIL",""+CurrencyName);
	}
	
	
	
	/******************* select the card in double  ***********************/
	lvcReport.detailsAppendNoScreenshot("Scenario : Navigate to double and validate collect,double,congratulations and win amts after card selection", "Navigate to double and validate collect,double,congratulations and win amts after card selection", "", ""); 	
	if(cfnlib.fun_Click("SelectDoubleCard"))
	{
		cfnlib.checkAvilabilityofElement("TotalWinAmount");
	    cfnlib.fun_ClickByID("moduleContent");
		log.debug("Select the card , wait until the double & collect button enable in double : PASS");
		/******************* Validate collect amt,double to amt , congratulations amt and win amt  in  double  ***********************/
		boolean collectAmtInDouble = cfnlib.verifyRegularExpression(lvcReport,regExpr,cfnlib.fun_GetText("DoubleToCollectAmt"));
		boolean doubleToAmtInDouble = cfnlib.verifyRegularExpression(lvcReport,regExpr,cfnlib.fun_GetText("DoubleDoubleToAmt"));
		boolean congratulationsAmtInDouble = cfnlib.verifyRegularExpression(lvcReport,regExpr,cfnlib.fun_GetText("CongratulationsWinAmt"));
		boolean totalWinAmtInDouble = cfnlib.verifyRegularExpression(lvcReport,regExpr,cfnlib.fun_GetText("DoubleToTotalwinAmt"));
		
		String amtsInDouble = "Collect amt in double : "+collectAmtInDouble+" Double to amt in double : "+doubleToAmtInDouble+" Congrats amt in double : "+congratulationsAmtInDouble+"  Win amt in double : "+totalWinAmtInDouble;
		
		if(collectAmtInDouble && doubleToAmtInDouble && congratulationsAmtInDouble && totalWinAmtInDouble) 
		{
			log.debug("Validate collect,double to ,congratulations and win amts in double : PASS");
			lvcReport.detailsAppendFolder("Validate collect,double to,congratulations and win amts in double","Collect,double to,congratulations and win amt in double","Collect,double to,congtatulatios and win amts in double","PASS",""+CurrencyName);
		}
		else
		{
			log.debug(amtsInDouble);System.out.println(amtsInDouble);
			lvcReport.detailsAppendFolder("Validate collect,double to ,congratulations and win amts in double","Collect,double to,congratulations and win amts in double",""+amtsInDouble,"FAIL",""+CurrencyName);	
		}
		
		cfnlib.resizeBrowser(600, 800);
		Thread.sleep(2000);
		lvcReport.detailsAppendFolder("Validate collect,double to ,congratulations and win amts in double", "Resizing the browser to 600 X 800", "Resizing the browser to 600 X 800", "PASS", CurrencyName);
		cfnlib.resizeBrowser(400,600);
		Thread.sleep(2000);
		lvcReport.detailsAppendFolder("Validate collect,double to ,congratulations and win amts in double", "Resizing the browser to 400 X 600", "Resizing the browser to 400 X 600", "PASS", CurrencyName);
		webdriver.manage().window().maximize();Thread.sleep(2000);
	
		boolean doubleBtn = cfnlib.fun_Click("DoubleButton");
		if(doubleBtn) 
		{
			log.debug("Click on double btn  : PASS");
		}
		else
		{
			log.debug("Click on double btn : FAIL");System.out.println("Click on double btn : FAIL");
			lvcReport.detailsAppendFolder("Verify double button","Click on double button ","Click on double button ","FAIL",""+CurrencyName);
		}
	}//closing if loop
	else
	{
		log.debug("Select the card , wait until the double & collect button enable in double : FAIL");System.out.println("Select the card , wait until the double & collect button enable in double : FAIL");
		lvcReport.detailsAppendFolder("Verify select the card , wait until the double & collect button enable in double","Select the card , wait until the double & collect button enable in double","Select the card , wait until the double & collect button enable in double","FAIL",""+CurrencyName);	
	}
	
	/**************gamble limit reached ******************************/
	lvcReport.detailsAppendNoScreenshot("Scenario : Verify Gamble limit reached", "Gamble limit reached", "", ""); 
	cfnlib.gambleLimitReachedForLVC(lvcReport,regExpr,CurrencyName);
	
	/******************verify stats highest wins************/
	lvcReport.detailsAppendNoScreenshot("Scenario : Verify stats highest wins after game play", "Validate all three highest wins from stats", "", "");
	cfnlib.validateStatsForLVC(lvcReport, regExprNoSymbol, CurrencyName);
  
   }//closing if loop load game 
	else
	{
		System.out.println("Unable to load the game");log.debug("Unable to load the game");
		lvcReport.detailsAppendFolder("CURRENCY NAME :: "+CurrencyName,"CURRENCY ID :: "+currencyID,"ISO CODE :: "+isoCode,"FA",CurrencyName);
	 }
				
   }//closing Balance Updation
    else
	{
		System.out.println("Unable to update the balance");log.debug("Unable to update the balance");
		lvcReport.detailsAppendFolder("Unable to update the balance","Launched Currency ID is"+currencyID,"Launched ISO Code is "+isoCode,"FAIL",CurrencyName);
	 }
    } //Closing user migration  
   else
	{
		System.out.println("Unable do user migartion");log.debug("Unable do user migartion");
		lvcReport.detailsAppendFolder("Unable do user migartion","Launched Currency ID is"+currencyID,"Launched ISO Code is "+isoCode,"FAIL",CurrencyName);
	 }
	
	}//closing for loop of currency mapping 	
}//closing if loop of webdriver
else
{
	System.out.println("Unable to load the  web driver");
	lvcReport.detailsAppend("Load Game ", "Unable to load the web driver", "Unable to load the web driver", "FAIL");
 }
}//closing try block 
		
		//-------------------Handling the exception---------------------//
		catch (Exception e) 
		{
			log.error(e.getMessage(),e);
			
		}
		//-------------------Closing the connections---------------//
		finally
		{
			lvcReport.endReport();
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
		}//closing finally block	
	
}
	
		
	}
