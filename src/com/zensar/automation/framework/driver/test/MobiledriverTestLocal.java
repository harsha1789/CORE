package com.zensar.automation.framework.driver.test;

import java.io.IOException;

import com.zensar.automation.framework.driver.MobileDriver;
import com.zensar.automation.framework.library.PropertyReader;
import com.zensar.automation.framework.library.UnableToLoadPropertiesException;

public class MobiledriverTestLocal {

	public static void main(String[] args) {

	androidDEviceFarmTest();
	//IOSDEviceFarmTest();
	}

	public static void androidDEviceFarmTest() {

		MobileDriver mDriver = new MobileDriver();
		//String deviceid = "5210c936fafe2419";//"ce031713e950349d0c";//"ce051715b276652a01";//5200a6d2fed13525 ce0316039d361e0b04 9887fc435633354650 R58M32FPR4A
		//String deviceid = "ce051715b276652a01";
		//Pune Provider1
		//String deviceid = "ad0516022904e192ab";//"21b857b82a0c7ece";//"2672088cc90d7ece";//2672088cc90d7ece //8533454a5a52534f
		//String osVersion = "7.0";// 
		//Pune Provider2
		//String deviceid ="8533454a5a52534f";
		//String osVersion = "7.0";
		//Durban Provider3
		String deviceid ="2672088cc90d7ece";
		String osVersion = "8.1.0";
		//Durban Provider3
		//String deviceid ="R5CR20LV31A";
		//String osVersion = "12";
		
		//String deviceid ="ce031713e950349d0c";
		//String osVersion = "8.0.0";
		
		
		//216daf7811027ece
		
		
		
		
		
				
		
		String ipAddress = "127.0.0.1";
		String port = "4723";
		int proxy = 0;
		String username = "player905";
		String zenReplica = "True";
		String deviceName = "Galaxy_S9Plus";
		String browser = "chrome";
		String osPlatform = "Android";
		String gameName = "GoldFactory";
		int checkedOutDeviceNum = 1;
		System.setProperty("logfilename",gameName+"/Selenium");
		
		try {
				PropertyReader.getInstance().loadDefaultProperties();
				PropertyReader.getInstance().loadGameProperties("GoldFactory");
				mDriver.test(deviceid, ipAddress, port, osVersion, proxy, username, zenReplica, deviceName, browser,
						osPlatform, gameName, checkedOutDeviceNum);
		} catch (UnableToLoadPropertiesException e) {
				e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public static void IOSDEviceFarmTest() {

		MobileDriver mDriver = new MobileDriver();
		
		//Pune Mac1
		//String deviceid = "00008030-000364DA3E82402E";//"00008101-0002386126F1003A";//"00008101-000D70C92192001E";//"00008101-0002386126F1003A";//"733d11d7d8a81fcb25f4f01225cfe8d413b19682";//"5b71650ea1a6c52cac875a31f4c736894dacbbf3";//00008101-000D70C92192001E //5b71650ea1a6c52cac875a31f4c736894dacbbf3
		//String osVersion = "13.7";
		
		//Pune Mac2
		//String deviceid = "00008030-001E05462186402E";//"00008101-0002386126F1003A";//"00008101-000D70C92192001E";//"00008101-0002386126F1003A";//"733d11d7d8a81fcb25f4f01225cfe8d413b19682";//"5b71650ea1a6c52cac875a31f4c736894dacbbf3";//00008101-000D70C92192001E //5b71650ea1a6c52cac875a31f4c736894dacbbf3
		//String osVersion = "14.6";
		
		//Durban Mac1
		//
		String deviceid ="00008020-00011C141E80402E";//00008101-000D70C92192001E";//00008030-000364DA3E82402E";//"00008101-001C205C22B8001E";//"00008030-000364DA3E82402E";//"00008101-001A4588112A001E";//"00008101-00065C8E1405001E";//"00008030-000E0C2A2109802E";//"00008101-000D70C92192001E"; //"00008030-000E0C2A2109802E";
		String osVersion ="15.3.1"; //"15.0";//"15.1";//"15.4.1";
		
		String ipAddress = "127.0.0.1";
		String port = "4723";
		//"13.0";//"14.5";//"14.4.2";
		int proxy = 0;
		String username = "hall2";
		String zenReplica = "True";
		String deviceName = "iPhone12Pro";//"iPhone12_GT3163";//"iPhone12_GT3163";//"iPhone7_GT0927";//iPadAir2
		String browser = "safari";
		String osPlatform = "ios";
		String gameName = "GoldFactory";
		int checkedOutDeviceNum = 1;

		System.setProperty("logfilename",gameName+"/Selenium");
		
		try {
				PropertyReader.getInstance().loadDefaultProperties();
				PropertyReader.getInstance().loadGameProperties("GoldFactory");
			mDriver.test(deviceid, ipAddress, port, osVersion, proxy, username, zenReplica, deviceName, browser,
					osPlatform, gameName, checkedOutDeviceNum);
		}	catch (UnableToLoadPropertiesException e) {
				e.printStackTrace();
		}  catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
