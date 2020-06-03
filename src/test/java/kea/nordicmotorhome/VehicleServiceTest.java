package kea.nordicmotorhome;

import kea.nordicmotorhome.model.SearchForm;
import kea.nordicmotorhome.model.Vehicle;
import kea.nordicmotorhome.service.VehicleService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class VehicleServiceTest {
    @Autowired
    VehicleService vehicleService;

//TESTS FOR FIND VEHICLE USE CASE//

    @Test
    //Method tests if list contains every existing vehicle in database
    // at this point we have 32 vehicles in database
    void getAllVehiclesTest(){
        assertEquals(32,vehicleService.getAllVehicles().size());
    }

    @Test
    //Method tests if list returned from repository contains all vehicles which fit searching criteria
    void getVehiclesTest(){
        SearchForm searchForm = new SearchForm();
        searchForm.setAttribute("vehicle_capacity");
        searchForm.setValue("6");
        assertEquals(6, vehicleService.getVehicles(searchForm).size());
    }

//TESTS FOR UPDATE VEHICLE USE CASE//

    @Test
    //Method tests if the right vehicle is returned based on given vehicle_id
    void getVehicleTest(){
        int id = 1;
        assertEquals("L-955q", vehicleService.getVehicle(id).getLicence_plate());
    }

    @Test
    //Method tests if information for specified vehicle are updated in database
    void updateVehicleTest(){
        Vehicle vehicle = new Vehicle();
        vehicle.setVehicle_id(1);
        vehicle.setVehicle_brand("American Dream");
        vehicle.setVehicle_model("42Q");
        vehicle.setLicence_plate("L-955q");
        vehicle.setVehicle_odometer(100000); //changed
        vehicle.setVehicle_status("READY");
        vehicle.setAdditional_notes(""); //changed
        vehicle.setMechanic_status("READY"); //changed
        vehicle.setCleaning_status("READY"); //changed
        vehicleService.updateVehicle(vehicle);
        assertEquals(100000, vehicleService.getVehicle(1).getVehicle_odometer());
        assertEquals("READY", vehicleService.getVehicle(1).getMechanic_status());
        assertEquals("READY", vehicleService.getVehicle(1).getCleaning_status());
        assertEquals("", vehicleService.getVehicle(1).getAdditional_notes());
    }


}
