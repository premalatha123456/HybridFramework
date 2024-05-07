package commonFunctions;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.Properties;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;

public class FunctionLibrary {
	public static Properties conpro;
	public static WebDriver driver;
	public static WebDriver startBrowser() throws Throwable
	{
		conpro=new Properties();
		conpro.load(new FileInputStream("./PropertyFiles/Environment.properties"));
		if(conpro.getProperty("Browser").equalsIgnoreCase("chrome"))
		{
			driver=new ChromeDriver();
			driver.manage().window().maximize();

		}
		else if(conpro.getProperty("Browser").equalsIgnoreCase("firefox"))
		{
			driver=new FirefoxDriver();
		}
		else
		{
			Reporter.log("Browser key value is not matching",true);
		}
		return driver;
	}
	public static void openUrl()
	{
		driver.get(conpro.getProperty("Url"));
	}
	public static void waitForElement(String Locatorname,String LocatorValue,String TestData)
	{
		WebDriverWait mywait=new WebDriverWait(driver,Duration.ofSeconds(Integer.parseInt(TestData)));
		if(Locatorname.equalsIgnoreCase("xpath"))
		{
			mywait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(LocatorValue)));
		}
		if(Locatorname.equalsIgnoreCase("id"))
		{
			mywait.until(ExpectedConditions.visibilityOfElementLocated(By.id(LocatorValue)));
		}
		if(Locatorname.equalsIgnoreCase("name"))
		{
			mywait.until(ExpectedConditions.visibilityOfElementLocated(By.name(LocatorValue)));
		}
	}
	public static void typeAction(String LocatorName,String LocatorValue,String TestData)
	{
		if(LocatorName.equalsIgnoreCase("xpath"))
		{
			driver.findElement(By.xpath(LocatorValue)).clear();
			driver.findElement(By.xpath(LocatorValue)).sendKeys(TestData);
		}
		if(LocatorName.equalsIgnoreCase("id"))
		{
			driver.findElement(By.id(LocatorValue)).clear();
			driver.findElement(By.id(LocatorValue)).sendKeys(TestData);
		}
		if(LocatorName.equalsIgnoreCase("name"))
		{
			driver.findElement(By.name(LocatorValue)).clear();
			driver.findElement(By.name(LocatorValue)).sendKeys(TestData);
		}

	}
	public static void clickAction(String LocatorName,String LocatorValue)
	{
		if(LocatorName.equalsIgnoreCase("xpath"))
		{
			driver.findElement(By.xpath(LocatorValue)).click();
		}
		if(LocatorName.equalsIgnoreCase("name"))
		{
			driver.findElement(By.name(LocatorValue)).click();
		}
		if(LocatorName.equalsIgnoreCase("id"))
		{
			driver.findElement(By.id(LocatorValue)).sendKeys(Keys.ENTER);;
		}
	}
	public static void validateTitle(String Expected_Title)
	{
		String Actual_Title=driver.getTitle();
		try {
		Assert.assertEquals(Actual_Title,Expected_Title,"Title is not matching");
		}catch(AssertionError a)
		{
			System.out.println(a.getMessage());
		}
		
	}
	public static void closeBrowser()
	{
		driver.quit();
	}
	public static String generateDate()
	{
		Date date=new Date();
		DateFormat df=new SimpleDateFormat("yyyy_MM_DD hh_mm_ss");
		return df.format(date);
		
	}
	public static void mouseClick() throws Throwable
	{
		Actions ac=new Actions(driver);
		ac.moveToElement(driver.findElement(By.xpath("//a[text()='Stock Items ']"))).perform();
		Thread.sleep(3000);
		ac.moveToElement(driver.findElement(By.xpath("(//a[.='Stock Categories'])[2]"))).click().perform();
		Thread.sleep(3000);
	}
	public static void categoryTable(String Exp_Data) throws Throwable
	{
		if(!driver.findElement(By.xpath(conpro.getProperty("search-textbox"))).isDisplayed())	
		driver.findElement(By.xpath(conpro.getProperty("search-panel"))).click();
		driver.findElement(By.xpath(conpro.getProperty("search-textbox"))).clear();
		driver.findElement(By.xpath(conpro.getProperty("search-textbox"))).sendKeys(Exp_Data);
		driver.findElement(By.xpath(conpro.getProperty("search-button"))).click();
		Thread.sleep(3000);
		 
		String Act_Data=driver.findElement(By.xpath("//table[@class='table ewTable']/tbody/tr/[1]/td[4]/dive/span/span")).getText();
		Reporter.log(Exp_Data+"    "+Act_Data,true);
		try {
		Assert.assertEquals(Act_Data, Exp_Data," Category name is not matching");
		}catch(AssertionError a)
		{
			Reporter.log(a.getMessage(),true);
		}
	}
	public static void dropDownAction(String LocatorName,String LocatorValue,String TestData)
	{
		if(LocatorName.equalsIgnoreCase("xpath"))
		{
			int value=Integer.parseInt(TestData);
			Select element=new Select(driver.findElement(By.xpath(LocatorValue)));
			element.selectByIndex(value);
		}
		if(LocatorName.equalsIgnoreCase("name"))
		{

			int value=Integer.parseInt(TestData);
			Select element=new Select(driver.findElement(By.name(LocatorValue)));
			element.selectByIndex(value);
			
		}
		if(LocatorName.equalsIgnoreCase("id"))
		{

			int value=Integer.parseInt(TestData);
			Select element=new Select(driver.findElement(By.id(LocatorValue)));
			element.selectByIndex(value);
			
		}
	}
	public static void captureStock(String LocatorName,String LocatorValue) throws Throwable
	{
		String stockNumber ="";
		if(LocatorName.equalsIgnoreCase("xpath"))
		{
			stockNumber=driver.findElement(By.xpath(LocatorValue)).getAttribute("value");
		}
		if(LocatorName.equalsIgnoreCase("id"))
		{
			stockNumber=driver.findElement(By.id(LocatorValue)).getAttribute("value");
		}
		if(LocatorName.equalsIgnoreCase("name"))
		{
			stockNumber=driver.findElement(By.name(LocatorValue)).getAttribute("value");
		}
		FileWriter fw=new FileWriter("./CaptureData/stockNumber.txt");
		BufferedWriter bw= new BufferedWriter(fw);
		bw.write(stockNumber);
		bw.flush();
		bw.close();
	}
	public static void stockTable() throws Throwable
	{
		FileReader fr=new FileReader("./CaptureData/stockNumber.txt");
		BufferedReader br=new BufferedReader(fr);
		String Exp_Data=br.readLine();
		if(!driver.findElement(By.xpath(conpro.getProperty("search-textbox"))).isDisplayed())	
			driver.findElement(By.xpath(conpro.getProperty("search-panel"))).click();
			driver.findElement(By.xpath(conpro.getProperty("search-textbox"))).clear();
			driver.findElement(By.xpath(conpro.getProperty("search-textbox"))).sendKeys(Exp_Data);
			driver.findElement(By.xpath(conpro.getProperty("search-button"))).click();
			Thread.sleep(3000);
			String Act_Data=driver.findElement(By.xpath("//table[@class='table ewTable']/tbody/tr[1]/td[8]/div/span/span")).getText();
			Reporter.log(Act_Data+"   "+Exp_Data,true);
			try {
			
			Assert.assertEquals(Act_Data, Exp_Data,"Stock number is not matching");
			}catch(AssertionError a)
			{
				Reporter.log(a.getMessage(),true);	
			}
			
	}
	public static void captureSupplier(String LocatorName,String LocatorValue) throws Throwable
	{
		String suppliernumber="";
		if(LocatorName.equalsIgnoreCase("xpath"))
		{
			suppliernumber=driver.findElement(By.xpath(LocatorValue)).getAttribute("value");
		}
		if(LocatorName.equalsIgnoreCase("name"))
		{
			suppliernumber=driver.findElement(By.name(LocatorValue)).getAttribute("value");
		}
		if(LocatorName.equalsIgnoreCase("id"))
		{
			suppliernumber=driver.findElement(By.id(LocatorValue)).getAttribute("value");
		}
		FileWriter fw=new FileWriter("./CaptureData/suppliernumber.txt");
		BufferedWriter bw=new BufferedWriter(fw);
		bw.write(suppliernumber);
		bw.flush();
		bw.close();
		
	}
	public static void supplierTable() throws Throwable
	{
		FileReader fr=new FileReader("./CaptureData/suppliernumber.txt");
		BufferedReader br=new BufferedReader(fr);
		String Exp_Data=br.readLine();
		if(!driver.findElement(By.xpath(conpro.getProperty("search-textbox"))).isDisplayed())	
			driver.findElement(By.xpath(conpro.getProperty("search-panel"))).click();
			driver.findElement(By.xpath(conpro.getProperty("search-textbox"))).clear();
			driver.findElement(By.xpath(conpro.getProperty("search-textbox"))).sendKeys(Exp_Data);
			driver.findElement(By.xpath(conpro.getProperty("search-button"))).click();
			Thread.sleep(3000);
			String Act_data=driver.findElement(By.xpath("//table[@class='ewTableRow']/tbody/tr[1]/td[6]/div/span/span")).getText();
			Reporter.log(Act_data+"   "+Exp_Data,true);
			try {
			Assert.assertEquals(Exp_Data, Act_data,"Suppiler number not matching");
			}catch(AssertionError a)
			{
				Reporter.log(a.getMessage());
			}
	}
	public static void captureCustomer(String LocatorName,String LocatorValue) throws Throwable
	{
		String customernumber="";
		if(LocatorName.equalsIgnoreCase("xpath"))
		{
			customernumber=driver.findElement(By.xpath(LocatorValue)).getAttribute("value");
		}
		if(LocatorName.equalsIgnoreCase("name"))
		{
			customernumber=driver.findElement(By.name(LocatorValue)).getAttribute("value");
		}
		if(LocatorName.equalsIgnoreCase("id"))
		{
			customernumber=driver.findElement(By.id(LocatorValue)).getAttribute("value");
		}
		FileWriter fw=new FileWriter("./CaptureData/customernumber.txt");
		BufferedWriter bw=new BufferedWriter(fw);
		bw.write(customernumber);
		bw.flush();
		bw.close();
		
	}
	public static void customerTable() throws Throwable
	{
		FileReader fr=new FileReader("./CaptureData/customernumber.txt");
		BufferedReader br=new BufferedReader(fr);
		String Exp_Data=br.readLine();
		if(!driver.findElement(By.xpath(conpro.getProperty("search-textbox"))).isDisplayed())	
			driver.findElement(By.xpath(conpro.getProperty("search-panel"))).click();
			driver.findElement(By.xpath(conpro.getProperty("search-textbox"))).clear();
			driver.findElement(By.xpath(conpro.getProperty("search-textbox"))).sendKeys(Exp_Data);
			driver.findElement(By.xpath(conpro.getProperty("search-button"))).click();
			Thread.sleep(3000);
			String Act_data=driver.findElement(By.xpath("//table[@class='ewTableRow']/tbody/tr[1]/td[5]/div/span/span")).getText();
			Reporter.log(Act_data+"   "+Exp_Data,true);
			try {
			Assert.assertEquals(Exp_Data, Act_data,"Suppiler number not matching");
			}catch(AssertionError a)
			{
				Reporter.log(a.getMessage());
			}
	}
	
	
	
}
