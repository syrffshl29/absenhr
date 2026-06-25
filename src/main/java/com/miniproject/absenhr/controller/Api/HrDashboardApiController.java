package com.miniproject.absenhr.controller.Api;

import com.miniproject.absenhr.core.dto.response.*;
import com.miniproject.absenhr.core.service.AttendanceService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/hr")
public class HrDashboardApiController {

    private AttendanceService attendanceService;

    public HrDashboardApiController(
        AttendanceService attendanceService) {

        this.attendanceService = attendanceService;
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
}
