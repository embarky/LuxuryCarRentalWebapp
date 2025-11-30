package ch.unil.softarch.luxurycarrental.ui;

import ch.unil.softarch.luxurycarrental.client.AdminClient;
import ch.unil.softarch.luxurycarrental.client.CustomerClient;
import ch.unil.softarch.luxurycarrental.domain.entities.Admin;
import ch.unil.softarch.luxurycarrental.domain.entities.Customer;

import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import java.io.Serializable;

@Named("userLoginBean")
@ViewScoped
public class UserLoginBean implements Serializable {

    private static final long serialVersionUID = 1L;

    // ===== Customer Login =====
    private String customerEmail;
    private String customerPassword;
    private Customer customer;

    // ===== Admin Login =====
    private String adminEmail;
    private String adminPassword;
    private Admin admin;

    // ===== REST Clients =====
    private final CustomerClient customerClient = new CustomerClient();
    private final AdminClient adminClient = new AdminClient();

    // ===== Customer Login =====
    public String loginCustomer() {
        FacesContext context = FacesContext.getCurrentInstance();

        try {
            if (customerEmail == null || customerEmail.isEmpty()
                    || customerPassword == null || customerPassword.isEmpty()) {
                context.addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, "Email and Password are required", null));
                return null;
            }

            // Authenticate customer via REST client
            customer = customerClient.authenticate(customerEmail, customerPassword);

            context.addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Customer login successful: " + customerEmail, null));

            // Redirect to customer dashboard
            return "CustomerDashboard?faces-redirect=true";

        } catch (Exception e) {
            customer = null;
            context.addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            "Customer login failed: " + (e.getMessage() != null ? e.getMessage() : "Unknown error"), null));
            return null;
        }
    }

    // ===== Admin Login =====
    public String loginAdmin() {
        FacesContext context = FacesContext.getCurrentInstance();

        try {
            if (adminEmail == null || adminEmail.isEmpty()
                    || adminPassword == null || adminPassword.isEmpty()) {
                context.addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, "Email and Password are required", null));
                return null;
            }

            // Authenticate admin via REST client
            admin = adminClient.loginAdmin(adminEmail, adminPassword);

            context.addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Admin login successful: " + adminEmail, null));

            // Redirect to admin dashboard
            return "AdminDashboard?faces-redirect=true";

        } catch (Exception e) {
            admin = null;
            context.addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            "Admin login failed: " + (e.getMessage() != null ? e.getMessage() : "Unknown error"), null));
            return null;
        }
    }

    // ===== Getters & Setters =====
    public String getCustomerEmail() { return customerEmail; }
    public void setCustomerEmail(String customerEmail) { this.customerEmail = customerEmail; }

    public String getCustomerPassword() { return customerPassword; }
    public void setCustomerPassword(String customerPassword) { this.customerPassword = customerPassword; }

    public Customer getCustomer() { return customer; }

    public String getAdminEmail() { return adminEmail; }
    public void setAdminEmail(String adminEmail) { this.adminEmail = adminEmail; }

    public String getAdminPassword() { return adminPassword; }
    public void setAdminPassword(String adminPassword) { this.adminPassword = adminPassword; }

    public Admin getAdmin() { return admin; }
}