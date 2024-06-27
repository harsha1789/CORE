package com.zensar.automation.framework.library;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

import org.apache.log4j.Logger;

import com.zensar.automation.framework.api.DeviceApi;
import com.zensar.automation.framework.driver.MobileDriver;
import com.zensar.automation.framework.model.Coordinates;
/*
 * This class provide the Screen Recording functionality using adb
 * such as start recording and stop recording 
 * */
public class ScreenRecorder 
{
	Logger log = Logger.getLogger("automationLogger");
	String className;
	Coordinates device=new Coordinates();
	List<String> adbProcessIDsBeforeScreenRecording = null;
	List<String> adbProcessIDsAfterScreenRecording = null;
	static DeviceApi deviceApi;
	public  void startScreenRecording(String className) throws IOException 
	{ 
		try
		{
			this.className=className;
			//Runtime.getRuntime().exec("adb kill-server");
			//Runtime.getRuntime().exec("adb start-server");
			Runtime.getRuntime().exec("adb connect "+new MobileDriver().getDeviceIPPort());
			adbProcessIDsBeforeScreenRecording = getProcessIDs("adb.exe");
			Runtime.getRuntime().exec("adb shell screenrecord /sdcard/"+className+".mp4");
			log.debug("video recording started"); 
		}
		catch(Exception e)
		{
			log.error("video recording Not started");
			log.error(e.getMessage());
		}
	}

	public  void startScreenRecordingWithBitRate(String currentTestMethodName) throws IOException {
		// Storing all ADB process ids before starting Screen Recording.
		adbProcessIDsBeforeScreenRecording = getProcessIDs("adb.exe");

		// "Starting Screen Recording for Current TestMethod.
		Runtime.getRuntime().exec( "cmd /c adb shell screenrecord --bit-rate 1000000 //Internal Storage//" + currentTestMethodName + ".mp4");
	}

	public  void stopScreenRecording(String path) throws IOException,InterruptedException 
	{
		try
		{

			Runtime.getRuntime().exec("taskkill /f /im java.exe");
			// Storing all ADB process ids after Screen Recording.
			adbProcessIDsAfterScreenRecording = getProcessIDs("adb.exe");


			// killing ADB task using process id.
			// First we are trying to get ADB process id that is started due to ADB
			// screenrecord then killing the same.
			for (String id : adbProcessIDsAfterScreenRecording) {
				boolean found = false;
				for (String tgtid : adbProcessIDsBeforeScreenRecording) {
					if (tgtid.equals(id)) {
						found = true;
						break;
					}
				}
				if (!found) {
					log.debug("Killing id ::"+id);
					Runtime.getRuntime().exec("adb shell kill -2 ["+id+"]");
					break;
				}
			}

			// Sleep time to save the recorded video in Device properly
			Thread.sleep(80000);

			// Pulling Screen Recording to PC/Machine
			Runtime.getRuntime().exec("adb pull /sdcard/"+className+".mp4 "+path);
			//Removing the video from mobile
			Runtime.getRuntime().exec("adb shell");
			Runtime.getRuntime().exec("adb shell rm -f /sdcard/"+className+".mp4");
		}
		catch(Exception e)
		{
			e.getMessage();
		}
	}


	//Method to get List of Process Ids using Process Name
	List<String> getProcessIDs(String processName) {
		List<String> processIDs = new ArrayList<>();
		try {
			String line;
			Process p = Runtime.getRuntime().exec("tasklist /v /fo csv");
			BufferedReader input = new BufferedReader(new InputStreamReader(
					p.getInputStream()));
			while ((line = input.readLine()) != null) {
				if (!line.trim().equals("")) {
					// Pid is after the 1st ", thus it's argument 3 after
					// splitting
					String currentProcessName = line.split("\"")[1];
					// Pid is after the 3rd ", thus it's argument 3 after
					// splitting
					String currentPID = line.split("\"")[3];
					if (currentProcessName.equalsIgnoreCase(processName)) {
						processIDs.add(currentPID);
					}
				}
			}
			input.close();
		} catch (Exception err) {
			log.error(err.getStackTrace());
		}
		return processIDs;
	}

}
