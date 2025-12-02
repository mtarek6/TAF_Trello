package com.trello.utils.actions;

import com.trello.utils.WaitManager;
import com.trello.utils.logs.LogsManager;
import org.openqa.selenium.By;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;

import java.io.File;

public class ElementActions {
    private final WebDriver driver;
    private WaitManager waitManager;


    public ElementActions(WebDriver driver) {
        this.driver = driver;
        this.waitManager = new WaitManager(driver);
    }

    public ElementActions click(By locator) {
        waitManager.fluentWait().until(d ->
        {
            try {
                WebElement element = d.findElement(locator);
                scrollToElementJS(locator);
                Point initialPoint = element.getLocation();
                LogsManager.info("Element initial position: " + initialPoint);
                Point finalPoint = element.getLocation();
                LogsManager.info("Element final position: " + finalPoint);
                if(!initialPoint.equals(finalPoint)){
                  return false;
                }
                LogsManager.info("element is in stable position, proceeding to click.");
                element.click();
                LogsManager.info("Clicked on element: " + locator);
                return true;
            } catch (Exception e) {
                LogsManager.error("Error clicking on element: " + locator + ". Exception: " + e.getMessage());
                return false;
            }

        }

    );
        return this;
    }

    public ElementActions type(By locator, String text) {
        waitManager.fluentWait().until(d ->
                    {
                       try {
                            WebElement element = d.findElement(locator);
                            scrollToElementJS(locator);
                            element.clear();
                            element.sendKeys(text);
                            LogsManager.info("Typed text '" + text + "' into element: " + locator);
                            return true;
                       } catch (Exception e) {
                            return false;
                       }
                    }
        );
        return this;
    }

    //hovering
    public ElementActions hover(By locator) {
        waitManager.fluentWait().until(d ->
        {
            try {
                WebElement element = d.findElement(locator);
                scrollToElementJS(locator);
                new Actions(d).moveToElement(element).moveByOffset(2, 2).perform();
                LogsManager.info("Hovered over element: " + locator);
                return true;
            } catch (Exception e) {
                return false;
            }
        });
        return this;
    }

    public String getText(By locator) {
        return waitManager.fluentWait().until(d ->
                {
                    try {
                        WebElement element = d.findElement(locator);
                        scrollToElementJS(locator);
                        String msg = element.getText();
                        LogsManager.info("Retrieved text '" + msg + "' from element: " + locator);
                        return !msg.isEmpty() ? element.getText() : null;
                    } catch (Exception e) {
                        return null;
                    }
                }
        );
    }

    public ElementActions uploadFile(By locator,String filePath) {
        String absolutePath = System.getProperty("user.dir") + File.separator + filePath;
        waitManager.fluentWait().until(d ->
        {
            try {
                WebElement element = d.findElement(locator);
                scrollToElementJS(locator);
                element.sendKeys(absolutePath);
                LogsManager.info("Uploaded file from path: " + absolutePath + " to element: " + locator);
                return true;
            } catch (Exception e) {
                return null;
            }
        });
        return this;
    }

    //find an element
    public WebElement findElement(By locator) {
        return driver.findElement(locator);
    }

    //function to scroll to an element using js
    public void scrollToElementJS(By locator) {
        ((org.openqa.selenium.JavascriptExecutor) driver)
                .executeScript("""
                        arguments[0].scrollIntoView({behaviour:"auto",block:"center",inline:"center"});
                        """, findElement(locator));
        //can handle exceptions by trying selenium actions scroll after js scroll
    }

    //function to select from dropdown by visible text
    public ElementActions selectFromDropdown(By locator, String value) {
        waitManager.fluentWait().until(d ->
        {
            try {
                WebElement element = d.findElement(locator);
                scrollToElementJS(locator);
                Select select = new Select(element);
                select.selectByVisibleText(value);
                LogsManager.info("Selected option '" + value + "' from dropdown: " + locator);
                return true;
            } catch (Exception e) {
                return false;
            }
        });
        return this;
    }

}
