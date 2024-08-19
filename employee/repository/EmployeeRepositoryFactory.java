package main.java.com.employee.repository;

public class EmployeeRepositoryFactory {
    private static InMemoryEmployeeRepository inMemoryInstance;
    private static FileEmployeeRepository fileInstance;

    public static EmployeeRepository getRepository(boolean flag) {
        if (flag) {
            if (inMemoryInstance == null) {
                inMemoryInstance = InMemoryEmployeeRepository.getInstance();
            }
            return inMemoryInstance;
        } else {
            if(fileInstance == null) {
                fileInstance = FileEmployeeRepository.getInstance();
            }
            return fileInstance;
        }
    }
}
