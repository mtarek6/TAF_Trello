package com.trello.utils.actions;

import com.trello.utils.WaitManager;
import com.trello.utils.logs.LogsManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class FrameActions {
    private final WaitManager waitManager;
    private final WebDriver driver;

    public FrameActions(WebDriver driver) {
        this.driver = driver;
        this.waitManager = new WaitManager(driver);
    }

    // Switch to frame by index
    public void switchToFrameByIndex(int index) {
        waitManager.fluentWait().until(d -> {
            try {
                driver.switchTo().frame(index);
                LogsManager.info("Switched to frame with index: " + index);
                return true;
            } catch (Exception e) {
                return false;
            }
        });
    }

    // Switch to frame by name or ID
    public void switchToFrameByNameOrId(String nameOrId) {
        waitManager.fluentWait().until(d -> {
            try {
                driver.switchTo().frame(nameOrId);
                LogsManager.info("Switched to frame with name or ID: " + nameOrId);
                return true;
            } catch (Exception e) {
                return false;
            }
        });
    }

    // Switch to frame by WebElement
    public void switchToFrameByElement(By frameLocator) {
        waitManager.fluentWait().until(d -> {
            try {
                driver.switchTo().frame(d.findElement(frameLocator));
                LogsManager.info("Switched to frame using locator: " + frameLocator);
                return true;
            } catch (Exception e) {
                return false;
            }
        });
    }

    //switch back to the main document from an iframe
    public void switchToDefaultContent() {
        waitManager.fluentWait().until(d -> {
            try {
                driver.switchTo().defaultContent();
                LogsManager.info("Switched back to the main document from an iframe");
                return true;
            } catch (Exception e) {
                return false;
            }
        });
    }
}
