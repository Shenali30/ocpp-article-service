package com.poc.techvoice.articleservice.external.repository;


import com.poc.techvoice.articleservice.domain.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {

    User findByEmail(String email);
}
