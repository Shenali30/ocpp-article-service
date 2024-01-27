package com.poc.techvoice.articleservice.domain.entities.dto.response;

import com.poc.techvoice.articleservice.domain.entities.dto.ArticleDto;
import com.poc.techvoice.articleservice.domain.entities.dto.CategoryDto;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ViewArticleListResponse {

    private CategoryDto category;
    private List<ArticleDto> articleList;
    private ResponseHeader responseHeader;
}
