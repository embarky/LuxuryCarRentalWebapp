package ch.unil.softarch.luxurycarrental.client;

import ch.unil.softarch.luxurycarrental.domain.entities.CarType;
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
import java.util.UUID;

@ApplicationScoped
public class CarTypeClient implements Serializable {

    private static final long serialVersionUID = 1L;

    // Base URL for CarType REST API
    private static final String BASE_URL = "http://localhost:8080/luxurycarrental/api/cartypes";

    private final Client client;
    private final WebTarget carTypeTarget;

    public CarTypeClient() {
        client = ClientBuilder.newClient();
        carTypeTarget = client.target(BASE_URL);
    }

    // ---------------- CRUD ----------------

    /**
     * Get a list of all car types.
     */
    public List<CarType> getAllCarTypes() {
        return carTypeTarget
                .request(MediaType.APPLICATION_JSON)
                .get(new GenericType<List<CarType>>() {});
    }

    /**
     * Get a single CarType by UUID.
     */
    public CarType getCarType(UUID id) {
        return carTypeTarget
                .path(id.toString())
                .request(MediaType.APPLICATION_JSON)
                .get(CarType.class);
    }

    /**
     * Add a new CarType (POST).
     */
    public CarType addCarType(CarType carType) {
        Response response = carTypeTarget
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.entity(carType, MediaType.APPLICATION_JSON));

        System.out.println(">>> Status: " + response.getStatus());

        if (response.getStatus() == 200 || response.getStatus() == 201) {
            return response.readEntity(CarType.class);
        } else {
            String body = response.readEntity(String.class);
            System.out.println(">>> Body: " + body);
            return null;
        }
    }

    /**
     * Update an existing CarType (PUT).
     */
    public CarType updateCarType(UUID id, CarType carType) {
        Response response = carTypeTarget
                .path(id.toString())
                .request(MediaType.APPLICATION_JSON)
                .put(Entity.entity(carType, MediaType.APPLICATION_JSON));

        System.out.println(">>> Status: " + response.getStatus());

        if (response.getStatus() == 200) {
            return response.readEntity(CarType.class);
        } else {
            String body = response.readEntity(String.class);
            System.out.println(">>> Body: " + body);
            return null;
        }
    }

    /**
     * Delete a CarType by ID.
     */
    public boolean removeCarType(UUID id) {
        Response response = carTypeTarget
                .path(id.toString())
                .request(MediaType.APPLICATION_JSON)
                .delete();

        System.out.println(">>> Status: " + response.getStatus());
        return response.getStatus() == 200;
    }
}