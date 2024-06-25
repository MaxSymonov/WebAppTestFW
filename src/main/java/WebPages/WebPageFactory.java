package WebPages;

import WebAppBase.WebApplicationManager;
import WebAppBase.WebAppBase;

public class WebPageFactory extends WebAppBase {
    public LoginPage loginPage() {return new LoginPage(WebApplicationManager.getWebDriver());}
    public MainPage mainPage() {return new MainPage(WebApplicationManager.getWebDriver());}
}
