package ch.unil.softarch.luxurycarrental.ui;

import ch.unil.softarch.luxurycarrental.client.CarClient;
import ch.unil.softarch.luxurycarrental.client.CarTypeClient;
import ch.unil.softarch.luxurycarrental.domain.entities.Car;
import ch.unil.softarch.luxurycarrental.domain.entities.CarType;
import ch.unil.softarch.luxurycarrental.domain.enums.CarStatus;

import jakarta.annotation.PostConstruct;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.convert.Converter;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Named("carFormBean")
@ViewScoped
public class CarFormBean implements Serializable {

    private static final long serialVersionUID = 1L;

    @Inject
    private CarClient carClient;

    @Inject
    private CarTypeClient carTypeClient;

    private Car car;
    private boolean editing;
    private UUID carId;
    private UUID carTypeId;

    private List<CarType> carTypes;
    private List<CarStatus> statuses;

    @PostConstruct
    public void init() {
        FacesContext fc = FacesContext.getCurrentInstance();
        if (fc.isPostback()) return;

        statuses = Arrays.asList(CarStatus.values());

        try {
            carTypes = carTypeClient.getAllCarTypes();
        } catch (Exception e) {
            carTypes = Collections.emptyList();
        }

        String idParam = fc.getExternalContext().getRequestParameterMap().get("carId");
        if (idParam != null && !idParam.isBlank()) {
            try {
                carId = UUID.fromString(idParam);
                car = carClient.getCar(carId);
                if (car.getCarType() != null) {
                    carTypeId = car.getCarType().getId();
                }
                editing = true;
            } catch (Exception e) {
                setupNewCar();
            }
        } else {
            setupNewCar();
        }
    }

    private void setupNewCar() {
        car = new Car();
        car.setStatus(CarStatus.AVAILABLE);
        editing = false;
    }

    public String save() {
        FacesContext ctx = FacesContext.getCurrentInstance();

        // 检查车型
        if (carTypeId == null) {
            ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Please select a Car Type!", null));
            return null;
        }

        try {
            CarType selectedType = carTypeClient.getCarType(carTypeId);
            car.setCarType(selectedType);
        } catch (Exception e) {
            ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Failed to load Car Type.", null));
            e.printStackTrace();
            return null;
        }

        try {
            if (editing) {
                carClient.updateCar(car.getId(), car);
                ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
                        "Car updated successfully!", null));
            } else {
                carClient.addCar(car);
                ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
                        "Car added successfully!", null));
            }
        } catch (Exception e) {
            ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Failed to save car.", null));
            e.printStackTrace();
            return null;
        }

        return "/pages/admin/car.xhtml?faces-redirect=true";
    }

    // --------------- UUID Converter 内置 Bean 方法 ---------------
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
    public Car getCar() { return car; }
    public void setCar(Car car) { this.car = car; }

    public boolean isEditing() { return editing; }

    public List<CarType> getCarTypes() { return carTypes; }
    public List<CarStatus> getStatuses() { return statuses; }

    public UUID getCarId() { return carId; }
    public void setCarId(UUID carId) { this.carId = carId; }

    public UUID getCarTypeId() { return carTypeId; }
    public void setCarTypeId(UUID carTypeId) { this.carTypeId = carTypeId; }
}