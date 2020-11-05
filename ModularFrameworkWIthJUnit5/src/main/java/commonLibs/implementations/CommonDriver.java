package commonLibs.implementations;

import java.net.URL;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

import commonLibs.contracts.Driver;

public class CommonDriver implements Driver {

	private WebDriver driver;

	private int pageloadTimeout;

	private int elementDetectionTimeout;

	private String hubUrl;

	public WebDriver getDriver() {
		return driver;
	}

	public void setPageloadTimeout(int pageloadTimeout) {
		this.pageloadTimeout = pageloadTimeout;
	}

	private String currentWorkingDirectory;

	public void setElementDetectionTimeout(int elementDetectionTimeout) {
		this.elementDetectionTimeout = elementDetectionTimeout;
	}

	public CommonDriver(String browserType, String env) throws Exception {

		initialSetup(browserType, env);

	}

	public CommonDriver(String browserType) throws Exception {

		initialSetup(browserType, "linux");

	}

	private void initialSetup(String browserType, String env) throws Exception {
		pageloadTimeout = 60;
		elementDetectionTimeout = 10;

		currentWorkingDirectory = System.getProperty("user.dir");

		if (browserType.equals("chrome")) {
			if (env.equals("linux")) {

				System.setProperty("webdriver.chrome.driver", currentWorkingDirectory + "/drivers/chromedriver");

			} else if (env.equals("windows")) {

				System.setProperty("webdriver.chrome.driver", currentWorkingDirectory + "/drivers/chromedriver.exe");

			}
			driver = new ChromeDriver();
		} else if (browserType.equals("chrome-headless")) {

			System.setProperty("webdriver.chrome.driver", currentWorkingDirectory + "/drivers/chromedriver.exe");
			ChromeOptions chromeOptions = new ChromeOptions();
			chromeOptions.addArguments("--headless");
			driver = new ChromeDriver(chromeOptions);
		} else if (browserType.equals("firefox")) {

			System.setProperty("webdriver.gecko.driver", currentWorkingDirectory + "/drivers/geckodriver.exe");

			driver = new ChromeDriver();
		} else if (browserType.equals("chrome-remote")) {

			ChromeOptions chromeOptions = new ChromeOptions();

			hubUrl = "http://172.17.0.1:4444/wd/hub";

			URL remoteAddress = new URL(hubUrl);

			driver = new RemoteWebDriver(remoteAddress, chromeOptions);
		}

		else if (browserType.equals("firefox-remote")) {

			FirefoxOptions firefoxOptions = new FirefoxOptions();

			hubUrl = "http://172.17.0.1:4444/wd/hub";

			URL remoteAddress = new URL(hubUrl);

			driver = new RemoteWebDriver(remoteAddress, firefoxOptions);
		} else {
			throw new Exception("Invalid Browser Type - " + browserType);
		}

		driver.manage().deleteAllCookies();

		driver.manage().window().maximize();
	}

	@Override
	public void navigateToUrl(String url) throws Exception {

		url = url.trim();

		driver.manage().timeouts().pageLoadTimeout(pageloadTimeout, TimeUnit.SECONDS);

		driver.manage().timeouts().implicitlyWait(elementDetectionTimeout, TimeUnit.SECONDS);

		driver.get(url);

	}

	@Override
	public void refresh() throws Exception {
		driver.navigate().refresh();

	}

	@Override
	public void navigateBack() throws Exception {
		driver.navigate().back();

	}

	@Override
	public void navigateForward() throws Exception {
		driver.navigate().forward();

	}

	@Override
	public String getTitle() throws Exception {

		return driver.getTitle();
	}

	@Override
	public String getCurrentUrl() throws Exception {

		return driver.getCurrentUrl();
	}

	@Override
	public void closeBrowser() throws Exception {
		if (driver != null) {
			driver.close();
		}

	}

	@Override
	public void closeAllBrowsers() throws Exception {
		if (driver != null) {
			driver.quit();
		}

	}

}
