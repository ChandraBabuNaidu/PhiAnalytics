
package suiteA;

import java.util.ArrayList;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import proj.Base.CommonMethods;
import proj.Base.Setup;
import proj.Base.WebPage;
import proj.PageActions.FoodPandaPageActions;



public class FoodPanda_TC extends TestSuiteBase
{

	public static String TDUserName;
	public static String TDPassword;
	public static String eCompany;
	public static String TDOwnerName;
	boolean isTestPass=false;
	protected String InvokedBrowser=null;
	public static proj.Base.CommonMethods com;
	public String URL=null;
	
	ArrayList<String> UmatchedSubMenu;
	
	ArrayList<String> Menubar;
	String listcountMenuItems;
	@Parameters({ "BrowserName" })
	@BeforeClass
	public void openBrowser(String BrowserName) throws Exception
	{
		URL = Setup.CONFIG.getProperty("BaseURL");
		WebPage.createWebDriverInstance(BrowserName);
		InvokedBrowser=BrowserName;
		com = new CommonMethods();
		WebPage.openURL(URL);
		WebPage.maximize();
			
	}

	/**
	 * To login the Foodpanda.....
	 * 
	 * 
	 * @throws Exception
	 */

	@Test
	public void TC_FoodPanda_01() throws Exception
	{
		try
		{
			Setup.APP_LOGS.info("Started executing " + new Exception().getStackTrace()[0].getMethodName());
			/*
			TDUserName = Setup.suiteAxls.getCellData("TestData", "UserName", 128);
			TDPassword = Setup.suiteAxls.getCellData("TestData", "Password", 128);*/
			FoodPandaPageActions fp = new FoodPandaPageActions();
			fp.foodpanda_Login("Test", "Test");
			Setup.pdf.table(this.getClass().getSimpleName(), new Exception().getStackTrace()[0].getMethodName(), "Pass");
			
			
		}
		catch(Exception e)
		{
			isTestPass = false;
			com.takeScreenShots(new Exception().getStackTrace()[0].getMethodName());
			//com.reportTestResult(Setup.suiteAxls,InvokedBrowser,new Exception().getStackTrace()[0].getMethodName(), isTestPass);
			Setup.pdf.table(this.getClass().getSimpleName(), new Exception().getStackTrace()[0].getMethodName(), "Fail");
			Setup.APP_LOGS.info("Executed " + new Exception().getStackTrace()[0].getMethodName());
			throw e;
		}
	}



	@AfterClass
	public void tearDown() throws Exception
	{
		WebPage.close();
		Setup.pdf.pdfList();
		Setup.pdf.addpdf(InvokedBrowser);
		String Currentdate= com.currentDate();
		String DateTime = com.currentDateTime();
		Setup.pdf.rename(System.getProperty("user.dir")+ "//reports//"+Currentdate+"//"+"Report_"+InvokedBrowser+"_"+DateTime+".pdf");
		Setup.recorder.stop();
	}
}
