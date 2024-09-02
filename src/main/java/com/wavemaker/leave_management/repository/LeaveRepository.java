package com.wavemaker.leave_management.repository;

import com.wavemaker.leave_management.dto.LeaveVO;
import com.wavemaker.leave_management.dto.Leave;
import com.wavemaker.leave_management.dto.LeaveTracker;
import com.wavemaker.leave_management.exception.EmployeeNotFoundException;
import com.wavemaker.leave_management.exception.LoginNotFoundException;
import com.wavemaker.leave_management.model.LeaveRequest;

import java.util.List;

public interface LeaveRepository {
    LeaveVO create(LeaveRequest leaveRequest);

    List<Leave> getEmployeeLeavesByManagerId(int userId) throws EmployeeNotFoundException, LoginNotFoundException;

    List<Leave> getMyLeavesByUserId(int userId) throws LoginNotFoundException;

    void rejectLeave(int leaveId);

    void approveLeave(int leaveId);

    int getLeaveCountByUserId(int userId, int leaveId);

    List<LeaveTracker> getLeaveTrackerByUserId(int userId);
}
