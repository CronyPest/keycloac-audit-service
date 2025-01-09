package com.example.Audit;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;

import static org.assertj.core.api.Assertions.assertThat;

@Profile("test")
class AuditApplicationTests {

	@Test
	void test() {
		assertThat(1).isEqualTo(1);
	}
}
