package ch.unil.softarch.luxurycarrental.ui;

import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.UUID;

@Named("carFormBean")
@SessionScoped
public class CarFormBean implements Serializable {

    // ---- Fields matching your JSON ----
    private String id;
    private String carTypeId;
    private String color;
    private Double dailyRentalPrice;
    private Double depositAmount;
    private String vin;
    private String licensePlate;
    private String imageUrl;
    private LocalDate registrationDate;
    private LocalDate insuranceExpiryDate;
    private LocalDate lastMaintenanceDate;
    private String status;

    // -------------------------------
    // Load existing car for editing
    // -------------------------------
    public String loadCar(String carId) {
        // TODO load from database/service
        System.out.println("Loading car: " + carId);

        // Mock example for demonstration
        this.id = carId;
        this.carTypeId = "aaa85d1a-1942-4517-b4b8-f37a2e33a7ad";
        this.color = "White";
        this.dailyRentalPrice = 120.0;
        this.depositAmount = 300.0;
        this.vin = "VIN123456";
        this.licensePlate = "VD12345";
        this.imageUrl = "https://example.com/camry.jpg";
        this.registrationDate = LocalDate.parse("2025-01-10");
        this.insuranceExpiryDate = LocalDate.parse("2026-11-02");
        this.lastMaintenanceDate = LocalDate.parse("2025-09-15");
        this.status = "AVAILABLE";

        return "add_or_edit_car.xhtml?faces-redirect=true";
    }

    // -------------------------------
    // Create new car
    // -------------------------------
    public String newCar() {
        this.id = UUID.randomUUID().toString(); // new UUID
        this.carTypeId = null;
        this.color = null;
        this.dailyRentalPrice = null;
        this.depositAmount = null;
        this.vin = null;
        this.licensePlate = null;
        this.imageUrl = null;
        this.registrationDate = null;
        this.insuranceExpiryDate = null;
        this.lastMaintenanceDate = null;
        this.status = "AVAILABLE";

        return "add_or_edit_car.xhtml?faces-redirect=true";
    }

    // -------------------------------
    // Save (create or update)
    // -------------------------------
    public String save() {
        System.out.println("Saving car:");
        System.out.println("ID = " + id);
        System.out.println("Car Type ID = " + carTypeId);
        System.out.println("Color = " + color);
        System.out.println("Daily Price = " + dailyRentalPrice);

        // TODO service.saveCar(...)

        return "car_list.xhtml?faces-redirect=true";
    }

    // Getters and setters ...
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getCarTypeId() { return carTypeId; }
    public void setCarTypeId(String carTypeId) { this.carTypeId = carTypeId; }

    public String getColor() { return color; }
    public void setColor(String color) { this.color = color; }

    public Double getDailyRentalPrice() { return dailyRentalPrice; }
    public void setDailyRentalPrice(Double dailyRentalPrice) { this.dailyRentalPrice = dailyRentalPrice; }

    public Double getDepositAmount() { return depositAmount; }
    public void setDepositAmount(Double depositAmount) { this.depositAmount = depositAmount; }

    public String getVin() { return vin; }
    public void setVin(String vin) { this.vin = vin; }

    public String getLicensePlate() { return licensePlate; }
    public void setLicensePlate(String licensePlate) { this.licensePlate = licensePlate; }

    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }

    public LocalDate getRegistrationDate() { return registrationDate; }
    public void setRegistrationDate(LocalDate registrationDate) { this.registrationDate = registrationDate; }

    public LocalDate getInsuranceExpiryDate() { return insuranceExpiryDate; }
    public void setInsuranceExpiryDate(LocalDate insuranceExpiryDate) { this.insuranceExpiryDate = insuranceExpiryDate; }

    public LocalDate getLastMaintenanceDate() { return lastMaintenanceDate; }
    public void setLastMaintenanceDate(LocalDate lastMaintenanceDate) { this.lastMaintenanceDate = lastMaintenanceDate; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}