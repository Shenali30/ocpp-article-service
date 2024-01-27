package com.poc.techvoice.articleservice.application.enums;

public enum ResponseEnum {

    SUCCESS("00", "Success", "Success"),
    REQUEST_VALIDATION_ERROR("01", "Bad Request", "Incorrect Input"),
    INVALID_USER("03", "The email address does not exist", "Invalid email address"),
    NO_PERMISSION_TO_PUBLISH("06", "User does not have permission to publish", "Please register as a Writer to publish articles"),
    INVALID_CATEGORY("07", "Invalid category", "Please select a valid category"),
    INVALID_ARTICLE("08", "Invalid article", "This article does not exist or you do not have permission to modify the article"),
    NO_ARTICLE_DATA("09", "No article data found", "Articles are not available."),
    NO_CATEGORY_DATA("10", "No category data found", "Categories are not available"),
    NO_CATEGORY_USER("11", "No category or user found", "The user or category is not available"),
    ACCESS_DENIED("12", "Invalid Token. Access Denied", "Unauthorized"),
    TOKEN_EXPIRED("98", "Token has expired", "An Error Occurred. Please Contact Administrator"),
    INTERNAL_ERROR("99", "Internal Error", "An Error Occurred. Please Contact Administrator");

    private final String code;
    private final String desc;
    private final String displayDesc;

    ResponseEnum(String code, String desc, String displayDesc) {
        this.code = code;
        this.desc = desc;
        this.displayDesc = displayDesc;
    }

    public String getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

    public String getDisplayDesc() {
        return displayDesc;
    }
}
