package com.poc.techvoice.articleservice.domain.service.impl;

import com.poc.techvoice.articleservice.application.constants.LoggingConstants;
import com.poc.techvoice.articleservice.application.enums.ResponseEnum;
import com.poc.techvoice.articleservice.application.exception.type.ServerException;
import com.poc.techvoice.articleservice.domain.entities.Article;
import com.poc.techvoice.articleservice.domain.entities.Category;
import com.poc.techvoice.articleservice.domain.entities.dto.ArticleDto;
import com.poc.techvoice.articleservice.domain.entities.dto.CategoryDto;
import com.poc.techvoice.articleservice.domain.entities.dto.response.ViewArticleListResponse;
import com.poc.techvoice.articleservice.domain.exception.DomainException;
import com.poc.techvoice.articleservice.domain.service.ReaderService;
import com.poc.techvoice.articleservice.domain.util.UtilityService;
import com.poc.techvoice.articleservice.external.repository.ArticleRepository;
import com.poc.techvoice.articleservice.external.repository.CategoryRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@AllArgsConstructor
public class ReaderServiceImpl extends UtilityService implements ReaderService {

    private final ArticleRepository articleRepository;
    private final CategoryRepository categoryRepository;

    @Override
    public ViewArticleListResponse listArticles(Integer categoryId, Integer pageNumber, Integer pageSize) throws ServerException, DomainException {

        try {
            log.debug(LoggingConstants.LIST_ARTICLE_LOG, "List articles", LoggingConstants.STARTED);

            Pageable pageable = PageRequest.of(pageNumber-1, pageSize, Sort.by(Sort.Direction.DESC, "lastModifiedOn"));
            List<Article> articleEntityList = articleRepository.findAllByCategory_Id(categoryId, pageable);

            if (!articleEntityList.isEmpty()) {

                CategoryDto categoryDto = getCategoryDto(categoryId);
                List<ArticleDto> articleList = articleEntityList.stream().map(this::getArticleDto).collect(Collectors.toList());

                log.debug(LoggingConstants.LIST_ARTICLE_LOG, "List article", LoggingConstants.ENDED);
                return ViewArticleListResponse.builder()
                        .category(categoryDto)
                        .articleList(articleList)
                        .responseHeader(getSuccessResponseHeader("Article list retrieved successfully"))
                        .build();

            } else {
                log.error(LoggingConstants.LIST_ARTICLE_ERROR, ResponseEnum.NO_ARTICLE_DATA.getDesc(), ResponseEnum.NO_ARTICLE_DATA.getDisplayDesc(), null);
                throw new DomainException(ResponseEnum.NO_ARTICLE_DATA.getDesc(), ResponseEnum.NO_ARTICLE_DATA.getCode(), ResponseEnum.NO_ARTICLE_DATA.getDisplayDesc());
            }

        } catch (DomainException ex) {
            throw ex;
        } catch (Exception ex) {
            log.error(LoggingConstants.LIST_ARTICLE_ERROR, ex.getMessage(), ResponseEnum.INTERNAL_ERROR.getDesc(), ex.getStackTrace());
            throw new ServerException(ex.getMessage(), ResponseEnum.INTERNAL_ERROR.getCode(), ResponseEnum.INTERNAL_ERROR.getDisplayDesc());
        }

    }

    private ArticleDto getArticleDto(Article article) {

        return ArticleDto.builder()
                .publishedDate(convertDateToDescriptiveString(article.getLastModifiedOn()))
                .title(article.getTitle())
                .summary(article.getSummary())
                .details(article.getDetails())
                .comments(article.getComments())
                .build();
    }

    private CategoryDto getCategoryDto(Integer categoryId) {

        Optional<Category> categoryOptional = categoryRepository.findById(categoryId);

        if (categoryOptional.isPresent()) {
            Category category = categoryOptional.get();
            return CategoryDto.builder()
                    .id(category.getId())
                    .name(category.getName())
                    .build();
        }

        return null;

    }
}
