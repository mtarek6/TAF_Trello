package com.trello.drivers;

import com.trello.utils.actions.AlertActions;
import com.trello.utils.actions.BrowserActions;
import com.trello.utils.actions.ElementActions;
import com.trello.utils.actions.FrameActions;
import com.trello.utils.dataReader.PropertyReader;
import com.trello.utils.logs.LogsManager;
import com.trello.validations.Validation;
import com.trello.validations.Verification;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ThreadGuard;

public class GUIDriver {
    private final String browser = PropertyReader.getProperty("browserType");

    private ThreadLocal<WebDriver> driverThreadLocal = new ThreadLocal<>();

    public GUIDriver() {
        Browser browserType = Browser.valueOf(browser.toUpperCase());
        LogsManager.info("Starting driver for browser: " + browserType);
        AbstractDriver abstractDriver = browserType.getDriverFactory();
        WebDriver driver = ThreadGuard.protect(abstractDriver.createDriver());
        driverThreadLocal.set(driver);
    }

    public ElementActions element() {
    return new ElementActions(get());
    }

    public BrowserActions browser() {
        return new BrowserActions(get());
    }

    public FrameActions frame() {
        return new FrameActions(get());
    }

    public AlertActions alert() {
        return new AlertActions(get());
    }
    // Soft Assertion
    public Validation validate() {
        return new Validation(get());
    }

    //hard assertion
    public Verification verify() {
        return new Verification(get());
    }



    public WebDriver get() {
        return driverThreadLocal.get();
    }

    public void quitDriver() {
        if (driverThreadLocal.get() != null) {
            driverThreadLocal.get().quit();
           // driverThreadLocal.remove();
        }
    }
}
