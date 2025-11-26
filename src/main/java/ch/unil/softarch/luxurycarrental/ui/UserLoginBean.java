package ch.unil.softarch.luxurycarrental.ui;

import ch.unil.softarch.luxurycarrental.client.CustomerClient;
import ch.unil.softarch.luxurycarrental.client.AdminClient;
import ch.unil.softarch.luxurycarrental.domain.entities.Customer;
import ch.unil.softarch.luxurycarrental.domain.entities.Admin;

import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.servlet.http.HttpSession;

import java.io.Serializable;

@SessionScoped
@Named
public class UserLoginBean implements Serializable {
    private static final long serialVersionUID = 1L;

    private String username;
    private String password;
    private String role;  // "customer" or "admin"

    @Inject
    private CustomerClient customerClient;

    @Inject
    private AdminClient adminClient;

    private Customer customer;
    private Admin admin;

    public UserLoginBean() {
        reset();
    }

    public void reset() {
        username = null;
        password = null;
        role = null;
        customer = null;
        admin = null;
    }

    public String login() {

        HttpSession session = getSession(true);

        try {
            if ("customer".equals(role)) {
                customer = customerClient.authenticate(username, password);
                if (customer != null) {
                    session.setAttribute("username", username);
                    session.setAttribute("role", "customer");
                    return "CustomerDashboard?faces-redirect=true";
                }
            }

            if ("admin".equals(role)) {
                admin = adminClient.authenticate(username, password);
                if (admin != null) {
                    session.setAttribute("username", username);
                    session.setAttribute("role", "admin");
                    return "AdminDashboard?faces-redirect=true";
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_ERROR,
                        "Invalid login credentials", null));

        reset();
        return "Login";
    }

    public String logout() {
        invalidateSession();
        reset();
        return "Login?faces-redirect=true";
    }

    // --- Session utils ---
    public static HttpSession getSession(boolean create) {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        if (facesContext == null) return null;
        return (HttpSession) facesContext.getExternalContext().getSession(create);
    }

    public static void invalidateSession() {
        HttpSession session = getSession(false);
        if (session != null) session.invalidate();
    }

    // --- Getters and Setters ---
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public Customer getCustomer() { return customer; }
    public Admin getAdmin() { return admin; }
}