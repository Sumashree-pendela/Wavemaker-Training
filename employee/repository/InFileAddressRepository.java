package main.java.com.employee.repository;

import main.java.com.employee.exception.AddressNotFoundException;
import main.java.com.employee.model.Address;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class InFileAddressRepository implements AddressRepository {

    String FILE_PATH = "C:\\Users\\sumashreep_700058\\Desktop\\addData.txt";

    private static InFileAddressRepository instance;

    private InFileAddressRepository() {
        System.out.println("InFile Address instance created");
    }

    public static InFileAddressRepository getInstance() {
        if (instance == null) {
            instance = new InFileAddressRepository();
        }
        return instance;
    }

    @Override
    public Address getById(int addrId) throws AddressNotFoundException {
        Map<Integer, Address> addressMap = getAllAddresses();
        Address address = addressMap.get(addrId);
        if (address != null) {
            return address;
        } else {
            throw new AddressNotFoundException("Address with Id: " + addrId + " is not found");
        }
    }

    @Override
    public void addAddress(Address address) throws IOException {

        try (FileWriter fileWriter = new FileWriter(FILE_PATH, true);
             BufferedWriter bufferedWriter = new BufferedWriter(fileWriter)) {

            String addressData = address.getId() + ", " + address.getLocation() + ", " + address.getPin();
            bufferedWriter.write(addressData);
            bufferedWriter.newLine();
            System.out.println("Data successfully inserted into file");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Map<Integer, Address> getAllAddresses() throws AddressNotFoundException {
        Map<Integer, Address> addressMap = new HashMap<>();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] data = line.split(", ");

                Address address = new Address(Integer.parseInt(data[0]), data[1], Integer.parseInt(data[2]));
                addressMap.put(address.getId(), address);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return addressMap;
    }

    @Override
    public String updateAddress(int id, Address address) throws AddressNotFoundException {
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
        Map<Integer, Address> addressMap = getAllAddresses();
        if (addressMap.containsKey(id)) {
            addressMap.remove(id);
            saveAllAddresses(addressMap);
            System.out.println("Deleted Address successfully");
        } else {
            throw new AddressNotFoundException("Address with Id: " + id + " doesn't exist");
        }
    }

    private void saveAllAddresses(Map<Integer, Address> addressMap) {
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
