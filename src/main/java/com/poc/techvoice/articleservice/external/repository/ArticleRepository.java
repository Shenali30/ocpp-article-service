package com.poc.techvoice.articleservice.external.repository;

import com.poc.techvoice.articleservice.domain.entities.Article;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Integer> {

    List<Article> findAllByCategory_Id(@Param("categoryId") Integer categoryId, Pageable pageable);
}
