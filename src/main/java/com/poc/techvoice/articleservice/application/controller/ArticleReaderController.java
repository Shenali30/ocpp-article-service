package com.poc.techvoice.articleservice.application.controller;

import com.poc.techvoice.articleservice.application.constants.LoggingConstants;
import com.poc.techvoice.articleservice.application.exception.type.ServerException;
import com.poc.techvoice.articleservice.domain.entities.dto.response.ViewArticleListResponse;
import com.poc.techvoice.articleservice.domain.exception.DomainException;
import com.poc.techvoice.articleservice.domain.service.ReaderService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("${base-url.context}/reader")
public class ArticleReaderController extends BaseController {

    private final ReaderService readerService;

    @GetMapping("/view")
    public ResponseEntity<ViewArticleListResponse> listAllArticles(@RequestHeader("user-id") String userId,
                                                                   @RequestParam("categoryId") Integer categoryId,
                                                                   @RequestParam("pageNo") Integer pageNumber,
                                                                   @RequestParam("pageSize") Integer pageSize,
                                                                   HttpServletRequest request) throws DomainException, ServerException {

        log.info(LoggingConstants.LIST_ARTICLE_REQUEST_INITIATED);
        log.debug(LoggingConstants.FULL_REQUEST, "Viewing page: " + pageNumber + " of category: " + categoryId);

        setCurrentUser(request);
        ViewArticleListResponse response = readerService.listArticles(categoryId, pageNumber, pageSize);

        log.info(LoggingConstants.LIST_ARTICLE_RESPONSE_SENT);
        log.debug(LoggingConstants.FULL_RESPONSE, response.toString());

        return new ResponseEntity<>(response, HttpStatus.OK);

    }
}
