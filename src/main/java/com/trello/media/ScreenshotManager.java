package com.trello.media;

import com.trello.utils.TimeManager;
import com.trello.utils.logs.LogsManager;
import com.trello.utils.report.AllureAttachmentManager;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;

public class ScreenshotManager {
    public static final String SCREENSHOTS_PATH = "test-outputs/screenshots/";
    public static void takeFullPageScreenshot(WebDriver driver, String screenshotName) {
        try {
            File screenshotSrc = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            LogsManager.debug("Screenshot source file path: " + screenshotSrc.getAbsolutePath());
            File screenshotFile = new File(SCREENSHOTS_PATH + screenshotName + "-" + TimeManager.getTimestamp()  );
            LogsManager.debug("Screenshot destination file path: " + screenshotFile.getAbsolutePath());
            FileUtils.copyFile(screenshotSrc, screenshotFile);

            AllureAttachmentManager.attachScreenshot(screenshotName, screenshotFile.getAbsolutePath());
            LogsManager.info("Screenshot taken: " + screenshotFile.getAbsolutePath());
        }
        catch (Exception e) {
            LogsManager.error("Failed to take screenshot: ", e.getMessage());
        }
    }

    //take element screenshot
    public static void takeElementScreenshot(WebDriver driver,By elementSelector) {
        try {
            String ariaName = driver.findElement(elementSelector).getAccessibleName();
            File screenshotSrc = driver.findElement(elementSelector).getScreenshotAs(OutputType.FILE);
            File screenshotFile = new File(SCREENSHOTS_PATH + ariaName + "-" + TimeManager.getTimestamp() + ".png");
            FileUtils.copyFile(screenshotSrc, screenshotFile);

            //TODO: Attach to Allure or other reporting tools if needed
            LogsManager.info("Screenshot taken: " + screenshotFile.getAbsolutePath());
        } catch (Exception e) {
            LogsManager.error("Failed to take element screenshot: ", e.getMessage());
        }
    }
}
