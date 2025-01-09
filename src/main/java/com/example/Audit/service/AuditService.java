package com.example.Audit.service;

import com.example.Audit.domain.Audit;
import com.example.Audit.mapper.AuditMapper;
import com.example.Audit.repository.AuditRepository;
import com.example.avro.ActionRecord;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuditService {

    private final AuditMapper mapper;
    private final AuditRepository repository;

    public void createAudit(ActionRecord record) {
        Audit audit = mapper.recordToEntity(record);
        repository.save(audit);
    }
}
