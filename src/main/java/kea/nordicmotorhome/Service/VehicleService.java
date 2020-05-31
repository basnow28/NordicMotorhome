package kea.nordicmotorhome.Service;

import kea.nordicmotorhome.Model.SearchForm;
import kea.nordicmotorhome.Model.Vehicle;
import kea.nordicmotorhome.Repository.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class VehicleService {

    @Autowired
    VehicleRepository vehicleRepository;

//FIND VEHICLE USE CASE//

    //Method for returning from repository all vehicles
    public List<Vehicle> getAllVehicles(){
        return vehicleRepository.getAllVehicles();
    }

    //Method for returning from repository list of vehicles which fulfill searching criteria
    public List<Vehicle> getVehicles(SearchForm searchForm) {
        return vehicleRepository.getVehicles(searchForm);
    }

//UPDATE VEHICLE USE CASE//

    //Method for returning specified vehicle from repository based on given vehicle_id
    public Vehicle getVehicle(int vehicle_id) {
        return vehicleRepository.getVehicle(vehicle_id);
    }

    //Method for passing updated vehicle information to repository
    public void updateVehicle(Vehicle vehicle) {
        vehicleRepository.updateVehicle(vehicle);
    }

//UPDATE VEHICLE STATUS USE CASE//

    //Method for passing updated status of vehicle to repository
    public void updateVehicleStatus(Vehicle vehicle) {
        vehicleRepository.updateVehicleStatus(vehicle);
    }
}
