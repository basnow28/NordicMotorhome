package kea.nordicmotorhome.Repository;

import kea.nordicmotorhome.Model.Booking;
import kea.nordicmotorhome.Model.Card;
import kea.nordicmotorhome.Model.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class BookingRepository {
    @Autowired
    JdbcTemplate template;


}
