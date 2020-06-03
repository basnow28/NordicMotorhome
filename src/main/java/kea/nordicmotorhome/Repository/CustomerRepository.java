package kea.nordicmotorhome.Repository;

import kea.nordicmotorhome.Model.Customer;
import kea.nordicmotorhome.Model.SearchForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class CustomerRepository {
    @Autowired
    JdbcTemplate template;


////// CREATE BOOKING USE CASE //
///////////////////********* DAGMARA ************///////////////////
    //Method for creating new customer in database and returning created customer ID
    public int createCustomer(Customer customer){

        String sqlCustomer = " INSERT INTO customers (first_name, last_name, date_of_birth, phone_number, email, driver_licence_number, address_id) VALUES (?,?,?,?,?,?,?)";
        template.update(sqlCustomer, customer.getFirst_name(), customer.getLast_name(), customer.getDate_of_birth(), customer.getPhone_number(), customer.getEmail(), customer.getDriver_licence_number(), customer.getAddress_id());

        String sqlCustomerID = "select customer_id from customers ORDER BY customer_id DESC LIMIT 1";
        int customer_id = template.queryForObject(sqlCustomerID, Integer.class);
        customer.setCustomer_id(customer_id);
        return customer.getCustomer_id();
    }
    ///////////////////********* DAGMARA ************///////////////////
    //Method for creating new address in database and returning created address ID
    public int createAddress(Customer customer){
        String sqlAddress = "INSERT INTO addresses (street_name, house_number, postcode, city, country) VALUES (?, ?, ?, ?, ?)";
        template.update(sqlAddress, customer.getStreet_name(), customer.getHouse_number(), customer.getPostcode(), customer.getCity(), customer.getCountry());
        String sqlAddressID = "select address_id from addresses ORDER BY address_id DESC LIMIT 1";
        return template.queryForObject(sqlAddressID, Integer.class);
    }

///FIND CUSTOMER USE CASE//
///////////////////********* DAGMARA ************///////////////////
    //Method for displaying all existing customers
    public List<Customer> getAllCustomers(){
        String sql = "SELECT customer_id, first_name, last_name, phone_number,email, address_id FROM customers ";
        RowMapper<Customer> rowMapper = new BeanPropertyRowMapper<>(Customer.class);
        return template.query(sql,rowMapper);
    }
    ///////////////////********* DAGMARA ************///////////////////
    //Method for creating list of all customers which fit searching criteria
    public List<Customer> findAllMatchingCustomers(SearchForm searchForm){
        String sql = "SELECT customer_id, first_name, last_name, phone_number,email, address_id FROM customers WHERE "+ searchForm.getAttribute() + " LIKE ?";
        RowMapper<Customer> rowMapper = new BeanPropertyRowMapper<>(Customer.class);
        String value = "%"+searchForm.getValue()+"%";
        return template.query(sql,rowMapper, value);
    }

//UPDATE CUSTOMER USE CASE//
///////////////////********* BARBARA ************///////////////////
    //Method for updating customer information in database
    public void updateCustomer(Customer customer){

        updateAddress(customer);
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
    ///////////////////********* BARBARA ************///////////////////
    ////Method for updating address information in database
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
    ///////////////////********* DAGMARA ************///////////////////
    //Method for returning customer from database based on given ID
    public Customer getCustomer(int customer_id) {
        String sql = "SELECT * FROM customers INNER JOIN addresses ON customers.address_id = addresses.address_id WHERE customer_id = ?";
        RowMapper<Customer> rowMapper = new BeanPropertyRowMapper<>(Customer.class);
        return template.query(sql, rowMapper, customer_id).get(0);
    }

//Check if customer exists
///////////////////********* BARBARA ************///////////////////
    //Method return from database customer that already exists by email
    public boolean doesExist(Customer customer) {
        String sql = "SELECT * FROM customers WHERE email = ? ";
        RowMapper<Customer> rowMapper = new BeanPropertyRowMapper<>(Customer.class);
        List<Customer> list = template.query(sql,rowMapper, customer.getEmail());
        return list.size() > 0;
    }
    ///////////////////********* BARBARA ************///////////////////
    public int getCustomerId(String email) {
        String sql = "SELECT * FROM customers INNER JOIN addresses ON customers.address_id = addresses.address_id WHERE email = ?";
        RowMapper<Customer> rowMapper = new BeanPropertyRowMapper<>(Customer.class);
        ArrayList<Customer> list = (ArrayList<Customer>) template.query(sql, rowMapper, email);

        if(list.size() > 0){
            return list.get(0).getCustomer_id();
        }
        return 0;
    }
}
