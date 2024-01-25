package com.poc.techvoice.articleservice.application.enums;

public enum ResponseEnum {

    SUCCESS("00", "Success", "Success"),
    REQUEST_VALIDATION_ERROR("01", "Bad Request", "Incorrect Input"),
    INVALID_USER("03", "The email address does not exist", "Invalid email address"),
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
