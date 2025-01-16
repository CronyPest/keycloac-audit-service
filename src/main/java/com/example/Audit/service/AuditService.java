package com.example.Audit.service;

import com.example.Audit.domain.Audit;
import com.example.Audit.mapper.AuditMapper;
import com.example.Audit.repository.AuditRepository;
import com.example.Audit.util.KeycloakUtil;
import com.example.avro.ActionRecord;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.TreeMap;

@Service
@RequiredArgsConstructor
public class AuditService {

    private final AuditMapper mapper;
    private final AuditRepository repository;
    private final KeycloakUtil keycloakUtil;

    public void createAudit(ActionRecord record) {
        Audit audit = mapper.recordToEntity(record);
        repository.save(audit);
    }

    public List<Audit> getAllAuditRecords(Pageable pageable) {
        Page<Audit> audits = repository.findAll(pageable);
        return audits.getContent();
    }

    public List<Audit> getAuditRecordsByUsername(String username, Pageable pageable) {
        return repository.findAllByUsername(username, pageable);
    }

    public List<Audit> getMyAuditRecords(Pageable pageable) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = keycloakUtil.getDomainUserName(auth);
        return repository.findAllByUsername(username, pageable);
    }
}
