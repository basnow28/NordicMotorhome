package kea.nordicmotorhome.service;


import kea.nordicmotorhome.model.*;

import kea.nordicmotorhome.repository.BookingRepository;
import kea.nordicmotorhome.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.time.temporal.ChronoUnit;

@Service
public class BookingService {
    @Autowired
    BookingRepository bookingRepository;
    @Autowired
    CustomerRepository customerRepository;

//CREATE BOOKING USE CASE
///////////////////********* BARBARA ************///////////////////
    //method for creating booking
    public int createBooking(Booking booking, Customer customer) {

        boolean isExistingCustomer = customerRepository.doesExist(customer);
        int address_id;
        int customer_id;
        if(!isExistingCustomer) {
            address_id = customerRepository.createAddress(customer);
            customer.setAddress_id(address_id);
            customer_id = customerRepository.createCustomer(customer);
        }else{
            if(customer.getCustomer_id() != 0){
                customer_id = customer.getCustomer_id();
            }else{
                customer_id = customerRepository.getCustomerId(customer.getEmail());
            }
        }
        return bookingRepository.createBooking(booking, customer_id);
    }

 //CHECK AVAILABLE BOOKINGS USE CASE
///////////////////********* DAGMARA ************///////////////////
    //Method for returning from repository list of free vehicles in selected dates, additionally in method body there is a check if end
    // date is higher than start date and if the start dates is higher or equals to today's date
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
    ///////////////////********* BARBARA ************///////////////////
    //Method for calculating initial price for renting vehicle in given date range
    public double getInitialQuote(String start_date, String end_date, int vehiclePricePerDay) {
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
    ///////////////////********* BARBARA ************///////////////////
    //Method for calculating price for a specific day depending on date, vehicle and season
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
    ///////////////////********* BARBARA ************///////////////////
    //Method for setting quota for every vehicle from available vehicles to book
    public void setVehiclesQuotes(String start_date, String end_date, ArrayList<Vehicle> freeVehicles) {
        for (Vehicle vehicle : freeVehicles) {
            vehicle.setVehicle_calculated_quote(getInitialQuote(start_date, end_date, vehicle.getCost_per_day()));
        }
    }

//FIND BOOKINGS USE CASE
///////////////////********* DAGMARA ************///////////////////
    //Method which based on searching criteria in search form returns from repository list of booking table objects
    // (combined information from bookings, customers, vehicles tables from DB)
    public List<BookingTable> getBookings(SearchForm searchForm) {
        return bookingRepository.getBookings(searchForm);
    }


//UPDATE BOOKING USE CASE
///////////////////********* BARBARA ************///////////////////
    //Method for adding to the initial price, price of extras (like bike rack)
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
    ///////////////////********* BARBARA ************///////////////////
    //Method for calculating price after vehicle is returned so the fee for additional kilometers can be added to the bill
    public double calculateExtraKilometersPrice(String start_date, String end_date, int kilometers){
        DateTimeFormatter pattern = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate startDate = LocalDate.parse(start_date, pattern);
        LocalDate endDate = LocalDate.parse(end_date, pattern);
        int days = (int) ChronoUnit.DAYS.between(startDate, endDate);

        double extraKilometersPrice = (kilometers - days*400)*bookingRepository.getExtraPrice("extra_kilometer");

        return extraKilometersPrice < 0? 0.0 : extraKilometersPrice;
    }
    ///////////////////********* DAGMARA ************///////////////////
    //Method for calculating fee for cancellation
    public double calculateCancellationFee(String start_date, double initial_cost) {
        DateTimeFormatter pattern = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate startDate = LocalDate.parse(start_date, pattern);
        LocalDate cancellation_date = LocalDate.now();
        int days = (int) ChronoUnit.DAYS.between(cancellation_date, startDate);
        if(days < 0) {
            days = 0;
        }
        Cancellation cancellation = getCancellation(days);

        double cancellationFee = cancellation.getCancellation_rate() * initial_cost;
        if (cancellationFee < cancellation.getMinimum_fee()) {
            return cancellation.getMinimum_fee();
        }else
            return Math.floor(cancellationFee);
    }
    ///////////////////********* DAGMARA ************///////////////////
    //Method for returning from repository right cancellation rate based on the days between cancellation day and start day og the booking
    private Cancellation getCancellation(int days_out){
        return bookingRepository.getCancellation(days_out);
    }
    ///////////////////********* DAGMARA ************///////////////////
    //Method for calling repositories to update changed fields
    public void updateBooking(Booking booking, Customer customer) {
        customerRepository.updateAddress(customer);
        customerRepository.updateCustomer(customer);
        bookingRepository.updateBooking(booking);
    }
    ///////////////////********* BARBARA ************///////////////////
    //Method for calling repository to update final payment amount
    public void updateBookingPayment(int booking_id, double payment_amount) {
        bookingRepository.updateBookingPayment(booking_id, payment_amount);
    }
    ///////////////////********* BARBARA ************///////////////////
    //Method which returns booking from repository based on its ID
    public Booking getBooking(int id) {
        return bookingRepository.getBooking(id);
    }

}
