package com.poc.techvoice.articleservice.application.transport.request.entities;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
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
