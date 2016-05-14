package proj.PageActions;

import proj.Base.Setup;
import proj.Base.WebPage;



public class FoodPandaPageActions {
	

	
	

	public void foodpanda_Login(String UserName, String Password) throws Exception
	{
		Setup.APP_LOGS.info("The user trying to login foodpanda ");
		
		WebPage.clickAndWait(Setup.OR.getProperty("lngbtn"), "X");
		WebPage.clickAndWait(Setup.OR.getProperty("lnkLgin"), "link");
		
		WebPage.sendKeys(Setup.OR.getProperty("txtEmail"), "id", UserName);
		WebPage.sendKeys(Setup.OR.getProperty("txtPassword"), "id", Password);
		WebPage.clickAndWait(Setup.OR.getProperty("btnLogin"), "id");
		
	}

	

		
}
