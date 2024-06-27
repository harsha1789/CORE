package com.zensar.automation.framework.model;


/**
 * This class creates a STF Service object.
 */
public class STFService {

    private String stfUrl;
    
    private String authToken;

    public STFService() {
    }

    public STFService(String stfUrl, String authToken)  {
        this.stfUrl = stfUrl;
        this.authToken = authToken;
    }

    public String getStfUrl() {
        return stfUrl;
    }

    public void setStfUrl(String stfUrl)  {
        this.stfUrl = stfUrl;
    }

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

}
