package ch.unil.softarch.luxurycarrental.ui;

import ch.unil.softarch.luxurycarrental.client.CarClient;
import ch.unil.softarch.luxurycarrental.client.BookingClient;
import ch.unil.softarch.luxurycarrental.client.CustomerClient;
import ch.unil.softarch.luxurycarrental.domain.entities.Booking;
import ch.unil.softarch.luxurycarrental.domain.entities.Car;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@Named("dashboardBean")
@SessionScoped
public class DashBoardBean implements Serializable {

    @Inject
    private CarClient carClient;

    @Inject
    private BookingClient bookingClient;

    @Inject
    private CustomerClient customerClient;

    private int totalVehicles;
    private int activeBookings;
    private int totalCustomers;
    private double monthlyRevenue;

    @PostConstruct
    public void init() {
        loadDashboardData();
    }

    public void loadDashboardData() {
        // Total vehicles
        List<Car> vehicles = carClient.getAllCars();
        totalVehicles = vehicles.size();

        // Total orders (all bookings)
        List<Booking> bookings = bookingClient.getAllBookings();
        activeBookings = bookings.size();

        // Total customers
        totalCustomers = customerClient.getAllCustomers().size();

        // Total revenue from all bookings
        monthlyRevenue = bookings.stream()
                .mapToDouble(Booking::getTotalCost)
                .sum();
    }

    // Getters

    public int getTotalVehicles() {
        return totalVehicles;
    }

    public int getActiveBookings() {
        return activeBookings;
    }

    public int getTotalCustomers() {
        return totalCustomers;
    }

    public double getMonthlyRevenue() {
        return monthlyRevenue;
    }
}
