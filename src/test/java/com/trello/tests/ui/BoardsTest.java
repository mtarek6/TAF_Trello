package com.trello.tests.ui;
import com.trello.drivers.GUIDriver;
import com.trello.drivers.UITest;
import com.trello.pages.LoginPage;
import com.trello.pages.components.Homepage;
import com.trello.tests.BaseTest;
import com.trello.utils.TimeManager;
import com.trello.utils.dataReader.JsonReader;
import io.qameta.allure.*;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;


@Epic("Trello Web Tests")
@Feature("UI Boards Management")
@Story("Create, Update, Delete Boards")
@Owner("Mostafa")
@Severity(SeverityLevel.CRITICAL)
@UITest
public class BoardsTest extends BaseTest {
    String timestamp = TimeManager.getSimpleTimeStamp();
    //tests

    @Description("Verify that user can create a new board after logging in")
    @Test
    public void createNewBoardTC() {
        new LoginPage(driver).navigate()
                .enterLoginEmail(testData.getJsonData("email"))
                .clickOnContinueButton()
                .enterLoginPassword(testData.getJsonData("password"))
                .clickOnLoginButton()
                .verifyBoardsPageIsLoaded()
                .clickOnCreateNewBoardButton()
                .enterBoardTitle(testData.getJsonData("board_name") + timestamp)
                .setBoardVisibility(testData.getJsonData("board_visibility.public"))
                .clickOnCreateBoardFinalButton()
                .verifyBoardIsCreated(testData.getJsonData("board_name") + timestamp);
    }

    @Description("Verify that user cannot create a new board with empty title")
    @Test
    public void createNewBoardInvalidTC() {
        new LoginPage(driver).navigate()
                .enterLoginEmail(testData.getJsonData("email"))
                .clickOnContinueButton()
                .enterLoginPassword(testData.getJsonData("password"))
                .clickOnLoginButton()
                .verifyBoardsPageIsLoaded()
                .clickOnCreateNewBoardButton()
                .verifyCreateBoardButtonIsDisabled();
    }

    //Configurations
    @BeforeClass
    protected void preCondition() {
        testData = new JsonReader("boards-data");
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
