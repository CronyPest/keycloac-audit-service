package com.example.Audit.facade;

import com.example.Audit.mapper.AuditMapper;
import com.example.Audit.service.AuditService;
import com.example.openapi.model.AuditDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class AuditFacade {

    private final AuditService service;
    private final AuditMapper mapper;

    public List<AuditDto> getAllAuditRecords(Integer page, Integer size, String sortBy, String sortOrder) {
        Pageable pageable = getPageable(page, size, sortBy, sortOrder);
        return mapper.toListDto(service.getAllAuditRecords(pageable));
    }

    public List<AuditDto> getMyAuditRecords(Integer page, Integer size, String sortBy, String sortOrder) {
        Pageable pageable = getPageable(page, size, sortBy, sortOrder);
        return mapper.toListDto(service.getMyAuditRecords(pageable));
    }

    public List<AuditDto> getAuditRecordsByUsername(String username, Integer page,
                                                    Integer size, String sortBy, String sortOrder) {
        Pageable pageable = getPageable(page, size, sortBy, sortOrder);
        return mapper.toListDto(service.getAuditRecordsByUsername(username, pageable));
    }

    private Pageable getPageable(Integer page, Integer size, String sortBy, String sortOrder) {
        Sort.Direction direction = Sort.Direction.fromString(sortOrder);
        return PageRequest.of(page, size, Sort.by(direction, sortBy));
    }
}
