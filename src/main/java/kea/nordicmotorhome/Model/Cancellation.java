package kea.nordicmotorhome.Model;

import org.springframework.stereotype.Component;

import javax.persistence.Id;

@Component
public class Cancellation {
    @Id
    private int cancellation_id;
    private String days_range;
    private double cancellation_rate;
    private int minimum_fee;

    public int getCancellation_id() {
        return cancellation_id;
    }

    public void setCancellation_id(int cancellation_id) {
        this.cancellation_id = cancellation_id;
    }

    public String getDays_range() {
        return days_range;
    }

    public void setDays_range(String days_range) {
        this.days_range = days_range;
    }

    public double getCancellation_rate() {
        return cancellation_rate;
    }

    public void setCancellation_rate(double cancellation_rate) {
        this.cancellation_rate = cancellation_rate;
    }

    public int getMinimum_fee() {
        return minimum_fee;
    }

    public void setMinimum_fee(int minimum_fee) {
        this.minimum_fee = minimum_fee;
    }
}
