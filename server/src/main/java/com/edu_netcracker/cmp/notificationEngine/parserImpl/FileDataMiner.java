package com.edu_netcracker.cmp.notificationEngine.parserImpl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
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

    private final int HEADER = 0;

    private List<RawFileData> rawFileData;
    private String        json;
    private String        jsonWithMappedAttributes;


    public void mine(MultipartFile file) throws IOException {
        this.rawFileData = parseData(file);
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

    public void handleMappedAttributes(MultipartFile multipartFile) throws IOException, NullPointerException {
        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode mappedAttributes = mapper.readTree(multipartFile.getInputStream());
            List<RawFileData> selectedRawFileData = new ArrayList<>();
            mapHeaders(mappedAttributes);
            selectedRawFileData.add(this.rawFileData.get(HEADER));
            addSelectedStudents(selectedRawFileData, mappedAttributes);
            this.jsonWithMappedAttributes = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(selectedRawFileData);
        } catch (IOException e) {
            log.error("Couldn't parse mapped attributes", e);
            throw e;
        }
    }

    private void mapHeaders(JsonNode mappedAttributes) {
        for (Iterator<Map.Entry<String, JsonNode>> it = mappedAttributes.fields(); it.hasNext();) {
            Map.Entry<String, JsonNode> curField = it.next();
            if (curField.getKey().matches("\\d+")) {
                int index = Integer.parseInt(curField.getKey());
                this.rawFileData.get(HEADER).getRawFileData().replace(index, curField.getValue().textValue());
            }
        }
    }

    private void addSelectedStudents(List<RawFileData> selectedRawFileData, JsonNode mappedAttributes) {
        int amountOfIntervals = mappedAttributes.get("Range").size();
        for (int i = 0; i < amountOfIntervals; i++) {
            int from = mappedAttributes.get("Range").get(i).get("from").asInt();
            int to = mappedAttributes.get("Range").get(i).get("to").asInt();
            if (checkRange(from, to)) {
                for (int j = from; j <= to; j++) {
                    selectedRawFileData.add(this.rawFileData.get(j));
                }
            }
        }
    }

    private boolean checkRange(int from, int to) {
        return from > 0 && from <= to && to < this.rawFileData.size();
    }

    public abstract List<RawFileData> parseData(MultipartFile file) throws IOException;
}
