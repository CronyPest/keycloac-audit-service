package com.example.Audit.rest;

import com.example.Audit.facade.AuditFacade;
import com.example.openapi.api.AuditApi;
import com.example.openapi.model.AuditDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class AuditController implements AuditApi {

    private final AuditFacade facade;

    @Override
    public  ResponseEntity<List<AuditDto>> getMyAuditRecords(Integer page,
                                                             Integer size,
                                                             String sortBy,
                                                             String sortOrder){
        return ResponseEntity.ok(facade.getMyAuditRecords(page, size, sortBy, sortOrder));
    }

    @Override
    public ResponseEntity<List<AuditDto>> getAllAuditRecords(Integer page,
                                                             Integer size,
                                                             String sortBy,
                                                             String sortOrder) {
        return ResponseEntity.ok(facade.getAllAuditRecords(page, size, sortBy, sortOrder));
    }

    @Override
    public ResponseEntity<List<AuditDto>> getAuditRecordsByUsername(String username,
                                                                    Integer page,
                                                                    Integer size,
                                                                    String sortBy,
                                                                    String sortOrder) {
        return ResponseEntity.ok(facade.getAuditRecordsByUsername(username, page, size, sortBy, sortOrder));
    }
}
