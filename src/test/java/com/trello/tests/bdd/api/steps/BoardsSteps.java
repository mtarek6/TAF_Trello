package com.trello.tests.bdd.api.steps;

import com.trello.apis.Builder;
import com.trello.apis.TrelloBoardsAPI;
import com.trello.utils.TimeManager;
import com.trello.utils.logs.LogsManager;
import com.trello.validations.Validation;
import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.testng.Assert.assertTrue;

public class BoardsSteps {

    public static Response response;
    private static final String createBoardEndpoint = "/boards";
    private static final String timeStamp = TimeManager.getSimpleTimeStamp();
    private static String boardId;
    private static TrelloBoardsAPI trelloBoardsAPI;

    @Before
    public void setupApiClient() {
        trelloBoardsAPI = new TrelloBoardsAPI();
    }

    @When("I create a board with name {string} and description {string}")
    public void i_create_a_board_with_name_and_description(String name, String desc) {

        String expectedBoardName = name + " " + timeStamp;
        trelloBoardsAPI.createBoard(expectedBoardName, desc);
    }

    @Then("The API call got success with status code {int}")
    public void the_api_call_got_success_with_status_code(Integer expectedCode) {
        // what was the way to write this without creating new Validation object here?

        //Validation validation = new Validation();
        new Validation().responseCodeEquals(
                trelloBoardsAPI.getStatusCode(),
                expectedCode
        );

    }

    @Then("\"name\" in response body is {string}")
    public void name_in_response_body_is(String expectedName) {

        String expected = expectedName + " " + timeStamp;
        trelloBoardsAPI.verifyBoardCreated(expected);
    }

    @Then("\"description\" in response body is {string}")
    public void description_in_response_body_is(String expectedDesc) {

        trelloBoardsAPI.verifyBoardDescription(expectedDesc);
    }

    @And("I store the created board id")
    public void i_store_the_created_board_id() {
        String boardId = trelloBoardsAPI.getCreatedBoardId();

        assertTrue(
                boardId != null && !boardId.trim().isEmpty(),
                "Board ID was not returned after board creation"
        );

        BoardsSteps.boardId = boardId;
    }

    // getter for later steps
    public static String getBoardId() {
        return boardId;
    }

    @When("I delete the created board")
    public void i_delete_the_created_board() {

        trelloBoardsAPI
                .deleteBoard(boardId)
                .verifyBoardDeleted();
    }
}
