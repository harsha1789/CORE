package com.zensar.automation.framework.library;
import org.apache.log4j.Logger;
import org.openqa.grid.selenium.GridLauncherV3;

import com.zensar.automation.framework.utils.Constant;

import io.github.bonigarcia.wdm.WebDriverManager;
/*
 * This class will launch node for specified  browser and  port
 *  
 * */
public class WebDriverManagerNode {

	Logger log = Logger.getLogger(WebDriverManagerNode.class.getName());
	
	public  void launchNode(String browserName,String hub,String port) throws UnSupportedBrowserException  {
		log.debug("Launching node for"+ browserName+" on port:"+port+ " for hub :"+hub);
		
		if(browserName.equalsIgnoreCase(Constant.CHROME))
		{
			WebDriverManager.chromedriver().setup();
		}
		else if(browserName.equalsIgnoreCase(Constant.FIREFOX))
		{
			WebDriverManager.firefoxdriver().setup();
		}
		else if(browserName.equalsIgnoreCase(Constant.INTERNETEXPLORER))
		{
			WebDriverManager.iedriver().setup();
		}
		else if(browserName.equalsIgnoreCase(Constant.EDGEDRIVER))
		{
			WebDriverManager.edgedriver().setup();
		}
		else if(browserName.equalsIgnoreCase(Constant.OPERA))
		{
			WebDriverManager.operadriver().setup();
		}
		else
		{
			throw new UnSupportedBrowserException("Browser Name :"+browserName+" is not in the supported list");
		}

		GridLauncherV3.main(new String[] { "-role", "node", "-hub",
				hub, "-browser",
				"browserName="+browserName.toLowerCase(), "-port", port });
	}

}
