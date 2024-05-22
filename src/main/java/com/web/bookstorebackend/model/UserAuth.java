package com.web.bookstorebackend.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "user_auth")
public class UserAuth {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String password;

    public UserAuth(String password) {
        this.password = password;
    }

    public UserAuth() {
    }

}
