package Modules.Regression.TestScript;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import com.zensar.automation.api.RestAPILibrary;
import com.zensar.automation.framework.model.ScriptParameters;
import com.zensar.automation.framework.report.Desktop_HTML_Report;
import com.zensar.automation.framework.utils.Constant;
import com.zensar.automation.library.CommonUtil;
import com.zensar.automation.library.ImageLibrary;
import Modules.Regression.FunctionLibrary.CFNLibrary_Desktop;
import Modules.Regression.FunctionLibrary.CFNLibrary_Desktop_CS;
import Modules.Regression.FunctionLibrary.CFNLibrary_Desktop_Force;
import Modules.Regression.FunctionLibrary.factory.DesktopFunctionLibraryFactory;
import net.lightbody.bmp.BrowserMobProxyServer;
/**
 * Generic script
 * 
 * @author pb61055
 *
 */

public class Mobile_GenericScript 
{
	Logger log = Logger.getLogger(Mobile_GenericScript.class.getName()); 
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
		int startindex=0;
		String strGameName =null;
			
		
		Desktop_HTML_Report report = new Desktop_HTML_Report(webdriver,browserName,filePath,startTime,mstrTCName,mstrTCDesc,mstrModuleName,mintDetailCount,mintPassed,mintFailed,mintWarnings,mintStepNo,status1,gameName);
		DesktopFunctionLibraryFactory desktopFunctionLibraryFactory=new DesktopFunctionLibraryFactory();
		CFNLibrary_Desktop cfnlib=desktopFunctionLibraryFactory.getFunctionLibrary(framework, webdriver, proxy,report, gameName);
		CommonUtil util = new CommonUtil();
		RestAPILibrary apiObj = new RestAPILibrary();
		
		List<String> copiedFiles=new ArrayList<>();
		
		try
		{
			// Step 1 
			if(webdriver!=null)
			{	
				if(framework.equalsIgnoreCase("CS"))
				{
					cfnlib = new CFNLibrary_Desktop_CS(webdriver, proxy, report, gameName);	
				}
				else if(framework.equalsIgnoreCase("Force"))
				{
					cfnlib = new CFNLibrary_Desktop_Force(webdriver, proxy, report, gameName);	
				}
				else
				{
					cfnlib = new CFNLibrary_Desktop(webdriver, proxy, report, gameName);	
				}
				
	
				if(gameName.contains("Desktop"))
				{   
					java.util.regex.Pattern  str=java.util.regex.Pattern.compile("Desktop");
					Matcher  substing=str.matcher(gameName);
					while(substing.find())
					{
						startindex=substing.start();													
					}
					strGameName=gameName.substring(0, startindex);
					log.debug("newgamename="+strGameName);
				}
				else
				{
					strGameName=gameName;
				}
				
				ImageLibrary imageLibrary = new ImageLibrary(webdriver, strGameName, "Desktop");
				
								
				
				String url = cfnlib.XpathMap.get("ApplicationURL");	
				
				//cfnlib.webdriver.navigate().to(url);
				
				cfnlib.loadGame(url);
				Thread.sleep(3000);
				
				
				report.detailsAppend("Verify Game launchaed ", "Game should be launched", "Game is launched", "PASS");
			
			
				
				Thread.sleep(5000);	
				/*if(cfnlib.XpathMap.get("NFD").equalsIgnoreCase("Yes"))
				{
					imageLibrary.waitTillNFDButtonVisible();
					//imageLibrary.details_append_folderOnlyScreeshot(webdriver,"NfdScreenVisible");

				}*/
				
					
				imageLibrary.click("NFDButton");						
				Thread.sleep(2000);
				report.detailsAppend("After click ", "Game should be launched", "Game is launched", "PASS");
				
				Thread.sleep(2000);
				
				//imageLibrary.isImageAppears("Spin");
				
				
				imageLibrary.click("Spin");
				
				report.detailsAppend("Spin click", "Game should be launched", "Game is launched", "PASS");
			}// driver close
	
				
				
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
			
			webdriver.close();
			webdriver.quit();
			//proxy.abort();
			Thread.sleep(1000);
		}//closing finally block	
	
}

}
