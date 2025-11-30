package ch.unil.softarch.luxurycarrental.ui;

import ch.unil.softarch.luxurycarrental.client.CarClient;
import ch.unil.softarch.luxurycarrental.domain.entities.Car;
import jakarta.annotation.PostConstruct;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

@Named("carsBean")
@ViewScoped
public class CarsBean implements Serializable {

    @Inject
    private CarClient carClient;

    private List<Car> carList;

    @PostConstruct
    public void init() {
        carList = carClient.getAllCars();
    }

    public String goToDetails(UUID id) {
        return "cars_details.xhtml?faces-redirect=true&carId=" + id;
    }

    public List<Car> getCarList() {
        return carList;
    }
}