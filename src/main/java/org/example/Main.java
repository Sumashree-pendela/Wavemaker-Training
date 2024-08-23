package org.example;

import org.example.controller.EmployeeController;
import org.example.dto.EmployeeRequest;
import org.example.enums.StorageType;
import org.example.exception.AddressNotFoundException;
import org.example.exception.EmployeeNotFoundException;
import org.example.model.Employee;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class Main {

    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {


        Scanner sc = new Scanner(System.in);

        System.out.println("Enter -- 1 -In File Application,  2 - In Memory Application, 3 - In Database");
        int num = sc.nextInt();

        StorageType storageType = null;

        if(num == 1) {
            storageType = StorageType.IN_FILE;
        } else if (num == 2) {
            storageType = StorageType.IN_MEMORY;
        } else if (num == 3) {
            storageType = StorageType.DATABASE;
        }

        EmployeeController controller = new EmployeeController(storageType);
        EmployeeRequest employee = new EmployeeRequest();
        while (true) {
            System.out.println("Menu Options : ");
            System.out.println("0 Exit" +
                    "1. Get Employee" +
                    "2. Add Employee" +
                    "3.Get All Employees" +
                    "4.Update Employee" +
                    "5.Delete Employee");

            int menu = sc.nextInt();

            switch (menu) {
                case 0:
                    return;
                case 1:
                    System.out.println("Enter Employee Id: ");
                    int empId = sc.nextInt();

                    Employee emp = null;
                    try {
                        emp = controller.getById(empId);
                    } catch (EmployeeNotFoundException | AddressNotFoundException e) {
                        logger.debug("Exception {} ", e.getMessage());

                    }
                    System.out.println(emp);
                    break;
                case 2:
                    System.out.println("Enter all employee details:");
                    System.out.print("Enter Employee name: ");
                    employee.setName(sc.next());
                    System.out.print("Enter employee email: ");
                    employee.setEmail(sc.next());
                    System.out.print("Enter employee address Location: ");
                    employee.setLocation(sc.next());
                    System.out.print("Enter employee address pin: ");
                    employee.setPin(sc.nextInt());

                    try {
                        controller.add(employee);
                    } catch (IOException | AddressNotFoundException | EmployeeNotFoundException e) {
                        logger.debug("Exception {} ", e.getMessage());
                    }
                    break;
                case 3:
                    List<Employee> employeeList = null;
                    try {
                        employeeList = controller.getAll();
                    } catch (EmployeeNotFoundException | AddressNotFoundException e) {
                        logger.debug("Exception {} ", e.getMessage());
                    }
                    System.out.println(employeeList);
                    break;
                case 4:
                    System.out.println("Enter Employee Id to update:");

                    int employeeId = sc.nextInt();
                    System.out.println("Enter all other details to update");
                    System.out.println("Enter Employee name:");
                    employee.setName(sc.next());
                    System.out.println("Enter employee email: ");
                    employee.setEmail(sc.next());
                    System.out.println("Enter employee address Location: ");
                    employee.setLocation(sc.next());
                    System.out.println("Enter employee address pin: ");
                    employee.setPin(sc.nextInt());

                    try {
                        controller.update(employeeId, employee);
                    } catch (AddressNotFoundException | EmployeeNotFoundException e) {
                        logger.debug("Exception {} ", e.getMessage());
                    }
                    break;
                case 5:
                    System.out.println("Enter employee Id to delete:");
                    try {
                        controller.delete(sc.nextInt());
                    } catch (EmployeeNotFoundException | AddressNotFoundException e) {
                        logger.debug("Exception occurred  while deleting{} ", e.getMessage());
                    }
                    break;
                default:
                    System.out.println("Enter options in the menu");
                    break;
            }
        }
    }
}


