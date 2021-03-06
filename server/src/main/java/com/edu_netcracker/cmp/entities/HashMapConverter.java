package com.edu_netcracker.cmp.entities;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.AttributeConverter;
import java.io.IOException;
import java.util.Map;

@Slf4j
public class HashMapConverter implements AttributeConverter<Map<String, Object>, String> {
    @Autowired
    ObjectMapper mapper;

    @Override
    public String convertToDatabaseColumn(Map<String, Object> columnMappingMap) {
        String columnMappingJson = null;
        try {
            columnMappingJson = mapper.writeValueAsString(columnMappingMap);
        } catch (JsonProcessingException e) {
            log.error("Json writing error");

        }
        return columnMappingJson;
    }

    @Override
    public Map<String, Object> convertToEntityAttribute(String columnMappingJson) {
        Map<String, Object> columnMappingMap = null;
        try {
            columnMappingMap = mapper.readValue(columnMappingJson, Map.class);
        } catch (final IOException e) {
            log.error("JSON reading error", e);
        }

        return columnMappingMap;
    }
}
