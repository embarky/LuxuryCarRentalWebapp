package ch.unil.softarch.luxurycarrental.ui;

import ch.unil.softarch.luxurycarrental.client.BookingClient;
import ch.unil.softarch.luxurycarrental.domain.entities.Booking;
import jakarta.annotation.PostConstruct;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Named("bookingListBean")
@ViewScoped
public class BookingListBean implements Serializable {

    private List<Booking> bookings;
    private final BookingClient bookingClient = new BookingClient();

    @PostConstruct
    public void init() {
        loadBookings();
    }

    private void loadBookings() {
        try {
            bookings = bookingClient.getAllBookings();
        } catch (Exception e) {
            e.printStackTrace();
            bookings = Collections.emptyList();
        }
    }

    // ===== CONFIRM BOOKING =====
    public void confirmBooking(UUID bookingId) {
        boolean success = bookingClient.confirmBooking(bookingId);
        loadBookings();
    }

    // ===== COMPLETE BOOKING =====
    public void completeBooking(UUID bookingId) {
        boolean success = bookingClient.completeBooking(bookingId);
        loadBookings();
    }

    // ===== REJECT BOOKING =====
    public void rejectBooking(UUID bookingId, String reason) {
        bookingClient.rejectBooking(bookingId, reason);
        loadBookings();
    }

    // ===== DELETE BOOKING =====
    public void deleteBooking(UUID bookingId) {
        boolean success = bookingClient.removeBooking(bookingId);
        loadBookings();
    }

    public List<Booking> getBookings() {
        return bookings;
    }
}