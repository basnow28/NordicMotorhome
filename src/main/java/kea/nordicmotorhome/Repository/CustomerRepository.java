package kea.nordicmotorhome.Repository;

import kea.nordicmotorhome.Model.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CustomerRepository {
    @Autowired
    JdbcTemplate template;

    public Customer getCustomer(int customer_id) {
        String sql = "SELECT * FROM customers INNER JOIN addresses ON customers.address_id = addresses.address_id WHERE customer_id = ?";
        RowMapper<Customer> rowMapper = new BeanPropertyRowMapper<>(Customer.class);
        return template.query(sql, rowMapper, customer_id).get(0);
    }

    public int createCustomer(Customer customer, int address_id){
        String sqlCustomer = " INSERT INTO customers (first_name, last_name, date_of_birth, phone_number, email, driver_licence_number, address_id) VALUES (?,?,?,?,?,?,?)";
        template.update(sqlCustomer, customer.getFirst_name(), customer.getLast_name(), customer.getDate_of_birth(), customer.getPhone_number(), customer.getEmail(), customer.getDriver_licence_number(), address_id);

        String sqlCustomerID = "select customer_id from customers ORDER BY customer_id DESC LIMIT 1";
        int customer_id = template.queryForObject(sqlCustomerID, Integer.class);
        customer.setCustomer_id(customer_id);
        return customer.getCustomer_id();
    }

    public int createAddress(Customer customer){
        String sqlAddress = "INSERT INTO addresses (street_name, house_number, postcode, city, country) VALUES (?, ?, ?, ?, ?)";
        template.update(sqlAddress, customer.getStreet_name(), customer.getHouse_number(), customer.getPostcode(), customer.getCity(), customer.getCountry());
        String sqlAddressID = "select address_id from addresses ORDER BY address_id DESC LIMIT 1";
        return template.queryForObject(sqlAddressID, Integer.class);
    }

    ///////////Updating customer////////////

    public void updateCustomer(Customer customer){
        String sqlCustomer = " UPDATE customers SET " +
                "first_name = ? , " +
                "last_name = ?, " +
                "date_of_birth = ?, " +
                "phone_number = ?, " +
                "email = ?, " +
                "driver_licence_number = ? " +
                "WHERE customer_id = ?";
        template.update(sqlCustomer, customer.getFirst_name(), customer.getLast_name(),
                customer.getDate_of_birth(), customer.getPhone_number(), customer.getEmail(),
                customer.getDriver_licence_number(), customer.getCustomer_id());
    }

    public void updateAddress(Customer customer){
        String sqlAddress = "UPDATE addresses SET " +
                "street_name = ? , " +
                "house_number = ?, " +
                "postcode = ?, " +
                "city = ?,  " +
                "country = ?" +
                "WHERE address_id = ?";
        template.update(sqlAddress, customer.getStreet_name(), customer.getHouse_number(), customer.getPostcode(), customer.getCity(), customer.getCountry(), customer.getAddress_id());
    }
    ///LIST//

    public List<Customer> findAllMatchingCustomer(String field_name, String field_value){
        String sql = "SELECT customer_id, first_name, last_name, phone_number,email, address_id FROM customers WHERE \"+ field_name + \" LIKE ?";
        RowMapper<Customer> rowMapper = new BeanPropertyRowMapper<>(Customer.class);
        return template.query(sql,rowMapper, field_value);
    }

}
