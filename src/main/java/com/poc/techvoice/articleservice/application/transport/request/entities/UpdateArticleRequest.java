package com.poc.techvoice.articleservice.application.transport.request.entities;

import com.poc.techvoice.articleservice.application.validator.RequestEntityInterface;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateArticleRequest extends ArticleContentDto implements RequestEntityInterface {

}
