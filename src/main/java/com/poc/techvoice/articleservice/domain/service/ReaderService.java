package com.poc.techvoice.articleservice.domain.service;

import com.poc.techvoice.articleservice.application.exception.type.ServerException;
import com.poc.techvoice.articleservice.application.transport.request.entities.SubscribeRequest;
import com.poc.techvoice.articleservice.domain.entities.dto.response.BaseResponse;
import com.poc.techvoice.articleservice.domain.entities.dto.response.ViewArticleListResponse;
import com.poc.techvoice.articleservice.domain.exception.DomainException;

public interface ReaderService {

    ViewArticleListResponse listArticles(Integer categoryId, Integer pageNumber, Integer pageSize) throws ServerException, DomainException;

    BaseResponse subscribeToCategory(SubscribeRequest request) throws ServerException, DomainException;
}
