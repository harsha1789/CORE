package Modules.Regression.TestScript;

/**
 * @author Sai Priyanka Emany
 * games covered:  book of atem wowpot
 *                 electric avenue
 */

/**
 * functionalities covered:
 * 
 * load game
 * refresh on load game
 * Spin functionality
 * help functionality
 * Bet Menu functionality
 * Autoplay functionality
 * Menu - Paytable functionality
 *        Settings functionality
 *        help functionality
 * Lobby functionality
 */

import java.util.regex.Matcher;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import com.zensar.automation.framework.model.ScriptParameters;
import com.zensar.automation.framework.report.Desktop_HTML_Report;
import com.zensar.automation.library.CommonUtil;
import com.zensar.automation.library.ImageLibrary;

import Modules.Regression.FunctionLibrary.CFNLibrary_Desktop;
import Modules.Regression.FunctionLibrary.CFNLibrary_Desktop_CS;
import Modules.Regression.FunctionLibrary.CFNLibrary_Desktop_Force;
import net.lightbody.bmp.BrowserMobProxyServer;

public class Desktop_OBAT_Regression_All41Studios {

	
	public static Logger log = Logger.getLogger(Desktop_OBAT_Regression_All41Studios.class.getName());
	public ScriptParameters scriptParameters;
	
	public void script() throws Exception{
		
		String mstrTC_Name=scriptParameters.getMstrTCName();
		String mstrTC_Desc=scriptParameters.getMstrTCDesc();
		String mstrModuleName=scriptParameters.getMstrModuleName();
		WebDriver webdriver=scriptParameters.getDriver();
		BrowserMobProxyServer proxy=scriptParameters.getProxy();
		String startTime=scriptParameters.getStartTime();
		String filePath=scriptParameters.getFilePath();
		String userName=scriptParameters.getUserName();
		String Browsername=scriptParameters.getBrowserName();
		String Framework=scriptParameters.getFramework();
		String GameName=scriptParameters.getGameName();

		String status=null;
		int mintDetailCount=0;
		//int mintSubStepNo=0;
		int mintPassed=0;
		int mintFailed=0;
		int mintWarnings=0;
		int mintStepNo=0;
		

		Desktop_HTML_Report languageReport = new Desktop_HTML_Report(webdriver,Browsername,filePath,startTime,mstrTC_Name,mstrTC_Desc,mstrModuleName,mintDetailCount,mintPassed,mintFailed,mintWarnings,mintStepNo,status,GameName);
		WebDriverWait Wait = new WebDriverWait(webdriver, 30);
		CFNLibrary_Desktop cfnlib=null;

		//Extent report
		ExtentHtmlReporter htmlReporter;

		ExtentReports extent=null;
		ExtentTest report = null,report1;
		int startindex=0;
		String strGameName =null;
		

		if(GameName.contains("Desktop"))
		{   
			String Gamename=GameName.replace("Desktop", "");
			java.util.regex.Pattern  str=java.util.regex.Pattern.compile("Desktop");
			Matcher  substing=str.matcher(GameName);
			while(substing.find())
			{
				startindex=substing.start();													
			}
			strGameName=GameName.substring(0, startindex);
			log.debug("newgamename="+strGameName);
		}
		else
		{
			strGameName=GameName;
		}

		Browsername = Browsername.toUpperCase();
		// initialize the HtmlReporter ToDO :Read it from report path	
		String path=System.getProperty("user.dir");
		htmlReporter= new ExtentHtmlReporter(path+"\\output\\"+GameName+(new CommonUtil()).createTimeStampStr()+mstrTC_Name+".html");//ScreenRecorder
		//ATUTestRecorder record = new ATUTestRecorder("D:\\ImageComparision\\"+strGameName, GameName.concat("_"+languageReport.createTimeStampStr()),false);//Screen recorder
		//initialize ExtentReports and attach the HtmlReporter

		extent= new ExtentReports();
		extent.attachReporter(htmlReporter);

		htmlReporter.config().setChartVisibilityOnOpen(true);
		htmlReporter.config().setDocumentTitle("Automation Test Script Excution summary");
		htmlReporter.config().setReportName("OBAT Test Script Excution summary");

		htmlReporter.config().setTheme(Theme.STANDARD);
		htmlReporter.config().setTimeStampFormat("EEEE, MMMM dd, yyyy, hh:mm a '('zzz')'");		

		if(Framework.equalsIgnoreCase("CS"))
		{
			cfnlib = new CFNLibrary_Desktop_CS(webdriver, proxy, languageReport, GameName);	

		}
		else if(Framework.equalsIgnoreCase("Force"))
		{
			cfnlib = new CFNLibrary_Desktop_Force(webdriver, proxy, languageReport, GameName);	
		}
		else
		{
			cfnlib = new CFNLibrary_Desktop(webdriver, proxy, languageReport, GameName);	
		}

		String url = cfnlib.XpathMap.get("ApplicationURL");
		ImageLibrary imageLibrary = new ImageLibrary(webdriver, strGameName, "Desktop");
		
		try
		{
			if(webdriver != null)
			{
				/*report=extent.createTest("OBATTestCases");

				System.out.println("url = " +url);								
				String extentScreenShotPath = cfnlib.extentScreenShotPath;

				//Launch Game URL

				if("Yes".equalsIgnoreCase(cfnlib.XpathMap.get("Clock")))
				{
					cfnlib.loadGame(url);
					}
				else
				{
					cfnlib.webdriver.navigate().to(url);
					cfnlib.waitForPageToBeReady();
				}
				
				Thread.sleep(13000);
				
				//nfd functionality
				try
				{			
					//NFD Functionality
					if("YES".equalsIgnoreCase(cfnlib.XpathMap.get("NFD")))
					{	
						if(imageLibrary.isImageAppears("NFDButton"))
						{
							imageLibrary.details_append_folderOnlyScreeshot(webdriver,"GameLoaded");
							report.log(Status.PASS, "Game Loaded ",MediaEntityBuilder.createScreenCaptureFromPath(extentScreenShotPath+"\\GameLoaded.jpg").build() );

							//Click on NFD
							imageLibrary.click("NFDButton");
							
						}
						else
						{
							imageLibrary.details_append_folderOnlyScreeshot(webdriver,"GameNtLoaded");
							report.log(Status.FAIL, "Game not yet Loaded ",MediaEntityBuilder.createScreenCaptureFromPath(extentScreenShotPath+"\\GameLoaded.jpg").build() );
						}
					}
					
					//check if in base game
					if(imageLibrary.isImageAppears("SpinBtn"))
					{
						imageLibrary.details_append_folderOnlyScreeshot(webdriver,"BaseGameVisible");
						report.log(Status.PASS, "Base Game Loaded ",MediaEntityBuilder.createScreenCaptureFromPath(extentScreenShotPath+"\\BaseGameVisible.jpg").build() );
					}
					else
					{
						imageLibrary.details_append_folderOnlyScreeshot(webdriver,"BaseGameVisibleNt");
						report.log(Status.FAIL, "Base Game not yet Loaded ",MediaEntityBuilder.createScreenCaptureFromPath(extentScreenShotPath+"\\BaseGameVisibleNt.jpg").build() );
					}
				}
				catch(Exception e)
				{
					log.error(e.getMessage(),e);
					cfnlib.evalException(e);
				}

				//Check refresh on Game Load.				
				try 
				{
					cfnlib.RefreshGame("clock");
					Thread.sleep(9000);
					if("YES".equalsIgnoreCase(cfnlib.XpathMap.get("NFD")))
					{	
						if(imageLibrary.isImageAppears("NFDButton"))
						{
							imageLibrary.details_append_folderOnlyScreeshot(webdriver,"GameLoadedAfterRefresh");
							report.log(Status.PASS, "Game Loaded after refresh ",MediaEntityBuilder.createScreenCaptureFromPath(extentScreenShotPath+"\\GameLoadedAfterRefresh.jpg").build() );

							imageLibrary.click("NFDButton");
							
						}
						else
						{
							imageLibrary.details_append_folderOnlyScreeshot(webdriver,"GameLoadedAfterRefreshNt");
							report.log(Status.FAIL, "Game not yet Loaded after refresh ",MediaEntityBuilder.createScreenCaptureFromPath(extentScreenShotPath+"\\GameLoadedAfterRefreshNt.jpg").build() );
						}
					}
					
					
					if(imageLibrary.isImageAppears("SpinBtn"))
					{
						imageLibrary.details_append_folderOnlyScreeshot(webdriver,"BaseGameVisibleAfterRefresh");
						report.log(Status.PASS, "Base Game Loaded after refresh ",MediaEntityBuilder.createScreenCaptureFromPath(extentScreenShotPath+"\\BaseGameVisibleAfterRefresh.jpg").build() );
					}
					else
					{
						imageLibrary.details_append_folderOnlyScreeshot(webdriver,"BaseGameVisibleAfterRefreshNt");
						report.log(Status.FAIL, "Base Game not yet Loaded after refresh ",MediaEntityBuilder.createScreenCaptureFromPath(extentScreenShotPath+"\\BaseGameVisibleAfterRefreshNt.jpg").build() );
					}

					System.out.println("Refresh");

				}
				catch (Exception e) 
				{
					log.error(e.getMessage(),e);
					cfnlib.evalException(e);
				}
				
				//spin with default bet
				if(imageLibrary.isImageAppears("SpinBtn"))
				{
					if(imageLibrary.click("SpinBtn"))
					{
						imageLibrary.details_append_folderOnlyScreeshot(webdriver,"SpinSuccessfull");
						report.log(Status.PASS, "spin clicked successfully ",MediaEntityBuilder.createScreenCaptureFromPath(extentScreenShotPath+"\\SpinSuccessfull.jpg").build() );
					}
					else
					{
						imageLibrary.details_append_folderOnlyScreeshot(webdriver,"SpinNtSuccessfull");
						report.log(Status.FAIL, "couldn't click on spin button",MediaEntityBuilder.createScreenCaptureFromPath(extentScreenShotPath+"\\SpinNtSuccessfull.jpg").build() );
					}	
					}
				
				//browser full screen
				try
				{
					cfnlib.resizeBrowser(400, 200);
                      Thread.sleep(9000);
                      imageLibrary.details_append_folderOnlyScreeshot(webdriver,"Resize done");
                      report.log(Status.PASS,"resize done", MediaEntityBuilder.createScreenCaptureFromPath(extentScreenShotPath+"\\resize.jpg").build() );
                      
                      
                      cfnlib.browserFullscreen();
                      Thread.sleep(9000);
                      imageLibrary.details_append_folderOnlyScreeshot(webdriver,"full screen done");
                      report.log(Status.PASS,"full screen done", MediaEntityBuilder.createScreenCaptureFromPath(extentScreenShotPath+"\\fullscreen.jpg").build() );
                      
                   
                      //maximize
                      cfnlib.maxiMizeBrowser();
                      Thread.sleep(9000);
				}
				catch (Exception e) 
				{
					log.error(e.getMessage(),e);
					cfnlib.evalException(e);
				}
				
				
				
				
				//bet menu functionality
				//refresh on bet menu
				try
				{
					if(imageLibrary.isImageAppears("BetMenu"))
					{
						imageLibrary.details_append_folderOnlyScreeshot(webdriver,"BetMenuVisible");
						report.log(Status.PASS, "BetMenu  button is visible",MediaEntityBuilder.createScreenCaptureFromPath(extentScreenShotPath+"\\BetMenuVisible.jpg").build() );						
				}
			  else
				{
					imageLibrary.details_append_folderOnlyScreeshot(webdriver,"BetMenuNtVisible");
					report.log(Status.FAIL, "BetMenu button is not visible",MediaEntityBuilder.createScreenCaptureFromPath(extentScreenShotPath+"\\BetMenuNtVisible.jpg").build() );						
				}
					
					imageLibrary.click("BetMenu");
					Thread.sleep(2000);
					if(imageLibrary.isImageAppears("MaxBet"))
					{
						imageLibrary.details_append_folderOnlyScreeshot(webdriver,"BetMenuOpen");
						report.log(Status.PASS, "BetMenu  is opened max bet visible",MediaEntityBuilder.createScreenCaptureFromPath(extentScreenShotPath+"\\BetMenuOpen.jpg").build() );						
				
					
						//refresh
							try
							{
								cfnlib.RefreshGame("clock");
								Thread.sleep(9000);
								
							}
							catch (Exception e)
								{
								Thread.sleep(2000);
								log.error(e.getMessage(),e);
								cfnlib.evalException(e);
								}
							
							if(cfnlib.XpathMap.get("NFD").equalsIgnoreCase("Yes"))
							{
								if(imageLibrary.isImageAppears("NFDButton"))
								{
									imageLibrary.click("NFDButton");
								}
							}
						
						//check if on base game after refresh
							if(imageLibrary.isImageAppears("SpinBtn"))
							{
								imageLibrary.details_append_folderOnlyScreeshot(webdriver,"BetMenuRefreshSuccessfull");
								report.log(Status.PASS, "Refresh betmenu,successfull ",MediaEntityBuilder.createScreenCaptureFromPath(extentScreenShotPath+"\\BetMenuRefreshSuccessfull.jpg").build() );
							}
							else
							{
								imageLibrary.details_append_folderOnlyScreeshot(webdriver,"BetMenuRefreshNtSuccessfull");
								report.log(Status.FAIL, "refresh betmenu, not successfull",MediaEntityBuilder.createScreenCaptureFromPath(extentScreenShotPath+"\\BetMenuRefreshNtSuccessfull.jpg").build() );						
							}
						}
				 else
				{
					imageLibrary.details_append_folderOnlyScreeshot(webdriver,"BetMenuNtOpen");
					report.log(Status.FAIL, "BetMenu may be not opened, max bet is not visible",MediaEntityBuilder.createScreenCaptureFromPath(extentScreenShotPath+"\\BetMenuNtOpen.jpg").build() );						
				}	
				}
				catch(Exception e)
				{
					Thread.sleep(1000);
					log.error(e.getMessage(),e);
					cfnlib.evalException(e);
				}
				
				//refresh on max bet select
				
				try
				{
					
					if(imageLibrary.isImageAppears("BetMenu"))
					{
						imageLibrary.click("BetMenu");
						Thread.sleep(2000);
						
						if(imageLibrary.isImageAppears("MaxBet"))
						{
							//click max bet
							imageLibrary.click("MaxBet");
							Thread.sleep(2000);
							imageLibrary.click("BetClicked");
							Thread.sleep(2000);
							if(imageLibrary.isImageAppears("MaxBetValue"))
							{
								imageLibrary.details_append_folderOnlyScreeshot(webdriver,"MaxBetSelected");
								report.log(Status.PASS, "MaxBet Selected",MediaEntityBuilder.createScreenCaptureFromPath(extentScreenShotPath+"\\MaxBetSelected.jpg").build() );						
							
							
								//refresh
								try
								{
									cfnlib.RefreshGame("clock");
									Thread.sleep(9000);
									
								}
								catch (Exception e)
									{
									Thread.sleep(2000);
									log.error(e.getMessage(),e);
									cfnlib.evalException(e);
									}
							
								if(cfnlib.XpathMap.get("NFD").equalsIgnoreCase("Yes"))
								{
									if(imageLibrary.isImageAppears("NFDButton"))
									{
										imageLibrary.click("NFDButton");
									}
								}
							//check if on base game after refresh
								if(imageLibrary.isImageAppears("MaxBetValue"))
								{
									imageLibrary.details_append_folderOnlyScreeshot(webdriver,"MaxBetRefreshNtSuccessfull");
									report.log(Status.FAIL, "refresh max bet, not successfull",MediaEntityBuilder.createScreenCaptureFromPath(extentScreenShotPath+"\\MaxBetRefreshNtSuccessfull.jpg").build() );						
								
										}
								else
								{
									imageLibrary.details_append_folderOnlyScreeshot(webdriver,"MaxBetRefreshSuccessfull");
									report.log(Status.PASS, "Refresh max bet,successfull ",MediaEntityBuilder.createScreenCaptureFromPath(extentScreenShotPath+"\\MaxBetRefreshSuccessfull.jpg").build() );
							
									}
							}
							else
							{
								imageLibrary.details_append_folderOnlyScreeshot(webdriver,"BetMenuNtSelected");
								report.log(Status.FAIL, "Maxbet not selected",MediaEntityBuilder.createScreenCaptureFromPath(extentScreenShotPath+"\\BetMenuNtSelected.jpg").build() );						
							}
						}
					}
				
				}catch (Exception e) {
					Thread.sleep(2000);
					log.error(e.getMessage(),e);
					cfnlib.evalException(e);
					}
				
				//spin with max bet
				//spin with max bet
				try
				{
					
					boolean betmenu=imageLibrary.isImageAppears("BetMenu");
					boolean clickmenu=imageLibrary.click("BetMenu");
					
					if(betmenu && clickmenu)
					{
						boolean maxbet=imageLibrary.isImageAppears("MaxBet");
						boolean clickmax=imageLibrary.click("MaxBet");
						
						if(maxbet && clickmax)
						{
							imageLibrary.click("BetClicked");
							Thread.sleep(2000);
							if(imageLibrary.isImageAppears("MaxBetValue"))
							{
								if(imageLibrary.click("SpinBtn"))
								{
									imageLibrary.details_append_folderOnlyScreeshot(webdriver,"SpinMaxBet");
									report.log(Status.PASS, "spin withmax bet ",MediaEntityBuilder.createScreenCaptureFromPath(extentScreenShotPath+"\\SpinMaxBet.jpg").build() );						
							}
						  else
							{
								imageLibrary.details_append_folderOnlyScreeshot(webdriver,"NoSpinMaxBet");
								report.log(Status.FAIL, "not spined with max bet",MediaEntityBuilder.createScreenCaptureFromPath(extentScreenShotPath+"\\NoSpinMaxBet.jpg").build() );						
							}
							}
							else
							{
								imageLibrary.details_append_folderOnlyScreeshot(webdriver,"FailedSlectMaxBet");
								report.log(Status.FAIL, "failed to slect max bet before spin",MediaEntityBuilder.createScreenCaptureFromPath(extentScreenShotPath+"\\FailedSlectMaxBet.jpg").build() );						
							}
							
						}
					}
					
				}catch (Exception e) {
					Thread.sleep(2000);
					log.error(e.getMessage(),e);
					cfnlib.evalException(e);
					}
				
				//spin with preset bet()
				try
				{
					boolean betmenu=imageLibrary.isImageAppears("BetMenu");
					boolean clickmenu=imageLibrary.click("BetMenu");
					
					if(betmenu && clickmenu)
					{
						boolean presetbet=imageLibrary.isImageAppears("PresetBet");
						boolean clickpreset=imageLibrary.click("PresetBet");
						
						if(presetbet && clickpreset)
						{
							imageLibrary.click("BetClicked");
							Thread.sleep(2000);
							if(imageLibrary.isImageAppears("PresetBetValue"))
							{
								if(imageLibrary.click("SpinBtn"))
								{
									imageLibrary.details_append_folderOnlyScreeshot(webdriver,"SpinPresetBet");
									report.log(Status.PASS, "spin with preset bet ",MediaEntityBuilder.createScreenCaptureFromPath(extentScreenShotPath+"\\SpinPresetBet.jpg").build() );						
							}
						  else
							{
								imageLibrary.details_append_folderOnlyScreeshot(webdriver,"NoSpinPresetBet");
								report.log(Status.FAIL, "not spined with Preset bet",MediaEntityBuilder.createScreenCaptureFromPath(extentScreenShotPath+"\\NoSpinPresetBet.jpg").build() );						
							}
							}
							else
							{
								imageLibrary.details_append_folderOnlyScreeshot(webdriver,"FailedSlectPresetBet");
								report.log(Status.FAIL, "failed to slect preset bet before spin",MediaEntityBuilder.createScreenCaptureFromPath(extentScreenShotPath+"\\FailedSlectPresetBet.jpg").build() );						
							}
							
						}
					}
					
				}catch (Exception e) {
					Thread.sleep(2000);
					log.error(e.getMessage(),e);
					cfnlib.evalException(e);
					}
				
				
				//autoplay functionality
				//refresh on autoplay menu
				try
				{
					if(imageLibrary.isImageAppears("Autoplay"))
					{
						imageLibrary.details_append_folderOnlyScreeshot(webdriver,"AutoplyBtnVisible");
						report.log(Status.PASS, "autoplay button is visible",MediaEntityBuilder.createScreenCaptureFromPath(extentScreenShotPath+"\\AutoplyBtnVisible.jpg").build() );						
				
					    //click
						imageLibrary.click("Autoplay");
						Thread.sleep(2000);
						if(imageLibrary.isImageAppears("AutoplayText"))
						{
							imageLibrary.details_append_folderOnlyScreeshot(webdriver,"InAutoplayMenu");
							report.log(Status.PASS, "AutoplayText is visible,in autoplay menu",MediaEntityBuilder.createScreenCaptureFromPath(extentScreenShotPath+"\\InAutoplayMenu.jpg").build() );
						
						
						 	//refresh 
							try
							{
								cfnlib.RefreshGame("clock");
								Thread.sleep(9000);
							}
							catch (Exception e)
								{
								Thread.sleep(2000);
								log.error(e.getMessage(),e);
								cfnlib.evalException(e);
								}
							
							
							//nfd click
							if(cfnlib.XpathMap.get("NFD").equalsIgnoreCase("Yes"))
							{
								imageLibrary.click("NFDButton");
								Thread.sleep(20000);
							}
							
							
						//check if on base game after refresh
							if(imageLibrary.isImageAppears("SpinBtn"))
							{
								imageLibrary.details_append_folderOnlyScreeshot(webdriver,"AutoplayMenuRefreshSuccessfull");
								report.log(Status.PASS, "Autoplay menu reefreshed,back to game",MediaEntityBuilder.createScreenCaptureFromPath(extentScreenShotPath+"\\AutoplayMenuRefreshSuccessfull.jpg").build() );
							}
							else
							{
								imageLibrary.details_append_folderOnlyScreeshot(webdriver,"AutoplayMenuRefreshNtSuccessfull");
								report.log(Status.FAIL, "refresh on Autoplaymenu not success",MediaEntityBuilder.createScreenCaptureFromPath(extentScreenShotPath+"\\AutoplayMenuRefreshNtSuccessfull.jpg").build() );						
							}
						}
						else
						{
							imageLibrary.details_append_folderOnlyScreeshot(webdriver,"NtInAutoplayMenu");
							report.log(Status.FAIL, "AutoplayText is not visible,not in autoplay menu",MediaEntityBuilder.createScreenCaptureFromPath(extentScreenShotPath+"\\NtInAutoplayMenu.jpg").build() );
						}
					}
			  else
				{
					imageLibrary.details_append_folderOnlyScreeshot(webdriver,"AutoplyBtnNtVisible");
					report.log(Status.FAIL, "autoplay button is not visible",MediaEntityBuilder.createScreenCaptureFromPath(extentScreenShotPath+"\\AutoplyBtnNtVisible.jpg").build() );						
				}
					
				}catch (Exception e) {
					Thread.sleep(2000);
					log.error(e.getMessage(),e);
					cfnlib.evalException(e);
					}
				
				//stop autoplay
				try
				{
					boolean autoplay=imageLibrary.isImageAppears("Autoplay");
					boolean autoplayclick=imageLibrary.click("Autoplay");
					
					if(autoplay && autoplayclick)
					{
						
						boolean nofspins=imageLibrary.isImageAppears("StartAutoplay");
						boolean click=imageLibrary.click("StartAutoplay");
						if(nofspins && click )
						{
							Thread.sleep(2000);
							if(imageLibrary.waitTillBtnVisible("AutoplayStop"))
								{
									
									imageLibrary.details_append_folderOnlyScreeshot(webdriver,"InAutoplayGame");
									report.log(Status.PASS, "autoplay game started",MediaEntityBuilder.createScreenCaptureFromPath(extentScreenShotPath+"\\InAutoplayGame.jpg").build() );
								
									imageLibrary.click("AutoplayStop");
									Thread.sleep(1500);
									if(imageLibrary.waitTillBtnVisible("SpinBtn"))
									{
										imageLibrary.details_append_folderOnlyScreeshot(webdriver,"AutoplayGameStopped");
										report.log(Status.PASS, "autoplay game stopped",MediaEntityBuilder.createScreenCaptureFromPath(extentScreenShotPath+"\\AutoplayGameStopped.jpg").build() );
									}
									else
									{
										imageLibrary.details_append_folderOnlyScreeshot(webdriver,"AutoplayGameNtStopped");
										report.log(Status.FAIL, "autoplay game not stopped",MediaEntityBuilder.createScreenCaptureFromPath(extentScreenShotPath+"\\AutoplayGameNtStopped.jpg").build() );						
									}
								}
								else
								{
									imageLibrary.details_append_folderOnlyScreeshot(webdriver,"NtInAutoplayGame");
									report.log(Status.FAIL, "autoplay game not started",MediaEntityBuilder.createScreenCaptureFromPath(extentScreenShotPath+"\\NtInAutoplayGame.jpg").build() );						
								}
							}
						
					}
				}catch (Exception e) {
					Thread.sleep(2000);
					log.error(e.getMessage(),e);
					cfnlib.evalException(e);
					}
				
				
				//refresh aautoplay
				try
				{
					boolean autoplay=imageLibrary.isImageAppears("Autoplay");
					boolean autoplayclick=imageLibrary.click("Autoplay");
					
					if(autoplay && autoplayclick)
					{
						
						boolean startautoplay=imageLibrary.isImageAppears("StartAutoplay");
							boolean startclick=imageLibrary.click("StartAutoplay");
							
							if(startautoplay && startclick)
							{
								Thread.sleep(2000);
								if(imageLibrary.waitTillBtnVisible("AutoplayStop"))
								{
									
									//refresh 
									try
									{
										cfnlib.RefreshGame("clock");
										Thread.sleep(9000);
										}
									catch (Exception e)
										{
										Thread.sleep(2000);
										log.error(e.getMessage(),e);
										cfnlib.evalException(e);
										}
									
									//nfd click
									if(cfnlib.XpathMap.get("NFD").equalsIgnoreCase("Yes"))
									{
										imageLibrary.click("NFDButton");
										Thread.sleep(2000);
									}
									
									
									if(imageLibrary.isImageAppears("SpinBtn"))
									{
										imageLibrary.details_append_folderOnlyScreeshot(webdriver,"AutoplayGameRefreshed");
										report.log(Status.PASS, "autoplay game refreshed",MediaEntityBuilder.createScreenCaptureFromPath(extentScreenShotPath+"\\AutoplayGameRefreshed.jpg").build() );
									}
									else
									{
										imageLibrary.details_append_folderOnlyScreeshot(webdriver,"AutoplayGameNtRefreshed");
										report.log(Status.FAIL, "autoplay game not refreshed",MediaEntityBuilder.createScreenCaptureFromPath(extentScreenShotPath+"\\AutoplayGameNtRefreshed.jpg").build() );						
									}
								}
								}
							
					}
				}catch (Exception e) {
					Thread.sleep(2000);
					log.error(e.getMessage(),e);
					cfnlib.evalException(e);
					}
				
				//menu functionality
				//refresh on menu open
				try
				{
					if(imageLibrary.isImageAppears("Menu"))
					{
						imageLibrary.details_append_folderOnlyScreeshot(webdriver,"MenuVisible");
						report.log(Status.PASS, "Menu  button is visible",MediaEntityBuilder.createScreenCaptureFromPath(extentScreenShotPath+"\\MenuVisible.jpg").build() );						
				}
			  else
				{
					imageLibrary.details_append_folderOnlyScreeshot(webdriver,"MenuNtVisible");
					report.log(Status.FAIL, "Menu button is not visible",MediaEntityBuilder.createScreenCaptureFromPath(extentScreenShotPath+"\\MenuNtVisible.jpg").build() );						
				}
					
					imageLibrary.click("Menu");
					Thread.sleep(2000);
					
					if(imageLibrary.isImageAppears("MenuClicked") &&  imageLibrary.isImageAppears("Paytable") && imageLibrary.isImageAppears("InMenuHelp"))
					{
						imageLibrary.details_append_folderOnlyScreeshot(webdriver,"MenuOpen");
						report.log(Status.PASS, "Menu is open",MediaEntityBuilder.createScreenCaptureFromPath(extentScreenShotPath+"\\MenuOpen.jpg").build() );						
					
						//refresh
						cfnlib.RefreshGame("clock");
						Thread.sleep(9000);
						
						if("Yes".equalsIgnoreCase(cfnlib.XpathMap.get("NFD")))
						{
							imageLibrary.click("NFDButton");
							Thread.sleep(9000);
						}
						
						//check if on base game
						if(imageLibrary.isImageAppears("SpinBtn"))
						{
							imageLibrary.details_append_folderOnlyScreeshot(webdriver,"MenuRefreshSuccessfull");
							report.log(Status.PASS, "Refresh menu,successfull",MediaEntityBuilder.createScreenCaptureFromPath(extentScreenShotPath+"\\MenuRefreshSuccessfull.jpg").build() );
						
						  }
						else
						{
							imageLibrary.details_append_folderOnlyScreeshot(webdriver,"MenuRefreshNtSuccessfull");
							report.log(Status.FAIL, "refresh menu, not successfull",MediaEntityBuilder.createScreenCaptureFromPath(extentScreenShotPath+"\\MenuRefreshNtSuccessfull.jpg").build() );						
						}
					}
					else
					{
						imageLibrary.details_append_folderOnlyScreeshot(webdriver,"MenuNtOpen");
						report.log(Status.FAIL, "Menu is not open ",MediaEntityBuilder.createScreenCaptureFromPath(extentScreenShotPath+"\\MenuNtOpen.jpg").build() );						
					}
				}
				catch (Exception e) {
					Thread.sleep(2000);
					log.error(e.getMessage(),e);
					cfnlib.evalException(e);
					}
				
				//paytable functionality
				try
				{
					if(imageLibrary.isImageAppears("Menu"))
					{
						imageLibrary.click("Menu");
						Thread.sleep(2000);
						
						if(imageLibrary.isImageAppears("Paytable"))
						{
							imageLibrary.details_append_folderOnlyScreeshot(webdriver,"PaytableVisible");
							report.log(Status.PASS, "Paytable  button is visible",MediaEntityBuilder.createScreenCaptureFromPath(extentScreenShotPath+"\\PaytableVisible.jpg").build() );						
						}
						else
						{
							imageLibrary.details_append_folderOnlyScreeshot(webdriver,"PaytableNtVisible");
							report.log(Status.FAIL, "Paytable button is not visible",MediaEntityBuilder.createScreenCaptureFromPath(extentScreenShotPath+"\\PaytableNtVisible.jpg").build() );						
						}	
						
						imageLibrary.click("Paytable");
						Thread.sleep(2000);
						
						if(imageLibrary.isImageAppears("PaytableBack"))
						{
							imageLibrary.details_append_folderOnlyScreeshot(webdriver,"InPaytable");
							report.log(Status.PASS, "Paytable  is open",MediaEntityBuilder.createScreenCaptureFromPath(extentScreenShotPath+"\\InPaytable.jpg").build() );						
						
						     //scroll through paytable
							cfnlib.paytableScroll(cfnlib.XpathMap.get("PaytableLastElement"));
							Thread.sleep(2000);
							
							imageLibrary.details_append_folderOnlyScreeshot(webdriver,"Paytablescrolled");
							report.log(Status.PASS, "Paytable last image visible paytable scrolled",MediaEntityBuilder.createScreenCaptureFromPath(extentScreenShotPath+"\\PaytableScrolled.jpg").build() );
								
							
							//close paytable
							imageLibrary.click("PaytableBack");
							Thread.sleep(2000);
							//check if on base game
							if(imageLibrary.isImageAppears("SpinBtn"))
							{
								imageLibrary.details_append_folderOnlyScreeshot(webdriver,"PaytableBackSuccessfull");
								report.log(Status.PASS, "clicked paytable back,on base game ",MediaEntityBuilder.createScreenCaptureFromPath(extentScreenShotPath+"\\PaytableBackSuccessfull.jpg").build() );
							}										   									
							else
							{
								imageLibrary.details_append_folderOnlyScreeshot(webdriver,"PaytableBackNtSuccessfull");
								report.log(Status.FAIL, "clicked paytable back may not be successfull,not on base game ",MediaEntityBuilder.createScreenCaptureFromPath(extentScreenShotPath+"\\PaytableBackNtSuccessfull.jpg").build() );						
							}
						
						}
						else
						{
							imageLibrary.details_append_folderOnlyScreeshot(webdriver,"NtInPaytable");
							report.log(Status.FAIL, "Paytable is not open",MediaEntityBuilder.createScreenCaptureFromPath(extentScreenShotPath+"\\NtInPaytable.jpg").build() );						
						}	
							
					}
				}catch(Exception e)
				{
					Thread.sleep(1000);
					log.error(e.getMessage(),e);
					cfnlib.evalException(e);
				}
				
				//paytable refresh
				try
			    {
			    	if(imageLibrary.isImageAppears("Menu"))
					{
					    imageLibrary.click("Menu");	
					    Thread.sleep(3000);
					}
					
					if(imageLibrary.isImageAppears("Paytable"))
					{
						imageLibrary.click("Paytable");
						Thread.sleep(2000);
					}
					
					if(imageLibrary.isImageAppears("PaytableBack"))
					{
						
						//refresh
						try
						{
							cfnlib.RefreshGame("clock");
							Thread.sleep(9000);
						}catch (Exception e) {
							Thread.sleep(2000);
							log.error(e.getMessage(),e);
							cfnlib.evalException(e);
						}
						if(cfnlib.XpathMap.get("NFD").equalsIgnoreCase("Yes"))
						{
							if(imageLibrary.isImageAppears("NFDButton"))
							{
								imageLibrary.click("NFDButton");
							}	
							}
					if(imageLibrary.isImageAppears("SpinBtn"))
					{	
						imageLibrary.details_append_folderOnlyScreeshot(webdriver,"RefreshPaytableDone");
						report.log(Status.PASS, "refresh on paytable done,spin button visible",MediaEntityBuilder.createScreenCaptureFromPath(extentScreenShotPath+"\\RefreshPaytableDone.jpg").build() );
					}
					else
					{
						imageLibrary.details_append_folderOnlyScreeshot(webdriver,"RefreshPaytableNtDone");
						report.log(Status.FAIL, "refresh on paytable not done,spin button not visible",MediaEntityBuilder.createScreenCaptureFromPath(extentScreenShotPath+"\\RefreshPaytableNtDone.jpg").build() );						
					}	
					
					}
					
			    }catch (Exception e) {
					Thread.sleep(2000);
					log.error(e.getMessage(),e);
					cfnlib.evalException(e);
					}
				
				//settings
				try
				{
					if("Yes".equalsIgnoreCase(cfnlib.XpathMap.get("SettingsInMenu")))
					{
						if(imageLibrary.isImageAppears("Menu"))
						{
							imageLibrary.click("Menu");
						}
						
						if(imageLibrary.isImageAppears("Settings"))
						{
							imageLibrary.details_append_folderOnlyScreeshot(webdriver,"SettingsVisible");
							report.log(Status.PASS, "Settings  button is visible",MediaEntityBuilder.createScreenCaptureFromPath(extentScreenShotPath+"\\SettingsVisible.jpg").build() );						
						}
						else
						{
							imageLibrary.details_append_folderOnlyScreeshot(webdriver,"SettingsNtVisible");
							report.log(Status.FAIL, "Settings button is not visible",MediaEntityBuilder.createScreenCaptureFromPath(extentScreenShotPath+"\\SettingsNtVisible.jpg").build() );						
						}
						
						imageLibrary.click("Settings");
						
						if(imageLibrary.isImageAppears("SettingsText"))
						{
							imageLibrary.details_append_folderOnlyScreeshot(webdriver,"InSettings");
							report.log(Status.PASS, "Settings menu is open,settings text visible",MediaEntityBuilder.createScreenCaptureFromPath(extentScreenShotPath+"\\InSettings.jpg").build() );						
						
							//refresh
							try
							{
								cfnlib.RefreshGame("clock");
							}catch (Exception e) {
								Thread.sleep(2000);
								log.error(e.getMessage(),e);
								cfnlib.evalException(e);
							}
							if(cfnlib.XpathMap.get("NFD").equalsIgnoreCase("Yes"))
							{
								if(imageLibrary.isImageAppears("NFDButton"))
								{
									imageLibrary.click("NFDButton");
								}	
								}
						if(imageLibrary.isImageAppears("SpinBtn"))
						{	
							imageLibrary.details_append_folderOnlyScreeshot(webdriver,"RefreshSettingsDone");
							report.log(Status.PASS, "refresh on settings done,spin button visible",MediaEntityBuilder.createScreenCaptureFromPath(extentScreenShotPath+"\\RefreshSettingsDone.jpg").build() );
						}
						else
						{
							imageLibrary.details_append_folderOnlyScreeshot(webdriver,"RefreshSettingsNtDone");
							report.log(Status.FAIL, "refresh on settings not done,spin button not visible",MediaEntityBuilder.createScreenCaptureFromPath(extentScreenShotPath+"\\RefreshSettingsNtDone.jpg").build() );						
						}	
						
						
						}
						else
						{
							imageLibrary.details_append_folderOnlyScreeshot(webdriver,"NtInSettings");
							report.log(Status.FAIL, "Settingsmenu open,settings text is not visible",MediaEntityBuilder.createScreenCaptureFromPath(extentScreenShotPath+"\\NtInSettings.jpg").build() );						
						}	
						
					}
				}catch (Exception e) {
					Thread.sleep(2000);
					log.error(e.getMessage(),e);
					cfnlib.evalException(e);
					}
				
				//help in menu
				try
				{
					if(cfnlib.XpathMap.get("HelpInMenu").equalsIgnoreCase("Yes"))
					{
						if(imageLibrary.isImageAppears("Menu"))
						{
						    imageLibrary.click("Menu");	
						    Thread.sleep(2000);
						}
						
						if(imageLibrary.isImageAppears("InMenuHelp"))
						{
							imageLibrary.details_append_folderOnlyScreeshot(webdriver,"HelpInMenuVisible");
							report.log(Status.PASS, "help in menu  button is visible",MediaEntityBuilder.createScreenCaptureFromPath(extentScreenShotPath+"\\HelpInMenuVisible.jpg").build() );						
						}
						else
						{
							imageLibrary.details_append_folderOnlyScreeshot(webdriver,"HelpInMenuNtVisible");
							report.log(Status.FAIL, "Help in menu button is not visible",MediaEntityBuilder.createScreenCaptureFromPath(extentScreenShotPath+"\\HelpInMenuNtVisible.jpg").build() );						
						}	
						
						imageLibrary.click("InMenuHelp");
						Thread.sleep(2000);
						
						if("Yes".equalsIgnoreCase(cfnlib.XpathMap.get("HelpWindow")))
						{
							cfnlib.maxiMizeBrowser();
							String helptitle=cfnlib.getPageTitle(webdriver);
							System.out.println(helptitle);
							
							imageLibrary.details_append_folderOnlyScreeshot(webdriver,"HelpIsNavigatedFromMenu");
							report.log(Status.PASS, "Help Page is Visible,navigated from menu",MediaEntityBuilder.createScreenCaptureFromPath(extentScreenShotPath+"\\HelpIsNtNavigatedFromMenu.jpg").build() );
							cfnlib.navigate_back();
						
						}
						else
						{
						
							imageLibrary.details_append_folderOnlyScreeshot(webdriver,"HelpIsVisible");
							report.log(Status.PASS, "Help Page is Visible",MediaEntityBuilder.createScreenCaptureFromPath(extentScreenShotPath+"\\HelpCloseVisible.jpg").build() );

							cfnlib.webdriver.navigate().back();
						
						}
							//back to game
							if(cfnlib.XpathMap.get("NFD").equalsIgnoreCase("Yes"))
							{
								if(imageLibrary.isImageAppears("NFDButton"))
								{
									imageLibrary.click("NFDButton");
								}
							}
							else
							{
								log.debug("Nfd not seen");
							}
							if(imageLibrary.isImageAppears("SpinBtn"))
							{
								imageLibrary.details_append_folderOnlyScreeshot(webdriver,"BackFromHelpFromMenu");
								report.log(Status.PASS, "navigated to help from menu",MediaEntityBuilder.createScreenCaptureFromPath(extentScreenShotPath+"\\BackFromHelpFromMenu.jpg").build() );
							}
							else
							{
								imageLibrary.details_append_folderOnlyScreeshot(webdriver,"NtBackFromHelpFromMenu");
								report.log(Status.PASS, "not navigated to help from menu",MediaEntityBuilder.createScreenCaptureFromPath(extentScreenShotPath+"\\NtBackFromHelpFromMenu.jpg").build() );
							}	
						
						
					}
				}catch (Exception e) {
					Thread.sleep(2000);
					log.error(e.getMessage(),e);
					cfnlib.evalException(e);
					}
				
				//lobby cancel
				try
				{
					
						if(imageLibrary.isImageAppears("Lobby"))
						{
							imageLibrary.click("Lobby");
							
							if(imageLibrary.isImageAppears("Cancel"))
							{
								imageLibrary.click("Cancel");
							}
							
							if(imageLibrary.isImageAppears("Lobby"))
							{
								imageLibrary.details_append_folderOnlyScreeshot(webdriver,"LobbyCanceled");
								report.log(Status.PASS, "Lobby cancel clicked",MediaEntityBuilder.createScreenCaptureFromPath(extentScreenShotPath+"\\LobbyCanceled.jpg").build() );
							}
							else
							{
								imageLibrary.details_append_folderOnlyScreeshot(webdriver,"LobbyNtCanceled");
								report.log(Status.PASS, "Lobby cancel not clicked",MediaEntityBuilder.createScreenCaptureFromPath(extentScreenShotPath+"\\LobbyNtCanceled.jpg").build() );
							}	
						}
					
				}catch (Exception e) {
					Thread.sleep(2000);
					log.error(e.getMessage(),e);
					cfnlib.evalException(e);
					}
				
				//help menu
				try 
				{
					if("Yes".equalsIgnoreCase(cfnlib.XpathMap.get("HelpMenu")))
					{
						cfnlib.func_Click_BYID(cfnlib.XpathMap.get("HelpMenu_Id"));

					}
					if(imageLibrary.isImageAppears("Help"))
					{
						imageLibrary.details_append_folderOnlyScreeshot(webdriver,"HelpMVPIsVisible");
						report.log(Status.PASS, "Help MVP button is Visible",MediaEntityBuilder.createScreenCaptureFromPath(extentScreenShotPath+"\\HelpMVPIsVisible.jpg").build() );

						cfnlib.func_Click_BYID(cfnlib.XpathMap.get("Help_Id"));
						
						if("Yes".equalsIgnoreCase(cfnlib.XpathMap.get("HelpWindow")))
						{
							cfnlib.maxiMizeBrowser();
							String helptitle=cfnlib.getPageTitle(webdriver);
							System.out.println(helptitle);
							
							if(imageLibrary.isImageAppears("HelpContents"))
							{
								imageLibrary.details_append_folderOnlyScreeshot(webdriver,"HelpIsVisible");
								report.log(Status.PASS, "Help Page is Visible",MediaEntityBuilder.createScreenCaptureFromPath(extentScreenShotPath+"\\HelpCloseVisible.jpg").build() );
								cfnlib.navigate_back();
								}
							else
							{
								imageLibrary.details_append_folderOnlyScreeshot(webdriver,"HelpIsVisibleNt");
								report.log(Status.FAIL, "Help page is not Visible",MediaEntityBuilder.createScreenCaptureFromPath(extentScreenShotPath+"\\HelpIsVisibleNt.jpg").build() );
							}
							
						}
						else
						{
						if(imageLibrary.isImageAppears("HelpClose"))
						{
								imageLibrary.details_append_folderOnlyScreeshot(webdriver,"HelpIsVisible");
							report.log(Status.PASS, "Help Page is Visible",MediaEntityBuilder.createScreenCaptureFromPath(extentScreenShotPath+"\\HelpCloseVisible.jpg").build() );

							cfnlib.webdriver.navigate().back();
							
						}
						else
						{
							imageLibrary.details_append_folderOnlyScreeshot(webdriver,"HelpIsVisibleNt");
							report.log(Status.FAIL, "Help page is not Visible",MediaEntityBuilder.createScreenCaptureFromPath(extentScreenShotPath+"\\HelpIsVisibleNt.jpg").build() );
						}

						}
						//click anywhere
						if("Yes".equalsIgnoreCase(cfnlib.XpathMap.get("HelpMenu")))
						{
						if(imageLibrary.isImageAppears("HelpMenu"))
						{
							imageLibrary.click("HelpMenu");

						}
						}
					
							
							if(imageLibrary.isImageAppears("SpinBtn"))
							{
								imageLibrary.details_append_folderOnlyScreeshot(webdriver,"ClockVisible");
								report.log(Status.PASS, "Help close button is Functional",MediaEntityBuilder.createScreenCaptureFromPath(extentScreenShotPath+"\\ClockVisible.jpg").build() );
							}
							else
							{
								imageLibrary.details_append_folderOnlyScreeshot(webdriver,"ClockVisibleNt");
								report.log(Status.PASS, "Help close button is not Functional",MediaEntityBuilder.createScreenCaptureFromPath(extentScreenShotPath+"\\ClockVisibleNt.jpg").build() );
							}						
						
					}
					else
					{
						imageLibrary.details_append_folderOnlyScreeshot(webdriver,"HelpMVPIsVisibleNt");
						report.log(Status.FAIL, "Help button is not Visible",MediaEntityBuilder.createScreenCaptureFromPath(extentScreenShotPath+"\\HelpMVPIsVisibleNt.jpg").build() );
					}

				}
				catch(Exception e)
				{
					Thread.sleep(1000);
					log.error(e.getMessage(),e);
					cfnlib.evalException(e);
				}
				
				
				//lobby navigate
				try
				{
					
						if(imageLibrary.isImageAppears("Lobby"))
							{
								imageLibrary.details_append_folderOnlyScreeshot(webdriver,"isLobbyVisible");
								report.log(Status.PASS, "Lobby is Visible",MediaEntityBuilder.createScreenCaptureFromPath(extentScreenShotPath+"\\isLobbyVisible.jpg").build() );

								imageLibrary.click("Lobby");
								
								if(imageLibrary.isImageAppears("Yes"))
								{
									imageLibrary.click("Yes");
								}
								Thread.sleep(2000);
								if(cfnlib.webdriver.getCurrentUrl().contains("lobby"))
								{
									imageLibrary.details_append_folderOnlyScreeshot(webdriver,"PlayRealVisible");
									report.log(Status.PASS, "Navigated ti lobby on click on play real",MediaEntityBuilder.createScreenCaptureFromPath(extentScreenShotPath+"\\PlayRealVisible.jpg").build() );
									Thread.sleep(2000);
								}
								else
								{
									imageLibrary.details_append_folderOnlyScreeshot(webdriver,"PlayRealVisibleNt");
									report.log(Status.PASS, "Not Navigated to lobby,on click on play real",MediaEntityBuilder.createScreenCaptureFromPath(extentScreenShotPath+"\\PlayRealVisibleNt.jpg").build() );
								}
							}
							else
							{
								imageLibrary.details_append_folderOnlyScreeshot(webdriver,"LobbyVisibleNt");
								report.log(Status.FAIL, "Lobby is not Visible",MediaEntityBuilder.createScreenCaptureFromPath(extentScreenShotPath+"\\LobbyVisibleNt.jpg").build() );
							}
					}
				catch(Exception e)
				{
					log.error(e.getMessage(),e);
					cfnlib.evalException(e);
				}
				
				 report.log(Status.INFO, "Paytable scrolling to be handled manually");
				 report.log(Status.INFO, "Settings menu sliders to be checked manually since they have same UI");
				 report.log(Status.INFO, "Autoplay game completed and autoplay menu spin slider to be handled manually");
				 report.log(Status.INFO, "Autoplay 25x, 50x, 100x to be checked manually");
				 
			*/}
		}
		//-------------------Handling the exception---------------------//
				catch (Exception e) {
					log.error(e.getMessage(),e);
					cfnlib.evalException(e);
					cfnlib.loadGame(url);
				}
				finally
				{	
					//record.stop();	
					extent.flush();
					webdriver.close();
					languageReport.endReport();
					webdriver.quit();
					proxy.abort();
					Thread.sleep(3000);
				}	
	}
}
