package kea.nordicmotorhome.Service;


import kea.nordicmotorhome.Model.Booking;

import kea.nordicmotorhome.Model.Customer;

import kea.nordicmotorhome.Model.Vehicle;
import kea.nordicmotorhome.Repository.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
