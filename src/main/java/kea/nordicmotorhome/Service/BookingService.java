package kea.nordicmotorhome.Service;


import kea.nordicmotorhome.Model.Booking;

import kea.nordicmotorhome.Model.Customer;

import kea.nordicmotorhome.Repository.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookingService {
    @Autowired
    BookingRepository bookingRepository;


    public Booking createBooking(Booking booking, Customer customer, Card card, Address address){
        return bookingRepository.createBooking(booking, customer, card, address);
    }

}
