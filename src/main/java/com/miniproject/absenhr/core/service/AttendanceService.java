package com.miniproject.absenhr.core.service;

import com.miniproject.absenhr.core.dto.response.*;


import java.time.LocalDate;
import java.util.List;

public interface AttendanceService {
    void checkIn(String username, String location);
    void checkOut(String username) ;
    List<AttendanceResponseDto> getAttendanceHistory(String username);
    DashboardSummaryDto getDashboardSummary();
    List<AttendanceTodayResponseDto> getTodayAttendances();
    List<AttendanceResponseDto> getAttendanceHistory(
            String username,
            LocalDate startDate,
            LocalDate endDate);
    List<HrAttendanceResponseDto> getAttendanceReport(
            LocalDate startDate,
            LocalDate endDate);
    List<MonthlyAttendanceReportDto> getMonthlyAttendancesReport(
            int month,
            int year);
    DashboardEmployeeResponseDto getEmployeeDashboard(
            String username);
    List<LateSummaryResponseDto> getLateSummary();
    List<HrAttendanceResponseDto> getAllAttendanceReport();
}
