package com.trello.pages;

import com.google.common.base.Verify;
import com.trello.drivers.GUIDriver;
import com.trello.utils.dataReader.PropertyReader;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

public class BoardsPage {
    private final GUIDriver driver;
    private final String loginPageEndpoint = "/u/mostafatarek18/boards";
    public BoardsPage(GUIDriver driver) {
        this.driver = driver;
    }

    //locators
    private final By createButton = By.className("jzNA5uVDhk7V5S");
    private final By createBoardButton = By.xpath("//button[@data-testid='header-create-board-button']");
    private final By createNewBoardTile = By.xpath("//button[@data-testid='create-board-tile']");
    private final By boardTitleInput = By.xpath("//input[@data-testid='create-board-title-input']");
    private final By createBoardFinalButton = By.xpath("//button[@data-testid='create-board-submit-button']");
    private final By makeBoardPublicButton = By.xpath("//button[.='Yes, make board public']");
    private final By switchBoardsButton = By.xpath(".//button[@data-testid='panel-nav-board-switcher-button']");
    private final By boardVisibilityDropdown = By.xpath("//div[@data-testid='create-board-select-visibility-select--control']");
    //dynamic locators
    private By boardByTitle(String boardTitle)
    {
        return By.cssSelector("h3.xtkiiaSp5ulDJM + div._0WILnE2Yki1zE3 a[title='"+boardTitle+"']");
    }

    private final By boardVisibility(String visibilityOption)
    {
        return switch (visibilityOption.toLowerCase()) {
            case "private" -> By.xpath("//div[@data-testid='create-board-select-visibility-select--option-0']");
            case "workspace" -> By.xpath("//div[@data-testid='create-board-select-visibility-select--option-1']");
            case "public" -> By.xpath("//div[@data-testid='create-board-select-visibility-select--option-2']");
            default -> throw new IllegalArgumentException("Invalid visibility option: " + visibilityOption);
        };
    }

    //actions
    @Step("Navigate to Boards page")
    public BoardsPage navigate()
    {
        driver.browser().navigateTo(PropertyReader.getProperty("baseUrlWeb") + loginPageEndpoint);
        return this;
    }

    @Step("Click on Create new Board button")
    public BoardsPage clickOnCreateNewBoardButton() {
        driver.element().click(createNewBoardTile);
        return this;
    }

    @Step("Enter Board title: {boardTitle}")
    public BoardsPage enterBoardTitle(String boardTitle) {
        driver.element().type(boardTitleInput, boardTitle);
        return this;
    }

    @Step("Set Board visibility to: {visibilityOption}")
    public BoardsPage setBoardVisibility(String visibilityOption) {
        driver.element().click(boardVisibilityDropdown);
        driver.element().click(boardVisibility(visibilityOption));
        if(visibilityOption.equalsIgnoreCase("public"))
        {
            driver.element().click(makeBoardPublicButton);
        }
        return this;
    }

    @Step("Click on Create new Board final button")
    public BoardsPage clickOnCreateBoardFinalButton() {
        driver.element().click(createBoardFinalButton);
        return this;
    }

    //validations
    @Step("Verify logged in by checking url contains 'boards'")
    public BoardsPage verifyUserIsLoggedIn() {
        driver.verify().assertPageUrlContains("/boards");
        return new BoardsPage(driver);
    }

    @Step("Verify Boards page is loaded by checking Create Board button is visible")
    public BoardsPage verifyBoardsPageIsLoaded() {
        driver.verify().isElementVisible(createButton);
        return this;
    }

    @Step("Verify Board '{boardTitle}' is created")
    public BoardPage verifyBoardIsCreated(String boardTitle) {
        driver.verify().assertPageUrlContains(boardTitle.replaceAll(" ", "-").toLowerCase());
        return new BoardPage(driver);
    }

    @Step("Verify Create Board button is disabled when board title is empty")
    public BoardsPage verifyCreateBoardButtonIsDisabled() {
        driver.verify().isElementDisabled(createBoardFinalButton);
        return this;
    }
}
