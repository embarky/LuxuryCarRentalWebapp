package ch.unil.softarch.luxurycarrental.ui;

import ch.unil.softarch.luxurycarrental.domain.entities.Booking;

import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;
import java.io.Serializable;
import java.util.List;

@Named
@SessionScoped
public class BookingListBean implements Serializable {

    private List<Booking> allBookings; // 获取所有订单
    private Booking selectedBooking;

    public List<Booking> getAllBookings() {
        // TODO: 从数据库或服务获取所有订单
        return allBookings;
    }

    public Booking getSelectedBooking() {
        return selectedBooking;
    }

    public void viewBookingDetails(String bookingId) {
        // TODO: 根据bookingId查询详细信息
        selectedBooking = allBookings.stream()
                .filter(b -> b.getBookingId().equals(bookingId))
                .findFirst()
                .orElse(null);
        // 可跳转到详情页面
    }
}
