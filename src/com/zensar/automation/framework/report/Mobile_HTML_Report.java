package com.zensar.automation.framework.report;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ListMultimap;
import com.zensar.automation.framework.driver.MobileDriver;
import com.zensar.automation.framework.library.ScreenRecorder;
import com.zensar.automation.framework.utils.Constant;

import io.appium.java_client.AppiumDriver;


public class Mobile_HTML_Report{
	RemoteWebDriver webdriver; 
	String mstrAppendString;
	int mintSummaryCount=0;
	String status;
	int mintDetailCount=0;
	int mintSubStepNo=0;
	int mintPassed=0;
	int mintFailed=0;
	int mintWarnings=0;
	int mintStepNo=0;
	FileWriter mObjFW0;	
	static ListMultimap<String, String> table2 = ArrayListMultimap.create();
	static HashMap<String,String> time= new HashMap<>();
	Logger log = Logger.getLogger( Mobile_HTML_Report.class.getName());
	DateFormat mObjDateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
	Calendar mstrCalenderStrt = Calendar.getInstance();
	String mstrStartTime=mObjDateFormat.format(mstrCalenderStrt.getTime());
	Date mDateToday=new Date();
	String gstrCountryName="India";
	String mstrTCName;
	String mstrTCDesc;
	String mstrModuleName;
	int mintMasterStepNo=0;
	String detailStrtTime;
	String deviceName;
	String filePath;
	String startTime;
	String gameName;
	 String fileName = new SimpleDateFormat("yyyyMMddHHmm").format(new Date()); 
	CSSStyleSheet styleSheet=new CSSStyleSheet();
	ScreenRecorder screen=new ScreenRecorder();
	String videoPath=System.getProperty("user.dir")+"\\src\\Result\\Videos";

	//AppiumDriver<WebElement>     RemoteWebDriver--> selenium grid
	public Mobile_HTML_Report( AppiumDriver<WebElement>   webdriver,String deviceName, String filePath, String startTime1,
			String mstrTCName, String mstrTCDesc, String mstrModuleName,int mintDetailCount, int mintPassed, int mintFailed,int mintWarnings,int mintStepNo, String status,String gameName)
	{
		this.webdriver=webdriver;
		this.deviceName=deviceName;
		this.filePath=filePath;
		this.startTime=startTime1;
		this.mstrTCName=mstrTCName;
		this.mstrTCDesc=mstrTCDesc;
		this.mstrModuleName=mstrModuleName;
		this.mintDetailCount=mintDetailCount;
		this.mintPassed=mintPassed;
		this.mintFailed=mintFailed;
		this.mintWarnings=mintWarnings;
		this.mintStepNo=mintStepNo;
		this.status=status;
		this.gameName=gameName;
	}

	public void initdetailTCReport(String testCase,String testCaseDesc)  {
		int i;
		int flag=0;
		//With Map
		for(i=0;i<table2.get(deviceName).size();i++)
		{
			if(table2.get(deviceName).get(i).equals(MobileDriver.repo.get(deviceName)+"_"+deviceName+"_"+gameName+"_"+this.mstrTCName))
			{
				flag=1;
			}
		}
		if(flag==0)
		{
			table2.put(deviceName, MobileDriver.repo.get(deviceName)+"_"+deviceName+"_"+gameName+"_"+this.mstrTCName);
		}
		Calendar calStrt = Calendar.getInstance();
		detailStrtTime=mObjDateFormat.format(calStrt.getTime());
		styleSheet.writeStyleSheet(filePath+this.mstrModuleName+"/"+MobileDriver.repo.get(deviceName)+"_"+deviceName+"_"+gameName+"_"+this.mstrTCName+Constant.HTMLEXTENSION,filePath );

		try(FileWriter f1=new FileWriter(filePath+this.mstrModuleName+File.separator+MobileDriver.repo.get(deviceName)+"_"+deviceName+"_"+gameName+"_"+this.mstrTCName+Constant.HTMLEXTENSION,true)){

			String detailHeader="\n<script>var txt='<title>Detail Test Case Report</title><table align=center width=900px><tr><td width=900px><h1><b>Detail Execution Log "+deviceName+"</b></h1></td></tr></table>'; document.write(txt);</script>";
			f1.append(detailHeader);

			String mstrBottomImg="\n<table align=center width=900px><tr><td width=900px><img src=\"../Images/Bottom.GIF\"></td></tr></table>";
			f1.append(mstrBottomImg);

			String detailHdTable="\n<script>var txt='<table class=subheader align=center width=900px ><tr><td class=subheader width=700px>Test Case: "+testCase+"</td></tr><tr><td class=subheader width=700px>Test Description: "+testCaseDesc+"</td></tr></table><hr class=divline>'; document.write(txt);</script>";
			f1.append(detailHdTable);
			//<td class=subheader width=200px>Date:"+mObjDateFormat.format(mDateToday)+"</td> <td class=subheader>Assigned To: "+gstrAssignedTo+"</td>

			mstrAppendString="\n<script>var txt1='<table align=center width=900px><tr><td width=900px><a class=tcindex font-weight:bold href=\"../"+MobileDriver.repo.get(deviceName)+"_"+deviceName+"_"+"Automation_summary"+".html\"><b>Back to Automation Summary</a></tr></td></table><br>'; document.write(txt1);</script>";
			f1.append(mstrAppendString);

			mstrAppendString="<script>var txt='<br><table align=center width=900px class=teststeps><tr><td width=70px class=tsheader>Step#</td><td width=200px class=tsheader>Step Descritption</td><td width=200px class=tsheader>Expected Result</td><td width=200px class=tsheader>Actual Result</td><td width=70px align=center class=tsheader>Status</td><td width=100px class=tsheader>Screen Shot</td></tr>'; document.write(txt);</script>";
			f1.append(mstrAppendString);
		} catch(IOException ioException) {
			log.error(ioException.getMessage(),ioException);
			detailsAppendNoScreenshot("IoException occur ,hence skipping the current test step", "", "", "Fail");
		}
	}	
	
	public String createTimeStampStr() {
		Calendar mycalendar = Calendar.getInstance();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd_hhmmss");
		return formatter.format(mycalendar.getTime());
	}
	
	public void detailsAppend(String stepDesc,String expResult,String actResult,String status)
	{
		//Captures screenshot for calling step
		File scrFile = webdriver.getScreenshotAs(OutputType.FILE);
		String imglinkPath=MobileDriver.repo.get(deviceName)+"_"+deviceName+"_"+gameName+"_"+this.mstrTCName+"/"+ createTimeStampStr() + Constant.JPEGEXTENSION;
		// Now you can do whatever you need to do with it, for example copy somewhere
		String screenShotPath=filePath+this.mstrModuleName+"/"+imglinkPath;

		try {
			FileUtils.copyFile(scrFile, new File(screenShotPath));
		} catch (IOException ioException) {
			log.error(ioException.getMessage(),ioException);
		}
		boolean isFileDeleted = scrFile.delete();
		
		if(!isFileDeleted) {
			log.debug("Memory not cleared after taking screen shot, possible memory error!.");
		}

		mintDetailCount++; //Counts the call to this function frmo individual test case
		try(FileWriter f1=new FileWriter(filePath+this.mstrModuleName+File.separator+MobileDriver.repo.get(deviceName)+"_"+deviceName+"_"+gameName+"_"+this.mstrTCName+Constant.HTMLEXTENSION,true)){

			if(mintDetailCount==1){
				initdetailTCReport(this.mstrTCName,mstrTCDesc);
			}

			if(actResult.equals("")&& status.equals(""))
			{
				mintStepNo++;
				mintMasterStepNo=0;
				mintSubStepNo=0;
				mstrAppendString="<script>var txt='<tr><td width=70px class=tsindlevel1>"+mintStepNo+"</td><td width=300px class=tsgen>"+stepDesc+"</td><td width=70px class=tsgen>"+expResult+"</td><td width=70px class=tsgen></td><td width=70px align=center class=tsgen><b><font size = 2 color = green></td><td></td></tr>'; document.write(txt);</script>";
				f1.append("\n"+mstrAppendString);
			}
			else
			{
				mintSubStepNo++;
				if(status.equalsIgnoreCase("PASS"))
				{
					mstrAppendString="<script>var txt='<tr><td width=70px class=tsindlevel2>"+mintStepNo+"."+mintSubStepNo+"</td><td width=300px class=tsgen>"+stepDesc+"</td><td width=70px class=tsgen>"+expResult+"</td><td width=70px class=tsgen>"+actResult+"</td><td width=70px align=center class=tsgen><b><font size = 2 color = green>"+status+"</td><td class=tsind width=50px><a target=_blank class=anibutton href=\"./"+imglinkPath+"\"><img class=screen src=\"file:///"+filePath+"Images/img.png\"></a></td></tr>'; document.write(txt);</script>";
				}
				else if(status.equalsIgnoreCase("FAIL"))
				{
					if(mintMasterStepNo==0){
						mintFailed++;
						mintMasterStepNo++;
					}
					mstrAppendString="<script>var txt='<tr><td width=70px class=tsindlevel2>"+mintStepNo+"."+mintSubStepNo+"</td><td width=300px class=tsgen>"+stepDesc+"</td><td width=70px class=tsgen>"+expResult+"</td><td width=70px class=tsgen>"+actResult+"</td><td width=70px align=center class=tsgen><b><font size = 2 color = red>"+status+"</td><td class=tsind width=50px><a target=_blank class=anibutton href=\"./"+imglinkPath+"\"><img class=screen src=\"file:///"+filePath+"Images/img.png\"></a></td></tr>'; document.write(txt);</script>";
				}
				else if(status.equalsIgnoreCase(Constant.INTERRUPTED))
				{
					if(mintMasterStepNo==0){
						mintFailed++;
						mintMasterStepNo++;
					}
					mstrAppendString="<script>var txt='<tr><td></td><td width=600px><b><font size = 2 color = Brown>"+stepDesc+"</td><td></td><td></td><td><b><font size = 2 color = Brown>"+status+"</td><td align=center width=50px><a target=_blank class=anibutton href=\"./"+imglinkPath+"\"><img class=screen src=\"file:///"+filePath+"Images/img.png\"></a></td></tr>'; document.write(txt);</script>";
				}
				f1.append("\n"+mstrAppendString);
			}
		}catch(IOException ioException) {
			log.debug(ioException.getMessage(),ioException);
			detailsAppendNoScreenshot("IoException occur ,hence skipping the current test step", "", "", "Fail");
		}
		if(mintFailed==0)
		{
			mintPassed=mintStepNo;
			summary(MobileDriver.repo.get(deviceName)+"_"+deviceName+"_"+gameName+"_"+mstrTCName,mstrModuleName, mstrTCDesc, String.valueOf(mintStepNo), String.valueOf(mintPassed), String.valueOf(mintFailed), String.valueOf(mintWarnings), "PASS");
		}
		else
		{
			mintPassed=mintStepNo-mintFailed;
			summary(MobileDriver.repo.get(deviceName)+"_"+deviceName+"_"+gameName+"_"+mstrTCName,mstrModuleName, mstrTCDesc, String.valueOf(mintStepNo), String.valueOf(mintPassed), String.valueOf(mintFailed), String.valueOf(mintWarnings), "FAIL");
		}
	}

	public void detailsAppendFolder(String stepDesc,String expResult,String actResult,String status, String folderName) 
	{
		//Captures screenshot for calling step
		File scrFile = webdriver.getScreenshotAs(OutputType.FILE);
		String imglinkPath=MobileDriver.repo.get(deviceName)+"_"+deviceName+"_"+gameName+"_"+this.mstrTCName+"/"+folderName+"/"+ createTimeStampStr() + Constant.JPEGEXTENSION;
		// Now you can do whatever you need to do with it, for example copy somewhere
		String screenShotPath=filePath+this.mstrModuleName+"/"+imglinkPath;

		try(FileWriter f1=new FileWriter(filePath+this.mstrModuleName+File.separator+MobileDriver.repo.get(deviceName)+"_"+deviceName+"_"+gameName+"_"+this.mstrTCName+Constant.HTMLEXTENSION,true)){
			

			FileUtils.copyFile(scrFile, new File(screenShotPath));
			
			boolean isFileDeleted = scrFile.delete();
			
			if(!isFileDeleted) {
				log.debug("Memory not cleared after taking screen shot, possible memory error.");
			}
			
			///***************************************************
			mintDetailCount++; //Counts the call to this function frmo individual test case
			if(mintDetailCount==1){
				initdetailTCReport(this.mstrTCName,mstrTCDesc);
			}
			if(actResult.equals("")&& status.equals(""))
			{
				mintStepNo++;
				mintMasterStepNo=0;
				mintSubStepNo=0;
				mstrAppendString="<script>var txt='<tr><td width=70px class=tsindlevel1>"+mintStepNo+"</td><td width=300px class=tsgen>"+stepDesc+"</td><td width=70px class=tsgen>"+expResult+"</td><td width=70px class=tsgen></td><td width=70px align=center class=tsgen><b><font size = 2 color = green></td><td></td></tr>'; document.write(txt);</script>";
				f1.append("\n"+mstrAppendString);
			}
			else
			{
				mintSubStepNo++;
				if(status.equalsIgnoreCase("PASS"))
				{
					mstrAppendString="<script>var txt='<tr><td width=70px class=tsindlevel2>"+mintStepNo+"."+mintSubStepNo+"</td><td width=300px class=tsgen>"+stepDesc+"</td><td width=70px class=tsgen>"+expResult+"</td><td width=70px class=tsgen>"+actResult+"</td><td width=70px align=center class=tsgen><b><font size = 2 color = green>"+status+"</td><td class=tsind width=50px><a target=_blank class=anibutton href=\"./"+imglinkPath+"\"><img class=screen src=\"file:///"+filePath+"ScreenShots/Images/img.png\"></a></td></tr>'; document.write(txt);</script>";
				}
				else if(status.equalsIgnoreCase("FAIL"))
				{
					if(mintMasterStepNo==0){
						mintFailed++;
						mintMasterStepNo++;
					}
					mstrAppendString="<script>var txt='<tr><td width=70px class=tsindlevel2>"+mintStepNo+"."+mintSubStepNo+"</td><td width=300px class=tsgen>"+stepDesc+"</td><td width=70px class=tsgen>"+expResult+"</td><td width=70px class=tsgen>"+actResult+"</td><td width=70px align=center class=tsgen><b><font size = 2 color = red>"+status+"</td><td class=tsind width=50px><a target=_blank class=anibutton href=\"./"+imglinkPath+"\"><img class=screen src=\"file:///"+filePath+"ScreenShots/Images/img.png\"></a></td></tr>'; document.write(txt);</script>";
				}
				else if(status.equalsIgnoreCase(Constant.INTERRUPTED))
				{
					if(mintMasterStepNo==0){
						mintFailed++;
						mintMasterStepNo++;
					}
					mstrAppendString="<script>var txt='<tr><td></td><td width=600px><b><font size = 2 color = Brown>"+stepDesc+"</td><td></td><td></td><td><b><font size = 2 color = Brown>"+status+"</td><td align=center width=50px><a target=_blank class=anibutton href=\"./"+imglinkPath+"\"><img class=screen src=\"file:///"+filePath+"Images/img.png\"></a></td></tr>'; document.write(txt);</script>";
				}
				f1.append("\n"+mstrAppendString);
			}
		}catch(IOException ioException) {
			log.error(ioException.getMessage(),ioException);
			detailsAppendNoScreenshot("IoException occur ,hence skipping the current test step", "", "", "Fail");
		}
		if(mintFailed==0)
		{
			mintPassed=mintStepNo;
			summary(MobileDriver.repo.get(deviceName)+"_"+deviceName+"_"+gameName+"_"+mstrTCName,mstrModuleName, mstrTCDesc, String.valueOf(mintStepNo), String.valueOf(mintPassed), String.valueOf(mintFailed), String.valueOf(mintWarnings), "PASS");
		}
		else
		{
			mintPassed=mintStepNo-mintFailed;
			summary(MobileDriver.repo.get(deviceName)+"_"+deviceName+"_"+gameName+"_"+mstrTCName,mstrModuleName, mstrTCDesc, String.valueOf(mintStepNo), String.valueOf(mintPassed), String.valueOf(mintFailed), String.valueOf(mintWarnings), "FAIL");
		}
	}
	
	public void detailsAppendNoScreenshot(String stepDesc,String expResult,String actResult,String status)
	{
		

		mintDetailCount++; //Counts the call to this function frmo individual test case
		try(FileWriter f1=new FileWriter(filePath+this.mstrModuleName+File.separator+MobileDriver.repo.get(deviceName)+"_"+deviceName+"_"+gameName+"_"+this.mstrTCName+Constant.HTMLEXTENSION,true)){

			if(mintDetailCount==1){
				initdetailTCReport(this.mstrTCName,mstrTCDesc);
			}

			if(actResult.equals("")&& status.equals(""))
			{
				mintStepNo++;
				mintMasterStepNo=0;
				mintSubStepNo=0;
				mstrAppendString="<script>var txt='<tr><td width=70px class=tsindlevel1>"+mintStepNo+"</td><td width=300px class=tsgen>"+stepDesc+"</td><td width=70px class=tsgen>"+expResult+"</td><td width=70px class=tsgen></td><td width=70px align=center class=tsgen><b><font size = 2 color = green></td><td></td></tr>'; document.write(txt);</script>";
				f1.append("\n"+mstrAppendString);
			}
			else
			{
				mintSubStepNo++;
				if(status.equalsIgnoreCase("PASS"))
				{
					mstrAppendString="<script>var txt='<tr><td width=70px class=tsindlevel2>"+mintStepNo+"."+mintSubStepNo+"</td><td width=300px class=tsgen>"+stepDesc+"</td><td width=70px class=tsgen>"+expResult+"</td><td width=70px class=tsgen>"+actResult+"</td><td width=70px align=center class=tsgen><b><font size = 2 color = green>"+status+"</td><td class=tsind width=50px></td></tr>'; document.write(txt);</script>";
				}
				else if(status.equalsIgnoreCase("FAIL"))
				{
					if(mintMasterStepNo==0){
						mintFailed++;
						mintMasterStepNo++;
					}
					mstrAppendString="<script>var txt='<tr><td width=70px class=tsindlevel2>"+mintStepNo+"."+mintSubStepNo+"</td><td width=300px class=tsgen>"+stepDesc+"</td><td width=70px class=tsgen>"+expResult+"</td><td width=70px class=tsgen>"+actResult+"</td><td width=70px align=center class=tsgen><b><font size = 2 color = red>"+status+"</td><td class=tsind width=50px></td></tr>'; document.write(txt);</script>";
				}
				else if(status.equalsIgnoreCase(Constant.INTERRUPTED))
				{
					if(mintMasterStepNo==0){
						mintFailed++;
						mintMasterStepNo++;
					}
					mstrAppendString="<script>var txt='<tr><td></td><td width=600px><b><font size = 2 color = Brown>"+stepDesc+"</td><td></td><td></td><td><b><font size = 2 color = Brown>"+status+"</td></tr>'; document.write(txt);</script>";
				}
				f1.append("\n"+mstrAppendString);
			}
		}catch(IOException ioException) {
			log.error(ioException.getMessage(),ioException);
			detailsAppendNoScreenshot("IoException occur ,hence skipping the current test step", "", "", "Fail");
		}
		if(mintFailed==0)
		{
			mintPassed=mintStepNo;
			summary(MobileDriver.repo.get(deviceName)+"_"+deviceName+"_"+gameName+"_"+mstrTCName,mstrModuleName, mstrTCDesc, String.valueOf(mintStepNo), String.valueOf(mintPassed), String.valueOf(mintFailed), String.valueOf(mintWarnings), "PASS");
		}
		else
		{
			mintPassed=mintStepNo-mintFailed;
			summary(MobileDriver.repo.get(deviceName)+"_"+deviceName+"_"+gameName+"_"+mstrTCName,mstrModuleName, mstrTCDesc, String.valueOf(mintStepNo), String.valueOf(mintPassed), String.valueOf(mintFailed), String.valueOf(mintWarnings), "FAIL");
		}
	}

	public void videoRecording(String folderName)  
	{
		try
		{
			//String videoRecording=cfnLibrary_Mobile.XpathMap.get("videoRecording");
			//if(videoRecording.equalsIgnoreCase("yes"))
			
			screen.startScreenRecording(folderName);
			

		}
		catch(IOException ioException)
		{
			log.error("Error in making video ::"+ioException.getMessage(), ioException);
		}
	}
	public void summary(String testCase,String module,String description,String steps,String passed,String failed,String warnings,String status){
		
		
		
		File file = new File(filePath+"Dump");
        if (!file.exists()) {
            if (file.mkdir()) {
            	log.debug("Directory is created!");
            } else {
            	log.debug("Failed to create directory!");
            }
        }
		
		//*************************************************************
		String str=filePath+"Dump/"+testCase+".txt";

		try{mObjFW0=new FileWriter(str);
		//**********************************************************************************************
		if(status.equalsIgnoreCase("PASS")){
			String summary="\n<script>var txt='<tr><td width=100px align=center class=tsgen><a class=tcindex href=\"./"+this.mstrModuleName+"/"+MobileDriver.repo.get(deviceName)+"_"+deviceName+"_"+gameName+"_"+this.mstrTCName+".html\">"+testCase+"</a></td><td width=80px class=tsgen>"+module+"</td><td width=200px class=tsgen>"+description+"</td><td width=70px align=center class=tsgen>"+steps+"</td><td width=70px align=center class=tsgen>"+passed+"</td><td width=70px align=center class=tsgen>"+failed+"</td><td width=70px align=center class=tsgen>"+warnings+"</td><td width=70px align=center class=tsgen><b><font size = 2 color = green>"+status+"</td></tr>'; document.write(txt);</script>";
			mObjFW0.write(summary);
		}//file:/
		//+Global.gstrResultPath
		else if(status.equalsIgnoreCase("FAIL")){
			String summary="\n<script>var txt='<tr><td width=100px align=center class=tsgen><a class=tcindex href=\"./"+this.mstrModuleName+"/"+MobileDriver.repo.get(deviceName)+"_"+deviceName+"_"+gameName+"_"+this.mstrTCName+".html\">"+testCase+"</a></td><td width=80px class=tsgen>"+module+"</td><td width=200px class=tsgen>"+description+"</td><td width=70px align=center class=tsgen>"+steps+"</td><td width=70px align=center class=tsgen>"+passed+"</td><td width=70px align=center class=tsgen>"+failed+"</td><td width=70px align=center class=tsgen>"+warnings+"</td><td width=70px align=center class=tsgen><b><font size = 2 color = red>"+status+"</td></tr>'; document.write(txt);</script>";
			mObjFW0.write(summary);
		}
		mObjFW0.close();
		}catch(IOException ioException){
			detailsAppendNoScreenshot("IoException occur ,hence skipping the current test step", "", "", "Fail");
			log.error(ioException.getMessage(),ioException);
		}
	}//end of summary

	public void endReport()
	{
		//		Thread.sleep(5000);
		try {
			mObjFW0=new FileWriter(filePath+this.mstrModuleName+File.separator+MobileDriver.repo.get(deviceName)+"_"+deviceName+"_"+gameName+"_"+this.mstrTCName+Constant.HTMLEXTENSION,true);

			Calendar calEnd = Calendar.getInstance();
			String endTime=mObjDateFormat.format(calEnd.getTime());

			Date date1 = mObjDateFormat.parse(detailStrtTime);
			Date date2 = mObjDateFormat.parse(endTime);
			long difference = date2.getTime()-date1.getTime();

			long var1=difference%1000;
			difference=(difference/1000);
			String timeDuration=difference+"."+var1;
			String summary1="\n<script>var txt='</table><br><br><table width=900px align = center><tr><td width=650px></td><td colspan=2 class=tsheader>Execution Time</td></tr><tr><td width=650px></td><td class=pfhead width=120px>Start Time</td><td class=pfind width=130px>"+detailStrtTime+"</td></tr><tr><td width=650px></td><td class=pfhead width=120px>End Time</td><td class=pfind width=130px>"+endTime+"</td></tr><tr><td width=650px></td><td class=pfhead width=120px>Duration</td><td class=pfind width=130px>"+timeDuration+" secs</td></tr></table>'; document.write(txt);</script>";
			mObjFW0.append(summary1);
			mObjFW0.close();
		} catch (IOException ioException) {
			log.error(ioException.getMessage(),ioException);
			detailsAppendNoScreenshot("IoException occur ,hence skipping the current test step", "", "", "Fail");
		} catch (ParseException parseException) {
			log.error(parseException.getMessage(),parseException);
		}
	}

	public void buildAutomationSummary()
	{
		// Initially writes a schema for automation summary report
		try {
			styleSheet.writeStyleSheet(filePath+MobileDriver.repo.get(deviceName)+"_"+deviceName+"_"+"Automation_summary"+Constant.HTMLEXTENSION,filePath);
			mObjFW0=new FileWriter(filePath+MobileDriver.repo.get(deviceName)+"_"+deviceName+"_"+"Automation_summary"+Constant.HTMLEXTENSION,true);

			mstrAppendString="\n<script>var txt='<title>Automation summary</title><table width=900px align=center class=reportheader><tr><td height=50px>Zensar Technologies<BR><td height=50px align=center>"+gstrCountryName+"</td></tr></table>'; document.write(txt);</script>";
			mObjFW0.append(mstrAppendString);

			String mstrBottomImg="\n</head><table align=center width=900px><tr><td width=900px><img src=\"./Images/Bottom.GIF\"></td></tr></table>";
			mObjFW0.append(mstrBottomImg);

			String summary="\n<script>var txt='<br><br><table align=center width=900px class=teststeps id=\"summary_table\"><tr><td align=center width=100px class=tsheader>Test_Case</td><td width=80px class=tsheader>Module</td><td width=200px class=tsheader>Description</td><td width=70px class=tsheader>Steps</td><td width=70px class=tsheader>Passed</td><td width=70px class=tsheader>Failed</td><td width=70px class=tsheader>Warnings</td><td width=70px class=tsheader>Status</td></tr>'; document.write(txt);</script>";
			mObjFW0.append(summary);

			//*****************************************
			for(int i=0;i<table2.get(deviceName).size();i++)
			{
				String str=filePath+"Dump/"+table2.get(deviceName).get(i)+".txt";
				File f=new File(str);
				FileReader fr=new FileReader(f);
				try(BufferedReader br = new BufferedReader(fr)){

					String line = br.readLine();
					while (line != null) {
						mObjFW0.append(line);
						mObjFW0.append("\n");
						line = br.readLine();
					}
				}
				fr.close();
				f.deleteOnExit();
			}
			Calendar calEnd = Calendar.getInstance();
			String endTime=mObjDateFormat.format(calEnd.getTime());
			Date date3 = mObjDateFormat.parse(startTime);
			Date date4 = mObjDateFormat.parse(endTime);
			long difference1 = date4.getTime() - date3.getTime();
			long var=difference1%1000;
			difference1=(difference1/1000);
			String duration=difference1+"."+var;

			String summary2="\n<script>var txt='</table><br><br><table width=900px align=center><tr><td width=650px></td><td colspan=2 class=tsheader>Execution Time</td></tr><tr><td width=650px></td><td class=pfhead width=120px>Start Time</td><td class=pfind width=130px>"+startTime+"</td></tr><tr><td width=650px></td><td class=pfhead width=120px>End Time</td><td class=pfind width=130px>"+endTime+"</td></tr><tr><td width=650px></td><td class=pfhead width=120px>Duration</td><td class=pfind width=130px>"+duration+" secs</td></tr></table>'; document.write(txt);</script>";
			mObjFW0.append(summary2);

			mObjFW0.close();
		} catch (FileNotFoundException fileNotFoundException) {
			log.error(fileNotFoundException.getMessage(),fileNotFoundException);
			detailsAppendNoScreenshot("Exception occur ,hence skipping the current test step", "", "", "Fail");

		} catch (IOException ioException) {
			log.error(ioException.getMessage(),ioException);
			detailsAppendNoScreenshot("IoException occur ,hence skipping the current test step", "", "", "Fail");
		} catch (ParseException parseException) {
			log.error(parseException.getMessage(),parseException);
			detailsAppendNoScreenshot("Exception occur ,hence skipping the current test step", "", "", "Fail");

		}
	}
	/**
	 * Author : Kamal Kumar Vishwakarma
	 * Description : This function creates the defect log for the defect.
	 * @param stepDesc
	 * @param expResult
	 * @param actResult
	 * @param report
	 * @param status
	 * @throws Exception
	 */

	public void detailAppend(String stepDesc,String expResult,String actResult,String report,String status) {

		String defectlog=filePath+"DefectLogs/"+this.mstrModuleName+"/"+MobileDriver.repo.get(deviceName)+"_"+deviceName+"_"+gameName+"_"+this.mstrTCName+"/"+ createTimeStampStr() + ".txt";
		

		try(FileWriter f1 = new FileWriter(filePath+this.mstrModuleName+File.separator+MobileDriver.repo.get(deviceName)+"_"+deviceName+"_"+gameName+"_"+this.mstrTCName+Constant.HTMLEXTENSION,true)){
			FileUtils.writeStringToFile(new File(defectlog), report, StandardCharsets.UTF_8);
			mintDetailCount++;
			
			if(mintDetailCount == 1) {
				initdetailTCReport(this.mstrTCName,mstrTCDesc);
			}
			if(actResult.equals("")&& status.equals(""))
			{
				mintStepNo++;
				mintMasterStepNo=0;
				mintSubStepNo=0;
				mstrAppendString="<script>var txt='<tr><td width=70px class=tsindlevel1>"+mintStepNo+"</td><td width=300px class=tsgen>"+stepDesc+"</td><td width=70px class=tsgen>"+expResult+"</td><td width=70px class=tsgen></td><td width=70px align=center class=tsgen><b><font size = 2 color = green></td><td></td></tr>'; document.write(txt);</script>";
				f1.append("\n"+mstrAppendString);
			}
			else
			{
				mintSubStepNo++;
				if(status.equalsIgnoreCase("PASS"))
				{
					mstrAppendString="<script>var txt='<tr><td width=70px class=tsindlevel2>"+mintStepNo+"."+mintSubStepNo+"</td><td width=300px class=tsgen>"+stepDesc+"</td><td width=70px class=tsgen>"+expResult+"</td><td width=70px class=tsgen>"+actResult+"</td><td width=70px align=center class=tsgen><b><font size = 2 color = green>"+status+"</td><td class=tsind width=50px><a target=_blank class=anibutton href=\"file:///"+defectlog+"\"><img class=screen src=\"file:///"+filePath+"Images/img.png\"></a></td></tr>'; document.write(txt);</script>";
				}
				else if(status.equalsIgnoreCase("FAIL"))
				{
					if(mintMasterStepNo==0){
						mintFailed++;
						mintMasterStepNo++;
					}
					mstrAppendString="<script>var txt='<tr><td width=70px class=tsindlevel2>"+mintStepNo+"."+mintSubStepNo+"</td><td width=300px class=tsgen>"+stepDesc+"</td><td width=70px class=tsgen>"+expResult+"</td><td width=70px class=tsgen>"+actResult+"</td><td width=70px align=center class=tsgen><b><font size = 2 color = red>"+status+"</td><td class=tsind width=50px><a target=_blank class=anibutton href=\"file:///"+defectlog+"\"><img class=screen src=\"file:///"+filePath+"Images/img.png\"></a></td></tr>'; document.write(txt);</script>";
				}
				else if(status.equalsIgnoreCase(Constant.INTERRUPTED))
				{
					if(mintMasterStepNo==0){
						mintFailed++;
						mintMasterStepNo++;
					}
					mstrAppendString="<script>var txt='<tr><td></td><td width=600px><b><font size = 2 color = Brown>"+stepDesc+"</td><td></td><td></td><td><b><font size = 2 color = Brown>"+status+"</td><td align=center width=50px><a target=_blank class=anibutton href=\"file:///"+defectlog+"\"><img class=screen src=\"file:///"+filePath+"Images/img.png\"></a></td></tr>'; document.write(txt);</script>";
				}
				f1.append("\n"+mstrAppendString);
			}
		}catch(IOException ioException) {
			log.error(ioException.getMessage(),ioException);
			detailsAppendNoScreenshot("Exception occur ,hence skipping the current test step", "", "", "Fail");
		}
		if(mintFailed==0)
		{
			mintPassed=mintStepNo;
			summary(MobileDriver.repo.get(deviceName)+"_"+deviceName+"_"+gameName+"_"+mstrTCName,mstrModuleName, mstrTCDesc, String.valueOf(mintStepNo), String.valueOf(mintPassed), String.valueOf(mintFailed), String.valueOf(mintWarnings), "PASS");
		}
		else
		{
			mintPassed=mintStepNo-mintFailed;
			summary(MobileDriver.repo.get(deviceName)+"_"+deviceName+"_"+gameName+"_"+mstrTCName,mstrModuleName, mstrTCDesc, String.valueOf(mintStepNo), String.valueOf(mintPassed), String.valueOf(mintFailed), String.valueOf(mintWarnings), "FAIL");
		}

	}
	
	public void detailsAppendFolderOnlyScreenshot(String stepDesc,String expResult,String actResult,String status, String folderName)
	{
		//Captures screenshot for calling step
		File scrFile = webdriver.getScreenshotAs(OutputType.FILE);

		// Now you can do whatever you need to do with it, for example copy somewhere
		String screenShotPath=filePath+this.mstrModuleName+"/"+MobileDriver.repo.get(deviceName)+"_"+deviceName+"_"+gameName+"_"+this.mstrTCName+"/"+folderName+"/"+ createTimeStampStr() + Constant.JPEGEXTENSION;
		File destFile =new File(screenShotPath);
		try {
			FileUtils.copyFile(scrFile, destFile);
		} catch (IOException ioException) {
			log.error(ioException.getMessage(),ioException);
		}
		boolean isFileDeleted = scrFile.delete();
		
		if(!isFileDeleted) {
			log.debug("Memory not cleared after taking screen shot, possible memory error.");
		}
		
	}
	public void detailsAppendFolderOnlyScreeshot( String folderName)
	{
		//Captures screenshot for calling step
		File scrFile = webdriver.getScreenshotAs(OutputType.FILE);

		// Now you can do whatever you need to do with it, for example copy somewhere
		try {
			String screenShotPath=filePath+this.mstrModuleName+"/"+MobileDriver.repo.get(deviceName)+"_"+deviceName+"_"+gameName+"_"+this.mstrTCName+"/"+folderName+"/"+ createTimeStampStr() + Constant.JPEGEXTENSION;
			File destFile =new File(screenShotPath);
			FileUtils.copyFile(scrFile, destFile);
			boolean isFileDeleted = scrFile.delete();
			
			if(!isFileDeleted) {
				log.debug("Memory not cleared after taking screen shot, possible memory error.");
			}
		} catch (IOException ioException) {
			log.error(ioException.getMessage(),ioException);
			detailsAppendNoScreenshot("Exception occur ,hence skipping the current test step", "", "", "Fail");

		}
		
	}
}
