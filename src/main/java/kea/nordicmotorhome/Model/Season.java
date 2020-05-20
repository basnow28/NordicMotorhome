package kea.nordicmotorhome.Model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Season {
    @Id
    private int season_id;
    private String season_name;
    private String season_start;
    private String season_end;
    private double season_rate;

    public int getSeason_id() {
        return season_id;
    }

    public void setSeason_id(int season_id) {
        this.season_id = season_id;
    }

    public String getSeason_name() {
        return season_name;
    }

    public void setSeason_name(String season_name) {
        this.season_name = season_name;
    }

    public String getSeason_start() {
        return season_start;
    }

    public void setSeason_start(String season_start) {
        this.season_start = season_start;
    }

    public String getSeason_end() {
        return season_end;
    }

    public void setSeason_end(String season_end) {
        this.season_end = season_end;
    }

    public double getSeason_rate() {
        return season_rate;
    }

    public void setSeason_rate(double season_rate) {
        this.season_rate = season_rate;
    }
}
