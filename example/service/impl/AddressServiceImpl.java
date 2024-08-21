package org.example.service.impl;

import org.example.dto.AddressRequest;
import org.example.enums.StorageType;
import org.example.exception.AddressNotFoundException;
import org.example.model.Address;
import org.example.repository.AddressRepository;
import org.example.repository.AddressRepositoryFactory;
import org.example.service.AddressService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class AddressServiceImpl implements AddressService {
    private static final Logger logger = LoggerFactory.getLogger(AddressServiceImpl.class);

    AddressRepository repository = null;

    public AddressServiceImpl(StorageType storageType) {
        repository = AddressRepositoryFactory.getRepository(storageType);
    }

    @Override
    public Address getByAddressId(int addrId) throws AddressNotFoundException {
        logger.debug("Getting address by id: {}", addrId);

        System.out.println(repository.getById(addrId) + "...add in add");
        return repository.getById(addrId);
    }

    @Override
    public int addAddress(AddressRequest addressRequest) throws IOException, AddressNotFoundException {
        logger.debug("Adding Address with details {} ", addressRequest);



       return repository.addAddress(addressRequest);
    }

    @Override
    public List<Address> getAllAddresses() throws AddressNotFoundException {
        logger.debug("Retrieving all addresses");

        Map<Integer, Address> addressMap = repository.getAllAddresses();

        return addressMap.values().stream().toList();
    }

    @Override
    public String updateAddress(int id, AddressRequest addressRequest) throws AddressNotFoundException {
        logger.debug("Updating address with {} with {}", id, addressRequest);

        return repository.updateAddress(id, addressRequest);
    }

    @Override
    public void deleteAddress(int id) throws AddressNotFoundException {
        logger.debug("Deleting address with id {}", id);

        repository.deleteAddress(id);
    }
}
