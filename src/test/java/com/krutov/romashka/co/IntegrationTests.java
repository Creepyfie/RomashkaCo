package com.krutov.romashka.co;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;

import static java.util.Collections.emptyMap;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = RomashkaCoApplication.class)
@AutoConfigureMockMvc
@ExtendWith(PostgresContainerExtension.class)

public abstract class IntegrationTests {

	@Autowired
	protected NamedParameterJdbcOperations jdbcOperations;

	protected void clearTables(String... tableNames) {
		for (String tableName : tableNames) {
			jdbcOperations.update("DELETE FROM " + tableName, emptyMap());
		}
	}

	@Test
	void contextLoads() {
	}



}
