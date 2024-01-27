package com.poc.techvoice.articleservice.application.config;

import com.poc.techvoice.articleservice.application.exception.RestAccessDeniedHandler;
import com.poc.techvoice.articleservice.application.exception.RestAuthenticationEntryPoint;
import com.poc.techvoice.articleservice.application.filter.JwtRequestFilter;
import com.poc.techvoice.articleservice.domain.service.AuthService;
import com.poc.techvoice.articleservice.domain.service.JwtService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@AllArgsConstructor
public class WebSecurityConfig {

    private final JwtService jwtService;
    private final AuthService authService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.cors().and().csrf().disable().authorizeRequests()
                .antMatchers("/v3/api-docs/*", "/v3/api-docs","/swagger-ui/**", "/swagger-ui.html", "/webjars/**").permitAll()
                .antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                .anyRequest().authenticated()

                .and().exceptionHandling().accessDeniedHandler(new RestAccessDeniedHandler())
                .authenticationEntryPoint(new RestAuthenticationEntryPoint(jwtService))

                .and().addFilterBefore(new JwtRequestFilter(authService), UsernamePasswordAuthenticationFilter.class)
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        return http.build();

    }

}
