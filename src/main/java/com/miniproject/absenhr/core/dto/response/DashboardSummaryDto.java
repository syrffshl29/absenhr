package com.miniproject.absenhr.core.dto.response;

import java.util.List;

public class DashboardSummaryDto {
    private long totalEmployees;
    private long presentToday;
    private long lateToday;
    private long absentToday;

    private List<String> lateEmployees;
    private List<String> absentEmployees;

    public long getTotalEmployees() {
        return totalEmployees;
    }

    public void setTotalEmployees(long totalEmployees) {
        this.totalEmployees = totalEmployees;
    }

    public long getPresentToday() {
        return presentToday;
    }

    public void setPresentToday(long presentToday) {
        this.presentToday = presentToday;
    }

    public long getLateToday() {
        return lateToday;
    }

    public void setLateToday(long lateToday) {
        this.lateToday = lateToday;
    }

    public long getAbsentToday() {
        return absentToday;
    }

    public void setAbsentToday(long absentToday) {
        this.absentToday = absentToday;
    }

    public List<String> getLateEmployees() {
        return lateEmployees;
    }

    public void setLateEmployees(List<String> lateEmployees) {
        this.lateEmployees = lateEmployees;
    }

    public List<String> getAbsentEmployees() {
        return absentEmployees;
    }

    public void setAbsentEmployees(List<String> absentEmployees) {
        this.absentEmployees = absentEmployees;
    }
}
