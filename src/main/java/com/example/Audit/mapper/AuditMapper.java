package com.example.Audit.mapper;

import com.example.Audit.domain.Audit;
import com.example.avro.ActionRecord;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

import java.time.Instant;

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
}
