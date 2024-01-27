package com.poc.techvoice.articleservice.domain.util;

import com.poc.techvoice.articleservice.application.enums.ResponseEnum;
import com.poc.techvoice.articleservice.domain.entities.dto.response.BaseResponse;
import com.poc.techvoice.articleservice.domain.entities.dto.response.ResponseHeader;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class UtilityService {

    private static final Map<Long, String> ORDINAL_MAP;

    static {
        ORDINAL_MAP = new HashMap<>();
        ORDINAL_MAP.put(1L, "st");
        ORDINAL_MAP.put(2L, "nd");
        ORDINAL_MAP.put(3L, "rd");
        ORDINAL_MAP.put(21L, "st");
        ORDINAL_MAP.put(22L, "nd");
        ORDINAL_MAP.put(23L, "rd");
        ORDINAL_MAP.put(31L, "st");

        for (long i = 4; i <= 30; i++) {
            if (!ORDINAL_MAP.containsKey(i)) {
                ORDINAL_MAP.put(i, "th");
            }
        }
    }

    protected ResponseHeader getSuccessResponseHeader(String successDisplayMessage) {

        return ResponseHeader.builder()
                .responseCode(ResponseEnum.SUCCESS.getCode())
                .responseDesc(ResponseEnum.SUCCESS.getDesc())
                .timestamp(LocalDateTime.now().toString())
                .displayDesc(successDisplayMessage)
                .build();
    }

    protected BaseResponse getSuccessBaseResponse(String successDisplayMessage) {
        return BaseResponse.builder()
                .responseHeader(getSuccessResponseHeader(successDisplayMessage))
                .build();
    }

    protected String convertDateToDescriptiveString(LocalDateTime dateTime) {

        if (Objects.nonNull(dateTime)) {
            DateTimeFormatter formatter = new DateTimeFormatterBuilder()
                    .appendPattern("d")
                    .appendText(ChronoField.DAY_OF_MONTH, ORDINAL_MAP)
                    .appendPattern(" MMMM, uuuu")
                    .toFormatter();

            return dateTime.format(formatter);
        } else {
            return null;
        }

    }
}
