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

    public List<Vehicle> getAllVehicles(){
        return vehicleRepository.getAllVehicles();
    }

    public Vehicle getVehicle(int vehicle_id) {
        return vehicleRepository.getVehicle(vehicle_id);
    }

    public void updateVehicle(Vehicle vehicle) {
        vehicleRepository.updateVehicle(vehicle);
    }

    public List<Vehicle> getVehicles(SearchForm searchForm) {
        return vehicleRepository.getVehicles(searchForm);
    }
}
