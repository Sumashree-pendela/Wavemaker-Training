package com.wavemaker.vehicle;

public  abstract class ElectricVehicle extends Vehicle{
    private int batteryCapacity;
    private String number;

    public ElectricVehicle(int totalGears, String number, String color, int seatingCapacity, FuelType fuel, int batteryCapacity) {
        super(totalGears, number, color, seatingCapacity, fuel);
        this.batteryCapacity = batteryCapacity;
        this.number = number;
    }

    void getBatteryCapacity() {
        System.out.println("Battery Capacity of " + number + " is :"  + batteryCapacity);
    }

}
