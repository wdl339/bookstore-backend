package com.web.bookstorebackend.config;


import org.neo4j.driver.Driver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.neo4j.core.DatabaseSelectionProvider;
import org.springframework.data.neo4j.core.Neo4jClient;
import org.springframework.data.neo4j.core.Neo4jTemplate;
import org.springframework.data.neo4j.core.mapping.Neo4jMappingContext;
import org.springframework.data.neo4j.core.transaction.Neo4jTransactionManager;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;

@Configuration
@EnableNeo4jRepositories(basePackages = "com.web.bookstorebackend.repository")
public class Neo4jConfig {

    @Bean("neo4jTemplate")
    public Neo4jTemplate neo4jTemplate(
            Neo4jClient neo4jClient,
            Neo4jMappingContext neo4jMappingContext,
            Driver driver,
            DatabaseSelectionProvider databaseNameProvider) {

        // 手动创建并配置 Neo4jTransactionManager
        Neo4jTransactionManager transactionManager = new Neo4jTransactionManager(driver, databaseNameProvider);

        // 返回一个绑定到 Neo4jTransactionManager 的 Neo4jTemplate
        return new Neo4jTemplate(neo4jClient, neo4jMappingContext, transactionManager);
    }
}