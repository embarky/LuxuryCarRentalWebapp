package ch.unil.softarch.luxurycarrental.ui;

import ch.unil.softarch.luxurycarrental.client.AdminClient;
import ch.unil.softarch.luxurycarrental.client.CustomerClient;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.io.Serializable;
import java.util.Map;

@Named("resetBean")
@ViewScoped
public class ResetBean implements Serializable {

    private static final long serialVersionUID = 1L;

    @Inject
    private AdminClient adminClient;

    @Inject
    private CustomerClient customerClient;

    // ===== Admin Fields =====
    private String adminEmail;
    private String adminCode;
    private String adminNewPassword;

    // ===== Customer Fields =====
    private String customerEmail;
    private String customerCode;
    private String customerNewPassword;

    // ==========================
    //       ADMIN ACTIONS
    // ==========================
    public void sendAdminResetCode() {
        try {
            Map<String, String> result = adminClient.sendAdminResetCode(adminEmail);
            addInfo(result.getOrDefault("message", "Admin verification code sent successfully."));
        } catch (Exception e) {
            addError("Failed to send admin verification code: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void resetAdminPassword() {
        try {
            Map<String, String> result = adminClient.resetAdminPassword(adminEmail, adminCode, adminNewPassword);
            addInfo(result.getOrDefault("message", "Admin password reset successfully."));
        } catch (Exception e) {
            addError("Admin password reset failed: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // ==========================
    //     CUSTOMER ACTIONS
    // ==========================
    public void sendCustomerResetCode() {
        try {
            Map<String, String> result = customerClient.sendCustomerResetCode(customerEmail);
            addInfo(result.getOrDefault("message", "Customer verification code sent successfully."));
        } catch (Exception e) {
            addError("Failed to send customer verification code: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void resetCustomerPassword() {
        try {
            Map<String, String> result = customerClient.resetCustomerPassword(customerEmail, customerCode, customerNewPassword);
            addInfo(result.getOrDefault("message", "Customer password reset successfully."));
        } catch (Exception e) {
            addError("Customer password reset failed: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // ==========================
    //      HELPER METHODS
    // ==========================
    private void addInfo(String message) {
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_INFO, message, null));
    }

    private void addError(String message) {
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_ERROR, message, null));
    }

    // ==========================
    //       GETTERS / SETTERS
    // ==========================
    public String getAdminEmail() { return adminEmail; }
    public void setAdminEmail(String adminEmail) { this.adminEmail = adminEmail; }

    public String getAdminCode() { return adminCode; }
    public void setAdminCode(String adminCode) { this.adminCode = adminCode; }

    public String getAdminNewPassword() { return adminNewPassword; }
    public void setAdminNewPassword(String adminNewPassword) { this.adminNewPassword = adminNewPassword; }

    public String getCustomerEmail() { return customerEmail; }
    public void setCustomerEmail(String customerEmail) { this.customerEmail = customerEmail; }

    public String getCustomerCode() { return customerCode; }
    public void setCustomerCode(String customerCode) { this.customerCode = customerCode; }

    public String getCustomerNewPassword() { return customerNewPassword; }
    public void setCustomerNewPassword(String customerNewPassword) { this.customerNewPassword = customerNewPassword; }
}