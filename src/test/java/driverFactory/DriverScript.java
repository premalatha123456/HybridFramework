package driverFactory;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.Test;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import commonFunctions.FunctionLibrary;
import utilities.ExcelFileUtil;

public class DriverScript {
	WebDriver driver;
	String inputpath="./FileInput/DataEngine.xlsx";
	String outputpath="./FileOutput/HybridResults.xlsx";
	ExtentReports report;
	ExtentTest logger;
	String TestCases="MasterTestCases";

	@Test
	public void startTest() throws Throwable
	{ 
		String Module_Status;
		ExcelFileUtil xl=new ExcelFileUtil(inputpath);
		for(int i=1;i<=xl.rowCount(TestCases);i++)
		{
			if(xl.getCellData(TestCases, i, 2).equalsIgnoreCase("Y"))
			{
				String TCModule=xl.getCellData(TestCases, i, 1);
				report=new ExtentReports("./target/ExtentReports/"+TCModule+FunctionLibrary.generateDate()+".html");
         		//logger.assignAuthor("Ranga");
				logger=report.startTest(TCModule);
				for(int j=1;j<=xl.rowCount(TCModule);j++)
				{
					String Description=xl.getCellData(TCModule, j, 0);
					String Object_Type=xl.getCellData(TCModule, j, 1);
					String Lname=xl.getCellData(TCModule, j, 2);
					String Lvalue=xl.getCellData(TCModule, j, 3);
					String Test_Data=xl.getCellData(TCModule, j, 4);
					try
					{
						if(Object_Type.equalsIgnoreCase("startBrowser"))
						{
							driver=FunctionLibrary.startBrowser();
							logger.log(LogStatus.INFO, Description);
						}
						if(Object_Type.equalsIgnoreCase("openUrl"))
						{
							FunctionLibrary.openUrl();
							logger.log(LogStatus.INFO, Description);
						}
						if(Object_Type.equalsIgnoreCase("waitForElement"))
						{
							FunctionLibrary.waitForElement(Lname,Lvalue,Test_Data);
							logger.log(LogStatus.INFO, Description);
						}
						if(Object_Type.equalsIgnoreCase("typeAction"))
						{
							FunctionLibrary.typeAction(Lname, Lvalue, Test_Data);
							logger.log(LogStatus.INFO, Description);
						}
						if(Object_Type.equalsIgnoreCase("clickAction"))
						{
							FunctionLibrary.clickAction(Lname, Lvalue);
							logger.log(LogStatus.INFO, Description);
						}
						if(Object_Type.equalsIgnoreCase("validateTitle"))
						{
							FunctionLibrary.validateTitle(Test_Data);
							logger.log(LogStatus.INFO, Description);
						}
						if(Object_Type.equalsIgnoreCase("closeBrowser"))
						{
							FunctionLibrary.closeBrowser();
							logger.log(LogStatus.INFO, Description);

						}
						if(Object_Type.equalsIgnoreCase("mouseClick"))
						{
							FunctionLibrary.mouseClick();
							logger.log(LogStatus.INFO,Description);
						}
						if(Object_Type.equalsIgnoreCase("category Table"))
						{
							FunctionLibrary.categoryTable(Test_Data);
							logger.log(LogStatus.INFO,Description);
						}
						if(Object_Type.equalsIgnoreCase("dropDownAction"))
						{
							FunctionLibrary.dropDownAction(Lname, Lvalue, Test_Data);
							logger.log(LogStatus.INFO,Description);
					     }
						if(Object_Type.equalsIgnoreCase("captureStock"))
						{
							FunctionLibrary.captureStock(Lname, Lvalue);
							logger.log(LogStatus.INFO,Description);
						}
						if(Object_Type.equalsIgnoreCase("captureSupplier"))
						{
							FunctionLibrary.captureSupplier(Lname, Lvalue);
							logger.log(LogStatus.INFO,Description);
						}
						if(Object_Type.equalsIgnoreCase("supplierTable"))
						{
							FunctionLibrary.supplierTable();
							logger.log(LogStatus.INFO,Description);
						}
						if(Object_Type.equalsIgnoreCase("captureCustomer"))
						{
							FunctionLibrary.captureCustomer(Lname, Lvalue);
							logger.log(LogStatus.INFO,Description);
						}
						if(Object_Type.equalsIgnoreCase("customerTable"))
						{
							FunctionLibrary.customerTable();
							logger.log(LogStatus.INFO,Description);
						}
					
						//write as TCModule
						xl.setCellData(TCModule, j, 5, "pass", outputpath);
						logger.log(LogStatus.PASS, Description);
						Module_Status="True";
					}catch(Exception e)
					{
						System.out.println(e.getMessage());
						xl.setCellData(TCModule, j, 5, "fail", outputpath);
						logger.log(LogStatus.FAIL, Description);
						Module_Status="True";
					}
					if(Module_Status.equalsIgnoreCase("True"))
					{
						xl.setCellData(TestCases, i, 3, "pass", outputpath);
					}
					else
					{
						xl.setCellData(TestCases, i, 3, "Fail", outputpath);
					}
					report.endTest(logger);
					report.flush();
				}
			}else
			{
				xl.setCellData(TestCases, i, 3, "Blocked",outputpath);
			}

		}

	}
}

