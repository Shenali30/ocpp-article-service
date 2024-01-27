package com.poc.techvoice.articleservice.application.controller;

import com.poc.techvoice.articleservice.application.constants.LoggingConstants;
import com.poc.techvoice.articleservice.application.exception.type.ServerException;
import com.poc.techvoice.articleservice.domain.entities.dto.response.ViewCategoriesResponse;
import com.poc.techvoice.articleservice.domain.exception.DomainException;
import com.poc.techvoice.articleservice.domain.service.CategoryService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestController
@AllArgsConstructor
@Tag(name = "Article Category Controller")
@RequestMapping("${base-url.context}/categories")
public class ArticleCategoryController extends BaseController {

    private final CategoryService categoryService;

    @GetMapping
    public ResponseEntity<ViewCategoriesResponse> listAllCategories(@RequestHeader("user-id") String userId,
                                                                    @RequestHeader("Authorization") String authToken,
                                                                    HttpServletRequest request) throws DomainException, ServerException {

        log.info(LoggingConstants.LIST_CATEGORIES_REQUEST_INITIATED);

        setCurrentUser(request);
        ViewCategoriesResponse response = categoryService.listArticleCategories();

        log.info(LoggingConstants.LIST_CATEGORIES_RESPONSE_SENT);
        log.debug(LoggingConstants.FULL_RESPONSE, response.toString());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
