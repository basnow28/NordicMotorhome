package kea.nordicmotorhome.controller;

import kea.nordicmotorhome.Model.*;
import kea.nordicmotorhome.Service.CustomerService;
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
    @Autowired
    CustomerService customerService;

    @GetMapping("/bookingDetails/{id}")
    String bookingDetails(@PathVariable("id") int id,  Model model) {
        BookingForm bookingForm = new BookingForm();
        bookingForm.setBooking(bookingService.getBooking(id));
        bookingForm.setCustomer(customerService.getCustomer(bookingForm.getBooking().getCustomer_id()));
        bookingForm.setVehicle(vehicleService.getVehicle(bookingForm.getBooking().getVehicle_id()));
        if (bookingForm.getBooking().getBooking_status().equalsIgnoreCase("cancelled")){
            System.out.println("I am here");
            bookingForm.getBooking().setInitial_cost(bookingService.calculateCancellationRate(bookingForm.getBooking().getStart_date(), bookingForm.getBooking().getInitial_cost()));
        }else {
            bookingForm.getBooking().setExtra_kilometers_fee(
                bookingService.calculateExtraKilometersPrice(
                        bookingForm.getBooking().getStart_date(),
                        bookingForm.getBooking().getEnd_date(),
                        bookingForm.getBooking().getDistance_driven()));
        }
        model.addAttribute("bookingForm", bookingForm);
        model.addAttribute("title", "Booking " + bookingForm.getBooking().getBooking_id());

        return "bookingDetails.html";
    }

    @PostMapping("/saveBooking")
    String saveBooking(@ModelAttribute("bookingForm") BookingForm bookingForm){
        bookingForm.getBooking().setExtras_cost(bookingService.setExtrasPrice(bookingForm.getBooking()));
        bookingForm.getBooking().setExtra_kilometers_fee(
                bookingService.calculateExtraKilometersPrice(
                        bookingForm.getBooking().getStart_date(),
                        bookingForm.getBooking().getEnd_date(),
                        bookingForm.getBooking().getDistance_driven()));

        bookingService.updateBooking(bookingForm.getBooking(), bookingForm.getCustomer());

        return "redirect:/bookingDetails/"+bookingForm.getBooking().getBooking_id();
    }

    @PostMapping("/newBooking")
    String createNewBooking(@ModelAttribute("bookingForm") BookingForm bookingForm){

        bookingForm.getBooking().setVehicle_id(bookingForm.getVehicle().getVehicle_id());
        bookingForm.getBooking().setExtras_cost(bookingService.setExtrasPrice(bookingForm.getBooking()));

        int booking_id = bookingService.createBooking(bookingForm.getBooking(), bookingForm.getCustomer());
        bookingForm.getBooking().setExtra_kilometers_fee(
                bookingService.calculateExtraKilometersPrice(
                        bookingForm.getBooking().getStart_date(),
                        bookingForm.getBooking().getEnd_date(),
                        bookingForm.getBooking().getDistance_driven()));

        bookingForm.getBooking().setBooking_id(booking_id);

        return "redirect:/bookingDetails/"+bookingForm.getBooking().getBooking_id();
    }

    @GetMapping("/bookingDetails/{vehicle.vehicle_id}/{start_date}/{end_date}/{vehicle.vehicle_calculated_quote}")
    public String createNewBooking(@PathVariable("vehicle.vehicle_id") int vehicle_id,
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

}
