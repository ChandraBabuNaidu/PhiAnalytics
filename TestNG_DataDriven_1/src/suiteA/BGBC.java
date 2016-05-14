package suiteA;

import org.testng.annotations.Test;

import proj.Base.Setup;
import proj.Features.TestUtil;

public class BGBC extends TestSuiteBase {
	@Test
	public void sample(){
		System.out.println("The row number "+ TestUtil.getFirstDataRowNum(suiteAxls, "Test Cases","TC_81_003"));
	}

}
