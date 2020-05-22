package kea.nordicmotorhome.controller;

import kea.nordicmotorhome.Model.Address;
import kea.nordicmotorhome.Model.Booking;
import kea.nordicmotorhome.Model.Card;
import kea.nordicmotorhome.Model.Customer;
import kea.nordicmotorhome.Service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class BookingController {
    @Autowired
    CarService carService;


    @GetMapping("/bookingDetails")
    String bookingDetails(Model model){
        model.addAttribute("booking", new Booking());
        model.addAttribute("customer", new Customer());
        model.addAttribute("address", new Address());
        model.addAttribute("card", new Card());
        return "bookingDetails.html";
    }

    @PostMapping("/saveBooking")
    String createNewBooking(@ModelAttribute Booking booking){
        System.out.println(booking.getStart_date());

        return null;
    }

}
