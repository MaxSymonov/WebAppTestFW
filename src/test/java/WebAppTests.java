import WebAppBase.WebUtilMethods;
import WebAppBase.WebAppBase;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class WebAppTests {

    @Test()
    public void test1_StartWebSession() {

        WebAppBase.setUpBrowserSession();

        System.out.println("Login");

        WebUtilMethods.getPage().loginPage().login();

        System.out.println("Validating page title");

        WebUtilMethods.getPage().mainPage().validatePageTitleIs("Home Page");

        System.out.println("Closing webdriver and quitting browser");

        WebAppBase.tearDownBrowserSession();
    }
}
