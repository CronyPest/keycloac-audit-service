package com.example.Audit;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration(initializers = {PostgreSQLContainerInitializer.class})
@Testcontainers(disabledWithoutDocker = true)
class AuditApplicationTest {

	@Test
	public void contextLoads() {
	}
}
