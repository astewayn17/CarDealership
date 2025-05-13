package com.pluralsight;

import java.io.*;

public class DealershipFileManager {

    public Dealership getDealership() {
        //
        Dealership dealership = null;
        //
        try {
            //
            FileReader fileReadBoi = new FileReader("src/main/resources/inventory.csv");
            BufferedReader buffReadBoi = new BufferedReader(fileReadBoi);
            //
            String line = buffReadBoi.readLine();
            if (line != null) {
                String[] dealershipSegments = line.split("\\|");
                dealership = new Dealership(dealershipSegments[0], dealershipSegments[1], dealershipSegments[2]);
            }
            //
            while ((line = buffReadBoi.readLine()) != null) {
                String[] segments = line.split("\\|");
                //
                int vin = Integer.parseInt(segments[0]);
                int year = Integer.parseInt(segments[1]);
                String make = segments[2];
                String model = segments[3];
                String type = segments[4];
                String color = segments[5];
                int odometer = Integer.parseInt(segments[6]);
                double price = Double.parseDouble(segments[7]);
                //
                Vehicle vehicle = new Vehicle(vin, year, make, model, type, color, odometer, price);
                dealership.addVehicle(vehicle);
            }
        }
        //
        catch (IOException e) {
            System.out.println("Error reading dealership file: " + e.getMessage());
        }
        return dealership;
    }
    //
    public void saveDealership(Dealership dealership) {
        try {
            FileWriter fileWriteBoi = new FileWriter("src/main/resources/inventory.csv");
            BufferedWriter buffWriteBoi = new BufferedWriter(fileWriteBoi);
            //
            buffWriteBoi.write(dealership.getName() + "|" + dealership.getAddress() + "|" + dealership.getPhone());
            buffWriteBoi.newLine();
            //
            for (Vehicle vehicle : dealership.getAllVehicles()) {
                String line = vehicle.getVin() + "|" +
                        vehicle.getYear() + "|" +
                        vehicle.getMake() + "|" +
                        vehicle.getModel() + "|" +
                        vehicle.getVehicleType() + "|" +
                        vehicle.getColor() + "|" +
                        vehicle.getOdometer() + "|" +
                        vehicle.getPrice();
                buffWriteBoi.write(line);
                buffWriteBoi.newLine();
            }
            buffWriteBoi.close();
        } catch (IOException e) {
            System.out.println("\nError writing dealership file: " + e.getMessage());
        }
    }
}

