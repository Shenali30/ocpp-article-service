package com.poc.techvoice.articleservice.application.exception;

import com.poc.techvoice.articleservice.application.constants.AppConstants;
import com.poc.techvoice.articleservice.application.enums.ResponseEnum;
import com.poc.techvoice.articleservice.application.exception.type.BaseException;
import com.poc.techvoice.articleservice.application.exception.type.ServerException;
import com.poc.techvoice.articleservice.application.exception.type.ValidationException;
import com.poc.techvoice.articleservice.application.validator.RequestEntityInterface;
import com.poc.techvoice.articleservice.domain.exception.DomainException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.support.MissingServletRequestPartException;

import javax.validation.ConstraintViolation;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

@ControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(DomainException.class)
    public ResponseEntity<Map<String, Object>> handleDomainException(DomainException ex) {
        Map<String, Object> responseBody = getErrorResponseBodyForCustomExceptions(ex);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseBody);
    }

    @ExceptionHandler(ServerException.class)
    public ResponseEntity<Map<String, Object>> handleServerException(ServerException ex) {
        Map<String, Object> responseBody = getErrorResponseBodyForCustomExceptions(ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseBody);
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<Map<String, Object>> handleValidationException(ValidationException ex) {
        Map<String, Object> responseBody = getErrorResponseBodyForRuntimeValidationExceptions(ex);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseBody);
    }

    @ExceptionHandler({MissingServletRequestParameterException.class, MissingRequestHeaderException.class,
            MissingServletRequestPartException.class})
    public ResponseEntity<Map<String, Object>> handleMissingServletRequestParameterException(Exception ex) {
        Map<String, Object> responseBody = getErrorResponseBodyForMissingRequestParams(ex);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseBody);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleGenericException(Exception ex) {
        Map<String, Object> responseBody = getErrorResponseBodyForGenericExceptions(ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseBody);
    }

    private Map<String, Object> getErrorResponseBodyForCustomExceptions(BaseException ex) {
        Map<String, Object> responseBody = new HashMap<>();
        Map<String, Object> responseHeader = new HashMap<>();
        responseHeader.put(AppConstants.TIMESTAMP, LocalDateTime.now().toString());
        responseHeader.put(AppConstants.RESPONSE_DESCRIPTION, ex.getMessage());
        responseHeader.put(AppConstants.DISPLAY_DESCRIPTION, ex.getDisplayMsg());
        responseHeader.put(AppConstants.RESPONSE_CODE, ex.getCode());

        responseBody.put(AppConstants.RESPONSE_HEADER, responseHeader);
        return responseBody;
    }

    private Map<String, Object> getErrorResponseBodyForMissingRequestParams(Exception ex) {
        Map<String, Object> responseBody = new HashMap<>();
        Map<String, Object> responseHeader = new HashMap<>();
        responseHeader.put(AppConstants.TIMESTAMP, LocalDateTime.now().toString());
        responseHeader.put(AppConstants.RESPONSE_DESCRIPTION, ex.getMessage());
        responseHeader.put(AppConstants.DISPLAY_DESCRIPTION, ResponseEnum.REQUEST_VALIDATION_ERROR.getDisplayDesc());
        responseHeader.put(AppConstants.RESPONSE_CODE, ResponseEnum.REQUEST_VALIDATION_ERROR.getCode());

        responseBody.put(AppConstants.RESPONSE_HEADER, responseHeader);
        return responseBody;
    }

    private Map<String, Object> getErrorResponseBodyForRuntimeValidationExceptions(ValidationException ex) {
        Map<String, Object> responseBody = new HashMap<>();
        Map<String, Object> responseHeader = new HashMap<>();
        responseHeader.put(AppConstants.TIMESTAMP, LocalDateTime.now().toString());
        responseHeader.put(AppConstants.RESPONSE_DESCRIPTION, formatValidationErrors(ex.getErrors()));
        responseHeader.put(AppConstants.DISPLAY_DESCRIPTION, ResponseEnum.REQUEST_VALIDATION_ERROR.getDisplayDesc());
        responseHeader.put(AppConstants.RESPONSE_CODE, ResponseEnum.REQUEST_VALIDATION_ERROR.getCode());

        responseBody.put(AppConstants.RESPONSE_HEADER, responseHeader);
        return responseBody;
    }

    private Map<String, Object> getErrorResponseBodyForGenericExceptions(Exception ex) {
        Map<String, Object> responseBody = new HashMap<>();
        Map<String, Object> responseHeader = new HashMap<>();
        responseHeader.put(AppConstants.TIMESTAMP, LocalDateTime.now().toString());
        responseHeader.put(AppConstants.RESPONSE_DESCRIPTION, ex.getMessage());
        responseHeader.put(AppConstants.DISPLAY_DESCRIPTION, ResponseEnum.INTERNAL_ERROR.getDisplayDesc());
        responseHeader.put(AppConstants.RESPONSE_CODE, ResponseEnum.INTERNAL_ERROR.getCode());

        responseBody.put(AppConstants.RESPONSE_HEADER, responseHeader);
        return responseBody;
    }

    private String formatValidationErrors(Set<ConstraintViolation<RequestEntityInterface>> errors) {

        Map<String, String> errDetailsMap = new LinkedHashMap<>();

        errors.forEach(error -> {
            String key = error.getPropertyPath().toString();
            String val = error.getMessage();
            errDetailsMap.put(key, val);
        });

        StringBuilder errorMessages = new StringBuilder();
        for (Map.Entry<String, String> e : errDetailsMap.entrySet()) {
            errorMessages.append(e.getKey()).append(": ").append(e.getValue()).append(", ");
        }

        errorMessages.delete(errorMessages.length() - 2, errorMessages.length());
        return String.valueOf(errorMessages);
    }

}