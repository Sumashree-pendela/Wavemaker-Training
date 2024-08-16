package com.wavemaker.vehicle;

public class Bike extends Vehicle {
    private static final int TOTAL_GEARS = 5;
    private static final int SEATING_CAPACITY =2;
    private String number;

    Bike( String color, String number ) {
        super(TOTAL_GEARS,number,color,SEATING_CAPACITY,FuelType.PETROL);
        this.number=number;
    }

    @Override
    public void moveBackward() {
        throw new RuntimeException(number +" has no Backward direction");
    }


}
