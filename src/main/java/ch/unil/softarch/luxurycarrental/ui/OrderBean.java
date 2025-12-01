package ch.unil.softarch.luxurycarrental.ui;

import ch.unil.softarch.luxurycarrental.client.BookingClient;
import ch.unil.softarch.luxurycarrental.domain.entities.Booking;
import jakarta.annotation.PostConstruct;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Named("orderBean")
@ViewScoped
public class OrderBean implements Serializable {

    private List<Booking> orders = Collections.emptyList();  // current user's bookings

    @Inject
    private BookingClient bookingClient;  // injected client

    @Inject
    private CustomerSessionBean customerSessionBean; // logged-in customer session

    @PostConstruct
    public void init() {
        // Load orders for logged-in customer
        if (customerSessionBean.getCustomer() != null) {
            UUID customerId = customerSessionBean.getCustomer().getId();
            loadOrders(customerId);
        } else {
            orders = Collections.emptyList();
        }
    }

    /**
     * Load bookings for a specific customer
     */
    private void loadOrders(UUID customerId) {
        try {
            orders = bookingClient.getBookingsByCustomer(customerId);
        } catch (Exception e) {
            e.printStackTrace();
            orders = Collections.emptyList();
        }
    }

    /**
     * Cancel a specific booking
     */
    // ===== CANCEL BOOKING =====
    public void cancelBooking(UUID bookingId) {
        boolean success = bookingClient.cancelBooking(bookingId);
        loadBookings();
    }
    private void loadBookings() {
        try {
            orders = bookingClient.getAllBookings();
        } catch (Exception e) {
            e.printStackTrace();
            orders = Collections.emptyList();
        }
    }

    public List<Booking> getOrders() {
        return orders;
    }
}