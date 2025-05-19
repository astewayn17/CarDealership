package com.pluralsight;

public class SalesContract extends Contract {

    // Declared class variables
    private double salesTaxAmount;
    private double recordingFee;
    private double processingFee;
    private boolean finance;
    private double vehiclePrice;
    private int termMonths;
    private double apr;

    // Constructor
    public SalesContract(String date, String customerName, String customerEmail, String vehicleSold, double vehiclePrice, boolean finance) {
        super(date, customerName, customerEmail, vehicleSold);
        this.salesTaxAmount = vehiclePrice * .05;
        this.recordingFee = 100;
        this.processingFee = (vehiclePrice < 10000) ? 295 : 495;
        this.finance = finance;
        this.vehiclePrice = vehiclePrice;
        // Initializing these here with the math to simplify the getMonthlyPayment method
        this.termMonths = (vehiclePrice >= 10000) ? 48 : 24;
        this.apr = (this.termMonths == 48) ? .0425 : .0525;
    }

    // Abstract method overridden from the super class
    @Override
    public double getTotalPrice() {
        return vehiclePrice + salesTaxAmount + recordingFee + processingFee;
    }

    // Abstract method overridden from the super class
    @Override
    public double getMonthlyPayment() {
        // If the customer chooses not to finance and buy the car at once, there will be no monthly payment
        if (!finance) return 0;
        // Declaring these to be used in the formula
        double monthlyRate = apr / 12;
        double totalPrice = getTotalPrice();
        // Using the standard amortization formula for calculating fixed monthly payments
        // P * (r * (1 + r)^n) / ((1 + r)^n - 1)
        // P = Principal (loan amount, which is the total price)
        // r = Monthly interest rate
        // n = Total number of months
        return (totalPrice * (monthlyRate * Math.pow(1 + monthlyRate, this.termMonths))) /
                (Math.pow(1 + monthlyRate, this.termMonths) - 1);
    }

    // Standard getters and setters
    public double getSalesTaxAmount() { return salesTaxAmount; }
    public void setSalesTaxAmount(double salesTaxAmount) { this.salesTaxAmount = salesTaxAmount; }
    public double getRecordingFee() { return recordingFee; }
    public void setRecordingFee(double recordingFee) { this.recordingFee = recordingFee; }
    public double getProcessingFee() { return processingFee; }
    public void setProcessingFee(double processingFee) { this.processingFee = processingFee; }
    public boolean isFinance() { return finance; }
    public void setFinance(boolean finance) { this.finance = finance; }
    public double getVehiclePrice() { return vehiclePrice; }
    public void setVehiclePrice(double vehiclePrice) { this.vehiclePrice = vehiclePrice; }
    // Added getters to use these when displaying the sales contract to the user
    public int getTermMonths() { return termMonths; }
    public double getApr() { return apr; }
}