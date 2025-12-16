Feature: Trello Boards API

  @CreateBoard
  Scenario Outline: Verify that a new board is created successfully
    Given I have valid Trello API credentials
    When I create a board with name "<name>" and description "<desc>"
    And I store the created board id
    Then The API call got success with status code 200
    And "name" in response body is "<name>"
    And "description" in response body is "<desc>"

    Examples:
      | name          | desc                     |
      | Project Plan | Board for project plans  |
      | Sprint Backlog | Board for sprint tasks   |
