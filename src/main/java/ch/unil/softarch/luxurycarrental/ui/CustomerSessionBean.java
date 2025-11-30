package ch.unil.softarch.luxurycarrental.ui;

import ch.unil.softarch.luxurycarrental.domain.entities.Customer;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;
import java.io.Serializable;

@Named("customerSessionBean")
@SessionScoped
public class CustomerSessionBean implements Serializable {

    private Customer customer;

    // Check whether the customer is logged in
    public boolean isLoggedIn() {
        return customer != null;
    }

    public Customer getCustomer() {
        return customer;
    }

    // Set after login
    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    // Logout method
    public String logout() {
        customer = null;
        return "/index.xhtml?faces-redirect=true";
    }
}