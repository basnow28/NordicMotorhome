package kea.nordicmotorhome.controller;

import kea.nordicmotorhome.Model.*;
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

import java.awt.print.Book;
import java.util.ArrayList;
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
    String saveBooking(Model model, @ModelAttribute("bookingForm") BookingForm bookingForm){
        System.out.println(bookingForm.toString());

        model.addAttribute(bookingForm);
        model.addAttribute("title", "Booking");
        return "bookingDetails.html";
    }

    @PostMapping("/newBooking")
    String createNewBooking(Model model, @ModelAttribute("bookingForm") BookingForm bookingForm){

        bookingForm.getBooking().setVehicle_id(bookingForm.getVehicle().getVehicle_id());
        int booking_id = bookingService.createBooking(bookingForm.getBooking(), bookingForm.getCustomer());

        bookingForm.getBooking().setBooking_id(booking_id);
        model.addAttribute(bookingForm);
        model.addAttribute("title", "Booking "+booking_id);
        return "bookingDetails.html";
    }

    @GetMapping("/bookingDetails/{vehicle.vehicle_id}/{start_date}/{end_date}/{vehicle.vehicle_calculated_quote}")
    public String createNewBooking(@PathVariable("vehicle.vehicle_id") String vehicle_id,
                                   @PathVariable("start_date") String start_date,
                                   @PathVariable("end_date") String end_date,
                                   @PathVariable("vehicle.vehicle_calculated_quote") double quote,
                                   Model model){

        BookingForm bookingForm = new BookingForm();
        bookingForm.setBooking(new Booking());
        bookingForm.setCustomer(new Customer());
        bookingForm.getBooking().setStart_date(start_date);
        bookingForm.getBooking().setEnd_date(end_date);
        bookingForm.getBooking().setInitial_cost(quote);
        bookingForm.setVehicle(vehicleService.getVehicle(vehicle_id));


        model.addAttribute("bookingForm", bookingForm);
        model.addAttribute("title", "New Booking");
        return "bookingDetails.html";
    }

    @PostMapping("/findFreeVehicles")
    public String findFreeVehicles(@ModelAttribute SearchAvailabilityForm searchAvailabilityForm, Model model){

        ArrayList<Vehicle> freeVehicles = (ArrayList<Vehicle>) bookingService.findFreeVehicles(searchAvailabilityForm.getStart_date(), searchAvailabilityForm.getEnd_date(), searchAvailabilityForm.getVehicle_capacity());
        bookingService.setVehiclesQuotes(searchAvailabilityForm.getStart_date(), searchAvailabilityForm.getEnd_date(), freeVehicles);

        model.addAttribute("freeVehicles", freeVehicles);
        model.addAttribute("availabilityForm", searchAvailabilityForm);
        return "createNewBooking";
    }

    @PostMapping("/findBooking")
    public String findBooking(@ModelAttribute FindBookingForm findBookingForm, Model model){
        ArrayList<Booking> bookinglist = (ArrayList<Booking>) bookingService.getBookings(findBookingForm.getStart_date(), findBookingForm.getEnd_date(), findBookingForm.getInputType(), findBookingForm.getInputText());

        model.addAttribute("findBooking", bookinglist);
        model.addAttribute("FindBookingForm", findBookingForm);
        return "findBooking";
    }

}
