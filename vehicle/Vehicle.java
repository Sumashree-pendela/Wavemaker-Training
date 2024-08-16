package com.wavemaker.vehicle;

public class Vehicle {
    protected int currentGear = 0;
    private int totalGears;
    protected boolean start = false;
    private String number;
    private String color;
    private int seatingCapacity;
    private FuelType fuel;
    protected Direction direction;


    public Vehicle(int totalGears, String number, String color, int seatingCapacity, FuelType fuel) {
        this.totalGears = totalGears;
        this.number = number;
        this.color = color;
        this.seatingCapacity = seatingCapacity;
        this.fuel = fuel;
    }

    public void vehicleDescription() {
        System.out.println("It is a  " + number);
        System.out.println("seating capacity of " + number + " is " + seatingCapacity);
        System.out.println(" No of gears in the " + number + " = " + totalGears);
        System.out.println(number + " colour is  " + color);
        System.out.println(number + " numberplate is" + number);
        System.out.println("fuel type of " + number + " is " + fuel);
    }


    void moveForward() {
        if (start == false) {
            throw new RuntimeException("You need to start the " + number + " first for moving forward");
        } else {
            if (currentGear == totalGears && currentGear == 0) {

                throw new RuntimeException("You to change the gear");
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

    void moveBackward() {
        if (start == false) {

            throw new RuntimeException("You need to start the " + number + " first");
        } else {
            if (currentGear < totalGears) {
                throw new RuntimeException("You need to change the gear");
            } else {
                if (direction == Direction.BACKWARD) {
                    throw new RuntimeException("You are already in the backward direction");

                } else {
                    System.out.println(number + " is moving Backward");
                    direction = Direction.BACKWARD;
                }
            }
        }
    }

    void startVehicle() {
        if (start == false) {
            System.out.println(" You started the " + number + " ");
            start = true;
        } else {

            throw new RuntimeException(number + "  has already started");
        }
    }

    void stopVehicle() {
        if (start == true) {
            if (currentGear == 0) {

                System.out.println(number + " has stopped");
                start = false;
            } else {

                throw new RuntimeException("You need to change the gear to first");
            }
        } else {
            throw new RuntimeException(number + " has already stopped");


        }
    }

    void brake() {
        if (start == true) {
            if (direction == Direction.FORWARD || direction == Direction.BACKWARD) {

                System.out.println("You pressed brake");
                direction = Direction.NULL;
            } else {
                System.out.println();
                throw new RuntimeException(number + " is in rest position,you need to move Forward or Backward");
            }
        } else {

            throw new RuntimeException(number + "  has not started yet");
        }
    }


    int upGear() {
        if (start == true) {
            if (currentGear < totalGears) {
                currentGear++;
                System.out.println("You pressed upgear, now gear is at " + currentGear);
            } else {

                throw new RuntimeException("Reached last gear  " + currentGear);
            }


        } else {

            throw new RuntimeException("You need to start the " + number + "  first to change the gear");
        }
        return currentGear;
    }

    int downGear() {
        if (start == true) {
            if (currentGear > 0) {
                currentGear--;
                System.out.println("You pressed Down gear, now gear is at " + currentGear);
                if (currentGear == 0) {
                    direction = Direction.NULL;
                }
            } else {

                throw new RuntimeException("Reached neutral gear,need to increase the gear" + currentGear);
            }

        } else {

            throw new RuntimeException("You need to start the " + number + " to change the gear");

        }
        return currentGear;
    }

    int currentGear() {

        return currentGear;
    }

    public Direction currentDirection() {

        return direction;
    }

    public int getTotalGears() {
        return totalGears;
    }

    public String getNumberPlate() {
        return number;
    }

    public String getColor() {
        return color;
    }

    public int getSeatingCapacity() {
        return seatingCapacity;
    }

    public FuelType getFuel() {
        return fuel;
    }
}
