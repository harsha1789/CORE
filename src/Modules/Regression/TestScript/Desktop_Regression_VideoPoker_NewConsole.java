package Modules.Regression.TestScript;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;

import com.zensar.automation.api.RestAPILibrary;
import com.zensar.automation.framework.model.ScriptParameters;
import com.zensar.automation.framework.report.Desktop_HTML_Report;
import com.zensar.automation.framework.utils.Constant;
import com.zensar.automation.library.CommonUtil;
import com.zensar.automation.library.TestPropReader;

import Modules.Regression.FunctionLibrary.CFNLibrary_Desktop;
import Modules.Regression.FunctionLibrary.factory.DesktopFunctionLibraryFactory;
import net.lightbody.bmp.BrowserMobProxyServer;

public class Desktop_Regression_VideoPoker_NewConsole
{
	Logger log = Logger.getLogger(Desktop_Regression_VideoPoker_NewConsole.class.getName()); 
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
		String languageCode=null;
		int mintDetailCount=0;
		int mintPassed=0;
		int mintFailed=0;
		int mintWarnings=0;
		int mintStepNo=0;	
			
	Desktop_HTML_Report report = new Desktop_HTML_Report(webdriver,browserName,filePath,startTime,mstrTCName,mstrTCDesc,mstrModuleName,mintDetailCount,mintPassed,mintFailed,mintWarnings,mintStepNo,status1,gameName);
	DesktopFunctionLibraryFactory desktopFunctionLibraryFactory=new DesktopFunctionLibraryFactory();
	CFNLibrary_Desktop cfnlib=desktopFunctionLibraryFactory.getFunctionLibrary(framework, webdriver, proxy,report, gameName);
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
	String strFileName=TestPropReader.getInstance().getProperty("RegressionTestDataPath");
	System.out.println("TD : "+strFileName);log.debug("TD : "+strFileName);	
	File testDataFile=new File(strFileName);
	
	userName=util.randomStringgenerator();System.out.println("UserName : "+userName);log.debug("UserName : "+userName);	
	if(	util.copyFilesToTestServer(mid, cid, testDataFile, userName, copiedFiles)) 
	{
		String url = cfnlib.XpathMap.get("ApplicationURL"); 
	    System.out.println(url);
		String launchURl = url.replaceFirst("\\busername=.*?(&|$)", "username="+userName+"$1" );
		System.out.println(launchURl); log.info("url = " +launchURl);
				
	if(	cfnlib.loadGame(launchURl))
	{			
	if(framework.equalsIgnoreCase("CS"))
	{
		cfnlib.setNameSpace();
	}
	   Thread.sleep(2000);
					
	//wait for the draw button
	cfnlib.waitForSpinButton();
	Thread.sleep(3000);	
	
	
	/*String getsessiontime = cfnlib.getConsoleText(cfnlib.XpathMap.get("SessionTime")); 
	System.out.println(getsessiontime);
	
	String held = cfnlib.getConsoleText(cfnlib.XpathMap.get("HeldCard")); 
	System.out.println(held);*/
	
	/**********verify stats symbol********/
	cfnlib.verifyStatsOkButton(report,languageCode);
	
	
	/*********Refresh the game ************/
  	report.detailsAppendNoScreenshot("Scenario : Following is validate the Refresh ","On Refresh validate all the buttons availability","","");
  	if(cfnlib.refreshTheGame(report,languageCode))
  	{
  		System.out.println("Refresh : PASS");log.debug("Refresh : PASS");
  		
  		if(cfnlib.newConsoleButtonsAreEnabledOnRefresh(report,languageCode))
  		{
  			System.out.println("newConsoleButtonsAreEnabledOnRefresh: PASS");log.debug("newConsoleButtonsAreEnabledOnRefresh : PASS");
  		}
  		else
  		{
  			System.out.println("newConsoleButtonsAreEnabledOnRefresh: FAIL");log.debug("newConsoleButtonsAreEnabledOnRefresh : FAIL");
  		}
  	}
  	else
  	{
  		System.out.println("Refresh : FAIL");log.debug("Refresh : FAIL");
  		report.detailsAppendFolder("Refresh","Clicked on Refresh","Clicked on Refresh","FAIL",""+languageCode);
  	}
  

   /*************No win  with held strategy ******************/
  report.detailsAppendNoScreenshot("Scenario : Following is to validate no win  ","Click on Deal , Draw and validate the buttons availability ","","");
if(cfnlib.fun_Click("DealButton"))
{
	log.debug("DealButton : PASS");
	report.detailsAppendFolder("Verify Deal Button ","Clicked on Deal","Clicked on Deal","PASS",""+languageCode);
	
	if(cfnlib.newConsoleButtonsAreDisabled(report,languageCode))
	{
		System.out.println("newConsoleButtonsAreDisabled : PASS");log.debug("newConsoleButtonsAreDisabled : PASS");
	}
	else 
	{
		System.out.println("newConsoleButtonsAreDisabled : FAIL");log.debug("newConsoleButtonsAreDisabled : FAIL");
	}
	
	//cfnlib.verifyHeldCardsAndClick();
	
	// click on draw 	
	if(cfnlib.fun_Click("DrawButton"))
	{
		System.out.println("DrawButton : PASS");log.debug("DrawButton: PASS");
		report.detailsAppendFolder("Draw","Clicked on Draw","Clicked on Draw","PASS",""+languageCode);
		
		if(cfnlib.newConsoleButtonsAreEnabled(report,languageCode))
    	{
    		System.out.println("newConsoleButtonsAreEnabled : PASS");log.debug("newConsoleButtonsAreEnabled : PASS");
    	}
    	else 
    	{
    		System.out.println("newConsoleButtonsAreEnabled : FAIL");log.debug("newConsoleButtonsAreEnabled : FAIL");
    	} 	
	}
	else
	{
		System.out.println("DealButton : FAIL");log.debug("DealButton : FAIL");
		report.detailsAppendFolder("Deal","Clicked on Deal","Bet Amount","FAIL",""+languageCode);
		
	}
}
else
{
	System.out.println("DrawButton : FAIL");log.debug("DrawButton : FAIL");
	report.detailsAppendFolder("Draw","Clicked on Draw","Clicked on Draw","FAIL",""+languageCode);
	
}

 /*******************Max Win in base game *****************************************/
	report.detailsAppendNoScreenshot("Scenario: Following is to validate the maximum win in Base game ","Set the bet to max to get the maximum win in the base game and validate the buttons availability","","");
if(cfnlib.setMaxBet(report,languageCode))
{
	System.out.println("setMaxBet : PASS");log.debug("setMaxBet : PASS");
	
		
 if(cfnlib.fun_Click("DealButton"))
 {
	log.debug("DealButton : PASS");
		
		if(cfnlib.fun_Click("DrawButton"))
	   {
			log.debug("DrawButton : PASS");
			
			if(cfnlib.checkAvilabilityofElement("TotalWinAmount"))
			{
				if(cfnlib.fun_GetText("TotalWinAmount") != null)
				{
					String totalwin = cfnlib.fun_GetText("TotalWinAmount");
			        System.out.println("TotalWinAmount : PASS");log.debug("TotalWinAmount : PASS");
			        report.detailsAppendFolder("Verify the Total Win Amount","Get the total win amount from the base game","Total Win Text is : "+totalwin,"PASS",""+languageCode);
			        if(cfnlib.XpathMap.get("isPaytableButtonAvailable").equalsIgnoreCase("yes"))
			        {  cfnlib.checkAvilabilityofElement("IsDealButtonEnabled");
			        	//cfnlib.checkAvilabilityofElement("PaytableClose");
			           cfnlib.fun_Click("PaytableClose"); Thread.sleep(2000);
			        }
			        if(cfnlib.newConsoleButtonsAreEnabled(report,languageCode))
			    	{
			    		System.out.println("newConsoleButtonsAreEnabled : PASS");log.debug("newConsoleButtonsAreEnabled : PASS");
			    	}
			    	else 
			    	{
			    		System.out.println("newConsoleButtonsAreEnabled : FAIL");log.debug("newConsoleButtonsAreEnabled : FAIL");
			    	} 
			        
				}//closing total win 
			   else
		        {
		             System.out.println("TotalBet : FAIL");log.debug("TotalBet : FAIL");
		             report.detailsAppendFolder("Total Win","Total Win Text","Total Win Text","FAIL",""+languageCode);
		        }
			}//closing draw button 
	}
		else 
		{
			System.out.println("DrawButton : FAIL");log.debug("DrawButton : FAIL");
			report.detailsAppendFolder("DrawButton","Clicked on Draw","Clicked on Draw","FAIL",""+languageCode);
		}
 }//closing deal button 
	 else 
	 {
		System.out.println(" click on DealButton : FAIL");log.debug(" click on DealButton : FAIL");
		report.detailsAppendFolder("DealButton","Set bet to maximun and click on DealButton","Set bet to maximun and click on DealButton","FAIL",""+languageCode);
 }
}//closing set max bet	 
	 else
	 {
		System.out.println("Set bet to maximun : FAIL");log.debug("Set bet to maximun  : FAIL");
		report.detailsAppendFolder("DealButton","Set bet to maximun and click on DealButton","Set bet to maximun and click on DealButton","FAIL",""+languageCode);
	 }


	/************** Coins Minus- Validating the Bet Amt , Payouts , Coins Text from Base and Bet Panel after clicking on  Coins minus button ,  Coinsize updation in base and in bet panel and validates all the new console buttons*************************/
	  report.detailsAppendNoScreenshot("Scenario : Following is to validate the coins Minus Button ","Validates the bet amt , payouts, coins from bet panel by clicking on coins minus button and validate the buttons availability ","","");
	  if(cfnlib.coinsMinusButtonValidation(report,languageCode))
	  {
		  System.out.println("coinsMinusButton : PASS");log.debug("coinsMinusButton : PASS");
		  
		  if(cfnlib.newConsoleButtonsAreEnabled(report,languageCode))
	    	{
	    		System.out.println("newConsoleButtonsAreEnabled : PASS");log.debug("newConsoleButtonsAreEnabled : PASS");
	    	}
	    	else 
	    	{
	    		System.out.println("newConsoleButtonsAreEnabled : FAIL");log.debug("newConsoleButtonsAreEnabled : FAIL");log.debug("isBetMaxButtonAvailableAndEnabled : FAIL");
	    	} 
	  }
	  else
	  {
		  System.out.println("coinsMinusButton : FAIL");log.debug("coinsMinusButton : FAIL");
	  }
		
	 /******** Coins Plus - Validating the Bet Amt , Payouts , Coins Text from Base and Bet Panel after clicking on  Coins plus button ,  Coinsize updation in base and in bet panel and validates all the new console buttons  ***********************/
	  report.detailsAppendNoScreenshot("Scenario : Following is to validate the coins Plus Button ","Validates the bet amt , payouts, coins from bet panel by clicking on coins plus button and validate the buttons availability ","","");
	  if(cfnlib.coinsPlusButtonValidation(report,languageCode))
	  {
		  System.out.println("coinsPlusButtonValidation : PASS");log.debug("coinsPlusButtonValidation : PASS");
		  
		  if(cfnlib.newConsoleButtonsAreEnabled(report,languageCode))
	    	{
	    		System.out.println("newConsoleButtonsAreEnabled : PASS");log.debug("newConsoleButtonsAreEnabled : PASS");
	    	}
	    	else 
	    	{
	    		System.out.println("newConsoleButtonsAreEnabled : FAIL");log.debug("newConsoleButtonsAreEnabled : FAIL");log.debug("isBetMaxButtonAvailableAndEnabled : FAIL");
	    	}
	  }
	  else
	  {
		  System.out.println("coinsPlusButtonValidation : FAIL");log.debug("coinsPlusButtonValidation : FAIL");
	  }
	  
	/*****Paytable - Validating the change in COINS but , not in coinsize in base and in bet panel by clicking on paytable in loop and validate the new console buttons are enabled are not ***********/
	  	report.detailsAppendNoScreenshot("Verify Payouts from Paytable","Verify the bet amount , coins from bet panel and base game and paytable payouts and validate the buttons availability","","");
	  if(cfnlib.payoutsValidationByChangingCoins(report,languageCode))
	  {
		  System.out.println("payoutsValidationByChangingCoins : PASS");log.debug("payoutsValidationByChangingCoins : PASS");
		  
		  if(cfnlib.newConsoleButtonsAreEnabled(report,languageCode))
	    	{
	    		System.out.println("newConsoleButtonsAreEnabled : PASS");log.debug("newConsoleButtonsAreEnabled : PASS");
	    	}
	    	else 
	    	{
	    		System.out.println("newConsoleButtonsAreEnabled : FAIL");log.debug("isBetMaxButtonAvailableAndEnabled : FAIL");
	    	} 
	  }
	  else
	  {
		  System.out.println("payoutsValidationByChangingCoins : FAIL");log.debug("payoutsValidationByChangingCoins : FAIL");
	  }
	  
	/**********Coinsize(BackWord Button) - Validate Bet Amt, Payouts and coinsize from bet panel and from base by clicking on BetMinus button  *************************/
	  report.detailsAppendNoScreenshot("Scenario : Verify the Coinsize Backword Button","Verify the bet amt , payouts, coinsize from bet panel and from base game and validate the buttons availability","","");
	  if(cfnlib.betMinusButtonValidation(report, languageCode))
	  {
		  System.out.println("betMinusButtonValidation : PASS");log.debug("betMinusButtonValidation : PASS");
		  
		  if(cfnlib.newConsoleButtonsAreEnabled(report,languageCode))
	    	{
	    		System.out.println("newConsoleButtonsAreEnabled : PASS");log.debug("newConsoleButtonsAreEnabled : PASS");
	    	}
	    	else 
	    	{
	    		System.out.println("newConsoleButtonsAreEnabled : FAIL");log.debug("newConsoleButtonsAreEnabled : FAIL");
	    	} 
		  		
	  }
	  else
	  {
		  System.out.println("betMinusButtonValidation : FAIL");log.debug("betMinusButtonValidation : FAIL");
	  }

	  /**********Coinsize(Forward Button) - Validate Bet Amt, Payouts and coinsize from bet panel and from base by clicking on BetMinus button  *************************/
	  report.detailsAppendNoScreenshot("Scenario : Verify the Coinsize Forward Button","Verify the bet amt , payouts, coinsize from bet panel and from base game and validate the buttons availability","","");
	  if(cfnlib.betPlusButtonValidation(report, languageCode))															
	  {
		  System.out.println("betPlusButtonValidation : PASS");log.debug("betPlusButtonValidation : PASS");
		  
		  if(cfnlib.newConsoleButtonsAreEnabled(report,languageCode))
	    	{
	    		System.out.println("newConsoleButtonsAreEnabled : PASS");log.debug("newConsoleButtonsAreEnabled : PASS");
	    	}
	    	else 
	    	{
	    		System.out.println("newConsoleButtonsAreEnabled : FAIL");log.debug("newConsoleButtonsAreEnabled : FAIL");
	    	} 
	  }
	  else
	  {
		  System.out.println("betPlusButtonValidation : FAIL");log.debug("betPlusButtonValidation : FAIL");
	  }

	/**** BetPlusOne Button - Validate the change in Bet Amt , Payouts and coins from base and bet panel by clicking on bet plus one button ********************/
	  report.detailsAppendNoScreenshot("Scenario : Following is to validate BetPlusOne button","Verifies the bet amt , payouts, coins from bet panel and from the base game and validate the buttons availability","","");
	  if(cfnlib.betPlusOneButtonValidation(report, languageCode))
	  {
	 	  System.out.println("betPlusOneButtonValidation : PASS");log.debug("betPlusOneButtonValidation : PASS");
	 	  
	 	 if(cfnlib.newConsoleButtonsAreEnabled(report,languageCode))
	    	{
	    		System.out.println("newConsoleButtonsAreEnabled : PASS");log.debug("newConsoleButtonsAreEnabled : PASS");
	    	}
	    	else 
	    	{
	    		System.out.println("newConsoleButtonsAreEnabled : FAIL");log.debug("newConsoleButtonsAreEnabled : FAIL");
	    	} 
	  }
	  else
	  {
	 	  System.out.println("betPlusOneButtonValidation : FAIL");log.debug("betPlusOneButtonValidation : FAIL");
	  }
	  
	/******** validate BetAmt , Payouts and Coins from base and betPanel by clicking on BetMaxButton**********/
	  report.detailsAppendNoScreenshot("Scenario : Verifies the BetMax button","Verify the bet amt , payouts, coins from bet panel by clicking on BetMax button","","");
	  cfnlib.betMaxButtonValidation(report, languageCode );
	
	  
	 
	/********************************
	 ********************** Regression 
	 ***********************/
	
	 report.detailsAppendNoScreenshot("Scenario : Verify Clock ","Verify clock and get the text ","","");
 	// Validate the clock and get text 
 	 if(cfnlib.clockfromTopBar(report, languageCode ) != null)
 	 {
 		 System.out.println(" clockfromTopBar : PASS");log.debug("Clock : PASS"); 
 	 }
 	 else
 	 {
 		 System.out.println("clockfromTopBar : FAIL");log.debug("Clock : FAIL");  
 	 }Thread.sleep(2000);
 	 
 	report.detailsAppendNoScreenshot("Scenario : Verify Hamburger Menu Options ","Verify Hamburger Menu, Home , Banking and Settings and there navigations","","");
 	// Validate the hamburgerMenu and its navigations 
 	 cfnlib.hamburgerMenu(report, languageCode );
 	 Thread.sleep(2000);
 	 
 	report.detailsAppendNoScreenshot("Scenario : Verify Help  ","Verify Help and its navigation","","");
 	 //Validate the help and its navigation 
 	 if(cfnlib.helpFromTopBarMenu(report, languageCode ))
 	 {
 		 System.out.println("Help Navigation : PASS");log.debug("Help Navigation : PASS");  
 	 }
 	 else
     {
 		 System.out.println("Help Navigation : FAIL");log.debug("Help Navigation : FAIL");  
 	 }
 	 Thread.sleep(3000);
 		
 	report.detailsAppendNoScreenshot("Scenario : Verify Quick Deal Enable and Disable","Verify Quick Deal by  enabiling and disabiling","","");
 	 // Quick Deal enable and Disable 
 	if(cfnlib.isQuickDealButtonAvailable(report, languageCode ))
 	{
 		System.out.println("Quick Deal : PASS");log.debug("Quick Deal : PASS");
 	}
 	else
 	{	
 		System.out.println("Quick Deal : FAIL");log.debug("Quick Deal : FAIL");
 	}Thread.sleep(2000);
 	
 	
 	 // Set min bet , max bet & validations  
 	for(int i=1;i<=2;i++)
 	{
 	if(i==1)
 	{ report.detailsAppendNoScreenshot("Scenario : Following is to set the Bet as Maximum","Verifies all the functionality checks with maximum bet","","");
 	System.out.println("***Maximun Bet***");log.debug("***Maximun Bet***");
 	if( cfnlib.setMaxBetAndValidate(report, languageCode ))//setMinBet
 	{
 		System.out.println("Maximun Bet : PASS");log.debug("Maximun Bet : PASS");
 	}
 	else
 	{
 		System.out.println("Maximun Bet : FAIL");log.debug("Maximun Bet : FAIL");
 	}
 	
 	
 	}//closing the minimum bet 
 			
 	else
 	{	System.out.println("***Minium Bet***");log.debug("***Minium Bet***");
 	report.detailsAppendNoScreenshot("Scenario : Following is to set the Bet as Minimum","Verifies all the functionality checks with minimum bet","","");
 	if( cfnlib.setMinBetAndValidate(report,languageCode))//setMaxBet
 	{
 	System.out.println("Minium Bet : PASS");log.debug("Minium Bet : PASS");
 	}
 	else
 	{	
 		System.out.println("Minium Bet : FAIL");log.debug("Minium Bet : FAIL");
 	}
 	}//closing the maximum bet 
 	
 	
 	 // Paytable open  
 	if(cfnlib.XpathMap.get("isPaytableButtonAvailable").equalsIgnoreCase("yes"))
 	{
 	if(cfnlib.paytable(report,languageCode))
 	{
 		System.out.println("Paytable Open: PASS");log.debug("Paytable Open: PASS");
 	}
 	else
 	{	
 		System.out.println("Paytable Open: FAIL");log.debug("Paytable Open: FAIL");
 	}}
 	
  
 	//For Payouts without paytable button 
 	
 	boolean paytablepayouts = cfnlib.paytablePayoutsByChangingTheCoinSizes(report,languageCode);
 	if(paytablepayouts)
 	{
 	  System.out.println("Paytable Payouts : PASS");log.debug("Paytable Payouts : PASS");
 	 report.detailsAppendFolder("Verify Paytable Payouts","Paytable Payouts by changing the coin sizes","Paytable Payouts by changing the coin sizes : "+paytablepayouts,"PASS",""+languageCode);
 	}
 	else
 	{
 		System.out.println("Paytable Payouts : FAIL");log.debug("Paytable Payouts : FAIL");	
 		report.detailsAppendFolder("Verify Paytable Payouts","Paytable Payouts by changing the coin sizes","Paytable Payouts by changing the coin sizes : "+paytablepayouts,"FAIL",""+languageCode);
 	}
 	
 	// Credit Value Validation  				
 	boolean creditAmt  = cfnlib.fun_GetText("CreditAmount") != null;
 	boolean betAmt = cfnlib.fun_GetText("BetAmount") != null;
 	
 	String creditAndBetAmt = "Credit Amt : "+ creditAmt +" Bet Amt : "+betAmt ;
 	
     if(creditAmt && betAmt)
     {
     	System.out.println("Credit and Bet  Value : PASS");log.debug("Credit and Bet  Value : PASS");
     	report.detailsAppendFolder("Verify Credit and Bet Amt ","Credit and Bet Amt ","Credit and Bet Amt : " +creditAndBetAmt,"PASS",""+languageCode);
 	}
 	else
 	{
 		System.out.println("Credit and Bet  Value : PASS");log.debug("Credit and Bet  Value : PASS");
 		report.detailsAppendFolder("Verify Credit and Bet Amt ","Credit and Bet Amt ","Credit and Bet Amt : " +creditAndBetAmt,"FAIL",""+languageCode);
 	}
 	
     
 	// Click Deal, Draw , Collect the Amount & validate the total win 	
 	for(int j=0;j<=2;j++)
 	{
 	if( cfnlib.clickOnDealDrawAndValidateTotalWin( report , languageCode))
 	{
 		System.out.println("Deal Draw and Validate Total Win Amount : PASS");log.debug("Deal Draw and Validate Total Win Amount : PASS");
 	}
 	else
 	{
 		System.out.println("Deal Draw and Validate Total Win Amount : FAIL");log.debug("Deal Draw and Validate Total Win Amount : FAIL");
 	}Thread.sleep(2000);
 		
 	//Scenario 1 : Base Game : Collect the Amount 	
 	if(j==0)
 	{ 
 	System.out.println("*** Scenario 1 : Base Game : Collect the Amount  ***");
    boolean collect =	cfnlib.fun_Click("CollectButton");
 	if(collect)
 	{
 		System.out.println("CollectButton : PASS");log.debug("CollectButton : PASS");
 		report.detailsAppendFolder("Verify Collect Button ","Collect Button ","Collect Button : "+collect ,"PASS",""+languageCode);
 	}
 	else
 	{
 		System.out.println("CollectButton : FAIL");log.debug("CollectButton : FAIL");
 		report.detailsAppendFolder("Verify Collect Button ","Collect Button ","Collect Button : "+collect ,"FAIL",""+languageCode);
 	}}
 	
 	// Scenario 2 : Base Game : Double the Amount 
 	else if(j==1)
 	{
 	System.out.println("***  Scenario 2 : Base Game : Double the Amount  ***");
 	boolean clickOnDoubleTheAmt = cfnlib.fun_Click("DoubleButton");
 	if(clickOnDoubleTheAmt)
 	{
 		System.out.println("clickOnDoubleTheAmt : PASS");log.debug("clickOnDoubleTheAmt : PASS");
 		report.detailsAppendFolder("Verify Double-To Button ","Double-To Button ","Double-To Button : "+clickOnDoubleTheAmt ,"PASS",""+languageCode);
 	}
 	else
 	{
 		System.out.println("clickOnDoubleTheAmt : FAIL");log.debug("clickOnDoubleTheAmt : FAIL");
 		report.detailsAppendFolder("Verify Double-To Button ","Double-To Button ","Double-To Button : "+clickOnDoubleTheAmt ,"FAIL",""+languageCode);
 	}Thread.sleep(2000);
 				
 	if(cfnlib.checkAvilabilityofElement("DoubleFeature"))
 	{	
 		
 		// Credit Amt in Double to &  its validation 	

 	/*************** Double To  **********************/
 	boolean doubleCreditAmt = cfnlib.fun_GetText("CreditAmount") != null;
 	boolean doubleBetAmt =cfnlib.fun_GetText("BetAmount") != null;
 	boolean selectCardInDoubleTo =cfnlib.fun_Click("SelectDoubleCard");
 	Thread.sleep(6000);
 	boolean congratsText = cfnlib.fun_GetText("CongratulationsWinAmt") != null;
 	boolean doubleToTotalWin =cfnlib.fun_GetText("TotalWinAmount") != null;
 	Thread.sleep(2000);
 	
   String SlectcardInDoubleTo = "Double To - Credit Amt : "+doubleCreditAmt + "  Double To - Bet Amt : "+doubleBetAmt+"  Select Card in Double To  : "+selectCardInDoubleTo + "  Congrats Text : "+congratsText + "  TotalWin Text in Double-To : "+doubleToTotalWin;
 	
 	if(doubleCreditAmt && doubleBetAmt && selectCardInDoubleTo && congratsText && doubleToTotalWin )
 	{
 		System.out.println("Double To Feature : PASS");log.debug("Double To Feature : PASS");
 		report.detailsAppendFolder("Verify Double To Feature  ","Verifies Double To - Credit , Bet Amt , Card , Congrats Text and Tota Win ",""+SlectcardInDoubleTo,"PASS",""+languageCode);
 	}
 	else
 	{
 		System.out.println("Double To Feature : FAIL");log.debug("Double To Feature : FAIL");
 		report.detailsAppendFolder("Verify Double To Feature ","Verifies Double To - Credit , Bet Amt , Card , Congrats Text and Tota Win ",""+SlectcardInDoubleTo,"FAIL",""+languageCode);
 	}
 	
 	
 	if(cfnlib.checkAvilabilityofElement("CollectButton"))
 	{
 	// Collect  Amt in Double to &  its validation 
 	boolean doubleCollectAmt =cfnlib.fun_GetText("DoubleToCollectAmt") != null;
 	boolean doubleDoubleAmt =cfnlib.fun_GetText("DoubleDoubleToAmt") != null;
 	Thread.sleep(2000);
 	boolean collectButton =cfnlib.fun_Click("CollectButton");
 	Thread.sleep(2000);
 	//boolean backToBaseGameBtn =cfnlib.fun_Click("BackToBaseGame");
 	
 	String collectAndDoubleAmt = "Collect Amt in Double To : "+doubleCollectAmt +" Double Amt in Double To : "+doubleDoubleAmt +"  Click on collect botton in double to feature  : "+ collectButton ;
 	if(doubleCollectAmt && doubleDoubleAmt && collectButton )
 	{
	 	System.out.println("Verify Double to feature by collect button, Collect the Amt and Back to Base Game : PASS");log.debug("Verify Collect & Double Amt, Collect the Amt and Back to Base Game : PASS");
	 	report.detailsAppendFolder("Verifies the Collect button in Double to feature","Verifies the Collect & Double Amt, Collect the Amt and Back to Base Game",""+collectAndDoubleAmt ,"PASS",""+languageCode);
 	}
 	else
 	{
 		System.out.println("Verify Collect & Double Amt, Collect the Amt and Back to Base Game : FAIL");log.debug("Verify Collect & Double Amt, Collect the Amt and Back to Base Game: FAIL");
 		report.detailsAppendFolder("Verifies the Collect button in Double to feature","Verifies the Collect & Double Amt, Collect the Amt and Back to Base Game",""+collectAndDoubleAmt ,"FAIL",""+languageCode);
 	}Thread.sleep(2000);
   }
 	}//closing the #check Availability of double to screen
 }//closing else if 
 		
 	// Scenario 3 : Double To  : Gamble Limit Reached  
 	
 	else
 	{System.out.println("***  Scenario 3 : Double To  : Gamble Limit Reached    ***");
	if(cfnlib.checkAvilabilityofElement("IsCollectButtonEnabled"))
 	{	
 	boolean clickOnDoubleTheAmt = cfnlib.fun_Click("DoubleButton");
 	if(clickOnDoubleTheAmt)
 	{
 		System.out.println("Double the Amount : PASS");log.debug("Double the Amount : PASS");
 		report.detailsAppendFolder("Verify Double Button in Double to feature ","Double Button in Double to feature ","Double Button in Double to feature  : "+clickOnDoubleTheAmt,"PASS",""+languageCode);
 	}
 	else
 	{
 		System.out.println("Double the Amount : FAIL");log.debug("Double the Amount : FAIL");
 		report.detailsAppendFolder("Verify Double Button in Double to feature ","Double Button in Double to feature ","Double Button in Double to feature  : "+clickOnDoubleTheAmt,"FAIL",""+languageCode);
 	}Thread.sleep(2000);
 	
 	// gamble limit reached 
 	cfnlib.doubleTheAmountToDouble(report,languageCode);
 	
 	if(cfnlib.newConsoleButtonsAreEnabledOnBacktoBaseGameFromDoubleToScreen(report,languageCode))
	{
		System.out.println("newConsoleButtonsAreEnabledOnBacktoBaseGameFromDoubleToScreen : PASS");log.debug("newConsoleButtonsAreEnabledOnBacktoBaseGameFromDoubleToScreen : PASS");
	}
	else 
	{
		System.out.println("newConsoleButtonsAreEnabledOnBacktoBaseGameFromDoubleToScreen : FAIL");log.debug("newConsoleButtonsAreEnabledOnBacktoBaseGameFromDoubleToScreen : FAIL");
	} 
 	
// 	String sesionEndTime = cfnlib.func_GetText("StatsSessionTime");
//	System.out.println("Session Start time"+sesionStartTime);
//	//String sessionTime = (sesionEndTime) - (sesionStartTime) ; 
	
 	} 
	else
 	{
		report.detailsAppendFolder("Verifies the gamble limit reached in Double to feature","gamble limit reached in Double to feature","gamble limit : fail","FAIL",""+languageCode);}//closing else block
 	}
     }//closing for loop for deal draw & collect 
   }//closing for loop		
 	
 	/**********verify stats symbol********/
	//cfnlib.verifyStatsOkButton(report,languageCode);
	cfnlib.verifyStatsResetButton(report,languageCode);
	//cfnlib.verifyStatsOkButton(report,languageCode);
	
   }//closing if loop load game 
	else
	{
		System.out.println("Unable to load the game");log.debug("Unable to load the game");
		
	 }
				
	}//closing copying files to test server
	
	
		
}//closing if loop of webdriver
else
{
	System.out.println("Unable to load the  web driver");
report.detailsAppend("Load Game ", "Unable to load the web driver", "Unable to load the web driver", "FAIL");
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
			report.endReport();
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
