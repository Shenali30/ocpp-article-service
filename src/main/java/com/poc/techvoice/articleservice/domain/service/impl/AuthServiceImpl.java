package com.poc.techvoice.articleservice.domain.service.impl;

import com.poc.techvoice.articleservice.application.constants.AppConstants;
import com.poc.techvoice.articleservice.application.constants.LoggingConstants;
import com.poc.techvoice.articleservice.application.enums.ResponseEnum;
import com.poc.techvoice.articleservice.application.exception.type.ServerException;
import com.poc.techvoice.articleservice.domain.entities.User;
import com.poc.techvoice.articleservice.domain.enums.TokenType;
import com.poc.techvoice.articleservice.domain.service.AuthService;
import com.poc.techvoice.articleservice.domain.service.JwtService;
import com.poc.techvoice.articleservice.domain.util.UtilityService;
import com.poc.techvoice.articleservice.external.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Objects;

@Slf4j
@Service
@AllArgsConstructor
public class AuthServiceImpl extends UtilityService implements AuthService {

    private final JwtService jwtService;
    private final UserRepository userRepository;


    @Override
    public boolean isTokenValid(String token, TokenType tokenType, Map<String, User> tokenUserData) throws ServerException {

        try {
            log.info(LoggingConstants.TOKEN_LOG, AppConstants.VALIDATING_TOKEN, tokenType.name());
            Map<String, Object> claimsMap = jwtService.getAllClaimsFromTokenAsMap(token);

            String userEmail = (String) claimsMap.get(AppConstants.EMAIL);
            String sessionId = (String) claimsMap.get(AppConstants.ACTIVE_SESSION_ID);
            User userToAuthenticate = userRepository.findByEmail(userEmail);

            if (Objects.nonNull(userToAuthenticate) && Objects.nonNull(userToAuthenticate.getActiveSessionId())
                    && userToAuthenticate.getActiveSessionId().equals(sessionId)) {

                if (!jwtService.isTokenExpired(token)) {
                    if (TokenType.REFRESH_TOKEN.equals(tokenType)) {
                        tokenUserData.put(AppConstants.USER_DATA, userToAuthenticate);
                    }
                    return true;

                } else {
                    log.debug(LoggingConstants.TOKEN_LOG, AppConstants.VALIDATING_TOKEN, "Token is expired");
                }

            } else {
                log.debug(LoggingConstants.TOKEN_LOG, AppConstants.VALIDATING_TOKEN, "User not found or session Id is not valid");
            }

        } catch (Exception ex) {
            log.error(LoggingConstants.TOKEN_ERROR, ex.getMessage(), ResponseEnum.INTERNAL_ERROR.getDesc(), ex.getStackTrace());
            return false;
        }

        return false;
    }

}
