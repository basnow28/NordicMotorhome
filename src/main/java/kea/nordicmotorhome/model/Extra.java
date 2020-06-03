package kea.nordicmotorhome.model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Extra {
    @Id
    private int extra_id;
    private String extra_name;
    private double extra_price;

    public int getExtra_id() {
        return extra_id;
    }

    public void setExtra_id(int extra_id) {
        this.extra_id = extra_id;
    }

    public String getExtra_name() {
        return extra_name;
    }

    public void setExtra_name(String extra_name) {
        this.extra_name = extra_name;
    }

    public double getExtra_price() {
        return extra_price;
    }

    public void setExtra_price(double extra_price) {
        this.extra_price = extra_price;
    }
}
