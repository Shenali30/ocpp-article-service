package com.poc.techvoice.articleservice.domain.entities;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String name;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "category", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Article> articleList;
    @ManyToMany(mappedBy = "subscribedCategories", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<User> subscribedUsers;
}
