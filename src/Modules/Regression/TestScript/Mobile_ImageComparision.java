package Modules.Regression.TestScript;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import org.apache.log4j.Logger;
import org.openqa.selenium.ScreenOrientation;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import com.zensar.automation.api.RestAPILibrary;
import com.zensar.automation.framework.model.ScriptParameters;
import com.zensar.automation.framework.report.Desktop_HTML_Report;
import com.zensar.automation.framework.report.Mobile_HTML_Report;
import com.zensar.automation.framework.utils.Constant;
import com.zensar.automation.framework.utils.ExcelDataPoolManager;
import com.zensar.automation.library.CommonUtil;
import com.zensar.automation.library.ImageLibrary;
import Modules.Regression.FunctionLibrary.CFNLibrary_Desktop;
import Modules.Regression.FunctionLibrary.CFNLibrary_Desktop_CS;
import Modules.Regression.FunctionLibrary.CFNLibrary_Desktop_Force;
import Modules.Regression.FunctionLibrary.CFNLibrary_Mobile;
import Modules.Regression.FunctionLibrary.CFNLibrary_Mobile_CS;
import Modules.Regression.FunctionLibrary.CFNLibrary_Mobile_CS_Renovate;
import Modules.Regression.FunctionLibrary.CFNLibrary_Mobile_Force;
import Modules.Regression.FunctionLibrary.factory.DesktopFunctionLibraryFactory;
import Modules.Regression.FunctionLibrary.factory.MobileFunctionLibraryFactory;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import net.lightbody.bmp.BrowserMobProxyServer;
/**
 * Image comparison
 * 
 * @author pb61055
 *
 */

public class Mobile_ImageComparision 
{
	Logger log = Logger.getLogger(Mobile_ImageComparision.class.getName()); 
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


		
		cfnlib.setOsPlatform(scriptParameters.getOsPlatform());

		
		try
		{
			if(webdriver!=null)
			{		

				//ImageLibrary imageLibrary = new ImageLibrary(webdriver, gameName,"Mobile");		
				//report.detailsAppend("Verify if able to click on statistics", "Should be able to click on statistics button", "Able to click on statistics button", "PASS");
				
				
				/*
				for(int orientation=0;orientation<2;orientation++) 
				{
					if(orientation==0 && !webdriver.getOrientation().equals(ScreenOrientation.PORTRAIT))
					{
						boolean portrait= cfnlib.funcPortrait();
													
						if(portrait)
						{
							log.debug("Able to rotate portrait");
							System.out.println("Able to rotate portrait");
							report.detailsAppend("Execution in Portrait mode ", " ", "", "Pass");
						}
						else
						{
							log.debug("Unable to rotate portrait");
							report.detailsAppend("Unable to rotate portrait ", " ", "", "Fail");
						}
					}
					else if(orientation==1 && !webdriver.getOrientation().equals(ScreenOrientation.LANDSCAPE))
					{
						boolean landscape=cfnlib.funcLandscape();
						
						if(landscape)
						{
							log.debug("Able to rotate landscape");
							System.out.println("Able to rotate landscape");
							report.detailsAppend("Execution in Landscape mode ", " ", "", "Pass");
						}
						else
						{
							log.debug("Unable to rotate landscape");
							report.detailsAppend("Unable to rotate landscape ", " ", "", "Fail");
						}
					}		
				
			
				imageLibrary.setScreenOrientation(webdriver.getOrientation().toString().toLowerCase());
				
				
				if (webdriver instanceof AndroidDriver) {
					webdriver.context("CHROMIUM");
		        } else if (webdriver instanceof IOSDriver) {
		            Set<String> contexts=webdriver.getContextHandles();
		            for(String context:contexts) {
		                if(context.startsWith("WEBVIEW")){
		                    log.debug("context going to set in IOS is:"+context);
		                    webdriver.context(context);
		                }
		            }			           
		        }
				if (webdriver instanceof AndroidDriver) {
										
				System.out.println("Context="+webdriver.getContext());
		        } 
				webdriver.context("NATIVE_APP");	
*/
				String url = cfnlib.xpathMap.get("ApplicationURL");
				System.out.println("url = " +url);
				
				webdriver.navigate().to(url);
				Thread.sleep(15000);
				
				cfnlib.funcFullScreen();
				Thread.sleep(1000);
				report.detailsAppend("Verify Game launchaed ", "Game should be launched", "Game is launched", "PASS");
				
				System.out.println("CORE");
				String gameSettingsText = "return " + cfnlib.xpathMap.get("getSettingsText");
				
				String consoleSettings = cfnlib.GetConsoleText(gameSettingsText);
				System.out.println(consoleSettings);
				
			/*	
				if(framework.equalsIgnoreCase("PlayNext"))
				{

					report.detailsAppend("Verify Game launchaed ", "Game should be launched", "Game is launched",
							"PASS");

					try {
						imageLibrary.click("NFDButton");
						report.detailsAppend("Verify if able to click on continue",
								"Should be able to click on continue button", "Able to click on continue button",
								"PASS");
					} catch (Exception e) {
						log.error(e.getMessage(), e);
					}

					try {
						imageLibrary.click("Spin");
						report.detailsAppend("Verify able to click on Spin", "Should be able to click on spin button",
								"Able to click on spin button", "PASS");
					} catch (Exception e) {
						log.error(e.getMessage(), e);
					}
					Thread.sleep(5000);
					try {
						imageLibrary.click("Autoplay");
						report.detailsAppend("Verify if able to click on autoplay",
								"Should be able to click on autoplay button", "Able to click on autoplay button",
								"PASS");
					} catch (Exception e) {
						log.error(e.getMessage(), e);
					}

				}
				
				try 
				{
					imageLibrary.click("QuickSpinOff");				
					report.detailsAppend("Verify able to click on quick spin", "Should be able to click on quick spin button", "Able to click on quick spin button", "PASS");
				}catch (Exception e) 
				{
					log.error(e.getMessage(),e);	
				}
				
				
				try 
				{
					imageLibrary.click("BetMenu");										
					report.detailsAppend("Verify if able to click on statistics", "Should be able to click on statistics button", "Able to click on statistics button", "PASS");
				}catch (Exception e) 
				{
					log.error(e.getMessage(),e);	
				}
				
				try 
				{
					imageLibrary.click("Menu");										
					report.detailsAppend("Verify if able to click on Menu", "Should be able to click on Menu button", "Able to click on Menu button", "PASS");
				}catch (Exception e) 
				{
					log.error(e.getMessage(),e);	
				}
				
				
				
				}*/
			}//-------------------Handling the exception---------------------//
		}catch (Exception e) 
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
