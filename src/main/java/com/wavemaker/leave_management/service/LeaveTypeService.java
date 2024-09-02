package com.wavemaker.leave_management.service;

import com.wavemaker.leave_management.dto.LeaveType;
import com.wavemaker.leave_management.enums.LeaveName;
import com.wavemaker.leave_management.exception.LeaveTypeNotFoundException;

public interface LeaveTypeService {
    LeaveType getById(int id) throws LeaveTypeNotFoundException;

    LeaveType getByName(LeaveName leaveName) throws LeaveTypeNotFoundException;
}
