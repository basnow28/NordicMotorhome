package kea.nordicmotorhome.Repository;


import kea.nordicmotorhome.Model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.awt.print.Book;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Repository
public class BookingRepository {
    @Autowired
    JdbcTemplate template;
    @Autowired
    CustomerRepository customerRepository;

    public int createCardInformation(Booking booking){
        String sqlCard = "INSERT INTO card_information (card_number, card_expiry_date, card_cvv) VALUES (?, ?, ?)";
        template.update(sqlCard, booking.getCard_number(), booking.getCard_expiry_date(), booking.getCard_cvv());

        String sqlCardID = "select card_information.card_id from card_information ORDER BY card_information.card_id DESC LIMIT 1";
        return template.queryForObject(sqlCardID, Integer.class);
    }

    public int createBooking(Booking booking, Customer customer) { //employee_id is manual for now
        int address_id = customerRepository.createAddress(customer);
        int card_id = createCardInformation(booking);
        int customer_id = customerRepository.createCustomer(customer, address_id);

        String sqlBooking = "INSERT INTO bookings (" +
                "start_date, " +
                "end_date, " +
                "distance_driven, " +
                "drop_off_kilometers, "+
                "initial_cost, "+
                "extras_cost, " +
                "booking_status, " +
                "payment_amount, " +
                "fuel_check, " +
                "booking_notes, " +
                "has_picnic, " +
                "has_bikerack, " +
                "has_dvd_player," +
                "has_tent, " +
                "has_linen, " +
                "vehicle_id, " +
                "employee_id, " +
                "cancellation_id, " +
                "customer_id, " +
                "card_id) " +
                "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        template.update(sqlBooking, booking.getStart_date(), booking.getEnd_date(),
                booking.getDistance_driven(), booking.getDrop_off_kilometers(), booking.getInitial_cost(), booking.getExtras_cost(), booking.getBooking_status(),
                booking.getPayment_amount(), booking.isFuel_check(), booking.getBooking_notes(), booking.isHas_picnic(), booking.isHas_bikerack(),
                booking.isHas_dvd_player(), booking.isHas_tent(), booking.isHas_linen(),
                booking.getVehicle_id(), 1, 1, customer_id, card_id);

        String sqlBookingID = "SELECT booking_id FROM bookings ORDER BY customer_id DESC LIMIT 1";
        int booking_id = template.queryForObject(sqlBookingID, Integer.class);

        return booking_id;
    }

    public List<Vehicle> findFreeVehicles(String startDate, String endDate, int vehicle_capacity){
        String sql = "SELECT vehicles.vehicle_id, vehicles.vehicle_model, vehicles.vehicle_brand, \n" +
                "vehicle_types.vehicle_type_name, vehicle_types.vehicle_capacity, vehicles.licence_plate, \n" + "vehicle_types.cost_per_day\n" +
                "FROM vehicles JOIN vehicle_types\n" +
                "ON vehicles.vehicle_type_id = vehicle_types.vehicle_type_id\n" +
                "WHERE vehicle_types.vehicle_capacity=? AND vehicles.vehicle_id NOT IN \n" +
                "(SELECT vehicles.vehicle_id FROM vehicles JOIN bookings " +
                "ON vehicles.vehicle_id=bookings.vehicle_id\n" +
                "WHERE bookings.start_date <= ? AND bookings.end_date >= ? AND bookings.booking_status != 'CANCELLED' )";
        RowMapper<Vehicle> rowMapper = new BeanPropertyRowMapper<>(Vehicle.class);
        return template.query(sql, rowMapper, vehicle_capacity, endDate,startDate);
    }

    public int findSeasonRate(String startDate, String endDate){
        String sql = "SELECT seasons.season_rate FROM seasons WHERE ? BETWEEN season_start AND season_end";
        return template.queryForObject(sql, new Object[] {startDate}, Integer.class);
    }

    public List<Season> getSeasons() {
        String sql = "SELECT * FROM seasons;";
        RowMapper<Season> rowMapper = new BeanPropertyRowMapper<>(Season.class);

        return template.query(sql, rowMapper);
    }


    public List<Booking> getBookings(String startDate, String endDate, String inputType, String inputText ) {
        String inputsql = "SELECT * FROM bookings WHERE ? = ?; ";
        String datesql = "SELECT * FROM bookings WHERE start_date = ? AND end_date = ?;";
        RowMapper<Booking> rowMapper = new BeanPropertyRowMapper<>(Booking.class);
        if(inputType==null){
            return template.query(datesql, rowMapper, startDate, endDate);
        }
        return template.query(inputsql, rowMapper, inputType, inputText);
    }

    public List<Booking> getAllBookings(){
        String sql = "SELECT * FROM bookings;";
        RowMapper<Booking> rowMapper = new BeanPropertyRowMapper<>(Booking.class);
        return template.query(sql, rowMapper);
    }


    public double getExtraPrice(String extra_name) {
        String sql = "SELECT extra_price FROM extras WHERE extra_name = ?" ;
        return template.queryForObject(sql,new Object[]{extra_name}, Double.class);
    }

    public Booking getBooking(int id) {
        String sql = "SELECT * FROM bookings INNER JOIN card_information ON bookings.card_id = card_information.card_id WHERE booking_id = ?";
        RowMapper<Booking> rowMapper = new BeanPropertyRowMapper<>(Booking.class);
        return template.query(sql, rowMapper, id).get(0);
    }

    ///////////////UPDATE BOOKING INFORMATION////////////////////

    public void updateCardInformation(Booking booking){
        String sqlCard = "UPDATE card_information SET " +
                            "card_number = ? , " +
                            "card_expiry_date = ? , " +
                            "card_cvv =  ? " +
                            "WHERE card_id = ?";

        template.update(sqlCard, booking.getCard_number(), booking.getCard_expiry_date(), booking.getCard_cvv(), booking.getCard_id());
    }

    public void updateBooking(Booking booking, Customer customer) { //employee_id is manual for now
        updateCardInformation(booking);
        customerRepository.updateAddress(customer);
        customerRepository.updateCustomer(customer);

        String sqlBooking = "UPDATE bookings SET " +
                "start_date = ? , " +
                "end_date = ? , " +
                "distance_driven = ? , " +
                "drop_off_kilometers = ? , "+
                "initial_cost = ? , "+
                "extras_cost = ? , " +
                "booking_status = ? , " +
                "payment_amount = ? , " +
                "fuel_check = ? , " +
                "booking_notes = ? , " +
                "has_picnic = ? , " +
                "has_bikerack = ? , " +
                "has_dvd_player = ? ," +
                "has_tent = ? , " +
                "has_linen = ? " +
                "WHERE booking_id = ?";
        template.update(sqlBooking, booking.getStart_date(), booking.getEnd_date(),
                booking.getDistance_driven(), booking.getDrop_off_kilometers(), booking.getInitial_cost(), booking.getExtras_cost(), booking.getBooking_status(),
                booking.getPayment_amount(), booking.isFuel_check(), booking.getBooking_notes(), booking.isHas_picnic(), booking.isHas_bikerack(),
                booking.isHas_dvd_player(), booking.isHas_tent(), booking.isHas_linen(), booking.getBooking_id());
    }
    public Cancellation getCancellationRate(int days_out){
        String sql = "SELECT * FROM cancellations WHERE ? BETWEEN cancellations.days_out_min AND cancellations.days_out_max ";
        RowMapper<Cancellation> rowMapper = new BeanPropertyRowMapper<>(Cancellation.class);
        Cancellation c = template.query(sql, rowMapper, days_out).get(0);

        String sqlUpdateBooking= "UPDATE bookings SET cancellation_id = ?";
        template.update(sqlUpdateBooking, c.getCancellation_id());

        System.out.println(c.toString());
        return c;
    }

    public void updateBookingPayment(int booking_id, double payment_amount) {
        String sql = "UPDATE bookings SET " +
                "payment_amount = ? " +
                "WHERE booking_id = ?";
        template.update(sql, payment_amount, booking_id);
    }
}
