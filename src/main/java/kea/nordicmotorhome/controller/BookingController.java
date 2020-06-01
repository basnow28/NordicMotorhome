package kea.nordicmotorhome.controller;

import kea.nordicmotorhome.Model.*;
import kea.nordicmotorhome.NordicmotorhomeApplication;
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

import javax.print.attribute.IntegerSyntax;
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
    @Autowired
    CustomerService customerService;


//CREATE BOOKING USE CASE//

    //Method for passing new booking information to service
    @PostMapping("/newBooking")
    String createNewBooking(@ModelAttribute("bookingForm") BookingForm bookingForm){
        bookingForm.getBooking().setVehicle_id(bookingForm.getVehicle().getVehicle_id());
        bookingForm.getBooking().setExtras_cost(bookingService.setExtrasPrice(bookingForm.getBooking()));
        bookingForm.getBooking().setExtra_kilometers_fee(
                bookingService.calculateExtraKilometersPrice(
                        bookingForm.getBooking().getStart_date(),
                        bookingForm.getBooking().getEnd_date(),
                        bookingForm.getBooking().getDistance_driven()));

        int booking_id = bookingService.createBooking(bookingForm.getBooking(), bookingForm.getCustomer());
        bookingForm.getBooking().setBooking_id(booking_id);

        return "redirect:/bookingDetails/"+bookingForm.getBooking().getBooking_id();
    }

    //Method which allows to open booking form page for creating new booking with specified dates, capacity and initial cost
    @GetMapping("/bookingDetails/{vehicle.vehicle_id}/{start_date}/{end_date}/{vehicle.vehicle_calculated_quote}")
    public String createNewBooking(@PathVariable("vehicle.vehicle_id") int vehicle_id, @PathVariable("start_date") String start_date,
                                   @PathVariable("end_date") String end_date, @PathVariable("vehicle.vehicle_calculated_quote") double quote,
                                   Model model){
        if(!NordicmotorhomeApplication.isAuthorized()){
            return "redirect:/";
        }
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


//CHECK AVAILABLE VEHICLES USE CASE//

    //Method responsible for modeling search form in which are saved searching criteria
    //and adding to model list of vehicles that fit searching criteria

    @PostMapping("/findFreeVehicles")
    public String findFreeVehicles(@ModelAttribute SearchForm searchForm, Model model){
        ArrayList<Vehicle> freeVehicles = (ArrayList<Vehicle>) bookingService.findFreeVehicles(searchForm.getStart_date(),
                searchForm.getEnd_date(),
                Integer.parseInt(searchForm.getValue()));
        bookingService.setVehiclesQuotes(searchForm.getStart_date(), searchForm.getEnd_date(), freeVehicles);

        model.addAttribute("freeVehicles", freeVehicles);
        model.addAttribute("searchForm", searchForm);
        return "createNewBooking";
    }
    //Find existing customer //

    @GetMapping("/bookingDetails/{vehicle_id}/{start_date}/{end_date}/{quote}/{customer_id}")
    public String createNewBooking(@PathVariable("vehicle_id") int vehicle_id,
                                   @PathVariable("start_date") String start_date,
                                   @PathVariable("end_date") String end_date,
                                   @PathVariable("quote") double quote,
                                   @PathVariable("customer_id") int customer_id,
                                   Model model){
        if(!NordicmotorhomeApplication.isAuthorized()){
            return "redirect:/";
        }
        BookingForm bookingForm = new BookingForm();
        bookingForm.setBooking(new Booking());
        bookingForm.setCustomer(customerService.getCustomer(customer_id));
        bookingForm.getBooking().setStart_date(start_date);
        bookingForm.getBooking().setEnd_date(end_date);
        bookingForm.getBooking().setInitial_cost(quote);
        bookingForm.setVehicle(vehicleService.getVehicle(vehicle_id));

        model.addAttribute("bookingForm", bookingForm);
        model.addAttribute("title", "New Booking");
        return "bookingDetails.html";
    }
    @PostMapping("/findCustomerForBooking")
    public String findCustomer(@ModelAttribute BookingForExistingCustomer bookingForExistingCustomer, Model model){
        ArrayList<Customer> customersList = (ArrayList<Customer>) customerService.findAllMatchingCustomers(bookingForExistingCustomer.getSearchForm());
        model.addAttribute("customersList", customersList);
        model.addAttribute("bookExistingCustomer", bookingForExistingCustomer);
        return "bookForCustomer.html";
    }

    @GetMapping("/bookForCustomer/{vehicle_id}/{start_date}/{end_date}/{quote}")
    public String createBookingExistingCustomer(@PathVariable("vehicle_id") int vehicle_id,
                                                @PathVariable("start_date") String start_date,
                                                @PathVariable("end_date") String end_date,
                                                @PathVariable("quote") double quote,
                                                Model model){
        BookingForExistingCustomer bookingForExistingCustomer = new BookingForExistingCustomer();
        bookingForExistingCustomer.setSearchForm(new SearchForm());
        bookingForExistingCustomer.getSearchForm().setStart_date(start_date);
        bookingForExistingCustomer.getSearchForm().setEnd_date(end_date);
        bookingForExistingCustomer.setVehicle_id(vehicle_id);
        bookingForExistingCustomer.setQuote(quote);

        model.addAttribute("bookExistingCustomer", bookingForExistingCustomer);

        return "bookForCustomer.html";
    }

//FIND BOOKING USE CASE//

    //Method is responsible modeling search form and  list of booking table objects which contains information
    // from different tables from database, the list has to fulfill searching criteria from search form object
    @PostMapping("/findBooking")
    public String findBooking(@ModelAttribute SearchForm searchForm, Model model){
        if (searchForm.getStart_date().equals("")){
            searchForm.setStart_date("0000-00-00");
        }
        if (searchForm.getEnd_date().equals("")){
            searchForm.setEnd_date("0000-00-00");
        }
        ArrayList<BookingTable> bookinglist = (ArrayList<BookingTable>) bookingService.getBookings(searchForm);
        model.addAttribute("bookingTable", bookinglist);
        return "bookings";
    }

    //UPDATE BOOKING USE CASE//

    //Method for displaying specified booking details
    @GetMapping("/bookingDetails/{id}")
    String bookingDetails(@PathVariable("id") int id,  Model model) {
        if(!NordicmotorhomeApplication.isAuthorized()){
            return "redirect:/";
        }
        BookingForm bookingForm = new BookingForm();
        bookingForm.setBooking(bookingService.getBooking(id));
        bookingForm.setCustomer(customerService.getCustomer(bookingForm.getBooking().getCustomer_id()));
        bookingForm.setVehicle(vehicleService.getVehicle(bookingForm.getBooking().getVehicle_id()));

        bookingForm.getBooking().setExtra_kilometers_fee(bookingService.calculateExtraKilometersPrice(
                bookingForm.getBooking().getStart_date(),
                bookingForm.getBooking().getEnd_date(),
                bookingForm.getBooking().getDistance_driven()));

        model.addAttribute("bookingForm", bookingForm);
        model.addAttribute("title", "Booking " + bookingForm.getBooking().getBooking_id());
        model.addAttribute("employee_type", NordicmotorhomeApplication.getEmployee().getEmployee_type().toUpperCase());

        return "bookingDetails.html";
    }

    //Method for passing changed information in the booking form to the service
    @RequestMapping(value = "/saveBooking", params="save", method=RequestMethod.POST)
    String saveBooking(@ModelAttribute("bookingForm") BookingForm bookingForm){
        if (bookingForm.getBooking().getBooking_status().equalsIgnoreCase("cancelled")){
            bookingForm.getBooking().setInitial_cost(bookingService.calculateCancellationFee(bookingForm.getBooking().getStart_date(), bookingForm.getBooking().getInitial_cost()));
        }else {
            bookingForm.getBooking().setExtras_cost(bookingService.setExtrasPrice(bookingForm.getBooking()));
            bookingForm.getBooking().setExtra_kilometers_fee(
                    bookingService.calculateExtraKilometersPrice(
                            bookingForm.getBooking().getStart_date(),
                            bookingForm.getBooking().getEnd_date(),
                            bookingForm.getBooking().getDistance_driven()));
        }
        bookingService.updateBooking(bookingForm.getBooking(), bookingForm.getCustomer());

        return "redirect:/bookingDetails/"+bookingForm.getBooking().getBooking_id();
    }

    //Method for modeling new booking form and sending information to update payment amount
    @RequestMapping(value = "/saveBooking", params="addNewPayment", method=RequestMethod.POST)
    String addNewPayment(@ModelAttribute("bookingForm") BookingForm bookingForm){
        double bookingPayment = bookingForm.getBooking().getPayment_amount() + bookingForm.getNewPaymentAmount();
        bookingForm.getBooking().setPayment_amount(bookingPayment);
        bookingService.updateBookingPayment(bookingForm.getBooking().getBooking_id(), bookingForm.getBooking().getPayment_amount());
        return "redirect:/bookingDetails/"+bookingForm.getBooking().getBooking_id();
    }

}
