package main.java.com.employee.service;

import main.java.com.employee.exception.AddressNotFoundException;
import main.java.com.employee.model.Address;
import main.java.com.employee.repository.AddressRepository;
import main.java.com.employee.repository.AddressRepositoryFactory;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class AddressServiceImpl implements AddressService {

    AddressRepository repository = null;

    @Override
    public Address getByAddressId(int addrId, boolean flag) throws AddressNotFoundException {
        repository = AddressRepositoryFactory.getRepository(flag);
        System.out.println(repository.getById(addrId) + "...add in add");
        return repository.getById(addrId);
    }

    @Override
    public void addAddress(Address address, boolean flag) throws IOException {

        repository = AddressRepositoryFactory.getRepository(flag);

        repository.addAddress(address);
    }

    @Override
    public List<Address> getAllAddresses(boolean flag) throws AddressNotFoundException {
        repository = AddressRepositoryFactory.getRepository(flag);

        Map<Integer, Address> addressMap =  repository.getAllAddresses();

        return addressMap.values().stream().toList();
    }

    @Override
    public String updateAddress(int id, Address address, boolean flag) throws AddressNotFoundException {
        repository = AddressRepositoryFactory.getRepository(flag);

        return repository.updateAddress(id, address);
    }

    @Override
    public void deleteAddress(int id, boolean flag) throws AddressNotFoundException {

        repository = AddressRepositoryFactory.getRepository(flag);

        repository.deleteAddress(id);
    }
}
