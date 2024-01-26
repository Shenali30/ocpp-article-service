package com.poc.techvoice.articleservice.application.constants;

public class LoggingConstants {

    private LoggingConstants() {
    }

    public static final String FULL_REQUEST = "FULL REQUEST | CONTROLLER REQUEST: {}";
    public static final String FULL_RESPONSE = "FULL RESPONSE | CONTROLLER RESPONSE: {}";
    public static final String STARTED = "Started";
    public static final String ENDED = "Ended";


    // edit writer profile
    public static final String EDIT_PROFILE_REQUEST_INITIATED = "EDIT PROFILE REQUEST INITIATED";
    public static final String EDIT_PROFILE_RESPONSE_SENT = "EDIT PROFILE RESPONSE SENT";
    public static final String EDIT_PROFILE_LOG = "EDIT PROFILE LOG | MESSAGE: {} | OPERATION: {}";
    public static final String EDIT_PROFILE_ERROR = "EDIT PROFILE ERROR | MESSAGE: {} | ERROR REASON: {}, ERROR STACKTRACE: {}";


    // publish article
    public static final String PUBLISH_ARTICLE_REQUEST_INITIATED = "PUBLISH ARTICLE REQUEST INITIATED";
    public static final String PUBLISH_ARTICLE_RESPONSE_SENT = "PUBLISH ARTICLE RESPONSE SENT";
    public static final String PUBLISH_ARTICLE_LOG = "PUBLISH ARTICLE LOG | MESSAGE: {} | OPERATION: {}";
    public static final String PUBLISH_ARTICLE_ERROR = "PUBLISH ARTICLE ERROR | MESSAGE: {} | ERROR REASON: {}, ERROR STACKTRACE: {}";


    // update article
    public static final String UPDATE_ARTICLE_REQUEST_INITIATED = "UPDATE ARTICLE REQUEST INITIATED";
    public static final String UPDATE_ARTICLE_RESPONSE_SENT = "UPDATE ARTICLE RESPONSE SENT";
    public static final String UPDATE_ARTICLE_LOG = "UPDATE ARTICLE LOG | MESSAGE: {} | OPERATION: {}";
    public static final String UPDATE_ARTICLE_ERROR = "UPDATE ARTICLE ERROR | MESSAGE: {} | ERROR REASON: {}, ERROR STACKTRACE: {}";


    // delete article
    public static final String DELETE_ARTICLE_REQUEST_INITIATED = "DELETE ARTICLE REQUEST INITIATED";
    public static final String DELETE_ARTICLE_RESPONSE_SENT = "DELETE ARTICLE RESPONSE SENT";
    public static final String DELETE_ARTICLE_LOG = "DELETE ARTICLE LOG | MESSAGE: {} | OPERATION: {}";
    public static final String DELETE_ARTICLE_ERROR = "DELETE ARTICLE ERROR | MESSAGE: {} | ERROR REASON: {}, ERROR STACKTRACE: {}";
}
