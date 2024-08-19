package main.java.com.employee.model;

import java.util.Objects;

public class AddressEntity {
    private int id;
    private String location;
    private int pin;


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
        AddressEntity that = (AddressEntity) o;
        return id == that.id && pin == that.pin && Objects.equals(location, that.location);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, location, pin);
    }

    @Override
    public String toString() {
        return "AddressEntity{" +
                "id=" + id +
                ", location='" + location + '\'' +
                ", pin=" + pin +
                '}';
    }
}
