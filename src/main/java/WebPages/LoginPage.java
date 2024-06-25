package WebPages;

import WebAppBase.WebUtilMethods;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.time.Duration;

public class LoginPage extends WebUtilMethods {
    @FindBy(id = "inputEmailAddress")
    WebElement emailTextField;

    @FindBy(linkText = "NEXT")
    WebElement nextButton;

    @FindBy(id = "password")
    WebElement passwordTextField;

    @FindBy(linkText = "SIGN ON")
    WebElement signOnButton;

    @FindBy(xpath = "(//div[@class='toast-message'])[1]")
    WebElement toast;

    public LoginPage(WebDriver wd) {
        super(wd);
        PageFactory.initElements(new AppiumFieldDecorator(wd), this);
    }

    public MainPage login() {
        String email = getValuesFromTestDataFile().get("email");
        String password = getValuesFromTestDataFile().get("password");
        super.typeText(emailTextField, email);
        super.waitForElementToBeClickableAndClick(nextButton, Duration.ofSeconds(5));

        System.out.println("Email has been entered");

        super.typeText(passwordTextField, password);
        super.waitForElementToBeClickableAndClick(signOnButton, Duration.ofSeconds(5));

        System.out.println("Letting the page to fully load");

        try {
            Thread.sleep(20000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        System.out.println("Dismissing toast if it appears");
        try {
            toast.click();
        } catch (NoSuchElementException ignored) {
        }

        return WebUtilMethods.getPage().mainPage();
    }
}
