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
}
