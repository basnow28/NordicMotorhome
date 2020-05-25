package kea.nordicmotorhome.Service;


import kea.nordicmotorhome.Model.Booking;

import kea.nordicmotorhome.Model.Customer;

import kea.nordicmotorhome.Model.Season;
import kea.nordicmotorhome.Model.Vehicle;
import kea.nordicmotorhome.Repository.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class BookingService {
    @Autowired
    BookingRepository bookingRepository;


    public Booking createBooking(Booking booking, Customer customer){
        return bookingRepository.createBooking(booking, customer);
    }

    public List<Vehicle> findFreeVehicles(String startDate, String endDate, int vehicle_capacity){
        return bookingRepository.findFreeVehicles(startDate, endDate, vehicle_capacity);
    }
    public int findSeasonRate(String startDate, String endDate){
        return bookingRepository.findSeasonRate(startDate, endDate);
    }

    private double getInitialQuote(String start_date, String end_date, int vehiclePricePerDay) {
        ArrayList<Season> seasons = (ArrayList<Season>) bookingRepository.getSeasons();
        System.out.println("Got the season" + seasons.toString());
        ///Getting a format to change string to LocalDate object
        DateTimeFormatter pattern = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        double quote = 0.0;

        LocalDate startDate  = LocalDate.parse(start_date, pattern);
        LocalDate endDate = LocalDate.parse(end_date, pattern);
        System.out.println(startDate);
        System.out.println(endDate);

        ///Calculating a season rate for each day of the upcoming booking
        for(LocalDate day = startDate; day.isBefore(endDate); day = day.plusDays(1L)){
            //System.out.println(day);
            double result = getPricePerDayDependingOnASeason(day,seasons, vehiclePricePerDay);
            System.out.println(result);
            quote += result;
        }
        System.out.println(quote);
        return Math.floor(quote);
    }

    private double getPricePerDayDependingOnASeason(LocalDate day, ArrayList<Season> seasons, int vehiclePricePerDay){
        for(Season season : seasons){
            System.out.println(season.getSeason_start_month());
            System.out.println(season.getSeason_end_month());
            System.out.println("Vehicle price" + vehiclePricePerDay);
            System.out.println("Vehicle price" + season.getSeason_rate());
            if(season.getSeason_id() == 1){
                if(day.getMonthValue() == season.getSeason_start_month()){

                }else if (day.getMonthValue() <= season.getSeason_end_month()) {
                    return season.getSeason_rate() * vehiclePricePerDay;
                }
            }else {
                if (day.getMonthValue() >= season.getSeason_start_month() && day.getMonthValue() <= season.getSeason_end_month()) {
                    return season.getSeason_rate() * vehiclePricePerDay;
                }
            }
        }
        return 0.0;
    }

    public void setVehiclesQuotes(String start_date, String end_date, ArrayList<Vehicle> freeVehicles) {
        for(Vehicle vehicle : freeVehicles){
            System.out.println("SetVehiclesQuotes");
            vehicle.setVehicle_calculated_quote(getInitialQuote(start_date, end_date, vehicle.getCost_per_day()));
            System.out.println("Vehicle quote " + vehicle.getVehicle_calculated_quote());
        }
    }
}
