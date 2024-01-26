package com.poc.techvoice.articleservice.domain.service;

import com.poc.techvoice.articleservice.application.exception.type.ServerException;
import com.poc.techvoice.articleservice.domain.entities.dto.response.ViewArticleListResponse;
import com.poc.techvoice.articleservice.domain.exception.DomainException;

public interface ReaderService {

    ViewArticleListResponse listArticles(Integer categoryId, Integer pageNumber, Integer pageSize) throws ServerException, DomainException;
}
