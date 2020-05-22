package kea.nordicmotorhome.Model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.Id;



@Component
public class Vehicle {
    @Autowired
    VehicleType vehicle_type;

    @Id
    private int vehicle_id;
    private String vehicle_brand;
    private String vehicle_model;
    private String licence_plate;
    private int vehicle_odometer;
    private String vehicle_status;
    private String additional_notes;
    private String mechanic_status;
    private String cleaning_status;

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

    public VehicleType getVehicle_type() {
        return vehicle_type;
    }

    public void setVehicle_type(VehicleType vehicle_type) {
        this.vehicle_type = vehicle_type;
    }
}
