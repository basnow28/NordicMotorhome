package kea.nordicmotorhome.controller;

import kea.nordicmotorhome.Model.Booking;
import kea.nordicmotorhome.Model.BookingForm;
import kea.nordicmotorhome.Model.Customer;
import kea.nordicmotorhome.Model.Vehicle;
import kea.nordicmotorhome.Service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import kea.nordicmotorhome.Service.BookingService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;
import java.util.List;

import static java.lang.Integer.parseInt;

@Controller
public class BookingController {
    @Autowired
    VehicleService vehicleService;
  
    @Autowired
    BookingService bookingService;

    @GetMapping("/bookingDetails")
    String bookingDetails(Model model){
        model.addAttribute("booking", new Booking());
        model.addAttribute("customer", new Customer());
        return "bookingDetails.html";
    }

    @PostMapping("/saveBooking")
    String createNewBooking(Model model, @ModelAttribute("bookingForm") BookingForm bookingForm){
        System.out.println(bookingForm.toString());

        model.addAttribute(bookingForm);
        model.addAttribute("title", "Booking");
        return "bookingDetails.html";
    }

    @PostMapping("/newBooking")
    String newBooking(Model model, @ModelAttribute("bookingForm") BookingForm bookingForm){
        System.out.println(bookingForm.toString());

        model.addAttribute(bookingForm);
        model.addAttribute("title", "Booking");
        return "bookingDetails.html";
    }

    @GetMapping("/bookingDetails/{vehicle.vehicle_id}/{start_date}/{end_date}")
    public String createNewBooking(@PathVariable("vehicle.vehicle_id") String vehicle_id,
                                   @PathVariable("start_date") String start_date,
                                   @PathVariable("end_date") String end_date,
                                   Model model){

        BookingForm bookingForm = new BookingForm();
        bookingForm.setBooking(new Booking());
        bookingForm.setCustomer(new Customer());
        bookingForm.getBooking().setStart_date(start_date);
        bookingForm.getBooking().setEnd_date(end_date);
        bookingForm.setVehicle(vehicleService.getVehicle(vehicle_id));


        model.addAttribute("bookingForm", bookingForm);
        model.addAttribute("title", "New Booking");
        return "bookingDetails.html";
    }

    @PostMapping("/findFreeVehicles")
    public String findFreeVehicles(@RequestParam String start_date, @RequestParam String end_date, @RequestParam(name="vehicle_capacity") Integer vehicle_capacity, Model model){
        System.out.println(start_date+ end_date+ vehicle_capacity);
        //List<Vehicle> freeVehicles = bookingService.findFreeVehicles(start_date, end_date, vehicle_capacity);
        model.addAttribute("freeVehicles", vehicleService.getAllVehicles());

        //int season = bookingService.findSeasonRate(startDate, endDate);
        //model.addAttribute("season_rate", season);
        //int numberOfDays = parseInt(endDate.substring(0,2))- parseInt(startDate.substring(0,2));
        //model.addAttribute("numberOfDays", numberOfDays);
        //System.out.println(startDate);
        model.addAttribute("start_date", start_date);
        model.addAttribute("end_date", end_date);
        model.addAttribute("vehicle_capacity", vehicle_capacity);
        return "createNewBooking";
    }

}
