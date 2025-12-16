package com.trello.tests.bdd.api.hooks;

import com.trello.apis.Builder;
import com.trello.tests.bdd.api.steps.BoardsSteps;
import io.cucumber.java.After;

public class CleanupHooks {

    @After("@CreateBoard")
    public void cleanupCreatedBoard() {

        String boardId = BoardsSteps.getBoardId();

        if (boardId != null && !boardId.trim().isEmpty()) {
            io.restassured.RestAssured
                    .given()
                    .spec(Builder.getTrelloSpec())
                    .pathParam("id", boardId)
                    .when()
                    .delete("/boards/{id}");
        }
    }
}
