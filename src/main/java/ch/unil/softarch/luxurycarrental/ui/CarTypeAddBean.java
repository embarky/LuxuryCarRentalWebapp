package ch.unil.softarch.luxurycarrental.ui;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Named("carTypeAddBean")
@RequestScoped
public class CarTypeAddBean implements Serializable {

    private String brand;
    private String model;
    private String category;
    private String engine;
    private int power;
    private int maxSpeed;
    private double weight;
    private double acceleration;
    private int seats;
    private String transmission;
    private String driveType;
    private String description;

    private List<String> features = new ArrayList<>();

    // 提交方法
    public String save() {
        System.out.println("=== New CarType Created ===");
        System.out.println("Brand: " + brand);
        System.out.println("Model: " + model);
        System.out.println("Features: " + features);
        System.out.println("==========================");

        // TODO: 你在这里调用 service.addCarType(...)

        return "add_car_type_success.xhtml?faces-redirect=true";
    }

    // Getters & Setters（全部字段）
    public String getBrand() { return brand; }
    public void setBrand(String brand) { this.brand = brand; }

    public String getModel() { return model; }
    public void setModel(String model) { this.model = model; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public String getEngine() { return engine; }
    public void setEngine(String engine) { this.engine = engine; }

    public int getPower() { return power; }
    public void setPower(int power) { this.power = power; }

    public int getMaxSpeed() { return maxSpeed; }
    public void setMaxSpeed(int maxSpeed) { this.maxSpeed = maxSpeed; }

    public double getWeight() { return weight; }
    public void setWeight(double weight) { this.weight = weight; }

    public double getAcceleration() { return acceleration; }
    public void setAcceleration(double acceleration) { this.acceleration = acceleration; }

    public int getSeats() { return seats; }
    public void setSeats(int seats) { this.seats = seats; }

    public String getTransmission() { return transmission; }
    public void setTransmission(String transmission) { this.transmission = transmission; }

    public String getDriveType() { return driveType; }
    public void setDriveType(String driveType) { this.driveType = driveType; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public List<String> getFeatures() { return features; }
    public void setFeatures(List<String> features) { this.features = features; }
}