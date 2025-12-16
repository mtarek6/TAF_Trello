package com.trello.tests.api;

import com.trello.apis.TrelloBoardsAPI;
import com.trello.tests.BaseTest;
import com.trello.utils.TimeManager;
import com.trello.utils.dataReader.JsonReader;
import org.testng.ITestContext;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class BoardsApiTest extends BaseTest {
    String timestamp = TimeManager.getSimpleTimeStamp();
    @Test
    public void createBoardTC_API(ITestContext context) {
        TrelloBoardsAPI trelloBoardsAPI = new TrelloBoardsAPI();
        String boardName = testData.getJsonData("board_name" ) + timestamp;
        trelloBoardsAPI.createBoard(boardName, null)
                .verifyBoardCreated(boardName);
        String boardId = trelloBoardsAPI.getCreatedBoardId();
        context.setAttribute("boardId", boardId);
    }

    @Test(dependsOnMethods = "createBoardTC_API")
    public void deleteBoardTC_API(ITestContext context) {
        String boardId = (String) context.getAttribute("boardId");

        TrelloBoardsAPI boardsApi = new TrelloBoardsAPI();
        boardsApi.deleteBoard(boardId)
                .verifyBoardDeleted();
    }

    //Configurations

    @BeforeClass
    public void setUp() {
        testData = new JsonReader("boards-data");
    }
}
