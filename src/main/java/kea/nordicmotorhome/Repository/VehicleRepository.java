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

    public List<Vehicle> getAllVehicles(){
        String sql = "SELECT * FROM vehicles INNER JOIN vehicle_types WHERE vehicles.vehicle_type_id = vehicle_types.vehicle_type_id";
        RowMapper<Vehicle> rowMapper = new BeanPropertyRowMapper<>(Vehicle.class);
        return template.query(sql, rowMapper);
    }


    public Vehicle getVehicle(int vehicle_id) {
        String sql = "SELECT * FROM vehicles INNER JOIN vehicle_types WHERE vehicles.vehicle_id = " + vehicle_id + " AND vehicles.vehicle_type_id = vehicle_types.vehicle_type_id";
        RowMapper<Vehicle> rowMapper = new BeanPropertyRowMapper<>(Vehicle.class);
        return template.query(sql, rowMapper).get(0);
    }

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

    public List<Vehicle> getVehicles(SearchForm searchForm) {
        String sql = "SELECT * FROM vehicles INNER JOIN vehicle_types ON vehicles.vehicle_type_id = vehicle_types.vehicle_type_id " +
                "WHERE " + searchForm.getAttribute() + " LIKE ? ";
        RowMapper<Vehicle> rowMapper = new BeanPropertyRowMapper<>(Vehicle.class);
        String value = "%"+searchForm.getValue()+"%";
        return template.query(sql, rowMapper, value);
    }
}
