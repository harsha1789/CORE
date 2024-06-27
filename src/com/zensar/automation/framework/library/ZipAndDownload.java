package com.zensar.automation.framework.library;
import java.io.IOException;
/**
 * 
 * @author ak47374
 *
 */
public class ZipAndDownload {
	

	
	    public static void main(String[] args){
	        ZipHelper zippy = new ZipHelper();
	        try {
	        	String reportPath="D:\\Report\\AgeOfDiscovery_12530_40302_50302_PortfolioZensarDevQA_0_26_11_2020_9_10_42";
	            zippy.zipDir(reportPath,reportPath+"test.zip");
	        } catch(IOException e2) {
	            System.err.println(e2);
	        }
	    }
	

	
}
