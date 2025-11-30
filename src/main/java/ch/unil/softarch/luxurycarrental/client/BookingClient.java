package ch.unil.softarch.luxurycarrental.client;

import ch.unil.softarch.luxurycarrental.domain.entities.Booking;
import jakarta.ws.rs.client.Client;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.GenericType;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class BookingClient implements Serializable {

    private static final String BASE_URL = "http://localhost:8080/luxurycarrental/api/bookings";
    private final Client client;
    private final WebTarget bookingTarget;

    public BookingClient() {
        client = ClientBuilder.newClient();
        bookingTarget = client.target(BASE_URL);
    }

    // ---------------- CRUD ----------------

    public List<Booking> getAllBookings() {
        return bookingTarget
                .request(MediaType.APPLICATION_JSON)
                .get(new GenericType<List<Booking>>() {});
    }

    public Booking getBooking(UUID bookingId) {
        return bookingTarget
                .path(bookingId.toString())
                .request(MediaType.APPLICATION_JSON)
                .get(Booking.class);
    }

    public List<Booking> getBookingsByCustomer(UUID customerId) {
        return bookingTarget
                .path("customer")
                .path(customerId.toString())
                .request(MediaType.APPLICATION_JSON)
                .get(new GenericType<List<Booking>>() {});
    }

    public List<Booking> getBookingsByCar(UUID carId) {
        return bookingTarget
                .path("car")
                .path(carId.toString())
                .request(MediaType.APPLICATION_JSON)
                .get(new GenericType<List<Booking>>() {});
    }

    public Booking createBooking(UUID customerId, UUID carId, LocalDate startDate, LocalDate endDate) {
        BookingRequest request = new BookingRequest(customerId, carId, startDate, endDate);
        return bookingTarget
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.entity(request, MediaType.APPLICATION_JSON), Booking.class);
    }

    public Booking updateBooking(UUID bookingId, Booking update) {
        return bookingTarget
                .path(bookingId.toString())
                .request(MediaType.APPLICATION_JSON)
                .put(Entity.entity(update, MediaType.APPLICATION_JSON), Booking.class);
    }

    public boolean removeBooking(UUID bookingId) {
        Response response = bookingTarget
                .path(bookingId.toString())
                .request(MediaType.APPLICATION_JSON)
                .delete();
        return response.getStatus() == 200;
    }

    // ---------------- Operations ----------------

    public Booking payBooking(UUID bookingId) {
        return bookingTarget
                .path(bookingId.toString())
                .path("pay")
                .request(MediaType.APPLICATION_JSON)
                .post(null, Booking.class);
    }

    public boolean completeBooking(UUID bookingId) {
        Response response = bookingTarget
                .path(bookingId.toString())
                .path("complete")
                .request(MediaType.APPLICATION_JSON)
                .put(Entity.json("{}"));
        boolean success = response.getStatus() == 200;
        response.close();
        return success;
    }

    public boolean cancelBooking(UUID bookingId) {
        Response response = bookingTarget
                .path(bookingId.toString())
                .path("cancel")
                .request(MediaType.APPLICATION_JSON)
                .put(Entity.json("{}"));
        boolean success = response.getStatus() == 200;
        response.close();
        return success;
    }

    public Booking rejectBooking(UUID bookingId, String reason) {
        return bookingTarget
                .path(bookingId.toString())
                .path("reject")
                .queryParam("reason", reason)
                .request(MediaType.APPLICATION_JSON)
                .post(null, Booking.class);
    }

    public boolean confirmBooking(UUID bookingId) {
        Response response = bookingTarget
                .path(bookingId.toString())
                .path("confirm")
                .request(MediaType.APPLICATION_JSON)
                .put(Entity.json("{}"));
        boolean success = response.getStatus() == 200;
        response.close();
        return success;
    }

    // ---------------- Inner class for booking request ----------------
    public static class BookingRequest {
        public UUID customerId;
        public UUID carId;
        public LocalDate startDate;
        public LocalDate endDate;

        public BookingRequest(UUID customerId, UUID carId, LocalDate startDate, LocalDate endDate) {
            this.customerId = customerId;
            this.carId = carId;
            this.startDate = startDate;
            this.endDate = endDate;
        }
    }
}
