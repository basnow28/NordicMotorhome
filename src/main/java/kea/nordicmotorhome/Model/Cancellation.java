package kea.nordicmotorhome.Model;

import org.springframework.stereotype.Component;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Cancellation {
    @Id
    private int cancellation_id;
    private int days_out_min;
    private int days_out_max;
    private double cancellation_rate;
    private int minimum_fee;

    public int getCancellation_id() {
        return cancellation_id;
    }

    public void setCancellation_id(int cancellation_id) {
        this.cancellation_id = cancellation_id;
    }

    public int getDays_out_min() {
        return days_out_min;
    }

    public void setDays_out_min(int days_out_min) {
        this.days_out_min = days_out_min;
    }

    public int getDays_out_max() {
        return days_out_max;
    }

    public void setDays_out_max(int days_out_max) {
        this.days_out_max = days_out_max;
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
        return "cancellation_id: "+cancellation_id + "cancellation_rate: "+ cancellation_rate + "days_range_min: " + days_out_min
                 + "days_range_max: " + days_out_max;
    }
}
