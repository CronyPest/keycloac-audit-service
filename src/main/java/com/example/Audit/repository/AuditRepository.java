package com.example.Audit.repository;

import com.example.Audit.domain.Audit;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface AuditRepository extends JpaRepository<Audit, UUID> {

    List<Audit> findAllByUsername(String username, Pageable pageable);
}
