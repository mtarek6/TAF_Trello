package com.trello.validations;

import com.trello.FileUtils;
import com.trello.utils.WaitManager;
import com.trello.utils.actions.ElementActions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public abstract class BaseAssertion {
    protected  WebDriver driver;
    protected  WaitManager waitManager;
    protected ElementActions elementActions;

    //default constructor
    protected  BaseAssertion() {
    }

    public BaseAssertion(WebDriver driver) {
        this.driver = driver;
        waitManager = new WaitManager(driver);
        elementActions = new ElementActions(driver);
    }

    protected abstract void assertTrue(boolean condition, String message);
    protected abstract void assertFalse(boolean condition, String message);
    protected abstract void assertEquals(String actual, String expected, String message);

    public BaseAssertion Equals(String actual, String expected, String message) {
        assertEquals(actual, expected, message);
        return this;
    }

    public void isElementVisible(By locator)
    {
        boolean flag = waitManager.fluentWait().until(d -> {
            try {
                d.findElement(locator).isDisplayed();
                return true;
            } catch (Exception e) {
                return false;
            }
        });
        assertTrue(flag, "Element is not visible: " + locator.toString());
    }

    // verify page url
    public void assertPageUrl(String expectedUrl)
    {
       boolean matched = waitManager.fluentWait().until(d -> {
           String currentUrl = d.getCurrentUrl();
           return currentUrl != null && currentUrl.equals(expectedUrl);
       });
         assertTrue(matched,
                "Page URL does not match. Expected: "
                          + expectedUrl + ", but got: " + driver.getCurrentUrl());
    }

    //verify page url contains
    public void assertPageUrlContains(String expectedUrlFragment)
    {
        boolean matched = waitManager.fluentWait().until(d -> {
            String currentUrl = d.getCurrentUrl();
            return currentUrl != null && currentUrl.contains(expectedUrlFragment);
        });

        assertTrue(matched,
                "Page URL does not contain expected fragment. Expected: "
                        + expectedUrlFragment + ", but got: " + driver.getCurrentUrl());
    }

    // verify page title
    public void assertPageTitle(String expectedTitle) {
        String actualTitle = driver.getTitle();
        assertEquals(actualTitle, expectedTitle, "Page title does not match. Expected: " + expectedTitle + ", but got: " + actualTitle);
    }

    //verify that a file exists
    public void assertFileExists(String fileName, String message) {
        boolean fileExists = FileUtils.isFileExist(fileName, 3);
        assertTrue(fileExists, message);
    }

    //verify that an element equals text
    public void assertElementTextEquals(By locator, String expectedText)
    {
        boolean flag = waitManager.fluentWait().until(d -> {
            try {
                System.out.println("DEBUG error text = '"
                        + driver.findElement(locator).getText().trim() + "'");
               return driver.findElement(locator).getText().trim().equals(expectedText);
            } catch (Exception e) {
                return false;
            }
        });
        assertTrue(flag, "Element text does not match expected text. Expected: " + expectedText);
    }

    //verify that an element contains text
    public void assertElementTextContains(By locator, String expectedText)  {
        boolean flag = waitManager.fluentWait().until(d -> {
            try {
                System.out.println("DEBUG error text = '"
                        + driver.findElement(locator).getText().trim() + "'");
                return driver.findElement(locator).getText().trim().contains(expectedText);
            } catch (Exception e) {
                return false;
            }
        });
        assertTrue(flag, "Element text does not contain the expected text. Expected: " + expectedText);
    }

    //verify that an element is disabled
    public void isElementDisabled(By locator) {
        boolean isDisabled = !driver.findElement(locator).isEnabled();
        assertTrue(isDisabled, "Button should be disabled");
    }
}
