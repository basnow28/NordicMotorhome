package kea.nordicmotorhome.Repository;


import kea.nordicmotorhome.Model.Booking;
import kea.nordicmotorhome.Model.Customer;
import kea.nordicmotorhome.Model.Vehicle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Repository
public class BookingRepository {
    @Autowired
    JdbcTemplate template;

    public int createAddress(Customer customer){
        String sqlAddress = "INSERT INTO addresses (street_name, house_number, postcode, city, country) VALUES (?, ?, ?, ?, ?)";
        template.update(sqlAddress, customer.getStreet_name(), customer.getHouse_number(), customer.getPostcode(), customer.getCity(), customer.getCountry());
        String sqlAddressID = "SELECT address_id FROM addresses WHERE street_name = ? AND  house_number = ? AND postcode = ? AND city = ? AND country = ?";
        int address_id=(template.queryForObject(sqlAddressID, new Object[] {customer.getStreet_name(), customer.getHouse_number(), customer.getPostcode(), customer.getCity(), customer.getCountry()}, Integer.class));
        return address_id;
    }
    public int createCardInfomration(Booking booking){
        String sqlCard = "INSERT INTO card_information (card_number, card_expiry_date, card_cvv) VALUES (?, ?, ?)";
        template.update(sqlCard, booking.getCard_number(), booking.getCard_expiry_date(), booking.getCard_cvv());
        String sqlCardID = "SELECT card_information.card_id FROM card_information WHERE card_number = ? AND card_expiry_date = ? AND card_cvv = ?";
        int card_id=(template.queryForObject(sqlCardID, new Object[] {booking.getCard_number(), booking.getCard_expiry_date(), booking.getCard_cvv()}, Integer.class));
        return card_id;
    }

    public void createCustomer(Customer customer, int address_id){
        String sqlCustomer = " INSERT INTO customers (first_name, last_name, date_of_birth, phone_number, email, driver_licence_number, address_id) VALUES (?,?,?,?,?,?,?)";
        template.update(sqlCustomer, customer.getFirst_name(), customer.getLast_name(), customer.getDate_of_birth(), customer.getPhone_number(), customer.getEmail(), customer.getDriver_licence_number(), address_id);

        String sqlCustomerID = "SELECT customer_id FROM customers WHERE first_name=? AND last_name=? AND date_of_birth=? AND phone_number=? AND email=? AND driver_licence_number=? AND adress_id=?";
        customer.setCustomer_id(template.queryForObject(sqlCustomerID, new Object[] {customer.getFirst_name(), customer.getLast_name(), customer.getDate_of_birth(), customer.getPhone_number(), customer.getEmail(), customer.getDriver_licence_number(), address_id}, Integer.class));
        System.out.println("customer"+ customer.getCustomer_id());
    }

    public Booking createBooking(Booking booking, Customer customer) {
        int address_id = createAddress(customer);
        int card_id = createCardInfomration(booking);
        createCustomer(customer, address_id);

        //????????????????????
        //String sqlSeason = "SELECT season_id FROM seasons WHERE season_start < ?"; //we will count whole price depending when the reservation starts, which mean that if reservation starts in mid season and ends in low season wew ill count whole price for mid season
        //int season_id = template.queryForObject(sqlSeason, new Object[] {booking.getStart_date()}, Integer.class);

        /*String sqlBooking = "INSERT INTO bookings (start_date, end_date, distance_driven, booking_status, payment_amount, fuel_check, booking_notes, has_picnic, has_bikerack, has_dvd_player" +
                "has_tent, has_linen, vehicle_id, employee_id, season_id, cancellation_id, customer_id, card_id) " +
                "VALUES (?,?,?,?,?,?,?,?,?,?,?,?)";
        template.update(sqlBooking, booking.getStart_date(), booking.getEnd_date(), booking.getDistance_driven(), booking.getBooking_status(), booking.getPayment_amount(), booking.isFuel_check(), booking.getBooking_notes(), booking.isHas_picnic(), booking.isHas_bikerack(),
                booking.isHas_dvd_player(), booking.isHas_tent(), booking.isHas_linen(), booking.getVehicle_id(), booking.getEmployee_id(), season_id, booking.getCustomer_id(), card_id);

        return booking;

         */
        return null;
    }



    public List<Vehicle> findFreeVehicles(String startDate, String endDate, int vehicle_capacity){
        String sql = "SELECT vehicles.vehicle_id, vehicles.vehicle_model, vehicles.vehicle_brand, vehicle_types.vehicle_type_name, vehicle_types.vehicle_capacity, vehicles.licence_plate, vehicle_types.cost_per_day" +
                "FROM vehicles JOIN on vehicles.vehicle_id = vehicle_types.vehicle_id LEFT JOIN bookings ON vehicles.vehicle_id= bookings.vehicle.id " +
                "WHERE ? NOT BETWEEN bookings.start_date AND bookings_end_date AND vehicle_types.vehicle_capacity=? " +
                "AND bookings.start_date NOT BETWEEN ? AND ? " +
                "AND ? NOT BETWEEN bookings.start_date AND bookings_end_date AND vehicle_types.vehicle_capacity=?";
        RowMapper<Vehicle> rowMapper = new BeanPropertyRowMapper<>(Vehicle.class);
        return template.query(sql, rowMapper, startDate, vehicle_capacity, endDate);
    }

    public int findSeasonRate(String startDate, String endDate){
        String sql = "SELECT seasons.season_rate FROM seasons WHERE ? BETWEEN season_start AND season_end";
        return template.queryForObject(sql, new Object[] {startDate}, Integer.class);
    }
}
