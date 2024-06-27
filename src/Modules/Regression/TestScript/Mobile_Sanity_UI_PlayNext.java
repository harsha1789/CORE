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
public class Mobile_Sanity_UI_PlayNext {

	Logger log = Logger.getLogger(Mobile_Sanity_UI_PlayNext.class.getName());
	public ScriptParameters scriptParameters;

	public void script() throws Exception {
		String mstrTC_Name = scriptParameters.getMstrTCName();
		String mstrTC_Desc = scriptParameters.getMstrTCDesc();
		String mstrModuleName = scriptParameters.getMstrModuleName();
		BrowserMobProxyServer proxy = scriptParameters.getProxy();
		String startTime = scriptParameters.getStartTime();
		String filePath = scriptParameters.getFilePath();
		AppiumDriver<WebElement> webdriver = scriptParameters.getAppiumWebdriver();
		String DeviceName = scriptParameters.getDeviceName();
		String framework = scriptParameters.getFramework();
		String gameName = scriptParameters.getGameName();
		String osPlatform = scriptParameters.getOsPlatform();
		String osVersion = scriptParameters.getOsVersion();
		String userName = scriptParameters.getUserName();

		String Status = null;
		int mintDetailCount = 0;
		// int mintSubStepNo=0;
		int mintPassed = 0;
		int mintFailed = 0;
		int mintWarnings = 0;
		int mintStepNo = 0;
		String urlNew = null;
		ExcelDataPoolManager excelpoolmanager = new ExcelDataPoolManager();
		Mobile_HTML_Report sanityReport = new Mobile_HTML_Report(webdriver, DeviceName, filePath, startTime,
				mstrTC_Name, mstrTC_Desc, mstrModuleName, mintDetailCount, mintPassed, mintFailed, mintWarnings,
				mintStepNo, Status, gameName);

		log.info("Framework" + framework);
		MobileFunctionLibraryFactory mobileFunctionLibraryFactory = new MobileFunctionLibraryFactory();
		CFNLibrary_Mobile cfnlib = mobileFunctionLibraryFactory.getFunctionLibrary(framework, webdriver, proxy,
				sanityReport, gameName);

		cfnlib.setOsPlatform(osPlatform);
		cfnlib.setOsVersion(osVersion);

		CommonUtil util = new CommonUtil();
		RestAPILibrary apiObj = new RestAPILibrary();

		List<String> copiedFiles = new ArrayList<>();
		int mid = Integer.parseInt(TestPropReader.getInstance().getProperty("MID"));
		int cid = Integer.parseInt(TestPropReader.getInstance().getProperty("CIDMobile"));

		try {
			// Step 1
			if (webdriver != null) 
			{
				
				sanityReport.detailsAppend("Sanity Suite Test cases in  for  ", "Sanity Suite Test cases", "", "");
				
				/*
				String strFileName = TestPropReader.getInstance().getProperty("MobileSanityTestDataPath");
				File testDataFile = new File(strFileName);

				System.out.println(gameName);

				List<Map<String, String>> currencyList = util.readCurrList();// mapping

				

				String url = cfnlib.xpathMap.get("ApplicationURL");
				for (Map<String, String> currencyMap : currencyList) {

					try {

						String isoCode = currencyMap.get(Constant.ISOCODE).trim();
						String currencyID = currencyMap.get(Constant.ID).trim();
						String currencyName = currencyMap.get(Constant.ISONAME).trim();
						String languageCurrency = currencyMap.get(Constant.LANGUAGECURRENCY).trim();
						
						
						log.debug(this + " I am processing currency:  " + currencyName);
						
						
						sanityReport.detailsAppend("*** CURRENCY AND LANGUAGE ***", "Currency: "+currencyName+", Language: "+languageCurrency,"", "");
						

						sanityReport.detailsAppend("Sanity Suite Test cases in "+languageCurrency+" for "+currencyName+" ", "Sanity Suite Test cases", "", "");
						
						userName = util.randomStringgenerator();
						System.out.println(userName);
						
						if (util.copyFilesToTestServer(mid, cid, testDataFile, userName, copiedFiles, currencyID,isoCode))
						{
							Thread.sleep(3000);
							
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
							
							String launchURl = url.replaceFirst("\\busername=.*?(&|$)", "username=" + userName + "$1");

							if (launchURl.contains("LanguageCode"))
								urlNew = launchURl.replaceAll("LanguageCode=en", "LanguageCode=" + languageCurrency);
							else if (launchURl.contains("languagecode"))
								urlNew = launchURl.replaceAll("languagecode=en", "languagecode=" + languageCurrency);
							else if (launchURl.contains("languageCode"))
								urlNew = launchURl.replaceAll("languageCode=en", "languageCode=" + languageCurrency);

							log.info("url = " + urlNew);
							System.out.println(urlNew);

							webdriver.navigate().to(urlNew);
							cfnlib.waitForElement("isNFDButtonVisible");
							Thread.sleep(5000);

							cfnlib.funcFullScreen();
							Thread.sleep(3000);

							sanityReport.detailsAppendFolder("Verify if Game is launched ", "Game should be launched","Game is launched", "Pass", languageCurrency);

							// Continue Button
							if (cfnlib.xpathMap.get("NFD").equalsIgnoreCase("Yes")) 
							{
								if (cfnlib.xpathMap.get("NFDHasHook").equalsIgnoreCase("Yes")) 
								{
									cfnlib.validateNFDButton(sanityReport,languageCurrency);

								}
								// Click on continue button
								cfnlib.closeOverlayForLVC();
								Thread.sleep(1000);

								if (cfnlib.isElementVisible("isSpinBtnVisible")) {
									sanityReport.detailsAppendFolder("Verify if game is in BaseScene",
											"BaseScene should be visible", "BaseScene is visible", "Pass",
											languageCurrency);
								} else {
									sanityReport.detailsAppendFolder("Verify if game is in BaseScene",
											"BaseScene should be visible", "BaseScene is not visible", "Pass",
											languageCurrency);
								}
							}

							
							 sanityReport. detailsAppend("Following is the QuickSpin verification test case" ,"Verify QuickSpin", "", "");
							
							//Quick spin on base game
								if(cfnlib.xpathMap.get("QuickSpinOnBaseGame").equalsIgnoreCase("Yes"))
								{
									if(cfnlib.isElementVisible("isQuickSpinBGVisible"))
									{
										boolean abletoClick=cfnlib.clickOnQuickSpin();
										Thread.sleep(1000);
										if(abletoClick)
										{
											sanityReport.detailsAppendFolder("Verify if QuickSpin button is clicked", "QuickSpin button should be clicked", "QuickSpin button is clicked", "Pass",languageCurrency);									
											//cfnlib.refresh();
											//sanityReport.detailsAppend("Verify quick spin after refreshing", "Quick spin after refresh", "Quick spin after refresh", "Pass");									
										}	
										else
										{
											sanityReport.detailsAppendFolder("Verify QuickSpin buttom ", "QuickSpin button should be clicked", "QuickSpin button is not clicked", "Fail",languageCurrency);
											
										}
									}
									else
									{
										log.debug("Quick spin is not visible");
									}
								}
								

								sanityReport.detailsAppend("Following are the Autoplay verification test cases", "Verify Autoplay", "", "");
								
								//auto play menu
								if(cfnlib.isElementVisible("isAutoplayMenuBtnVisible"))
								{
									boolean isAuoplayOpen=cfnlib.openAutoplayPanel();
									Thread.sleep(2000);
									if(isAuoplayOpen)
									{
										sanityReport.detailsAppendFolder("Verify if Autoplay panel is displayed ", "Autoplay panel should be displayed", "Autoplay panel is displayed", "Pass",languageCurrency);
										log.debug("Autoplay is Visible");
										//cfnlib.refresh();
										//sanityReport.detailsAppend("Verify Autoplay panel after refreshing", "Autoplay panel after refresh", "Autoplay panel after refresh", "Pass");
										//Thread.sleep(2000);
										cfnlib.verifyAutoplayOptions(sanityReport,languageCurrency);
										Thread.sleep(2000);
										
									}
									else
									{
										sanityReport.detailsAppendFolder("Verify if Autoplay panel is displayed ", "Autoplay panel should be displayed", "Autoplay panel is not displayed", "Fail",languageCurrency);
										log.debug("Autoplay is not Visible");
									}
										
								}
								else
								{
									log.debug("Autoplay button is not visible");
								}
							//	cfnlib.closeUsingCoordinates();
								Thread.sleep(2000);
								
								sanityReport.detailsAppend("Following are the Bet value verification test cases", "Verify Bet values", "", "");	
								
								//enter if loop only when bet button is visible on base game	
								if(cfnlib.isElementVisible("IsBetButtonVisible"))
								{
									boolean isBetOpen=cfnlib.openBetPanelOnBaseScene();
									Thread.sleep(2000);
									if(isBetOpen)
									{
										sanityReport.detailsAppendFolder("Verify if Bet panel is displayed ", "Bet panel should be displayed", "Bet panel is displayed", "Pass",languageCurrency);
										//cfnlib.refresh();
										//sanityReport.detailsAppend("Verify Bet Panel after refreshing", "Bet Panel after refresh", "Bet Panel after refresh", "Pass");
										//Thread.sleep(2000);
										
										cfnlib.verifyBetSliders(sanityReport,languageCurrency);	
										
										//set max bet
										boolean isMaxBetSet=cfnlib.setMaxbetPlayNext();
										Thread.sleep(1000);
										if(isMaxBetSet)
										{
											sanityReport.detailsAppendFolder("Verify if max bet panel is set ", "Max bet should be set", "Max bet panel is set", "Pass",languageCurrency);	
										}
										else
										{
											sanityReport.detailsAppendFolder("Verify if max bet panel is set ", "Max bet should be set", "Unable to set max bet", "Fail",languageCurrency);
										}
										Thread.sleep(1000);
									}
									else
									{
										sanityReport.detailsAppendFolder("Verify if Bet panel is displayed ", "Bet panel should be displayed", "Bet panel is not displayed", "Fail",languageCurrency);
											
									}
									
								}
								else
								{
									log.debug("Bet button is not visible");
								}
								
								
								
								sanityReport.detailsAppend("Following are the MenuOption test cases", "Verify MenuOptions", "", "");
								
								// Menu Pay table Validation
								if(cfnlib.isElementVisible("isMenuBtnVisible"))
								{
									sanityReport.detailsAppendFolder("Verify menu button on base scene", "Menu Button should be visible on base scene", "Menu Button is visible on base scene", "Pass",languageCurrency);
									boolean isMenuOpen=cfnlib.openMenuPanel();
									Thread.sleep(2000);
									if(isMenuOpen)
									{
										sanityReport.detailsAppendFolder("Verify if menu panel is open ", "Menu panel should be displayed", "Menu panel is displayed", "Pass",languageCurrency);
										//cfnlib.refresh();
										//sanityReport.detailsAppend("Verify menu panel after refreshing", "Menu Panel after refresh", "Menu Panel after refresh", "Pass");
										cfnlib.verifyMenuOptions(sanityReport,languageCurrency);	
									}
											
									else
									{
										sanityReport.detailsAppendFolder("Verify if menu panel is open ", "Menu panel should be displayed", "Menu panel is not displayed", "Fail",languageCurrency);
										}
										
								}
								else
								{
									log.debug("menu is not visible");
									sanityReport.detailsAppendFolder("Verify menu button on base scene", "Menu Button should be visible on base scene", "Menu Button is not visible on base scene", "Fail",languageCurrency);
								}
								Thread.sleep(2000);
								
								sanityReport.detailsAppend("Following is the Help navigation test case", "Verify Help", "", "");
								
								//help menu
								if(cfnlib.xpathMap.get("HelpMenuPresent").equalsIgnoreCase("Yes"))
								{
									cfnlib.verifyHelpOnTopbar(sanityReport,languageCurrency);
								}
							 

						} else {
							log.debug("Unable to Copy testdata");
							sanityReport.detailsAppendFolder("unable to copy test dat to server ", " ", "", "Fail",
									languageCurrency);
						}
					} // try
					catch (Exception e) {
						log.error("Exception occur while processing currency", e);
						sanityReport.detailsAppend("Exception occur while processing currency ", " ", "", "Fail");
						cfnlib.evalException(e);
					}

				} // for loop : mapping currencies

			*/} // if: web driver

		} // closing try block
			// -------------------Handling the exception---------------------//
		catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		// -------------------Closing the connections---------------//
		finally {
			sanityReport.endReport();
			if(!copiedFiles.isEmpty())
			{
				if(TestPropReader.getInstance().getProperty("EnvironmentName").equalsIgnoreCase("Bluemesa"))
						util.deleteBluemesaTestDataFiles(mid,cid,copiedFiles);
				else
						apiObj.deleteAxiomTestDataFiles(copiedFiles);
			}
			webdriver.close();
			webdriver.quit();
			Thread.sleep(1000);
		}
		
	}

}