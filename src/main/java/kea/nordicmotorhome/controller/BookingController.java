package kea.nordicmotorhome.controller;

import kea.nordicmotorhome.Model.Booking;
import kea.nordicmotorhome.Model.Customer;
import kea.nordicmotorhome.Model.Vehicle;
import kea.nordicmotorhome.Service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class BookingController {
    @Autowired
    VehicleService vehicleService;


    @GetMapping("/bookingDetails")
    String bookingDetails(Model model){
        model.addAttribute("booking", new Booking());
        model.addAttribute("customer", new Customer());
        return "bookingDetails.html";
    }

    @PostMapping("/saveBooking")
    String createNewBooking(@ModelAttribute Booking booking){

        return null;
    }

    @GetMapping("/bookingDetails/{vehicle.vehicle_id}/{start_date}/{end_date}")
    public String createNewBooking(@PathVariable("vehicle.vehicle_id") String vehicle_id,
                                   @PathVariable("start_date") String start_date,
                                   @PathVariable("end_date") String end_date,
                                   Model model){
        System.out.println(vehicle_id);
        Booking booking = new Booking();
        booking.setStart_date(start_date);
        booking.setEnd_date(end_date);

        Vehicle vehicle = vehicleService.getVehicle(vehicle_id);
        Customer customer = new Customer();

        model.addAttribute("booking", booking);
        model.addAttribute("vehicle", vehicle);
        model.addAttribute("customer", customer);

        return "bookingDetails.html";
    }

    @PostMapping("/saveBooking/{booking}/{customer}/{vehicle}")
    public String saveBooking(Booking booking, Customer customer, Vehicle vehicle){
        System.out.println(booking.toString());
        System.out.println(customer.toString());
        System.out.println(vehicle.toString());

        return null;
    }

}
