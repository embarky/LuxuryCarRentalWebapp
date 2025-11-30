package ch.unil.softarch.luxurycarrental.ui;

import ch.unil.softarch.luxurycarrental.client.CarClient;
import ch.unil.softarch.luxurycarrental.domain.entities.Car;
import jakarta.annotation.PostConstruct;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.io.Serializable;
import java.util.Map;
import java.util.UUID;

@Named("carDetailsBean")
@ViewScoped
public class CarDetailsBean implements Serializable {

    @Inject
    private CarClient carClient;

    private Car car;

    @PostConstruct
    public void init() {
        Map<String, String> params =
                FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();

        String idStr = params.get("carId");

        if (idStr != null) {
            car = carClient.getCar(UUID.fromString(idStr));
        }
    }

    public Car getCar() {
        return car;
    }
}