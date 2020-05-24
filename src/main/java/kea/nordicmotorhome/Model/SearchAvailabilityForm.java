package kea.nordicmotorhome.Model;


import org.springframework.stereotype.Component;

@Component
public class SearchAvailabilityForm {
    private String start_date;
    private String end_date;
    private int vehicle_capacity;

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

    public int getVehicle_capacity() {
        return vehicle_capacity;
    }

    public void setVehicle_capacity(int vehicle_capacity) {
        this.vehicle_capacity = vehicle_capacity;
    }

    @Override
    public String toString() {
        return "SearchAvailabilityForm{" +
                "start_date='" + start_date + '\'' +
                ", end_date='" + end_date + '\'' +
                "attribute" + vehicle_capacity +
                '}';
    }
}
