package org.example.repository.impl;

import org.example.dto.AddressRequest;
import org.example.exception.AddressNotFoundException;
import org.example.model.Address;
import org.example.repository.AddressRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class InFileAddressRepositoryImpl implements AddressRepository {
    private static final Logger logger = LoggerFactory.getLogger(InFileAddressRepositoryImpl.class);


    String FILE_PATH = "C:\\Users\\sumashreep_700058\\Desktop\\addData.txt";

    private static InFileAddressRepositoryImpl instance;

    private InFileAddressRepositoryImpl() {
        System.out.println("InFile Address instance created");
    }

    public static InFileAddressRepositoryImpl getInstance() {
        if (instance == null) {
            instance = new InFileAddressRepositoryImpl();
        }
        return instance;
    }

    @Override
    public Address getById(int addressId) throws AddressNotFoundException {
        logger.debug("Getting Address by id {}", addressId);

        Map<Integer, Address> addressMap = getAllAddresses();
        Address address = addressMap.get(addressId);
        if (address != null) {
            return address;
        } else {
            throw new AddressNotFoundException("Address with Id: " + addressId + " is not found");
        }
    }

    @Override
    public int addAddress(AddressRequest address) throws IOException {
        logger.debug("Adding address by with details {}", address);

        int addressId = 0;
        try (FileWriter fileWriter = new FileWriter(FILE_PATH, true);
             BufferedWriter bufferedWriter = new BufferedWriter(fileWriter)) {

            int maxId = 0;
            Map<Integer, Address> addresses = getAllAddresses();

            if (addresses != null && !addresses.isEmpty()) {
                for (Integer id : addresses.keySet()) {
                    if (id > maxId) {
                        maxId = id;
                    }
                }
            }

             addressId = maxId + 1;

            String addressData = addressId + ", " + address.getLocation() + ", " + address.getPin();
            bufferedWriter.write(addressData);
            bufferedWriter.newLine();
            System.out.println("Data successfully inserted into file");

        } catch (IOException | AddressNotFoundException e) {
            e.printStackTrace();
        }
        return addressId;
    }

    @Override
    public Map<Integer, Address> getAllAddresses() throws AddressNotFoundException {
        logger.debug("Getting all addresses ");

        Map<Integer, Address> addressMap = new HashMap<>();

        File file = new File(FILE_PATH);
        if (!file.exists() || file.length() == 0) {
            logger.debug("Address file is empty");
            return addressMap;
        }

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] data = line.split(", ");
                Arrays.stream(data).forEach(content-> {
                    System.out.println("Content:"  +  content);
                });

                Address address = new Address(Integer.parseInt(data[0]), data[1], Integer.parseInt(data[2]));
                addressMap.put(address.getId(), address);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return addressMap;
    }

    @Override
    public String updateAddress(int id, AddressRequest address) throws AddressNotFoundException {
        logger.debug("Updating address by with details {}", address);

        Map<Integer, Address> addressMap = getAllAddresses();
        Address existingAddress = addressMap.get(id);
        if (existingAddress != null) {
            existingAddress.setLocation(address.getLocation());
            existingAddress.setPin(address.getPin());
            saveAllAddresses(addressMap);
            System.out.println("Updated Address successfully");
            return "updated";
        } else {
            throw new AddressNotFoundException("Address with Id: " + id + " doesn't exist");
        }
    }

    @Override
    public void deleteAddress(int id) throws AddressNotFoundException {
        logger.debug("Deleting Address by with id {}", id);
        Map<Integer, Address> addressMap = getAllAddresses();
        if (addressMap.containsKey(id)) {
            addressMap.remove(id);
            saveAllAddresses(addressMap);
            logger.debug("Deleted Address successfully");
        } else {
            throw new AddressNotFoundException("Address with Id: " + id + " doesn't exist");
        }
    }

    private void saveAllAddresses(Map<Integer, Address> addressMap) {
        logger.debug("Saving all addresses");
        try (FileWriter fileWriter = new FileWriter(FILE_PATH, false);
             BufferedWriter bufferedWriter = new BufferedWriter(fileWriter)) {

            for (Address address : addressMap.values()) {
                String addressData = address.getId() + ", " + address.getLocation() + ", " + address.getPin();
                bufferedWriter.write(addressData);
                bufferedWriter.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
