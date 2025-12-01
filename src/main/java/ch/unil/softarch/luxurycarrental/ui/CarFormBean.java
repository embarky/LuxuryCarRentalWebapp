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

    private List<CarType> carTypes;
    private List<CarStatus> statuses;

    @PostConstruct
    public void init() {
        FacesContext fc = FacesContext.getCurrentInstance();
        if (fc.isPostback()) return;

        // 加载下拉框数据
        statuses = Arrays.asList(CarStatus.values());

        try {
            carTypes = carTypeClient.getAllCarTypes();
        } catch (Exception e) {
            carTypes = Collections.emptyList();
        }

        // URL 参数 carId
        String idParam = fc.getExternalContext().getRequestParameterMap().get("carId");

        if (idParam != null && !idParam.isBlank()) {
            try {
                carId = UUID.fromString(idParam);
                car = carClient.getCar(carId);
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

        // 校验 CarType
        if (car.getCarType() == null || car.getCarType().getId() == null) {
            ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Please select a Car Type!", null));
            return null;
        }

        try {
            if (editing) {
                carClient.updateCar(car.getId(), car);
                ctx.addMessage(null, new FacesMessage("Car updated successfully!"));
            } else {
                carClient.addCar(car);
                ctx.addMessage(null, new FacesMessage("Car added successfully!"));
            }
        } catch (Exception e) {
            ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Failed to save car.", null));
            e.printStackTrace();
            return null;
        }

        return "/pages/admin/car.xhtml?faces-redirect=true";
    }

    // Getter / Setter
    public Car getCar() { return car; }
    public void setCar(Car car) { this.car = car; }

    public boolean isEditing() { return editing; }

    public List<CarType> getCarTypes() { return carTypes; }
    public List<CarStatus> getStatuses() { return statuses; }

    public UUID getCarId() { return carId; }
    public void setCarId(UUID carId) { this.carId = carId; }
}