package kea.nordicmotorhome.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {
    @GetMapping("/")
    public String dashboard(){
        return "index.html";
    }

    @GetMapping("/createNewBooking")
    public String createNewBooking(){
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
