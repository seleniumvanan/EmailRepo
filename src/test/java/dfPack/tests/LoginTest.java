package dfPack.tests;

import java.util.HashMap;

import org.testng.SkipException;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.LogStatus;

import dfPack.base.BaseTest;
import dfPack.util.DataUtil;
import dfPack.util.ExtentManager;
import dfPack.util.MyXLSReader;
import junit.framework.Assert;

public class LoginTest extends BaseTest {
	

	//Comment here for Git testing purpose
	//comment add at LoginTest.java file for git at 4:38pm 04/10/2020 
	//comment added from branchkalai

	@BeforeClass
	public void init() {
		
		initialise();
		
	}
	
	
	@DataProvider
	public Object[][] getData() throws Exception{
		
		String filePath = prop.getProperty("xlsxFilePath");
		
	    xls = new MyXLSReader(filePath);
	    
	    Object[][] testData = DataUtil.getTestData(xls,"LoginTest","Data");
		
	    return testData;
	}
	
	
	@Test(dataProvider="getData")
	public void doLoginTest(HashMap<String,String> map) {	
		
		eReport = ExtentManager.getInstance();
		eTest = eReport.startTest("LoginTest");
		eTest.log(LogStatus.INFO, "Login test started");
		
		if(!DataUtil.isRunnable(xls,"LoginTest","Testcases") || map.get("Runmode").equals("N")){
			
			eTest.log(LogStatus.SKIP, "Skipping the test as the run mode is set to N");
			throw new SkipException("Skipping the test as the run mode is set to N");
			
		}
		
		//Automation code - starts here
		openBrowser(map.get("Browser"));
		
		navigate("appURL");
		
		 boolean actualResult =doLogin(map.get("LoginUsername_id"),map.get("LoginPassword_id"));
		 
		 boolean expectedResult = false;
		    
		    if(map.get("ExpectedResult").equals("Success")) {
		    	
		    	expectedResult = true;
		    	
		    }else {
		    	
		    	expectedResult = false;
		    	
		    }
				
			
			if(actualResult==expectedResult) {
				
				reportPass("doLoginTest got passed");				
				
			}else {
				
				reportFail("doLoginTest got failed");				
				Assert.fail("doLoginTest got failed");
				
			}
			
		
		}
	
	 @AfterMethod
		public void testClosure() {
			
			eReport.endTest(eTest);
			
			eReport.flush();
			
			if(driver!=null) {
			driver.quit();
			}
		}
	
	
}
