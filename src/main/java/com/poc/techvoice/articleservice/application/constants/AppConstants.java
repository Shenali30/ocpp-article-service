package com.poc.techvoice.articleservice.application.constants;

public class AppConstants {

    private AppConstants() {
    }

    public static final String TIMESTAMP = "timestamp";
    public static final String RESPONSE_DESCRIPTION = "responseDesc";
    public static final String DISPLAY_DESCRIPTION = "displayDesc";
    public static final String RESPONSE_CODE = "responseCode";
    public static final String RESPONSE_HEADER = "responseHeader";

    public static final String AUTHORIZATION = "Authorization";
    public static final String BEARER = "Bearer";
    public static final String CONTENT_TYPE = "Content-Type";
    public static final String VALIDATING_TOKEN = "Validating Token";
    public static final String EMAIL = "email";
    public static final String ACTIVE_SESSION_ID = "activeSessionId";
    public static final String USER_DATA = "userData";

    public static final String EMAIL_SUBJECT = "New Update in Tech Voice Article Publishing Platform";

    public static final String EMAIL_BODY = "Dear Tech Voice reader,\n\nThe article titled %s has been %s under %s category" +
            "\n\n(Please note: This is an automatically generated email. Kindly refrain from replying to this message.)";
}
