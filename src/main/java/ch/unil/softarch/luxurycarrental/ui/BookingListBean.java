package ch.unil.softarch.luxurycarrental.ui;

import ch.unil.softarch.luxurycarrental.client.BookingClient;
import ch.unil.softarch.luxurycarrental.domain.entities.Booking;
import jakarta.annotation.PostConstruct;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

@Named("bookingListBean")
@ViewScoped
public class BookingListBean implements Serializable {

    private List<Booking> bookings;

    private final BookingClient bookingClient = new BookingClient();

    @PostConstruct
    public void init() {
        try {
            bookings = bookingClient.getAllBookings();
            System.out.println("Fetched bookings: " + bookings.size());
        } catch (Exception e) {
            e.printStackTrace();
            bookings = Collections.emptyList();
        }
    }

    public List<Booking> getBookings() {
        return bookings;
    }
}