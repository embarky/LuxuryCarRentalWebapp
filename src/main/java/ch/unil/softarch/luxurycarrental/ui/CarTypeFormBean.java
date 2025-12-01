package ch.unil.softarch.luxurycarrental.ui;

import ch.unil.softarch.luxurycarrental.client.CarTypeClient;
import ch.unil.softarch.luxurycarrental.domain.entities.CarType;
import ch.unil.softarch.luxurycarrental.domain.enums.DriveType;
import ch.unil.softarch.luxurycarrental.domain.enums.Transmission;

import jakarta.annotation.PostConstruct;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.convert.Converter;
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

    private CarType carType;
    private boolean editing;
    private UUID carTypeId;

    private String featuresAsString;

    private List<DriveType> driveTypes;
    private List<Transmission> transmissions;

    @PostConstruct
    public void init() {
        FacesContext fc = FacesContext.getCurrentInstance();
        if (fc.isPostback()) return;

        driveTypes = Arrays.asList(DriveType.values());
        transmissions = Arrays.asList(Transmission.values());

        String idParam = fc.getExternalContext().getRequestParameterMap().get("carTypeId");
        if (idParam != null && !idParam.isBlank()) {
            try {
                carTypeId = UUID.fromString(idParam);
                carType = carTypeClient.getCarType(carTypeId);
                if (carType.getFeatures() == null) carType.setFeatures(new ArrayList<>());
                featuresAsString = String.join(", ", carType.getFeatures());
                editing = true;
            } catch (Exception e) {
                setupNewCarType();
            }
        } else {
            setupNewCarType();
        }
    }

    private void setupNewCarType() {
        carType = new CarType();
        carType.setFeatures(new ArrayList<>());
        featuresAsString = "";
        carType.setDriveType(DriveType.FRONT_WHEEL_DRIVE);
        carType.setTransmission(Transmission.MANUAL);
        editing = false;
    }

    public String save() {
        FacesContext ctx = FacesContext.getCurrentInstance();

        if (featuresAsString != null && !featuresAsString.isBlank()) {
            carType.setFeatures(Arrays.asList(featuresAsString.split("\\s*,\\s*")));
        } else {
            carType.setFeatures(new ArrayList<>());
        }

        try {
            if (editing) {
                carTypeClient.updateCarType(carType.getId(), carType);
                ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
                        "Car Type updated successfully!", null));
            } else {
                carTypeClient.addCarType(carType);
                ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
                        "Car Type added successfully!", null));
            }
        } catch (Exception e) {
            ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Failed to save Car Type.", null));
            e.printStackTrace();
            return null;
        }

        return "/pages/admin/car_type.xhtml?faces-redirect=true";
    }

    // --------------- UUID Converter ---------------
    public Converter getUuidConverter() {
        return new Converter<UUID>() {
            @Override
            public UUID getAsObject(FacesContext context, UIComponent component, String value) {
                if (value == null || value.isEmpty()) return null;
                return UUID.fromString(value);
            }

            @Override
            public String getAsString(FacesContext context, UIComponent component, UUID value) {
                if (value == null) return "";
                return value.toString();
            }
        };
    }

    // --- Getter / Setter ---
    public CarType getCarType() { return carType; }
    public void setCarType(CarType carType) { this.carType = carType; }

    public boolean isEditing() { return editing; }

    public UUID getCarTypeId() { return carTypeId; }
    public void setCarTypeId(UUID carTypeId) { this.carTypeId = carTypeId; }

    public String getFeaturesAsString() { return featuresAsString; }
    public void setFeaturesAsString(String featuresAsString) { this.featuresAsString = featuresAsString; }

    public List<DriveType> getDriveTypes() { return driveTypes; }
    public List<Transmission> getTransmissions() { return transmissions; }
}