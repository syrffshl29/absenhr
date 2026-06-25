package com.miniproject.absenhr.core.dto.response;

public class MonthlyAttendanceReportDto {

    private String employeeCode;
    private String employeeName;

    private long present;
    private long late;
    private long absent;

    public String getEmployeeCode() {
        return employeeCode;
    }

    public void setEmployeeCode(String employeeCode) {
        this.employeeCode = employeeCode;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public long getPresent() {
        return present;
    }

    public void setPresent(long present) {
        this.present = present;
    }

    public long getLate() {
        return late;
    }

    public void setLate(long late) {
        this.late = late;
    }

    public long getAbsent() {
        return absent;
    }

    public void setAbsent(long absent) {
        this.absent = absent;
    }
}
