package com.miniproject.absenhr.core.dto.request;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.time.LocalTime;

public class AttendanceRequestDto {
    @NotNull(message = "Attendance Date is Required ")
    private LocalDate attendanceDate;
    @NotNull(message = "Check In Time is Required")
    private LocalTime checkInTime;
    private LocalTime checkOutTime;
    public String notes;
}
