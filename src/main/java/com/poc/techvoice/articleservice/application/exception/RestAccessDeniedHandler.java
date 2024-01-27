package com.poc.techvoice.articleservice.application.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.poc.techvoice.articleservice.application.constants.AppConstants;
import com.poc.techvoice.articleservice.application.enums.ResponseEnum;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Component
public class RestAccessDeniedHandler implements AccessDeniedHandler {

    ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response,
                       AccessDeniedException accessDeniedException) throws IOException {

        Map<String, Object> responseBody = new HashMap<>();
        Map<String, Object> responseHeader = new HashMap<>();

        responseHeader.put(AppConstants.TIMESTAMP, LocalDateTime.now().toString());
        responseHeader.put(AppConstants.RESPONSE_DESCRIPTION, ResponseEnum.ACCESS_DENIED.getDesc());
        responseHeader.put(AppConstants.DISPLAY_DESCRIPTION, ResponseEnum.ACCESS_DENIED.getDisplayDesc());
        responseHeader.put(AppConstants.RESPONSE_CODE, ResponseEnum.ACCESS_DENIED.getCode());

        responseBody.put(AppConstants.RESPONSE_HEADER, responseHeader);

        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        OutputStream out = response.getOutputStream();
        objectMapper.writeValue(out, responseBody);
        out.flush();
    }

}
