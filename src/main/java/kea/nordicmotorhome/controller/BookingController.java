package kea.nordicmotorhome.controller;

import kea.nordicmotorhome.Model.Vehicle;
import kea.nordicmotorhome.Service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

import static java.lang.Integer.parseInt;

@Controller
public class BookingController {
    @Autowired
    BookingService bookingService;

    @PostMapping("/findFreeVehicles")
    public String findFreeVehicles(@RequestParam String startDate, @RequestParam String endDate, @RequestParam int vehicle_capacity, Model model){
        List<Vehicle> freeVehicles = bookingService.findFreeVehicles(startDate, endDate, vehicle_capacity);
        model.addAttribute("freeVehicles", freeVehicles);
        int season = bookingService.findSeasonRate(startDate, endDate);
        model.addAttribute("season_rate", season);
        int numberOfDays = parseInt(endDate.substring(0,2))- parseInt(startDate.substring(0,2));
        model.addAttribute("numberOfDays", numberOfDays);
        return "createNewBooking";
    }


}
