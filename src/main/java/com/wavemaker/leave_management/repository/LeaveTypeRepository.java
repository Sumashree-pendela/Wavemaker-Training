package com.wavemaker.leave_management.repository;

import com.wavemaker.leave_management.dto.LeaveType;
import com.wavemaker.leave_management.enums.LeaveName;
import com.wavemaker.leave_management.exception.LeaveTypeNotFoundException;

public interface LeaveTypeRepository {
    LeaveType getById(int id) throws LeaveTypeNotFoundException;
    LeaveType getByName(LeaveName leaveName) throws LeaveTypeNotFoundException;
}
