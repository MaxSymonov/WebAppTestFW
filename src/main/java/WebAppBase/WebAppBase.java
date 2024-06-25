package WebAppBase;

import org.openqa.selenium.SessionNotCreatedException;
import org.openqa.selenium.remote.Browser;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;

public class WebAppBase  {
    /**
     * This is the main class for managing the web application session. Define the web browser you want to use for the testing session here.
     */
    public static WebApplicationManager app = new WebApplicationManager(WebBrowser.CHROME.getBrowser(), false);

    enum WebBrowser {
        CHROME(Browser.CHROME),
        FIREFOX(Browser.FIREFOX),
        EDGE(Browser.EDGE);

        final Browser browser;

        WebBrowser(Browser browser) {
            this.browser = browser;
        }

        public Browser getBrowser() {
            return browser;
        }
    }

    @BeforeTest
    public static void setUpBrowserSession() {
        System.out.println("Starting web browser session");
        try {
            app.init();
        } catch (SessionNotCreatedException e) {
            if (e.getMessage().contains("Expected browser binary location, but unable to find binary in default location")) {
                Assert.fail("Failed to start browser session: " + e.getMessage());
            } else {
                throw new RuntimeException("Failed to start browser session: " + e.getMessage());
            }
        }
    }

    @AfterTest(enabled = true)
    public static void tearDownBrowserSession() {
        System.out.println("Closing webdriver and quitting browser");
        app.stop();
    }

}
