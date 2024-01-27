package com.poc.techvoice.articleservice.application.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.poc.techvoice.articleservice.application.constants.AppConstants;
import com.poc.techvoice.articleservice.application.constants.LoggingConstants;
import com.poc.techvoice.articleservice.application.enums.ResponseEnum;
import com.poc.techvoice.articleservice.domain.entities.dto.response.ResponseHeader;
import com.poc.techvoice.articleservice.domain.service.JwtService;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Slf4j
@Component
public class RestAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final JwtService jwtService;

    ObjectMapper objectMapper = new ObjectMapper();

    public RestAuthenticationEntryPoint(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException, ServletException {

        Map<String, Object> responseBody = new HashMap<>();
        ResponseHeader responseHeader = ResponseHeader.builder().build();
        responseHeader.setDisplayDesc(ResponseEnum.ACCESS_DENIED.getDisplayDesc());
        responseHeader.setResponseCode(ResponseEnum.ACCESS_DENIED.getCode());

        String token = request.getHeader(AppConstants.AUTHORIZATION);

        try {
            if (Objects.isNull(token)) {
                log.debug(LoggingConstants.TOKEN_ERROR, AppConstants.VALIDATING_TOKEN, "Token is null", null);
                responseHeader.setResponseDesc(ResponseEnum.ACCESS_DENIED.getDesc());

            } else if (!token.contains(AppConstants.BEARER)) {
                log.debug(LoggingConstants.TOKEN_ERROR, AppConstants.VALIDATING_TOKEN, "Token does not start with Bearer", null);
                responseHeader.setResponseDesc(ResponseEnum.ACCESS_DENIED.getDesc());

            } else if (jwtService.isTokenExpired(token)) {
                log.debug(LoggingConstants.TOKEN_ERROR, AppConstants.VALIDATING_TOKEN, "Token is expired", null);
                responseHeader.setResponseDesc(ResponseEnum.ACCESS_DENIED.getDesc());

            } else {
                log.debug(LoggingConstants.TOKEN_ERROR, AppConstants.VALIDATING_TOKEN, authException.getMessage(), null);
                responseHeader.setResponseDesc(authException.getMessage());
            }

        } catch (ExpiredJwtException ex) {
            log.debug(LoggingConstants.TOKEN_ERROR, AppConstants.VALIDATING_TOKEN, "Token is expired", ex.getStackTrace());
            responseHeader.setResponseDesc(ResponseEnum.ACCESS_DENIED.getDesc());

        } catch (Exception ex) {
            log.debug(LoggingConstants.TOKEN_ERROR, AppConstants.VALIDATING_TOKEN, ex.getMessage(), ex.getStackTrace());
            responseHeader.setResponseDesc(ResponseEnum.ACCESS_DENIED.getDesc());
        }

        responseHeader.setTimestamp(LocalDateTime.now().toString());
        responseBody.put(AppConstants.RESPONSE_HEADER, responseHeader);

        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setHeader(AppConstants.CONTENT_TYPE, MediaType.APPLICATION_PROBLEM_JSON_VALUE);

        OutputStream out = response.getOutputStream();
        objectMapper.writeValue(out, responseBody);
        out.flush();

    }

}
