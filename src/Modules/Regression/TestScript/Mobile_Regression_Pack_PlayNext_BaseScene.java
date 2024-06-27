package Modules.Regression.TestScript;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.zensar.automation.api.RestAPILibrary;
import com.zensar.automation.framework.model.ScriptParameters;
import com.zensar.automation.framework.report.Mobile_HTML_Report;
import com.zensar.automation.framework.utils.Constant;
import com.zensar.automation.framework.utils.ExcelDataPoolManager;
import com.zensar.automation.library.CommonUtil;
import com.zensar.automation.library.ImageLibrary;
import com.zensar.automation.library.TestPropReader;

import Modules.Regression.FunctionLibrary.CFNLibrary_Desktop;
import Modules.Regression.FunctionLibrary.CFNLibrary_Mobile;
import Modules.Regression.FunctionLibrary.factory.DesktopFunctionLibraryFactory;
import Modules.Regression.FunctionLibrary.factory.MobileFunctionLibraryFactory;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import net.lightbody.bmp.BrowserMobProxyServer;
/**
 * 
 * @author pb61055
 *
 */
public class Mobile_Regression_Pack_PlayNext_BaseScene {
	
		Logger log = Logger.getLogger(Mobile_Regression_Pack_PlayNext_BaseScene.class.getName()); 
		public ScriptParameters scriptParameters;
		public void script() throws Exception
		{
			String mstrTC_Name=scriptParameters.getMstrTCName();
			String mstrTC_Desc=scriptParameters.getMstrTCDesc();
			String mstrModuleName=scriptParameters.getMstrModuleName();
			BrowserMobProxyServer proxy=scriptParameters.getProxy();
			String startTime=scriptParameters.getStartTime();
			String filePath=scriptParameters.getFilePath();
			AppiumDriver<WebElement> webdriver=scriptParameters.getAppiumWebdriver();
			String DeviceName=scriptParameters.getDeviceName();
			String framework=scriptParameters.getFramework();
			String gameName=scriptParameters.getGameName();
			String osPlatform=scriptParameters.getOsPlatform();
			String osVersion=scriptParameters.getOsVersion();
			String userName=scriptParameters.getUserName();


			String Status=null;
			int mintDetailCount=0;
			//int mintSubStepNo=0;
			int mintPassed=0;
			int mintFailed=0;
			int mintWarnings=0;
			int mintStepNo=0;
			
			ExcelDataPoolManager excelpoolmanager= new ExcelDataPoolManager();
			Mobile_HTML_Report report=new Mobile_HTML_Report(webdriver,DeviceName,filePath,startTime,mstrTC_Name,mstrTC_Desc,mstrModuleName,mintDetailCount,mintPassed,mintFailed,mintWarnings,mintStepNo,Status,gameName);

			log.info("Framework"+framework);
			MobileFunctionLibraryFactory mobileFunctionLibraryFactory=new MobileFunctionLibraryFactory();
			CFNLibrary_Mobile cfnlib=mobileFunctionLibraryFactory.getFunctionLibrary(framework, webdriver, proxy, report, gameName);
				
			cfnlib.setOsPlatform(osPlatform);
			cfnlib.setOsVersion(osVersion);
			
			CommonUtil util = new CommonUtil();
			RestAPILibrary apiObj = new RestAPILibrary();
		
			List<String> copiedFiles=new ArrayList<>();
			int mid=Integer.parseInt(TestPropReader.getInstance().getProperty("MID"));
			int cid=Integer.parseInt(TestPropReader.getInstance().getProperty("CIDMobile"));
			
			
			//getting the orientation via image library , to get the screenshot path
			//ImageLibrary imageLibrary = new ImageLibrary(webdriver, gameName,"Mobile");			
			//imageLibrary.setScreenOrientation(webdriver.getOrientation().toString().toLowerCase());
			
			

			try {
				// -------Getting webdriver of proper test site-------//
				if (webdriver != null) 
				{
				//./Mobile_Regression_CurrencySymbol.testdata
				String strFileName=TestPropReader.getInstance().getProperty("MobileCurrencyTestDataPath");
				File testDataFile=new File(strFileName);
				
				String testDataExcelPath=TestPropReader.getInstance().getProperty("TestData_Excel_Path");		
				boolean isGameLoaded = false;
	           
				
				List<Map<String, String>> currencyList= util.readCurrList();// mapping

				String url = cfnlib.xpathMap.get("ApplicationURL");
				log.info("url = " + url);
				
				for (Map<String, String> currencyMap:currencyList) 
				{
			
					try
					{
						
						String isoCode = currencyMap.get(Constant.ISOCODE).trim();
						String currencyID = currencyMap.get(Constant.ID).trim();
						String currencyName = currencyMap.get(Constant.ISONAME).trim();
						String languageCurrency = currencyMap.get(Constant.LANGUAGECURRENCY).trim();
						String currencyFormat=currencyMap.get(Constant.DISPALYFORMAT).trim();
						String regExpr = currencyMap.get(Constant.REGEXPRESSION).trim();
						String regExprNoSymbol = currencyMap.get(Constant.REGEXPRESSION_NOSYMBOL).trim();


						log.debug(this+" I am processing cuurency:  "+languageCurrency);
						
						
						report.detailsAppend("*** CURRENCY AND LANGUAGE ***", "Currency: "+currencyName+", Language: "+languageCurrency,"", "");
						

						report.detailsAppend("Regression pack Test cases in "+languageCurrency+" for "+currencyName+" ", "Regression pack Test cases", "", "");
					  	userName=util.randomStringgenerator();
						

							System.out.println(userName);
							
							if(	util.copyFilesToTestServer(mid, cid, testDataFile, userName, copiedFiles,currencyID,isoCode))
							{
							
								log.debug("Updating the balance");
								String balance="10000000";
								
								
								
								if(cfnlib.xpathMap.get("LVC").equalsIgnoreCase("Yes")) 
								{
									util.migrateUser(userName);
									
									log.debug("Able to migrate user");
									System.out.println("Able to migrate user");
									
									log.debug("Updating the balance");
									balance="700000000000";
									Thread.sleep(60000);
								
								}
							
							
								util.updateUserBalance(userName,balance);
								Thread.sleep(3000);
						
						
						String LaunchURl = url.replaceFirst("\\busername=.*?(&|$)", "username="+userName+"$1");
						
						System.out.println(LaunchURl);
						log.info("LaunchURl = " + LaunchURl);
						webdriver.navigate().to(LaunchURl);
						
						cfnlib.waitForElement("isNFDButtonVisible");
						Thread.sleep(1000);			
								
						cfnlib.funcFullScreen();
						Thread.sleep(1000);
						
						
						
						report.detailsAppendFolder("Verify Game is launched ", "Game should be launched", "Game is launched", "Pass",languageCurrency);
						
						
						
							// Continue Button
							if(cfnlib.xpathMap.get("NFD").equalsIgnoreCase("Yes"))
							{
								if(cfnlib.xpathMap.get("NFDHasHook").equalsIgnoreCase("Yes"))
								{
									cfnlib.validateNFDButton(report,languageCurrency);
									
								}
								 //Click on continue button
								cfnlib.closeOverlayForLVC();
								Thread.sleep(1000);
									
									
								if(cfnlib.isElementVisible("isSpinBtnVisible"))
								{
									report.detailsAppendFolder("Verify if game is in base Scene", "Base Scene should be visible", "Base Scene is visible", "Pass",languageCurrency);
								}
								else
								{
									report.detailsAppendFolder("Verify if game is in base Scene", "Base Scene should be visible", "Base Scene is not visible", "Pass",languageCurrency);
								}
							}
											
							// Gets the Credit Amt && verifies Currency Format and check the currency format

							report.detailsAppend("Following is the Credit value verification test case","Verify Credit value", "", "");
							
							log.debug("Base Game Credit Value : "+cfnlib.funcGetText("Creditvalue"));
							
							boolean credits = cfnlib.verifyRegularExpressionPlayNext(report, regExpr,
									cfnlib.funcGetText("Creditvalue"),isoCode);
							
							if (credits) {
								System.out.println("Base Game Credit Value : Pass");
								log.debug("Base Game Credit Value : Pass");
								report.detailsAppendFolder("Verify the currency format in credit ","Credit should display in correct currency format " ,
										"Credit is displaying in correct currency format ", "Pass",languageCurrency);
						
												
							} else {
								System.out.println("Base Game Credit Value : Fail");
								log.debug("Base Game Credit Value : Fail");
								report.detailsAppendFolder("Verify the currency format in credit ","Credit should display in correct currency format " ,
										"Credit is displaying in incorrect currency format ", "Fail",languageCurrency);
							}

							// Gets the Bet Amt && verifies Currency Format and check the currency format

							report.detailsAppend("Following is the Bet value verification test case","Verify Bet value", "", "");
							log.debug("Base Game Credit Value : "+cfnlib.funcGetText("BetTextValue"));
							boolean betAmt = cfnlib.verifyRegularExpressionPlayNext(report, regExpr,
									cfnlib.funcGetText("BetTextValue"),isoCode);

							if (betAmt) {
								log.debug("Base Game Bet Value : Pass");
								report.detailsAppendFolder("Verify the currency format for Bet ","Bet should display in correct currency format " ,
										"Bet is displaying in correct currency format ", "Pass",languageCurrency);
						
												
							} else {
								log.debug("Base Game Bet Value : Fail");
								report.detailsAppendFolder("Verify the currency format for Bet ","Bet should display in correct currency format " ,
										"Bet is displaying in incorrect currency format ", "Fail",languageCurrency);
								}

							
							
							
		
							try {
								// Lets check the Quick bets
								

								if (cfnlib.isElementVisible("IsBetButtonVisible")) 
								{
									boolean isBetOpen=cfnlib.openBetPanelOnBaseScene();
									{
										Thread.sleep(2000);
										if(isBetOpen)
										{
											report.detailsAppend("Verify Bet panel is displayed ", "Bet panel is displayed", "Bet panel is displayed", "Pass");
											
											cfnlib.verifyBetSliders(report,languageCurrency);
											
											report.detailsAppend("Following are the Quick Bet value verification test case","Verify Quick Bet value", "", "");
											
											cfnlib.verifyQuickbetOptionsRegExp(report, languageCurrency, regExpr,regExprNoSymbol,isoCode);
										}
										else
										{
											report.detailsAppend("Verify Bet panel is displayed ", "Bet panel is displayed", "Bet panel is displayed", "Fail");
											
										}
									}
								}
							} catch (Exception e) {
								log.error(e.getMessage(), e);
							}
						
						
							report.detailsAppend("Following are the Paytable test cases", "Verify Paytable payouts", "", "");		
							try {
								if (cfnlib.isElementVisible("isMenuBtnVisible")) {

									// click on menu
									Thread.sleep(2000);
									boolean isMenuOpen=cfnlib.openMenuPanel();
									Thread.sleep(2000);
									if(isMenuOpen)

									{

										System.out.println("Paytable Open : Pass");
										log.debug("Paytable Open : Pass");
										
										
										boolean scrollPaytable = cfnlib.verifyPaytableScroll(report,languageCurrency);
										if (scrollPaytable) 
										{
											//report.detailsAppendFolder("Verify if able to scroll paytable", "Paytable should be scrolled towards the end","Able to scroll paytable", "Pass",languageCurrency);
											
											boolean scatterAndWildPayouts = cfnlib.validatePayoutsFromPaytable(report,languageCurrency, regExpr);
											
											boolean symbolPayouts=cfnlib.verifyGridPayouts(report,regExpr,languageCurrency,isoCode);
											
											if (scatterAndWildPayouts&&symbolPayouts) 
											{
												report.detailsAppendNoScreenshot(
														"Verify Paytable payout currency format for selected bet value with the game currency format",
														"Paytable payout verification with the game currency format ",
														"Paytbale payout verification  with the game currency format is done and is correct",
														"Pass");
												log.debug("Paytable currency format: Pass");
												
											} else {
												report.detailsAppendNoScreenshot(
														"Verify Paytable payout currency format for selected bet value with the game currency format",
														"Paytable payout verification with the game currency format ",
														"Paytable payout verification with the game currency format is done but failed coz some formats are not matched",
														"Fail");
												log.debug("Paytable currency format: Fail");
											}

										} 
										else 
										{
											System.out.println("Paytable Open : Fail");
											log.debug("Paytable Open : Fail");
											report.detailsAppendFolder("Verify if able to scroll paytable", "Paytable should be scrolled towards the end","Unable to scroll paytable", "Fail",languageCurrency);
										}
										// close paytable
										cfnlib.paytableClose();
										Thread.sleep(2000);									

									} else {
										
										report.detailsAppendFolder("Verify if able to menu is diplayed", "Menu shoould be displayed","Menu is not displayed", "Fail",languageCurrency);
										
									}
								}
							} catch (Exception e) {
								log.error(e.getMessage(), e);
								cfnlib.evalException(e);
							}
							
							if(cfnlib.xpathMap.get("isClosingRequired").equalsIgnoreCase("Yes"))
							{
								cfnlib.closeUsingCoordinates();
								Thread.sleep(1000);
								
							}
						
							report.detailsAppend("Following are the win test cases", "Verify win in base scene", "", "");
							
							
							//To verify normal win in base game
							try
							{
								if (cfnlib.isElementVisible("isSpinBtnVisible")) 
								{
									cfnlib.spinclick();							
									Thread.sleep(4000);	
							
									boolean winFormatVerification = cfnlib.verifyRegularExpressionPlayNext(report, regExpr,cfnlib.getCurrentWinAmt(report, languageCurrency),isoCode);
									if (winFormatVerification) 
									{
										report.detailsAppendFolder("verify win amount currency format in baseScene", "Win amount should be in correct currency format","Win amount is in correct currency format", "Pass",languageCurrency);
										log.debug("Base Game Win Value : Pass");
										System.out.println("Base Game Win Value : Pass");
												
									}
									else 
									{
										report.detailsAppendFolder("verify win amount in baseScene", "Win amount should be in correct currency format","Win amount is in incorrect currency format", "Fail",languageCurrency);
										log.debug("Base Game Win Value : Fail");
										System.out.println("Base Game Win Value : Fail");
												
									}
								}
							} catch (Exception e) {
								log.error(e.getMessage(), e);
							}
							
							
							//To verify big win in base game
							try {
								if (cfnlib.isElementVisible("isSpinBtnVisible")) 
								{
									cfnlib.spinclick();		
									Thread.sleep(8000);
									
									// Verifies the Big Win currency format
									boolean bigWinFormatVerification = cfnlib.verifyRegularExpressionPlayNext(report,regExpr, cfnlib.verifyBigWin(report, languageCurrency),isoCode);
									if (bigWinFormatVerification) 
									{
										report.detailsAppendFolder("verify bigwin amount currency format in baseScene", "BigWin amount should be in correct currency format","BigWin amount is in correct currency format", "Pass",languageCurrency);
										log.debug("Base Game BigWin Value : Pass");
										System.out.println("Base Game BigWin Value : Pass");
										
									} else 
									{
										report.detailsAppendFolder("verify bigwin amount currency format in baseScene", "BigWin amount should be in correct currency format","BigWin amount is in incorrect currency format", "Fail",languageCurrency);
										log.debug("Base Game BigWin Value : Fail");
										System.out.println("Base Game BigWin Value : Fail");
										
									}

								}
							} catch (Exception e) {
								log.error(e.getMessage(), e);
							}

						
							if(cfnlib.xpathMap.get("isBonusPresent").equalsIgnoreCase("Yes"))
							{
							//To verify Amazing win in base game
							try {
								if(cfnlib.xpathMap.get("isBonusWinInBaseScene").equalsIgnoreCase("Yes"))
								{
								if (cfnlib.isElementVisible("isSpinBtnVisible")) 
								{
									cfnlib.spinclick();	
									Thread.sleep(8000);
									
									// method is used to get the Win amt and check the currency format

									boolean winVerification = cfnlib.verifyRegularExpressionPlayNext(report, regExpr,cfnlib.verifyBonusWin(report, languageCurrency),isoCode);
									if (winVerification) 
									{
										report.detailsAppendFolder("verify Bonus win amount currency format in baseScene", "Bonus win amount should be in correct currency format","Bonus win amount is in correct currency format", "Pass",languageCurrency);
										System.out.println("Base Game bonue Win Value : Pass");
										log.debug("Base Game Win Value : Pass");
										
									} else 
									{
										report.detailsAppendFolder("verify Bonus win amount currency format in baseScene", "Bonus win amount should be in correct currency format","Bonus win amount is in incorrect currency format", "Fail",languageCurrency);
										System.out.println("Base Game bonus Win Value : Fail");
										log.debug("Base Game bonus Win Value : Fail");
										
									}
								
								} else 
								{
									report.detailsAppendFolder("verify BaseGame Bonus win","Spin Button is not working", "Spin Button is not working", "Fail",languageCurrency);

								}
								}
							} catch (Exception e) {
								log.error(e.getMessage(), e);
								cfnlib.evalException(e);
							}
							}						
							
							report.detailsAppend("Following are the Freespin test cases", "Verify Freespin scene", "", "");	
							
							if (TestPropReader.getInstance().getProperty("IsFreeSpinAvailable").equalsIgnoreCase("yes")) 
							{
								
								cfnlib.spinclick();
								Thread.sleep(8000);
								report.detailsAppendFolder("Verify if freespins are triggered in baseScene", "FreeSpins should be triggered","FreeSpins are triggered", "Pass", languageCurrency);
								
								Thread.sleep(15000);
								
								
								if(cfnlib.xpathMap.get("tapToContinueFreespinsAvailable").equalsIgnoreCase("Yes"))
								{
									cfnlib.waitForElement("tapToContinueFreespins");
									
									if(cfnlib.xpathMap.get("isTapToContinueEnabled").equalsIgnoreCase("Yes"))
									{
										cfnlib.clickOnButtonUsingCoordinates("tapToContinueFreespinsX","tapToContinueFreespinsY");
										cfnlib.clickOnButtonUsingCoordinates("tapToContinueFreespinsX","tapToContinueFreespinsY");
										cfnlib.clickOnButtonUsingCoordinates("tapToContinueFreespinsX","tapToContinueFreespinsY");
										
									}
									else
									{
										cfnlib.closeOverlayForLVC();
									}
									
								}
								
								
								//report.detailsAppendFolder("Verify if game is in freespins", "FreeSpins should be displayed","FreeSpins screen is displayed", "Pass", languageCurrency);
								
								
								Thread.sleep(6000);
								// Free spins  1St Spins to get correct Win
								boolean winFormatVerificationINFS = cfnlib.verifyRegularExpressionPlayNext(report,regExpr, cfnlib.verifyFSCurrentWinAmt(report, languageCurrency),isoCode);
								if (winFormatVerificationINFS) 
								{
									report.detailsAppendFolder("verify win amount currency format in FreeSpins", "Win amount should be in correct currency format","Win amount is in correct currency format", "Pass",languageCurrency);									
									System.out.println("Freespins Win Value : Pass");
									log.debug("Freespins Win Value : Pass");
								} else 
								{
									report.detailsAppendFolder("verify win amount currency format in FreeSpins", "Win amount should be in correct currency format","Win amount is in incorrect currency format", "Fail",languageCurrency);									
									
									System.out.println("Freespins  Win Value : Fail");
									log.debug("Freespins Win Value : Fail");
									
								}
							
							
								
								// method is used to get the current big win and check the currency format
								boolean bigWinFormatVerificationINFS = cfnlib.verifyRegularExpressionPlayNext(report,regExpr, cfnlib.verifyFreeSpinBigWin(report, languageCurrency),isoCode);
								if (bigWinFormatVerificationINFS) 
								{
									report.detailsAppendFolder("verify bigwin amount currency format in FreeSpins", "BigWin amount should be in correct currency format","BigWin amount is in correct currency format", "Pass",languageCurrency);									
									
									System.out.println("Free Spins BigWin Value : Pass");
									log.debug("Free Spins BigWin Value : Pass");
								
								} else 
								{
									report.detailsAppendFolder("verify bigwin amount currency format in FreeSpins", "BigWin amount should be in correct currency format","BigWin amount is in correct currency format", "Fail",languageCurrency);									
									
									System.out.println("Free Spins BigWin Value : Fail");
									log.debug("Free Spins BigWin Value : Fail");
								}
								
								
								
								if(cfnlib.xpathMap.get("isBonusPresent").equalsIgnoreCase("Yes"))
								{
									
								boolean amzWinFormatVerificationINFS = cfnlib.verifyRegularExpressionPlayNext(report,regExpr, cfnlib.verifyBonusWinInFS(report, languageCurrency),isoCode);
								if (amzWinFormatVerificationINFS) 
								{
									report.detailsAppendFolder("verify bonus win amount currency format in FreeSpins", "Bonus win amount should be in correct currency format","Bonus win amount is in correct currency format", "Pass",languageCurrency);									
									
									System.out.println("Free Spins Amazing Value : Pass");
									log.debug("Free Spins Amazing Value : Pass");
									
								} else 
								{
									report.detailsAppendFolder("verify bonus win amount currency format in FreeSpins", "Bonus win amount should be in correct currency format","Bonus win amount is in incorrect currency format", "Fail",languageCurrency);									
									
									System.out.println("Free Spins BigWin Value : Fail");
									log.debug("Free Spins BigWin Value : Fail");
									
								}
								}
								
								
								
								String cred =cfnlib.funcGetText("FSBalanceText");
								System.out.println("Credit Value : "+cred);
									// method is used to get the current credit and check the currency format
									boolean freeSpinsCredits = cfnlib.verifyRegularExpressionPlayNext(report, regExpr,cfnlib.funcGetText("FSBalanceText"),isoCode);
									if (freeSpinsCredits) 
									{
										
										report.detailsAppendFolder("Verify FreeSpins Credits currency format", "Credits should be in correct currecny format", "Credits are in correct currency format", "Pass",languageCurrency);
									} else {
										
										report.detailsAppendFolder("Verify FreeSpins Credits currency format", "Credits should be in correct currecny format", "Credits are in incorrect currency format", "Fail",languageCurrency);
										}
								
								
								
								
								// method is used to get the current total win and check the currency format
								  String totalFs =cfnlib.funcGetText("getTotalWinInFS");
	                                System.out.println("Free Spins Total win value : Pass" +totalFs);
								boolean freeSpinsTotalWin = cfnlib.verifyRegularExpressionPlayNext(report, regExpr,cfnlib.funcGetText("getTotalWinInFS"),isoCode);
								if (freeSpinsTotalWin) 
								{
									report.detailsAppendFolder("Verify FreeSpins Total win currency format", "Total win should be in correct currecny format", "Total win is in correct currency format", "Pass",languageCurrency);
									
									System.out.println("Free Spins Total win Value : Pass");
									log.debug("Free Spins Total win Value : Pass");
									
								} else
								{
									System.out.println("Free Spins Win Value : Fail");
									log.debug("Free Spins Win Value : Fail");
									report.detailsAppendFolder("Verify FreeSpins Total win currency format", "Total win should be in correct currecny format", "Total win is in incorrect currency format", "Fail",languageCurrency);
									}
								
								Thread.sleep(30000);
								
								// method is used to verify free spins summary screen and check the currency format
								
								if (cfnlib.waitForElement("FSSummaryScreen")) 
								{
									boolean fsSummaryScreen = cfnlib.verifyRegularExpressionPlayNext(report,
											regExpr,cfnlib.funcGetText("FSSummaryScreenWinAmt"), isoCode);
									if (fsSummaryScreen) 
									{
										report.detailsAppendFolder("Verify FreeSpins summary screen currency format", "Summary screen should be in correct currecny format", "Summary screen is in correct currency format", "Pass",languageCurrency);
										
										System.out.println("Free Spins Summary Screen : Pass");
										log.debug("Free Spins Summary Screen : Pass");
									
									} else {
										report.detailsAppendFolder("Verify FreeSpins summary screen currency format", "Summary screen should be in correct currecny format", "Summary screen is in incorrect currency format", "Fail",languageCurrency);
										
										System.out.println("Free Spins Summary Screen : Fail");
										log.debug("Free Spins Summary Screen : Fail");
										
									}
								} 
								
								
								// Closing playnext Summary Screen
								// method is used to verify free spins summary screen clicked and Basegame opened					
								
								if (cfnlib.waitForElement("FSSummaryScreen"))
								{
									// click on spin button
									cfnlib.closeOverlayForLVC();
									Thread.sleep(5000);
									if (cfnlib.isElementVisible("isSpinBtnVisible")) 
									{
										log.debug("Back to base scene");
										boolean creditsAfterAllwin = cfnlib.verifyRegularExpressionPlayNext(report, regExpr,
												cfnlib.funcGetText("Creditvalue"),isoCode);
										
										if (creditsAfterAllwin) {
											System.out.println("Base Game Credit Value : Pass");
											log.debug("Base Game Credit Value : Pass");
											report.detailsAppendFolder("Verify the currency format in credit after all features win ","Credit should display in correct currency format " ,
													"Credit is displaying in correct currency format ", "Pass",languageCurrency);
									
															
										} else {
											System.out.println("Base Game Credit Value : Fail");
											log.debug("Base Game Credit Value : Fail");
											report.detailsAppendFolder("Verify the currency format in credit after all features win","Credit should display in correct currency format " ,
													"Credit is displaying in incorrect currency format ", "Fail",languageCurrency);
										}
									} else

									{
										log.debug("Unable to go to base scene");
									}
								} // Closing free spins Summary Screen
					     		} // Closing free spins
							
						
						}//test data
						 else
							{
								log.debug("Unable to Copy testdata");
								report.detailsAppendFolder("unable to copy test dat to server ", " ", "", "Fail", languageCurrency);

							}
					}// try
						catch ( Exception e) 
						{
							log.error("Exception occur while processing currency",e);
							report.detailsAppend("Exception occur while processing currency ", " ", "", "Fail");
							cfnlib.evalException(e);
						}
							
						}// for loop : mapping currencies 
				}// if: web driver
			}//try
					
			//-------------------Handling the exception---------------------//
		catch (Exception e) 
		{
			log.error(e.getMessage(),e);	
		}
		//-------------------Closing the connections---------------//
		finally
		{
			report.endReport();
			webdriver.close();
			webdriver.quit();
			//proxy.abort();
			Thread.sleep(1000);
		}//closing finally block	
		
	}	

}
