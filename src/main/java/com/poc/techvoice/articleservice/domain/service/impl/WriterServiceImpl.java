package com.poc.techvoice.articleservice.domain.service.impl;

import com.poc.techvoice.articleservice.application.constants.LoggingConstants;
import com.poc.techvoice.articleservice.application.enums.ResponseEnum;
import com.poc.techvoice.articleservice.application.exception.type.ServerException;
import com.poc.techvoice.articleservice.application.transport.request.entities.PublishArticleRequest;
import com.poc.techvoice.articleservice.application.transport.request.entities.UpdateProfileRequest;
import com.poc.techvoice.articleservice.domain.entities.Article;
import com.poc.techvoice.articleservice.domain.entities.Category;
import com.poc.techvoice.articleservice.domain.entities.User;
import com.poc.techvoice.articleservice.domain.entities.dto.response.BaseResponse;
import com.poc.techvoice.articleservice.domain.enums.Role;
import com.poc.techvoice.articleservice.domain.exception.DomainException;
import com.poc.techvoice.articleservice.domain.service.WriterService;
import com.poc.techvoice.articleservice.domain.util.UtilityService;
import com.poc.techvoice.articleservice.external.repository.ArticleRepository;
import com.poc.techvoice.articleservice.external.repository.CategoryRepository;
import com.poc.techvoice.articleservice.external.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Slf4j
@Service
@AllArgsConstructor
public class WriterServiceImpl extends UtilityService implements WriterService {

    private final UserRepository userRepository;
    private final ArticleRepository articleRepository;
    private final CategoryRepository categoryRepository;

    @Override
    public BaseResponse updateProfile(UpdateProfileRequest request) throws ServerException, DomainException {

        try {
            log.debug(LoggingConstants.EDIT_PROFILE_LOG, "Update user profile", LoggingConstants.STARTED);

            User user = userRepository.findByEmail(request.getEmail());
            if (Objects.nonNull(user)) {

                user.setRole(Role.WRITER);
                user.setName(request.getName());
                user.setProfileDescription(request.getProfileDescription());
                user.setCountryOfOrigin(request.getCountryOfOrigin());
                userRepository.save(user);

                log.debug(LoggingConstants.EDIT_PROFILE_LOG, "Update user profile", LoggingConstants.ENDED);
                return getSuccessBaseResponse("User profile updated successfully");

            } else {
                log.error(LoggingConstants.EDIT_PROFILE_ERROR, ResponseEnum.INVALID_USER.getDesc(), ResponseEnum.INVALID_USER.getDesc(), null);
                throw new DomainException(ResponseEnum.INVALID_USER.getDesc(), ResponseEnum.INVALID_USER.getCode(), ResponseEnum.INVALID_USER.getDisplayDesc());
            }

        } catch (DomainException ex) {
            throw ex;
        } catch (Exception ex) {
            log.error(LoggingConstants.EDIT_PROFILE_ERROR, ex.getMessage(), ResponseEnum.INTERNAL_ERROR.getDesc(), ex.getStackTrace());
            throw new ServerException(ex.getMessage(), ResponseEnum.INTERNAL_ERROR.getCode(), ResponseEnum.INTERNAL_ERROR.getDisplayDesc());
        }

    }


    @Override
    public BaseResponse publishArticle(PublishArticleRequest content) throws ServerException, DomainException {

        try {
            log.debug(LoggingConstants.PUBLISH_ARTICLE_LOG, "Publish article", LoggingConstants.STARTED);
            User user = userRepository.findByEmail(content.getUserEmail());

            if (Objects.nonNull(user) && Role.WRITER.equals(user.getRole())) {
                createNewArticle(content, user);

                log.debug(LoggingConstants.PUBLISH_ARTICLE_LOG, "Publish article", LoggingConstants.ENDED);
                return getSuccessBaseResponse("Published article successfully");
            } else {
                log.error(LoggingConstants.PUBLISH_ARTICLE_ERROR, ResponseEnum.NO_PERMISSION_TO_PUBLISH.getDesc(), ResponseEnum.NO_PERMISSION_TO_PUBLISH.getDesc(), null);
                throw new DomainException(ResponseEnum.NO_PERMISSION_TO_PUBLISH.getDesc(), ResponseEnum.NO_PERMISSION_TO_PUBLISH.getCode(), ResponseEnum.NO_PERMISSION_TO_PUBLISH.getDisplayDesc());
            }

        } catch (DomainException ex) {
            throw ex;
        } catch (Exception ex) {
            log.error(LoggingConstants.PUBLISH_ARTICLE_ERROR, ex.getMessage(), ResponseEnum.INTERNAL_ERROR.getDesc(), ex.getStackTrace());
            throw new ServerException(ex.getMessage(), ResponseEnum.INTERNAL_ERROR.getCode(), ResponseEnum.INTERNAL_ERROR.getDisplayDesc());
        }

    }

    private void createNewArticle(PublishArticleRequest content, User user) throws DomainException {

        Optional<Category> categoryOptional = categoryRepository.findById(content.getCategoryId());

        if (categoryOptional.isPresent()) {

            Category articleCategory = categoryOptional.get();
            Article article = Article.builder()
                    .title(content.getTitle())
                    .summary(content.getSummary())
                    .details(content.getDetails())
                    .comments(content.getComments())
                    .user(user)
                    .category(articleCategory)
                    .build();

            user.getArticleList().add(article);
            articleCategory.getArticleList().add(article);

            articleRepository.save(article);

        } else {
            log.error(LoggingConstants.PUBLISH_ARTICLE_ERROR, ResponseEnum.INVALID_CATEGORY.getDesc(), ResponseEnum.INVALID_CATEGORY.getDesc(), null);
            throw new DomainException(ResponseEnum.INVALID_CATEGORY.getDesc(), ResponseEnum.INVALID_CATEGORY.getCode(), ResponseEnum.INVALID_CATEGORY.getDisplayDesc());
        }

    }

}
