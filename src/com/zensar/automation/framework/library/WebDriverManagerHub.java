package com.zensar.automation.framework.library;

import org.openqa.grid.selenium.GridLauncherV3;

/*
 * This class will launch the hub at specified port in localhost
 * */
public class WebDriverManagerHub {

	    public void launchHub(String port)  {
	        GridLauncherV3.main(new String[] { "-role", "hub", "-port", port });
	    }

}
