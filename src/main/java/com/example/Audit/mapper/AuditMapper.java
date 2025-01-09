package com.example.Audit.mapper;

import com.example.Audit.domain.Audit;
import com.example.avro.ActionRecord;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.time.Instant;

@Mapper(componentModel = "spring")
public interface AuditMapper {

    @Mapping(target = "actionDate", source = "actionDate", qualifiedByName = "charSequenceToInstant")
    @Mapping(target = "username", source = "username", qualifiedByName = "charSequenceToString")
    @Mapping(target = "action", source = "action", qualifiedByName = "charSequenceToString")
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
