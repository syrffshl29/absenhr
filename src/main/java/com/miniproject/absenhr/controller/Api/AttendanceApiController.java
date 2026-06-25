package com.miniproject.absenhr.controller.Api;

import com.miniproject.absenhr.core.dto.request.CheckInRequestDto;
import com.miniproject.absenhr.core.dto.response.AttendanceResponseDto;
import com.miniproject.absenhr.core.dto.response.AttendanceTodayResponseDto;
import com.miniproject.absenhr.core.dto.response.DashboardEmployeeResponseDto;
import com.miniproject.absenhr.core.service.AttendanceService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/attendance")
public class AttendanceApiController {

    private final AttendanceService attendanceService;

    public AttendanceApiController(AttendanceService attendanceService) {
        this.attendanceService = attendanceService;
    }

    @PostMapping("/checkin")
    public String checkIn(Authentication authentication,
                          @RequestBody CheckInRequestDto dto) {

        attendanceService.checkIn(
                authentication.getName(),
                dto.getLocation()
        );

        return "Absen Berhasil";
    }
    @PostMapping("/checkout")
    public String checkOut(Authentication authentication) {
        attendanceService.checkOut(authentication.getName());
        return "Absen Berhasil";
    }
    @GetMapping("/history")
    public List<AttendanceResponseDto> getHistory(Authentication authentication

    ){
        return attendanceService.getAttendanceHistory(
                authentication.getName());
    }

    @GetMapping("/history/filter")
    public List<AttendanceResponseDto> getAttendanceHistoryByDate(
            Authentication authentication,
            @RequestParam LocalDate startDate,
            @RequestParam LocalDate endDate) {

        return attendanceService.getAttendanceHistory(
                authentication.getName(),
                startDate,
                endDate
        );
    }
    @GetMapping("/dashboard")
    public DashboardEmployeeResponseDto getDashboard(
            Authentication authentication) {

        return attendanceService.getEmployeeDashboard(
                authentication.getName()
        );
    }
}
