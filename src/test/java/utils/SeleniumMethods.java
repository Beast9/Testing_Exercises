package utils;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Parameters;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.ChartLocation;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class SeleniumMethods {

	public static WebDriver driver;
	public static WebDriverWait wait;
	static ExtentHtmlReporter htmlReport;
	protected static ExtentReports extent;
	protected static ExtentTest test;
	
	// Screenshot folder created
	static Calendar cal = Calendar.getInstance();
	static File directory = new File("./src/ExtentReport/Screenshots");
	static String fileDirectory = directory.toString();
	
	public static DateFormat day = new SimpleDateFormat("yyyy-MM-dd");

	@BeforeSuite
	public void beforeSuite() {
		Calendar cal = Calendar.getInstance();
		htmlReport = new ExtentHtmlReporter("./src/ExtentReport/TestReport.html");
		extent = new ExtentReports();
		extent.attachReporter(htmlReport);

		extent.setSystemInfo("Execution Date", day.format(cal.getTime()));
		extent.setSystemInfo("Platform", "Jenkins");
		extent.setSystemInfo("Plug-In", "Maven");
		extent.setSystemInfo("Enviroment", "Prod");

		htmlReport.config().setDocumentTitle("Excersice");
		htmlReport.config().setReportName("Excersice");
		htmlReport.config().setTestViewChartLocation(ChartLocation.TOP);
		htmlReport.config().setTheme(Theme.DARK);
	}
	
	@BeforeClass
	@Parameters({ "BrowserType" })
	public void beforeClass(String browserType) throws IOException {
		
		if( browserType.equalsIgnoreCase("Chrome") ) {
			System.setProperty("webdriver.chrome.driver", "./src/test/resources/chromedriver.exe");
			driver = new ChromeDriver();
		} else if ( browserType.equalsIgnoreCase("Firefox") ) {
			System.setProperty("webdriver.gecko.driver", "./src/test/resources/geckodriver.exe");
			FirefoxProfile profile = new FirefoxProfile();
			profile.setPreference("browser.helperApps.neverAsk.saveToDisk", "application/csv, text/csv, text/plain,application/octet-stream doc xls pdf txt");
		    profile.setPreference("browser.download.manager.focusWhenStarting", false);
			FirefoxOptions options = new FirefoxOptions();
			options.setProfile(profile);
			
			driver = new FirefoxDriver(options);
		} else if ( browserType.equalsIgnoreCase("Internet Explorer") ) {
			System.setProperty("webdriver.ie.driver", "./src/test/resources/IEDriverServer.exe");
			driver = new InternetExplorerDriver();
		}
		
		driver.manage().window().maximize();
		
		wait = new WebDriverWait(driver, 120);
	}
	
	public static void captureScreenshot(WebDriver driver, String name) throws IOException {

		if (!directory.exists()) {
			directory.mkdir();
		}
		File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		String path = directory + "/" + name + ".png";
		FileUtils.copyFile(scrFile, new File(path));
		test.pass("Screenshot below: ",
				MediaEntityBuilder.createScreenCaptureFromPath("Screenshots/" + name + ".png").build());

	}

	@AfterMethod
	public void getResult(ITestResult result) {
		if (result.getStatus() == ITestResult.FAILURE) {
			System.out.println("FAILURE");
			test.log(Status.FAIL, MarkupHelper.createLabel(result.getName() + " status FAILED due to below issues:",
					ExtentColor.RED));
			test.fail(result.getThrowable());
		} else if (result.getStatus() == ITestResult.SUCCESS) {
			System.out.println("SUCCESS");
			test.log(Status.PASS, MarkupHelper.createLabel(result.getName() + " status PASSED", ExtentColor.GREEN));
		} else {
			System.out.println("SKIP");
			test.log(Status.SKIP, MarkupHelper.createLabel(result.getName() + " status SKIPPED", ExtentColor.ORANGE));
			test.skip(result.getThrowable());
		}
	}

	@AfterTest
	public void tearDown() {
		driver.quit();
	}
	
	@AfterSuite
	public void afterSuite() {
		extent.flush();
	}
}
