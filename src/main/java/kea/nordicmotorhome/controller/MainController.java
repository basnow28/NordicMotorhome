package kea.nordicmotorhome.controller;



import kea.nordicmotorhome.Model.Employee;
import kea.nordicmotorhome.Model.SearchAvailabilityForm;
import kea.nordicmotorhome.Model.SearchForm;
import kea.nordicmotorhome.Model.Vehicle;
import kea.nordicmotorhome.NordicmotorhomeApplication;
import kea.nordicmotorhome.Service.EmployeeService;

import com.mysql.cj.x.protobuf.MysqlxCrud;
import kea.nordicmotorhome.Model.Booking;
import kea.nordicmotorhome.Model.FindBookingForm;
import kea.nordicmotorhome.Service.BookingService;

import kea.nordicmotorhome.Service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
public class MainController {
    @Autowired
    VehicleService carservice;
    @Autowired
    EmployeeService employeeService;
    @Autowired
    BookingService bookingservice;


    @GetMapping("/")
    public String login(Model model){
        model.addAttribute("employee", NordicmotorhomeApplication.getEmployee());
        model.addAttribute("incorrect", "");
        return "login.html";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute Employee employee, Model model){
        NordicmotorhomeApplication.setEmployee(employeeService.auth(employee));
        if(NordicmotorhomeApplication.getEmployee() != null){
            return "redirect:/dashboard";
        }
        model.addAttribute("incorrect", "Incorrect login id or password");
        return "login.html";
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model){
        if(!NordicmotorhomeApplication.isAuthorized()){
            return "redirect:/";
        }
        String welcome = "Welcome "+NordicmotorhomeApplication.getEmployee().getEmployee_first_name()+"! :)";
        model.addAttribute("welcome", welcome);
        return "index.html";
    }

    @GetMapping("/createNewBooking")
    public String createNewBooking(Model model){
        if(!NordicmotorhomeApplication.isAuthorized()){
            return "redirect:/";
        }
        model.addAttribute("availabilityForm", new SearchAvailabilityForm());
        return "createNewBooking.html";
    }

    @GetMapping("/bookings")
    public String bookings(Model model){
        if(!NordicmotorhomeApplication.isAuthorized()){
            return "redirect:/";
        }
        model.addAttribute("FindBookingForm", new FindBookingForm());
        List<Booking> allbookings = bookingservice.getAllBookings();
        model.addAttribute("getAllBookings", allbookings);
        return "bookings.html";
    }

    @GetMapping("/vehicles")
    public String vehicles(Model model){
        if(!NordicmotorhomeApplication.isAuthorized()){
            return "redirect:/";
        }
        List<Vehicle> vehicles = carservice.getAllVehicles();

        model.addAttribute("searchForm", new SearchForm());
        model.addAttribute("vehicles", vehicles);
        return "vehicles.html";
    }

    @GetMapping("/logout")
    public String logout(){
        NordicmotorhomeApplication.getEmployee().setAllAttributesToEmpty();
        return "redirect:/";
    }

    @GetMapping("/noAuth")
    public String noAuth(){
        return "noAuth.html";
    }

}
