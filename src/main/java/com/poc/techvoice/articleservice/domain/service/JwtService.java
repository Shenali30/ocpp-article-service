package com.poc.techvoice.articleservice.domain.service;

import java.util.Map;

public interface JwtService {

    Map<String, Object> getAllClaimsFromTokenAsMap(String token);

    boolean isTokenExpired(String authToken);
}
