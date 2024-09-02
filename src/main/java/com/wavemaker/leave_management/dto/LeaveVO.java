package com.wavemaker.leave_management.dto;

import com.wavemaker.leave_management.enums.LeaveName;
import com.wavemaker.leave_management.enums.LeaveStatus;

import java.sql.Timestamp;
import java.time.LocalDate;

public class LeaveVO {
    private int id;
    private int appliedBy;
    private LocalDate dateFrom;
    private LocalDate dateTo;
    private LeaveName leaveName;
    private int leaveId;
    private LeaveStatus status;
    private String reason;
    private Timestamp createdAt;
    private LocalDate appliedDate;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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

    public LocalDate getDateTo() {
        return dateTo;
    }

    public void setDateTo(LocalDate dateTo) {
        this.dateTo = dateTo;
    }

    public LeaveName getLeaveName() {
        return leaveName;
    }

    public void setLeaveName(LeaveName leaveName) {
        this.leaveName = leaveName;
    }

    public int getLeaveId() {
        return leaveId;
    }

    public void setLeaveId(int leaveId) {
        this.leaveId = leaveId;
    }

    public LeaveStatus getStatus() {
        return status;
    }

    public void setStatus(LeaveStatus status) {
        this.status = status;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDate getAppliedDate() {
        return appliedDate;
    }

    public void setAppliedDate(LocalDate appliedDate) {
        this.appliedDate = appliedDate;
    }


    @Override
    public String toString() {
        return "EmployeeLeave{" +
                "id=" + id +
                ", appliedBy=" + appliedBy +
                ", dateFrom=" + dateFrom +
                ", dateTo=" + dateTo +
                ", leaveName=" + leaveName +
                ", leaveId=" + leaveId +
                ", status=" + status +
                ", reason='" + reason + '\'' +
                ", createdAt=" + createdAt +
                ", appliedDate=" + appliedDate +
                '}';
    }
}
