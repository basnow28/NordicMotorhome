package kea.nordicmotorhome.controller;


import kea.nordicmotorhome.Model.Vehicle;
import kea.nordicmotorhome.Service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class MainController {
    @Autowired
    CarService carservice;

    @GetMapping("/")
    public String dashboard(){
        return "index.html";
    }

    @GetMapping("/createNewBooking")
    public String createNewBooking(Model model){
        List<Vehicle> vehicles = carservice.getAllVehicles();
        model.addAttribute("vehicles", vehicles);
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
