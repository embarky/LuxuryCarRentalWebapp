package ch.unil.softarch.luxurycarrental.ui;

import ch.unil.softarch.luxurycarrental.client.CarClient;
import ch.unil.softarch.luxurycarrental.domain.entities.Car;
import jakarta.annotation.PostConstruct;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

@Named("carListBean")
@ViewScoped
public class CarListBean implements Serializable {

    private List<Car> cars;

    private final CarClient carClient = new CarClient();

    @PostConstruct
    public void init() {
        try {
            cars = carClient.getAllCars();
            System.out.println("Fetched cars: " + cars.size());
        } catch (Exception e) {
            e.printStackTrace();
            cars = Collections.emptyList();
        }
    }

    public List<Car> getCars() {
        return cars;
    }
}