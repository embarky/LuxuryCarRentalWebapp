package ch.unil.softarch.luxurycarrental.ui;

import ch.unil.softarch.luxurycarrental.client.BookingClient;
import ch.unil.softarch.luxurycarrental.domain.entities.Booking;
import jakarta.annotation.PostConstruct;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.Map;

@Named("orderBean")
@ViewScoped
public class OrderBean implements Serializable {

    private List<Booking> orders;  // list of orders for the current user
    private final BookingClient bookingClient = new BookingClient();

    @Inject
    private CustomerSessionBean customerSessionBean; // logged-in customer session

    @Inject
    private FacesContext facesContext; // for reading request parameters

    @PostConstruct
    public void init() {
        // First, try to read customerId from request parameter
        Map<String, String> params = facesContext.getExternalContext().getRequestParameterMap();
        String customerIdParam = params.get("customerId");

        if (customerIdParam != null && !customerIdParam.isEmpty()) {
            try {
                UUID customerId = UUID.fromString(customerIdParam);
                loadOrders(customerId);
                return;
            } catch (IllegalArgumentException e) {
                // invalid UUID, fallback to session customer
            }
        }

        // fallback to currently logged-in customer
        if (customerSessionBean.getCustomer() != null) {
            loadOrders(customerSessionBean.getCustomer().getId());
        } else {
            orders = Collections.emptyList();
        }
    }

    /**
     * Load orders for a specific customer
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
     * Cancel a specific order
     */
    public void cancelOrder(UUID bookingId) {
        try {
            bookingClient.cancelBooking(bookingId);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // reload orders after action
            if (customerSessionBean.getCustomer() != null) {
                loadOrders(customerSessionBean.getCustomer().getId());
            }
        }
    }

    /**
     * Getter for JSF page
     */
    public List<Booking> getOrders() {
        return orders;
    }
}