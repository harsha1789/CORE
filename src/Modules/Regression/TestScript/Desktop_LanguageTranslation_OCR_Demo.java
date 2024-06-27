package Modules.Regression.TestScript;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.List;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import com.zensar.automation.framework.model.ScriptParameters;
import com.zensar.automation.framework.report.Desktop_HTML_Report;
import com.zensar.automation.framework.utils.Constant;
import com.zensar.automation.framework.utils.ExcelDataPoolManager;
import com.zensar.automation.framework.utils.Util;
import com.zensar.automation.library.CommonUtil;
import com.zensar.automation.library.ImageLibrary;
import Modules.Regression.FunctionLibrary.CFNLibrary_Desktop;
import Modules.Regression.FunctionLibrary.factory.DesktopFunctionLibraryFactory;
import net.lightbody.bmp.BrowserMobProxyServer;

/**
 * This script is used to verify language translations
 * 
 * @author pb61055
 *
 */
public class Desktop_LanguageTranslation_OCR_Demo {

	Logger log = Logger.getLogger(Desktop_LanguageTranslation_OCR_Demo.class.getName());
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
						String Settings = LanguageMap.get(Constant.Settings).trim();
						String regExpr = LanguageMap.get(Constant.REGEXPRESSION).trim();

						String url = cfnlib.XpathMap.get("ApplicationURL");
						log.info("url = " + url);

						webdriver.navigate().to(url);
						Thread.sleep(10000);

						if (cfnlib.isElementVisible("NFDButton")) {
							imageLibrary.click("NFDButton");
						}

						Thread.sleep(2000);

						String creditAmouttext = imageLibrary.getTextCredit("Credits");
						Thread.sleep(1000);
						boolean isCreditAmountCorrect = cfnlib.verifyRegularExpression(report, regExpr,
								creditAmouttext);
						if (isCreditAmountCorrect) {
							report.detailsAppendFolder("Verify the currency format in credit ",
									"Credit should display in correct currency format ",
									"Credit is displaying in correct currency format ", "Pass", languageCode);
						} else {
							report.detailsAppendFolder("Verify the currency format in credit ",
									"Credit should display in correct currency format ",
									"Credit is displaying in incorrect currency format ", "Fail", languageCode);
						}

						
						String SettingsText = imageLibrary.getTextSettings("Settings");
						Thread.sleep(2000);
						boolean isSettingsCorrect = cfnlib.compareTextOCR(Settings, SettingsText);

						if (isSettingsCorrect) {
							
							report.detailsAppendNoScreenshot("Verify Settings text translation",
									"Settings text should display correctly in " + languageDescription, "Settings is correct",
									"Pass");

						} else {
						
							report.detailsAppendNoScreenshot("Verify Settings text translation",
									"Settings text should display correctly in " + languageDescription, "Settings is Incorrect",
									"Fail");
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
			Thread.sleep(1000);
		}
	}
}
