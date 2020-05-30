package kea.nordicmotorhome;

import kea.nordicmotorhome.Model.Booking;
import kea.nordicmotorhome.Model.Customer;
import kea.nordicmotorhome.Model.Season;
import kea.nordicmotorhome.Service.BookingService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class BookingServiceTest {
    @Autowired
    BookingService bookingService;

    //TEST FOR CREATE BOOKING USE CASE

    //method for testing if booking was added to the database
    @Test
    void createBookingTest(){
        Booking booking = new Booking();

        booking.setStart_date("2020-08-03");
        booking.setEnd_date("2020-08-10");
        booking.setDistance_driven(0);
        booking.setDrop_off_kilometers(0);
        booking.setInitial_cost(4792);
        booking.setExtras_cost(0);
        booking.setBooking_status("CREATED");
        booking.setPayment_amount(0);
        booking.setFuel_check(true);
        booking.setBooking_notes("smth");
        booking.setHas_picnic(false);
        booking.setHas_bikerack(false);
        booking.setHas_dvd_player(false);
        booking.setHas_tent(false);
        booking.setHas_linen(false);
        booking.setVehicle_id(2);
        booking.setEmployee_id(4); //for this test to work in createBooking method in repository employee_ID has to be set up in booking not called from nordicmotorhomeAPP

        booking.setCard_number("1234 3333 2222 1111");
        booking.setCard_expiry_date("12/21");
        booking.setCard_cvv(222);

        Customer customer = new Customer();
        customer.setFirst_name("Marcin");
        customer.setLast_name("Przygocki");
        customer.setDate_of_birth("1970-11-21");
        customer.setPhone_number("20202020");
        customer.setEmail("m@przyg.com");
        customer.setDriver_licence_number("GDA123");
        customer.setStreet_name("Husumvej");
        customer.setHouse_number("10");
        customer.setPostcode("2700");
        customer.setCity("Copenhagen");
        customer.setCountry("Denmark");
        assertEquals(7,bookingService.createBooking(booking, customer));
    }

    //TEST FOR CHECK AVAILABILITY USE CASE

    //Method for testing if list of available vehicles for booking contains option for overbooking vehicle in specified date
    @Test
    void findFreeVehiclesTest(){
        String startDate = "2020-06-19";
        String endDate = "2020-06-22";
        int capacity = 6;
        Booking booking = bookingService.getBooking(4);
        assertFalse(bookingService.findFreeVehicles(startDate, endDate, capacity).contains(booking));
    }

    //Method for testing if calculated price for a specific day depending on date, vehicle and season is correct
    @Test
    void getPricePerDayDependingOnASeasonTest(){
        DateTimeFormatter pattern = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate day = LocalDate.parse("2020-06-19", pattern);
        ArrayList<Season > seasons = new ArrayList<Season>();

        Season lowSeason = new Season();
        lowSeason.setSeason_id(1);
        lowSeason.setSeason_name("Low Season");
        lowSeason.setSeason_start_month(12);
        lowSeason.setSeason_end_month(2);
        lowSeason.setSeason_rate(1);
        seasons.add(lowSeason);

        Season midSeason = new Season();
        midSeason.setSeason_id(2);
        midSeason.setSeason_name("Mid Season");
        midSeason.setSeason_start_month(3);
        midSeason.setSeason_end_month(5);
        midSeason.setSeason_rate(1.3);
        seasons.add(midSeason);

        Season highSeason = new Season();
        highSeason.setSeason_id(3);
        highSeason.setSeason_name("High Season");
        highSeason.setSeason_start_month(6);
        highSeason.setSeason_end_month(9);
        highSeason.setSeason_rate(1.6);
        seasons.add(highSeason);

        Season midSeason2 = new Season();
        midSeason2.setSeason_id(4);
        midSeason2.setSeason_name("Mid Season");
        midSeason2.setSeason_start_month(10);
        midSeason2.setSeason_end_month(11);
        midSeason2.setSeason_rate(1.3);
        seasons.add(midSeason2);
        int vehiclePricePerDay = 99;
        assertEquals(158.4, bookingService.getPricePerDayDependingOnASeason(day, seasons, vehiclePricePerDay));
    }

    //Method which test if initial quota for selected vehicle in specified time range is calculated correctly
    @Test
    void getInitialQuoteTest(){
        String start_date = "2020-06-19";
        String end_date = "2020-06-22";
        int vehiclePricePerDay = 99;
        assertEquals(475, bookingService.getInitialQuote(start_date, end_date, vehiclePricePerDay));
    }

 //UPDATE USE CASE ITERATION

    //Test method for calculating additional fee for renting extras
    @Test
    void calculateExtras(){
        Booking booking = new Booking();
        booking.setStart_date("2020-05-10");
        booking.setEnd_date("2020-05-15");
        booking.setDistance_driven(2000);
        booking.setDrop_off_kilometers(100);
        booking.setHas_picnic(true);
        booking.setHas_bikerack(true);
        booking.setHas_dvd_player(true);
        booking.setHas_linen(true);
        booking.setHas_tent(true);

        double expected = 85.00;
        assertEquals(expected, bookingService.setExtrasPrice(booking));
    }

    //Method for calculating fee for cancelling a booking
    @Test
    void calculateCancellationFee(){


      //  double expected =
      ///  assertEquals(expected, );
    }

    // testing JUnit ????????????????????????????????????????????????????????????????????????????????
    @Test
    void getBookings(){
        assertEquals(4, bookingService.getAllBookings().size());

    }
}
