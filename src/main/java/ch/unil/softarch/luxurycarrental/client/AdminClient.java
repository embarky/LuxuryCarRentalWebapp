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

    private static final String BASE_URL = "http://localhost:8080/LuxuryCarRental/api/admins";
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
        return adminTarget
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.entity(admin, MediaType.APPLICATION_JSON), Admin.class);
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

    // Login
    public Map<String, Object> login(String username, String password) {
        Map<String, String> body = Map.of("username", username, "password", password);
        return adminTarget
                .path("login")
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.entity(body, MediaType.APPLICATION_JSON), new GenericType<Map<String, Object>>() {});
    }

    // Send password reset code
    public Map<String, String> sendPasswordResetCode(UUID id) {
        return adminTarget
                .path(id.toString())
                .path("password-reset-code")
                .request(MediaType.APPLICATION_JSON)
                .post(null, new GenericType<Map<String, String>>() {});
    }

    // Reset password with code
    public Map<String, String> resetPasswordWithCode(UUID id, String code, String newPassword) {
        Map<String, String> body = Map.of("code", code, "newPassword", newPassword);
        return adminTarget
                .path(id.toString())
                .path("reset-password")
                .request(MediaType.APPLICATION_JSON)
                .put(Entity.entity(body, MediaType.APPLICATION_JSON), new GenericType<Map<String, String>>() {});
    }

    public Admin authenticate(String username, String password) {
        try {
            var json = """
                {
                    "username": "%s",
                    "password": "%s"
                }
            """.formatted(username, password);

            return client.target(BASE_URL + "/login")
                    .request(MediaType.APPLICATION_JSON)
                    .post(Entity.json(json), Admin.class);

        } catch (Exception e) {
            System.out.println("Admin login failed: " + e.getMessage());
            return null;
        }
    }
}