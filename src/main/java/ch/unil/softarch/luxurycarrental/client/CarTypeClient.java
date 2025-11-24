package ch.unil.softarch.luxurycarrental.client;

import ch.unil.softarch.luxurycarrental.domain.entities.CarType;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.GenericType;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;
import java.util.UUID;

public class CarTypeClient {

    private static final String BASE_URL = "http://localhost:8080/LuxuryCarRental/api/cartypes";
    private final Client client;
    private final WebTarget carTypeTarget;

    public CarTypeClient() {
        client = ClientBuilder.newClient();
        carTypeTarget = client.target(BASE_URL);
    }

    // ---------------- CRUD ----------------

    public List<CarType> getAllCarTypes() {
        return carTypeTarget
                .request(MediaType.APPLICATION_JSON)
                .get(new GenericType<List<CarType>>() {});
    }

    public CarType getCarType(UUID id) {
        return carTypeTarget
                .path(id.toString())
                .request(MediaType.APPLICATION_JSON)
                .get(CarType.class);
    }

    public CarType addCarType(CarType carType) {
        return carTypeTarget
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.entity(carType, MediaType.APPLICATION_JSON), CarType.class);
    }

    public CarType updateCarType(UUID id, CarType carType) {
        return carTypeTarget
                .path(id.toString())
                .request(MediaType.APPLICATION_JSON)
                .put(Entity.entity(carType, MediaType.APPLICATION_JSON), CarType.class);
    }

    public boolean removeCarType(UUID id) {
        Response response = carTypeTarget
                .path(id.toString())
                .request(MediaType.APPLICATION_JSON)
                .delete();
        return response.getStatus() == 200;
    }
}