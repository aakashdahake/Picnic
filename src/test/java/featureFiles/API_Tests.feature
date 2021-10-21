Feature: Github Gist API Testing

  @CreateGist @FunctionalTest @all
  Scenario Outline: Verify that Create Gist API works in various conditions
    Given user sets base URL and endpoint as "<Endpoint>"
    Then user adds "Accept" as "<ContentType>" in header
    And user adds description to gist as "<Description>"
    And user makes gist as "<GistType>"
    Then user creates payload body with <NumOfFiles> files
    When user makes "POST" request to endpoint
    Then user validates response code as <ExpectedStatusCode>
    And user validates JSON schema for request for Status code <ExpectedStatusCode> for create gist API
    Then user validates data received in json response for create gist request

    Examples: 
      | Endpoint | ContentType                       | Description | GistType | NumOfFiles | ExpectedStatusCode |
      | /gists   | application/vnd.github.v3+json    | Test files  | private  |          5 |                201 |
      | /gists   | application/json                  | Test data   | public   |          3 |                201 |
      | /gists   | application/json                  |             | public   |          2 |                201 |
      | /gists   | application/x-www-form-urlencoded | 00000000000 | private  |          2 |                201 |
      | /gists   | application/vnd.github.v3+json    | asdasdasd   | public   |          0 |                422 |
      | /gistsss | application/vnd.github.v3+json    | asdasdasd   | public   |          3 |                404 |

  @CreateAndReadGist @FunctionalTest @all
  Scenario Outline: Verify that Read Gist API works in various conditions
    Given user sets base URL and endpoint as "<Endpoint>"
    Then user adds "Accept" as "<ContentType>" in header
    And user adds description to gist as "<Description>"
    And user makes gist as "<GistType>"
    Then user creates payload body with <NumOfFiles> files
    When user makes "POST" request to endpoint
    Then user validates response code as <CreateStatusCode>
    And user validates JSON schema for request for Status code <CreateStatusCode> for create gist API
    Then user validates data received in json response for create gist request
    When user sets base URL and endpoint as "/gists/{gist_Id}"
    Then user makes "GET" request to endpoint with path params "gist_Id" as "<ReadGistID>" with "auth" token
    And user validates response code as <ReadStatusCode>
    And user validates JSON schema for request for Status code <ReadStatusCode> for read gist API

    Examples: 
      | Endpoint | ContentType                    | Description | GistType | ReadGistID   | NumOfFiles | CreateStatusCode | ReadStatusCode |
      | /gists   | application/vnd.github.v3+json | Test files  | private  | CreatedAbove |          2 |              201 |            200 |
      #| /gists   | application/json               | ABCCD       | public   | CreatedAbove |          3 |              201 |            200 |
      #| /gists   | application/json               | Random      | public   | WrongGist    |          3 |              201 |            404 |

  @CreateAndDeleteAndVerifyDeletedGist @FunctionalTest @all
  Scenario Outline: Verify that Delete Gist API works in various conditions
    Given user sets base URL and endpoint as "<Endpoint>"
    Then user adds "Accept" as "<ContentType>" in header
    And user adds description to gist as "<Description>"
    And user makes gist as "<GistType>"
    Then user creates payload body with <NumOfFiles> files
    When user makes "POST" request to endpoint
    Then user validates response code as <CreateStatusCode>
    And user validates JSON schema for request for Status code <CreateStatusCode> for create gist API
    Then user validates data received in json response for create gist request
    Then user sets base URL and endpoint as "/gists/{gist_Id}"
    When user makes "DELETE" request to endpoint with path params "gist_Id" as "<GistID>" with "auth" token
    Then user validates response code as <DeleteStatusCode>
    When user makes "GET" request to endpoint with path params "gist_Id" as "<GistID>" with "auth" token
    Then user validates response code as <ReadStatusCode>
    And user validates JSON schema for request for Status code <ReadStatusCode> for read gist API

    Examples: 
      | Endpoint | ContentType                    | Description | GistType | GistID       | NumOfFiles | CreateStatusCode | DeleteStatusCode | ReadStatusCode |
      | /gists   | application/vnd.github.v3+json | Test files  | private  | CreatedAbove |          2 |              201 |              204 |            404 |
      | /gists   | application/json               | ABCCD       | public   | CreatedAbove |          3 |              201 |              204 |            404 |
      | /gists   | application/json               | Random      | public   | WrongGist    |          3 |              201 |              404 |            404 |

  @ReadUnauthenticatedUserGist @FunctionalTest @all
  Scenario Outline: Verify that Read Gist API works for unauthenticated user in various conditions
    Given user sets base URL and endpoint as "/users/{username}/gists"
    When user makes "GET" request to endpoint with path params "username" as "<Username>" with "<AuthToken>" token
    Then user validates response code as <ExpectedStatusCode>
    And user validates JSON schema for request for Status code <ExpectedStatusCode> for list user gists
    Then user validates data received in json response for listing user as "<Username>"

    Examples: 
      | Username             | AuthToken | ExpectedStatusCode |
      | aakashdahake         | auth      |                200 |
      | ******************** | auth      |                404 |
      |                      | auth      |                404 |

  @ReadAuthenticatedUserGists @FunctionalTest @all
  Scenario Outline: Verify that Read Gist API works for authenticated users token in various conditions
    Given user sets base URL and endpoint as "<Endpoint>"
    Then user adds query param "since" as "<Since>" to endpoint path
    Then user adds query param "per_page" as "<PerPage>" to endpoint path
    Then user adds query param "page" as "<Page>" to endpoint path
    When user makes "GET" request to endpoint with query params with "auth" token
    Then user validates response code as <ExpectedStatusCode>
    And user validates JSON schema for request for Status code <ExpectedStatusCode> for list authenticated user gists
    Then user validates data received in json response for listing authenticated user gists

    Examples: 
      | Endpoint | Since                    | PerPage | Page | ExpectedStatusCode |
      | /gists   | 2021-03-26T03:19:53.895Z |       1 |    1 |                200 |
      | /gists   | 2021-04-26T03:19:53.895Z |       0 |    1 |                200 |
      | /gists   | 2021-03-20T03:19:53.895Z |       5 |    0 |                200 |
      | /gistss  | 2021-03-26T03:19:53.895Z |       1 |    1 |                404 |
      | /gists   | 2****///****             |       1 |    1 |                422 |

  @ReadPublicGists @FunctionalTest @all
  Scenario Outline: Verify that List Gist API works for authenticated users token in various conditions
    Given user sets base URL and endpoint as "<Endpoint>"
    Then user adds "Accept" as "<ContentType>" in header
    When user makes "GET" request to endpoint
    Then user validates response code as <ExpectedStatusCode>
    And user validates JSON schema for request for Status code <ExpectedStatusCode> for list authenticated user gists
    Then user validates data received in json response for listing authenticated user gists

    Examples: 
      Examples:

      | Endpoint | ContentType                           | ExpectedStatusCode |
      | /gists   | application/vnd.github.v3+json        |                200 |
      | /gists   | application/json                      |                200 |
      | /gists   | application/vnd.github.VERSION.base64 |                200 |
      | /gists   | application/vnd.github.VERSION.raw    |                200 |
      | /gistss  | application/vnd.github.v3+json        |                404 |

  @GetRateLimit @NonFunctionalTest @all
  Scenario Outline: Verify that Rate Limit API works for authenticated users token in various conditions
    Given user sets base URL and endpoint as "<Endpoint>"
    When user makes "GET" request to endpoint with query params with "<AuthToken>" token
    Then user validates response code as <ExpectedStatusCode>
    And user validates JSON schema for request for Status code <ExpectedStatusCode> for rate limit stats
    And user captures rate limit statistics for comparison
    When user sets base URL and endpoint as "/gists"
    Then user adds "Accept" as "application/vnd.github.v3+json" in header
    When user makes "GET" request to endpoint
    Then user sets base URL and endpoint as "<Endpoint>"
    When user makes "GET" request to endpoint with query params with "<AuthToken>" token
    Then user validates response code as <ExpectedStatusCode>
    And user validates JSON schema for request for Status code <ExpectedStatusCode> for rate limit stats
    Then user validates rate limit statistics

    Examples: 
      | Endpoint      | AuthToken  | ExpectedStatusCode |
      | /rate_limit   | auth       |                200 |
      | /rate_limit   | wrong_auth |                401 |
      | /rate_limittt | auth       |                404 |

  @CheckAccessibility @NonFunctionalTest @all
  Scenario Outline: Verify that accessibility works for authenticated users token in various conditions
    Given user sets base URL and endpoint as "<Endpoint>"
    And user makes gist as "<GistType>"
    Then user creates payload body with <NumOfFiles> files
    When user makes "POST" request to endpoint
    Then user validates response code as <ExpectedStatusCode>
    And user validates JSON schema for request for Status code <ExpectedStatusCode> for create gist API
    Then user validates data received in json response for create gist request
    When user sets base URL and endpoint as "/users/{username}/gists"
    Then user validates accessibility for gist when gist type is "<GistType>" provided Username as "<Username>"

    Examples: 
      | Endpoint | GistType | NumOfFiles | Username     | ExpectedStatusCode |
      | /gists   | private  |          2 | aakashdahake |                201 |
      | /gists   | public   |          3 | aakashdahake |                201 |
