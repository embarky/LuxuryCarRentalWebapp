package ch.unil.softarch.luxurycarrental.ui;

import ch.unil.softarch.luxurycarrental.client.CarTypeClient;
import ch.unil.softarch.luxurycarrental.domain.entities.CarType;
import ch.unil.softarch.luxurycarrental.domain.enums.DriveType;
import ch.unil.softarch.luxurycarrental.domain.enums.Transmission;
import jakarta.annotation.PostConstruct;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Named("carTypeFormBean")
@ViewScoped
public class CarTypeFormBean implements Serializable {

    private static final long serialVersionUID = 1L;

    @Inject
    private CarTypeClient carTypeClient;

    private CarType carType;           // Current car type object
    private boolean editing;           // True if editing an existing car type
    private String featuresAsString;   // Comma-separated features for UI

    private UUID carTypeId;            // URL parameter

    private List<DriveType> driveTypes;        // Dropdown options
    private List<Transmission> transmissions;  // Dropdown options

    /**
     * Initialize the bean.
     * Only initialize if this is NOT a postback (avoid overwriting form data).
     */
    @PostConstruct
    public void init() {
        FacesContext fc = FacesContext.getCurrentInstance();
        if (fc.isPostback()) return;

        // Initialize dropdown lists
        driveTypes = Arrays.asList(DriveType.values());
        transmissions = Arrays.asList(Transmission.values());

        if (carTypeId != null) {
            // Editing mode: fetch existing car type
            carType = carTypeClient.getCarType(carTypeId);
            if (carType.getFeatures() == null) carType.setFeatures(new ArrayList<>());
            featuresAsString = String.join(", ", carType.getFeatures());
            editing = true;
        } else {
            // New car type mode
            carType = new CarType();
            carType.setFeatures(new ArrayList<>());
            featuresAsString = "";
            // Set default values to avoid null
            carType.setDriveType(DriveType.FRONT_WHEEL_DRIVE);
            carType.setTransmission(Transmission.MANUAL);
            editing = false;
        }
    }

    /**
     * Save the car type.
     * If editing, call update; if new, call add.
     */
    public String save() {
        // Convert comma-separated string to list
        if (featuresAsString != null && !featuresAsString.isBlank()) {
            carType.setFeatures(Arrays.asList(featuresAsString.split("\\s*,\\s*")));
        } else {
            carType.setFeatures(new ArrayList<>());
        }

        if (editing) {
            carTypeClient.updateCarType(carType.getId(), carType);
        } else {
            carTypeClient.addCarType(carType);
        }

        // Redirect back to car type list page
        return "/pages/admin/car_type.xhtml?faces-redirect=true";
    }

    // --- Getters and setters ---
    public CarType getCarType() { return carType; }
    public void setCarType(CarType carType) { this.carType = carType; }

    public boolean isEditing() { return editing; }

    public String getFeaturesAsString() { return featuresAsString; }
    public void setFeaturesAsString(String featuresAsString) { this.featuresAsString = featuresAsString; }

    public UUID getCarTypeId() { return carTypeId; }
    public void setCarTypeId(UUID carTypeId) { this.carTypeId = carTypeId; }

    public List<DriveType> getDriveTypes() { return driveTypes; }
    public List<Transmission> getTransmissions() { return transmissions; }
}