package Modules.Regression.TestScript;

/*
 * game covered - lucky twins
 */
import java.util.Set;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.ScreenOrientation;
import org.openqa.selenium.WebElement;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import com.zensar.automation.framework.model.ScriptParameters;
import com.zensar.automation.framework.report.Mobile_HTML_Report;
import com.zensar.automation.library.ImageLibrary;

import Modules.Regression.FunctionLibrary.CFNLibrary_Mobile;
import Modules.Regression.FunctionLibrary.CFNLibrary_Mobile_CS;
import Modules.Regression.FunctionLibrary.CFNLibrary_Mobile_CS_Renovate;
import Modules.Regression.FunctionLibrary.CFNLibrary_Mobile_Force;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import net.lightbody.bmp.BrowserMobProxyServer;

public class Mobile_OBAT_Regression_Image_PlaydotNext {

	public static Logger log = Logger.getLogger(Mobile_OBAT_Regression_Image_PlaydotNext.class.getName());
	public ScriptParameters scriptParameters;
	
	public void script() throws Exception{
		
		String mstrTC_Name=scriptParameters.getMstrTCName();
		String mstrTC_Desc=scriptParameters.getMstrTCDesc();
		String mstrModuleName=scriptParameters.getMstrModuleName();
		BrowserMobProxyServer proxy=scriptParameters.getProxy();
		String startTime=scriptParameters.getStartTime();
		String filePath=scriptParameters.getFilePath();
		AppiumDriver<WebElement> webdriver=scriptParameters.getAppiumWebdriver();
		String DeviceName=scriptParameters.getDeviceName();
		String Framework=scriptParameters.getFramework();
		String GameName=scriptParameters.getGameName();


		String status=null;
		int mintDetailCount=0;
		int mintPassed=0;
		int mintFailed=0;
		int mintWarnings=0;
		int mintStepNo=0;

		Mobile_HTML_Report languageReport=new Mobile_HTML_Report(webdriver,DeviceName,filePath,startTime,mstrTC_Name,mstrTC_Desc,mstrModuleName,mintDetailCount,mintPassed,mintFailed,mintWarnings,mintStepNo,status,GameName);
		ExtentHtmlReporter htmlReporter;

		ExtentReports extent=null;
		ExtentTest report = null;		
		String path1=System.getProperty("user.dir");
		htmlReporter= new ExtentHtmlReporter(path1+"\\output\\"+GameName+languageReport.createTimeStampStr()+mstrTC_Name+".html");
		//((CanRecordScreen)webdriver).startRecordingScreen();
		
		//initialize ExtentReports and attach the HtmlReporter
		extent= new ExtentReports();
		extent.attachReporter(htmlReporter);

		htmlReporter.config().setChartVisibilityOnOpen(true);
		htmlReporter.config().setDocumentTitle("Automation Test Script Excution summary");
		htmlReporter.config().setReportName("OBAT initial Excution summary");

		htmlReporter.config().setTheme(Theme.STANDARD);
		htmlReporter.config().setTimeStampFormat("EEEE, MMMM dd, yyyy, hh:mm a '('zzz')'");



		CFNLibrary_Mobile cfnlib=null; 
		if(Framework.equalsIgnoreCase("PN")){
			cfnlib=new CFNLibrary_Mobile(webdriver,proxy,languageReport,GameName);
		}else if(Framework.equalsIgnoreCase("Force")){
			cfnlib=new CFNLibrary_Mobile_Force(webdriver, proxy, languageReport, GameName);
		}else if(Framework.equalsIgnoreCase("CS_Renovate")){
			cfnlib=new CFNLibrary_Mobile_CS_Renovate(webdriver, proxy, languageReport, GameName);
		}else{
			cfnlib=new CFNLibrary_Mobile_CS(webdriver,proxy,languageReport,GameName);
		}

		cfnlib.setOsPlatform(scriptParameters.getOsPlatform());

		
		try
		{
			if(webdriver!=null)
			{		

				String url = cfnlib.xpathMap.get("ApplicationURL");
				System.out.println("url = " +url);

				ImageLibrary imageLibrary = new ImageLibrary(webdriver, GameName,"Mobile");		
				
				webdriver.context("NATIVE_APP");
				webdriver.rotate(ScreenOrientation.LANDSCAPE);
				webdriver.context("CHROMIUM");
				
				imageLibrary.setScreenOrientation(webdriver.getOrientation().toString().toLowerCase());
				String extentScreenShotPath=imageLibrary.extentScreenShotPath+"\\"+imageLibrary.getScreenOrientation();		
				
				
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
				
				try
				{
				System.out.println("Context="+webdriver.getContext());
				cfnlib.loadGame(url);
				Thread.sleep(9000);
				}
				catch (Exception e) {
				Thread.sleep(2000);
				log.error(e.getMessage(),e);
				cfnlib.evalException(e);
				}
				//cfnlib.func_SwipeUp();
				Thread.sleep(2000);
				
				if (webdriver instanceof AndroidDriver) {
					webdriver.context("NATIVE_APP");
		        } 
									
				System.out.println("Context="+webdriver.getContext());

				report=extent.createTest("OBATTestCases");
				
		/*		//check if on nfd 
				if(cfnlib.XpathMap.get("GameName").equalsIgnoreCase("Extreme"))
				{
					imageLibrary.details_append_folderOnlyScreeshot(webdriver,"BaseGameVisible");
					report.log(Status.PASS, "We are on Base Game ",MediaEntityBuilder.createScreenCaptureFromPath(extentScreenShotPath+"\\BaseGameVisible.jpg").build() );
					
						cfnlib.tapOnCoordinates(200,200);
				}	
				 //check if on base game
	             try
	             {
						if(imageLibrary.isImageAppears("Spin"))
						{
							imageLibrary.details_append_folderOnlyScreeshot(webdriver,"BaseGameVisible");
							report.log(Status.PASS, "We are on Base Game ",MediaEntityBuilder.createScreenCaptureFromPath(extentScreenShotPath+"\\BaseGameVisible.jpg").build() );
							if(imageLibrary.click("Spin"))
							{
								imageLibrary.details_append_folderOnlyScreeshot(webdriver,"SpinbuttonWorks");
								report.log(Status.PASS, "Spin button is working ",MediaEntityBuilder.createScreenCaptureFromPath(extentScreenShotPath+"\\SpinbuttonWorks.jpg").build() );
							}
							else
							{
								imageLibrary.details_append_folderOnlyScreeshot(webdriver,"SpinbuttondidntWorks");
								report.log(Status.FAIL, "Spin button is not working ",MediaEntityBuilder.createScreenCaptureFromPath(extentScreenShotPath+"\\SpinbuttondidntWorks.jpg").build() );						
							}
						}
						else
						{
							imageLibrary.details_append_folderOnlyScreeshot(webdriver,"BaseGameNotVisible");
							report.log(Status.FAIL, "We are not on Base game ",MediaEntityBuilder.createScreenCaptureFromPath(extentScreenShotPath+"\\BaseGameNotVisible.jpg").build() );						
						}
						//refresh base game after spin
						try
						{
							webdriver.context("CHROMIUM");
							cfnlib.refresh();
							Thread.sleep(5000);
							//cfnlib.func_SwipeUp();
							Thread.sleep(2000);
							webdriver.context("NATIVE_APP");
						}
						catch (Exception e) {
							Thread.sleep(2000);
							log.error(e.getMessage(),e);
							cfnlib.evalException(e);
							}			
						
						
						 if("Yes".equalsIgnoreCase(cfnlib.XpathMap.get("NFD")))
		            	 {
							if(cfnlib.XpathMap.get("GameName").equalsIgnoreCase("Extreme"))
							{
									cfnlib.tapOnCoordinates(200,200);
							}	
		            	 }
						 
						if(imageLibrary.isImageAppears("Spin"))
						{
							imageLibrary.details_append_folderOnlyScreeshot(webdriver,"RefreshOnBaseGame");
							report.log(Status.PASS, "refreshed on base game",MediaEntityBuilder.createScreenCaptureFromPath(extentScreenShotPath+"\\RefreshOnBaseGame.jpg").build() );
						}
						else
						{
							imageLibrary.details_append_folderOnlyScreeshot(webdriver,"NoRefreshOnBaseGame");
							report.log(Status.FAIL, "not refreshed on base game",MediaEntityBuilder.createScreenCaptureFromPath(extentScreenShotPath+"\\NoRefreshOnBaseGame.jpg").build() );						
						}
						
	             }catch (Exception e) {
						Thread.sleep(2000);
						log.error(e.getMessage(),e);
						cfnlib.evalException(e);
						}
					
				// menu
	             try
	             {
	            	 if(imageLibrary.isImageAppears("Menu"))
	            		 imageLibrary.click("Menu");
	            	 
	            	 Thread.sleep(2000);
	            	 
	            	 if(imageLibrary.isImageAppears("Paytable"))
	            	 {
							imageLibrary.details_append_folderOnlyScreeshot(webdriver,"MenuWorking");
							report.log(Status.PASS, "Clicked Menu is working",MediaEntityBuilder.createScreenCaptureFromPath(extentScreenShotPath+"\\MenuWorking.jpg").build() );
						}
						else
						{
							imageLibrary.details_append_folderOnlyScreeshot(webdriver,"MenuNotWorking");
							report.log(Status.FAIL, "Clicked Menu is not working",MediaEntityBuilder.createScreenCaptureFromPath(extentScreenShotPath+"\\MenuNotWorking.jpg").build() );						
						}
							//Refresh in Menu
						try
						{
							webdriver.context("CHROMIUM");
							cfnlib.refresh();
							Thread.sleep(7000);
							//cfnlib.func_SwipeUp();
							Thread.sleep(2000);
							webdriver.context("NATIVE_APP");
						}
						catch (Exception e) {
						Thread.sleep(2000);
						log.error(e.getMessage(),e);
						cfnlib.evalException(e);
						}						
							
							if("YES".equalsIgnoreCase(cfnlib.XpathMap.get("NFD")))
							{
								if(cfnlib.XpathMap.get("GameName").equalsIgnoreCase("Extreme"))
							{
									cfnlib.tapOnCoordinates(200,200);
							}
							else
							{
								imageLibrary.waitTillNFDButtonVisible();
								imageLibrary.click("NFDButton");						
							}
							}
							
							if(imageLibrary.isImageAppears("Spin"))
							{
								imageLibrary.details_append_folderOnlyScreeshot(webdriver,"RefreshAfterMenuSuccessful");
								report.log(Status.PASS, "Refresh After Menu Successful ",MediaEntityBuilder.createScreenCaptureFromPath(extentScreenShotPath+"\\RefreshAfterMenuSuccessful.jpg").build() );
							}
							else
							{
								imageLibrary.details_append_folderOnlyScreeshot(webdriver,"RefreshAfterMenuNotSuccessful");
								report.log(Status.FAIL, "Refresh After Menu not Successful ",MediaEntityBuilder.createScreenCaptureFromPath(extentScreenShotPath+"\\RefreshAfterMenuNotSuccessful.jpg").build() );
							}
	             }catch (Exception e) {
						Thread.sleep(2000);
						log.error(e.getMessage(),e);
						cfnlib.evalException(e);
						}
	             
	             try
					{
							imageLibrary.waitTillBtnVisible("Menu");
							imageLibrary.click("Menu");
							
							imageLibrary.isImageAppears("Paytable");						
							imageLibrary.click("Paytable");					
							if(imageLibrary.isImageAppears("PaytableImage"))
								{										
									imageLibrary.details_append_folderOnlyScreeshot(webdriver,"PaytableWorking");
									report.log(Status.PASS, "Clicked Paytable is working",MediaEntityBuilder.createScreenCaptureFromPath(extentScreenShotPath+"\\PaytableWorking.jpg").build() );
									
									if(cfnlib.XpathMap.get("GameName").equalsIgnoreCase("Extreme"))
									{
										cfnlib.rightToLeftSwipe();
									}else
									{
									for(int i=0;i<=10;i++)
									{
										cfnlib.func_SwipeDown();
										Thread.sleep(2000);
									}
									}
									
									imageLibrary.details_append_folderOnlyScreeshot(webdriver,"PaytableScrolled");
									report.log(Status.PASS, "Paytable has been scrolled",MediaEntityBuilder.createScreenCaptureFromPath(extentScreenShotPath+"\\PaytableScrolled.jpg").build() );
									
								}
							else
								{
									imageLibrary.details_append_folderOnlyScreeshot(webdriver,"PaytableNotWorking");
									report.log(Status.FAIL, "Clicked Paytable is not working",MediaEntityBuilder.createScreenCaptureFromPath(extentScreenShotPath+"\\PaytableNotWorking.jpg").build() );						
								}
							
								//Refresh in Paytable 
								try
								{
									webdriver.context("CHROMIUM");
									cfnlib.refresh();
									Thread.sleep(7000);
									//cfnlib.func_SwipeUp();
									Thread.sleep(2000);
									webdriver.context("NATIVE_APP");
								}
								catch (Exception e) 
								{
								Thread.sleep(2000);
								log.error(e.getMessage(),e);
								cfnlib.evalException(e);
								}
									
									if("YES".equalsIgnoreCase(cfnlib.XpathMap.get("NFD")))
									{
										if(cfnlib.XpathMap.get("GameName").equalsIgnoreCase("Extreme"))
										{
											cfnlib.tapOnCoordinates(200,200);
										}
										else
										{
											imageLibrary.waitTillNFDButtonVisible();
											imageLibrary.click("NFDButton");						
										}
									}									
									if(imageLibrary.isImageAppears("Spin"))
									{
										imageLibrary.details_append_folderOnlyScreeshot(webdriver,"RefreshAfterPaytableSuccessful");
										report.log(Status.PASS, "Refresh After Paytable Successful ",MediaEntityBuilder.createScreenCaptureFromPath(extentScreenShotPath+"\\RefreshAfterPaytableSuccessful.jpg").build() );
									}
									else
									{
										imageLibrary.details_append_folderOnlyScreeshot(webdriver,"RefreshAfterPaytableNotSuccessful");
										report.log(Status.FAIL, "Refresh After Paytable not Successful ",MediaEntityBuilder.createScreenCaptureFromPath(extentScreenShotPath+"\\RefreshAfterPaytableNotSuccessful.jpg").build() );
									}						
						
					}catch (Exception e) {
						Thread.sleep(2000);
						log.error(e.getMessage(),e);
						cfnlib.evalException(e);
						}
	             
	             //settings refresh
	             try
	             {
	            	 imageLibrary.click("Menu");
						imageLibrary.waitTillBtnVisible("Settings");							
						imageLibrary.click("Settings");
						if(imageLibrary.isImageAppears("SettingsText"))								
						{		
							//cfnlib.func_SwipeUp();
							imageLibrary.details_append_folderOnlyScreeshot(webdriver,"SettingWorking");
							report.log(Status.PASS, "Clicked setting is working",MediaEntityBuilder.createScreenCaptureFromPath(extentScreenShotPath+"\\SettingWorking.jpg").build() );						
						
							//click max bet in settings
							if((cfnlib.XpathMap.get("GameName").equalsIgnoreCase("Extreme") )!= true)
							{
								
							if(imageLibrary.click("MaxBetInSettings"))
							{
								imageLibrary.details_append_folderOnlyScreeshot(webdriver,"MaxBetInSettingsClicked");
								report.log(Status.PASS, "Clicked max bet button in settings",MediaEntityBuilder.createScreenCaptureFromPath(extentScreenShotPath+"\\MaxBetInSettingsClicked.jpg").build() );
							}
							else
							{
								imageLibrary.details_append_folderOnlyScreeshot(webdriver,"MaxBetInSettingsNotClicked");
								report.log(Status.FAIL, "Not Clicked max bet button in settings ",MediaEntityBuilder.createScreenCaptureFromPath(extentScreenShotPath+"\\MaxBetInSettingsNotClicked.jpg").build() );
							}
							//slider
							try
							{
							Thread.sleep(2000);
							webdriver.context("CHROMIUM");
							cfnlib.clickAtButton(cfnlib.XpathMap.get("SetSliderBet"));
							Thread.sleep(2000);
							webdriver.context("NATIVE_APP");
							
							imageLibrary.details_append_folderOnlyScreeshot(webdriver,"SettingsSliderWorking");
							report.log(Status.PASS, "Settings slider is working",MediaEntityBuilder.createScreenCaptureFromPath(extentScreenShotPath+"\\SettingsSliderWorking.jpg").build() );						
							
							}catch (Exception e) {
								Thread.sleep(2000);
								log.error(e.getMessage(),e);
								cfnlib.evalException(e);
								}
								
							}
							try
							{
								webdriver.context("CHROMIUM");
								cfnlib.refresh();
								Thread.sleep(7000);
								//cfnlib.func_SwipeUp();
								Thread.sleep(2000);
								webdriver.context("NATIVE_APP");
							}
							catch (Exception e) {
								Thread.sleep(2000);
								log.error(e.getMessage(),e);
								cfnlib.evalException(e);
								}
									
									if("YES".equalsIgnoreCase(cfnlib.XpathMap.get("NFD")))
									{
										if(cfnlib.XpathMap.get("GameName").equalsIgnoreCase("Extreme"))
										{
											cfnlib.tapOnCoordinates(200,200);
										}
										else
										{
											imageLibrary.waitTillNFDButtonVisible();
											imageLibrary.click("NFDButton");						
										}
									}
									
									if(imageLibrary.isImageAppears("Spin"))
									{
										imageLibrary.details_append_folderOnlyScreeshot(webdriver,"RefreshAfterBetMenuSuccessful");
										report.log(Status.PASS, "Refresh After BetMenu Successful ",MediaEntityBuilder.createScreenCaptureFromPath(extentScreenShotPath+"\\RefreshAfterBetMenuSuccessful.jpg").build() );
									}
									else
									{
										imageLibrary.details_append_folderOnlyScreeshot(webdriver,"RefreshAfterBetMenuNotSuccessful");
										report.log(Status.FAIL, "Refresh After BetMenu not Successful ",MediaEntityBuilder.createScreenCaptureFromPath(extentScreenShotPath+"\\RefreshAfterBetMenuNotSuccessful.jpg").build() );
									}
						}
						else
						{
							imageLibrary.details_append_folderOnlyScreeshot(webdriver,"SettingNotWorking");
							report.log(Status.FAIL, "Clicked setting is not working",MediaEntityBuilder.createScreenCaptureFromPath(extentScreenShotPath+"\\SettingNotWorking.jpg").build() );						
						}	
	             }catch (Exception e) {
						Thread.sleep(2000);
						log.error(e.getMessage(),e);
						cfnlib.evalException(e);
						}
	             
	             //close working in paytable and settings
	             try
	             {
	            	 imageLibrary.click("Menu");
						imageLibrary.waitTillBtnVisible("Settings");							
						imageLibrary.click("Settings");
						if(imageLibrary.isImageAppears("PaytableClose"))
							imageLibrary.click("PaytableClose");
						
						if(imageLibrary.isImageAppears("Settings"))
						{
							imageLibrary.details_append_folderOnlyScreeshot(webdriver,"SettingsClosed");
							report.log(Status.PASS, "Closed Settings",MediaEntityBuilder.createScreenCaptureFromPath(extentScreenShotPath+"\\SettingsClosed.jpg").build() );
						}
						else
						{
							imageLibrary.details_append_folderOnlyScreeshot(webdriver,"SettingsNtClosed");
							report.log(Status.FAIL, "Settings not closed",MediaEntityBuilder.createScreenCaptureFromPath(extentScreenShotPath+"\\SettingsNotClosed.jpg").build() );
						}
						
						//paytable
						imageLibrary.waitTillBtnVisible("Paytable");							
						imageLibrary.click("Paytable");
						if(imageLibrary.isImageAppears("PaytableClose"))
							imageLibrary.click("PaytableClose");
						
						if(imageLibrary.isImageAppears("Paytable"))
						{
							imageLibrary.details_append_folderOnlyScreeshot(webdriver,"PaytableClosed");
							report.log(Status.PASS, "Closed paytable",MediaEntityBuilder.createScreenCaptureFromPath(extentScreenShotPath+"\\PaytableClosed.jpg").build() );
						}
						else
						{
							imageLibrary.details_append_folderOnlyScreeshot(webdriver,"PaytableNtClosed");
							report.log(Status.FAIL, "Paytable not closed",MediaEntityBuilder.createScreenCaptureFromPath(extentScreenShotPath+"\\PaytableNotClosed.jpg").build() );
						}
						
						imageLibrary.click("Menu");
						
						if(imageLibrary.isImageAppears("Spin"))
						{
							imageLibrary.details_append_folderOnlyScreeshot(webdriver,"MenuClosed");
							report.log(Status.PASS, "Closed Menu",MediaEntityBuilder.createScreenCaptureFromPath(extentScreenShotPath+"\\MenuClosed.jpg").build() );
						}
						else
						{
							imageLibrary.details_append_folderOnlyScreeshot(webdriver,"MenuNtClosed");
							report.log(Status.FAIL, "Menu not closed",MediaEntityBuilder.createScreenCaptureFromPath(extentScreenShotPath+"\\MenuNotClosed.jpg").build() );
						}
	             }catch (Exception e) {
						Thread.sleep(2000);
						log.error(e.getMessage(),e);
						cfnlib.evalException(e);
						}
	             
	             
	             //betmenu
	             try
					{
						imageLibrary.click("BetMenu");
						
						
						if(imageLibrary.isImageAppears("MaxBet"))
						{	
						imageLibrary.details_append_folderOnlyScreeshot(webdriver,"BetisWorking");
						report.log(Status.PASS, "Bet button is working",MediaEntityBuilder.createScreenCaptureFromPath(extentScreenShotPath+"\\BetisWorking.jpg").build() );					
						
						
						if((cfnlib.XpathMap.get("GameName").equalsIgnoreCase("Extreme"))!= true)
						{
						imageLibrary.click("MaxBet");
						
						
						if(imageLibrary.isImageAppears("MaxBetSelected"))
						{
							imageLibrary.details_append_folderOnlyScreeshot(webdriver,"BetChangedDecreased");
							report.log(Status.PASS, "Bet changed successfully",MediaEntityBuilder.createScreenCaptureFromPath(extentScreenShotPath+"\\BetChangedDecreased.jpg").build() );
						}
						else
						{
							imageLibrary.details_append_folderOnlyScreeshot(webdriver,"BetNotChangedDecreased");
							report.log(Status.FAIL, "Bet did not changed successfully",MediaEntityBuilder.createScreenCaptureFromPath(extentScreenShotPath+"\\BetNotChangedDecreased.jpg").build() );
						}
						}
						//refresh
						try
						{
							webdriver.context("CHROMIUM");
							cfnlib.refresh();
							Thread.sleep(7000);
							//cfnlib.func_SwipeUp();
							Thread.sleep(2000);
							webdriver.context("NATIVE_APP");
							if("YES".equalsIgnoreCase(cfnlib.XpathMap.get("NFD")))
							{
								if(cfnlib.XpathMap.get("GameName").equalsIgnoreCase("Extreme"))
								{
									cfnlib.tapOnCoordinates(200,200);
								}
								else
								{
									imageLibrary.waitTillNFDButtonVisible();
									imageLibrary.click("NFDButton");						
								}
							}
							if(imageLibrary.waitTillBtnVisible("Spin"))
							{
								imageLibrary.details_append_folderOnlyScreeshot(webdriver,"RefreshAfterBetMenuSuccessful");
								report.log(Status.PASS, "Refresh After BetMenu Successful ",MediaEntityBuilder.createScreenCaptureFromPath(extentScreenShotPath+"\\RefreshAfterBetMenuSuccessful.jpg").build() );
							}
							else
							{
								imageLibrary.details_append_folderOnlyScreeshot(webdriver,"RefreshAfterBetMenuNotSuccessful");
								report.log(Status.FAIL, "Refresh After BetMenu not Successful ",MediaEntityBuilder.createScreenCaptureFromPath(extentScreenShotPath+"\\RefreshAfterBetMenuNotSuccessful.jpg").build() );
							}
						}catch (Exception e) {
							Thread.sleep(2000);
							log.error(e.getMessage(),e);
							cfnlib.evalException(e);
							}
						
						}
						else
						{
						imageLibrary.details_append_folderOnlyScreeshot(webdriver,"BetisnotWorking");
						report.log(Status.FAIL, "Bet button is not working",MediaEntityBuilder.createScreenCaptureFromPath(extentScreenShotPath+"\\BetisnotWorking.jpg").build() );							
						}
					}catch (Exception e) {
						Thread.sleep(2000);
						log.error(e.getMessage(),e);
						cfnlib.evalException(e);
						}
						
	             //spin with changed bet
	             try
	             {
					 if((cfnlib.XpathMap.get("GameName").equalsIgnoreCase("Extreme"))!= true)
						{
	            	 imageLibrary.click("BetMenu");
						
						if(imageLibrary.isImageAppears("MaxBet"))
							imageLibrary.click("MaxBet");
						
						cfnlib.tapOnCoordinates(200, 300);
						
						if(imageLibrary.click("Spin"))
						{
							imageLibrary.details_append_folderOnlyScreeshot(webdriver,"SpinWithChangedBet");
							report.log(Status.PASS, "Spin successfull with changed bet",MediaEntityBuilder.createScreenCaptureFromPath(extentScreenShotPath+"\\SpinWithChangedBet.jpg").build() );
						}
						else
						{
							imageLibrary.details_append_folderOnlyScreeshot(webdriver,"NoSpinWithChangedBet");
							report.log(Status.FAIL, "Spin not successfull with changed bet",MediaEntityBuilder.createScreenCaptureFromPath(extentScreenShotPath+"\\NoSpinWithChangedBet.jpg").build() );
						}
						}
	             }catch (Exception e) {
						Thread.sleep(2000);
						log.error(e.getMessage(),e);
						cfnlib.evalException(e);
						}
	             */
	             
	             report.log(Status.INFO, "Toggle button in settings to be handled manually");
	             report.log(Status.INFO, "Titan help menu to be handled manually");
	             report.log(Status.INFO, "Bet menu wheel bet values to be handled manually");
	             
			}
		}
		//-------------------Handling the exception---------------------//
				catch (Exception e) {
					log.error(e.getMessage(),e);
					cfnlib.evalException(e);
				}
				finally
				{	
					extent.flush();
					languageReport.endReport();
					webdriver.close();
					webdriver.quit();
					proxy.abort();
					Thread.sleep(3000);
				}	
	}
}
