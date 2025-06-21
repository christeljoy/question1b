package com.mycompany.utms;

abstract class User {
    private String username;
    private String password;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public abstract void requestTransport();
}

// Interface for vehicle behaviors
interface Serviceable {
    void service();
}

interface Trackable {
    void track();
}

interface Schedulable {
    void schedule();
}

class Driver {
    private String name;
    private String licenseNumber;

    public Driver(String name, String licenseNumber) {
        this.name = name;
        this.licenseNumber = licenseNumber;
    }

    public String getName() {
        return name;
    }

    public String getLicenseNumber() {
        return licenseNumber;
    }
}
class Student extends User {
    public Student(String username, String password) {
        super(username, password);
    }

    @Override
    public void requestTransport() {
        System.out.println("Student " + getUsername() + " requests transport.");
    }
}

class Lecturer extends User {
    public Lecturer(String username, String password) {
        super(username, password);
    }

    @Override
    public void requestTransport() {
        System.out.println("Lecturer " + getUsername() + " requests transport.");
    }
}

class TransportOfficer extends User {
    public TransportOfficer(String username, String password) {
        super(username, password);
    }

    @Override
    public void requestTransport() {
        System.out.println("Transport Officer " + getUsername() + " requests transport.");
    }
}
class Vehicle {
    private String vehicleId;
    private String vehicleType;

    public Vehicle(String vehicleId, String vehicleType) {
        this.vehicleId = vehicleId;
        this.vehicleType = vehicleType;
    }

    public String getVehicleId() {
        return vehicleId;
    }

    public String getVehicleType() {
        return vehicleType;
    }
}

class Bus extends Vehicle implements Serviceable, Trackable, Schedulable {
    public Bus(String vehicleId) {
        super(vehicleId, "Bus");
    }

    @Override
    public void service() {
        System.out.println("Bus " + getVehicleId() + " is being serviced.");
    }

    @Override
    public void track() {
        System.out.println("Tracking Bus " + getVehicleId() + ".");
    }

    @Override
    public void schedule() {
        System.out.println("Scheduling Bus " + getVehicleId() + ".");
    }
}

class Van extends Vehicle implements Serviceable, Trackable {
    public Van(String vehicleId) {
        super(vehicleId, "Van");
    }

    @Override
    public void service() {
        System.out.println("Van " + getVehicleId() + " is being serviced.");
    }

    @Override
    public void track() {
        System.out.println("Tracking Van " + getVehicleId() + ".");
    }
}
class TransportManager {
    public void assignDriver(Driver driver, Vehicle vehicle) {
        System.out.println("Assigning driver " + driver.getName() + " to vehicle " + vehicle.getVehicleId());
    }

    public void assignDriver(Driver driver, Vehicle vehicle, String shiftTime) {
        System.out.println("Assigning driver " + driver.getName() + " to vehicle " + vehicle.getVehicleId() + " for shift " + shiftTime);
    }
}



    

