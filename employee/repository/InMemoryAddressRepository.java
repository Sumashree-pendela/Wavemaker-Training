package main.java.com.employee.repository;

import main.java.com.employee.exception.AddressNotFoundException;
import main.java.com.employee.model.Address;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class InMemoryAddressRepository implements AddressRepository {

    private static InMemoryAddressRepository instance;
    private final Map<Integer, Address> addressMap;

    private InMemoryAddressRepository() {
        addressMap = new HashMap<>();
        System.out.println("InMemory Address instance created");
    }

    public static InMemoryAddressRepository getInstance() {
        if (instance == null) {
            instance = new InMemoryAddressRepository();
        }
        return instance;
    }

    @Override
    public Address getById(int addressId) throws AddressNotFoundException {
        Address address = addressMap.get(addressId);
        if (address != null) {
            return address;
        } else {
            throw new AddressNotFoundException("Address with Id: " + addressId + " is not found");
        }
    }

    @Override
    public void addAddress(Address address) throws IOException {
        addressMap.put(address.getId(), address);
        System.out.println("Address Added successfully");
    }

    @Override
    public Map<Integer, Address> getAllAddresses() throws AddressNotFoundException {
        if (!addressMap.isEmpty()) {
            return addressMap;
        } else {
            throw new AddressNotFoundException("There are no addresses to return");
        }
    }

    @Override
    public String updateAddress(int id, Address address) throws AddressNotFoundException {
        if (addressMap.containsKey(id)) {
            Address existingAddress = addressMap.get(id);
            existingAddress.setLocation(address.getLocation());
            existingAddress.setPin(address.getPin());
            return "Address updated";
        } else {
            throw new AddressNotFoundException("Address with Id: " + id + " doesn't exist");
        }
    }

    @Override
    public void deleteAddress(int id) throws AddressNotFoundException {
        if (addressMap.containsKey(id)) {
            addressMap.remove(id);
            System.out.println("Address with Id: " + id + " deleted successfully");
        } else {
            throw new AddressNotFoundException("Address with Id: " + id + " doesn't exist");
        }
    }
}
