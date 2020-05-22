package kea.nordicmotorhome.Repository;

import kea.nordicmotorhome.Model.Booking;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Repository
public class BookingRepository {
    @Autowired
    JdbcTemplate template;

}
