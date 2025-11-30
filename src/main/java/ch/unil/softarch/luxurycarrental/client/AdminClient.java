package ch.unil.softarch.luxurycarrental.client;

import ch.unil.softarch.luxurycarrental.domain.entities.Admin;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.GenericType;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@ApplicationScoped
public class AdminClient implements Serializable {
    private static final long serialVersionUID = 1L;

    private static final String BASE_URL = "http://localhost:8080/luxurycarrental/api/admins";
    private final Client client;
    private final WebTarget adminTarget;

    public AdminClient() {
        client = ClientBuilder.newClient();
        adminTarget = client.target(BASE_URL);
    }

    // Get all admins
    public List<Admin> getAllAdmins() {
        return adminTarget
                .request(MediaType.APPLICATION_JSON)
                .get(new GenericType<List<Admin>>() {});
    }

    // Get one admin by ID
    public Admin getAdmin(UUID id) {
        return adminTarget
                .path(id.toString())
                .request(MediaType.APPLICATION_JSON)
                .get(Admin.class);
    }

    // Add new admin
    public Admin addAdmin(Admin admin) {
        Response response = adminTarget
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.entity(admin, MediaType.APPLICATION_JSON));

        if (response.getStatus() == 200 || response.getStatus() == 201) {
            return response.readEntity(Admin.class);
        } else {
            System.out.println(">>> Status: " + response.getStatus());
            String body = response.readEntity(String.class);
            System.out.println(">>> Body: " + body);
            return null;
        }
    }

    // Update existing admin
    public Admin updateAdmin(UUID id, Admin update) {
        return adminTarget
                .path(id.toString())
                .request(MediaType.APPLICATION_JSON)
                .put(Entity.entity(update, MediaType.APPLICATION_JSON), Admin.class);
    }

    // Delete admin
    public boolean removeAdmin(UUID id) {
        Response response = adminTarget
                .path(id.toString())
                .request(MediaType.APPLICATION_JSON)
                .delete();
        return response.getStatus() == 200;
    }

// ---------------- Admin ----------------

    /**
     * Send password reset code to admin via email
     */
    public Map<String, String> sendAdminResetCode(String email) {
        Map<String, String> body = Map.of("email", email);
        return adminTarget
                .path("password-reset-code")
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.entity(body, MediaType.APPLICATION_JSON),
                        new GenericType<Map<String, String>>() {});
    }

    /**
     * Reset admin password with code and new password
     */
    public Map<String, String> resetAdminPassword(String email, String code, String newPassword) {
        Map<String, String> body = Map.of(
                "email", email,
                "code", code,
                "newPassword", newPassword
        );
        return adminTarget
                .path("reset-password")
                .request(MediaType.APPLICATION_JSON)
                .put(Entity.entity(body, MediaType.APPLICATION_JSON),
                        new GenericType<Map<String, String>>() {});
    }

    /**
     * Login admin via REST
     */
    public Admin loginAdmin(String email, String password) {
        Map<String, String> requestBody = Map.of(
                "email", email,
                "password", password
        );

        Response response = client.target(BASE_URL + "/login")
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.json(requestBody));

        if (response.getStatus() == 200) {
            return response.readEntity(Admin.class);
        } else {
            System.out.println("Login failed: " + response.getStatus());
            System.out.println(response.readEntity(String.class));
            return null;
        }
    }

    // --- NEW: find by username ---
    public Admin findByUsername(String username) {
        return adminTarget.path("by-username")
                .queryParam("username", username)
                .request(MediaType.APPLICATION_JSON)
                .get(Admin.class);
    }
}