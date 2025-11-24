package ch.unil.softarch.luxurycarrental.client;

import ch.unil.softarch.luxurycarrental.domain.entities.Customer;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.GenericType;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public class CustomerClient {

    private static final String BASE_URL = "http://localhost:8080/LuxuryCarRental/api/customers";
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
        return customerTarget
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.entity(customer, MediaType.APPLICATION_JSON), Customer.class);
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

    // ---------------- Password Reset ----------------

    public Map<String, String> sendPasswordResetCode(UUID id) {
        return customerTarget
                .path(id.toString())
                .path("password-reset-code")
                .request(MediaType.APPLICATION_JSON)
                .post(null, new GenericType<Map<String, String>>() {});
    }

    public Map<String, String> resetPasswordWithCode(UUID id, String code, String newPassword) {
        Map<String, String> body = Map.of("code", code, "newPassword", newPassword);
        return customerTarget
                .path(id.toString())
                .path("reset-password")
                .request(MediaType.APPLICATION_JSON)
                .put(Entity.entity(body, MediaType.APPLICATION_JSON),
                        new GenericType<Map<String, String>>() {});
    }
}