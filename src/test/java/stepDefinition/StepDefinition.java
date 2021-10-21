package stepDefinition;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.picnic.constants.Constants;
import com.picnic.corelibs.RestMethodProfiles;
import com.picnic.model.pojos.ListUserGistResponse;
import com.picnic.utils.ConfigManager;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;
import io.restassured.response.ResponseOptions;

public class StepDefinition {
	private static final String OAUTH_TOKEN = ConfigManager.getInstance().getString("oauthtoken");
	private static final String DELETE = "DELETE";
	private static final String GET = "GET";
	private static final String POST = "POST";
	private static final String PUBLIC = "public";
	private static final String PRIVATE = "private";
	private Map<String, Map<String, String>> filesData = new HashMap<>();
	private Map<String, String> fileNContent = new HashMap<>();
	private Map<String, Integer> rateLimit = new HashMap<>();
	private Map<String, Object> body = new HashMap<>();
	private Logger logger = LogManager.getLogger();
	private Map<String, String> headData = new HashMap<>();
	private Map<String, String> queryData = new HashMap<>();
	private String endPath;
	private ResponseOptions<Response> resp;
	private Boolean isPublic = false;
	private Boolean gistCreated;
	private String gistID;
	private Boolean shouldValidateResponse = false;
	RestMethodProfiles restEx = new RestMethodProfiles();
	private Scenario scenario;

	@Before
	public void scenarioSetup(Scenario scenario) {
		this.scenario = scenario;
		headData.clear();
		queryData.clear();
		shouldValidateResponse = false;
		gistCreated = false;
		gistID = null;
		
		logger.info("--------------------------------------------------------------------------------------------------------");
		logger.info(" Execution Started :::: {}",scenario.getName());
		logger.info("--------------------------------------------------------------------------------------------------------");
	}


	@After
	public void cleanup() {
		try {
			if (gistID != null && gistCreated) {
				Map<String, String> deleteParam = new HashMap<>();
				deleteParam.put("gist_Id", gistID);
				restEx.deleteOpsWithPathParamsAndToken(Constants.ENDPOINT_PATH_GISTID.getConstant(), deleteParam, OAUTH_TOKEN);
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}
	}

	@Given("user sets base URL and endpoint as {string}")
	public void user_sets_base_url_and_endpoint_as(String path) {
		logger.info("Setting base path as [{}]", path);
		endPath = path;
	}

	@Then("user adds {string} as {string} in header")
	public void user_adds_as_in_header(String key, String value) {
		logger.info("Setting Header as [{}:{}]", key, value);
		headData.put(key, value);
	}

	@Then("user adds description to gist as {string}")
	public void user_adds_description_to_gist_as(String description) {
		logger.info("Adding description to payload for creating gist as [{}]", description);
		body.put(Constants.HEADER_DESCRIPTION.getConstant(), description);
	}

	@Then("user makes gist as {string}")
	public void user_makes_gist_as(String type) {

		switch (type) {
		case PRIVATE:
			isPublic = false;
			break;
		case PUBLIC:
			isPublic = true;
			break;
		case "":
			isPublic = true;
			type = PUBLIC;
		default:
			logger.error("Please provide proper gist access type, it should be [public] or [private]");
		}

		logger.info("Making Gist as [{}]", type);
		body.put(PUBLIC, isPublic);

	}

	@Then("user creates payload body with {int} files")
	public void user_creates_payload_body_with_files(Integer number) {

		for (int count = 1; count <= number; count++) {
			Map<String, String> fileContent = new HashMap<>();
			String content = "abc" + count;
			String fileName = Constants.TEST_FILE.getConstant() + count;
			fileContent.put(Constants.HEADER_CONTENT.getConstant(), content);
			filesData.put(fileName, fileContent);
			fileNContent.put(fileName, content);
		}
		body.put("files", filesData);
		logger.info("Created payload body :: [{}]", body);
	}

	@When("user makes {string} request to endpoint")
	public void user_makes_request_to_endpoint(String req) {

		switch (req) {
		case POST:
			resp = restEx.postOpsWithBodyAndToken(endPath, OAUTH_TOKEN, body);
			break;
		case GET:
			resp = restEx.getWithToken(endPath, OAUTH_TOKEN);
			break;
		// More methods to be added if required
		default:
			logger.error("Please provide proper REST method type, it should be [GET] or [POST] or [PUT] or [DELETE]");
			assertEquals("Please provide proper REST method type, it should be [GET] or [POST] or [PUT] or [DELETE]", null, req);
		}

		logger.info("Headers :: [{}]", resp.getHeaders());
		logger.info("Cookies :: [{}]", resp.getCookies());
		logger.info("Status Code :: [{}]", resp.getStatusCode());
		logger.info("Status Line :: [{}]", resp.getStatusLine());
		logger.info("Session ID :: [{}]", resp.getSessionId());
		logger.info("Response Time :: [{}] milliseconds", resp.getTimeIn(TimeUnit.MILLISECONDS));
		logger.info("Response :: {}", resp.getBody().asString());
	}

	@When("user makes {string} request to endpoint with path params {string} as {string} with {string} token")
	public void user_makes_request_to_endpoint_with_path_params_as_with_token(String req, String param, String gistId, String token) {

		// If value=CreatedAbove then it means global gist ID will be used, which
		// obtained from create request earlier
		// If value=WrongGist then it will add invalid character to corrupt to created
		// gist
		if (gistId.contains(Constants.GIST_TYPE_CREATED_ABOVE.getConstant())) {
			gistId = gistID;
			logger.info("Gist ID being used is one created already [{}]", gistID);
		} else if (gistId.contains(Constants.GIST_TYPE_WRONG_GIST.getConstant())) { // WHen WrongGist keyword is found, it will add invalid character to corrupt to
																					// // created gist
			logger.info("Gist ID being used is wrong one intentionally [{}]", gistID);
			gistId = gistID + "xxxxxxxxxxxxxxxxxxx";
		}

		if (token.equalsIgnoreCase(Constants.AUTH_TYPE_AUTH.getConstant())) {
			token = OAUTH_TOKEN;
			logger.info("Token being used [{}]", token);
		} else if (token.equalsIgnoreCase(Constants.AUTH_TYPE_WRONGAUTH.getConstant())) {
			token = OAUTH_TOKEN + "6666666666666666";
			logger.info("Wrong token being used intentionally [{}]", token);
		} else {
			token = "";
			logger.info("No token will be provided with rest method");
		}

		Map<String, String> paramData = new HashMap<>();
		paramData.put(param, gistId);

		switch (req) {
		case POST:
			resp = restEx.postOpsWithBodyAndPathParamsAndToken(endPath, paramData, body, token);
			break;
		case GET:
			resp = restEx.getWithPathParamsAndToken(endPath, paramData, token);
			break;
		case DELETE:
			resp = restEx.deleteOpsWithPathParamsAndToken(endPath, paramData, token);
			break;
		default:
			logger.error("Please provide proper REST method type, it should be [GET] or [POST] or [PUT] or [DELETE]");
			assertEquals("Please provide proper REST method type, it should be [GET] or [POST] or [PUT] or [DELETE]", null, req);
		}

		logger.info("Headers :: [{}]", resp.getHeaders());
		logger.info("Cookies :: [{}]", resp.getCookies());
		logger.info("Status Code :: [{}]", resp.getStatusCode());
		logger.info("Status Line :: [{}]", resp.getStatusLine());
		logger.info("Session ID :: [{}]", resp.getSessionId());
		logger.info("Response Time :: [{}] milliseconds", resp.getTimeIn(TimeUnit.MILLISECONDS));
		logger.info("Response :: {}", resp.getBody().asString());

	}

	@Then("user validates response code as {int}")
	public void user_validates_response_code_as(int code) {
		// assertEquals(resp.getStatusCode(), code, "Status code validation failed");
		assertEquals(resp.getStatusCode(), code);
		logger.info("Successfully verified status code as [{}]", code);
	}

	@Then("user validates JSON schema for request for Status code {int} for create gist API")
	public void user_validates_json_schema_for_request_for_status_code_for_create_gist_api(Integer code) {
		JsonSchemaValidator validator = null;

		switch (code) {
		case 201:
			validator = JsonSchemaValidator.matchesJsonSchemaInClasspath("CreateGistSchema.json");
			shouldValidateResponse = true;
			break;
		case 422:
			validator = JsonSchemaValidator.matchesJsonSchemaInClasspath("MissingFieldCreateGist.json");
			break;
		case 404:
			validator = JsonSchemaValidator.matchesJsonSchemaInClasspath("ResourceNotFound.json");
			break;
		default:
			logger.error("Please provide proper expected status code type");
			assertEquals("Please provide proper expected status code type", null, code);
		}

		if (resp.getBody().asString() != "") {
			boolean result = validator.matches(resp.body().asString());
			assertEquals("Failed to validate JSON schema", true, result);
			logger.info("Successfully validated JSON schema");
		}
	}

	@Then("user validates data received in json response for create gist request")
	public void user_validates_data_received_in_json_response_for_create_gist_request() {

		if (shouldValidateResponse) {

			if (resp.getBody().jsonPath().get("id") != null) {
				gistCreated = true;
				gistID = resp.getBody().jsonPath().get("id");
			}

			Boolean publicVal = resp.getBody().jsonPath().getBoolean(PUBLIC);
			logger.info("Found created gist as public [{}]", publicVal);
			assertEquals("Failed to validate gits public type", publicVal, isPublic);

			gistID = resp.getBody().jsonPath().get("id");

			logger.info("Validating response body for filename and their content");
			for (Entry<String, String> file : fileNContent.entrySet()) {
				String fileName = "files.'" + file.getKey() + "'.filename";
				String contentPath = "files.'" + file.getKey() + "'.content";
				assertEquals(resp.getBody().jsonPath().getString(fileName).toString(), file.getKey());
				assertEquals(resp.getBody().jsonPath().get(contentPath).toString(), file.getValue());
			}
		}

	}

	@Then("user validates gist type as {string}")
	public void user_validates_gist_type_as(String gistType) {

		if (shouldValidateResponse) {
			switch (gistType) {
			case PRIVATE:
				isPublic = false;
				break;
			case PUBLIC:
				isPublic = true;
				break;
			case "":
				isPublic = true;
				logger.info("No gist type provided, therefore Gist type will be created as public:true");
				break;
			default:
				logger.error("Please provide proper gist access type, it should be [public] or [private]");
			}
			Boolean publicVal = resp.getBody().jsonPath().getBoolean(PUBLIC);
			logger.info("Found created gist as public [{}]", publicVal);
			assertEquals("Please check for Gist type", publicVal, isPublic);
		}
	}

	@Then("user validates JSON schema for request for Status code {int} for read gist API")
	public void user_validates_json_schema_for_request_for_status_code_for_read_gist_api(Integer code) {
		JsonSchemaValidator validator = null;

		switch (code) {
		case 200:
			validator = JsonSchemaValidator.matchesJsonSchemaInClasspath("CreateGistSchema.json");
			shouldValidateResponse = true;
			break;
		case 404:
			validator = JsonSchemaValidator.matchesJsonSchemaInClasspath("ResourceNotFound.json");
			break;
		default:
			logger.error("Please provide proper expected status code type");
			assertEquals("Please provide proper expected status code type", null, code);
		}

		if (resp.getBody().asString() != "") {
			boolean result = validator.matches(resp.body().asString());
			assertEquals("Failed to validate JSON schema", true, result);
			logger.info("Successfully validated JSON schema");
		}
	}

	@Then("user validates data received in json response for read gist request with {string}")
	public void user_validates_data_received_in_json_response_for_read_gist_request_with(String gistID) {
		if (shouldValidateResponse) {
			logger.info("Validating gist ID for requested and received response data");
			assertEquals(resp.getBody().jsonPath().get("id"), gistID);
		}
	}

	@Then("user validates data received in json response for listing user as {string}")
	public void user_validates_data_received_in_json_response_for_listing_user_as(String username) {
		if (shouldValidateResponse) {
			logger.info("Validating gist ID for requested and received response data");
			assertEquals(resp.getBody().jsonPath().get("[0].owner.login"), username);
		}
	}

	@Then("user validates JSON schema for request for Status code {int} for list user gists")
	public void user_validates_json_schema_for_request_for_status_code_for_list_user_gists(Integer code) {
		JsonSchemaValidator validator = null;

		switch (code) {
		case 200:
			validator = JsonSchemaValidator.matchesJsonSchemaInClasspath("ListUserGistsSchema.json");
			shouldValidateResponse = true;
			break;
		case 404:
			validator = JsonSchemaValidator.matchesJsonSchemaInClasspath("ResourceNotFound.json");
			break;
		default:
			logger.error("Please provide proper expected status code type");
			assertEquals("Please provide proper expected status code type", null, code);
		}

		if (resp.getBody().asString() != "") {
			boolean result = validator.matches(resp.body().asString());
			assertEquals("Failed to validate JSON schema", true, result);
			logger.info("Successfully validated JSON schema");
		}
	}

	@Then("user adds query param {string} as {string} to endpoint path")
	public void user_adds_query_param_as_to_endpoint_path(String key, String value) {
		logger.info("Adding query parameter [{}] : [{}]", key, value);
		queryData.put(key, value);
	}

	@When("user makes {string} request to endpoint with query params with {string} token")
	public void user_makes_request_to_endpoint_with_query_params_with_token(String req, String token) {

		if (token.equalsIgnoreCase(Constants.AUTH_TYPE_AUTH.getConstant())) {
			token = OAUTH_TOKEN;
		} else if (token.equalsIgnoreCase(Constants.AUTH_TYPE_WRONGAUTH.getConstant())) {
			token = OAUTH_TOKEN + "000000000000000000000";
		} else {
			token = "";
		}

		switch (req) {
		case GET:
			resp = restEx.getWithQueryParamsWithToken(endPath, queryData, token);
			break;
		// More methods to be added as per need
		default:
			logger.error("Please provide proper REST method type, it should be [GET]");
			assertEquals("Please provide proper REST method type, it should be [GET]", null, req);
		}

		logger.info("Headers :: [{}]", resp.getHeaders());
		logger.info("Cookies :: [{}]", resp.getCookies());
		logger.info("Status Code :: [{}]", resp.getStatusCode());
		logger.info("Status Line :: [{}]", resp.getStatusLine());
		logger.info("Session ID :: [{}]", resp.getSessionId());
		logger.info("Response Time :: [{}] milliseconds", resp.getTimeIn(TimeUnit.MILLISECONDS));
		logger.info("Response :: {}", resp.getBody().asString());

	}

	@Then("user validates JSON schema for request for Status code {int} for list authenticated user gists")
	public void user_validates_json_schema_for_request_for_status_code_for_list_authenticated_user_gists(Integer code) {
		JsonSchemaValidator validator = null;

		switch (code) {
		case 200:
			validator = JsonSchemaValidator.matchesJsonSchemaInClasspath("ListUserGistsSchema.json");
			shouldValidateResponse = true;
			break;
		case 422:
			validator = JsonSchemaValidator.matchesJsonSchemaInClasspath("ResourceNotFound.json");
			break;
		case 404:
			validator = JsonSchemaValidator.matchesJsonSchemaInClasspath("ResourceNotFound.json");
			break;
		default:
			logger.error("Please provide proper expected status code type");
			assertEquals("Please provide proper expected status code type", null, code);
		}

		if (resp.getBody().asString() != "") {
			boolean result = validator.matches(resp.body().asString());
			// assertEquals(result, true, "Failed to validate JSON schema");
			assertEquals(true, result);
			logger.info("Successfully validated JSON schema");
		}

	}

	@Then("user validates data received in json response for listing authenticated user gists")
	public void user_validates_data_received_in_json_response_for_listing_authenticated_user_gists() {
		try {
			if (shouldValidateResponse) {
				ObjectMapper mapper = new ObjectMapper();
				ListUserGistResponse[] user = mapper.readValue(resp.getBody().asString(), ListUserGistResponse[].class);
				assertEquals("Failed to validate gists per page", user.length, Integer.parseInt(queryData.get(Constants.PER_PAGE.getConstant())));
				logger.info("Validated number of gists per page");
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
		}

	}

	@Then("user validates JSON schema for request for Status code {int} for rate limit stats")
	public void user_validates_json_schema_for_request_for_status_code_for_rate_limit_stats(Integer code) {
		JsonSchemaValidator validator = null;

		switch (code) {
		case 200:
			validator = JsonSchemaValidator.matchesJsonSchemaInClasspath("RateLimitResponseSchema.json");
			shouldValidateResponse = true;
			break;
		case 401:
			validator = JsonSchemaValidator.matchesJsonSchemaInClasspath("ResourceNotFound.json");
			break;
		case 404:
			validator = JsonSchemaValidator.matchesJsonSchemaInClasspath("ResourceNotFound.json");
			break;
		default:
			logger.error("Please provide proper expected status code type");
			assertEquals("Please provide proper expected status code type", null, code);
		}

		if (resp.getBody().asString() != "") {
			boolean result = validator.matches(resp.body().asString());
			assertEquals("Failed to validate JSON schema", true, result);
			logger.info("Successfully validated JSON schema");

		}
	}

	@Then("user validates rate limit statistics")
	public void user_validates_rate_limit_statistics() {
		if (shouldValidateResponse) {
			int currentRemainingLimit = resp.getBody().jsonPath().getInt("rate.remaining");
			int currentUsedLimit = resp.getBody().jsonPath().getInt("rate.used");

			assertEquals("Failed to validates current rate remaining quota", rateLimit.get(Constants.RATE_LIMIT_REMAINING.getConstant()) - 1, currentRemainingLimit);
			assertEquals("Failed to validates current rate used quota", rateLimit.get(Constants.RATE_LIMIT_USED.getConstant()) + 1, currentUsedLimit);

			logger.info("Successfully validated Rate Remaining and Used limit");
		}
	}

	@Then("user captures rate limit statistics for comparison")
	public void user_captures_rate_limit_statistics_for_comparison() {
		if (shouldValidateResponse) {
			rateLimit.put(Constants.RATE_LIMIT_LIMIT.getConstant(), resp.getBody().jsonPath().getInt("rate.limit"));
			rateLimit.put(Constants.RATE_LIMIT_REMAINING.getConstant(), resp.getBody().jsonPath().getInt("rate.remaining"));
			rateLimit.put(Constants.RATE_LIMIT_RESET.getConstant(), resp.getBody().jsonPath().getInt("rate.reset"));
			rateLimit.put(Constants.RATE_LIMIT_USED.getConstant(), resp.getBody().jsonPath().getInt("rate.used"));
			logger.info("Rate Limit data :: Limit [{}], Remaining [{}] Used [{}] Reset [{}]", rateLimit.get(Constants.RATE_LIMIT_LIMIT.getConstant()),
					rateLimit.get(Constants.RATE_LIMIT_REMAINING.getConstant()), rateLimit.get(Constants.RATE_LIMIT_RESET.getConstant()),
					rateLimit.get(Constants.RATE_LIMIT_USED.getConstant()));
		}
	}

	@Then("user validates accessibility for gist when gist type is {string} provided Username as {string}")
	public void user_validates_accessibility_for_gist_when_gist_type_is_provided_username_as(String gistType, String user) {

		Map<String, String> userData = new HashMap<>();
		userData.put(Constants.PATH_PARAM_USERNAME.getConstant(), user);

		if (shouldValidateResponse) {
			ObjectMapper mapper = new ObjectMapper();
			ListUserGistResponse[] gistsWithAuth;
			ListUserGistResponse[] gistsWithoutAuth;
			gistCreated=false;

			try {

				ResponseOptions<Response> respWithAuth = restEx.getWithPathParamsAndToken(endPath, userData, OAUTH_TOKEN);
				logger.info("Printing response data when auth token is used");
				logger.info("Headers :: [{}]", respWithAuth.getHeaders());
				logger.info("Cookies :: [{}]", respWithAuth.getCookies());
				logger.info("Status Code :: [{}]", respWithAuth.getStatusCode());
				logger.info("Status Line :: [{}]", respWithAuth.getStatusLine());
				logger.info("Session ID :: [{}]", respWithAuth.getSessionId());
				logger.info("Response Time :: [{}] milliseconds", respWithAuth.getTimeIn(TimeUnit.MILLISECONDS));
				logger.info("Response :: {}", respWithAuth.getBody().asString());

				ResponseOptions<Response> respWithoutAuth = restEx.getWithPathParams(endPath, userData);
				logger.info("Printing response data when auth token is not used");
				logger.info("Headers :: [{}]", respWithoutAuth.getHeaders());
				logger.info("Cookies :: [{}]", respWithoutAuth.getCookies());
				logger.info("Status Code :: [{}]", respWithoutAuth.getStatusCode());
				logger.info("Status Line :: [{}]", respWithoutAuth.getStatusLine());
				logger.info("Session ID :: [{}]", respWithoutAuth.getSessionId());
				logger.info("Response Time :: [{}] milliseconds", respWithoutAuth.getTimeIn(TimeUnit.MILLISECONDS));
				logger.info("Response :: {}", respWithoutAuth.getBody().asString());

				gistsWithAuth = mapper.readValue(respWithAuth.getBody().asString(), ListUserGistResponse[].class);
				gistsWithoutAuth = mapper.readValue(respWithoutAuth.getBody().asString(), ListUserGistResponse[].class);

				if (gistType.equalsIgnoreCase(Constants.GIST_TYPE_PRIVATE.getConstant())) {
					logger.info("Validating accessibility for gist when its type is private");
					assertEquals(gistsWithAuth[0].getId(), gistID);
					assertEquals(false, gistsWithAuth[0].getPublic());

				} else if (gistType.equalsIgnoreCase(Constants.GIST_TYPE_PUBLIC.getConstant())) {
					logger.info("Validating accessibility for gist when its type is public");
					assertEquals(gistsWithAuth[0].getId(), gistID);
					assertEquals(true, gistsWithAuth[0].getPublic());
					assertEquals(gistsWithoutAuth[0].getId(), gistID);
					assertEquals(true, gistsWithoutAuth[0].getPublic());
				}
				logger.info("Successfully validated accessibility of gist");

			} catch (Exception e) {
				e.printStackTrace();
				logger.error(e.getMessage());
			}

		}
	}

}
