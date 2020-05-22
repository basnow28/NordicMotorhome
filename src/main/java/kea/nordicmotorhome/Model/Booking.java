package kea.nordicmotorhome.Model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.*;

@Entity
@Component
public class Booking {

    @Id
    private int booking_id;
    private String start_date;
    private String end_date;
    private int distance_driven;
    private String booking_status;
    private int payment_amount;
    private boolean  fuel_check;
    private String booking_notes;
    private boolean has_picnic;
    private boolean has_bikerack;
    private boolean has_dvd_player;
    private boolean has_tent;
    private boolean has_linen;

    private int vehicle_id;
    private int employee_id;
    private int season_id;
    private int cancellation_id;
    private int customer_id;
    private int card_id;

    public int getBooking_id() {
        return booking_id;
    }

    public void setBooking_id(int booking_id) {
        this.booking_id = booking_id;
    }

    public String getStart_date() {
        return start_date;
    }

    public void setStart_date(String start_date) {
        this.start_date = start_date;
    }

    public String getEnd_date() {
        return end_date;
    }

    public void setEnd_date(String end_date) {
        this.end_date = end_date;
    }

    public int getDistance_driven() {
        return distance_driven;
    }

    public void setDistance_driven(int distance_driven) {
        this.distance_driven = distance_driven;
    }

    public String getBooking_status() {
        return booking_status;
    }

    public void setBooking_status(String booking_status) {
        this.booking_status = booking_status;
    }

    public int getPayment_amount() {
        return payment_amount;
    }

    public void setPayment_amount(int payment_amount) {
        this.payment_amount = payment_amount;
    }

    public boolean isFuel_check() {
        return fuel_check;
    }

    public void setFuel_check(boolean fuel_check) {
        this.fuel_check = fuel_check;
    }

    public String getBooking_notes() {
        return booking_notes;
    }

    public void setBooking_notes(String booking_notes) {
        this.booking_notes = booking_notes;
    }

    public boolean isHas_picnic() {
        return has_picnic;
    }

    public void setHas_picnic(boolean has_picnic) {
        this.has_picnic = has_picnic;
    }

    public boolean isHas_bikerack() {
        return has_bikerack;
    }

    public void setHas_bikerack(boolean has_bikerack) {
        this.has_bikerack = has_bikerack;
    }

    public boolean isHas_dvd_player() {
        return has_dvd_player;
    }

    public void setHas_dvd_player(boolean has_dvd_player) {
        this.has_dvd_player = has_dvd_player;
    }

    public boolean isHas_tent() {
        return has_tent;
    }

    public void setHas_tent(boolean has_tent) {
        this.has_tent = has_tent;
    }

    public boolean isHas_linen() {
        return has_linen;
    }

    public void setHas_linen(boolean has_linen) {
        this.has_linen = has_linen;
    }

    public int getEmployee_id() {
        return employee_id;
    }

    public void setEmployee_id(int employee_id) {
        this.employee_id = employee_id;
    }

    public int getSeason_id() {
        return season_id;
    }

    public void setSeason_id(int season_id) {
        this.season_id = season_id;
    }

    public int getCancellation_id() {
        return cancellation_id;
    }

    public void setCancellation_id(int cancellation_id) {
        this.cancellation_id = cancellation_id;
    }

    public int getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(int customer_id) {
        this.customer_id = customer_id;
    }

    public int getCard_id() {
        return card_id;
    }

    public void setCard_id(int card_id) {
        this.card_id = card_id;
    }

    public int getVehicle_id() {
        return vehicle_id;
    }

    public void setVehicle_id(int vehicle_id) {
        this.vehicle_id = vehicle_id;
    }
}
