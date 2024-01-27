package com.poc.techvoice.articleservice.domain.service.impl;

import com.poc.techvoice.articleservice.application.constants.LoggingConstants;
import com.poc.techvoice.articleservice.application.enums.ResponseEnum;
import com.poc.techvoice.articleservice.application.exception.type.ServerException;
import com.poc.techvoice.articleservice.domain.entities.Category;
import com.poc.techvoice.articleservice.domain.entities.dto.CategoryDto;
import com.poc.techvoice.articleservice.domain.entities.dto.response.ViewCategoriesResponse;
import com.poc.techvoice.articleservice.domain.exception.DomainException;
import com.poc.techvoice.articleservice.domain.service.CategoryService;
import com.poc.techvoice.articleservice.domain.util.UtilityService;
import com.poc.techvoice.articleservice.external.repository.CategoryRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@AllArgsConstructor
public class CategoryServiceImpl extends UtilityService implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Override
    public ViewCategoriesResponse listArticleCategories() throws ServerException, DomainException {

        try {
            log.debug(LoggingConstants.LIST_CATEGORIES_LOG, "List article categories", LoggingConstants.STARTED);
            List<Category> categortEntityList = categoryRepository.findAll();

            if (!categortEntityList.isEmpty()) {
                List<CategoryDto> categoryList = categortEntityList.stream().map(this::getCategoryDto)
                        .collect(Collectors.toList());

                log.debug(LoggingConstants.LIST_ARTICLE_LOG, "List article categories", LoggingConstants.ENDED);
                return ViewCategoriesResponse.builder()
                        .categoriesList(categoryList)
                        .responseHeader(getSuccessResponseHeader("Category list retrieved successfully"))
                        .build();

            } else {
                log.error(LoggingConstants.LIST_CATEGORIES_ERROR, ResponseEnum.NO_CATEGORY_DATA.getDesc(), ResponseEnum.NO_CATEGORY_DATA.getDisplayDesc(), null);
                throw new DomainException(ResponseEnum.NO_CATEGORY_DATA.getDesc(), ResponseEnum.NO_CATEGORY_DATA.getCode(), ResponseEnum.NO_CATEGORY_DATA.getDisplayDesc());
            }

        } catch (DomainException ex) {
            throw ex;
        } catch (Exception ex) {
            log.error(LoggingConstants.LIST_CATEGORIES_ERROR, ex.getMessage(), ResponseEnum.INTERNAL_ERROR.getDesc(), ex.getStackTrace());
            throw new ServerException(ex.getMessage(), ResponseEnum.INTERNAL_ERROR.getCode(), ResponseEnum.INTERNAL_ERROR.getDisplayDesc());
        }

    }

    private CategoryDto getCategoryDto(Category category) {
        return CategoryDto.builder()
                .id(category.getId())
                .name(category.getName())
                .build();
    }
}
