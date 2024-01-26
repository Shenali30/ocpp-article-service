package com.poc.techvoice.articleservice.application.controller;

import com.poc.techvoice.articleservice.application.constants.LoggingConstants;
import com.poc.techvoice.articleservice.application.exception.type.ServerException;
import com.poc.techvoice.articleservice.application.transport.request.entities.PublishArticleRequest;
import com.poc.techvoice.articleservice.application.transport.request.entities.UpdateArticleRequest;
import com.poc.techvoice.articleservice.application.transport.request.entities.UpdateProfileRequest;
import com.poc.techvoice.articleservice.application.validator.RequestEntityValidator;
import com.poc.techvoice.articleservice.domain.entities.dto.response.BaseResponse;
import com.poc.techvoice.articleservice.domain.exception.DomainException;
import com.poc.techvoice.articleservice.domain.service.WriterService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("${base-url.context}/writer")
public class ArticleWriterController extends BaseController {

    private final WriterService writerService;
    private final RequestEntityValidator validator;


    @PostMapping("/profile")
    public ResponseEntity<BaseResponse> editUserProfile(@RequestHeader("user-id") String userId,
                                                        @RequestBody UpdateProfileRequest updateProfileRequest,
                                                        HttpServletRequest request) throws DomainException, ServerException {

        log.info(LoggingConstants.EDIT_PROFILE_REQUEST_INITIATED);
        log.debug(LoggingConstants.FULL_REQUEST, updateProfileRequest.toString());

        setCurrentUser(request);
        validator.validate(updateProfileRequest);
        BaseResponse response = writerService.updateProfile(updateProfileRequest);

        log.info(LoggingConstants.EDIT_PROFILE_RESPONSE_SENT);
        log.debug(LoggingConstants.FULL_RESPONSE, response.toString());

        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    @PostMapping("article/publish")
    public ResponseEntity<BaseResponse> publishArticle(@RequestHeader("user-id") String userId,
                                                       @RequestBody PublishArticleRequest publishArticleRequest,
                                                       HttpServletRequest request) throws DomainException, ServerException {

        log.info(LoggingConstants.PUBLISH_ARTICLE_REQUEST_INITIATED);
        log.debug(LoggingConstants.FULL_REQUEST, publishArticleRequest.toString());

        setCurrentUser(request);
        validator.validate(publishArticleRequest);
        BaseResponse response = writerService.publishArticle(publishArticleRequest);

        log.info(LoggingConstants.PUBLISH_ARTICLE_RESPONSE_SENT);
        log.debug(LoggingConstants.FULL_RESPONSE, response.toString());

        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    @PatchMapping("article/update/{articleId}")
    public ResponseEntity<BaseResponse> updateArticle(@RequestHeader("user-id") String userId,
                                                      @PathVariable("articleId") Integer articleId,
                                                      @RequestBody UpdateArticleRequest updateArticleRequest,
                                                      HttpServletRequest request) throws DomainException, ServerException {

        log.info(LoggingConstants.UPDATE_ARTICLE_REQUEST_INITIATED);
        log.debug(LoggingConstants.FULL_REQUEST, updateArticleRequest.toString());

        setCurrentUser(request);
        validator.validate(updateArticleRequest);
        BaseResponse response = writerService.updateArticle(updateArticleRequest, articleId);

        log.info(LoggingConstants.UPDATE_ARTICLE_RESPONSE_SENT);
        log.debug(LoggingConstants.FULL_RESPONSE, response.toString());

        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    @DeleteMapping("/article/delete/{articleId}")
    public ResponseEntity<BaseResponse> deleteArticle(@RequestHeader("user-id") String userId,
                                                      @PathVariable("articleId") Integer articleId,
                                                      HttpServletRequest request) throws DomainException, ServerException {

        log.info(LoggingConstants.DELETE_ARTICLE_REQUEST_INITIATED);
        log.debug(LoggingConstants.FULL_REQUEST, "article to delete: " + articleId);

        setCurrentUser(request);
        BaseResponse response = writerService.deleteArticle(userId, articleId);

        log.info(LoggingConstants.DELETE_ARTICLE_RESPONSE_SENT);
        log.debug(LoggingConstants.FULL_RESPONSE, response.toString());

        return new ResponseEntity<>(response, HttpStatus.OK);


    }

}
