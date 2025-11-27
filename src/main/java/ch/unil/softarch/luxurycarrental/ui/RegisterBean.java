package ch.unil.softarch.luxurycarrental.ui;

import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;

import java.io.Serializable;
import java.util.Date;

import ch.unil.softarch.luxurycarrental.client.CustomerClient;
import ch.unil.softarch.luxurycarrental.client.AdminClient;
import ch.unil.softarch.luxurycarrental.domain.entities.Admin;
import ch.unil.softarch.luxurycarrental.domain.entities.Customer;

@Named("registerBean")
@ViewScoped
public class RegisterBean implements Serializable {

    private static final long serialVersionUID = 1L;

    @Inject
    private AdminClient adminClient;

    @Inject
    private CustomerClient customerClient;

    // ===== Admin fields =====
    private String adminUsername;
    private String adminPassword;
    private String adminName;
    private String adminEmail;

    // ===== Customer fields =====
    private String firstName;
    private String lastName;
    private String password;
    private String email;
    private String phoneNumber;
    private String drivingLicenseNumber;
    private Date drivingLicenseExpiryDate;
    private int age = 0;;
    private boolean verifiedIdentity;
    private String billingAddress;
    private double balance = 0.0;

    // ========== REGISTER ADMIN ==========
    public String registerAdmin() {
        try {
            Admin admin = new Admin();
            admin.setUsername(adminUsername);
            admin.setPassword(adminPassword);
            admin.setName(adminName);
            admin.setEmail(adminEmail);

            Admin result = adminClient.addAdmin(admin);

            addInfo("Admin registered: " + result.getUsername());
            return null; // stay on same page

        } catch (Exception e) {
            addError("Admin registration failed: " + e.getMessage());
            return null;
        }

    }

    // ========== REGISTER CUSTOMER ==========
    public String registerCustomer() {
        try {
            Customer customer = new Customer();
            customer.setFirstName(firstName);
            customer.setLastName(lastName);
            customer.setPassword(password);
            customer.setEmail(email);
            customer.setPhoneNumber(phoneNumber);
            customer.setDrivingLicenseNumber(drivingLicenseNumber);
            customer.setDrivingLicenseExpiryDate(drivingLicenseExpiryDate);
            customer.setAge(age);
            customer.setVerifiedIdentity(verifiedIdentity);
            customer.setBillingAddress(billingAddress);
            customer.setBalance(balance);

            Customer result = customerClient.addCustomer(customer);

            addInfo("Customer registered: " + result.getEmail());
            return null;

        } catch (Exception e) {
            addError("Customer registration failed: " + e.getMessage());
            return null;
        }
    }

    // ========== Message helpers ==========
    private void addInfo(String msg) {
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_INFO, msg, null));
    }

    private void addError(String msg) {
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, null));
    }

    // ===== Getters / Setters =====
    public String getAdminUsername() { return adminUsername; }
    public void setAdminUsername(String adminUsername) { this.adminUsername = adminUsername; }

    public String getAdminPassword() { return adminPassword; }
    public void setAdminPassword(String adminPassword) { this.adminPassword = adminPassword; }

    public String getAdminName() { return adminName; }
    public void setAdminName(String adminName) { this.adminName = adminName; }

    public String getAdminEmail() { return adminEmail; }
    public void setAdminEmail(String adminEmail) { this.adminEmail = adminEmail; }

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }

    public String getDrivingLicenseNumber() { return drivingLicenseNumber; }
    public void setDrivingLicenseNumber(String drivingLicenseNumber) { this.drivingLicenseNumber = drivingLicenseNumber; }

    public Date getDrivingLicenseExpiryDate() { return drivingLicenseExpiryDate; }
    public void setDrivingLicenseExpiryDate(Date drivingLicenseExpiryDate) { this.drivingLicenseExpiryDate = drivingLicenseExpiryDate; }

    public int getAge() { return age; }
    public void setAge(int age) { this.age = age; }

    public boolean isVerifiedIdentity() { return verifiedIdentity; }
    public void setVerifiedIdentity(boolean verifiedIdentity) { this.verifiedIdentity = verifiedIdentity; }

    public String getBillingAddress() { return billingAddress; }
    public void setBillingAddress(String billingAddress) { this.billingAddress = billingAddress; }

    public double getBalance() { return balance; }
    public void setBalance(double balance) { this.balance = balance; }

}