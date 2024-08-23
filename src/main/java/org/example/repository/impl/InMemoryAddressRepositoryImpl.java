package org.example.repository.impl;


import org.example.dto.AddressRequest;
import org.example.exception.AddressNotFoundException;
import org.example.model.Address;
import org.example.repository.AddressRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class InMemoryAddressRepositoryImpl implements AddressRepository {
    private static final Logger logger = LoggerFactory.getLogger(InMemoryAddressRepositoryImpl.class);


    private static InMemoryAddressRepositoryImpl instance;
    private final Map<Integer, Address> addressMap;

    private InMemoryAddressRepositoryImpl() {
        addressMap = new HashMap<>();
        System.out.println("InMemory Address instance created");
    }

    public static InMemoryAddressRepositoryImpl getInstance() {
        if (instance == null) {
            instance = new InMemoryAddressRepositoryImpl();
        }
        return instance;
    }

    @Override
    public Address getById(int addressId) throws AddressNotFoundException {
        logger.debug("Getting Address by id {}", addressId);

        Address address = addressMap.get(addressId);
        if (address != null) {
            return address;
        } else {
            throw new AddressNotFoundException("Address with Id: " + addressId + " is not found");
        }
    }

    @Override
    public int addAddress(AddressRequest addressRequest) throws IOException {
        logger.debug("Adding address with details {}", addressRequest);

        int maxId = 0;

        Map<Integer, Address> addresses = getAllAddresses();

        if (addresses != null && !addresses.isEmpty()) {
            for (Integer id : addresses.keySet()) {
                if (id > maxId) {
                    maxId = id;
                }
            }
        }

        int addressId = maxId + 1;
        Address address = new Address(addressId, addressRequest.getLocation(), addressRequest.getPin());

        addressMap.put(addressId, address);
        logger.debug("Address Added successfully {} ", address);

        return addressId;
    }

    @Override
    public Map<Integer, Address> getAllAddresses() {
        logger.debug("Getting all addresses ");

        return addressMap;
    }

    @Override
    public String updateAddress(int id, AddressRequest address) throws AddressNotFoundException {
        logger.debug("Updating address by with details {}", address);

        if (addressMap.containsKey(id)) {
            Address existingAddress = addressMap.get(id);
            existingAddress.setLocation(address.getLocation());
            existingAddress.setPin(address.getPin());
            logger.debug("Address updated successfully");

            return "Address updated";
        } else {
            throw new AddressNotFoundException("Address with Id: " + id + " doesn't exist");
        }
    }

    @Override
    public void deleteAddress(int id) throws AddressNotFoundException {
        logger.debug("Deleting Address by with id {}", id);

        if (addressMap.containsKey(id)) {
            addressMap.remove(id);
            logger.debug("Address with Id: {} deleted successfully", id);
        } else {
            throw new AddressNotFoundException("Address with Id: " + id + " doesn't exist");
        }
    }
}
