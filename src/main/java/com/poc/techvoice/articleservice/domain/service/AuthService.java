package com.poc.techvoice.articleservice.domain.service;


import com.poc.techvoice.articleservice.application.exception.type.ServerException;
import com.poc.techvoice.articleservice.domain.entities.User;
import com.poc.techvoice.articleservice.domain.enums.TokenType;

import java.util.Map;

public interface AuthService {

    boolean isTokenValid(String token, TokenType tokenType, Map<String, User> tokenUserData) throws ServerException;
}
