![](https://picnic.app/nl/wp-content/uploads/sites/2/2020/11/logo.svg)
![alt text](https://picnic.app/nl/wp-content/uploads/sites/2/2020/11/skyline-universal.svg)
# Gist API Test Framework

### Functional scenarios : 

```
Scenario Details - Creates a gist for given input parameters in API payload & validates response status, JSON schema, and actual data.

@CreateGist @FunctionalTest
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
      | Endpoint | ContentType                       | Description | GistType | NumOfFiles | ExpectedStatusCode |
      | /gists   | application/vnd.github.v3+json    | Test files  | private  |          2 |                201 |
      | /gists   | application/json                  | Test data   | public   |          3 |                201 |
      | /gists   | application/json                  |             | public   |          2 |                201 |
      | /gists   | application/x-www-form-urlencoded | 00000000000 | private  |          2 |                201 |
      | /gists   | application/vnd.github.v3+json    | asdasdasd   | public   |          0 |                422 |
      | /gistsss | application/vnd.github.v3+json    | asdasdasd   | public   |          3 |                404 |
```

```
Scenario Details - Creates a gist, and reads it using Read API, validates response code, JSON schema, and actual data.

@CreateAndReadGist @FunctionalTest
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
      | Endpoint | ContentType                    | Description | GistType | ReadGistID   | NumOfFiles | CreateStatusCode | ReadStatusCode |
      | /gists   | application/vnd.github.v3+json | Test files  | private  | CreatedAbove |          2 |              201 |            200 |
      | /gists   | application/json               | ABCCD       | public   | CreatedAbove |          3 |              201 |            200 |
      | /gists   | application/json               | Random      | public   | WrongGist    |          3 |              201 |            404 |

```

```
Scenario Details - Creates a gist, and deletes it using Delete API, and verifies deletion by trying to read it.

@CreateAndDeleteAndVerifyDeletedGist @FunctionalTest
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
      | Endpoint | ContentType                    | Description | GistType | GistID       | NumOfFiles | CreateStatusCode | DeleteStatusCode | ReadStatusCode |
      | /gists   | application/vnd.github.v3+json | Test files  | private  | CreatedAbove |          2 |              201 |              204 |            404 |
      | /gists   | application/json               | ABCCD       | public   | CreatedAbove |          3 |              201 |              204 |            404 |
      | /gists   | application/json               | Random      | public   | WrongGist    |          3 |              201 |              404 |            404 |
```

```
 Scenario Details - Gets Rate limit statistics, makes a request and verifies that stattics has been changed for Rate Limit 

 @GetRateLimit @NonFunctionalTest
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
     | Endpoint      | AuthToken  | ExpectedStatusCode |
     | /rate_limit   | auth       |                200 |
     | /rate_limit   | wrong_auth |                401 |
     | /rate_limittt | auth       |                404 |
```

.. 4 more

    



### Explanation
- **Functional Tests** focuses on a functional test of API for expected implementation usage, designed with Scenario Outline keyword that runs for given inputs.

### Environment setup
- Needs JDK installed (Ex- Java JDK version 1.8.0_251)
- Needs Maven binary installed & env variable set. Refer to this link -> [link](https://maven.apache.org/install.html)

### Framework overview
- This is a hybrid maven based framework that uses Rest-Assured Java DSL library, JUnit, and Cucumber (Gherkin)

### Details required in **resources/config.properties**
```
base_url= <base url to github gist>
oauthtoken= <access token for gist having gist scope>
```
_Note: These settings are placed for a temporary purpose, one should have test data file separately in future_

### How to execute the tests from the command line
- Navigate to the folder where the code has been placed/pulled
- Open command prompt in the same directory
- Enter command **mvn test -Dcucumber.filter.tags="@tag_of_respective_scenario"**.
- Example - **mvn test -Dcucumber.filter.tags="@CreateGist"**

### How to execute the tests from the eclipse UI
- Open Eclipse and import code as an existing maven project
- Navigate to **src/test/java** and run RunTest.java as JUnit Test. It will run all scenarios if tags are not mentioned as tags="@tag_of_respective_scenario" inside Cucumber options

### Execute using the custom batch file
- Created a Run_All_Tests.bat file that can run all test cases. It is placed on the root directory of the code base

### Reports
##### Types of reports are being created under **/test-ouput** folder
- API_Tests.html
- Cucumber.xml
- Cucumber.json

### Logs
Logs can be found under logs folder on the root directory.





### Author - Aakash Dahake
##### 27/03/2021