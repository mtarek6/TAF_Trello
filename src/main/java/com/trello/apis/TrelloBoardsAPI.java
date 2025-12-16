package com.trello.apis;

import com.trello.utils.logs.LogsManager;
import com.trello.validations.Verification;
import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.util.HashMap;
import java.util.Map;

public class TrelloBoardsAPI {
    RequestSpecification requestSpecification;
    Response response;
    Verification verification;
    //constructor
    public TrelloBoardsAPI()
    {
        requestSpecification = RestAssured.given();
        verification = new Verification();

    }

    //endpoints
    private static final String createBoardEndpoint = "boards";
    private static final String deleteBoardEndpoint = "boards/{id}";


    @Step("Create Trello Board with name: {boardName} and description: {description}")
    public TrelloBoardsAPI createBoard(String boardName, String description)
    {
        Map<String, Object> queryParams = new HashMap<>();
        queryParams.put("name", boardName);
        if (description != null && !description.trim().isEmpty()) {
            queryParams.put("desc", description);
        }
        response = requestSpecification.spec(Builder.getTrelloSpec(queryParams))
                .post(createBoardEndpoint);
        LogsManager.info(response.asPrettyString());
        return this;
    }

    @Step("Delete Trello board with id: {boardId}")
    public TrelloBoardsAPI deleteBoard(String boardId) {
        response = requestSpecification
                .spec(Builder.getTrelloSpec())
                .pathParam("id", boardId)
                .delete(deleteBoardEndpoint);

        LogsManager.info(response.asPrettyString());
        return this;
    }

    // helper to expose ID from last createBoard()
    public String getCreatedBoardId() {

        int status = response.getStatusCode();
        String contentType = response.getHeader("Content-Type");
        String body = response.asString();

        // 1) Ensure request succeeded
        if (status != 200) {
            throw new AssertionError("Create board failed. Status: " + status
                    + ", Content-Type: " + contentType
                    + ", Body: " + body);
        }

        // 2) Ensure response is JSON
        if (contentType == null || !contentType.toLowerCase().contains("application/json")) {
            throw new AssertionError("Create board response is not JSON. Content-Type: " + contentType
                    + ", Body: " + body);
        }

        // 3) Safe JSON parse
        String id = response.jsonPath().getString("id");
        if (id == null || id.trim().isEmpty()) {
            throw new AssertionError("Board id was not found in create board response. Body: " + body);
        }

        return id;
    }

    public int getStatusCode() {
        return response.getStatusCode();
    }

    // ======== validations =========
    @Step("Verify board is created successfully with name: {expectedBoardName}")
    public TrelloBoardsAPI verifyBoardCreated(String expectedBoardName) {
        verification.responseCodeEquals(
                response.statusCode(), 200
        );
        verification.Equals(
                response.jsonPath().getString("name"), expectedBoardName,
                "Board name does not match. Expected: " + expectedBoardName + ", Actual: " + response.jsonPath().getString("name")
        );
        return this;
    }

    @Step("Verify board description is: {expectedDescription}")
    public TrelloBoardsAPI verifyBoardDescription(String expectedDescription) {
        verification.Equals(
                response.jsonPath().getString("desc"),
                expectedDescription,
                "Board description does not match. Expected: "
                        + expectedDescription + ", Actual: "
                        + response.jsonPath().getString("desc")
        );
        return this;
    }

    @Step("Verify Trello board is deleted successfully")
    public TrelloBoardsAPI verifyBoardDeleted() {
        verification.responseCodeEquals(
                response.getStatusCode(),
                200
        );
        return this;
    }
}
