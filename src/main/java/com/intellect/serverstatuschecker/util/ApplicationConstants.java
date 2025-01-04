package com.intellect.serverstatuschecker.util;

import java.io.IOException;
import java.util.Properties;

import com.intellect.serverstatuschecker.ServerstatuscheckerApp;

public class ApplicationConstants {
	static Properties prop = new Properties();
	static {
		try {
			prop.load(ServerstatuscheckerApp.class.getClassLoader().getResourceAsStream("constant.properties"));
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
	
	static Properties properties = new Properties();

	static {
	    try {
	        properties.load(ServerstatuscheckerApp.class.getClassLoader()
	            .getResourceAsStream("config/application-" + "dev" + ".properties"));
	            
//	                    .getResourceAsStream("config/application-"
//	                            + System.getProperty(AbstractEnvironment.ACTIVE_PROFILES_PROPERTY_NAME) + ".properties"));
	        } catch (IOException ex) {
	        }
	    }
	
	public static final String RES_STATUS_ERROR = prop.getProperty("RES_STATUS_ERROR");
	
	public static final String BAD_REQUEST = prop.getProperty("BAD_REQUEST");
	
	public static final String CREATE_RECORD_FAILED = prop.getProperty("CREATE_RECORD_FAILED");
	
	public static final String SERVER_ERROR = prop.getProperty("SERVER_ERROR");
	
	public static final String RES_STATUS_SUCCESS = prop.getProperty("RES_STATUS_SUCCESS");
	
	public static final String SERVER_DETAILS = prop.getProperty("SERVER_DETAILS");
	
	public static final String CREATE_RECORD_SUCCESS = prop.getProperty("CREATE_RECORD_SUCCESS");
	
	public static final String FETCH_RECORD_FAILED = prop.getProperty("FETCH_RECORD_FAILED");
	
	public static final String FETCH_RECORD_SUCCESS = prop.getProperty("FETCH_RECORD_SUCCESS");
	
	public static final Boolean TRUE = Boolean.parseBoolean(prop.getProperty("TRUE"));
	
	public static final String ACTIVE_STATUS= prop.getProperty("ACTIVE_STATUS");
	
	public static final String IN_ACTIVE_STATUS = prop.getProperty("IN_ACTIVE_STATUS");
	
	public static final String THESE_SERVERS_DOWN = prop.getProperty("THESE_SERVERS_DOWN");
	
	public static final String SERVER_DETAILS_ALREADY_EXISTED = prop.getProperty("SERVER_DETAILS_ALREADY_EXISTED");
	
	public static final Boolean FALSE = Boolean.parseBoolean(prop.getProperty("FALSE"));
	
	public static final Integer ONE = Integer.parseInt(prop.getProperty("ONE"));
	
	public static final String USER_NAME_NOT_FOUND = prop.getProperty("USER_NAME_NOT_FOUND");
	
	public static final String AUTHENTICATION_FAILED = prop.getProperty("AUTHENTICATION_FAILED");
	
	public static final String INVAILD_USERNAME_OR_PASSWORD = prop.getProperty("INVAILD_USERNAME_OR_PASSWORD");
	
	public static final String AN_ERROR_OCCURED_DURING_AUTHENTICATION = prop.getProperty("AN_ERROR_OCCURED_DURING_AUTHENTICATION");
	
	public static final String AUTHICATION_FAILED = prop.getProperty("AUTHICATION_FAILED");
	
	public static final String LOGIN_SUCCESS = prop.getProperty("LOGIN_SUCCESS");
	
	public static final String LOGIN_FAILED = prop.getProperty("LOGIN_FAILED");
	
	public static final String REGISTER_SUCCESS = prop.getProperty("REGISTER_SUCCESS");
	
	public static final String REGISTER_FAILED = prop.getProperty("REGISTER_FAILED");
	
	public static final String DELETED_SUCCESSFULLY = prop.getProperty("DELETED_SUCCESSFULLY");
	
	public static final String USER_EMAIL_IS_ALREADY_EXISTED = prop.getProperty("USER_EMAIL_IS_ALREADY_EXISTED");
}
