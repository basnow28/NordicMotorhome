package kea.nordicmotorhome.Service;

import kea.nordicmotorhome.Model.Vehicle;
import kea.nordicmotorhome.Repository.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
