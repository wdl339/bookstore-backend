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

    @Column(columnDefinition = "LONGTEXT")
    private String avatar;

    private String phone;

    private String address;

    private int balance;

    private int level;

    @Column(length = 1000)
    private String description;

    private boolean isBan;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @PrimaryKeyJoinColumn
    @JsonIgnore
    private UserAuth userAuth;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
//    @JsonIgnore
    private List<Order> orders;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
//    @JsonIgnore
    private List<CartItem> cartItems;

    public User(String name, String email, String avatar, String phone, String address, int balance, int level, String description, UserAuth userAuth) {
        this.name = name;
        this.email = email;
        this.avatar = avatar;
        this.phone = phone;
        this.address = address;
        this.balance = balance;
        this.level = level;
        this.description = description;
        this.userAuth = userAuth;
        this.isBan = false;
    }

    public User() {

    }
}
