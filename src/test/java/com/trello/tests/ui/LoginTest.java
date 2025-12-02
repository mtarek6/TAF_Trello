package com.trello.tests.ui;

import com.trello.drivers.GUIDriver;
import com.trello.drivers.UITest;
import com.trello.pages.LoginPage;
import com.trello.pages.components.Homepage;
import com.trello.tests.BaseTest;
import com.trello.utils.dataReader.JsonReader;
import io.qameta.allure.*;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

@Epic("Trello Web Tests")
@Feature("UI User Management")
@Story("Login")
@Owner("Mostafa")
@Severity(SeverityLevel.CRITICAL)
@UITest
public class LoginTest extends BaseTest {

    @Description("Verify that user can login with valid data")
    @Test
    public void validLoginTC() {
        new LoginPage(driver).navigate()
                .enterLoginEmail(testData.getJsonData("email"))
                .clickOnContinueButton()
                .enterLoginPassword(testData.getJsonData("password"))
                .clickOnLoginButton()
                .verifyBoardsPageIsLoaded();
    }

    @Description("Verify the error message is displayed when user tries to login with invalid email")
    @Test
    public void InvalidEmailLoginTC() {
        new LoginPage(driver).navigate()
                .enterLoginEmail(testData.getJsonData("invalid_email"))
                .clickOnContinueButton()
                .verifyInvalidEmailMessageIsDisplayed(testData.getJsonData("messages.VerifyInvalidEmail"));
    }

    @Description("Verify the error message is displayed when user tries to login with invalid password")
    @Test
    public void InvalidPasswordLoginTC() {
        new LoginPage(driver).navigate()
                .enterLoginEmail(testData.getJsonData("email"))
                .clickOnContinueButton()
                .enterLoginPassword(testData.getJsonData("invalid_password"))
                .clickOnContinueButton()
                .verifyInvalidPasswordMessageIsDisplayed(testData.getJsonData("messages.VerifyInvalidPassword"));
    }

    @Description("Verify the error message is displayed when email field is empty")
    @Test
    public void EmptyEmailLoginTC() {
        new LoginPage(driver).navigate()
                .clickOnContinueButton()
                .verifyEmptyEmailMessageIsDisplayed(testData.getJsonData("messages.VerifyEmptyEmail"));
    }

    @Description("Verify the error message is displayed when password field is empty")
    @Test
    public void EmptyPasswordLoginTC() {
        new LoginPage(driver).navigate()
                .enterLoginEmail(testData.getJsonData("email"))
                .clickOnContinueButton()
                .enterLoginPassword("")
                .clickOnContinueButton()
                .verifyEmptyPasswordMessageIsDisplayed(testData.getJsonData("messages.VerifyEmptyPassword"));
    }

    //Configurations
    @BeforeClass
    protected void preCondition() {
        testData = new JsonReader("login-data");
    }

    @BeforeMethod
    public void setUp() {
        driver = new GUIDriver();
        new Homepage(driver).navigate();
        //driver.browser().closeExtensionTab();
    }

    @AfterMethod
    public void tearDown() {
        driver.quitDriver();
    }
}
