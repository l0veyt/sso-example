package com.l0veyt.example.ssoserver.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
@Entity
public class User {

    @Id
    @GeneratedValue
    @Column(name = "user_id")
    private Integer id;

    @Column(name = "user_name")
    private String username;

    @Column(name = "user_password")
    private String password;
}
