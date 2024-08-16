package com.wavemaker.vehicle;

public  class Car extends Vehicle{
    private static final int TOTAL_GEARS = 5;
    private static final int SEATING_CAPACITY =5;

    public Car(String number, String color, FuelType fuel) {
        super(TOTAL_GEARS, number, color, SEATING_CAPACITY, fuel);
    }
}