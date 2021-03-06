package com.example.my_kinopoisk.domain.entity.security;

import com.example.my_kinopoisk.validation.OnCreate;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Null;
import java.io.Serializable;

@Getter
@Setter
@Entity
@Table(name = "user_security")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class User implements Serializable {

    private static final long serialVersionUID = 1905122041950251207L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Null(groups = OnCreate.class)
    private Long id;
    @Column(unique = true)
    private String login;

    @Column(unique = true)

    private String userName;

    private String password;
    @Enumerated(value = EnumType.STRING)
    @Null(groups = OnCreate.class)
    private Role role;
    @Enumerated(value = EnumType.STRING)
    @Null(groups = OnCreate.class)
    private Status status;


}
