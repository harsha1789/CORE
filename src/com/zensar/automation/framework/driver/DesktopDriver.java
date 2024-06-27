package com.zensar.automation.framework.driver;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

import org.apache.log4j.Logger;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.UnexpectedAlertBehaviour;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerOptions;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.opera.OperaOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.safari.SafariOptions;
import org.testng.annotations.AfterTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.zensar.automation.framework.library.Global;
import com.zensar.automation.framework.library.PropertyReader;
import com.zensar.automation.framework.model.ScriptParameters;
import com.zensar.automation.framework.report.Desktop_HTML_Report;
import com.zensar.automation.framework.utils.Constant;
import com.zensar.automation.framework.utils.ExcelDataPoolManager;

import net.lightbody.bmp.BrowserMobProxyServer;
import net.lightbody.bmp.client.ClientUtil;
import net.lightbody.bmp.proxy.CaptureType;

/**
 * This is  the driver class for desktop , it reads the configured test cases from excel sheet,
 * creates driver object based on browser and calls the test case using reflection.
 * 
 */

public class DesktopDriver {
	Logger log = Logger.getLogger(DesktopDriver.class.getName());
	BrowserMobProxyServer browserproxy;
	
	String browserName;
	String userName;
	WebDriver driver;
	DateFormat mObjDateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
	String filePath;
	String startTime;
	int mintDetailCount;
	int mintSubStepNo;
	int mintPassed;
	int mintFailed;
	int mintWarnings;
	int mintStepNo;
	String mstrTestCase;
	String msrtTCDesc;
	String msrtModuleName;
	String status;
	String time;
	String mstrGame;
	String gameName;
	public static Map<String, String> repo = new HashMap<>();

	
	@Test
	@Parameters({ "Browser","username","OSplatform","gameName"})
	public void test(String browser,String username, String osPlatform,String gameName) throws IOException, InterruptedException {

		log.info("Excecution started for Desktop .....");
		this.browserName = browser;
		this.userName=username;
		this.gameName=gameName;
		
		// set the log file name , when not executing from jar
		if(null==System.getProperty("logfilename"))
		{
			System.setProperty("logfilename",gameName+"/Selenium");
		}
		// **************************************************************//
		// Reflection API
		// Logic to execute scripts which have run status as Run
		try{
			Global obj = new Global(gameName);
			

			// ------------- Declaration Of Global Variable in Config
			// File-----------
			obj.cfnRootPath();
			String suitExcelPath= PropertyReader.getInstance().getProperty("Suit_Excel_Path");
			String regressionExcelPath=PropertyReader.getInstance().getProperty("Regression_Excel_Path");
			log.debug("suitExcelPath :: "+suitExcelPath);
			log.debug("regressionExcelPath ::"+regressionExcelPath);
			ExcelDataPoolManager oExcelFile = new ExcelDataPoolManager();
			String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
			repo.put(browser, timeStamp);
			int rowcount = oExcelFile.rowCount(suitExcelPath, Constant.CONFIGFILE);
			Map<String, String> rowData = null;
			
			for (int suitRowCounter = 1; suitRowCounter < rowcount; suitRowCounter++) {
				// ---------- Reading Excel Module Suit File On the Basis of Run
				// Flag -------------
				rowData = oExcelFile.readExcelByRow(suitExcelPath, Constant.CONFIGFILE, suitRowCounter);
				String suitRunFlag = rowData.get("RunStatus");
				if (suitRunFlag.equalsIgnoreCase("run")) {

					obj.setGstrModuleName(rowData.get("ModuleName"));
					obj.cfnModuleRootPath();
					obj.setgCalenderCalStart(Calendar.getInstance());
					obj.setGstrStartTime(mObjDateFormat.format(obj.getgCalenderCalStart().getTime()));

					log.debug("Execution Started for Module :: "+obj.getGstrModuleName());
					// ****************************************************************//
					int testcasesCount =0;
					synchronized(this){
						testcasesCount = oExcelFile.rowCount(regressionExcelPath, Constant.CONFIGFILE);
					}
					for (int testcaseCounter = 1; testcaseCounter < testcasesCount; testcaseCounter++) {
						// ---------- Reading Excel Suit File On the Basis of Run

						synchronized (this) {
							rowData = oExcelFile.readExcelByRow(regressionExcelPath, Constant.CONFIGFILE, testcaseCounter);
						}
						String testcaseRunFlag = rowData.get("RunFlag");
						String  mstrPlatform=rowData.get("Platform").trim();
						String mstrFramework=rowData.get("Framework").trim();
						mstrGame=rowData.get("GameName").trim();
						mstrTestCase = rowData.get("TestCase");
						if (testcaseRunFlag.equalsIgnoreCase("run") && mstrPlatform.equals("Desktop")) {
							msrtTCDesc =rowData.get("TestCaseDescription");

							log.debug("Execution Started for script :: "+msrtTCDesc);
							repo.put(browser, timeStamp);
							org.openqa.selenium.Proxy seleniumProxy=null;


							// the below if you capturing the har
							if("yes".equalsIgnoreCase(PropertyReader.getInstance().getProperty("enableReadHar")))
							{
								browserproxy = new BrowserMobProxyServer();

								if (!(browserproxy.isStarted())) {
									browserproxy.setTrustAllServers(true);   
									Set<CaptureType> captureTypes = new HashSet<>();
									captureTypes.add(CaptureType.REQUEST_CONTENT);
									browserproxy.enableHarCaptureTypes(captureTypes);
									browserproxy.start();
								}
								browserproxy.setHarCaptureTypes(CaptureType.getAllContentCaptureTypes());
								browserproxy.newHar();

								browserproxy.getClientBindAddress();
								seleniumProxy = ClientUtil.createSeleniumProxy(browserproxy);
							}

							if (browser.equalsIgnoreCase("firefox")) {
								log.debug(msrtTCDesc+"Execution Started in Firefox");
								//DesiredCapabilities cap = DesiredCapabilities.firefox(); //For selenium version 3.141.59
								// Create an instance of FirefoxOptions
								FirefoxOptions cap = new FirefoxOptions(); //For selenium version 4
								cap.setCapability("marionette", true);
								cap.setCapability(CapabilityType.PROXY, seleniumProxy);
								
								//cap.acceptInsecureCerts(); //For selenium version 3.141.59
								// Accept insecure SSL certificates
								cap.setAcceptInsecureCerts(true);//For selenium version 4
								
								//cap.setJavascriptEnabled(false); //For selenium version 3.141.59
								// Disable JavaScript
								cap.addPreference("javascript.enabled", false);//For selenium version 4
								
								//ProfilesIni ini=new ProfilesIni();
								//FirefoxProfile firefoxProfile= ini.getProfile("Testing");
								/*firefoxProfile.setPreference("security.mixed_content.block_active_content", false);
									firefoxProfile.setPreference("security.mixed_content.block_display_content", true);*/
								
								//cap.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);//For selenium version 3.141.59	
								cap.setCapability(CapabilityType.ACCEPT_INSECURE_CERTS, true);//For selenium version 4
								
								if(osPlatform.equalsIgnoreCase(Constant.WINDOWS)){
									//cap.setPlatform(org.openqa.selenium.Platform.ANY);//For selenium version 3.141.59
									// Set the desired platform name
									cap.setCapability("platformName", "ANY");//For selenium version 4

								}
								else
								{
									//cap.setPlatform(org.openqa.selenium.Platform.MAC);//For selenium version 3.141.59
									// Set the desired platform name
									cap.setCapability("platformName", "MAC");//For selenium version 4
								}
								driver = new RemoteWebDriver(new URL(Constant.LOCALHUBURL), cap);
							}
							else if (browser.equalsIgnoreCase("IE")) {
								log.debug(msrtTCDesc+"Execution Started in IE");
								/*DesiredCapabilities cap = DesiredCapabilities.internetExplorer();
								cap.setCapability(CapabilityType.ACCEPT_SSL_CERTS,true);*/
								InternetExplorerOptions internetExplorerOptions= new InternetExplorerOptions();
								/**
								 * Setting desire capabilities for IE browser
								 * ******/
								//internetExplorerOptions.setCapability("browserName","internet explorer");//For selenium version 3.141.59
								internetExplorerOptions.setCapability("browser","internet explorer");//For selenium version 4
								//internetExplorerOptions.setCapability(CapabilityType.ForSeleniumServer.ENSURING_CLEAN_SESSION, true); //For selenium version 3.141.59
								// Enable the "ensureCleanSession" capability
								
								//internetExplorerOptions.setCapability(InternetExplorerOptions.FOR_SELENIUM_SERVER_ENSURE_CLEAN_SESSION, true);
								internetExplorerOptions.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS,true);
								internetExplorerOptions.setCapability("ignoreZoomSetting", true);//For selenium version 4
								internetExplorerOptions.setCapability("enableElementCacheCleanup", true);//For selenium version 4
								internetExplorerOptions.setCapability("enablePersistentHover", true);//For selenium version 4
								internetExplorerOptions.setCapability("requireWindowFocus", true);
								internetExplorerOptions.setCapability("nativeEvents",true);
								//newly added
								//internetExplorerOptions.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);//For selenium version 3.141.59
								// Accept insecure SSL certificates
								internetExplorerOptions.setCapability(CapabilityType.ACCEPT_INSECURE_CERTS, true);//For selenium version 4
								internetExplorerOptions.setCapability("ignoreProtectedModeSettings",true);
								internetExplorerOptions.setCapability("setJavascriptEnabled",true);
								internetExplorerOptions.setCapability("ignoreProtectedModeSettings",true);
								internetExplorerOptions.setCapability(InternetExplorerDriver.UNEXPECTED_ALERT_BEHAVIOR, UnexpectedAlertBehaviour.ACCEPT);

								//cap.setCapability(internetExplorerOptions.IE_OPTIONS, internetExplorerOptions);
								if(osPlatform.equalsIgnoreCase("Windows")){
									internetExplorerOptions.setCapability("org.openqa.selenium.Platform", org.openqa.selenium.Platform.WINDOWS);
									log.debug(msrtTCDesc+"Execution Started in IE in windows");
								}
								else
								{
									internetExplorerOptions.setCapability("org.openqa.selenium.Platform", org.openqa.selenium.Platform.MAC);

								}

								
								driver = new RemoteWebDriver(new URL(Constant.LOCALHUBURL), internetExplorerOptions); 
							} 
							//++++++++++++++++++++++++++//////////////////// edge driver
							else if(browser.equalsIgnoreCase("edge")) {
								log.debug(msrtTCDesc+"Execution Started in Edge ");
								System.setProperty("webdriver.edge.driver", PropertyReader.getInstance().getProperty("EdgeDriverPath"));
								//DesiredCapabilities cap = DesiredCapabilities.edge();//For selenium version 3.141.59
								// Create an instance of EdgeOptions
								EdgeOptions cap = new EdgeOptions();//For selenium version 4
								
								
								//cap.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);//For selenium version 3.141.59
								// Accept insecure SSL certificates
								cap.setAcceptInsecureCerts(true);//For selenium version 4

								if(osPlatform.equalsIgnoreCase("Windows")){
									//cap.setPlatform(org.openqa.selenium.Platform.WINDOWS);//For selenium version 3.141.59
									// Set the desired platform name
									cap.setCapability("platformName", "WINDOWS");//For selenium version 4
									log.debug(msrtTCDesc+"Execution Started in EDGE in windows");
								}
								else
								{
									//cap.setPlatform(org.openqa.selenium.Platform.MAC);
									
									//cap.setPlatform(org.openqa.selenium.Platform.MAC);//For selenium version 3.141.59
									// Set the desired platform name
									cap.setCapability("platformName", "MAC");//For selenium version 4
								}

								driver = new RemoteWebDriver(new URL(Constant.LOCALHUBURL), cap);

							}
							else if (browser.equalsIgnoreCase("Chrome")) {

								log.debug(msrtTCDesc+"Execution Started in Chrome ");
								//DesiredCapabilities cap = DesiredCapabilities.chrome();Used for selenium 3.141.59
								// Create an instance of ChromeOptions
								ChromeOptions cap = new ChromeOptions(); //Used for Java 4
								LoggingPreferences logPrefs = new LoggingPreferences();
								logPrefs.enable(LogType.BROWSER, Level.ALL);
								logPrefs.enable(LogType.PERFORMANCE, Level.ALL);
								
								// Set the logging preferences in the ChromeOptions
							//	cap.setCapability(CapabilityType.LOGGING, logPrefs);//Used for Java 3.141.59
								
								// Set the desired log type and log level
								logPrefs.enable(LogType.BROWSER, Level.ALL);//Used for Java 4

								// Set the logging preferences in the ChromeOptions
								cap.setCapability("goog:loggingPrefs", logPrefs);//Used for Java 4
								
								
								cap.setCapability(CapabilityType.PROXY, seleniumProxy);
								
								//cap.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);//Used for Java 3.141.59
								
								// Accept insecure SSL certificates
								cap.setAcceptInsecureCerts(true);//Used for Java 4
								
								if(osPlatform.equalsIgnoreCase(Constant.WINDOWS)){
									log.debug(msrtTCDesc+"Execution Started in Chrome in windows");
									//cap.setPlatformName(org.openqa.selenium.Platform.WINDOWS);//For selenium version 3.141.59
									
									cap.setCapability("platformName", "WINDOWS");//For selenium version 4

								}
								else
								{
									//cap.setPlatformName(org.openqa.selenium.Platform.MAC);//For selenium version 3.141.59
							        cap.setCapability("platformName", "MAC");//For selenium version 4
								}
								ChromeOptions options = new ChromeOptions();
								options.addArguments("disable-infobars");
								options.addArguments("--disable-web-security");
								options.addArguments("--start-maximized");
								
								Map<String, Object> prefs = new HashMap<>();
								prefs.put("credentials_enable_service", false);
								prefs.put("profile.password_manager_enabled", false);
								options.setExperimentalOption("prefs", prefs);
								options.setExperimentalOption("useAutomationExtension", false);
								options.setExperimentalOption("excludeSwitches", Collections.singletonList("enable-automation"));
								options.addArguments("--start-maximized"); 

								cap.setCapability(ChromeOptions.CAPABILITY, options);
								driver = new RemoteWebDriver(new URL(Constant.LOCALHUBURL), cap);	
							}
							else if (browser.equalsIgnoreCase("Opera")) {
								System.setProperty("webdriver.chrome.driver", PropertyReader.getInstance().getProperty("OPERADriverPath"));
								//DesiredCapabilities cap = DesiredCapabilities.operaBlink();//Used for selenium 3.141.59
								OperaOptions cap = new OperaOptions();//Used for selenium 4
								//cap.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);//For selenium version 3.141.59
								cap.setCapability(CapabilityType.ACCEPT_INSECURE_CERTS, true);//For selenium version 3.141.59
								if(osPlatform.equalsIgnoreCase(Constant.WINDOWS)){
									//cap.setPlatform(org.openqa.selenium.Platform.WINDOWS);//For selenium version 3.141.59
									cap.setCapability("platformName", "WINDOWS");//For selenium version 4

								}
								else
								{
									//cap.setPlatform(org.openqa.selenium.Platform.MAC);//For selenium version 3.141.59
									cap.setCapability("platformName", "MAC");//For selenium version 4
								}


								ChromeOptions oo = new ChromeOptions();
								oo.setBinary(PropertyReader.getInstance().getProperty("OperaBinaryPath"));
								cap.setCapability(ChromeOptions.CAPABILITY, oo);

								driver = new RemoteWebDriver(new URL(Constant.LOCALHUBURL), cap);
							}
							//Added By Abhishek
							else if(browser.equalsIgnoreCase("safari"))
							{
								log.debug("Checking for Safari Browser");
								System.out.println("Checking for Safari Browser");
								//DesiredCapabilities cap = DesiredCapabilities.safari();//Used for selenium 3.141.59 
								// Create an instance of SafariOptions
								SafariOptions cap = new SafariOptions(); //Used for selenium 4 
								cap.setCapability(CapabilityType.ACCEPT_INSECURE_CERTS, true);
								//cap.setPlatform(org.openqa.selenium.Platform.MAC);//For selenium version 3.141.59
								cap.setCapability("platformName", "MAC");//For selenium version 4

								SafariOptions sf = new SafariOptions();
								driver = new SafariDriver();
								
							}
							driver.manage().deleteAllCookies();
							driver.manage().timeouts().implicitlyWait(120, TimeUnit.SECONDS);
							Thread.sleep(1000);

							// Converting Test case name to ClassName Format
							String s = "Modules." + obj.getGstrModuleName() + ".TestScript.";

							String mstrClassName = s.concat(mstrTestCase);
							log.info("Test Case Name="+mstrClassName);
							// Method Name to call From respective class
							String mstrFunctionName = "script";
							msrtModuleName = obj.getGstrModuleName();

							filePath = obj.getGstrResultPath();
							log.debug("Result File path="+filePath);
							startTime = obj.getGstrStartTime();
							log.debug("Starting time::"+startTime);
							ScriptParameters scriptParameters=new ScriptParameters();

							Class<?> c = Class.forName(mstrClassName);
							Object scriptClassObj = c.newInstance();

							scriptParameters.setMstrTCName(mstrTestCase);
							scriptParameters.setMstrTCDesc(msrtTCDesc);
							scriptParameters.setMstrModuleName(msrtModuleName);
							scriptParameters.setProxy(browserproxy);
							scriptParameters.setStartTime(startTime);
							scriptParameters.setFilePath(filePath);
							scriptParameters.setDriver(driver);
							scriptParameters.setUserName(username);
							scriptParameters.setFramework(mstrFramework);
							scriptParameters.setGameName(mstrGame);
							scriptParameters.setBrowserName(browserName);


							Field scriptParametersObj=c.getDeclaredField("scriptParameters"); 
							scriptParametersObj.set(scriptClassObj,scriptParameters);


							Method m = c.getDeclaredMethod(mstrFunctionName);
							m.invoke(scriptClassObj);
							log.info("Execution started for= "+mstrFunctionName);


						}
					}
				}
			}
			
		}/*catch(UnableToLoadPropertiesException e)
		{
			log.error("Unable to load test propety file",e);
		}*/
		catch(Exception e)
		{
			log.error("Exception in Desktop Driver ",e);
		}
	}

	/**
	 * This method will be called  on browser close
	 * @throws Exception
	 */
	@AfterTest
	public void closeBrowser() throws Exception {

		Desktop_HTML_Report h = new Desktop_HTML_Report(driver, browserName, filePath, startTime, mstrTestCase, msrtTCDesc,
				msrtModuleName, mintDetailCount, mintPassed, mintFailed, mintWarnings, mintStepNo, status,mstrGame);
		h.buildAutomationSummary();
		String mstrAutoSummPath = filePath + repo.get(browserName) + "_" + browserName + "_"
				+ "Automation_summary" + ".html";
		String[] cmds = new String[] { "cmd", "/c", mstrAutoSummPath };
		Runtime.getRuntime().exec(cmds);
	}

	protected String getStackTrace(Throwable t) {
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		t.printStackTrace(pw);
		return sw.toString();
	}

	public void waitForPageToBeReady() {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		for (int i = 0; i < 400; i++) {
			try {
				Thread.sleep(1000);
			} catch (Exception e) {
				log.error("error in driver", e);
			}
			if (js.executeScript("return document.readyState").toString().equals("complete")) {
				break;
			}
		}
	}
}