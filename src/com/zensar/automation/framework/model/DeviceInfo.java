package com.zensar.automation.framework.model;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Model class for device properties
 * @author ak47374
 *
 */
@JsonIgnoreProperties
public class DeviceInfo implements Serializable {
	

	/**
	 * serial version
	 */
	private static final long serialVersionUID = 1L;

	@JsonProperty("Product")
	public String Product;
	
	@JsonProperty("manufacturer")
	public String manufacturer;

	@JsonProperty("model")
	public String model;
	
	@JsonProperty("ready")
	public boolean ready;
	
	@JsonProperty("serial")
	public String serial;

	@JsonProperty("present")
	public boolean present;
	
	@JsonProperty("using")
	boolean using;
	
	@JsonProperty("owner")
	public Owner owner;
	
	@JsonProperty("display")
	public Display display;
	
	@JsonProperty("marketName")
	public String marketName;
	
	String testName;
	
	String username;
	
	String osPlatform;
	
	String browserName;
	
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

	@JsonProperty("version")
	public String version;
	
	@JsonProperty("notes")
	public String notes;
	
	int checkedOutDeviceNum;

	

	public int getCheckedOutDeviceNum() {
		return checkedOutDeviceNum;
	}

	public void setCheckedOutDeviceNum(int checkedOutDeviceNum) {
		this.checkedOutDeviceNum = checkedOutDeviceNum;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getTestName() {
		return testName;
	}

	public void setTestName(String testName) {
		this.testName = testName;
	}

	public String getMarketName() {
		return marketName;
	}

	public void setMarketName(String marketName) {
		this.marketName = marketName;
	}

	public Display getDisplay() {
		
		return display;
	}

	public void setDisplay(Display display) {
		this.display = display;
	}

	public boolean isUsing() {
		return using;
	}

	public void setUsing(boolean using) {
		this.using = using;
	}

	public boolean isPresent() {
		return present;
	}

	public void setPresent(boolean present) {
		this.present = present;
	}

	public String getSerial() {
		return serial;
	}

	public void setSerial(String serial) {
		this.serial = serial;
	}

	public boolean getReady() {
		return ready;
	}

	public void setReady(boolean ready) {
		this.ready = ready;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getManufacturer() {
		return manufacturer;
	}

	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}

	public String getProduct() {
		
		return Product;
	}

	public void setProduct(String product) {
		Product = product;
	}

	public Owner getOwner() {
		return owner;
	}

	public void setOwner(Owner owner) {
		this.owner = owner;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	
	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	
	
}
