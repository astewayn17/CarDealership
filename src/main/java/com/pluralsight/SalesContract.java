package com.pluralsight;

public class SalesContract extends Contract {

    // Declared class variables
    private double salesTaxAmount;
    private double recordingFee;
    private double processingFee;
    private boolean finance;
    private double monthlyPayment;

    // Constructor
    public SalesContract(String date, String customerName, String vehicleSold, boolean finance) {
        super(date, customerName, vehicleSold);
        this.salesTaxAmount = 1.05;
        this.recordingFee = 100;
        this.processingFee = 0;
        this.finance = finance;
        this.monthlyPayment = 0;

    }

    // Abstract method overridden from the super class
    @Override
    public double getTotalPrice() {
        return 0;
    }

    // Abstract method overridden from the super class
    @Override
    public double getMonthlyPayment() {
        return 0;
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

    //

}
