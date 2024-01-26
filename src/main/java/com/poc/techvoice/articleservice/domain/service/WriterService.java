package com.poc.techvoice.articleservice.domain.service;

import com.poc.techvoice.articleservice.application.exception.type.ServerException;
import com.poc.techvoice.articleservice.application.transport.request.entities.PublishArticleRequest;
import com.poc.techvoice.articleservice.application.transport.request.entities.UpdateArticleRequest;
import com.poc.techvoice.articleservice.application.transport.request.entities.UpdateProfileRequest;
import com.poc.techvoice.articleservice.domain.entities.dto.response.BaseResponse;
import com.poc.techvoice.articleservice.domain.exception.DomainException;

public interface WriterService {

    BaseResponse updateProfile(UpdateProfileRequest request) throws ServerException, DomainException;

    BaseResponse publishArticle(PublishArticleRequest request) throws ServerException, DomainException;

    BaseResponse updateArticle(UpdateArticleRequest request, Integer articleId) throws ServerException, DomainException;

    BaseResponse deleteArticle(String userEmail, Integer articleId) throws ServerException, DomainException;
}
