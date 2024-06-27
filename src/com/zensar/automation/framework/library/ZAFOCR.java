package com.zensar.automation.framework.library;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.HashMap;

import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

/**
 * This is the OCR file reads the text from image and converts in to text
 *
 */
public class ZAFOCR 
{
	HashMap<String, String> languageMap=null;
	public ZAFOCR(){
		languageMap=new HashMap<String,String>();
		languageMap.put("en","eng"); //bg,cs,da,de,el,es,fi,fr,hi,    hr,hu,id,it,ja,ko,lv,lt,nl,no,pl,pt,pt-br,ro,ru,sk,sl,sv,th,tr,vi,uk,zh-cn
		languageMap.put("bg","bul");
		languageMap.put("cs","ces");
		languageMap.put("da","dan");
		languageMap.put("de","deu");
		languageMap.put("el","ell");
		languageMap.put("es","spa");
		languageMap.put("fi","fin");
		languageMap.put("fr","fra");
		languageMap.put("hi","hin");
		languageMap.put("hr","hrv");
		languageMap.put("hu","hun");
		languageMap.put("id","ind");
		languageMap.put("it","ita");
		languageMap.put("ja","jpn");
		languageMap.put("ko","kor");
		languageMap.put("lv","lav");
		languageMap.put("lt","lit");
		languageMap.put("nl","nld");
		languageMap.put("no","nor");
		languageMap.put("pl","pol");
		languageMap.put("pt","por");
		languageMap.put("pt-br","por");
		languageMap.put("ro","ron");
		languageMap.put("ru","rus");
		languageMap.put("sk","slk");
		languageMap.put("sl","slv");
		languageMap.put("sv","swe");
		languageMap.put("th","tha");
		languageMap.put("tr","tur");
		languageMap.put("vi","vie");
		languageMap.put("uk","ukr");
		languageMap.put("zh-cn","chi_tra");
		languageMap.put("zh-tw","chi_tra");
		
	}
    public String getText(BufferedImage  image)
    {
    	String result=null;
        System.out.println("Going to read image text");
        
        try {
			
			Tesseract tesseract = new Tesseract();
			//tesseract.setDatapath("C:\\Program Files\\Tesseract-OCR\\tessdata");
			tesseract.setDatapath(PropertyReader.getInstance().getProperty("tessdata-master"));//
			//tesseract.setDatapath("D:/softwares/tessdata_best-master");
			//tesseract.setDatapath("D:/softwares/tessdata_best-master");
			tesseract.setLanguage("eng");
			tesseract.setPageSegMode(3);
			tesseract.setOcrEngineMode(0);
			result = tesseract.doOCR(image);
			//System.out.println(result);
		} catch (TesseractException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
        return result;
    }
    
    public String getText(BufferedImage  image,String language)
    {
    	String result=null;
        System.out.println("Going to read image text");
        
        try {
			
			Tesseract tesseract = new Tesseract();
			//tesseract.setDatapath("C:\\Program Files\\Tesseract-OCR\\tessdata");
			
			//tesseract.setDatapath("C:\\Users\\ak47374.ZENDER\\Documents\\tessract\\tessdata-master");
			//tesseract.setDatapath("D:/softwares/tessdata_best-master");
			System.out.println("Tesssract lang file using ::" + languageMap.get(language));
			
			if(language.equalsIgnoreCase("hi")||language.equalsIgnoreCase("th")){
				tesseract.setDatapath(PropertyReader.getInstance().getProperty("tessdata_best-masterPath"));
				tesseract.setOcrEngineMode(1);
			}else{
				tesseract.setDatapath(PropertyReader.getInstance().getProperty("tessdata-master"));	
				tesseract.setOcrEngineMode(0);
			}
			tesseract.setLanguage(languageMap.get(language));
			tesseract.setPageSegMode(3);
			result = tesseract.doOCR(image);
			//System.out.println(result);
		} catch (TesseractException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
        return result;
    }
    
    
    public String getText(File  image)
    {
    	String result=null;
        System.out.println( "Hello World!" );
        
        try {
			
			Tesseract tesseract = new Tesseract();
			//tesseract.setDatapath("C:\\Program Files\\Tesseract-OCR\\tessdata");
			tesseract.setDatapath(PropertyReader.getInstance().getProperty("tessdata-master"));
			//tesseract.setDatapath("D:/softwares/tessdata_best-master");
			tesseract.setLanguage("eng");
			tesseract.setPageSegMode(3);
			tesseract.setOcrEngineMode(0);
			result = tesseract.doOCR(image);
			System.out.println(result);
		} catch (TesseractException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
        return result;
    }
}
