package com.poc.techvoice.articleservice.domain.entities;

import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Article extends AuditEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @Column(nullable = false)
    private String title;
    @Column(length = 500)
    private String summary;
    @Column(nullable = false, columnDefinition = "TEXT")
    private String details;
    private String comments;
    @ManyToOne
    private User user;
    @ManyToOne
    private Category category;

}
