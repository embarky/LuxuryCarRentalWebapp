package ch.unil.softarch.luxurycarrental.ui;

import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.io.Serializable;

@Named
@SessionScoped
public class UserRegisterBean implements Serializable {

    private static final long serialVersionUID = 1L;

    // --- Common properties ---
    private String role = "customer"; // "customer" or "admin"

    // Admin fields
    private String adminUsername;
    private String adminPassword;
    private String adminName;
    private String adminEmail;

    // Customer fields
    private String firstName;
    private String lastName;
    private String password;
    private String email;
    private String phoneNumber;
    private String drivingLicenseNumber;
    private String drivingLicenseExpiryDate; // YYYY-MM-DD
    private int age;
    private boolean verifiedIdentity;
    private String billingAddress;
    private double balance;

    // --- Getters & setters ---
    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    // Admin getters/setters
    public String getAdminUsername() { return adminUsername; }
    public void setAdminUsername(String adminUsername) { this.adminUsername = adminUsername; }

    public String getAdminPassword() { return adminPassword; }
    public void setAdminPassword(String adminPassword) { this.adminPassword = adminPassword; }

    public String getAdminName() { return adminName; }
    public void setAdminName(String adminName) { this.adminName = adminName; }

    public String getAdminEmail() { return adminEmail; }
    public void setAdminEmail(String adminEmail) { this.adminEmail = adminEmail; }

    // Customer getters/setters
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

    public String getDrivingLicenseExpiryDate() { return drivingLicenseExpiryDate; }
    public void setDrivingLicenseExpiryDate(String drivingLicenseExpiryDate) { this.drivingLicenseExpiryDate = drivingLicenseExpiryDate; }

    public int getAge() { return age; }
    public void setAge(int age) { this.age = age; }

    public boolean isVerifiedIdentity() { return verifiedIdentity; }
    public void setVerifiedIdentity(boolean verifiedIdentity) { this.verifiedIdentity = verifiedIdentity; }

    public String getBillingAddress() { return billingAddress; }
    public void setBillingAddress(String billingAddress) { this.billingAddress = billingAddress; }

    public double getBalance() { return balance; }
    public void setBalance(double balance) { this.balance = balance; }

    // --- Methods to register ---
    public void registerAdmin() {
        try {
            Client client = ClientBuilder.newClient();
            String json = String.format(
                    "{\"username\":\"%s\",\"password\":\"%s\",\"name\":\"%s\",\"email\":\"%s\"}",
                    adminUsername, adminPassword, adminName, adminEmail
            );
            Response response = client.target("http://localhost:8080/api/admin/register")
                    .request(MediaType.APPLICATION_JSON)
                    .post(Entity.entity(json, MediaType.APPLICATION_JSON));

            if (response.getStatus() == 200 || response.getStatus() == 201) {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_INFO, "Admin registered successfully!", null));
            } else {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, "Failed to register admin. Status: " + response.getStatus(), null));
            }
            client.close();
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Exception: " + e.getMessage(), null));
        }
    }

    public void registerCustomer() {
        try {
            Client client = ClientBuilder.newClient();
            String json = String.format(
                    "{" +
                            "\"firstName\":\"%s\"," +
                            "\"lastName\":\"%s\"," +
                            "\"password\":\"%s\"," +
                            "\"email\":\"%s\"," +
                            "\"phoneNumber\":\"%s\"," +
                            "\"drivingLicenseNumber\":\"%s\"," +
                            "\"drivingLicenseExpiryDate\":\"%s\"," +
                            "\"age\":%d," +
                            "\"verifiedIdentity\":%b," +
                            "\"billingAddress\":\"%s\"," +
                            "\"balance\":%.2f" +
                            "}",
                    firstName, lastName, password, email, phoneNumber,
                    drivingLicenseNumber, drivingLicenseExpiryDate,
                    age, verifiedIdentity, billingAddress, balance
            );

            Response response = client.target("http://localhost:8080/api/customer/register")
                    .request(MediaType.APPLICATION_JSON)
                    .post(Entity.entity(json, MediaType.APPLICATION_JSON));

            if (response.getStatus() == 200 || response.getStatus() == 201) {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_INFO, "Customer registered successfully!", null));
            } else {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, "Failed to register customer. Status: " + response.getStatus(), null));
            }

            client.close();
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Exception: " + e.getMessage(), null));
        }
    }
}
