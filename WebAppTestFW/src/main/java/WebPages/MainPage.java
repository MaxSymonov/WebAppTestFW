package WebPages;

import WebAppBase.WebUtilMethods;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

public class MainPage extends WebUtilMethods {
    public MainPage(WebDriver wd) {
        super(wd);
        PageFactory.initElements(new AppiumFieldDecorator(wd), this);
        validatePageTitleIs("Home Page");
    }
}
