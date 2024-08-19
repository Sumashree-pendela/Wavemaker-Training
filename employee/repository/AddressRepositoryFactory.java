package main.java.com.employee.repository;

public class AddressRepositoryFactory {
    private static InMemoryAddressRepository inMemoryInstance;
    private static InFileAddressRepository inFileInstance;

    public static AddressRepository getRepository(boolean flag) {
        if (flag) {
            if (inMemoryInstance == null) {
                inMemoryInstance = InMemoryAddressRepository.getInstance();
            }
            return inMemoryInstance;
        } else {
            if (inFileInstance == null) {
                inFileInstance = InFileAddressRepository.getInstance();
            }
            return inFileInstance;
        }
    }
}
