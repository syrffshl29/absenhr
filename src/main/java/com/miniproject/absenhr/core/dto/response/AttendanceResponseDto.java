package com.miniproject.absenhr.core.dto.response;

import com.miniproject.absenhr.model.enums.AttendanceStatus;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class AttendanceResponseDto {
    private Long id;
    private LocalDate attendanceDate;
    private LocalDateTime checkInTime;
    private LocalDateTime checkOutTime;
    private String location;
    private AttendanceStatus status;

    public AttendanceResponseDto() {
    }

    public AttendanceResponseDto(Long id,
                                 LocalDate attendanceDate,
                                 LocalDateTime checkInTime,
                                 LocalDateTime checkOutTime,
                                 AttendanceStatus status) {
        this.id = id;
        this.attendanceDate = attendanceDate;
        this.checkInTime = checkInTime;
        this.checkOutTime = checkOutTime;
        this.location = location;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getAttendanceDate() {
        return attendanceDate;
    }

    public void setAttendanceDate(LocalDate attendanceDate) {
        this.attendanceDate = attendanceDate;
    }

    public LocalDateTime getCheckInTime() {
        return checkInTime;
    }

    public void setCheckInTime(LocalDateTime checkInTime) {
        this.checkInTime = checkInTime;
    }

    public LocalDateTime getCheckOutTime() {
        return checkOutTime;
    }

    public void setCheckOutTime(LocalDateTime checkOutTime) {
        this.checkOutTime = checkOutTime;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public AttendanceStatus getStatus() {
        return status;
    }

    public void setStatus(AttendanceStatus status) {
        this.status = status;
    }
}
