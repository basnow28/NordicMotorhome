package kea.nordicmotorhome.Repository;

import kea.nordicmotorhome.Model.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class CustomerRepository {
    @Autowired
    JdbcTemplate template;

    public Customer getCustomer(int customer_id) {
        System.out.println(customer_id);
        String sql = "SELECT * FROM customers INNER JOIN addresses ON customers.address_id = addresses.address_id WHERE customer_id = ?";
        RowMapper<Customer> rowMapper = new BeanPropertyRowMapper<>(Customer.class);
        return template.query(sql, rowMapper, customer_id).get(0);
    }
}
