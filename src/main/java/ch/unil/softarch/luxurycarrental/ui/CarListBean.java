package ch.unil.softarch.luxurycarrental.ui;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Named;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Named("carListBean")
@RequestScoped
public class CarListBean implements Serializable {

    public static class Car {
        public String id;
        public String carTypeId;
        public String color;
        public Double dailyRentalPrice;
        public Double depositAmount;
        public String vin;
        public String licensePlate;
        public String imageUrl;
        public LocalDate registrationDate;
        public LocalDate insuranceExpiryDate;
        public LocalDate lastMaintenanceDate;
        public String status;

        public Car(String id, String carTypeId, String color, Double dailyRentalPrice,
                   Double depositAmount, String vin, String licensePlate, String imageUrl,
                   LocalDate registrationDate, LocalDate insuranceExpiryDate,
                   LocalDate lastMaintenanceDate, String status) {

            this.id = id;
            this.carTypeId = carTypeId;
            this.color = color;
            this.dailyRentalPrice = dailyRentalPrice;
            this.depositAmount = depositAmount;
            this.vin = vin;
            this.licensePlate = licensePlate;
            this.imageUrl = imageUrl;
            this.registrationDate = registrationDate;
            this.insuranceExpiryDate = insuranceExpiryDate;
            this.lastMaintenanceDate = lastMaintenanceDate;
            this.status = status;
        }
    }

    public List<Car> getAllCars() {
        List<Car> list = new ArrayList<>();

        list.add(new Car(
                "b6a21b3f-879e-45a9-b6db-1d4a5f5e93a0",
                "aaa85d1a-1942-4517-b4b8-f37a2e33a7ad",
                "White",
                120.0,
                300.0,
                "VIN123456",
                "VD12345",
                "https://example.com/camry.jpg",
                LocalDate.parse("2025-01-10"),
                LocalDate.parse("2026-11-02"),
                LocalDate.parse("2025-09-15"),
                "AVAILABLE"
        ));

        return list;
    }
}