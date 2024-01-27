package com.poc.techvoice.articleservice.application.filter;

import com.poc.techvoice.articleservice.application.constants.AppConstants;
import com.poc.techvoice.articleservice.application.constants.LoggingConstants;
import com.poc.techvoice.articleservice.application.enums.ResponseEnum;
import com.poc.techvoice.articleservice.application.exception.type.ServerException;
import com.poc.techvoice.articleservice.domain.enums.TokenType;
import com.poc.techvoice.articleservice.domain.service.AuthService;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    private final AuthService authService;

    public JwtRequestFilter(AuthService authService) {
        this.authService = authService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String jwtToken;
        UsernamePasswordAuthenticationToken auth;
        List<String> authorities = new ArrayList<>();
        authorities.add("USER");

        final String authorizationHeader = request.getHeader(AppConstants.AUTHORIZATION);

        try {

            if (Objects.nonNull(authorizationHeader) && authorizationHeader.startsWith(AppConstants.BEARER)) {

                jwtToken = authorizationHeader.substring(7);
                log.debug(LoggingConstants.TOKEN_LOG, AppConstants.VALIDATING_TOKEN, "Token received to JWT Filter");

                if (authService.isTokenValid(jwtToken, TokenType.ACCESS_TOKEN, null) &&
                        SecurityContextHolder.getContext().getAuthentication() == null) {

                    auth = new UsernamePasswordAuthenticationToken(null, null,
                            authorities.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList()));

                    SecurityContextHolder.getContext().setAuthentication(auth);
                    log.debug(LoggingConstants.TOKEN_LOG, AppConstants.VALIDATING_TOKEN, "Token is valid");
                }

            } else {
                log.debug(LoggingConstants.TOKEN_ERROR, AppConstants.VALIDATING_TOKEN, "Token does not start with Bearer", null);
            }

        } catch (ServerException | IllegalArgumentException ex) {
            log.error(LoggingConstants.TOKEN_ERROR, ex.getMessage(), ResponseEnum.INTERNAL_ERROR.getDesc(), ex.getStackTrace());
        } catch (ExpiredJwtException ex) {
            log.error(LoggingConstants.TOKEN_ERROR, ex.getMessage(), ResponseEnum.TOKEN_EXPIRED.getDesc(), ex.getStackTrace());
        }

        filterChain.doFilter(request, response);

    }

}
