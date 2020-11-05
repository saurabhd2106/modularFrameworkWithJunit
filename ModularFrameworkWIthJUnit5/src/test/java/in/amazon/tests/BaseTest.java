package in.amazon.tests;

import java.util.Properties;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestWatcher;
import org.openqa.selenium.WebDriver;

import com.aventstack.extentreports.Status;

import commonLibs.implementations.CommonDriver;
import commonLibs.implementations.ScreenshotControl;
import commonLibs.utils.ConfigPropertyUtils;
import commonLibs.utils.DateUtils;
import commonLibs.utils.ExtentReportUtils;
import in.amazon.pages.Homepage;

public class BaseTest implements TestWatcher {

	static CommonDriver cmnDriver;

	static String url;

	static Homepage homepage;

	static WebDriver driver;

	static ExtentReportUtils extentReportUtils;

	static String htmlReportFilename;
	static String currentWorkingDirectory;
	static String executeStartTime;

	static String configFilename;
	static Properties configPropeties;

	static ScreenshotControl screenshotControl;

	static {
		executeStartTime = DateUtils.getCurrentDate();

		currentWorkingDirectory = System.getProperty("user.dir");

		htmlReportFilename = String.format("%s/reports/amazon-report-%s.html", currentWorkingDirectory,
				executeStartTime);

	}

	@BeforeAll
	public static void preSetup() throws Exception {

		configFilename = String.format("%s/config/config.properties", currentWorkingDirectory);
		configPropeties = ConfigPropertyUtils.configFileReader(configFilename);

		initializeReports();

	}

	@BeforeEach
	public void setup() throws Exception {

		extentReportUtils.createATestcase("Setup - All setup for test execution");

		invokeBrowser();

		classInitializations();

		screenshotControl = new ScreenshotControl(driver);

	}

	@AfterAll
	public static void postCleanup() throws Exception {

		closeReports();

	}

	private static void closeReports() {
		extentReportUtils.flushReport();

	}

	private static void initializeReports() {
		extentReportUtils = new ExtentReportUtils(htmlReportFilename);

	}

	private void invokeBrowser() throws Exception {

		String browserType = configPropeties.getProperty("browserType");

		String url = configPropeties.getProperty("baseUrl");

		String env = configPropeties.getProperty("env");

		cmnDriver = new CommonDriver(browserType, env);

		extentReportUtils.addLogStatus(Status.INFO, "Browser invoked is - " + browserType);

		int pageloatimeout = Integer.parseInt(configPropeties.getProperty("pageloadTimeout"));
		cmnDriver.setPageloadTimeout(pageloatimeout);

		extentReportUtils.addLogStatus(Status.INFO, "Pageload timeout - " + pageloatimeout);

		int elementDetectionTimeout = Integer.parseInt(configPropeties.getProperty("elementDetectionTimeout"));
		cmnDriver.setElementDetectionTimeout(elementDetectionTimeout);

		extentReportUtils.addLogStatus(Status.INFO, "Implict timeout - " + elementDetectionTimeout);

		cmnDriver.navigateToUrl(url);

		driver = cmnDriver.getDriver();

	}

	private void classInitializations() {
		homepage = new Homepage(driver);

	}

	private void closeBrowser() throws Exception {
		cmnDriver.closeAllBrowsers();

	}

	@Override
	public void testSuccessful(ExtensionContext context) {

		try {
			closeBrowser();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public void testFailed(ExtensionContext context, Throwable cause) {

		try {
			
			extentReportUtils.addLogStatus(Status.ERROR, cause.getMessage());
			
			String testcaseName = context.getDisplayName();

			String screenshotCaptureTime = DateUtils.getCurrentDate();

			String screenshotFilename = String.format("%s/screenshot/%s-%s.jpeg", currentWorkingDirectory, testcaseName,
					screenshotCaptureTime);

			screenshotControl.captureAndSaveScreenshot(screenshotFilename);

			extentReportUtils.addLogStatus(Status.FAIL, "One or more steps failed");

			extentReportUtils.addScreenshotToTheLogs(screenshotFilename);

		} catch (Exception e) {

			extentReportUtils.addLogStatus(Status.ERROR, e.getMessage());
			e.printStackTrace();
		} finally {

			try {
				closeBrowser();

			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

}
