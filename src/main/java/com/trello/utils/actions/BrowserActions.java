package com.trello.utils.actions;

import com.trello.utils.WaitManager;
import com.trello.utils.logs.LogsManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WindowType;

public class BrowserActions {
    private final WebDriver driver;
    private WaitManager waitManager;
    public BrowserActions (WebDriver driver) {
        this.driver = driver;
        this.waitManager = new WaitManager(driver);
    }

    //maximize the browser window
    public void maximizeWindow() {
        driver.manage().window().maximize();
    }

    //navigate to a specific URL
    public void navigateTo(String url) {
        driver.get(url);
        LogsManager.info("Navigated to URL: " + url);
    }

    //refresh the current page
    public void refreshPage() {
        driver.navigate().refresh();
    }

    //get current page url
    public String getCurrentUrl() {
        String url = driver.getCurrentUrl();
        LogsManager.info("Current URL: " + url);
        return url;
    }

    //close current window
    public void closeWindow() {
        driver.close();
    }

    //open a new window
    public void openNewWindow() {
        driver.switchTo().newWindow(WindowType.WINDOW);
    }

    public void closeExtensionTab()
    {
        String originalWindow = driver.getWindowHandle();
        waitManager.fluentWait().until(
                d -> d.getWindowHandles().size() > 1
        );
        for (String windowHandle : driver.getWindowHandles()) //extension tab is opened
        {
            if (!windowHandle.equals(originalWindow))
                driver.switchTo().window(windowHandle).close(); //close the extension tab
        }
        driver.switchTo().window(originalWindow);
        LogsManager.info("Closed extension tab and switched back to original window");
    }
}
