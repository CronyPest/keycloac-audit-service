package com.example.Audit;

import com.example.Audit.domain.Audit;
import com.example.Audit.repository.AuditRepository;
import com.example.Audit.service.AuditService;
import com.example.Audit.util.KeycloakUtil;
import com.example.avro.ActionRecord;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.Instant;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
@ContextConfiguration(initializers = {PostgreSQLContainerInitializer.class})
@Testcontainers(disabledWithoutDocker = true)
@Sql("/before-test-init.sql")
@Sql(scripts = "/after-test-init.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class AuditServiceTest {

    @Autowired
    private AuditService service;
    @Autowired
    private AuditRepository repository;
    @MockBean
    private KeycloakUtil keycloakUtil;

    @Test
    @DisplayName("Creating an audit record")
    void createAuditTest() {
        ActionRecord record = ActionRecord.newBuilder()
                .setUsername("user3")
                .setAction("LOGIN")
                .setActionResult("SUCCESS")
                .setActionDate(Instant.now().toString())
                .build();

        service.createAudit(record);

        assertThat(repository.findAll().size()).isEqualTo(4);
        assertTrue(repository.findAll().stream().map(Audit::getUsername).anyMatch((v -> v.equals("user3"))));
    }

    @Test
    @DisplayName("Getting all audit records")
    void getAllAuditRecordsTest() {
        List<Audit> result = service.getAllAuditRecords(Pageable.unpaged());

        assertThat(result.size()).isEqualTo(3);
        assertTrue(result.stream().map(Audit::getUsername).noneMatch(v -> v.equals("user3")));
        assertTrue(result.stream().map(Audit::getUsername).anyMatch(v -> v.equals("user1")));
        assertTrue(result.stream().map(Audit::getUsername).anyMatch(v -> v.equals("user2")));
    }

    @Test
    @DisplayName("Getting an audit of your own actions")
    void getMyAuditRecordsTest() {
        when(keycloakUtil.getDomainUserName(any())).thenReturn("user1");

        List<Audit> result = service.getMyAuditRecords(Pageable.unpaged());

        assertThat(result.size()).isEqualTo(2);
        assertThat(result.get(0).getUsername()).isEqualTo("user1");
        assertThat(result.get(0).getAction()).isEqualTo("LOGIN");
        assertThat(result.get(1).getUsername()).isEqualTo("user1");
        assertThat(result.get(0).getAction()).isEqualTo("LOGIN");
    }

    @Test
    @DisplayName("Getting an audit on a domain username")
    void getAuditRecordsByUsernameTest() {
        List<Audit> result = service.getAuditRecordsByUsername("user2", Pageable.unpaged());

        assertThat(result.size()).isEqualTo(1);
        assertThat(result.get(0).getUsername()).isEqualTo("user2");
        assertThat(result.get(0).getActionResult()).isEqualTo("FAIL");
    }
}
