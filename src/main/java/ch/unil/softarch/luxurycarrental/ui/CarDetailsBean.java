package ch.unil.softarch.luxurycarrental.ui;

import ch.unil.softarch.luxurycarrental.client.CarClient;
import ch.unil.softarch.luxurycarrental.client.BookingClient;
import ch.unil.softarch.luxurycarrental.domain.entities.Car;
import ch.unil.softarch.luxurycarrental.ui.CustomerSessionBean;

import jakarta.annotation.PostConstruct;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Map;
import java.util.UUID;

@Named("carDetailsBean")
@ViewScoped
public class CarDetailsBean implements Serializable {

    @Inject
    private CarClient carClient;

    @Inject
    private BookingClient bookingClient;

    @Inject
    private CustomerSessionBean customerSessionBean;

    private Car car;
    private LocalDate startDate;
    private LocalDate endDate;

    @PostConstruct
    public void init() {
        Map<String, String> params = FacesContext.getCurrentInstance()
                .getExternalContext()
                .getRequestParameterMap();
        String idStr = params.get("carId");
        if (idStr != null) {
            try {
                car = carClient.getCar(UUID.fromString(idStr));
            } catch (Exception e) {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error",
                                "Car not found: " + e.getMessage()));
            }
        }
    }

    // Getter / Setter
    public Car getCar() { return car; }
    public LocalDate getStartDate() { return startDate; }
    public void setStartDate(LocalDate startDate) { this.startDate = startDate; }
    public LocalDate getEndDate() { return endDate; }
    public void setEndDate(LocalDate endDate) { this.endDate = endDate; }

    // Book the car
    public void bookCar() {
        FacesContext context = FacesContext.getCurrentInstance();

        if (!customerSessionBean.isLoggedIn() || customerSessionBean.getCustomer() == null) {
            context.addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN, "Error", "Please login first."));
            return;
        }

        if (startDate == null || endDate == null) {
            context.addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN, "Error", "Please select start and end dates."));
            return;
        }

        if (!endDate.isAfter(startDate)) {
            context.addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN, "Error", "End date must be after start date."));
            return;
        }

        try {
            UUID customerId = customerSessionBean.getCustomer().getId(); // 直接从 session bean 获取
            bookingClient.createBooking(customerId, car.getId(), startDate, endDate);

            context.addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Success",
                            "Booking created successfully for car "
                                    + car.getCarType().getBrand() + " " + car.getCarType().getModel()
                                    + " from " + startDate + " to " + endDate));

            startDate = null;
            endDate = null;

        } catch (Exception e) {
            context.addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error",
                            "Failed to create booking: " + e.getMessage()));
        }
    }
}