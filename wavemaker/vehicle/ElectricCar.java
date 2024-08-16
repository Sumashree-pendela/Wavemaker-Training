package com.wavemaker.vehicle;

public class ElectricCar extends ElectricVehicle{

    private static final int TOTAL_GEARS = 5;
    private static final int SEATING_CAPACITY =5;
    private String number;
    private boolean start = false;

    public ElectricCar(String number, String color, int batteryCapacity) {
        super(TOTAL_GEARS, number, color, SEATING_CAPACITY, null, batteryCapacity);
    }

}
