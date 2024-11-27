package com.web.bookstorebackend.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.util.HashSet;
import java.util.Set;

@Node
public class BookTag {

    @Id
    @GeneratedValue
    private Long id;

    @Setter
    @Getter
    private String name;

    @Getter
    @Relationship(type = "SUBCATEGORY_OF")
    private Set<BookTag> subcategories;

}
