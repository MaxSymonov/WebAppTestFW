package WebAppBase;

import WebPages.WebPageFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.yaml.snakeyaml.Yaml;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class WebUtilMethods {
    public WebDriver wd;

    public WebUtilMethods(WebDriver wd) {
        this.wd = wd;
    }

    public void validatePageTitleIs(String pageTitle) {
        WebDriverWait wait = new WebDriverWait(wd, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.titleIs(pageTitle));
    }

    public void validatePageTitleContains(String pageTitle) {
        WebDriverWait wait = new WebDriverWait(wd, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.titleContains(pageTitle));
    }

    public void waitForElementToAppear(WebElement element, Duration duration) {
        WebDriverWait wait = new WebDriverWait(wd, duration);
        wait.until(ExpectedConditions.visibilityOfElementLocated((By) element));
    }

    public void waitForElementToBeClickable(WebElement element, Duration duration) {
        WebDriverWait wait = new WebDriverWait(wd, duration);
        wait.until(ExpectedConditions.elementToBeClickable(element));
    }

    public void verifyIfElementSelected(WebElement element, Duration duration, boolean isSelected) {
        WebDriverWait wait = new WebDriverWait(wd, duration);
        wait.until(ExpectedConditions.elementSelectionStateToBe(element, isSelected));
    }

    public void waitForElementToBeClickableAndClick(WebElement element, Duration duration) {
        WebDriverWait wait = new WebDriverWait(wd, duration);
        wait.until(ExpectedConditions.elementToBeClickable(element)).click();
    }

    public void typeText(WebElement element, String text) {
        waitForElementToBeClickable(element, Duration.ofSeconds(10));
        element.click();
        element.clear();
        element.sendKeys(text);
    }

    public void selectDropdown(By locator, int index){
        Select listbox = new Select(wd.findElement(locator));
        listbox.selectByIndex(index);
    }

    public void selectDropdownKeysDown(By locator){
        WebElement selectKeysDown = wd.findElement(locator);
        selectKeysDown.click();
        Actions keyDown = new Actions(wd);
        keyDown.sendKeys(Keys.chord(Keys.DOWN, Keys.DOWN)).perform();
    }

    public void switchToNextTab() {
        Set<String> windowHandles = wd.getWindowHandles();
        String parentWindowHandle = wd.getWindowHandle();
        windowHandles.remove(parentWindowHandle);
        wd.switchTo().window(windowHandles.iterator().next());
    }

    public void switchToPreviousTab() {
        Set<String> windowHandles = wd.getWindowHandles();
        String parentWindowHandle = wd.getWindowHandle();
        windowHandles.remove(parentWindowHandle);
        String previousWindowHandle = windowHandles.iterator().next();
        while (windowHandles.iterator().hasNext()) {
            previousWindowHandle = windowHandles.iterator().next();
        }
        wd.switchTo().window(previousWindowHandle);
    }

    public static Map<String, String> getValuesFromTestDataFile(String... keys) {
        Map<String, String> data;
        try (InputStream in = Files.newInputStream(Paths.get("src\\main\\resources\\TestData.yaml"))) {
            data = new Yaml().load(in);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Set<String> keySet = (keys.length == 0) ? data.keySet() : Set.of(keys);
        Map<String, String> result = new HashMap<>(keySet.size());
        if (keySet.size() == data.size()) {
            result.putAll(data);
        } else {
            for (String key : keySet) {
                result.put(key, data.get(key));
            }
        }
        return result;
    }

    public static WebPageFactory getPage() { return new WebPageFactory(); }
}


