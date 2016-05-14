package proj.Base;

import java.io.File;
import java.io.FileInputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;

import proj.Features.CreatePDF;
import proj.Xlsx.Xls_Reader;


import atu.testrecorder.ATUTestRecorder;



public class Setup 
{
	
	public static CreatePDF pdf;
	public static Logger APP_LOGS=null;
	public static Properties CONFIG=null;
	public static Properties OR=null;

	public static Xls_Reader suiteXls=null;
	public static Xls_Reader suiteAxls=null;
	public static Xls_Reader suiteBxls=null;
	public static ATUTestRecorder recorder;
	public static  boolean isInitialized=false;

	
	/**
	 * it is the default/ first to executed.
	 * It initialize all the pre-configured files like
	 * 
	 * 1. Creates PDF Report Object for the CreatePDF Class in the poc.features 
	 * 	  Object initialize --- Where PDF should be created with name for each suite executed.
	 * 	  and used all over the class to generated pdf report
	 * 
	 * 2. Creates Object for APP_Logs -- Used to report the logs
	 * 	  in user defined way.
	 * 
	 * 3. Creates Object to the Recorder -- Used to record the executed script suite wise manner. 
	 * 
	 * 4. Creates Object to all the Excel Class Used in the project.
	 * 
	 * @throws Exception
	 */
	
	public static void initialize() throws Exception
	{
		// OBJECT DECLARATION OF PDF CREATION
					@SuppressWarnings("unused")
					boolean makeDir = false;
			        Date date = new Date();
			        SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy") ;
			        String curDate =dateFormat.format(date);
			        File Path = new File(System.getProperty("user.dir")+ "//reports//"+curDate);
			        makeDir = Path.mkdir();
					SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy,HH-mm");
					String formattedDate = sdf.format(date);
					formattedDate.toString();
					pdf = new CreatePDF(Path+"//"+ "Report" +"-" + formattedDate + ".pdf" );
					
					
		//To Record the running script
					
					recorder = new ATUTestRecorder("Automationbatch - "+formattedDate,false);
					recorder.start();	
					
					
		
		if(!isInitialized)
		{
			
		try
		{
			
			// DECLARATION OF LOG4J FOR LOGGING.
			APP_LOGS=Logger.getLogger(Setup.class.getName());
			DOMConfigurator.configure("log4j.xml");
			
			
			// TO LOAD  General PROPERTIES FILE. 
			FileInputStream gc = new FileInputStream(System.getProperty("user.dir")+"//src//proj//Configuration//Generalcon.properties");
			CONFIG= new Properties();
			CONFIG.load(gc);
			APP_LOGS.info("--Config file is picked from the given path: Generalcon.properties");
			
			
			
			// TO LOAD  Object PROPERTIES FILE. 
			FileInputStream ORF = new FileInputStream(System.getProperty("user.dir")+"//src//proj//Configuration//ObjectRepository.properties");
			OR= new Properties();
			OR.load(ORF);
			APP_LOGS.info("--Config file is picked from the given path: OBJ.properties");
			
			
			
			// TO INITIALIZE EXCEL FILES. 
			
			APP_LOGS.info("Initializing Excel Files");
			suiteXls = new Xls_Reader(System.getProperty("user.dir")+"//src//proj//Xlsx//Suite.xlsx");
			APP_LOGS.info("Suite.xlsx file is Initialized");
			suiteAxls = new Xls_Reader(System.getProperty("user.dir")+"//src//proj//Xlsx//Suite A.xlsx");
			APP_LOGS.info("Suite A.xlsx file is Initialized");
			//suiteBxls = new Xls_Reader(System.getProperty("user.dir")+"//src//poc//xlsx//Suite B.xlsx");
			//APP_LOGS.info("Suite B.xlsx file is Initialized");
				
			isInitialized=true;
			
			
			
			
		}
		
		catch(Exception ex)
		{
			 System.out.println("File not found in  ");
			 ex.printStackTrace();
			 APP_LOGS.warn("--No file found in the given path");
			 pdf.table("Setup", "setup", "Fail");
			 
			
		}
		
		
		}	

	}
	
	
	
}
