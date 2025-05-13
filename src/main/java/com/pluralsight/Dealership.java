package com.pluralsight;

import java.util.ArrayList;
import java.util.List;

public class Dealership {

    //
    private String name;
    private String address;
    private String phone;
    //
    private ArrayList<Vehicle> inventory;

    //
    public Dealership(String name, String address, String phone) {
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.inventory = new ArrayList<>();
    }

    //
    public List<Vehicle> getVehiclesByPrice(double min, double max) {
        List<Vehicle> matchedVehicles = new ArrayList<>();
        for(Vehicle eachVehicle : inventory) {
            if (eachVehicle.getPrice() >= min && eachVehicle.getPrice() <= max) {
                matchedVehicles.add(eachVehicle);
            }
        } return matchedVehicles;
    }
    //
    public List<Vehicle> getVehiclesByMakeModel(String make, String model) {
        List<Vehicle> matchedVehicles = new ArrayList<>();
        for(Vehicle eachVehicle : inventory) {
            if (eachVehicle.getMake().equalsIgnoreCase(make) && eachVehicle.getModel().equalsIgnoreCase(model)) {
                matchedVehicles.add(eachVehicle);
            }
        } return matchedVehicles;
    }
    //
    public List<Vehicle> getVehiclesByYear(int min, int max) {
        List<Vehicle> matchedVehicles = new ArrayList<>();
        for(Vehicle eachVehicle : inventory) {
            if (eachVehicle.getYear() >= min && eachVehicle.getYear() <= max) {
                matchedVehicles.add(eachVehicle);
            }
        } return matchedVehicles;
    }
    //
    public List<Vehicle> getVehiclesByColor(String color) {
        List<Vehicle> matchedVehicles = new ArrayList<>();
        for(Vehicle eachVehicle : inventory) {
            if (eachVehicle.getColor().equalsIgnoreCase(color)) {
                matchedVehicles.add(eachVehicle);
            }
        } return matchedVehicles;
    }
    //
    public List<Vehicle> getVehiclesByMileage(int min, int max) {
        List<Vehicle> matchedVehicles = new ArrayList<>();
        for(Vehicle eachVehicle : inventory) {
            if (eachVehicle.getOdometer() >= min && eachVehicle.getOdometer() <= max) {
                matchedVehicles.add(eachVehicle);
            }
        } return matchedVehicles;
    }
    //
    public List<Vehicle> getVehiclesByType(String vehicleType) {
        List<Vehicle> matchedVehicles = new ArrayList<>();
        for(Vehicle eachVehicle : inventory) {
            if (eachVehicle.getVehicleType().equalsIgnoreCase(vehicleType)) {
                matchedVehicles.add(eachVehicle);
            }
        } return matchedVehicles;
    }

 //    public Vehicle getVehicleByVin(int vin) {
 //       for (Vehicle vehicle : inventory) {
 //           if (vehicle.getVin() == vin) {
 //               return vehicle;
 //           }
 //       } return null;
 //   }

    //
    public List<Vehicle> getAllVehicles() { return inventory; }
    //
    public void addVehicle(Vehicle vehicle) { inventory.add(vehicle); }
    public void removeVehicle(Vehicle vehicle) { inventory.remove(vehicle); }

    //
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
}
