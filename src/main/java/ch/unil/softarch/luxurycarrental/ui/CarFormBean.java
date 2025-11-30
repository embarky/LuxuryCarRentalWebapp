package ch.unil.softarch.luxurycarrental.ui;

import ch.unil.softarch.luxurycarrental.client.CarClient;
import ch.unil.softarch.luxurycarrental.client.CarTypeClient;
import ch.unil.softarch.luxurycarrental.domain.entities.Car;
import ch.unil.softarch.luxurycarrental.domain.entities.CarType;
import ch.unil.softarch.luxurycarrental.domain.enums.CarStatus;
import jakarta.annotation.PostConstruct;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

/**
 * JSF Managed Bean for adding/editing a Car.
 * Handles form binding, dropdowns, status selection, and submission.
 */
@Named("carFormBean")
@ViewScoped
public class CarFormBean implements Serializable {

    private static final long serialVersionUID = 1L;

    /** The car object bound to the form */
    private Car car;

    /** List of available car types for dropdown */
    private List<CarType> carTypes;

    /** Dropdown options for status */
    private List<CarStatus> statuses;

    /** Flag to indicate if editing an existing car */
    private boolean editing;

    /** Optional URL parameter for editing */
    private UUID carId;

    private final CarTypeClient carTypeClient = new CarTypeClient();
    private final CarClient carClient = new CarClient();

    @PostConstruct
    public void init() {
        FacesContext fc = FacesContext.getCurrentInstance();
        if (fc.isPostback()) return;

        // Load dropdown options
        statuses = new ArrayList<>();
        Collections.addAll(statuses, CarStatus.values());

        try {
            carTypes = carTypeClient.getAllCarTypes();
        } catch (Exception e) {
            carTypes = Collections.emptyList();
            e.printStackTrace();
        }

        if (carId != null) {
            // Editing mode: fetch existing car
            try {
                car = carClient.getCar(carId);
                if (car.getCarType() == null) {
                    car.setCarType(new CarType());
                }
                editing = true;
            } catch (Exception e) {
                car = new Car();
                editing = false;
                e.printStackTrace();
            }
        } else {
            // New car mode
            car = new Car();
            car.setCarType(new CarType());
            editing = false;
        }
    }

    /** Getter/Setter */
    public Car getCar() { return car; }
    public void setCar(Car car) { this.car = car; }

    public List<CarType> getCarTypes() { return carTypes; }
    public List<CarStatus> getStatuses() { return statuses; }

    public boolean isEditing() { return editing; }

    public UUID getCarId() { return carId; }
    public void setCarId(UUID carId) { this.carId = carId; }

    /**
     * Save or add a car.
     * If editing, call update; otherwise add.
     */
    public String save() {
        FacesContext context = FacesContext.getCurrentInstance();

        try {
            if (editing) {
                carClient.updateCar(car.getId(), car);
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
                        "Car updated successfully!", null));
            } else {
                carClient.addCar(car);
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
                        "Car added successfully!", null));
            }

            // Reset form
            car = new Car();
            car.setCarType(new CarType());
            editing = false;

        } catch (Exception e) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Failed to save car", null));
            e.printStackTrace();
        }

        // Redirect to car list
        return "/pages/admin/car.xhtml?faces-redirect=true";
    }
}