package com.trello.pages.components;

import com.trello.drivers.GUIDriver;
import com.trello.utils.dataReader.PropertyReader;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

public class Homepage {
    private final GUIDriver driver;
    public Homepage(GUIDriver driver) {
        this.driver = driver;
    }

    //locators
    private final By homeButton = By.linkText("/home");
    private final By loginButton = By.cssSelector("a[data-uuid='MJFtCCgVhXrVl7v9HA7EH_login']");
    private final By featuresButton = By.xpath("//button[@data-testid='bignav-tab' and .='Features']");
    private final By solutionsButton = By.xpath("//button[@data-testid='bignav-tab' and .='Solutions']");
    private final By plansButton = By.xpath("//button[@data-testid='bignav-tab' and .='Plans']");
    private final By resourcesButton = By.xpath("//button[@data-testid='bignav-tab' and .='Resources']");
    private final By pricingButton = By.linkText("/pricing");
    private final By homePageLabel = By.xpath("//h1[contains(text(),'Capture, organize')]");
    //actions
    @Step("Navigate to Trello homepage")
    public Homepage navigate()
    {
        driver.browser().navigateTo(PropertyReader.getProperty("baseUrlWeb"));
        return this;
    }

    @Step("Click on Home Button")
    public Homepage clickOnHomeButton()
    {
        driver.element().click(homeButton);
        return this;
    }

    @Step("Click on Login Button")
    public Homepage clickOnLoginButton()
    {
        driver.element().click(loginButton);
        return this;
    }

    @Step("Click on Features Button")
    public Homepage clickOnFeaturesButton()
    {
        driver.element().click(featuresButton);
        return this;
    }

    @Step("Click on Solutions Button")
    public Homepage clickOnSolutionsButton()
    {
        driver.element().click(solutionsButton);
        return this;
    }

    @Step("Click on Plans Button")
    public Homepage clickOnPlansButton()
    {
        driver.element().click(plansButton);
        return this;
    }

    @Step("Click on Resources Button")
    public Homepage clickOnResourcesButton()
    {
        driver.element().click(resourcesButton);
        return this;
    }

    @Step("Click on Pricing Button")
    public Homepage clickOnPricingButton()
    {
        driver.element().click(pricingButton);
        return this;
    }
    //validations
    @Step("Verify Home Page Label")
    public Homepage verifyHomePageLabel() {
        driver.verify().isElementVisible(homePageLabel);
        return this;
    }

}
