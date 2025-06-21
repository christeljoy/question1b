package com.mycompany.utms;

public class UTMS {
    public static void main(String[] args) {
        // Create users
        User student = new Student("Jackson", "password123");
        User lecturer = new Lecturer("Mr.Thomas", "password456");
        User transportOfficer = new TransportOfficer("Officer Jane", "password789");

        // Request transport
        student.requestTransport();
        lecturer.requestTransport();
        transportOfficer.requestTransport();

        // Create vehicles
        Vehicle bus = new Bus("BUS001");
        Vehicle van = new Van("VAN001");

        // Service vehicles
        ((Serviceable) bus).service();
        ((Serviceable) van).service();

        // Track vehicles
        ((Trackable) bus).track();
        ((Trackable) van).track();

        // Schedule vehicles
        ((Schedulable) bus).schedule();

        // Assign drivers
        Driver driver1 = new Driver("Mark", "DL12345");
        TransportManager manager = new TransportManager();
        manager.assignDriver(driver1, bus);
        manager.assignDriver(driver1, van, "Morning Shift");
    }
  }


