package com.example.Audit.mapper;

import com.example.Audit.domain.Audit;
import com.example.avro.ActionRecord;
import com.example.openapi.model.AuditDto;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.zone.ZoneRules;
import java.util.List;

@Mapper(componentModel = "spring", unmappedSourcePolicy = ReportingPolicy.ERROR)
public interface AuditMapper {

    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "username", source = "username", qualifiedByName = "charSequenceToString")
    @Mapping(target = "action", source = "action", qualifiedByName = "charSequenceToString")
    @Mapping(target = "actionResult", source = "actionResult", qualifiedByName = "charSequenceToString")
    @Mapping(target = "actionDate", source = "actionDate", qualifiedByName = "charSequenceToInstant")
    Audit recordToEntity(ActionRecord record);

    @Named("charSequenceToString")
    default String CharSequenceToString(CharSequence charSequence) {
        return charSequence.toString();
    }

    @Named("charSequenceToInstant")
    default Instant charSequenceToInstant(CharSequence charSequence) {
        return  Instant.parse(charSequence.toString());
    }

    @Named("toOffsetDateTime")
    default OffsetDateTime toOffsetDateTime(Instant instant) {
        ZoneRules rules = ZoneId.systemDefault().getRules();
        ZoneOffset offset = rules.getOffset(instant);

        return instant.atOffset(offset);
    }

    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "username", source = "username")
    @Mapping(target = "action", source = "action")
    @Mapping(target = "actionResult", source = "actionResult")
    @Mapping(target = "actionDate", source = "actionDate", qualifiedByName = "toOffsetDateTime")
    AuditDto toDto(Audit audit);

    List<AuditDto> toListDto(List<Audit> audits);
}
