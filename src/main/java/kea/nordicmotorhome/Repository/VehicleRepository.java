package kea.nordicmotorhome.Repository;

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
        String sql = "SELECT * FROM vehicles";
        RowMapper<Vehicle> rowMapper = new BeanPropertyRowMapper<>(Vehicle.class);
        return template.query(sql, rowMapper);
    }


    public Vehicle getVehicle(String vehicle_id) {
        String sql = "SELECT * FROM vehicles INNER JOIN vehicle_types WHERE vehicles.vehicle_id = " + vehicle_id + " AND vehicles.vehicle_type_id = vehicle_types.vehicle_type_id";
        RowMapper<Vehicle> rowMapper = new BeanPropertyRowMapper<>(Vehicle.class);
        return template.query(sql, rowMapper).get(0);
    }
}
