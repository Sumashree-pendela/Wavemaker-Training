package com.wavemaker.vehicle;

public class ElectricBike extends ElectricVehicle{

    private static final int TOTAL_GEARS = 0;
    private static final int SEATING_CAPACITY =2;
    private String number;
    private boolean start = false;


    public ElectricBike(String number, String color, int batteryCapacity) {
        super(TOTAL_GEARS, number, color, SEATING_CAPACITY, null, batteryCapacity);
        this.number=number;
    }


    @Override
    public void moveBackward() {
        throw new RuntimeException(number + " has no Backward direction");
    }

    @Override
    public   void stopVehicle() {
        if (start == true) {
                System.out.println(number + " has stopped");
                start = false;
        } else {
            throw new RuntimeException(number + " has already stopped");
        }
    }

    @Override
    public int upGear() {
        throw new RuntimeException(number + " has no gears");
    }

    @Override
    public int downGear() {
        throw new RuntimeException(number + " has no gears");
    }

    @Override
    public  void moveForward() {
        if (start == false) {
            throw new RuntimeException("You need to start the " + number + " first for moving forward");
        } else {
                if (direction == Direction.FORWARD) {
                    throw new RuntimeException("You are already in the forward direction.");
                } else {
                    System.out.println("You are moving forward");
                    direction = Direction.FORWARD;
                }
        }
    }
}
