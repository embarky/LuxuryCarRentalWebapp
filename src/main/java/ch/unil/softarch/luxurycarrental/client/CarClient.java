package ch.unil.softarch.luxurycarrental.client;

import ch.unil.softarch.luxurycarrental.domain.entities.Car;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.GenericType;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;
import java.util.UUID;

public class CarClient {

    private static final String BASE_URL = "http://localhost:8080/LuxuryCarRental/api/cars";
    private final Client client;
    private final WebTarget carTarget;

    public CarClient() {
        client = ClientBuilder.newClient();
        carTarget = client.target(BASE_URL);
    }

    // ---------------- CRUD ----------------

    public List<Car> getAllCars() {
        return carTarget
                .request(MediaType.APPLICATION_JSON)
                .get(new GenericType<List<Car>>() {});
    }

    public Car getCar(UUID id) {
        return carTarget
                .path(id.toString())
                .request(MediaType.APPLICATION_JSON)
                .get(Car.class);
    }

    public Car addCar(Car car) {
        return carTarget
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.entity(car, MediaType.APPLICATION_JSON), Car.class);
    }

    public Car updateCar(UUID id, Car car) {
        return carTarget
                .path(id.toString())
                .request(MediaType.APPLICATION_JSON)
                .put(Entity.entity(car, MediaType.APPLICATION_JSON), Car.class);
    }

    public boolean removeCar(UUID id) {
        Response response = carTarget
                .path(id.toString())
                .request(MediaType.APPLICATION_JSON)
                .delete();
        return response.getStatus() == 200;
    }

    // ---------------- Search / Filter ----------------

    public List<Car> searchCars(String q, String status, Double minPrice, Double maxPrice, UUID carTypeId) {
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
