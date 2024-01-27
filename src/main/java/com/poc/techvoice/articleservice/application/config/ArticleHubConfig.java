package com.poc.techvoice.articleservice.application.config;

import com.poc.techvoice.articleservice.domain.service.notification.ArticleHub;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ArticleHubConfig {

    @Bean
    public ArticleHub getArticleHub() {
        return new ArticleHub();
    }
}
