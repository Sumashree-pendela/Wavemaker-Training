package com.wavemaker.leave_management.service;

import com.wavemaker.leave_management.dto.Leave;
import com.wavemaker.leave_management.dto.LeaveTracker;
import com.wavemaker.leave_management.dto.LeaveVO;
import com.wavemaker.leave_management.exception.EmployeeNotFoundException;
import com.wavemaker.leave_management.exception.LeaveTypeNotFoundException;
import com.wavemaker.leave_management.exception.LoginNotFoundException;
import com.wavemaker.leave_management.model.LeaveRequest;

import java.util.List;

public interface LeaveService {

    LeaveVO create(LeaveRequest leaveRequest) throws LeaveTypeNotFoundException, LoginNotFoundException;

    List<Leave> getMyLeavesByUserId(int userId) throws LoginNotFoundException;

    List<Leave> getEmployeeLeavesByManagerId(int userId) throws EmployeeNotFoundException, LoginNotFoundException;

    void approveLeave(int leaveId);

    void rejectLeave(int leaveId);

    List<LeaveTracker> getLeavesTrackerByUserId(int userId);

}
