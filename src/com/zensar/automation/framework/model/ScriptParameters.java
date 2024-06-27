package com.zensar.automation.framework.model;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import io.appium.java_client.AppiumDriver;
import net.lightbody.bmp.BrowserMobProxyServer;

/**
 * Model class to pass the parameters from driver to test cases.
 * @author ak47374
 *
 */
public class ScriptParameters {

	String mstrTCName;
	String mstrTCDesc;
	String mstrModuleName;	
	BrowserMobProxyServer proxy;
	String filePath;
	String deviceName;
	int checkedOutDeviceNum;
	String startTime;
	String userName;
	String framework;
	String gameName;
	//for mobile appium driver
	//AppiumDriver<WebElement> appiumWebdriver;For sel 3.141.59
	AppiumDriver appiumWebdriver; // For Sel 4
	//for desktop selenium driver
	WebDriver driver;
	String browserName;
	String osPlatform;
	String osVersion;
	/**
	 * @return the osVersion
	 */
	public String getOsVersion() {
		return osVersion;
	}
	/**
	 * @param osVersion the osVersion to set
	 */
	public void setOsVersion(String osVersion) {
		this.osVersion = osVersion;
	}
	/**
	 * @return the mstrTCName
	 */
	public String getMstrTCName() {
		return mstrTCName;
	}
	/**
	 * @param mstrTCName the mstrTCName to set
	 */
	public void setMstrTCName(String mstrTCName) {
		this.mstrTCName = mstrTCName;
	}
	/**
	 * @return the mstrTCDesc
	 */
	public String getMstrTCDesc() {
		return mstrTCDesc;
	}
	/**
	 * @param mstrTCDesc the mstrTCDesc to set
	 */
	public void setMstrTCDesc(String mstrTCDesc) {
		this.mstrTCDesc = mstrTCDesc;
	}
	/**
	 * @return the mstrModuleName
	 */
	public String getMstrModuleName() {
		return mstrModuleName;
	}
	/**
	 * @param mstrModuleName the mstrModuleName to set
	 */
	public void setMstrModuleName(String mstrModuleName) {
		this.mstrModuleName = mstrModuleName;
	}
	/**
	 * @return the proxy
	 */
	public BrowserMobProxyServer getProxy() {
		return proxy;
	}
	/**
	 * @param proxy the proxy to set
	 */
	public void setProxy(BrowserMobProxyServer proxy) {
		this.proxy = proxy;
	}
	/**
	 * @return the filePath
	 */
	public String getFilePath() {
		return filePath;
	}
	/**
	 * @param filePath the filePath to set
	 */
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	/**
	 * @return the deviceName
	 */
	public String getDeviceName() {
		return deviceName;
	}
	/**
	 * @param deviceName the deviceName to set
	 */
	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}
	/**
	 * @return the checkedOutDeviceNum
	 */
	public int getCheckedOutDeviceNum() {
		return checkedOutDeviceNum;
	}
	/**
	 * @param checkedOutDeviceNum the checkedOutDeviceNum to set
	 */
	public void setCheckedOutDeviceNum(int checkedOutDeviceNum) {
		this.checkedOutDeviceNum = checkedOutDeviceNum;
	}
	/**
	 * @return the startTime
	 */
	public String getStartTime() {
		return startTime;
	}
	/**
	 * @param startTime the startTime to set
	 */
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	/**
	 * @return the userName
	 */
	public String getUserName() {
		return userName;
	}
	/**
	 * @param userName the userName to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}
	/**
	 * @return the framework
	 */
	public String getFramework() {
		return framework;
	}
	/**
	 * @param framework the framework to set
	 */
	public void setFramework(String framework) {
		this.framework = framework;
	}
	/**
	 * @return the gameName
	 */
	public String getGameName() {
		return gameName;
	}
	/**
	 * @param gameName the gameName to set
	 */
	public void setGameName(String gameName) {
		this.gameName = gameName;
	}
	/**
	 * @return the appiumWebdriver
	 */
	public AppiumDriver getAppiumWebdriver() {
		return appiumWebdriver;
	}
	/**
	 * @param appiumWebdriver the appiumWebdriver to set
	 */
	public void setAppiumWebdriver(AppiumDriver appiumWebdriver) {
		this.appiumWebdriver = appiumWebdriver;
	}
	/**
	 * @return the driver
	 */
	public WebDriver getDriver() {
		return driver;
	}
	/**
	 * @param driver the driver to set
	 */
	public void setDriver(WebDriver driver) {
		this.driver = driver;
	}
	/**
	 * @return the browserName
	 */
	public String getBrowserName() {
		return browserName;
	}
	/**
	 * @param browserName the browserName to set
	 */
	public void setBrowserName(String browserName) {
		this.browserName = browserName;
	}
	/**
	 * @return the osPlatform
	 */
	public String getOsPlatform() {
		return osPlatform;
	}
	/**
	 * @param osPlatform the osPlatform to set
	 */
	public void setOsPlatform(String osPlatform) {
		this.osPlatform = osPlatform;
	}
	
	
		
}
