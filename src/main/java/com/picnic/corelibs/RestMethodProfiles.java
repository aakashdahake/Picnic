package com.picnic.corelibs;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;

import com.picnic.utils.ConfigManager;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.response.Response;
import io.restassured.response.ResponseOptions;
import io.restassured.specification.RequestSpecification;

/**
 * The Class RestMethodProfiles.
 * Provides CRUD operation profiles for various combinations
 */
public class RestMethodProfiles {

	/** The Constant BASE_URL. */
	private static final String BASE_URL = ConfigManager.getInstance().getString("base_url");

	/** The Request. */
	private RequestSpecification request;

	/**
	 * Instantiates a new rest method profiles.
	 */
	public RestMethodProfiles() {
		RequestSpecBuilder builder = new RequestSpecBuilder();
		builder.setBaseUri(BASE_URL);
		builder.setContentType("application/vnd.github.v3+json");
		RequestSpecification requestSpec = builder.build();
		request = RestAssured.given().spec(requestSpec);
	}

	/**
	 * Gets the operation with path parameter.
	 *
	 * @param url        the url
	 * @param pathParams the path params
	 */
	public void getOpsWithPathParameter(String url, Map<String, String> pathParams) {
		request.pathParams(pathParams);
		try {
			request.get(new URI(url));
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Gets the operation with path parameter and token.
	 *
	 * @param url        the url
	 * @param token      the token
	 * @param pathParams the path params
	 */
	public void getOpsWithPathParameterAndToken(String url, String token, Map<String, String> pathParams) {
		request.pathParams(pathParams);
		request.auth().oauth2(token);
		try {
			request.get(new URI(url));
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Gets the operation.
	 *
	 * @param url the url
	 * @return the response options
	 */
	public ResponseOptions<Response> getWihoutToken(String url) {
		// Act
		try {
			return request.get(new URI(url));
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Gets the operation with token.
	 *
	 * @param url   the url
	 * @param token the token
	 * @return the response options
	 */
	public ResponseOptions<Response> getWithToken(String url, String token) {
		try {
			request.auth().oauth2(token);
			return request.get(new URI(url));
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Gets the with path params.
	 *
	 * @param url        the url
	 * @param pathParams the path params
	 * @return the response options
	 */
	public ResponseOptions<Response> getWithPathParams(String url, Map<String, String> pathParams) {
		request.pathParams(pathParams);
		return request.get(url);
	}

	/**
	 * Gets the with path params and token.
	 *
	 * @param url        the url
	 * @param pathParams the path params
	 * @param token      the token
	 * @return the response options
	 */
	public ResponseOptions<Response> getWithPathParamsAndToken(String url, Map<String, String> pathParams, String token) {
		request.pathParams(pathParams);
		request.auth().oauth2(token);
		return request.get(url);
	}

	/**
	 * Gets the with query params with token.
	 *
	 * @param url         the url
	 * @param queryParams the query params
	 * @param token       the token
	 * @return the response options
	 */
	public ResponseOptions<Response> getWithQueryParamsWithToken(String url, Map<String, String> queryParams, String token) {
		request.auth().oauth2(token);
		request.queryParams(queryParams);
		return request.get(url);
	}

	/**
	 * PUT operation with body and path params.
	 *
	 * @param url        the url
	 * @param body       the body
	 * @param pathParams the path params
	 * @return the response options
	 */
	public ResponseOptions<Response> putOpsWithBodyAndPathParams(String url, Map<String, String> body, Map<String, String> pathParams) {
		request.pathParams(pathParams);
		request.body(body);
		return request.put(url);
	}

	/**
	 * Post operation with body.
	 *
	 * @param url  the url
	 * @param body the body
	 * @return the response options
	 */
	public ResponseOptions<Response> postOpsWithBody(String url, Map<String, String> body) {
		request.body(body);
		return request.post(url);
	}

	/**
	 * Post operation with body and token.
	 *
	 * @param url   the url
	 * @param token the token
	 * @param body  the body
	 * @return the response options
	 */
	public ResponseOptions<Response> postOpsWithBodyAndToken(String url, String token, Map<String, Object> body) {
		request.body(body);
		request.auth().oauth2(token);
		return request.post(url);
	}

	/**
	 * Post operation with body and path params.
	 *
	 * @param url        the url
	 * @param pathParams the path params
	 * @param body       the body
	 * @return the response options
	 */
	public ResponseOptions<Response> postOpsWithBodyAndPathParams(String url, Map<String, String> pathParams, Map<String, String> body) {
		request.pathParams(pathParams);
		request.body(body);
		return request.post(url);
	}

	/**
	 * Post operation with body and path params and token.
	 *
	 * @param url        the url
	 * @param pathParams the path params
	 * @param body       the body
	 * @param tokekn     the tokekn
	 * @return the response options
	 */
	public ResponseOptions<Response> postOpsWithBodyAndPathParamsAndToken(String url, Map<String, String> pathParams, Map<String, Object> body, String tokekn) {
		request.pathParams(pathParams);
		request.auth().oauth2(tokekn);
		request.body(body);
		return request.post(url);
	}

	/**
	 * Delete operation with path params and token.
	 *
	 * @param url        the url
	 * @param pathParams the path params
	 * @param token      the token
	 * @return the response options
	 */
	public ResponseOptions<Response> deleteOpsWithPathParamsAndToken(String url, Map<String, String> pathParams, String token) {
		request.pathParams(pathParams);
		request.auth().oauth2(token);
		return request.delete(url);
	}

}
