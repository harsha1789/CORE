package com.zensar.automation.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * This class model hold the user meta data
 * @author sg56207
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserMetaData {

	@JsonProperty("userId")
	public String userID;

	@JsonProperty("loginName")
	public String loginName;
	@JsonProperty("serverId")
	public int serverID;
	@JsonProperty("balance")
	public double balance;
	@JsonProperty("productId")
	public int productId;
	
	public int getProductId() {
		return productId;
	}
	public void setProductId(int productId) {
		this.productId = productId;
	}
	public String getUserID() {
		return userID;
	}
	public void setUserID(String userID) {
		this.userID = userID;
	}
	public String getLoginName() {
		return loginName;
	}
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}
	public int getServerID() {
		return serverID;
	}
	public void setServerID(int serverID) {
		this.serverID = serverID;
	}
	public double getBalance() {
		return balance;
	}
	public void setBalance(double balance) {
		this.balance = balance;
	}
}
