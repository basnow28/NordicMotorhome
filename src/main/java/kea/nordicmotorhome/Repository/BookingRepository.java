package kea.nordicmotorhome.Repository;


import kea.nordicmotorhome.Model.Booking;
import kea.nordicmotorhome.Model.Customer;
import kea.nordicmotorhome.Model.Season;
import kea.nordicmotorhome.Model.Vehicle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.List;

@Repository
public class BookingRepository {
    @Autowired
    JdbcTemplate template;

    public int createAddress(Customer customer){
        String sqlAddress = "INSERT INTO addresses (street_name, house_number, postcode, city, country) VALUES (?, ?, ?, ?, ?)";
        template.update(sqlAddress, customer.getStreet_name(), customer.getHouse_number(), customer.getPostcode(), customer.getCity(), customer.getCountry());
        String sqlAddressID = "select address_id from addresses ORDER BY address_id DESC LIMIT 1";
        return template.queryForObject(sqlAddressID, Integer.class);
    }
    public int createCardInformation(Booking booking){
        String sqlCard = "INSERT INTO card_information (card_number, card_expiry_date, card_cvv) VALUES (?, ?, ?)";
        template.update(sqlCard, booking.getCard_number(), booking.getCard_expiry_date(), booking.getCard_cvv());

        String sqlCardID = "select card_information.card_id from card_information ORDER BY card_information.card_id DESC LIMIT 1";
        return template.queryForObject(sqlCardID, Integer.class);
    }

    public int createCustomer(Customer customer, int address_id){
        String sqlCustomer = " INSERT INTO customers (first_name, last_name, date_of_birth, phone_number, email, driver_licence_number, address_id) VALUES (?,?,?,?,?,?,?)";
        template.update(sqlCustomer, customer.getFirst_name(), customer.getLast_name(), customer.getDate_of_birth(), customer.getPhone_number(), customer.getEmail(), customer.getDriver_licence_number(), address_id);

        String sqlCustomerID = "select customer_id from customers ORDER BY customer_id DESC LIMIT 1";
        int customer_id = template.queryForObject(sqlCustomerID, Integer.class);
        customer.setCustomer_id(customer_id);
        return customer.getCustomer_id();
    }
    /*public int findSeasonId(Booking booking){
        String sqlSeason = "SELECT season_id FROM seasons WHERE season_start < ?"; //we will count whole price depending when the reservation starts, which mean that if reservation starts in mid season and ends in low season wew ill count whole price for mid season
        int season_id = template.queryForObject(sqlSeason, new Object[] {booking.getStart_date()}, Integer.class);
        System.out.println("Season:" + season_id);
        return season_id;
    }

     */

    public int createBooking(Booking booking, Customer customer) { //employee_id is manual for now
        int address_id = createAddress(customer);
        int card_id = createCardInformation(booking);
        int customer_id = createCustomer(customer, address_id);
        //int season_id= findSeasonId(booking);

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
                "WHERE bookings.start_date <= ? AND bookings.end_date >= ? )";
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

}
