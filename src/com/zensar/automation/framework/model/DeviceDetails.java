package com.zensar.automation.framework.model;

/**
 * Model class to store device details.
 * @author ak47374
 *
 */
public class DeviceDetails {
	
String serial;
boolean present;
boolean using;
boolean ready;
String provider;
String manufacturer;
String marketName;
String product;

/**
 * @return the serial
 */
public String getSerial() {
	return serial;
}
/**
 * @param serial the serial to set
 */
public void setSerial(String serial) {
	this.serial = serial;
}
/**
 * @return the present
 */
public boolean isPresent() {
	return present;
}
/**
 * @param present the present to set
 */
public void setPresent(boolean present) {
	this.present = present;
}
/**
 * @return the using
 */
public boolean isUsing() {
	return using;
}
/**
 * @param using the using to set
 */
public void setUsing(boolean using) {
	this.using = using;
}
/**
 * @return the ready
 */
public boolean isReady() {
	return ready;
}
/**
 * @param ready the ready to set
 */
public void setReady(boolean ready) {
	this.ready = ready;
}
/**
 * @return the provider
 */
public String getProvider() {
	return provider;
}
/**
 * @param provider the provider to set
 */
public void setProvider(String provider) {
	this.provider = provider;
}
/**
 * @return the manufacturer
 */
public String getManufacturer() {
	return manufacturer;
}
/**
 * @param manufacturer the manufacturer to set
 */
public void setManufacturer(String manufacturer) {
	this.manufacturer = manufacturer;
}
/**
 * @return the marketName
 */
public String getMarketName() {
	return marketName;
}
/**
 * @param marketName the marketName to set
 */
public void setMarketName(String marketName) {
	this.marketName = marketName;
}
/**
 * @return the product
 */
public String getProduct() {
	return product;
}
/**
 * @param product the product to set
 */
public void setProduct(String product) {
	this.product = product;
}



	
}
