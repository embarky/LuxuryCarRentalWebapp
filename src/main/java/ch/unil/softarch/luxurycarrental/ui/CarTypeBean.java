package ch.unil.softarch.luxurycarrental.ui;

import ch.unil.softarch.luxurycarrental.client.CarTypeClient;
import ch.unil.softarch.luxurycarrental.domain.entities.CarType;
import jakarta.annotation.PostConstruct;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Named("carTypeBean")
@ViewScoped
public class CarTypeBean implements Serializable {

    private static final long serialVersionUID = 1L;

    private List<CarType> carTypes;

    private final CarTypeClient carTypeClient = new CarTypeClient();

    @PostConstruct
    public void init() {
        try {
            // Load all car types from backend via REST client
            carTypes = carTypeClient.getAllCarTypes();
            System.out.println("Fetched car types: " + carTypes.size());
        } catch (Exception e) {
            e.printStackTrace();
            carTypes = Collections.emptyList();
        }
    }

    public List<CarType> getCarTypes() {
        return carTypes;
    }

    /**
     * Navigate to the CarType edit page.
     *
     * @param id ID of the CarType to edit.
     *           If null → open the page with an empty form (create new).
     *
     * @return navigation string to go to edit_car_type.xhtml
     */
    public String goToEdit(UUID id) {

        if (id != null) {
            // Put id in URL so "edit" page can load the CarType
            return "/pages/admin/car_type_form.xhtml?faces-redirect=true&id=" + id;
        }

        // id is null → open blank form for creating new CarType
        return "/pages/admin/car_type_form.xhtml?faces-redirect=true";
    }
}