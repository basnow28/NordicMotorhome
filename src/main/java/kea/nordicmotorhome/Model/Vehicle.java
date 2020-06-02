package kea.nordicmotorhome.Model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.Id;



@Component
public class Vehicle {
    private int vehicle_id;
    private String vehicle_brand;
    private String vehicle_model;
    private String licence_plate;
    private int vehicle_odometer;
    private String vehicle_status;
    private String additional_notes;
    private String mechanic_status;
    private String cleaning_status;
    private String vehicle_type_name;
    private int cost_per_day;
    private int vehicle_capacity;
    private String fuel_type;
    private int storage_size;
    private boolean kitchen;
    private boolean shower;
    private boolean wifi;
    private boolean tv;
    private double vehicle_calculated_quote;

    @Override
    public String toString() {
        return "Vehicle{" +
                "vehicle_id=" + vehicle_id +
                ", vehicle_brand='" + vehicle_brand + '\'' +
                ", vehicle_model='" + vehicle_model + '\'' +
                ", licence_plate='" + licence_plate + '\'' +
                ", vehicle_odometer=" + vehicle_odometer +
                ", vehicle_status='" + vehicle_status + '\'' +
                ", additional_notes='" + additional_notes + '\'' +
                ", mechanic_status='" + mechanic_status + '\'' +
                ", cleaning_status='" + cleaning_status + '\'' +
                ", vehicle_type_name='" + vehicle_type_name + '\'' +
                ", cost_per_day=" + cost_per_day +
                ", vehicle_capacity=" + vehicle_capacity +
                ", fuel_type='" + fuel_type + '\'' +
                ", storage_size=" + storage_size +
                ", kitchen=" + kitchen +
                ", shower=" + shower +
                ", wifi=" + wifi +
                ", tv=" + tv +
                '}';
    }

    public int getVehicle_id() {
        return vehicle_id;
    }

    public void setVehicle_id(int vehicle_id) {
        this.vehicle_id = vehicle_id;
    }

    public String getVehicle_brand() {
        return vehicle_brand;
    }

    public void setVehicle_brand(String vehicle_brand) {
        this.vehicle_brand = vehicle_brand;
    }

    public String getVehicle_model() {
        return vehicle_model;
    }

    public void setVehicle_model(String vehicle_model) {
        this.vehicle_model = vehicle_model;
    }

    public String getLicence_plate() {
        return licence_plate;
    }

    public void setLicence_plate(String licence_plate) {
        this.licence_plate = licence_plate;
    }

    public int getVehicle_odometer() {
        return vehicle_odometer;
    }

    public void setVehicle_odometer(int vehicle_odometer) {
        this.vehicle_odometer = vehicle_odometer;
    }

    public String getVehicle_status() {
        return vehicle_status;
    }

    public void setVehicle_status(String vehicle_status) {
        this.vehicle_status = vehicle_status;
    }

    public String getAdditional_notes() {
        return additional_notes;
    }

    public void setAdditional_notes(String additional_notes) {
        this.additional_notes = additional_notes;
    }

    public String getMechanic_status() {
        return mechanic_status;
    }

    public void setMechanic_status(String mechanic_status) {
        this.mechanic_status = mechanic_status;
    }

    public String getCleaning_status() {
        return cleaning_status;
    }

    public void setCleaning_status(String cleaning_status) {
        this.cleaning_status = cleaning_status;
    }

    public String getVehicle_type_name() {
        return vehicle_type_name;
    }

    public void setVehicle_type_name(String vehicle_type_name) {
        this.vehicle_type_name = vehicle_type_name;
    }

    public int getCost_per_day() {
        return cost_per_day;
    }

    public void setCost_per_day(int cost_per_day) {
        this.cost_per_day = cost_per_day;
    }

    public int getVehicle_capacity() {
        return vehicle_capacity;
    }

    public void setVehicle_capacity(int vehicle_capacity) {
        this.vehicle_capacity = vehicle_capacity;
    }

    public String getFuel_type() {
        return fuel_type;
    }

    public void setFuel_type(String fuel_type) {
        this.fuel_type = fuel_type;
    }

    public int getStorage_size() {
        return storage_size;
    }

    public void setStorage_size(int storage_size) {
        this.storage_size = storage_size;
    }

    public boolean isKitchen() {
        return kitchen;
    }

    public void setKitchen(boolean kitchen) {
        this.kitchen = kitchen;
    }

    public boolean isShower() {
        return shower;
    }

    public void setShower(boolean shower) {
        this.shower = shower;
    }

    public boolean isWifi() {
        return wifi;
    }

    public void setWifi(boolean wifi) {
        this.wifi = wifi;
    }

    public boolean isTv() {
        return tv;
    }

    public void setTv(boolean tv) {
        this.tv = tv;
    }

    public double getVehicle_calculated_quote() {
        return vehicle_calculated_quote;
    }

    public void setVehicle_calculated_quote(double vehicle_calculated_quote) {
        this.vehicle_calculated_quote = vehicle_calculated_quote;
    }
}
