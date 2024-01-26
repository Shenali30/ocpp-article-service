package com.poc.techvoice.articleservice.domain.entities.dto;

import com.poc.techvoice.articleservice.application.transport.request.entities.ArticleContentDto;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
public class ArticleDto extends ArticleContentDto {

    private String publishedDate;
}
