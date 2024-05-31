package com.web.bookstorebackend.dto;

import com.web.bookstorebackend.model.User;
import lombok.Data;

import java.util.List;

@Data
public class GetUsersDto {
    private long total;
    private List<User> users;

    public GetUsersDto(long total, List<User> users) {
        this.total = total;
        this.users = users;
    }
}
