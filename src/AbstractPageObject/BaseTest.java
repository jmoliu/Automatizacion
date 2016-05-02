package AbstractPageObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.LocalFileDetector;
import org.openqa.selenium.remote.RemoteWebDriver;

public class BaseTest {
	protected static final String WEB_SERVER = System.getProperty("WEB_SERVER",
			"http://demo.opencart.com");
	protected static final String BROWSER = System.getProperty("BROWSER",
			"firefox");
	protected static final boolean REMOTE_DRIVER = Boolean.valueOf(System
			.getProperty("REMOTE_DRIVER", "false"));
	protected static final String SELENIUM_HOST = System.getProperty(
			"SELENIUM_HOST", "localhost");
	protected static final int SELENIUM_PORT = Integer.valueOf(System
			.getProperty("SELENIUM_PORT", "4444"));
	public static RemoteWebDriver driver;

	@BeforeClass
	public static void setupWebDriver() throws MalformedURLException {
		if (REMOTE_DRIVER) {
			setupRemoteDriver();
			driver.setFileDetector(new LocalFileDetector());
		} else {
			setupLocalDriver();
		}
		driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
	}

	private static void setupLocalDriver() {
		if (BROWSER.equals("firefox")) {
			driver = new FirefoxDriver();
		} else if (BROWSER.equals("chrome")) {
			String path = "lib/chromedriver";
			if (System.getProperty("os.name").contains("Windows")) {
				path = "lib/chromedriver.exe";
			}
			System.setProperty("webdriver.chrome.driver", path);
			driver = new ChromeDriver();
		} else if (BROWSER.equals("internetExplorer")) {
			DesiredCapabilities capabilities = DesiredCapabilities
					.internetExplorer();
			capabilities
					.setCapability(
							InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS,
							true);
			driver = new InternetExplorerDriver(capabilities);
		} else {
			throw new RuntimeException("Browser type unsupported");
		}
	}

	private static void setupRemoteDriver() throws MalformedURLException {
		DesiredCapabilities capabilities;
		if (BROWSER.equals("firefox")) {
			capabilities = DesiredCapabilities.firefox();
		} else if (BROWSER.equals("internetExplorer")) {
			capabilities = DesiredCapabilities.internetExplorer();
			capabilities
					.setCapability(
							InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS,
							true);
		} else if (BROWSER.equals("chrome")) {
			capabilities = DesiredCapabilities.chrome();
		} else {
			throw new RuntimeException("Browser type unsupported");
		}
		driver = new RemoteWebDriver(new URL("http://" + SELENIUM_HOST + ":"
				+ SELENIUM_PORT + "/wd/hub"), capabilities);
	}

	@Before
	public void abrirAplicacion() {
		driver.get(WEB_SERVER);
	}

	@After
	public void deleteAllCookies() {
		driver.manage().deleteAllCookies();
	}

	@AfterClass
	public static void suiteTearDown() {
		driver.quit();
	}
}
