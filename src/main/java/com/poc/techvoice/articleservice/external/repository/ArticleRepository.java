package com.poc.techvoice.articleservice.external.repository;

import com.poc.techvoice.articleservice.domain.entities.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Integer> {
}
