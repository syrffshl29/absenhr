package com.miniproject.absenhr.core.dto.response;

public class DashboardEmployeeResponseDto {

        private String employeeName;
        private long totalPresent;
        private long totalLate;
        private long totalAttendance;

        public String getEmployeeName() {
            return employeeName;
        }

        public void setEmployeeName(String employeeName) {
            this.employeeName = employeeName;
        }

        public long getTotalPresent() {
            return totalPresent;
        }

        public void setTotalPresent(long totalPresent) {
            this.totalPresent = totalPresent;
        }

        public long getTotalLate() {
            return totalLate;
        }

        public void setTotalLate(long totalLate) {
            this.totalLate = totalLate;
        }

        public long getTotalAttendance() {
            return totalAttendance;
        }

        public void setTotalAttendance(long totalAttendance) {
            this.totalAttendance = totalAttendance;
        }
}
