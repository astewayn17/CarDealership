<h1 align="center">🚗 Welcome to the Car Dealership! 🏎️</h1>

<p align="center">
  <em>
    🚀 Updated version available! Switch to the <a href="https://github.com/astewayn17/CarDealership/tree/advanced-dealership">advanced-dealership</a>
    branch for the latest features including leasing/selling!
  </em>
</p>

## 📦 Features
- Display vehicles by: price range, make & model, year, color, mileage, type (car/truck/SUV/van)
- Add a new vehicle or remove a vehicle by VIN

## 🛠️ How to Run

1. Clone the repo.
2. Open in IntelliJ (or any Java IDE).
3. Make sure `inventory.csv` is in `src/main/resources/`.
4. Run `Program.java`.
5. Interact through the console menu.

## 📁 File Structure

```
CarDealership/
├── .idea/                      # IntelliJ project settings
├── screenshots/                # (Optional) Screenshots for documentation
├── src/
│   └── main/
│       ├── java/
│       │   └── com.pluralsight/
│       │       ├── Dealership.java
│       │       ├── DealershipFileManager.java
│       │       ├── Program.java
│       │       ├── UserInterface.java
│       │       └── Vehicle.java
│       └── resources/
│           └── inventory.csv   # Dealership + vehicle data
├── test/
│   └── java/                   # (Optional) Unit tests
├── target/                     # Compiled build output
├── .gitignore                  # Git ignore rules
├── pom.xml                     # Maven build configuration
└── README.md                   # Project documentation
```

## 📷 UI Screenshots

<table>
  <tr>
    <td align="center" width="500">
      <img src="https://github.com/astewayn17/CarDealership/blob/main/screenshots/home_screen.png" width="400"/><br/>
      <sub><i>Home Screen</i></sub>
    </td>
    <td align="center" width="500">
      <img src="https://github.com/astewayn17/CarDealership/blob/main/screenshots/exit_the_app.png" width="340"/><br/>
      <sub><i>Exit Confirmation</i></sub>
    </td>
  </tr>
</table>

<table>
  <tr>
    <td align="center" width="500">
      <img src="https://github.com/astewayn17/CarDealership/blob/main/screenshots/list_all_vehicles.png" width="370"/><br/>
      <sub><i>Listing All Vehicles</i></sub>
    </td>
    <td align="center" width="500">
      <img src="https://github.com/astewayn17/CarDealership/blob/main/screenshots/vehicles_by_price.png" width="360"/><br/>
      <sub><i>Searching by Price</i></sub>
    </td>
  </tr>
</table>

<table>
  <tr>
    <td align="center" width="500">
      <img src="https://github.com/astewayn17/CarDealership/blob/main/screenshots/adding_a_vehicle.png" width="380"/><br/>
      <sub><i>Adding a Vehicle</i></sub>
    </td>
    <td align="center" width="500">
      <img src="https://github.com/astewayn17/CarDealership/blob/main/screenshots/removing_a_vehicle.png" width="380"/><br/>
      <sub><i>Removing a Vehicle</i></sub>
    </td>
  </tr>
</table>

## 🧠 Interesting Code
**Method to read the dealership and vehicle information:**
One of the more interesting parts of my code is how it reads vehicle and dealership data from a CSV file, formats it, 
and stores it into an `ArrayList` as structured objects. This approach allows the program to manage inventory without 
relying on a database, demonstrating practical file handling and object-oriented design.
```java
public Dealership getDealership() {
        Dealership dealership = null;
        try {
            FileReader fileReadBoi = new FileReader("src/main/resources/inventory.csv");
            BufferedReader buffReadBoi = new BufferedReader(fileReadBoi);
            String line = buffReadBoi.readLine();
            if (line != null) {
                String[] dealershipSegments = line.split("\\|");
                dealership = new Dealership(dealershipSegments[0], dealershipSegments[1], dealershipSegments[2]);
            }
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
        catch (IOException e) {
            System.out.println("Error reading dealership file: " + e.getMessage());
        }
        return dealership;
    }
