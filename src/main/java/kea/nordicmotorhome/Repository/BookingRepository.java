package kea.nordicmotorhome.Repository;


import kea.nordicmotorhome.Model.Booking;

import kea.nordicmotorhome.Model.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class BookingRepository {
    @Autowired
    JdbcTemplate template;

    public Booking createBooking(Booking booking, Customer customer) {
        String sqlAddress = "INSERT INTO addresses (street_name, house_number, postcode, city, country) VALUES (?, ?, ?, ?, ?)";
        template.update(sqlAddress, customer.getStreet_name(), customer.getHouse_number(), customer.getPostcode(), customer.getCity(), customer.getCountry());
        String sqlAddressID = "SELECT address_id FROM addresses WHERE street_name = ? AND  house_number = ? AND postcode = ? AND city = ? AND country = ?";
        int address_id=(template.queryForObject(sqlAddressID, new Object[] {customer.getStreet_name(), customer.getHouse_number(), customer.getPostcode(), customer.getCity(), customer.getCountry()}, Integer.class));

        String sqlCard = "INSERT INTO card_information (card_number, card_expiry, card_cvv) VALUES (?, ?, ?)";
        template.update(sqlCard, booking.getCard_number(), booking.getCard_expiry(), booking.getCard_cvv());
        String sqlCardID = "SELECT card_id FROM card_information WHERE card_number=? AND card_expiry=? AND card_cvv=?";
        int card_id=(template.queryForObject(sqlCard, new Object[] {booking.getCard_number(), booking.getCard_expiry(), booking.getCard_cvv()}, Integer.class));

        String sqlCustomer = " INSERT INTO customers (first_name, last_name, date_of_birth, phone_number, email, driver_licence_number, address_id) VALUES (?,?,?,?,?,?,?)";
        template.update(sqlCustomer, customer.getFirst_name(), customer.getLast_name(), customer.getDate_of_birth(), customer.getPhone_number(), customer.getEmail(), customer.getDriver_licence_number(), address_id);
        String sqlCustomerID = "SELECT customer_id FROM customers WHERE first_name=? AND last_name=? AND date_of_birth=? AND phone_number=? AND email=? AND driver_licence_number=? AND adress_id=\'" + address_id +"\';";
        customer.setCustomer_id(template.queryForObject(sqlCustomerID, new Object[] {customer.getFirst_name(), customer.getLast_name(), customer.getDate_of_birth(), customer.getPhone_number(), customer.getEmail(), customer.getDriver_licence_number()}, Integer.class));

        //????????????????????
        String sqlSeason = "SELECT season_id FROM seasons WHERE season_start < ?"; //we will count whole price depending when the reservation starts, which mean that if reservation starts in mid season and ends in low season wew ill count whole price for mid season
        int season_id = template.queryForObject(sqlSeason, new Object[] {booking.getStart_date()}, Integer.class);

        String sqlBooking = "INSERT INTO bookings (start_date, end_date, distance_driven, booking_status, payment_amount, fuel_check, booking_notes, has_picnic, has_bikerack, has_dvd_player" +
                "has_tent, has_linen, vehicle_id, employee_id, season_id, cancellation_id, customer_id, card_id) " +
                "VALUES (?,?,?,?,?,?,?,?,?,?,?,?)";
        template.update(sqlBooking, booking.getStart_date(), booking.getEnd_date(), booking.getDistance_driven(), booking.getBooking_status(), booking.getPayment_amount(), booking.isFuel_check(), booking.getBooking_notes(), booking.isHas_picnic(), booking.isHas_bikerack(),
                booking.isHas_dvd_player(), booking.isHas_tent(), booking.isHas_linen(), booking.getVehicle_id(), booking.getEmployee_id(), season_id, booking.getCustomer_id(), card_id);

        return booking;
    }

}
