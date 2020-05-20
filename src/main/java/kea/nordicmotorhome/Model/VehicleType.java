package kea.nordicmotorhome.Model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class VehicleType {
    @Id
    private int vehicle_typr_id;
    private String vehicle_type_name;
    private int cost_per_day;
    private int vehicle_capacity;
    private String fuel_type;
    private int storage_size;
    private boolean kitchen;
    private boolean shower;
    private boolean wifi;
    private boolean tv;

    public int getVehicle_typr_id() {
        return vehicle_typr_id;
    }

    public void setVehicle_typr_id(int vehicle_typr_id) {
        this.vehicle_typr_id = vehicle_typr_id;
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
}
