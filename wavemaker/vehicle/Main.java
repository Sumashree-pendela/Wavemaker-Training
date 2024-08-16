package com.wavemaker.vehicle;

public class Main {
    public static void main(String[] args) {

        ElectricBike electricBike = new ElectricBike("TS065678", "Green", 80);
        electricBike.vehicleDescription();
        electricBike.getBatteryCapacity();

        Car car = new Car("AP24U0909", "YELLOW", FuelType.DIESEL);
        car.startVehicle();

        Bike bike = new Bike("Black", "TS07H1111");
        bike.moveBackward();
        bike.startVehicle();
    }
}
