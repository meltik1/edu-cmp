package com.edu_netcracker.cmp.notificationEngine.parserImpl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

@Component
@Slf4j
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public abstract class FileDataMiner {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    private final int FIRST_ROW = 0;

    private List<RawFileData> rawFileData;
    private String            json;
    private String            jsonWithMappedAttributes;


    public void mine(MultipartFile file) throws IOException {
        this.rawFileData = parseData(file);
        removeHeader();
        listToJson(rawFileData);
    }

    private void listToJson(List<RawFileData> rawFileData) throws JsonProcessingException {
        try {
            this.json = MAPPER.writerWithDefaultPrettyPrinter().writeValueAsString(rawFileData);
        } catch (JsonProcessingException e) {
            log.warn("Failed to convert list to json");
            throw e;
        }
    }

    private void removeHeader() {
        Map<Integer, String> firstRow = this.rawFileData.get(FIRST_ROW).getRawFileData();
        for (String value : firstRow.values()) {
            if (value.contains("@")) {
                return;
            }
        }
        this.rawFileData.remove(FIRST_ROW);
    }

    public static String applyMappedAttributes(String students, Map<String, String> attributes) {
        try {
            ArrayNode arrayNode = MAPPER.createArrayNode();
            List<RawFileData> rawFileData = MAPPER.readValue(students, new TypeReference<List<RawFileData>>(){});
            for (RawFileData curRawFileData : rawFileData) {
                ObjectNode objectNode = MAPPER.createObjectNode();
                for (String key : attributes.keySet()) {
                    objectNode.put(attributes.get(key), curRawFileData.getRawFileData().get(Integer.valueOf(key)));
                }
                arrayNode.add(objectNode);
            }
            return arrayNode.toPrettyString();
        } catch (JsonProcessingException e) {
            log.warn("Failed to parse json file");
        }
        return "";
    }

    public abstract List<RawFileData> parseData(MultipartFile file) throws IOException;
}
