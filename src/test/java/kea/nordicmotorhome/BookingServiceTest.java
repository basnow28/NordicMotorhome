package kea.nordicmotorhome;

import kea.nordicmotorhome.Model.Booking;
import kea.nordicmotorhome.Service.BookingService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class BookingServiceTest {
    @Autowired
    BookingService bookingService;

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

    @Test
    void calculateCancellationFee(){


      //  double expected =
      ///  assertEquals(expected, );
    }

    @Test
    void getInitailQuote(){

       // double expected = ;
       // assertEquals(expected, );
    }
}
