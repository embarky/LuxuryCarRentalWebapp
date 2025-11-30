package ch.unil.softarch.luxurycarrental.ui;

import ch.unil.softarch.luxurycarrental.client.CarClient;
import ch.unil.softarch.luxurycarrental.domain.entities.Car;
import jakarta.annotation.PostConstruct;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

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

    /**
     * Navigate to the Car edit page.
     */
    public String goToEdit(UUID id) {
        if (id != null) {
            return "/pages/admin/car_form.xhtml?faces-redirect=true&carId=" + id;
        }
        return "/pages/admin/car_form.xhtml?faces-redirect=true";
    }

    /**
     * Delete a car by ID.
     */
    public void deleteCar(UUID id) {
        FacesContext context = FacesContext.getCurrentInstance();

        try {
            boolean success = carClient.removeCar(id);

            if (success) {
                context.addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_INFO, "Car deleted successfully!", null));

                // Refresh list
                cars = carClient.getAllCars();

            } else {
                context.addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, "Failed to delete car", null));
            }

        } catch (Exception e) {
            context.addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error while deleting car", null));
            e.printStackTrace();
        }
    }
}