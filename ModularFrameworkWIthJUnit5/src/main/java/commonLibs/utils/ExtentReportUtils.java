package commonLibs.utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;

public class ExtentReportUtils {
	
	private ExtentHtmlReporter htmlReporter;
	
	private ExtentReports extentReports;
	
	private ExtentTest extentTests;
	
	public ExtentReportUtils(String htmlReportFilename) {
		
		htmlReporter = new ExtentHtmlReporter(htmlReportFilename);
		
		extentReports = new ExtentReports();
		
		extentReports.attachReporter(htmlReporter);
		
	}
	
	public void createATestcase(String testName) {
		
		extentTests = extentReports.createTest(testName);		
	}
	
	public void addLogStatus(Status status, String comment ) {
		
		extentTests.log(status, comment);
		
	}
	
	public void addScreenshotToTheLogs(String screenshotPath) throws Exception {
		
		extentTests.addScreenCaptureFromPath(screenshotPath);
		
	}
	
	public void flushReport() {
		extentReports.flush();
	}

}
