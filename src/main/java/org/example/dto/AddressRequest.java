package org.example.dto;

public class AddressRequest {

    private String location;
    private int pin;

    public AddressRequest(String location, int pin) {
        this.location = location;
        this.pin = pin;
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
        return "AddressRequest{" +
                ", location='" + location + '\'' +
                ", pin=" + pin +
                '}';
    }
}
