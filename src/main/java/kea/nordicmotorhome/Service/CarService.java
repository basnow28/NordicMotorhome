package kea.nordicmotorhome.Service;

import kea.nordicmotorhome.Model.Vehicle;
import kea.nordicmotorhome.Repository.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CarService {

    @Autowired
    CarRepository carRepository;

    public List<Vehicle> getAllVehicles(){
        return carRepository.getAllVehicles();
    }
}
