package com.wavemaker.leave_management.repository.Impl;

import com.wavemaker.leave_management.dto.Employee;
import com.wavemaker.leave_management.dto.Leave;
import com.wavemaker.leave_management.dto.LeaveTracker;
import com.wavemaker.leave_management.dto.LeaveVO;
import com.wavemaker.leave_management.enums.LeaveName;
import com.wavemaker.leave_management.enums.LeaveStatus;
import com.wavemaker.leave_management.exception.LoginNotFoundException;
import com.wavemaker.leave_management.model.LeaveRequest;
import com.wavemaker.leave_management.repository.EmployeeRepository;
import com.wavemaker.leave_management.repository.LeaveRepository;
import com.wavemaker.leave_management.util.DatabaseConnector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class LeaveRepositoryImpl implements LeaveRepository {

    private static final Logger logger = LoggerFactory.getLogger(LeaveRepositoryImpl.class);

    private static final String CREATE_LEAVE = "INSERT INTO EMPLOYEE_LEAVE(APPLIED_BY, DATE_FROM, DATE_TO, LEAVE_ID, " +
            "  STATUS, REASON, CREATED_AT) VALUES(?,?,?,?,?,?,?)";

    private static final String GET_MY_LEAVES = "SELECT L.ID, L.APPLIED_BY, L.DATE_FROM, L.DATE_TO, L.LEAVE_ID, LT.NAME," +
            " L.STATUS, L.REASON, L.CREATED_AT\n" +
            " FROM EMPLOYEE_LEAVE L INNER JOIN LEAVE_TYPE LT ON LT.ID = L.LEAVE_ID WHERE APPLIED_BY = ? ORDER BY L.CREATED_AT DESC";

    private static final String UPDATE_LEAVE = "UPDATE EMPLOYEE_LEAVE SET STATUS = ? WHERE ID = ?";

    private static final String GET_EMPLOYEE_LEAVES_BY_MANAGER_ID = "SELECT L.ID, L.APPLIED_BY, E.NAME, L.DATE_FROM, \n" +
            " L.DATE_TO, L.LEAVE_ID, LT.NAME, L.STATUS, L.REASON,L.CREATED_AT \n" +
            " FROM EMPLOYEE E INNER JOIN EMPLOYEE_LEAVE L  ON E.ID = L.APPLIED_BY INNER JOIN LEAVE_TYPE LT ON " +
            " LT.ID = L.LEAVE_ID  WHERE MANAGER_ID = ? ORDER BY L.CREATED_AT DESC";

    private static final String GET_LEAVE_COUNT = "SELECT SUM(CASE \n" +
            " WHEN DATE_FROM = DATE_TO THEN 1\n" +
            " ELSE DATEDIFF(DATE_TO, DATE_FROM) + 1 END) AS TOTAL_LEAVE_COUNT FROM EMPLOYEE_LEAVE WHERE STATUS != 'REJECTED' AND APPLIED_BY = ? AND LEAVE_ID = ?";

    private static final String GET_LEAVE_TRACKER_BY_USER_ID = "SELECT LT.NAME, \n" +
            "    COALESCE(SUM(CASE \n" +
            "        WHEN EL.DATE_FROM = EL.DATE_TO THEN 1 " +
            "        ELSE DATEDIFF(EL.DATE_TO, EL.DATE_FROM) + 1 " +
            "    END), 0) AS usedLeaveDays, " +
            "    LT.COUNT AS totalLeaveCount FROM LEAVE_TYPE LT " +
            " LEFT JOIN EMPLOYEE_LEAVE EL ON EL.LEAVE_ID = LT.ID AND EL.APPLIED_BY = ? " +
            " AND EL.STATUS != 'REJECTED' WHERE (LT.GENDER = 'All' OR LT.GENDER = (SELECT GENDER FROM EMPLOYEE WHERE ID = ?)) GROUP BY LT.NAME, LT.COUNT";

    private static LeaveRepositoryImpl instance;

    private LeaveRepositoryImpl() {
    }

    public static synchronized LeaveRepositoryImpl getInstance() {
        if (instance == null) {
            instance = new LeaveRepositoryImpl();
        }
        return instance;
    }

    EmployeeRepository employeeRepository = EmployeeRepositoryImpl.getInstance();

    @Override
    public LeaveVO create(LeaveRequest leaveRequest) {
        logger.debug("Adding leave_management into database with details {} ", leaveRequest);
        LeaveVO leaveVO = new LeaveVO();
        try {
            PreparedStatement preparedStatement = DatabaseConnector.connect().prepareStatement(CREATE_LEAVE, PreparedStatement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1, leaveRequest.getAppliedBy());
            preparedStatement.setDate(2, Date.valueOf(leaveRequest.getDateFrom()));
            preparedStatement.setDate(3, Date.valueOf(leaveRequest.getDateTo()));
            preparedStatement.setInt(4, leaveRequest.getLeaveId());
            preparedStatement.setString(5, LeaveStatus.PENDING.toString());
            preparedStatement.setString(6, leaveRequest.getReason());
            preparedStatement.setTimestamp(7, new java.sql.Timestamp(System.currentTimeMillis()));
            int rows = preparedStatement.executeUpdate();
            logger.debug("Inserted into employee_leave");

            if (rows > 0) {
                ResultSet resultSet = preparedStatement.getGeneratedKeys();
                if (resultSet.next()) {
                    leaveVO.setId(resultSet.getInt(1));
                    leaveVO.setAppliedBy(leaveRequest.getAppliedBy());
                    leaveVO.setDateFrom(leaveRequest.getDateFrom());
                    leaveVO.setDateTo(leaveRequest.getDateTo());
                    leaveVO.setLeaveId(leaveRequest.getLeaveId());
                    leaveVO.setLeaveName(leaveRequest.getLeaveName());
                    leaveVO.setStatus(LeaveStatus.PENDING);
                    leaveVO.setReason(leaveRequest.getReason());
                    leaveVO.setCreatedAt(new java.sql.Timestamp(System.currentTimeMillis()));
                    leaveVO.setAppliedDate(LocalDate.now());
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return leaveVO;
    }

    @Override
    public List<Leave> getEmployeeLeavesByManagerId(int userId) {
        logger.debug("Getting  employee leaves by id: {}", userId);
        List<Leave> employeeLeaveList = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = DatabaseConnector.connect().prepareStatement(GET_EMPLOYEE_LEAVES_BY_MANAGER_ID);
            preparedStatement.setInt(1, userId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                // Create a new Leave object for each row
                Leave leave = new Leave();
                leave.setId(resultSet.getInt(1));
                leave.setAppliedBy(resultSet.getInt(2));
                leave.setEmployeeName(resultSet.getString(3));
                leave.setDateFrom(resultSet.getDate(4).toLocalDate());
                leave.setDateTo(resultSet.getDate(5).toLocalDate());
                leave.setLeaveId(resultSet.getInt(6));
                leave.setLeaveName(LeaveName.valueOf(resultSet.getString(7)));
                leave.setStatus(LeaveStatus.valueOf(resultSet.getString(8)));
                leave.setReason(resultSet.getString(9));
                leave.setCreatedAt(resultSet.getTimestamp(10));
                leave.setAppliedDate(resultSet.getTimestamp(10).toLocalDateTime().toLocalDate());
                // Add the object to the list
                employeeLeaveList.add(leave);
            }
        } catch (SQLException e) {
            logger.debug(e.toString());
        }
        return employeeLeaveList;
    }

    @Override
    public List<Leave> getMyLeavesByUserId(int userId) throws LoginNotFoundException {
        Employee employee = employeeRepository.getById(userId);
        logger.debug("Getting leaves by id: {}", userId);
        List<Leave> leaveList = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = DatabaseConnector.connect().prepareStatement(GET_MY_LEAVES);
            preparedStatement.setInt(1, userId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                // Create a new Leave object for each row
                Leave leave = new Leave();
                leave.setId(resultSet.getInt(1));
                leave.setAppliedBy(resultSet.getInt(2));
                leave.setDateFrom(resultSet.getDate(3).toLocalDate());
                leave.setDateTo(resultSet.getDate(4).toLocalDate());
                leave.setLeaveId(resultSet.getInt(5));
                leave.setLeaveName(LeaveName.valueOf(resultSet.getString(6)));
                leave.setStatus(LeaveStatus.valueOf(resultSet.getString(7)));
                leave.setReason(resultSet.getString(8));
                leave.setCreatedAt(resultSet.getTimestamp(9));
                leave.setAppliedDate(resultSet.getTimestamp(9).toLocalDateTime().toLocalDate());
                leave.setEmployeeName(employee.getName());

                // Add the object to the list
                leaveList.add(leave);
            }
        } catch (SQLException e) {
            logger.debug(e.toString());
        }
        return leaveList;
    }

    @Override
    public void rejectLeave(int leaveId) {

        logger.debug("Updating leave with id: {}", leaveId);
        try {
            PreparedStatement preparedStatement = DatabaseConnector.connect().prepareStatement(UPDATE_LEAVE, PreparedStatement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, String.valueOf(LeaveStatus.REJECTED));
            preparedStatement.setInt(2, leaveId);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            logger.debug(e.toString());
        }
    }

    @Override
    public void approveLeave(int leaveId) {
        logger.debug("Updating leave with id: {}", leaveId);
        try {
            PreparedStatement preparedStatement = DatabaseConnector.connect().prepareStatement(UPDATE_LEAVE, PreparedStatement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, String.valueOf(LeaveStatus.APPROVED));
            preparedStatement.setInt(2, leaveId);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            logger.debug(e.toString());
        }
    }

    @Override
    public int getLeaveCountByUserId(int userId, int leaveId) {
        int count = 0;
        try {
            PreparedStatement preparedStatement = DatabaseConnector.connect().prepareStatement(GET_LEAVE_COUNT);
            preparedStatement.setInt(1, userId);
            preparedStatement.setInt(2, leaveId);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                count = resultSet.getInt(1);
            }

        } catch (SQLException e) {
            logger.debug(e.toString());
        }
        return count;
    }

    @Override
    public List<LeaveTracker> getLeaveTrackerByUserId(int userId) {
        logger.debug("Getting leave Tracker by id: {}", userId);
        List<LeaveTracker> leaveTrackerList = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = DatabaseConnector.connect().prepareStatement(GET_LEAVE_TRACKER_BY_USER_ID);
            preparedStatement.setInt(1, userId);
            preparedStatement.setInt(2, userId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                // Create a new Leave object for each row
                LeaveTracker leaveTracker = new LeaveTracker();
                leaveTracker.setLeaveName(LeaveName.valueOf(resultSet.getString(1)));
                leaveTracker.setUsedLeaves(resultSet.getInt(2));
                leaveTracker.setTotalLeaves(resultSet.getInt(3));
                leaveTracker.setLeavesLeft(resultSet.getInt(3) - resultSet.getInt(2));

                // Add the object to the list
                leaveTrackerList.add(leaveTracker);
            }
        } catch (SQLException e) {
            logger.debug(e.toString());
        }
        return leaveTrackerList;
    }
}
