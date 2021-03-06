package kea.nordicmotorhome.controller;

import kea.nordicmotorhome.model.SearchForm;
import kea.nordicmotorhome.model.Vehicle;
import kea.nordicmotorhome.NordicmotorhomeApplication;
import kea.nordicmotorhome.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;

@Controller
public class VehicleController {
    @Autowired
    VehicleService vehicleService;

//FIND VEHICLE USE CASE//
///////////////////********* BARBARA ************///////////////////
    //Method for modeling List of vehicles which fit searching criteria
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

//UPDATE VEHICLE USE CASE//
///////////////////********* BARBARA ************///////////////////
    //Method for displaying selected vehicle details page
    @GetMapping("/vehicleDetails/{vehicle_id}")
    public String vehicleDetails(@PathVariable("vehicle_id") int vehicle_id, Model model){
        if(!NordicmotorhomeApplication.isAuthorized()){
            return "redirect:/";
        }
        Vehicle vehicle = vehicleService.getVehicle(vehicle_id);
        String title = "Vehicle " + vehicle_id;

        model.addAttribute("vehicle", vehicle);
        model.addAttribute("title", title);
        if (NordicmotorhomeApplication.getEmployee().getEmployee_type().equals("SalesAssistant")){
            model.addAttribute("employee_type", "SALESASSISTANT");
        }else {
            model.addAttribute("employee_type", "MAINTENANCE");
        }
        return "vehicleDetails";
    }
    ///////////////////********* BARBARA ************///////////////////
    //Method for updating selected vehicle details
    @PostMapping("/saveVehicle")
    public String saveVehicle(@ModelAttribute Vehicle vehicle){
        vehicleService.updateVehicle(vehicle);

        return "redirect:/vehicleDetails/"+vehicle.getVehicle_id();
    }

//UPDATE VEHICLE STATUS USE CASE//
///////////////////********* BARBARA ************///////////////////
    //Method for updating new vehicle status
    @PostMapping("/saveVehicleStatus")
    public String saveVehicleStatus(@ModelAttribute Vehicle vehicle){
        vehicleService.updateVehicleStatus(vehicle);
        return "redirect:/vehicleDetails/"+vehicle.getVehicle_id();
    }

}
