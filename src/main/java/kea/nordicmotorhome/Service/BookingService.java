package kea.nordicmotorhome.Service;


import kea.nordicmotorhome.Model.*;

import kea.nordicmotorhome.Repository.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.time.temporal.ChronoUnit;

@Service
public class BookingService {
    @Autowired
    BookingRepository bookingRepository;


    public int createBooking(Booking booking, Customer customer) {
        return bookingRepository.createBooking(booking, customer);
    }

    public List<Vehicle> findFreeVehicles(String startDate, String endDate, int vehicle_capacity){
            DateTimeFormatter pattern = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate start_date = LocalDate.parse(startDate, pattern);
            LocalDate end_date = LocalDate.parse(endDate, pattern);
            LocalDate today = LocalDate.now();
            if (start_date.isBefore(end_date) && (start_date.isEqual(today) || start_date.isAfter(today))) {
                return bookingRepository.findFreeVehicles(startDate, endDate, vehicle_capacity);
            }
        return new ArrayList<Vehicle>();
    }


    private double getInitialQuote(String start_date, String end_date, int vehiclePricePerDay) {
        ArrayList<Season> seasons = (ArrayList<Season>) bookingRepository.getSeasons();
        ///Getting a format to change string to LocalDate object
        DateTimeFormatter pattern = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        double quote = 0.0;

        LocalDate startDate = LocalDate.parse(start_date, pattern);
        LocalDate endDate = LocalDate.parse(end_date, pattern);

        ///Calculating a season rate for each day of the upcoming booking
        for (LocalDate day = startDate; day.isBefore(endDate); day = day.plusDays(1L)) {
            double result = getPricePerDayDependingOnASeason(day, seasons, vehiclePricePerDay);
            quote += result;
        }
        return Math.floor(quote);
    }

    public double getPricePerDayDependingOnASeason(LocalDate day, ArrayList<Season> seasons, int vehiclePricePerDay){
        for(Season season : seasons){
            if(season.getSeason_id() == 1){
                if(day.getMonthValue() == season.getSeason_start_month()){
                    return season.getSeason_rate() * vehiclePricePerDay;
                } else if (day.getMonthValue() <= season.getSeason_end_month()) {
                    return season.getSeason_rate() * vehiclePricePerDay;
                }
            } else {
                if (day.getMonthValue() >= season.getSeason_start_month() && day.getMonthValue() <= season.getSeason_end_month()) {
                    return season.getSeason_rate() * vehiclePricePerDay;
                }
            }
        }
        return 0.0;
    }

    public void setVehiclesQuotes(String start_date, String end_date, ArrayList<Vehicle> freeVehicles) {
        for (Vehicle vehicle : freeVehicles) {
            vehicle.setVehicle_calculated_quote(getInitialQuote(start_date, end_date, vehicle.getCost_per_day()));
        }
    }


    public List<BookingTable> getBookings(SearchForm searchForm) {
        return bookingRepository.getBookings(searchForm);
    }

    public List<Booking> getAllBookings() {
        return bookingRepository.getAllBookings();
    }

    public double setExtrasPrice(Booking booking){
        double extrasPrice = 0;

        if(booking.isHas_picnic()){
            extrasPrice += bookingRepository.getExtraPrice("picnic");
        }
        if(booking.isHas_bikerack()){
            extrasPrice += bookingRepository.getExtraPrice("bike_rack");
        }
        if(booking.isHas_dvd_player()){
            extrasPrice += bookingRepository.getExtraPrice("dvd_player");
        }
        if(booking.isHas_tent()){
            extrasPrice += bookingRepository.getExtraPrice("tent");
        }
        if(booking.isHas_linen()){
            extrasPrice += bookingRepository.getExtraPrice("bed_linen");
        }
        if(!booking.isFuel_check()){
            extrasPrice += bookingRepository.getExtraPrice("fuel");
        }
        if(booking.getDrop_off_kilometers() > 0){
            extrasPrice += booking.getDrop_off_kilometers() * bookingRepository.getExtraPrice("pick_up_kilometer");
        }

        extrasPrice += calculateExtraKilometersPrice(booking.getStart_date(), booking.getEnd_date(), booking.getDistance_driven());

        return extrasPrice;
    }

    public double calculateExtraKilometersPrice(String start_date, String end_date, int kilometers){
        DateTimeFormatter pattern = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate startDate = LocalDate.parse(start_date, pattern);
        LocalDate endDate = LocalDate.parse(end_date, pattern);
        int days = (int) ChronoUnit.DAYS.between(startDate, endDate);

        double extraKilometersPrice = (kilometers - days*400)*bookingRepository.getExtraPrice("extra_kilometer");

        return extraKilometersPrice < 0? 0.0 : extraKilometersPrice;
    }

    public double calculateCancellationFee(String start_date, Double initial_cost) {
        DateTimeFormatter pattern = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate startDate = LocalDate.parse(start_date, pattern);
        LocalDate cancellation_date = LocalDate.now();
        int days = (int) ChronoUnit.DAYS.between(cancellation_date, startDate);
        Cancellation cancellation = getCancellation(days);

        double cancellationFee = cancellation.getCancellation_rate() * initial_cost;
        if (cancellationFee < cancellation.getMinimum_fee()) {
            return cancellation.getMinimum_fee();
        }else
            return cancellationFee ;
    }
    private Cancellation getCancellation(int days_out){
        return bookingRepository.getCancellation(days_out);
    }

    public Booking getBooking(int id) {
        return bookingRepository.getBooking(id);
    }

    public void updateBooking(Booking booking, Customer customer) {
        bookingRepository.updateBooking(booking, customer);
    }

    public void updateBookingPayment(int booking_id, double payment_amount) {
        bookingRepository.updateBookingPayment(booking_id, payment_amount);
    }
}
