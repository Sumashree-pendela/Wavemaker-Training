package main.java.com.employee.dto;

public class AddressRequest {

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
    public String toString() {
        return "AddressRequest{" +
                "id=" + id +
                ", location='" + location + '\'' +
                ", pin=" + pin +
                '}';
    }
}
