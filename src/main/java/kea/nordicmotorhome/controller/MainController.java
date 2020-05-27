package kea.nordicmotorhome.controller;


import com.mysql.cj.x.protobuf.MysqlxCrud;
import kea.nordicmotorhome.Model.Booking;
import kea.nordicmotorhome.Model.FindBookingForm;
import kea.nordicmotorhome.Model.SearchAvailabilityForm;
import kea.nordicmotorhome.Model.SearchForm;
import kea.nordicmotorhome.Model.Vehicle;
import kea.nordicmotorhome.Service.BookingService;
import kea.nordicmotorhome.Service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
public class MainController {
    @Autowired
    VehicleService carservice;
    @Autowired
    BookingService bookingservice;

    @GetMapping("/")
    public String dashboard(){
        return "index.html";
    }

    @GetMapping("/createNewBooking")
    public String createNewBooking(Model model){
        model.addAttribute("availabilityForm", new SearchAvailabilityForm());
        return "createNewBooking.html";
    }

    @GetMapping("/bookings")
    public String bookings(Model model){
        model.addAttribute("FindBookingForm", new FindBookingForm());
        List<Booking> allbookings = bookingservice.getAllBookings();
        model.addAttribute("getAllBookings", allbookings);
        return "bookings.html";
    }

    @GetMapping("/vehicles")
    public String vehicles(Model model){
        List<Vehicle> vehicles = carservice.getAllVehicles();

        model.addAttribute("searchForm", new SearchForm());
        model.addAttribute("vehicles", vehicles);
        return "vehicles.html";
    }


}
