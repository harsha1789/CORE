package com.zensar.automation.framework.library;
import java.io.IOException;
import java.util.Map;

import com.zensar.automation.framework.utils.ExcelDataPoolManager;

/*
 * Test class to read the test cases from config file
 * */
public class TestCaseReader {
	 String mstrTestDesc;
	 String gstrModuleSuitFilePath;
	 String gstrModuleName;
	 TestCaseReader(String gstrModuleSuitFilePath1, String gstrModuleName1 )
	{
		gstrModuleSuitFilePath=gstrModuleSuitFilePath1;
		gstrModuleName=gstrModuleName1;
	}
	public void getInfo(String mstrTestCaseName) throws IOException
	{
		String name;
		ExcelDataPoolManager oExcelFile = new ExcelDataPoolManager();
		Map<String, String> rowData = null;
		int rowcount =oExcelFile.rowCount(gstrModuleSuitFilePath+gstrModuleName+".xls","Config File");
		for(int i=0;i<rowcount;i++)
		{
			rowData = oExcelFile.readExcelByRow(gstrModuleSuitFilePath+gstrModuleName+".xls","Config File" , i);
			name=rowData.get("TestCase");
			if(name.equalsIgnoreCase(mstrTestCaseName))
			{
				mstrTestDesc=rowData.get("TestCaseDescription");
			}
		}
	}
}
