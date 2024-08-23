package org.example.model;

import java.util.Objects;

public class Employee {

    private int id;
    private String name;
    private String email;
    private int addressId;
    private Address address;

    public Employee() {
    }

    public Employee(int id, String name, String email, int addressId, Address address) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.addressId = addressId;
        this.address = address;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getAddressId() {
        return addressId;
    }

    public void setAddressId(int addressId) {
        this.addressId = addressId;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Employee employee = (Employee) o;
        return id == employee.id && addressId == employee.addressId && Objects.equals(name, employee.name) && Objects.equals(email, employee.email) && Objects.equals(address, employee.address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, email, addressId, address);
    }

    public String toString() {
        return String.format("Employee [ID: %d, Name: %s, Email: %s, Address ID: %d, Address: %s]",
                id, name, email, addressId, address.toString());
    }
}
