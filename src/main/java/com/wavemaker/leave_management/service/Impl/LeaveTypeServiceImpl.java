package com.wavemaker.leave_management.service.Impl;

import com.wavemaker.leave_management.dto.LeaveType;
import com.wavemaker.leave_management.enums.LeaveName;
import com.wavemaker.leave_management.exception.LeaveTypeNotFoundException;
import com.wavemaker.leave_management.repository.Impl.LeaveTypeRepositoryImpl;
import com.wavemaker.leave_management.repository.LeaveTypeRepository;
import com.wavemaker.leave_management.service.LeaveTypeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LeaveTypeServiceImpl implements LeaveTypeService {
    LeaveTypeRepository repository = LeaveTypeRepositoryImpl.getInstance();

    private static final Logger logger = LoggerFactory.getLogger(LeaveTypeServiceImpl.class);

    @Override
    public LeaveType getById(int id) throws LeaveTypeNotFoundException {
        logger.debug("Getting leave Type by id: {}", id);
        return repository.getById(id);
    }

    @Override
    public LeaveType getByName(LeaveName leaveName) throws LeaveTypeNotFoundException {
        logger.debug("Getting leave type by name: {}", leaveName);
        return repository.getByName(leaveName);
    }
}
