package Modules.Regression.TestScript;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;

import com.zensar.automation.framework.model.ScriptParameters;

/**
 * This is a test script to test the device and browser connection
 * 
 * @author Premlata
 */

public class DesktopTestScript {

	Logger log = Logger.getLogger(DesktopTestScript.class.getName());
	public ScriptParameters scriptParameters;
	

	public void script() throws Exception {
		System.setProperty("org.seleniumhq.jetty9.LEVEL", "INFO");
		WebDriver webdriver = scriptParameters.getDriver();

		try {
			// Step 1
			if (webdriver != null) {
				Thread.sleep(15000);
				webdriver.get("https://mobile-app1-gtp73.installprogram.eu/mobileWebGames/game/mgs/10_10_5_8061?displayName=Basketball+Star&gameId=basketballStarDesktop&gameVersion=basketballStarDesktop_TheForce_2_0_3_336&moduleId=10430&clientId=50301&clientTypeId=70&languageCode=en&productId=5007&brand=islandparadise&loginType=InterimUPE&returnUrl=https://mobile-app1-gtp73.installprogram.eu/lobby/en/IslandParadise/games/&bankingUrl=https://mobile-app1-gtp73.installprogram.eu/lobby/en/IslandParadise&routerEndPoints=&currencyFormat=&isPracticePlay=False&username=Zen_ncovyjz&password=test1234$&host=desktop");
				Thread.sleep(15000);
				webdriver.navigate().refresh();
			}
		}
		// -------------------Handling the exception---------------------//
		catch (Exception e) {
			log.error(e.getMessage(), e);

		}
		// -------------------Closing the connections---------------//
		finally {

			webdriver.close();
			webdriver.quit();
		}
	}
}
