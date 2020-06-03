package kea.nordicmotorhome.controller;




import kea.nordicmotorhome.Model.*;
import kea.nordicmotorhome.Model.Employee;
import kea.nordicmotorhome.Model.SearchForm;
import kea.nordicmotorhome.Model.Vehicle;
import kea.nordicmotorhome.NordicmotorhomeApplication;
import kea.nordicmotorhome.Service.CustomerService;
import kea.nordicmotorhome.Service.EmployeeService;

import kea.nordicmotorhome.Model.Booking;
import kea.nordicmotorhome.Service.BookingService;

import kea.nordicmotorhome.Service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class MainController {
    @Autowired
    VehicleService carservice;
    @Autowired
    EmployeeService employeeService;
    @Autowired
    BookingService bookingservice;
    @Autowired
    CustomerService customerService;



 //CREATE BOOKING USE CASE//

    //Method for displaying search available vehicles pages
    @GetMapping("/createNewBooking")
    public String createNewBooking(Model model){
        //check of login credentials
        if(!NordicmotorhomeApplication.isAuthorized()){
            return "redirect:/";
        }
        else if(!NordicmotorhomeApplication.getEmployee().getEmployee_type().toUpperCase().equals("SALESASSISTANT")){
            return "noAuth.html";
        }
        model.addAttribute("searchForm", new SearchForm());

        return "createNewBooking.html";
    }

 //UPDATE BOOKING USE CASE//

    //method for displaying existing bookings
    @GetMapping("/bookings")
    public String bookings(Model model){
        //authorisation check
        if(!NordicmotorhomeApplication.isAuthorized()){
            return "redirect:/";
        }
        else if(NordicmotorhomeApplication.getEmployee().getEmployee_type().toUpperCase().equals("MECHANIC") ||
                NordicmotorhomeApplication.getEmployee().getEmployee_type().toUpperCase().equals("CLEANER")){
            return "noAuth.html";
        }
        SearchForm searchForm = new SearchForm();
        searchForm.setAttribute("first_name");
        searchForm.setValue("");
        List<BookingTable> bookingTable = bookingservice.getBookings(searchForm);
        model.addAttribute("bookingTable", bookingTable);
        model.addAttribute("searchForm", searchForm);
        return "bookings.html";
    }

//FIND VEHICLE USE CASE//

    //method for displaying vehicle search page
    @GetMapping("/vehicles")
    public String vehicles(Model model){

        //authentication check
        if(!NordicmotorhomeApplication.isAuthorized()){
            return "redirect:/";
        }
        else if(NordicmotorhomeApplication.getEmployee().getEmployee_type().toUpperCase().equals("BOOKKEEPER")){
            return "noAuth.html";
        }
        //create list of vehicles and add it to the model
        List<Vehicle> vehicles = carservice.getAllVehicles();
        model.addAttribute("vehicles", vehicles);

        model.addAttribute("searchForm", new SearchForm());
        return "vehicles.html";
    }

//FIND CUSTOMER USE CASE//

    //method for displaying search customer page
    @GetMapping("/customers")
    public String customers(Model model){
        if(!NordicmotorhomeApplication.isAuthorized()){
            return "redirect:/";
        }
        else if(NordicmotorhomeApplication.getEmployee().getEmployee_type().toUpperCase().equals("MECHANIC") ||
                NordicmotorhomeApplication.getEmployee().getEmployee_type().toUpperCase().equals("CLEANER")){
            return "noAuth.html";
        }
        //create list of customers and add it to the model
        List<Customer> customers = customerService.getAllCustomers();
        model.addAttribute("customersList", customers);

        model.addAttribute("searchForm", new SearchForm());
        return "/customers";
    }

// Login and log out //

    //Method for displaying login page
    @GetMapping("/")
    public String login(Model model){
        model.addAttribute("employee", NordicmotorhomeApplication.getEmployee());
        model.addAttribute("incorrect", "");
        return "login.html";
    }

    //Method for logging in right employee
    @PostMapping("/login")
    public String login(@ModelAttribute Employee employee, Model model){
        NordicmotorhomeApplication.setEmployee(employeeService.auth(employee));
        if(NordicmotorhomeApplication.getEmployee() != null){
            return "redirect:/dashboard";
        }
        model.addAttribute("incorrect", "Incorrect login id or password");
        return "login.html";
    }

    //Method for logging out an employee and redirecting user to log in page
    @GetMapping("/logout")
    public String logout(){
        NordicmotorhomeApplication.getEmployee().setAllAttributesToEmpty();
        return "redirect:/";
    }

    //Method for displaying page when unauthorised user tries to enter the system
    @GetMapping("/noAuth")
    public String noAuth(){
        return "noAuth.html";
    }

    //Method for displaying dashboard
    @GetMapping("/dashboard")
    public String dashboard(Model model){
        if(!NordicmotorhomeApplication.isAuthorized()){
            return "redirect:/";
        }
        String welcome = "Welcome "+NordicmotorhomeApplication.getEmployee().getFirst_name()+"! :)";
        model.addAttribute("welcome", welcome);
        return "index.html";
    }

}
