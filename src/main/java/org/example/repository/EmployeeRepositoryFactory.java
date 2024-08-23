package org.example.repository;

import org.example.enums.StorageType;
import org.example.repository.impl.DatabaseEmployeeRepositoryImpl;
import org.example.repository.impl.FileEmployeeRepositoryImpl;
import org.example.repository.impl.InMemoryEmployeeRepositoryImpl;

public class EmployeeRepositoryFactory {
    private static EmployeeRepository inMemoryInstance;
    private static EmployeeRepository fileInstance;
    private static EmployeeRepository databaseInstance;

    //maps
    public static EmployeeRepository getRepository(StorageType storageType) {
        if (StorageType.IN_MEMORY.equals(storageType)) {
            if (inMemoryInstance == null) {
                inMemoryInstance = InMemoryEmployeeRepositoryImpl.getInstance();
            }
            return inMemoryInstance;
        } else if (StorageType.IN_FILE.equals(storageType)) {
            if (fileInstance == null) {
                fileInstance = FileEmployeeRepositoryImpl.getInstance();
            }
            return fileInstance;
        } else {
            if (databaseInstance == null) {
                databaseInstance = DatabaseEmployeeRepositoryImpl.getInstance();
            }
            return databaseInstance;
        }
    }
}
