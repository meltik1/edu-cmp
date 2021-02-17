package com.edu_netcracker.cmp.notificationEngine.parserImpl;

import com.edu_netcracker.cmp.notificationEngine.parserImpl.dataMinerImpl.XLSXDataMiner;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.Objects;

@Component
@Slf4j
public class FileHandler {
    private final String        XLSX = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
    private       FileDataMiner fileDataMiner = null;

    public void handle(MultipartFile multipartFile) {
        if (multipartFile != null && !multipartFile.isEmpty()) {
            switch (Objects.requireNonNull(multipartFile.getContentType())) {
                case XLSX:
                    this.fileDataMiner = new XLSXDataMiner();
                    break;

                default:
                    log.info("Not supported file format");
                    return;
            }
            fileDataMiner.mine(multipartFile);
        } else {
            log.info("No file loaded");
        }
    }
}
