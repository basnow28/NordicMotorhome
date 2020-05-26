package kea.nordicmotorhome.Model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BookingForm {
    @Autowired
    Booking booking;
    @Autowired
    Customer customer;
    @Autowired
    Vehicle vehicle;

    private boolean isExistingCustomer;
    private double newPaymentAmount;

    public Booking getBooking() {
        return booking;
    }

    public void setBooking(Booking booking) {
        this.booking = booking;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public boolean isExistingCustomer() {
        return isExistingCustomer;
    }

    public void setExistingCustomer(boolean existingCustomer) {
        isExistingCustomer = existingCustomer;
    }

    @Override
    public String toString() {
        return "BookingForm{" +
                "booking=" + booking +
                ", customer=" + customer +
                ", vehicle=" + vehicle +
                ", isExistingCustomer=" + isExistingCustomer +
                '}';
    }

    public double getNewPaymentAmount() {
        return newPaymentAmount;
    }

    public void setNewPaymentAmount(double newPaymentAmount) {
        this.newPaymentAmount = newPaymentAmount;
    }
}
