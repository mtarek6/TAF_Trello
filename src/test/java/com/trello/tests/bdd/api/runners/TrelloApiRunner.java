package com.trello.tests.bdd.api.runners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
        features = "src/test/resources/features",
        glue = {"com.trello.tests.bdd.api.steps", "com.trello.bdd.api.hooks"},
        plugin = {
                "pretty",
                "html:test-outputs/cucumber-reports/cucumber.html",
                "json:test-outputs/cucumber-reports/cucumber.json",
                "io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm"
        },
        monochrome = true
)
public class TrelloApiRunner extends AbstractTestNGCucumberTests { }