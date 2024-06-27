package com.zensar.automation.framework.library;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import org.apache.log4j.Logger;

/**
 * This class used to set some initial necessary path 
 * such as result path, Testcase path 
 *  
 * */
public class Global {	

	String gstrConfigFilesPath;
	String gstrDriverPath;
	String gstrFunctionLibraryPath;
	String gstrModuleFunctionLibraryPath;
	String gstrSuitFilePath;
	String gstrModuleSuitFilePath;
	String gstrModuleTestScriptPath;	
	String gstrModuleTestDataPath;	

	String gstrTestCaseEditorPath;	
	String gstrRecoveryManagerPath;
	String gstrModuleRecoveryPath;
	String gstrRootPath;	
	String gstrModuleRootPath;
	String gstrModuleTestCasePath;
	int gintRowCount=0;
	int gintStepCount=0;
	List<String> gstrMachineIp=new ArrayList<>();
	List<String> gstrBrowser=new ArrayList<>();
	int gintMachineCount=0;
	int gintsummaryFlag=0;
	String gameName;
	String gstrModuleName;
	Calendar gCalenderCalStart; 
	String gstrStartTime;
	String path=System.getProperty("user.dir");
	String gstrResultPath;
	 

	Logger log = Logger.getLogger(Global.class.getName());
	/**
	 * Parameterized constructor
	 * @param gameName
	 */
	 
	public Global(String gameName)
	{
		this.gameName=gameName;
	}
	
	/**
	 * @return the gstrModuleName
	 */
	public String getGstrModuleName() {
		return gstrModuleName;
	}

	/**
	 * @param gstrModuleName the gstrModuleName to set
	 */
	public void setGstrModuleName(String gstrModuleName) {
		this.gstrModuleName = gstrModuleName;
	}

	/**
	 * @return the gCalenderCalStart
	 */
	public Calendar getgCalenderCalStart() {
		return gCalenderCalStart;
	}

	/**
	 * @param gCalenderCalStart the gCalenderCalStart to set
	 */
	public void setgCalenderCalStart(Calendar gCalenderCalStart) {
		this.gCalenderCalStart = gCalenderCalStart;
	}

	/**
	 * @return the gstrStartTime
	 */
	public String getGstrStartTime() {
		return gstrStartTime;
	}

	/**
	 * @param gstrStartTime the gstrStartTime to set
	 */
	public void setGstrStartTime(String gstrStartTime) {
		this.gstrStartTime = gstrStartTime;
	}

	/**
	 * @return the path
	 */
	public String getPath() {
		return path;
	}

	/**
	 * @param path the path to set
	 */
	public void setPath(String path) {
		this.path = path;
	}

	/**
	 * @return the gstrResultPath
	 */
	public String getGstrResultPath() {
		return gstrResultPath;
	}

	/**
	 * @param gstrResultPath the gstrResultPath to set
	 */
	public void setGstrResultPath(String gstrResultPath) {
		this.gstrResultPath = gstrResultPath;
	}

	/**
	 * This Method set the initail nessary path
	 * @throws IOException
	 */
	public void cfnRootPath() throws IOException 
	{			
		File fs=new File("");
		String a=fs.getCanonicalPath();
		this.gstrRootPath=a;
		this.gstrRootPath=gstrRootPath+"/src";
		log.info("Setting nessary path....");
		this.gstrRootPath=gstrRootPath.replace("\\", "/")+File.separator;
		this.gstrModuleRootPath=gstrRootPath+"Modules/";


		this.gstrConfigFilesPath =gstrRootPath+"ConfigFiles/";


		this.gstrDriverPath =gstrRootPath+"Driver/";


		this.gstrFunctionLibraryPath =gstrRootPath+"FunctionLibrary/";


		this.gstrSuitFilePath =gstrRootPath+"SuitFile/";

		String currentToken=System.getProperty("Token");
		if(currentToken!=null)
		{	
			currentToken+="\\";
			this.gstrResultPath=".\\"+PropertyReader.getInstance().getProperty("GameName")+"\\Reports\\"+currentToken;

		}
		else
		{
			// Set desire result path
			this.gstrResultPath=".\\"+gameName+"\\Reports\\Default\\";
		}

		this.gstrTestCaseEditorPath=gstrRootPath+"TestCaseEditor/";


		this.gstrRecoveryManagerPath=gstrRootPath+"RecoveryManager/";


	}
	
	public void cfnModuleRootPath()  
	{	
		File f1=new File(gstrResultPath+gstrModuleName);
		f1.mkdirs();
		if(f1.setWritable(true))
			log.debug("File set to writable");
		this.gstrModuleFunctionLibraryPath=gstrModuleRootPath+gstrModuleName+"/FunctionLibrary/";		

		this.gstrModuleSuitFilePath =gstrModuleRootPath+gstrModuleName+"/SuitFile/";	

		this.gstrModuleTestScriptPath =gstrModuleRootPath+gstrModuleName+"/TestScript/";	

		this.gstrModuleTestDataPath=gstrModuleRootPath+gstrModuleName+"/TestData/";	

		this.gstrModuleTestCasePath=gstrModuleRootPath+gstrModuleName+"/TestCase/";	

		this.gstrModuleRecoveryPath=gstrModuleRootPath+gstrModuleName+"/Recovery/";	


	}
	public void cfnConfigDeclaration() throws IOException
	{
		cfnRootPath();
	}	
}
