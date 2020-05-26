package kea.nordicmotorhome.Model;

import org.springframework.stereotype.Component;

import javax.persistence.Id;

@Component
public class Cancellation {
    @Id
    private int cancellation_id;
    private int days_range_min;
    private int days_range_max;
    private double cancellation_rate;
    private int minimum_fee;

    public int getCancellation_id() {
        return cancellation_id;
    }

    public void setCancellation_id(int cancellation_id) {
        this.cancellation_id = cancellation_id;
    }

    public int getDays_range_min() {
        return days_range_min;
    }

    public void setDays_range_min(int days_range_min) {
        this.days_range_min = days_range_min;
    }

    public int getDays_range_max() {
        return days_range_max;
    }

    public void setDays_range_max(int days_range_max) {
        this.days_range_max = days_range_max;
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
    public String toString(){
        return "cancellation_id: "+cancellation_id + "cancellation_rate: "+ cancellation_rate + "days_range_min: " + days_range_min
                 + "days_range_max: " + days_range_max;
    }
}
