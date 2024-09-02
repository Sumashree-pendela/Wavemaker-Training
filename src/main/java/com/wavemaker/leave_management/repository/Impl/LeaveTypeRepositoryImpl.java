package com.wavemaker.leave_management.repository.Impl;

import com.wavemaker.leave_management.dto.LeaveType;
import com.wavemaker.leave_management.enums.LeaveName;
import com.wavemaker.leave_management.exception.LeaveTypeNotFoundException;
import com.wavemaker.leave_management.repository.LeaveTypeRepository;
import com.wavemaker.leave_management.util.DatabaseConnector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LeaveTypeRepositoryImpl implements LeaveTypeRepository {
    private static final Logger logger = LoggerFactory.getLogger(LeaveTypeRepositoryImpl.class);

    private static final String GET_LEAVE_TYPE_BY_ID = "SELECT * FROM LEAVE_TYPE WHERE ID = ?";
    private static final String GET_LEAVE_TYPE_BY_LEAVE_NAME = "SELECT * FROM LEAVE_TYPE WHERE NAME = ?";

    private static LeaveTypeRepositoryImpl instance;

    private LeaveTypeRepositoryImpl() {
    }

    public static synchronized LeaveTypeRepositoryImpl getInstance() {
        if (instance == null) {
            instance = new LeaveTypeRepositoryImpl();
        }
        return instance;
    }

    @Override
    public LeaveType getById(int id) throws LeaveTypeNotFoundException {
        logger.debug("Getting Leave Type Details by Id: {}", id);

        LeaveType leaveType = new LeaveType();

        try {
            PreparedStatement preparedStatement = DatabaseConnector.connect().prepareStatement(GET_LEAVE_TYPE_BY_ID);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                leaveType.setId(resultSet.getInt(1));
                leaveType.setName(LeaveName.valueOf(resultSet.getString(2)));
                leaveType.setCount(resultSet.getInt(3));
                leaveType.setGender(resultSet.getString(4));
            }
        } catch (SQLException e) {
            throw new LeaveTypeNotFoundException("No Leave type found");
        }
        return leaveType;
    }


    @Override
    public LeaveType getByName(LeaveName leaveName) throws LeaveTypeNotFoundException {
        logger.debug("Getting Leave Type Details by Name: {}", leaveName);

        LeaveType leaveType = new LeaveType();

        try {
            PreparedStatement preparedStatement = DatabaseConnector.connect().prepareStatement(GET_LEAVE_TYPE_BY_LEAVE_NAME);
            preparedStatement.setString(1, String.valueOf(leaveName));
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                leaveType.setId(resultSet.getInt(1));
                leaveType.setName(LeaveName.valueOf(resultSet.getString(2)));
                leaveType.setCount(resultSet.getInt(3));
                leaveType.setGender(resultSet.getString(4));
            }
        } catch (SQLException e) {
            throw new LeaveTypeNotFoundException("No Leave type found");
        }
        return leaveType;
    }
}
