package proj.PageActions;

import proj.Base.Setup;
import proj.Base.WebPage;



public class BGBCLoginPageActions {
	

	
	

	public void bgbc_Login(String UserName, String Password) throws Exception
	{
		Setup.APP_LOGS.info("The user trying to login BGBC Application ");
		
		WebPage.clickAndWait(Setup.OR.getProperty("btnLogin_Home"), "X");
		
		
		WebPage.sendKeys(Setup.OR.getProperty("txtEmail"), "id", UserName);
		WebPage.sendKeys(Setup.OR.getProperty("txtPass"), "id", Password);
		WebPage.clickAndWait(Setup.OR.getProperty("btnLogin"), "X");
		
	}

	public void bgbc_Logout() throws Exception
	{
		WebPage.click(Setup.OR.getProperty("btnLogout"), "link");
	}
	

		
}
