package suiteA;

import java.util.ArrayList;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import proj.Base.CommonMethods;
import proj.Base.Setup;
import proj.Base.WebPage;
import proj.Features.TestUtil;
import proj.PageActions.BgbcLoginPageActions;
//import proj.PageActions.FoodPandaPageActions;

public class Bgbc_execution  extends TestSuiteBase{
	
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
		createWebDriverInstance(BrowserName);
		InvokedBrowser=BrowserName;
		com = new CommonMethods();
		openURL(URL);
		maximize();
			
	}

	/**
	 * To login the Foodpanda.....
	 * 
	 * 
	 * @throws Exception
	 */

	@Test
	public void TC_81_001() throws Exception
	{
		try
		{
			Setup.APP_LOGS.info("Started executing " + new Exception().getStackTrace()[0].getMethodName());
			//TestUtil.getFirstDataRowNum(suiteAxls, "TestData","TC_81_002");
			//System.out.println("The TEst Case NAme "+ new Exception().getStackTrace()[0].getMethodName().toString());
			TDUserName = suiteAxls.getCellData("TestData", "UserName", TestUtil.getFirstDataRowNum(suiteAxls,"TestData",new Exception().getStackTrace()[0].getMethodName().toString()));
			TDPassword = suiteAxls.getCellData("TestData", "Password", TestUtil.getFirstDataRowNum(suiteAxls,"TestData",new Exception().getStackTrace()[0].getMethodName().toString()));
			BgbcLoginPageActions login = new BgbcLoginPageActions();
			login.Bgbc_Login(TDUserName,TDPassword);
			isTestPass = true;
			Setup.pdf.table(this.getClass().getSimpleName(), new Exception().getStackTrace()[0].getMethodName(), "Pass");
			com.reportTestResult(Setup.suiteAxls,InvokedBrowser,new Exception().getStackTrace()[0].getMethodName(), isTestPass);
			
		}
		catch(Exception e)
		{
			isTestPass = false;
			com.takeScreenShots(new Exception().getStackTrace()[0].getMethodName());
			com.reportTestResult(Setup.suiteAxls,InvokedBrowser,new Exception().getStackTrace()[0].getMethodName(), isTestPass);
			Setup.pdf.table(this.getClass().getSimpleName(), new Exception().getStackTrace()[0].getMethodName(), "Fail");
			Setup.APP_LOGS.info("Executed " + new Exception().getStackTrace()[0].getMethodName());
			throw e;
		}
	}



	@AfterClass
	public void tearDown() throws Exception
	{
		close();
		Setup.pdf.pdfList();
		Setup.pdf.addpdf(InvokedBrowser);
		String Currentdate= com.currentDate();
		String DateTime = com.currentDateTime();
		Setup.pdf.rename(System.getProperty("user.dir")+ "//reports//"+Currentdate+"//"+"Report_"+InvokedBrowser+"_"+DateTime+".pdf");
		Setup.recorder.stop();
	}
}


