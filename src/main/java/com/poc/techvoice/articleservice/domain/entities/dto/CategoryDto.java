package com.poc.techvoice.articleservice.domain.entities.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CategoryDto {

    private Integer id;
    private String name;
}
