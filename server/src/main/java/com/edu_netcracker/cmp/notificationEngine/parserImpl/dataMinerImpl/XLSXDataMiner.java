package com.edu_netcracker.cmp.notificationEngine.parserImpl.dataMinerImpl;

import com.edu_netcracker.cmp.notificationEngine.parserImpl.FileDataMiner;
import com.edu_netcracker.cmp.notificationEngine.parserImpl.RawFileData;
import lombok.extern.slf4j.Slf4j;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.*;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

@Component
@Slf4j
public class XLSXDataMiner extends FileDataMiner {
    public List<RawFileData> parseData(MultipartFile multipartFile) throws IOException {
        List<RawFileData> rawFileData = new ArrayList<>();
        try {
            XSSFWorkbook  workbook = new XSSFWorkbook(multipartFile.getInputStream());
            XSSFSheet     sheet    = workbook.getSheetAt(0);
            DataFormatter df       = new DataFormatter();
            for (Row row : sheet) {
                RawFileData curRow = new RawFileData();
                for (Cell cell : row) {
                    curRow.addStudentAttributes(cell.getColumnIndex(), df.formatCellValue(cell));
                }
                rawFileData.add(curRow);
            }
        } catch (IOException e) {
            log.error("Couldn't parse the EXCEL file", e);
            throw e;
        }
        return rawFileData;
    }
}
