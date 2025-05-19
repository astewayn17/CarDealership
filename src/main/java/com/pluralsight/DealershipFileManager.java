package com.pluralsight;

import java.io.*;

public class DealershipFileManager {

    public Dealership getDealership() {
        // Declaring the dealership object
        Dealership dealership = null;
        try {
            // File reader to locate the file and read the characters and buffered reader to wrap/read efficiently
            FileReader fileReadBoi = new FileReader("src/main/resources/inventory.csv");
            BufferedReader buffReadBoi = new BufferedReader(fileReadBoi);
            // Looping through the file line by line and splitting it and adding them to a string that is then made
            // into the dealership headline
            String line = buffReadBoi.readLine();
            if (line != null) {
                String[] dealershipSegments = line.split("\\|");
                dealership = new Dealership(dealershipSegments[0], dealershipSegments[1], dealershipSegments[2]);
            }
            // While loop what loops until a line doesn't exist by splitting every line it comes across and storing that
            // into strings that then are re-declared and initialized through the vehicle constructor making a new one
            // for every line then are each added to the dealership object.
            while ((line = buffReadBoi.readLine()) != null) {
                String[] segments = line.split("\\|");
                int vin = Integer.parseInt(segments[0]);
                int year = Integer.parseInt(segments[1]);
                String make = segments[2];
                String model = segments[3];
                String type = segments[4];
                String color = segments[5];
                int odometer = Integer.parseInt(segments[6]);
                double price = Double.parseDouble(segments[7]);
                Vehicle vehicle = new Vehicle(vin, year, make, model, type, color, odometer, price);
                dealership.addVehicle(vehicle);
            }
        }
        // Will catch any file reading issues
        catch (IOException e) {
            System.out.println("Error reading dealership file: " + e.getMessage());
        }
        return dealership;
    }
    // Method to save the dealership to the csv
    public void saveDealership(Dealership dealership) {
        try {
            FileWriter fileWriteBoi = new FileWriter("src/main/resources/inventory.csv");
            BufferedWriter buffWriteBoi = new BufferedWriter(fileWriteBoi);
            //Uses the buffered writer to concatenate the dealership's info with pipes
            buffWriteBoi.write(dealership.getName() + "|" + dealership.getAddress() + "|" + dealership.getPhone());
            buffWriteBoi.newLine();
            // Loops through all vehicles information and uses the getters to add all the information into the string
            // formatter to write
            for (Vehicle vehicle : dealership.getAllVehicles()) {
                String line = String.format("%d|%d|%s|%s|%s|%s|%d|%.2f",
                        vehicle.getVin(),
                        vehicle.getYear(),
                        vehicle.getMake(),
                        vehicle.getModel(),
                        vehicle.getVehicleType(),
                        vehicle.getColor(),
                        vehicle.getOdometer(),
                        vehicle.getPrice());
                buffWriteBoi.write(line);
                buffWriteBoi.newLine();
            }
            buffWriteBoi.close();
        } catch (IOException e) {
            System.out.println("\nError writing dealership file: " + e.getMessage());
        }
    }
}