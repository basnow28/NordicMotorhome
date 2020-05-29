package kea.nordicmotorhome.controller;

import kea.nordicmotorhome.Model.SearchForm;
import kea.nordicmotorhome.Model.Vehicle;
import kea.nordicmotorhome.NordicmotorhomeApplication;
import kea.nordicmotorhome.Service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.websocket.server.PathParam;
import java.util.ArrayList;

@Controller
public class VehicleController {
    @Autowired
    VehicleService vehicleService;

    @GetMapping("/vehicleDetails/{vehicle_id}")
    public String vehicleDetails(@PathVariable("vehicle_id") int vehicle_id, Model model){
        if(!NordicmotorhomeApplication.isAuthorized()){
            return "redirect:/";
        }
        Vehicle vehicle = vehicleService.getVehicle(vehicle_id);
        String title = "Vehicle " + vehicle_id;

        model.addAttribute("vehicle", vehicle);
        model.addAttribute("title", title);
        return "vehicleDetails";
    }

    @PostMapping("/saveVehicle")
    public String saveVehicle(@ModelAttribute Vehicle vehicle){
        vehicleService.updateVehicle(vehicle);

        return "redirect:/vehicleDetails/"+vehicle.getVehicle_id();
    }

    @GetMapping("/findVehicles")
    public String findVehicles(@ModelAttribute SearchForm searchForm, Model model){
        if(!NordicmotorhomeApplication.isAuthorized()){
            return "redirect:/";
        }
        ArrayList<Vehicle> vehicles = (ArrayList<Vehicle>) vehicleService.getVehicles(searchForm);
        
        model.addAttribute("searchForm", searchForm);
        model.addAttribute("vehicles", vehicles);
        return "vehicles";
    }

}
