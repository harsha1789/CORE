package Modules.Regression.TestScript;

import static org.testng.Assert.assertEquals;

import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;

import com.zensar.automation.framework.model.ScriptParameters;
import com.zensar.automation.framework.report.Desktop_HTML_Report;
import com.zensar.automation.framework.utils.Constant;
import com.zensar.automation.framework.utils.ExcelDataPoolManager;
import com.zensar.automation.framework.utils.Util;
import com.zensar.automation.library.CommonUtil;
import com.zensar.automation.library.ImageLibrary;
import com.zensar.automation.library.TestPropReader;

import Modules.Regression.FunctionLibrary.CFNLibrary_Desktop;
import Modules.Regression.FunctionLibrary.factory.DesktopFunctionLibraryFactory;
import net.lightbody.bmp.BrowserMobProxyServer;

/**
 * This script is used to verify language translations
 * 
 * @author pb61055
 *
 */
public class Desktop_LanguageTranslation {

	Logger log = Logger.getLogger(Desktop_LanguageTranslation.class.getName());
	public ScriptParameters scriptParameters;

	public void script() throws Exception {

		String mstrTCName = scriptParameters.getMstrTCName();
		String mstrTCDesc = scriptParameters.getMstrTCDesc();
		String mstrModuleName = scriptParameters.getMstrModuleName();
		WebDriver webdriver = scriptParameters.getDriver();
		BrowserMobProxyServer proxy = scriptParameters.getProxy();
		String startTime = scriptParameters.getStartTime();
		String filePath = scriptParameters.getFilePath();
		String userName = scriptParameters.getUserName();
		String browserName = scriptParameters.getBrowserName();
		String framework = scriptParameters.getFramework();
		String gameName = scriptParameters.getGameName();
		String status = null;
		int mintDetailCount = 0;
		int mintPassed = 0;
		int mintFailed = 0;
		int mintWarnings = 0;
		int mintStepNo = 0;
		int startindex = 0;
		String strGameName = null;
		String urlNew = null;

		ExcelDataPoolManager excelpoolmanager = new ExcelDataPoolManager();
		Desktop_HTML_Report report = new Desktop_HTML_Report(webdriver, browserName, filePath, startTime, mstrTCName,
				mstrTCDesc, mstrModuleName, mintDetailCount, mintPassed, mintFailed, mintWarnings, mintStepNo, status,
				gameName);
		DesktopFunctionLibraryFactory desktopFunctionLibraryFactory = new DesktopFunctionLibraryFactory();
		CFNLibrary_Desktop cfnlib = desktopFunctionLibraryFactory.getFunctionLibrary(framework, webdriver, proxy,
				report, gameName);

		CommonUtil util = new CommonUtil();
		try {
			// Step 1
			if (webdriver != null) {
								
				
				if (gameName.contains("Desktop")) {
					java.util.regex.Pattern str = java.util.regex.Pattern.compile("Desktop");
					Matcher substing = str.matcher(gameName);
					while (substing.find()) {
						startindex = substing.start();
					}
					strGameName = gameName.substring(0, startindex);
					log.debug("newgamename=" + strGameName);
				} else {
					strGameName = gameName;
				}

				ImageLibrary imageLibrary = new ImageLibrary(webdriver, strGameName, "Desktop");

				List<Map<String, String>> langList = ((Util) util).readLangTransList();

				for (Map<String, String> LanguageMap : langList) {
					try {

						String languageDescription = LanguageMap.get(Constant.LANGUAGE).trim();
						String languageCode = LanguageMap.get(Constant.LANG_CODE).trim();
						String Credits = LanguageMap.get(Constant.Credits).trim();
						String Bet = LanguageMap.get(Constant.Bet).trim();
						String TotalBet = LanguageMap.get(Constant.TotalBet).trim();
						String Banking = LanguageMap.get(Constant.Banking).trim();
						String Settings = LanguageMap.get(Constant.Settings).trim();
						String Sounds = LanguageMap.get(Constant.Sounds).trim();
						String CoinSize = LanguageMap.get(Constant.CoinSize).trim();

						
						report.detailsAppend("*** LANGUAGE TRANSLATION  ***", " Language: " + languageDescription,"","");
						
						String url = cfnlib.XpathMap.get("ApplicationURL");

						//userName = "user200";
						userName ="Zen_5a7psq2";

						String launchURl = url.replaceFirst("\\busername=.*?(&|$)", "username=" + userName + "$1");

						if (launchURl.contains("LanguageCode"))
							urlNew = launchURl.replaceAll("LanguageCode=en", "LanguageCode=" + languageCode);
						else if (launchURl.contains("languagecode"))
							urlNew = launchURl.replaceAll("languagecode=en", "languagecode=" + languageCode);
						else if (launchURl.contains("languageCode"))
							urlNew = launchURl.replaceAll("languageCode=en", "languageCode=" + languageCode);

						log.info("url = " + urlNew);
						System.out.println(urlNew);
						System.out.println(languageCode);

						webdriver.navigate().to(urlNew);
						Thread.sleep(5000);
						
						/*String gameSettingsText = "return " + cfnlib.XpathMap.get("getCreditsText");
						
						String consoleSettings = cfnlib.getConsoleText(gameSettingsText);
						
						System.out.println("Excel text --"+Settings);
						System.out.println("Console text-- "+consoleSettings);
						
						System.out.println(Settings.equals(consoleSettings));
						
						
						String text=imageLibrary.getText("Credits");
						System.out.println(text);
					*/
						

						report.detailsAppendFolder("Verify language translations in baseScene",
								"Translations should be in " + languageDescription + " correctly", "", "Pass",
								languageCode);
						try {
							if (cfnlib.isElementVisible("NFDButton")) {
								/*cfnlib.ClickByCoordinates("return " + cfnlib.XpathMap.get("ClicktoContinueCoordinatex"),
										"return " + cfnlib.XpathMap.get("ClicktoContinueCoordinatey"));*/
								imageLibrary.click("NFDButton");

							}
							
							Thread.sleep(1000);
							
							boolean isCreditCorrect = cfnlib.compareText(Credits, "getCreditsText","yes");
							if (isCreditCorrect) {
								System.out.println("Correct translation for credits text");
								log.debug("Correct translation for credits text");
								report.detailsAppendNoScreenshot("Verify credits text translation",
										"Credits text should display correctly in " + languageDescription,
										"Credits is correct", "Pass");
							} else {
								System.out.println("Incorrect translation for credits text");
								log.debug("Incorrect translation for credits text");
								report.detailsAppendNoScreenshot("Verify credits text translation",
										"Credits text should display correctly in " + languageDescription,
										"Credits is Incorrect", "Fail");

							}
							Thread.sleep(1000);
							boolean isBetCorrect = cfnlib.compareText(Bet, "getBetText","yes");
							if (isBetCorrect) {
								System.out.println("Correct translation for bet text");
								log.debug("Correct translation for bet text");
								report.detailsAppendNoScreenshot("Verify Bet text translation",
										"Bet text should display correctly in " + languageDescription, "Bet is correct",
										"Pass");

							} else {
								System.out.println("Incorrect translation for bet text");
								log.debug("Incorrect translation for bet text");
								report.detailsAppendNoScreenshot("Verify Bet text translation",
										"Bet text should display correctly in " + languageDescription,
										"Bet is Incorrect", "Fail");
							}

						} catch (Exception e) {
							log.error(e.getMessage(), e);
						}

						report.detailsAppendFolder("Verify language translations in Bet panale",
								"Translations should be in " + languageDescription + " correctly", "", "Pass",
								languageCode);

						try {
							if (cfnlib.isElementVisible("IsBetButtonVisible")) 
							{
								/*cfnlib.ClickByCoordinates("return " + cfnlib.XpathMap.get("BetMenuCoordinatex"),
										"return " + cfnlib.XpathMap.get("BetMenuCoordinatey"));*/
								imageLibrary.click("BetButton");

							}
							
							Thread.sleep(2000);
							boolean isTotalBetCorrect = cfnlib.compareText(TotalBet, "getTotalBetText","yes");
							if (isTotalBetCorrect) {
								System.out.println("Correct translation for Total bet");
								log.debug("Correct translation for Total bet");
								report.detailsAppendNoScreenshot("Verify Total bet text translation",
										"Total bet text should display correctly in " + languageDescription,
										"Total bet is correct", "Pass");
							} else {
								System.out.println("Incorrect translation for Total bet");
								log.debug("Incorrect translation for Total bet");
								report.detailsAppendNoScreenshot("Verify Total bet text translation",
										"Total bet text should display correctly in " + languageDescription,
										"Total bet is Incorrect", "Fail");
							}
							Thread.sleep(1000);
							if(cfnlib.XpathMap.get("CoinSizeSliderPresent").equalsIgnoreCase("Yes")) 
							{
								boolean isCoinSizeCorrect = cfnlib.compareText(CoinSize, "getCoinSizeText","yes");
								if (isCoinSizeCorrect) {
									System.out.println("Correct translation for CoinSize");
									log.debug("Correct translation for CoinSize");
									report.detailsAppendNoScreenshot("Verify CoinSize text translation",
											"CoinSize text should display correctly in " + languageDescription,
											"CoinSize is correct", "Pass");
								} else {
									System.out.println("Incorrect translation for CoinSize");
									log.debug("Incorrect translation for CoinSize");
									report.detailsAppendNoScreenshot("Verify CoinSize text translation",
											"CoinSize text should display correctly in " + languageDescription,
											"CoinSize is Incorrect", "Fail");
								}
							}
						} catch (Exception e) {
							log.error(e.getMessage(), e);

						}
						Thread.sleep(2000);
						report.detailsAppendFolder("Verify language translations in Menu panale",
								"Translations should be in " + languageDescription + " correctly", "", "Pass",
								languageCode);

						try {
							if (cfnlib.isElementVisible("isMenuBtnVisible")) {
								/*cfnlib.ClickByCoordinates("return " + cfnlib.XpathMap.get("MenuCoordinatex"),
										"return " + cfnlib.XpathMap.get("MenuCoordinatey"));*/
								imageLibrary.click("Menu");
							}
							
							Thread.sleep(2000);
							boolean isSettingsCorrect = cfnlib.compareText(Settings, "getSettingsText","yes");
							if (isSettingsCorrect) {
								System.out.println("Correct translation for Settinsg text");
								log.debug("Correct translation for Settinsg text");
								report.detailsAppendNoScreenshot("Verify Settings text translation",
										"Settings text should display correctly in " + languageDescription,
										"Settings is correct", "Pass");
							} else {
								System.out.println("Incorrect translation for Settinsg text");
								log.debug("Incorrect translation for Settinsg text");
								report.detailsAppendNoScreenshot("Verify Settings text translation",
										"Settings text should display correctly in " + languageDescription,
										"Settings is Incorrect", "Fail");
							}
							Thread.sleep(2000);
							boolean isBankingCorrect = cfnlib.compareText(Banking, "getBankingText","yes");
							if (isBankingCorrect) {
								System.out.println("Correct translation for Banking text");
								log.debug("Correct translation for Banking text");
								report.detailsAppendNoScreenshot("Verify Banking text translation",
										"Banking text should display correctly in " + languageDescription,
										"Banking is correct", "Pass");
							} else {
								System.out.println("Incorrect translation for Banking text");
								log.debug("Incorrect translation for Banking text");
								report.detailsAppendNoScreenshot("Verify Banking text translation",
										"Banking text should display correctly in " + languageDescription,
										"Banking is Incorrect", "Fail");
							}

						} catch (Exception e) {
							log.error(e.getMessage(), e);

						}Thread.sleep(2000);

						report.detailsAppendFolder("Verify language translations in Settings panale",
								"Translations should be in " + languageDescription + " correctly", "", "Pass",
								languageCode);

						try {
							if (cfnlib.isElementVisible("isSettingsBtnVisible")) {
								/*cfnlib.ClickByCoordinates("return " + cfnlib.XpathMap.get("SettingsCoordinatex"),
										"return " + cfnlib.XpathMap.get("SettingsCoordinatey"));*/
								
							}
							
							Thread.sleep(2000);
							boolean isSoundCorrect = cfnlib.compareText(Sounds, "getSoundsText","yes");
							if (isSoundCorrect) {
								System.out.println("Correct translation for Sounds text");
								log.debug("Correct translation for Sounds text");
								report.detailsAppendNoScreenshot("Verify Sounds text translation",
										"Sounds text should display correctly in " + languageDescription,
										"Sounds is correct", "Pass");
							} else {
								System.out.println("Incorrect translation for Sounds text");
								log.debug("Incorrect translation for Sounds text");
								report.detailsAppendNoScreenshot("Verify Sounds text translation",
										"Sounds text should display correctly in " + languageDescription,
										"Sounds is Incorrect", "Fail");
							}
						} catch (Exception e) {
							log.error(e.getMessage(), e);
						}

					} catch (Exception e) {
						log.error(e.getMessage(), e);
						cfnlib.evalException(e);
					}

				}
		}
			// -------------------Handling the exception---------------------//
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			cfnlib.evalException(e);
		}
		// -------------------Closing the connections---------------//
		finally {
			report.endReport();
			webdriver.close();
			webdriver.quit();
			//proxy.abort();
			Thread.sleep(1000);
		}
	}
}
