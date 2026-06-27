package com.miniproject.absenhr.core.service.export;

import com.miniproject.absenhr.core.dto.response.HrAttendanceResponseDto;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import java.io.ByteArrayOutputStream;
import java.util.List;

@Service
public class ExcelExportService {

    public byte[] exportAttendanceReport(
            List<HrAttendanceResponseDto> reports) {

        try (XSSFWorkbook workbook = new XSSFWorkbook()) {

            XSSFSheet sheet =
                    workbook.createSheet("Attendance Report");

            Row header = sheet.createRow(0);

            header.createCell(0).setCellValue("Employee Code");
            header.createCell(1).setCellValue("Full Name");
            header.createCell(2).setCellValue("Attendance Date");
            header.createCell(3).setCellValue("Status");
            header.createCell(4).setCellValue("Location");

            int rowNum = 1;

            for (HrAttendanceResponseDto dto : reports) {

                Row row = sheet.createRow(rowNum++);

                row.createCell(0)
                        .setCellValue(dto.getEmployeeCode());

                row.createCell(1)
                        .setCellValue(dto.getFullName());

                row.createCell(2)
                        .setCellValue(
                                String.valueOf(dto.getAttendanceDate()));

                row.createCell(3)
                        .setCellValue(dto.getStatus().name());

                row.createCell(4)
                        .setCellValue(
                                String.valueOf(dto.getLocation()));

            }

            for (int i = 0; i < 5; i++) {
                sheet.autoSizeColumn(i);
            }

            ByteArrayOutputStream outputStream =
                    new ByteArrayOutputStream();

            workbook.write(outputStream);

            return outputStream.toByteArray();

        } catch (Exception e) {
            throw new RuntimeException("Failed export excel");
        }
    }
}
