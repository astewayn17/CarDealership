<h1 align="center">🚗 Welcome to the Car Dealership! 🏎️</h1>

## 📦 Features

### Vehicle Management
- Search vehicles by: price, make/model, year, color, mileage, or type
- Add new vehicles or remove existing ones by VIN
- View all available inventory
### Contract Processing
- Sales: Cash or finance options with automatic APR and fee calculations
- Lease: 36-month terms for vehicles ≤3 years old with fixed 4% APR
- Smart monthly payment calculations using loan amortization formulas
### Database Integration
- MySQL database with connection pooling
- Persistent storage for vehicles and contracts
- Real-time inventory tracking (vehicles marked as sold, not deleted)

## 🛠️ How to Run

1. Clone the repo.
2. Set up MySQL database and run the provided SQL script.
3. Update database credentials in `Program.java`:
   ```java
   javaString username = "your_username";
   String password = "your_password";
   ```
4. Open in IntelliJ (or any Java IDE).
5. Run `mvn clean compile` to build.
6. Run Program.java.
7. Interact through the console menu.

## 📁 File Structure

```
CarDealership/
├── .idea/
├── screenshots/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/
│   │   │       └── pluralsight/
│   │   │           ├── dao/
│   │   │           │   ├── LeaseContractDao.java
│   │   │           │   ├── SalesContractDao.java
│   │   │           │   └── VehicleDao.java
│   │   │           ├── models/
│   │   │           │   ├── Contract.java
│   │   │           │   ├── Dealership.java
│   │   │           │   ├── LeaseContract.java
│   │   │           │   ├── SalesContract.java
│   │   │           │   └── Vehicle.java
│   │   │           ├── Program.java
│   │   │           └── UserInterface.java
│   │   └── resources/
│   └── test/
├── target/
├── .gitignore
├── pom.xml
└── README.md

```

## 📷 Screenshots
### Home Screen
```
        WELCOME TO ASTEWAY AUTO CENTER
============================================

(1) ----- Find vehicles within a price range
(2) ----- Find vehicles by make/model
(3) ----- Find vehicles by year range
(4) ----- Find vehicles by color
(5) ----- Find vehicles by mileage range
(6) ----- Find vehicles by type (car/truck/SUV/van)
(7) ----- List ALL vehicles
(8) ----- Add a vehicle
(9) ----- Remove a vehicle
(10) ---- Sell or Lease a vehicle
(99) ---- Quit

Please select a number from the choices above: 
```

### Vehicle Display Screen
```
      === All Available Vehicles ===

 VIN                  | Year | Make          | Model         | Type     | Color      | Mileage  | Price
----------------------------------------------------------------------------------------------------------
 1HGCM82633A004352    | 2021 | Honda         | Civic         | Sedan    | Blue       |   30,000 | $19,500.00
 1FTRX12W37FA12345    | 2022 | Ford          | F-150         | Truck    | Black      |   12,000 | $34,000.00
 SCFRMFAV3JGL12345    | 2019 | Aston Martin  | DB11          | Coupe    | Midnight Blue |    8,000 | $210,000.00
```

### Error Handling Example
```
Enter minimum price: 50000
Enter maximum price: 25000

No vehicles found in that price range.
```

### Interesting Code: Contract Monthly Payment Calculation

One of the most interesting pieces of code is the monthly payment calculation in the `SalesContract` class:
This is interesting because it applies real-world financial mathematics, using the standard loan amortization formula found in actual dealership systems. The calculation dynamically adjusts based on vehicle price, selecting appropriate loan terms and APR rates. Additionally, the code is clean, well-commented, and uses clear, meaningful variable names.

```java
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
```
