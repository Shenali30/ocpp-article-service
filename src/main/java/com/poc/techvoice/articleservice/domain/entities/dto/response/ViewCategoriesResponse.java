package com.poc.techvoice.articleservice.domain.entities.dto.response;

import com.poc.techvoice.articleservice.domain.entities.dto.CategoryDto;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ViewCategoriesResponse {

    private List<CategoryDto> categoriesList;
    private ResponseHeader responseHeader;
}
