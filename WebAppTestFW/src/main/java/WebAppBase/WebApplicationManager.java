package WebAppBase;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.Browser;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.time.Duration;

import static org.openqa.selenium.remote.Browser.*;
import static org.openqa.selenium.remote.CapabilityType.ACCEPT_INSECURE_CERTS;

/**
 * Manages the Web Application and provides access to the WebDriver.
 */
public class WebApplicationManager {
    private static Browser browser;
    private static boolean runBrowserInHeadlessMode;

    public static final String HEADLESS_NEW = "--headless=new";
    public static final String GUEST_MODE = "--guest";

    public static final String ALLOW_ALL_ORIGINS = "--remote-allow-origins=*";

    public static final String IGNORE_CERTIFICATE_ERRORS = "--ignore-certificate-errors";

    private static final ThreadLocal<WebDriver> driver = new ThreadLocal<>();

    /**
     * Constructs a new WebApplicationManager object.
     *
     * @param browser                 The browser for which the WebDriver is required.
     * @param runBrowserInHeadlessMode Boolean flag to indicate whether to run the browser in headless mode.
     */
    public WebApplicationManager(Browser browser, boolean runBrowserInHeadlessMode) {
        WebApplicationManager.browser = browser;
        WebApplicationManager.runBrowserInHeadlessMode = runBrowserInHeadlessMode;
    }

    /**
     * Gets the WebDriver.
     *
     * @return The WebDriver instance.
     */
    public static WebDriver getWebDriver() {
        if (driver.get() == null || ((RemoteWebDriver) driver.get()).getSessionId() == null) {
            driver.set(createWebDriver());
            init();
        }
        return driver.get();
    }

    /**
     * Configures the driver settings.
     */
    static void init() {
        getWebDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
        getWebDriver().manage().window();
        getWebDriver().navigate().to(WebUtilMethods.getValuesFromTestDataFile().get("baseURL"));
    }

    /**
     * Stops the execution and quits the WebDriver instance.
     */
    static void stop() {
        if (driver.get() != null) {
            try {
                driver.get().quit();
            } catch (Throwable ignored) {
            }
            driver.remove();
        }
    }

    /**
     * Creates a new WebDriver instance.
     *
     * @return The new WebDriver instance.
     */
    private static WebDriver createWebDriver() {
        if (browser.equals(EDGE)) {
            EdgeOptions edgeOptions = new EdgeOptions();
            edgeOptions.addArguments(GUEST_MODE);
            edgeOptions.addArguments(ALLOW_ALL_ORIGINS);
            edgeOptions.addArguments(IGNORE_CERTIFICATE_ERRORS);
            if (runBrowserInHeadlessMode) {
                edgeOptions.addArguments(HEADLESS_NEW);
            }
            WebDriverManager.edgedriver().clearDriverCache().setup();
            return new EdgeDriver(edgeOptions);
        } else if (browser.equals(FIREFOX)) {
            FirefoxOptions firefoxOptions = new FirefoxOptions();
            firefoxOptions.addArguments(GUEST_MODE);
            firefoxOptions.setCapability(ACCEPT_INSECURE_CERTS, true);
            if (runBrowserInHeadlessMode) {
                firefoxOptions.addArguments("--headless");
            }
            WebDriverManager.firefoxdriver().clearDriverCache().setup();
            return new FirefoxDriver(firefoxOptions);
        } else if (browser.equals(CHROME)) {
            ChromeOptions chromeOptions = new ChromeOptions();
            chromeOptions.addArguments(GUEST_MODE);
            chromeOptions.addArguments(ALLOW_ALL_ORIGINS);
            chromeOptions.addArguments(IGNORE_CERTIFICATE_ERRORS);
            if (runBrowserInHeadlessMode) {
                chromeOptions.addArguments(HEADLESS_NEW);
            }
            WebDriverManager.chromedriver().clearDriverCache().setup();
            return new ChromeDriver(chromeOptions);
        }
        throw new IllegalArgumentException("This browser is currently unsupported: " + browser.browserName());
    }
}