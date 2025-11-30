package ch.unil.softarch.luxurycarrental.ui;

import ch.unil.softarch.luxurycarrental.client.CarTypeClient;
import ch.unil.softarch.luxurycarrental.domain.entities.CarType;
import jakarta.annotation.PostConstruct;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
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
        loadCarTypes();
    }

    private void loadCarTypes() {
        try {
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
     * @return navigation string to car_type_form.xhtml
     */
    public String goToEdit(UUID id) {
        if (id != null) {
            return "/pages/admin/car_type_form.xhtml?faces-redirect=true&carTypeId=" + id;
        }
        return "/pages/admin/car_type_form.xhtml?faces-redirect=true";
    }

    /**
     * Delete a CarType by its ID.
     *
     * @param id UUID of the car type to delete
     */
    public void deleteCarType(UUID id) {
        if (id != null) {
            boolean success = carTypeClient.removeCarType(id);
            FacesContext context = FacesContext.getCurrentInstance();
            if (success) {
                context.addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_INFO, "Deleted successfully", null));
                // Refresh the list
                loadCarTypes();
            } else {
                context.addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, "Failed to delete", null));
            }
        }
    }
}