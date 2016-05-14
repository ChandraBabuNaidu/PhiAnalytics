package proj.PageActions;

import proj.Base.Setup;
import proj.Base.WebPage;

public class BgbcLoginPageActions extends WebPage {

	public void Bgbc_Login(String UserName, String Password) throws Exception
	{
		Setup.APP_LOGS.info("The user trying to login bgbc ");
		
		//WebPage.clickAndWait(Setup.OR.getProperty("lngbtn"), "X");
		clickAndWait(Setup.OR.getProperty("logbtnhome" ) ,"X");
		
		sendKeys(Setup.OR.getProperty("txtEmail"),"id" ,  UserName);
		sendKeys(Setup.OR.getProperty("txtPassword"),"id", Password);
		clickAndWait(Setup.OR.getProperty("logbtn"),"X");
		clickAndWait(Setup.OR.getProperty("logoutbtn"),"X");
		
	}

	
}
