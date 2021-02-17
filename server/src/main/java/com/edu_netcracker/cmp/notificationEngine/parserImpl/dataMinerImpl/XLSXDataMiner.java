package com.edu_netcracker.cmp.notificationEngine.parserImpl.dataMinerImpl;

import com.edu_netcracker.cmp.notificationEngine.parserImpl.FileDataMiner;
import com.edu_netcracker.cmp.notificationEngine.parserImpl.Student;
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
    public List<Student> parseData(MultipartFile multipartFile) {
        List<Student> students = new ArrayList<>();
        try {
            XSSFWorkbook  workbook = new XSSFWorkbook(multipartFile.getInputStream());
            XSSFSheet     sheet    = workbook.getSheetAt(0);
            DataFormatter df       = new DataFormatter();
            for (Row row : sheet) {
                Student student = new Student();
                for (Cell cell : row) {
                    student.addStudentAttributes(cell.getColumnIndex(), df.formatCellValue(cell));
                }
                students.add(student);
            }
        } catch (IOException e) {
            log.info(e.toString());
        }
        return students;
    }
}
