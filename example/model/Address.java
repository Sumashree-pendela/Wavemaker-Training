package org.example.model;

import java.util.Objects;

public class Address {
    private int id;
    private String location;
    private int pin;

    public Address() {
    }

    public Address(int id, String location, int pin) {
        this.id = id;
        this.location = location;
        this.pin = pin;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Address address = (Address) o;
        return id == address.id && pin == address.pin && Objects.equals(location, address.location);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, location, pin);
    }

    @Override
    public String toString() {
        return "Address{" +
                "id=" + id +
                ", location='" + location + '\'' +
                ", pin=" + pin +
                '}';
    }
}

