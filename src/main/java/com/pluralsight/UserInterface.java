package com.pluralsight;

import java.util.List;
import java.util.Scanner;

public class UserInterface {

    // Initializing instance variables for managing dealership data and user input
    private Dealership dealership;
    private Scanner input;

    // Constructor for the scanner. Not needed for dealership since it's loaded from the file
    public UserInterface() {
        this.input = new Scanner(System.in);
    }

    // Method that shows the main UI and processes the preliminary choice input by the user
    public void display() {
        // Calls init method
        init();
        // Loops based on the options selected from the menu
        boolean accessing = true;
        while (accessing) {
            displayMenu();
            int command = input.nextInt();
            // Consume next line since the input previous is an int or double
            input.nextLine();
            switch (command) {
                case 1 -> processGetByPriceRequest();
                case 2 -> processGetByMakeModelRequest();
                case 3 -> processGetByYearRequest();
                case 4 -> processGetByColorRequest();
                case 5 -> processGetByMileageRequest();
                case 6 -> processGetByTypeRequest();
                case 7 -> processAllVehiclesRequest();
                case 8 -> processAddVehicleRequest();
                case 9 -> processRemoveVehicleRequest();
                case 99-> {
                    System.out.println("\nExiting the application. Goodbye!");
                    accessing = false;
                }
                default -> System.out.println("\nInvalid option! Please try again.");
            }
        }
    }

    // Loads dealership data from CSV and puts it into dealership variable then it shows the first line
    private void init() {
        DealershipFileManager fileManager = new DealershipFileManager();
        this.dealership = fileManager.getDealership();
        if (dealership != null) {
            System.out.println("\nWELCOME TO " + dealership.getName().toUpperCase());
            System.out.println("========================================");
        } else {
            System.out.println("\nError loading dealership data.");
        }
    }

    // Shows the menu
    private void displayMenu() {
        System.out.println("(1) - Find vehicles within a price range");
        System.out.println("(2) - Find vehicles by make / model");
        System.out.println("(3) - Find vehicles by year range");
        System.out.println("(4) - Find vehicles by color");
        System.out.println("(5) - Find vehicles by mileage range");
        System.out.println("(6) - Find vehicles by type (car/truck/SUV/van)");
        System.out.println("(7) - List ALL vehicles");
        System.out.println("(8) - Add a vehicle");
        System.out.println("(9) - Remove a vehicle");
        System.out.println("(99) - Quit");
        System.out.print("\nPlease select a number from the choices above: ");
    }

    // Helper method used by the 'process...' methods to iterate through the list of vehicles and display them
    private void displayVehicles(List<Vehicle> vehicles) {
        if (vehicles == null || vehicles.isEmpty()) {
            System.out.println("\nNo vehicles found!\n");
            return;
        }
        System.out.println();
        System.out.println("Format:\nVIN | Year | Make | Model | Type | Color | Odometer | Price\n");
        // This actually calls the toString behind the scenes implicitly from the vehicle class
        for (Vehicle vehicle : vehicles) {
            System.out.println(vehicle);
        }
        System.out.println();
    }
    // User inputs the min and max parameters that are then put into the getVehiclesByPrice method in
    // the dealership class that then goes into the displayVehicles method to show them to the user
    private void processGetByPriceRequest() {
        System.out.print("Enter the minimum price: ");
        double min = input.nextDouble();
        System.out.print("Enter the maximum price: ");
        double max = input.nextDouble();
        input.nextLine();
        displayVehicles(dealership.getVehiclesByPrice(min, max));
    }
    // Similar to line 88, 89
    private void processGetByMakeModelRequest() {
        System.out.print("Enter the vehicle make: ");
        String make = input.nextLine().trim();
        System.out.print("Enter the vehicle model: ");
        String model = input.nextLine().trim();
        displayVehicles(dealership.getVehiclesByMakeModel(make, model));
    }
    // Similar to line 88, 89
    private void processGetByYearRequest() {
        System.out.print("Enter the minimum year: ");
        int min = input.nextInt();
        System.out.print("Enter the maximum year: ");
        int max = input.nextInt();
        input.nextLine();
        displayVehicles(dealership.getVehiclesByYear(min, max));
    }
    // Similar to line 88, 89
    private void processGetByMileageRequest() {
        System.out.print("Enter the minimum mileage: ");
        int min = input.nextInt();
        System.out.print("Enter the maximum mileage: ");
        int max = input.nextInt();
        input.nextLine();
        displayVehicles(dealership.getVehiclesByMileage(min, max));
    }
    // Similar to line 88, 89
    private void processGetByColorRequest() {
        System.out.print("Enter the vehicle color: ");
        String color = input.nextLine().trim();
        displayVehicles(dealership.getVehiclesByColor(color));
    }
    // Similar to line 88, 89
    private void processGetByTypeRequest() {
        System.out.print("Enter the vehicle type (car, truck, SUV, van): ");
        String type = input.nextLine().trim();
        displayVehicles(dealership.getVehiclesByType(type));
    }
    // Just acquires all vehicles and uses displayVehicles to show them
    private void processAllVehiclesRequest() {
        List<Vehicle> allVehicles = dealership.getAllVehicles();
        displayVehicles(allVehicles);
    }
    // User inputs info for the vehicle they want to add. Those are then used to instantiate
    // a new vehicle object which is then added to the array list and then saved to the CSV.
    private void processAddVehicleRequest() {
        System.out.print("Enter the VIN: ");
        int vin = input.nextInt();
        System.out.print("Enter the year: ");
        int year = input.nextInt();
        input.nextLine();
        System.out.print("Enter the make: ");
        String make = input.nextLine().trim();
        System.out.print("Enter the model: ");
        String model = input.nextLine().trim();
        System.out.print("Enter the type (car/truck/SUV/van): ");
        String type = input.nextLine().trim();
        System.out.print("Enter the color: ");
        String color = input.nextLine().trim();
        System.out.print("Enter the odometer reading: ");
        int odometer = input.nextInt();
        System.out.print("Enter the price: ");
        double price = input.nextDouble();
        input.nextLine();
        Vehicle vehicle = new Vehicle(vin, year, make, model, type, color, odometer, price);
        dealership.addVehicle(vehicle);
        // Save updated dealership
        DealershipFileManager fileManager = new DealershipFileManager();
        fileManager.saveDealership(dealership);
        System.out.println("\nVehicle added and inventory updated successfully!.\n");
    }
    // Handles vehicle removal by prompting the user for a VIN, locating the corresponding Vehicle object,
    // removing it from the dealership's inventory list, and saving the updated list back to the file
    private void processRemoveVehicleRequest() {
        System.out.print("Enter the VIN of the vehicle you'd like to remove: ");
        int vinChoice = input.nextInt();
        input.nextLine();
        Vehicle correspondingVehicle = null;
        for (Vehicle v : dealership.getAllVehicles()) {
            if (v.getVin() == vinChoice) {
                correspondingVehicle = v;
                break;
            }
        }if (correspondingVehicle != null) {
            dealership.removeVehicle(correspondingVehicle);
            DealershipFileManager fileManager = new DealershipFileManager();
            fileManager.saveDealership(dealership);
            System.out.println("\nVehicle removed and inventory updated successfully!\n");
        } else {
            System.out.println("\nNo corresponding vehicle found with this VIN.\n");
        }
    }
}
