package com.edu_netcracker.cmp.notificationEngine.parserImpl;

import com.edu_netcracker.cmp.notificationEngine.parserImpl.dataMinerImpl.XLSXDataMiner;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Component
@Slf4j
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FileHandler {
    private final String        XLSX = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
    private       FileDataMiner fileDataMiner = new XLSXDataMiner();

    public String handle(MultipartFile multipartFile) {
        try {
            this.initializeFileDataMiner(multipartFile);
            this.getFileDataMiner().mine(multipartFile);
            return this.getFileDataMiner().getJson();
        } catch (IOException e) {
            log.error("Could not handle file: " + multipartFile.getOriginalFilename(), e);
        } catch (NullPointerException e) {
            log.warn("No file loaded to initialize FileDataMiner", e);
        }
        return "";
    }

    public List<RawFileData> getRawFileData() throws NullPointerException {
        if (this.getFileDataMiner() == null) {
            throw new NullPointerException("FileDataMiner not initialized");
        }
        return this.getFileDataMiner().getRawFileData();
    }

    public String sendAttributes(String students, Map<String, String> attributes) throws NullPointerException {
        if (this.getFileDataMiner() == null) {
            throw new NullPointerException("FileDataMiner not initialized");
        }
        return FileDataMiner.applyMappedAttributes(students, attributes);
    }

    private void initializeFileDataMiner(MultipartFile multipartFile) throws IOException, NullPointerException {
        switch (Objects.requireNonNull(multipartFile.getContentType())) {
            case XLSX:
                this.fileDataMiner = new XLSXDataMiner();
                break;

            default:
                log.warn("Not supported file format");
                throw new IOException("Not supported file format");
        }
    }
}
