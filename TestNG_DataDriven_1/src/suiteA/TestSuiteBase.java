package suiteA;

import org.testng.SkipException;
import org.testng.annotations.BeforeSuite;

import proj.Base.Setup;
import proj.Features.TestUtil;






public class TestSuiteBase extends Setup{
  
	

	@BeforeSuite
	public void checkSuiteSkip() throws Exception{
		
		Setup.initialize();
		System.out.println("Setup is Initialized");
		Setup.APP_LOGS.info("Checking Runmode of Suite A");
		if(!TestUtil.isSuiteRunnable(Setup.suiteXls, "Suite A")){
		
			System.out.println("the runmode of suite A is " + TestUtil.isSuiteRunnable(Setup.suiteXls, "Suite A"));
			TestUtil.reportDataSetResult(Setup.suiteXls, "Test Suite", 2, "Skipped");
			Setup.APP_LOGS.info("Skipped Suite A as the runmode was set to NO");
			Setup.pdf.table("Suite A", "All Test Cases","Skip");
			Setup.pdf.pdfList();
			//Setup.pdf.addpdf();
			//Setup.recorder.stop();
			throw new SkipException("Runmode of Suite A set to no. So Skipping all tests in Suite A");
			
		}
		else
		{
			Setup.APP_LOGS.info("Executing Suite A as the runmode was set to Yes");
		}
		
	}
	
}
