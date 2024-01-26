package com.poc.techvoice.articleservice.domain.service.impl;

import com.poc.techvoice.articleservice.application.constants.LoggingConstants;
import com.poc.techvoice.articleservice.application.enums.ResponseEnum;
import com.poc.techvoice.articleservice.application.exception.type.ServerException;
import com.poc.techvoice.articleservice.application.transport.request.entities.PublishArticleRequest;
import com.poc.techvoice.articleservice.application.transport.request.entities.UpdateArticleRequest;
import com.poc.techvoice.articleservice.application.transport.request.entities.UpdateProfileRequest;
import com.poc.techvoice.articleservice.domain.entities.Article;
import com.poc.techvoice.articleservice.domain.entities.Category;
import com.poc.techvoice.articleservice.domain.entities.User;
import com.poc.techvoice.articleservice.domain.entities.dto.NotificationDto;
import com.poc.techvoice.articleservice.domain.entities.dto.response.BaseResponse;
import com.poc.techvoice.articleservice.domain.enums.Action;
import com.poc.techvoice.articleservice.domain.enums.Role;
import com.poc.techvoice.articleservice.domain.exception.DomainException;
import com.poc.techvoice.articleservice.domain.service.WriterService;
import com.poc.techvoice.articleservice.domain.service.notification.ArticleHub;
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
    private final ArticleHub articleHub;


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
                Article article = createNewArticle(content, user);

                NotificationDto notification = getNotification(article, Action.PUBLISH);
                articleHub.setNotification(notification, content.getCategoryId());

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

    @Override
    public BaseResponse updateArticle(UpdateArticleRequest content, Integer articleId) throws ServerException, DomainException {

        try {
            log.debug(LoggingConstants.UPDATE_ARTICLE_LOG, "Update article", LoggingConstants.STARTED);
            Optional<Article> articleOptional = articleRepository.findById(articleId);

            if (articleOptional.isPresent() && articleOptional.get().getUser().getEmail().equals(content.getUserEmail())) {
                Article article = articleOptional.get();
                updateExistingArticle(article, content);

                log.debug(LoggingConstants.UPDATE_ARTICLE_LOG, "Update article", LoggingConstants.ENDED);
                return getSuccessBaseResponse("Updated article successfully");

            } else {
                log.error(LoggingConstants.UPDATE_ARTICLE_ERROR, ResponseEnum.INVALID_ARTICLE.getDesc(), ResponseEnum.INVALID_ARTICLE.getDisplayDesc(), null);
                throw new DomainException(ResponseEnum.INVALID_ARTICLE.getDesc(), ResponseEnum.INVALID_ARTICLE.getCode(), ResponseEnum.INVALID_ARTICLE.getDisplayDesc());
            }

        } catch (DomainException ex) {
            throw ex;
        } catch (Exception ex) {
            log.error(LoggingConstants.UPDATE_ARTICLE_ERROR, ex.getMessage(), ResponseEnum.INTERNAL_ERROR.getDesc(), ex.getStackTrace());
            throw new ServerException(ex.getMessage(), ResponseEnum.INTERNAL_ERROR.getCode(), ResponseEnum.INTERNAL_ERROR.getDisplayDesc());
        }

    }

    @Override
    public BaseResponse deleteArticle(String userEmail, Integer articleId) throws ServerException, DomainException {

        try {
            log.debug(LoggingConstants.DELETE_ARTICLE_LOG, "Delete article", LoggingConstants.STARTED);
            Optional<Article> articleOptional = articleRepository.findById(articleId);

            if (articleOptional.isPresent() && articleOptional.get().getUser().getEmail().equals(userEmail)) {

                articleRepository.deleteById(articleId);
                log.debug(LoggingConstants.UPDATE_ARTICLE_LOG, "Delete article", LoggingConstants.ENDED);
                return getSuccessBaseResponse("Deleted article successfully");

            } else {
                log.error(LoggingConstants.DELETE_ARTICLE_ERROR, ResponseEnum.INVALID_ARTICLE.getDesc(), ResponseEnum.INVALID_ARTICLE.getDisplayDesc(), null);
                throw new DomainException(ResponseEnum.INVALID_ARTICLE.getDesc(), ResponseEnum.INVALID_ARTICLE.getCode(), ResponseEnum.INVALID_ARTICLE.getDisplayDesc());
            }

        } catch (DomainException ex) {
            throw ex;
        } catch (Exception ex) {
            log.error(LoggingConstants.DELETE_ARTICLE_ERROR, ex.getMessage(), ResponseEnum.INTERNAL_ERROR.getDesc(), ex.getStackTrace());
            throw new ServerException(ex.getMessage(), ResponseEnum.INTERNAL_ERROR.getCode(), ResponseEnum.INTERNAL_ERROR.getDisplayDesc());
        }

    }

    private Article createNewArticle(PublishArticleRequest content, User user) throws DomainException {

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
            return article;

        } else {
            log.error(LoggingConstants.PUBLISH_ARTICLE_ERROR, ResponseEnum.INVALID_CATEGORY.getDesc(), ResponseEnum.INVALID_CATEGORY.getDesc(), null);
            throw new DomainException(ResponseEnum.INVALID_CATEGORY.getDesc(), ResponseEnum.INVALID_CATEGORY.getCode(), ResponseEnum.INVALID_CATEGORY.getDisplayDesc());
        }

    }

    private void updateExistingArticle(Article article, UpdateArticleRequest content) throws DomainException {

        article.setTitle(content.getTitle());
        article.setSummary(content.getSummary());
        article.setDetails(content.getDetails());
        article.setComments(content.getComments());

        if (article.getCategory().getId() != content.getCategoryId()) {
            Optional<Category> categoryOptional = categoryRepository.findById(content.getCategoryId());

            if (categoryOptional.isPresent()) {
                article.setCategory(categoryOptional.get());
            } else {
                log.error(LoggingConstants.UPDATE_ARTICLE_ERROR, ResponseEnum.INVALID_CATEGORY.getDesc(), ResponseEnum.INVALID_CATEGORY.getDesc(), null);
                throw new DomainException(ResponseEnum.INVALID_CATEGORY.getDesc(), ResponseEnum.INVALID_CATEGORY.getCode(), ResponseEnum.INVALID_CATEGORY.getDisplayDesc());
            }
        }

        articleRepository.save(article);

    }

    private NotificationDto getNotification(Article article, Action action) {
        return NotificationDto.builder()
                .articleTitle(article.getTitle())
                .action(action)
                .category(article.getCategory().getName())
                .build();

    }
}
