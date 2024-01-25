package com.poc.techvoice.articleservice.application.transport.request.entities;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class ArticleContentDto {

    @Email
    @NotBlank
    private String userEmail;
    @NotNull
    private Integer categoryId;
    @NotBlank
    private String title;
    private String summary;
    @NotBlank
    private String details;
    private String comments;
}
