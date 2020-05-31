package kea.nordicmotorhome.Model;

import org.springframework.stereotype.Component;

import javax.persistence.Id;


@Component
public class Season {
    @Id
    private int season_id;
    private String season_name;
    private int season_start_month;
    private int season_end_month;
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

    public int getSeason_start_month() {
        return season_start_month;
    }

    public void setSeason_start_month(int season_start_month) {
        this.season_start_month = season_start_month;
    }

    public int getSeason_end_month() {
        return season_end_month;
    }

    public void setSeason_end_month(int season_end_month) {
        this.season_end_month = season_end_month;
    }

    public double getSeason_rate() {
        return season_rate;
    }

    public void setSeason_rate(double season_rate) {
        this.season_rate = season_rate;
    }
}
