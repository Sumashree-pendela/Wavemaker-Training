package org.example.repository;

import org.example.enums.StorageType;
import org.example.repository.impl.DatabaseAddressRepositoryImpl;
import org.example.repository.impl.InFileAddressRepositoryImpl;
import org.example.repository.impl.InMemoryAddressRepositoryImpl;

public class AddressRepositoryFactory {
    private static AddressRepository inMemoryInstance;
    private static AddressRepository inFileInstance;
    private static AddressRepository databaseInstance;

    public static AddressRepository getRepository(StorageType storageType) {
        if (StorageType.IN_MEMORY.equals(storageType)) {
            if (inMemoryInstance == null) {
                inMemoryInstance = InMemoryAddressRepositoryImpl.getInstance();
            }
            return inMemoryInstance;
        } else if (StorageType.IN_FILE.equals(storageType)) {
            if (inFileInstance == null) {
                inFileInstance = InFileAddressRepositoryImpl.getInstance();
            }
            return inFileInstance;
        } else {
            if (databaseInstance == null) {
                databaseInstance = DatabaseAddressRepositoryImpl.getInstance();
            }
            return databaseInstance;
        }
    }
}
