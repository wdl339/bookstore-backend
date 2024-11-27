package com.web.bookstorebackend.repository;

import com.web.bookstorebackend.model.BookTag;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;

import java.util.List;

public interface BookTagRepository extends Neo4jRepository<BookTag, Long>{

    @Query("MATCH (:Tag {name: $tag})-[:SUBCATEGORY*0..2]->(related:Tag) RETURN DISTINCT related.name AS name")
    List<String> findRelatedTags(String tag);

}


//// 创建主分类节点
//CREATE (literature:Tag {name: 'Literature'});
//CREATE (education:Tag {name: 'Education'});
//
//// 创建子分类节点
//CREATE (biography:Tag {name: 'Biography'});
//CREATE (sciFi:Tag {name: 'Scientific Fiction'});
//CREATE (fairyTale:Tag {name: 'Fairy Tales'});
//CREATE (popularScience:Tag {name: 'Popular Science'});
//CREATE (textbook:Tag {name: 'Textbooks'});
//CREATE (mystery:Tag {name: 'Mystery'});
//CREATE (chineseClassical:Tag {name: 'Chinese Classical Novels'});
//
//// 创建主分类和子分类之间的关系
//MATCH (a:Tag), (b:Tag)
//WHERE a.name = 'Literature' AND b.name = 'Biography'
//CREATE (a)-[:SUBCATEGORY]->(b);
//
//MATCH (a:Tag), (b:Tag)
//WHERE a.name = 'Literature' AND b.name = 'Chinese Classical Novels'
//CREATE (a)-[:SUBCATEGORY]->(b);
//
//MATCH (a:Tag), (b:Tag)
//WHERE a.name = 'Literature' AND b.name = 'Scientific Fiction'
//CREATE (a)-[:SUBCATEGORY]->(b);
//
//MATCH (a:Tag), (b:Tag)
//WHERE a.name = 'Education' AND b.name = 'Popular Science'
//CREATE (a)-[:SUBCATEGORY]->(b);
//
//MATCH (a:Tag), (b:Tag)
//WHERE a.name = 'Education' AND b.name = 'Textbooks'
//CREATE (a)-[:SUBCATEGORY]->(b);
//
//MATCH (a:Tag), (b:Tag)
//WHERE a.name = 'Literature' AND b.name = 'Fairy Tales'
//CREATE (a)-[:SUBCATEGORY]->(b);
//
//MATCH (a:Tag), (b:Tag)
//WHERE a.name = 'Literature' AND b.name = 'Mystery'
//CREATE (a)-[:SUBCATEGORY]->(b);
//
//CREATE (Chinese:Tag {name: 'Chinese'})
//CREATE (foreigner:Tag {name: 'Foreigner'})
//CREATE (math:Tag {name: 'Math'})
//CREATE (computer:Tag {name: 'Computer'})
//
//MATCH (a:Tag), (b:Tag)
//WHERE a.name = 'Textbooks' AND b.name = 'Math'
//CREATE (a)-[:SUBCATEGORY]->(b);
//
//MATCH (a:Tag), (b:Tag)
//WHERE a.name = 'Textbooks' AND b.name = 'Computer'
//CREATE (a)-[:SUBCATEGORY]->(b);

//MATCH (n)
//DETACH DELETE n
