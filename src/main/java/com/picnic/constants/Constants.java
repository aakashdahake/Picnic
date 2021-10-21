package com.picnic.constants;


public enum Constants {
	
	GIST_TYPE_PUBLIC("public"),
	GIST_TYPE_PRIVATE("private"),
	TEST_FILE("Test_File_"),
	HEADER_DESCRIPTION("description"),
	HEADER_CONTENT("content"),
	PATH_PARAM_USERNAME("username"),
	GIST_TYPE_CREATED_ABOVE("CreatedAbove"),
	GIST_TYPE_WRONG_GIST("WrongGist"),
	PER_PAGE("per_page"),
	AUTH_TYPE_AUTH("auth"),
	AUTH_TYPE_WRONGAUTH("wrong_auth"),
	RATE_LIMIT_LIMIT("limit"),
    RATE_LIMIT_REMAINING("remaining"),
    RATE_LIMIT_RESET("reset"),
    RATE_LIMIT_USED("used"),
	ENDPOINT_PATH_GISTID("/gists/{gist_Id}")
	
	;
	
	
	
	public final String constant;
	Constants(String constant) {
		this.constant = constant;
	}
	
	public String getConstant() 
    { 
        return this.constant; 
    } 
	
	


	
}
