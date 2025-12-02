package com.trello.tests;

import com.trello.drivers.GUIDriver;
import com.trello.drivers.WebDriverProvider;
import com.trello.utils.dataReader.JsonReader;
import org.openqa.selenium.WebDriver;

public class BaseTest implements WebDriverProvider {
    protected GUIDriver driver;
    protected JsonReader  testData;


    @Override
    public WebDriver getWebDriver() {
        return driver.get();
    }
}
