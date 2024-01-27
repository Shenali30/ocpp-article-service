package com.poc.techvoice.articleservice.domain.service.impl;

import com.poc.techvoice.articleservice.application.constants.AppConstants;
import com.poc.techvoice.articleservice.application.constants.LoggingConstants;
import com.poc.techvoice.articleservice.domain.service.JwtService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Slf4j
@Service
public class JwtServiceImpl implements JwtService {

    @Value("${security.jwt.sign-key}")
    private String jwtSignKey;


    @Override
    public Map<String, Object> getAllClaimsFromTokenAsMap(String token) {

        log.debug(LoggingConstants.TOKEN_LOG, "Extracting all claims", null);
        Claims allClaims = getAllClaims(token);
        return new HashMap<>(allClaims);
    }

    @Override
    public boolean isTokenExpired(String authToken) {

        if (authToken.contains(AppConstants.BEARER)) {
            authToken = authToken.substring(7);
        }

        Date expirationDateTime = getExpirationDateFromToken(authToken);
        return expirationDateTime.before(new Date());
    }

    private Date getExpirationDateFromToken(String token) {
        return getClaim(token, Claims::getExpiration);
    }

    private <T> T getClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims getAllClaims(String token) {
        return Jwts.parser().setSigningKey(jwtSignKey).parseClaimsJws(token).getBody();
    }
}
