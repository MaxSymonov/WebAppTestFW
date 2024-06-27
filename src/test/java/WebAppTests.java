import WebAppBase.WebAppBase;
import WebAppBase.WebUtilMethods;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

public class WebAppTests {

    @Test()
    public void test1_StartWebSession() {

        WebAppBase.setUpBrowserSession();

        System.out.println("Login");

        WebUtilMethods.getPage().loginPage().login();

        System.out.println("Switch to next tab");

        WebUtilMethods.getPage().mainPage().switchToNextTab();
    }

    @AfterMethod
    public void tearDown() {
        WebAppBase.tearDownBrowserSession();
    }
}
