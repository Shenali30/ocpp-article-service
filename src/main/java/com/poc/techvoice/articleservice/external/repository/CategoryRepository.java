package com.poc.techvoice.articleservice.external.repository;

import com.poc.techvoice.articleservice.domain.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {

    @Query("SELECT c FROM Category c JOIN FETCH c.subscribedUsers")
    Set<Category> findAllCategories();
}
