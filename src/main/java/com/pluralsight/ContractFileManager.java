package com.pluralsight;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class ContractFileManager {

    // Method to save the contract to the csv
    public void saveContract(Contract contract) {
        try {
            // File writer to create/write to the file the characters and buffered reader to wrap and write efficiently
            FileWriter fileWriteBoi = new FileWriter("src/main/resources/contracts.csv", true);
            BufferedWriter buffWriteBoi = new BufferedWriter(fileWriteBoi);
            // Downcasts to the sale contracts to save those specifically
            if (contract instanceof SalesContract sale) {
                String line = String.format("SALE|%s|%s|%s|%s|%.2f|%.2f|%.2f|%.2f|%.2f|%s|%.2f",
                        sale.getDate(),
                        sale.getCustomerName(),
                        sale.getCustomerEmail(),
                        sale.getVehicleSold(),
                        sale.getVehiclePrice(),
                        sale.getSalesTaxAmount(),
                        sale.getRecordingFee(),
                        sale.getProcessingFee(),
                        sale.getTotalPrice(),
                        sale.isFinance() ? "YES" : "NO",
                        sale.getMonthlyPayment());
                buffWriteBoi.write(line);
                }
            // Downcasts to the lease contracts to save those specifically
            else if (contract instanceof LeaseContract lease) {
                String line = String.format("LEASE|%s|%s|%s|%s|%.2f|%.2f|%.2f|%.2f|%.2f",
                        lease.getDate(),
                        lease.getCustomerName(),
                        lease.getCustomerEmail(),
                        lease.getVehicleSold(),
                        lease.getVehiclePrice(),
                        lease.getExpectedEndingValue(),
                        lease.getLeaseFee(),
                        lease.getTotalPrice(),
                        lease.getMonthlyPayment());
                buffWriteBoi.write(line);
                }
            buffWriteBoi.newLine();
            buffWriteBoi.close();
        // Will throw this catch if there is an issue with the file
        } catch (IOException e) {
            System.out.println("\nError writing contract file: " + e.getMessage());
        }
    }
}