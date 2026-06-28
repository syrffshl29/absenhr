package com.miniproject.absenhr.controller.Api;

import com.miniproject.absenhr.core.dto.response.*;
import com.miniproject.absenhr.core.service.AttendanceService;
import com.miniproject.absenhr.core.service.export.ExcelExportService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/hr")
public class HrDashboardApiController {

    private final ExcelExportService excelExportService;
    private AttendanceService attendanceService;


    public HrDashboardApiController(
            AttendanceService attendanceService,
            ExcelExportService excelExportService) {

        this.attendanceService = attendanceService;
        this.excelExportService = excelExportService;
    }

    @GetMapping("/dashboard-summary")
    public DashboardSummaryDto getDashboardSummary() {
        return attendanceService.getDashboardSummary();
    }

    @GetMapping("/attendance/today")
    public List<AttendanceTodayResponseDto> getTodayAttendances() {

        return attendanceService.getTodayAttendances();
    }

    @GetMapping("/attendance/filter")
    public List<HrAttendanceResponseDto> getAttendanceReport(
            @RequestParam LocalDate startDate,
            @RequestParam LocalDate endDate) {

        return attendanceService.getAttendanceReport(
                startDate,
                endDate);
    }

    @GetMapping("/reports/monthly")
    public List<MonthlyAttendanceReportDto> getMonthlyReport(
            @RequestParam int month,
            @RequestParam int year) {

        return attendanceService.getMonthlyAttendancesReport(
                month,
                year);
    }

    @GetMapping("/late-summary")
    public List<LateSummaryResponseDto> getLateSummary() {

        return attendanceService.getLateSummary();
    }

    @GetMapping("/attendance/export/excel")
    public ResponseEntity<byte[]> exportAttendanceExcel(
            @RequestParam LocalDate startDate,
            @RequestParam LocalDate endDate) {

        List<HrAttendanceResponseDto> reports =
                attendanceService.getAttendanceReport(
                        startDate,
                        endDate);

        byte[] excel =
                excelExportService.exportAttendanceReport(reports);

        return ResponseEntity.ok()
                .header(
                        HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=attendance-report.xlsx")
                .contentType(
                        MediaType.APPLICATION_OCTET_STREAM)
                .body(excel);
    }
    @GetMapping("/attendance/all")
    public List<HrAttendanceResponseDto> getAllAttendanceReport() {
        return attendanceService.getAllAttendanceReport();
    }
}