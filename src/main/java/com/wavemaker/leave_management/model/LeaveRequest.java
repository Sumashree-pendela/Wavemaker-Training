package com.wavemaker.leave_management.model;

import com.wavemaker.leave_management.enums.LeaveName;

import java.time.LocalDate;

public class LeaveRequest {
    private int appliedBy;
    private LocalDate dateFrom;
    private LocalDate dateTo;
    private LeaveName leaveName;
    private String reason;
    private int leaveId;

    public int getAppliedBy() {
        return appliedBy;
    }

    public void setAppliedBy(int appliedBy) {
        this.appliedBy = appliedBy;
    }

    public LocalDate getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(LocalDate dateFrom) {
        this.dateFrom = dateFrom;
    }

    public LeaveName getLeaveName() {
        return leaveName;
    }

    public void setLeaveName(LeaveName leaveName) {
        this.leaveName = leaveName;
    }

    public LocalDate getDateTo() {
        return dateTo;
    }

    public void setDateTo(LocalDate dateTo) {
        this.dateTo = dateTo;
    }




    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public int getLeaveId() {
        return leaveId;
    }

    public void setLeaveId(int leaveId) {
        this.leaveId = leaveId;
    }

    @Override
    public String toString() {
        return "LeaveRequest{" +
                "appliedBy=" + appliedBy +
                ", dateFrom=" + dateFrom +
                ", dateTo=" + dateTo +
                ", leaveName=" + leaveName +
                ", reason='" + reason + '\'' +
                ", leaveId=" + leaveId +
                '}';
    }
}
