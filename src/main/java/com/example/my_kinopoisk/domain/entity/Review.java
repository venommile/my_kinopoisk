package com.example.my_kinopoisk.domain.entity;

import com.example.my_kinopoisk.domain.entity.security.User;
import com.example.my_kinopoisk.validation.OnCreate;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Null;
import java.time.OffsetDateTime;

@Getter
@Setter
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Null(groups = OnCreate.class)

    @Column(name = "review_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    private User user;

    @Column(name = "user_id", insertable = false, updatable = false)
    private Long userId;

    @Column(name = "user_name", insertable = false, updatable = false)
    private String username;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private OffsetDateTime createDate;

    @UpdateTimestamp
    @Column(nullable = false)
    private OffsetDateTime updatedDate;


    private String title;
    private String body;

    @Max(10)
    @Min(0)
    private Float rating;

    @ManyToOne(fetch = FetchType.LAZY)
    private Movie movie;

}
