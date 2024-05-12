package com.web.bookstorebackend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    private String email;

    private String avatar;

    private String phone;

    private String address;

    private double balance;

    private int level;

    private String description;

    @OneToOne(cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    @JsonIgnore
    private UserAuth userAuth;

    public User(String name, String email, String avatar, String phone, String address, double balance, int level, String description, UserAuth userAuth) {
        this.name = name;
        this.email = email;
        this.avatar = avatar;
        this.phone = phone;
        this.address = address;
        this.balance = balance;
        this.level = level;
        this.description = description;
        this.userAuth = userAuth;
    }

    public User() {

    }
}
