package ch.unil.softarch.luxurycarrental.ui;

import ch.unil.softarch.luxurycarrental.client.AdminClient;
import ch.unil.softarch.luxurycarrental.domain.entities.Admin;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;

import java.io.Serializable;

@Named("adminSessionBean")
@SessionScoped
public class AdminSessionBean implements Serializable {

    private String email;       // Admin email
    private String password;    // Admin password
    private Admin admin;        // Currently logged-in admin

    private final AdminClient adminClient = new AdminClient();

    /**
     * Admin login
     * @return Redirects to the admin page on success, returns null and shows an error message on failure
     */
    public String login() {
        FacesContext context = FacesContext.getCurrentInstance();
        try {
            admin = adminClient.loginAdmin(email, password);

            if (admin != null) {
                // Login successful, redirect to admin page
                return "/pages/admin/admin.xhtml?faces-redirect=true";
            } else {
                // Login failed
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                        "Invalid email or password", null));
                return null;
            }
        } catch (Exception e) {
            // Catch any exception
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Login error: " + e.getMessage(), null));
            return null;
        }
    }

    /**
     * Admin logout
     * @return Redirects to the login page
     */
    public String logout() {
        admin = null;
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        return "/index.xhtml?faces-redirect=true";
    }

    // ===== Getters & Setters =====
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public Admin getAdmin() { return admin; }
}