package com.trello.tests.bdd.api.steps;

import com.trello.utils.dataReader.PropertyReader;
import com.trello.validations.Validation;
import io.cucumber.java.en.Given;

import static org.testng.Assert.assertFalse;

public class CommonSteps {
    @Given("I have valid Trello API credentials")
    public void i_have_valid_trello_api_credentials() {
        String key = PropertyReader.getProperty("apiKey");
        String token = PropertyReader.getProperty("apiToken");
        Validation validation = new Validation();
        validation.assertNotNull(key, "apiKey is null in properties");
        validation.assertNotNull(token, "apiToken is null in properties");

        assertFalse(key.trim().isEmpty(), "apiKey is empty");
        assertFalse(token.trim().isEmpty(), "apiToken is empty");
    }
}
