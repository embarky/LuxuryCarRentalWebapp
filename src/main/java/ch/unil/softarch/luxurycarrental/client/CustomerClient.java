package ch.unil.softarch.luxurycarrental.client;

import ch.unil.softarch.luxurycarrental.domain.entities.Customer;
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
public class CustomerClient implements Serializable {
    private static final long serialVersionUID = 1L;

    private static final String BASE_URL = "http://localhost:8080/luxurycarrental/api/customers";
    private final Client client;
    private final WebTarget customerTarget;

    public CustomerClient() {
        client = ClientBuilder.newClient();
        customerTarget = client.target(BASE_URL);
    }

    // ---------------- CRUD ----------------

    public List<Customer> getAllCustomers() {
        return customerTarget
                .request(MediaType.APPLICATION_JSON)
                .get(new GenericType<List<Customer>>() {});
    }

    public Customer getCustomer(UUID id) {
        return customerTarget
                .path(id.toString())
                .request(MediaType.APPLICATION_JSON)
                .get(Customer.class);
    }

    public Customer addCustomer(Customer customer) {
        // Send a POST request to the customer REST endpoint with JSON payload
        Response response = customerTarget
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.entity(customer, MediaType.APPLICATION_JSON));

        // Print the HTTP response status for debugging
        System.out.println(">>> Status: " + response.getStatus());

        if (response.getStatus() == 200 || response.getStatus() == 201) {
            // If the request is successful, read and return the Customer object
            return response.readEntity(Customer.class);
        } else {
            // If the request fails, read the response body as String and print it
            String body = response.readEntity(String.class);
            System.out.println(">>> Body: " + body);
            return null;
        }
    }

    public Customer updateCustomer(UUID id, Customer customer) {
        return customerTarget
                .path(id.toString())
                .request(MediaType.APPLICATION_JSON)
                .put(Entity.entity(customer, MediaType.APPLICATION_JSON), Customer.class);
    }

    public boolean removeCustomer(UUID id) {
        Response response = customerTarget
                .path(id.toString())
                .request(MediaType.APPLICATION_JSON)
                .delete();
        return response.getStatus() == 200;
    }

    // ---------------- Login ----------------

    public Map<String, Object> login(String email, String password) {
        Map<String, String> body = Map.of("email", email, "password", password);
        return customerTarget
                .path("login")
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.entity(body, MediaType.APPLICATION_JSON),
                        new GenericType<Map<String, Object>>() {});
    }

    // ---------------- Customer ----------------

    /**
     * Send password reset code to customer via email
     */
    public Map<String, String> sendCustomerResetCode(String email) {
        Map<String, String> body = Map.of("email", email);
        return customerTarget
                .path("password-reset-code")
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.entity(body, MediaType.APPLICATION_JSON),
                        new GenericType<Map<String, String>>() {});
    }

    /**
     * Reset customer password with code and new password
     */
    public Map<String, String> resetCustomerPassword(String email, String code, String newPassword) {
        Map<String, String> body = Map.of(
                "email", email,
                "code", code,
                "newPassword", newPassword
        );
        return customerTarget
                .path("reset-password")
                .request(MediaType.APPLICATION_JSON)
                .put(Entity.entity(body, MediaType.APPLICATION_JSON),
                        new GenericType<Map<String, String>>() {});
    }

    // Existing authentication
    public Customer authenticate(String email, String password) {
        Map<String, String> requestBody = Map.of(
                "email", email,
                "password", password
        );

        Response response = client.target(BASE_URL + "/login")
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.json(requestBody));

        if (response.getStatus() == 200) {
            // Return Customer object
            return response.readEntity(Customer.class);
        } else {
            // Login failed, throw exception or return null
            throw new RuntimeException("Login failed: " + response.readEntity(Map.class).get("message"));
        }
    }

    // --- NEW: find by username ---
    public Customer findByUsername(String username) {
        return customerTarget.path("by-username")
                .queryParam("username", username)
                .request(MediaType.APPLICATION_JSON)
                .get(Customer.class);
    }
}