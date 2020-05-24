package kea.nordicmotorhome.controller;


import kea.nordicmotorhome.Model.Vehicle;
import kea.nordicmotorhome.Service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Date;
import java.util.List;

@Controller
public class MainController {
    @Autowired
    VehicleService carservice;

    @GetMapping("/")
    public String dashboard(){
        return "index.html";
    }

    @GetMapping("/createNewBooking")
    public String createNewBooking(Model model){
        List<Vehicle> vehicles = carservice.getAllVehicles();
        model.addAttribute("freeVehicles", vehicles);
        model.addAttribute("start_date", "05-06-2020");
        model.addAttribute("end_date", "12-06-2020");
        model.addAttribute("vehicle_capacity", 2);
        return "createNewBooking.html";
    }

    @GetMapping("/bookings")
    public String bookings(){
        return "bookings.html";
    }

    @GetMapping("/vehicles")
    public String vehicles(){
        return "vehicles.html";
    }

}
