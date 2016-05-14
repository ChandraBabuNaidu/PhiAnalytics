package proj.Base;










import java.io.File;
import java.io.IOException;

import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.TestListenerAdapter;

public class Screenshot extends TestListenerAdapter {

@Override
public void onTestFailure(ITestResult result) {

	
    File file = new File(System.getProperty("user.dir"));
   

	

Reporter.setCurrentTestResult(result);
System.out.println(file.getAbsolutePath());

///Reporter.log("screenshot saved at "+file.getAbsolutePath()+"\\screenShots\\"+result.getName()+".jpg");
//String NewFileNamePath = "<a href="+"http://draft.blogger.com/+NewFileNamePath+"+"TestDAta" +"</a>";
//Reporter.log("<a href=test data </a> ");
//Reporter.log(NewFileNamePath);
try {
	CommonMethods.xScreenShot();
} catch (InterruptedException e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
}
Reporter.setCurrentTestResult(null);
}


@Override
public void onTestSkipped(ITestResult result) {
// will be called after test will be skipped
}

@Override
public void onTestSuccess(ITestResult result) {
// will be called after test will pass 
}

}
	

