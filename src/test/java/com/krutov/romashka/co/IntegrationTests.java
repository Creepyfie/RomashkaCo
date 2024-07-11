package com.krutov.romashka.co;

import org.junit.jupiter.api.BeforeAll;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static java.util.Collections.emptyMap;

@Testcontainers
@SpringBootTest
public abstract class IntegrationTests {

    @Container
    @ServiceConnection
    static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:15");

    @BeforeAll
    static void beforeAll() {
        postgreSQLContainer.start();
    }

    @Autowired
    protected NamedParameterJdbcOperations jdbcOperations;

    protected void clearTables(String... tableNames) {
        for (String tableName : tableNames) {
            jdbcOperations.update("DELETE FROM " + tableName, emptyMap());
        }
    }
}
