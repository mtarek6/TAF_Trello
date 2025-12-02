package com.trello.utils.actions;

import com.trello.utils.WaitManager;
import com.trello.utils.logs.LogsManager;
import org.openqa.selenium.WebDriver;

public class AlertActions {
    private final WebDriver driver;
    private final WaitManager waitManager;

    public AlertActions(WebDriver driver) {
        this.driver = driver;
        this.waitManager = new WaitManager(driver);
    }

    public void acceptAlert()
    {
        waitManager.fluentWait().until(d ->
        {
            try {
                driver.switchTo().alert().accept();
                return true;
            } catch (Exception e) {
                LogsManager.error("Failed to accept alert: ", e.getMessage());
                return false;
            }
        });
    }

    public String getAlertText()
    {
        return waitManager.fluentWait().until(d ->
        {
            try {
                String text = driver.switchTo().alert().getText();

                return !text.isEmpty() ? text : null;
            } catch (Exception e) {
                LogsManager.error("Failed to get alert text: ", e.getMessage());
                return null;
            }
        });
    }

    public void dismissAlert()
    {
        waitManager.fluentWait().until(d ->
        {
            try {
                driver.switchTo().alert().dismiss();
                return true;
            }
            catch (Exception e) {
                LogsManager.error("Failed to dismiss alert: ", e.getMessage());
                return false;
            }
        });
    }

    public void setAlertText(String text)
    {
        waitManager.fluentWait().until(d ->
        {
            try {
                driver.switchTo().alert().sendKeys(text);
                return true;
            } catch (Exception e) {
                LogsManager.error("Failed to set alert text: ", e.getMessage());
                return false;
            }
        });
    }


}
