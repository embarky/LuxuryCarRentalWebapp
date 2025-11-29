package ch.unil.softarch.luxurycarrental.client;

import ch.unil.softarch.luxurycarrental.domain.entities.Car;
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
public class CarClient implements Serializable {

    private static final long serialVersionUID = 1L;

    private static final String BASE_URL = "http://localhost:8080/luxurycarrental/api/cars";
    private final Client client;
    private final WebTarget carTarget;

    public CarClient() {
        client = ClientBuilder.newClient();
        carTarget = client.target(BASE_URL);
    }

    // ---------------- CRUD ----------------

    /**
     * Fetch all cars from the REST API.
     */
    public List<Car> getAllCars() {
        return carTarget
                .request(MediaType.APPLICATION_JSON)
                .get(new GenericType<List<Car>>() {});
    }

    /**
     * Fetch a single car by ID.
     */
    public Car getCar(UUID id) {
        return carTarget
                .path(id.toString())
                .request(MediaType.APPLICATION_JSON)
                .get(Car.class);
    }

    /**
     * Add a new car.
     */
    public Car addCar(Car car) {
        Response response = carTarget
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.entity(car, MediaType.APPLICATION_JSON));

        System.out.println(">>> Status: " + response.getStatus());

        if (response.getStatus() == 200 || response.getStatus() == 201) {
            return response.readEntity(Car.class);
        } else {
            String body = response.readEntity(String.class);
            System.out.println(">>> Body: " + body);
            return null;
        }
    }

    /**
     * Update an existing car.
     */
    public Car updateCar(UUID id, Car car) {

        Response response = carTarget
                .path(id.toString())
                .request(MediaType.APPLICATION_JSON)
                .put(Entity.entity(car, MediaType.APPLICATION_JSON));

        System.out.println(">>> Status: " + response.getStatus());

        if (response.getStatus() == 200) {
            return response.readEntity(Car.class);
        } else {
            String body = response.readEntity(String.class);
            System.out.println(">>> Body: " + body);
            return null;
        }
    }

    /**
     * Delete a car by ID.
     */
    public boolean removeCar(UUID id) {
        Response response = carTarget
                .path(id.toString())
                .request(MediaType.APPLICATION_JSON)
                .delete();

        System.out.println(">>> Delete Status: " + response.getStatus());

        return response.getStatus() == 200;
    }

    // ---------------- Search / Filter ----------------

    /**
     * Search for cars with multiple optional filters.
     */
    public List<Car> searchCars(String q, String status, Double minPrice,
                                Double maxPrice, UUID carTypeId) {

        WebTarget target = carTarget.path("search");

        if (q != null && !q.isBlank()) target = target.queryParam("q", q);
        if (status != null && !status.isBlank()) target = target.queryParam("status", status);
        if (minPrice != null) target = target.queryParam("minPrice", minPrice);
        if (maxPrice != null) target = target.queryParam("maxPrice", maxPrice);
        if (carTypeId != null) target = target.queryParam("carTypeId", carTypeId.toString());

        return target.request(MediaType.APPLICATION_JSON)
                .get(new GenericType<List<Car>>() {});
    }
}