package com.pluralsight;

import java.util.ArrayList;
import java.util.List;

public class Dealership {

    // Declaring the class variables
    private String name;
    private String address;
    private String phone;
    // Declaring the array list called inventory based on the vehicle class
    private ArrayList<Vehicle> inventory;

    // Constructor
    public Dealership(String name, String address, String phone) {
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.inventory = new ArrayList<>();
    }

    // Method to get vehicles by price, this is the main logic to acquire the desired vehicles based on the loop and
    // what methods use this
    public List<Vehicle> getVehiclesByPrice(double min, double max) {
        List<Vehicle> matchedVehicles = new ArrayList<>();
        for(Vehicle eachVehicle : inventory) {
            if (eachVehicle.getPrice() >= min && eachVehicle.getPrice() <= max) {
                matchedVehicles.add(eachVehicle);
            }
        } return matchedVehicles;
    }
    // Similar to line 23, 24
    public List<Vehicle> getVehiclesByMakeModel(String make, String model) {
        List<Vehicle> matchedVehicles = new ArrayList<>();
        for(Vehicle eachVehicle : inventory) {
            if (eachVehicle.getMake().equalsIgnoreCase(make) && eachVehicle.getModel().equalsIgnoreCase(model)) {
                matchedVehicles.add(eachVehicle);
            }
        } return matchedVehicles;
    }
    // Similar to line 23, 24
    public List<Vehicle> getVehiclesByYear(int min, int max) {
        List<Vehicle> matchedVehicles = new ArrayList<>();
        for(Vehicle eachVehicle : inventory) {
            if (eachVehicle.getYear() >= min && eachVehicle.getYear() <= max) {
                matchedVehicles.add(eachVehicle);
            }
        } return matchedVehicles;
    }
    // Similar to line 23, 24
    public List<Vehicle> getVehiclesByColor(String color) {
        List<Vehicle> matchedVehicles = new ArrayList<>();
        for(Vehicle eachVehicle : inventory) {
            if (eachVehicle.getColor().equalsIgnoreCase(color)) {
                matchedVehicles.add(eachVehicle);
            }
        } return matchedVehicles;
    }
    // Similar to line 23, 24
    public List<Vehicle> getVehiclesByMileage(int min, int max) {
        List<Vehicle> matchedVehicles = new ArrayList<>();
        for(Vehicle eachVehicle : inventory) {
            if (eachVehicle.getOdometer() >= min && eachVehicle.getOdometer() <= max) {
                matchedVehicles.add(eachVehicle);
            }
        } return matchedVehicles;
    }
    // Similar to line 23, 24
    public List<Vehicle> getVehiclesByType(String vehicleType) {
        List<Vehicle> matchedVehicles = new ArrayList<>();
        for(Vehicle eachVehicle : inventory) {
            if (eachVehicle.getVehicleType().equalsIgnoreCase(vehicleType)) {
                matchedVehicles.add(eachVehicle);
            }
        } return matchedVehicles;
    }

    // Similar to line 23, 24
 //    public Vehicle getVehicleByVin(int vin) {
 //       for (Vehicle vehicle : inventory) {
 //           if (vehicle.getVin() == vin) {
 //               return vehicle;
 //           }
 //       } return null;
 //   }

    // Getter for the inventory of the vehicles
    public List<Vehicle> getAllVehicles() { return inventory; }
    // Methods to add the vehicles and remove them from the list
    public void addVehicle(Vehicle vehicle) { inventory.add(vehicle); }
    public void removeVehicle(Vehicle vehicle) { inventory.remove(vehicle); }

    // Setters and getters
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
}