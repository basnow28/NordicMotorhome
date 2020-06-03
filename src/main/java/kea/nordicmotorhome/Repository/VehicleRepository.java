package kea.nordicmotorhome.Repository;

import kea.nordicmotorhome.Model.SearchForm;
import kea.nordicmotorhome.Model.Vehicle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class VehicleRepository {

    @Autowired
    JdbcTemplate template;

//FIND VEHICLE USE CASE//
///////////////////********* BARBARA ************///////////////////
    //Method for returning list of all vehicles from database
    public List<Vehicle> getAllVehicles(){
        String sql = "SELECT * FROM vehicles INNER JOIN vehicle_types WHERE vehicles.vehicle_type_id = vehicle_types.vehicle_type_id";
        RowMapper<Vehicle> rowMapper = new BeanPropertyRowMapper<>(Vehicle.class);
        return template.query(sql, rowMapper);
    }
    ///////////////////********* BARBARA ************///////////////////
    //Method for returning list of vehicles that fulfill specified searching criteria
    public List<Vehicle> getVehicles(SearchForm searchForm) {
        String sql = "SELECT * FROM vehicles INNER JOIN vehicle_types ON vehicles.vehicle_type_id = vehicle_types.vehicle_type_id " +
                "WHERE " + searchForm.getAttribute() + " LIKE ? ";
        RowMapper<Vehicle> rowMapper = new BeanPropertyRowMapper<>(Vehicle.class);
        String value = "%"+searchForm.getValue()+"%";
        return template.query(sql, rowMapper, value);
    }

//UPDATE VEHICLE USE CASE//
///////////////////********* BARBARA ************///////////////////
    //Method for returning from database specified vehicle by given ID
    public Vehicle getVehicle(int vehicle_id) {
        String sql = "SELECT * FROM vehicles INNER JOIN vehicle_types WHERE vehicles.vehicle_id = " + vehicle_id + " AND vehicles.vehicle_type_id = vehicle_types.vehicle_type_id";
        RowMapper<Vehicle> rowMapper = new BeanPropertyRowMapper<>(Vehicle.class);
        return template.query(sql, rowMapper).get(0);
    }
    ///////////////////********* BARBARA ************///////////////////
    //Method for updating in database vehicle information
    public void updateVehicle(Vehicle vehicle) {
        String sql = "UPDATE vehicles SET " +
                "licence_plate = ? , " +
                "vehicle_odometer = ? , " +
                "vehicle_status = ? , " +
                "additional_notes = ? , " +
                "mechanic_status = ? , " +
                "cleaning_status = ?  " +
         //       "cost_per_day = ? , " +
        //        "vehicle_capacity = ? " +
                " WHERE vehicle_id = ?";

        template.update(sql, vehicle.getLicence_plate(), vehicle.getVehicle_odometer(), vehicle.getVehicle_status(), vehicle.getAdditional_notes(),
                vehicle.getMechanic_status(), vehicle.getCleaning_status(),
               // vehicle.getCost_per_day(),
             //   vehicle.getVehicle_capacity(),
                vehicle.getVehicle_id());
    }
    ///////////////////********* BARBARA ************///////////////////
    //Method used by mechanic and cleaner for updating their status in database
    public void updateVehicleStatus(Vehicle vehicle) {
        String sql = "UPDATE vehicles SET " +
                "mechanic_status = ? , " +
                "cleaning_status = ?,  " +
                "additional_notes = ? " +
                " WHERE vehicle_id = ?";

        template.update(sql, vehicle.getMechanic_status(), vehicle.getCleaning_status(), vehicle.getAdditional_notes(), vehicle.getVehicle_id());
    }
}
