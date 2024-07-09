package com.krutov.romashka.co;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;

import static java.util.Collections.emptyMap;

@SpringBootTest
public abstract class RomashkaCoApplicationTests {
	@Test
	void contextLoads() {
	}
}
