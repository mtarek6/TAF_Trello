package com.trello.pages;

import com.trello.drivers.GUIDriver;
import org.openqa.selenium.By;

public class ListsPage {
    private final GUIDriver driver;
    public ListsPage(GUIDriver driver) {
        this.driver = driver;
    }

    //locators
    private final By createListButton = By.xpath("//button[@data-testid='list-composer-button']");
    private final By listNameInput = By.cssSelector("textarea[placeholder='Enter list name…']");
    private final By addListFinalButton = By.xpath("//button[@data-testid='list-composer-add-list-button']");



    //actions


    //validations
}
