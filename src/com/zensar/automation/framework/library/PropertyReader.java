package com.zensar.automation.framework.library;

import java.io.File;
import java.io.FileInputStream;
import java.util.Base64;
import java.util.Base64.Decoder;
import java.util.List;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.zensar.automation.framework.utils.Constant;
	//default constructor
	//load default properties(String token)
	//load game specific propertie ie.test env reding(String gamename)
	//load env specific properties(String envname)
	//load all properties (Token, envname,gameName)
public class PropertyReader {

	protected static PropertyReader instance = null;
	
	protected Properties prop = new Properties();

	public Properties getProp() {
		return prop;
	}

	public void setProp(Properties prop) {
		this.prop = prop;
	}

	Logger log = Logger.getLogger(PropertyReader.class.getName());
	

	public PropertyReader()
	{
		
	}

	public static PropertyReader getInstance(){
		if (instance == null) {
			instance = new PropertyReader();
		}
		return instance;
	}

	// Get and set method to access the properties 
	public String getProperty(String key) {
		return prop.getProperty(key);
	}

	public void setProperty(String key, String value) {

		prop.setProperty(key, value);
	}


	/*Load Deafult properties file and return boolean 
	 * 
	 * */
	public  boolean loadDefaultProperties() throws UnableToLoadPropertiesException
	{
		boolean isDefaultPropLoaded=false;
		Properties deafultProp = new Properties();
		log.info("Loading default properties file...");

		if(System.getProperty("Token") !=null)
		{
			try(FileInputStream defaultin= new FileInputStream(new File(Constant.CICD_DEFAULTPROP_FILE_PATH)))
			{

				log.debug("Token ="+System.getProperty("Token"));
				log.debug("Default properties file path="+Constant.CICD_DEFAULTPROP_FILE_PATH);
				isDefaultPropLoaded=true;
				deafultProp.load(defaultin);
			}catch (Exception e) {
				log.error(" Exception occur in Testpropreader :",e);
				throw new UnableToLoadPropertiesException(e.getMessage(),e);

			}
		}else{
			try( FileInputStream defaultin = new FileInputStream(new File(Constant.DEFAULTPROP_FILE_PATH)))
			{

				log.debug("Default properties file path="+Constant.DEFAULTPROP_FILE_PATH);
				isDefaultPropLoaded=true;
				deafultProp.load(defaultin);
			}catch (Exception e) {
				log.error(" Exception occur in Testpropreader :",e);
				throw new UnableToLoadPropertiesException(e.getMessage(),e);

			}
		}

		deafultProp.forEach((key, value) -> setProperty((String)key,(String)value));

		
		log.info("loading completed for Default properties");

		return isDefaultPropLoaded;
	}

	/*Load game specific propeties such as testdata file paths, game xml path etc. and return boolean
	 * @param String game Name
	 * */

	public boolean loadGameProperties(String gameName) throws UnableToLoadPropertiesException
	{
		boolean isTestPropLoaded=false;
		Properties envProp = new Properties();
		
		log.info("Loading Test Environment properties file..");
		try(FileInputStream in=new FileInputStream(new File("./"+gameName+Constant.GAMETESTPROP_FILE_PATH)))
		{

			log.debug("Test Environment properties file path=./"+gameName+Constant.GAMETESTPROP_FILE_PATH);

			isTestPropLoaded=true;
			envProp.load(in);
			envProp.forEach((key, value) -> setProperty((String)key,(String)value));

			setProperty("TestData_Excel_Path", "./"+gameName+Constant.TESTDATA_EXCEL_PATH);
			setProperty("Suit_Excel_Path", "./"+gameName+Constant.SUIT_EXCEL_PATH);
			setProperty("Regression_Excel_Path", "./"+gameName+Constant.REGRESSION_EXCEL_PATH);
			setProperty("XMLFilePath", "./"+gameName+"/Config/"+gameName+".xml");
			setProperty("GameName", gameName);

			Decoder decoder=Base64.getDecoder();

			setProperty("DBUser", new String(decoder.decode(getProperty("DBUser"))));
			setProperty("DBpass", new String(decoder.decode(getProperty("DBpass"))));

			setProperty("CurrencyTestDataPath", "./"+gameName+Constant.CURRENCY_TESTDATA_FILE_PATH);
			setProperty("FreeGamesTestDataPath", "./"+gameName+Constant.FREEGAMES_TESTDATA_FILE_PATH);
			setProperty("FreeSpinsTestDataPath", "./"+gameName+Constant.FREESPINS_TESTDATA_FILE_PATH);
			setProperty("BigWinTestDataPath", "./"+gameName+Constant.BIGWIN_TESTDATA_FILE_PATH);
			setProperty("SanityTestDataPath","./"+gameName+Constant.SANITY_TESTDATA_FILE_PATH);
			
			setProperty("MobileCurrencyTestDataPath", "./"+gameName+Constant.MOBILE_CURRENCY_TESTDATA_FILE_PATH);
			setProperty("MobileFreeGamesTestDataPath", "./"+gameName+Constant.MOBILE_FREEGAMES_TESTDATA_FILE_PATH);
			setProperty("MobileFreeSpinsTestDataPath", "./"+gameName+Constant.MOBILE_FREESPINS_TESTDATA_FILE_PATH);
			setProperty("MobileBigWinTestDataPath", "./"+gameName+Constant.MOBILE_BIGWIN_TESTDATA_FILE_PATH);
			setProperty("MobileSanityTestDataPath","./"+gameName+Constant.MOBILE_SANITY_TESTDATA_FILE_PATH);
			
			log.info("loading completed for Test Environment properties");

		}catch (Exception e) {
			log.error("Exception occur in Testpropreader :",e);
			throw new UnableToLoadPropertiesException(e.getMessage(),e);

		}
		return isTestPropLoaded;
	}

		
	
	
}//end of class
