package com.trello.pages;

import com.trello.drivers.GUIDriver;
import com.trello.utils.dataReader.PropertyReader;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

public class LoginPage {
    private final GUIDriver driver;
    private final String loginPageEndpoint = "/login";
    public LoginPage(GUIDriver driver) {
        this.driver = driver;
    }

    //locators
    private final By emailInput = By.xpath("//input[@data-testid='username']");
    private final By continueButton = By.id("login-submit");
    private final By passwordInput = By.id("password");
    private final By invalidEmailMsg = By.xpath("//div[@data-testid='form-text-field--input-invalid-error-message-field--idf-testid']");
    private final By invalidPasswordMsg = By.xpath("//div[@data-testid='form-error--content']");
    //actions
    @Step("Navigate to Trello login page")
    public LoginPage navigate()
    {
        driver.browser().navigateTo(PropertyReader.getProperty("baseUrlWeb") + loginPageEndpoint);
        return this;
    }

    @Step("Enter login email: {email}")
    public LoginPage enterLoginEmail(String email) {
        driver.element().type(emailInput, email);
        return this;
    }

    @Step("Click on Continue button")
    public LoginPage clickOnContinueButton() {
        driver.element().click(continueButton);
        return this;
    }

    @Step("Enter login password: {password}")
    public LoginPage enterLoginPassword(String password) {
        driver.element().type(passwordInput, password);
        return this;
    }

    @Step("Click on Login button")
    public BoardsPage clickOnLoginButton() {
        driver.element().click(continueButton);
        return new BoardsPage(driver);
    }


    //validations
    @Step("Verify logged in by checking url contains 'boards'")
    public BoardsPage verifyUserIsLoggedIn() {
        driver.verify().assertPageUrlContains("/boards");
        return new BoardsPage(driver);
    }

    @Step("Verify invalid email message is displayed")
    public LoginPage verifyInvalidEmailMessageIsDisplayed(String expectedMessage) {
        driver.verify().assertElementTextEquals(invalidEmailMsg, expectedMessage);
        return this;
    }

    @Step("Verify invalid password message is displayed")
    public LoginPage verifyInvalidPasswordMessageIsDisplayed(String expectedMessage) {
        driver.verify().assertElementTextContains(invalidPasswordMsg, expectedMessage);
        return this;
    }

    @Step("Verify enter email message is displayed")
    public LoginPage verifyEmptyEmailMessageIsDisplayed(String expectedMessage) {
        driver.verify().assertElementTextContains(invalidEmailMsg, expectedMessage);
        return this;
    }

    @Step("Verify enter password message is displayed")
    public LoginPage verifyEmptyPasswordMessageIsDisplayed(String expectedMessage) {
        driver.verify().assertElementTextContains(invalidEmailMsg, expectedMessage);
        return this;
    }
}
