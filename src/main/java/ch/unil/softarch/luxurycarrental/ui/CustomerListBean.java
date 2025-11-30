package ch.unil.softarch.luxurycarrental.ui;

import ch.unil.softarch.luxurycarrental.client.CustomerClient;
import ch.unil.softarch.luxurycarrental.domain.entities.Customer;
import jakarta.annotation.PostConstruct;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Named("customerListBean")
@ViewScoped
public class CustomerListBean implements Serializable {

    private List<Customer> customers;

    private final CustomerClient customerClient = new CustomerClient();

    @PostConstruct
    public void init() {
        try {
            customers = customerClient.getAllCustomers();
            System.out.println("Fetched customers: " + customers.size());
        } catch (Exception e) {
            e.printStackTrace();
            customers = Collections.emptyList();
        }
    }

    public List<Customer> getCustomers() {
        return customers;
    }

    public void deleteCustomer(UUID id) {
        if (id != null) {
            boolean success = customerClient.removeCustomer(id);
            FacesContext context = FacesContext.getCurrentInstance();
            if (success) {
                context.addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_INFO, "Deleted successfully", null));
                // Refresh the list
                init();
            } else {
                context.addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, "Failed to delete", null));
            }
        }
    }

    /**
     * Verify customer identity
     */
    public void verifyCustomer(UUID id) {
        Customer updated = customerClient.verifyCustomer(id);
    }
}