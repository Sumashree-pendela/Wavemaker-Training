package org.example.repository.impl;

import org.example.dto.AddressRequest;
import org.example.exception.AddressNotFoundException;
import org.example.model.Address;
import org.example.repository.AddressRepository;
import org.example.util.DatabaseConnector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class DatabaseAddressRepositoryImpl implements AddressRepository {
    private static final Logger logger = LoggerFactory.getLogger(DatabaseAddressRepositoryImpl.class);

    private static DatabaseAddressRepositoryImpl instance;

    private DatabaseAddressRepositoryImpl() {
        System.out.println("Database Address instance created");
    }

    public static DatabaseAddressRepositoryImpl getInstance() {
        if (instance == null) {
            instance = new DatabaseAddressRepositoryImpl();
        }
        return instance;
    }

    @Override
    public Address getById(int addrId) throws AddressNotFoundException {
        logger.debug("Getting address by id: {}", addrId);
        String selectQuery = "select * from address where id = ?";
        Address address = new Address();
        try {
            PreparedStatement preparedStatement = DatabaseConnector.connect().prepareStatement(selectQuery);
            preparedStatement.setInt(1, addrId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                address.setId(addrId);
                address.setLocation(resultSet.getString(2));
                address.setPin(resultSet.getInt(3));
                return address;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public int addAddress(AddressRequest address) throws IOException, AddressNotFoundException {
        logger.debug("Adding address into database with details {}", address);
        String query = "insert into address(location, pin) values(?,?)";
        int addressId = 0;
        try {
            PreparedStatement preparedStatement = DatabaseConnector.connect().prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, address.getLocation());
            preparedStatement.setInt(2, address.getPin());
            int rows = preparedStatement.executeUpdate();
            System.out.println("Rows..." + rows);

            if(rows > 0) {
                ResultSet resultSet = preparedStatement.getGeneratedKeys();
                if (resultSet.next()) {
                    addressId = resultSet.getInt(1);
                }
            }
            System.out.println("Address Id......: " + addressId);
            System.out.println("Inserted into address.....");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return addressId;
    }

    @Override
    public Map<Integer, Address> getAllAddresses() throws AddressNotFoundException {
        logger.debug("Get all addresses");
        String query = "select * from address";

        Map<Integer, Address> addressMap = new HashMap<>();

        try {
            PreparedStatement preparedStatement = DatabaseConnector.connect().prepareStatement(query);
            ResultSet resultset = preparedStatement.executeQuery(query);
            System.out.println(resultset);
            while (resultset.next()) {
                Address address = new Address();
                address.setId(resultset.getInt(1));
                address.setLocation(resultset.getString(2));
                address.setPin(resultset.getInt(3));

                addressMap.put(resultset.getInt(1), address);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return addressMap;
    }

    @Override
    public String updateAddress(int id, AddressRequest address) throws AddressNotFoundException {
        logger.debug("Updating address with details {}", address);
        String query = "update address set location = ?, pin = ? where id = ?";
        try {
            PreparedStatement preparedStatement = DatabaseConnector.connect().prepareStatement(query);
            preparedStatement.setString(1,address.getLocation());
            preparedStatement.setInt(2,address.getPin());
            preparedStatement.setInt(3,id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "Updated successfully";
    }

    @Override
    public void deleteAddress(int id) throws AddressNotFoundException {
        logger.debug("Deleting address with id : {}", id);
        String deleteQuery = "delete from address where id = ?";
        Address address = new Address();
        try {
            PreparedStatement preparedStatement = DatabaseConnector.connect().prepareStatement(deleteQuery);
            preparedStatement.setInt(1,id);
            preparedStatement.executeUpdate();
            System.out.println("Deleted address successfully");
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
