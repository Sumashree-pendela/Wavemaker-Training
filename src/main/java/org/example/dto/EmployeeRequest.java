package org.example.dto;

public class EmployeeRequest {
    private String name;
    private String email;
    private int addressId;
    private String location;
    private int pin;

    public EmployeeRequest() {
    }

    public EmployeeRequest(String name, String email, String location, int pin) {
        this.name = name;
        this.email = email;
        this.location = location;
        this.pin = pin;
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

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getPin() {
        return pin;
    }

    public void setPin(int pin) {
        this.pin = pin;
    }


    @Override
    public String toString() {
        return "EmployeeRequest{" +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", addressId=" + addressId +
                ", location='" + location + '\'' +
                ", pin=" + pin +
                '}';
    }
}
