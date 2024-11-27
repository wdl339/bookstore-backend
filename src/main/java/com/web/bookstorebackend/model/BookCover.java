package com.web.bookstorebackend.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Getter
@Setter
@Document(collection = "cover")
public class BookCover {
    @Id
    private int id;
    private String coverBase64;

    public BookCover() {
    }

    public BookCover(int id, String iconBase64) {
        this.id = id;
        this.coverBase64 = iconBase64;
    }
}

