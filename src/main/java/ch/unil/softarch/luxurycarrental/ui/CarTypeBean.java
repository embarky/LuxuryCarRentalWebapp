package ch.unil.softarch.luxurycarrental.ui;

import ch.unil.softarch.luxurycarrental.client.CarTypeClient;
import ch.unil.softarch.luxurycarrental.domain.entities.CarType;
import jakarta.annotation.PostConstruct;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;

import java.util.Collections;
import java.util.List;

@Named
@ViewScoped
public class CarTypeBean {

    private List<CarType> allCarTypes;

    private final CarTypeClient carTypeClient = new CarTypeClient();

    @PostConstruct
    public void init() {
        try {
            allCarTypes = carTypeClient.getAllCarTypes();
            System.out.println("CarTypes fetched: " + allCarTypes.size());
        } catch (Exception e) {
            e.printStackTrace();
            allCarTypes = Collections.emptyList();
        }
    }

    // Getter for XHTML
    public List<CarType> getAllCarTypes() {
        return allCarTypes;
    }
}