package kea.nordicmotorhome;

import kea.nordicmotorhome.Model.Customer;
import kea.nordicmotorhome.Model.SearchForm;
import kea.nordicmotorhome.Service.CustomerService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class CustomerServiceTest {
    @Autowired
    CustomerService customerService;

    //TESTS FOR FIND CUSTOMER USE CASE//

    @Test
    //Method tests if list contains every existing customer in database
    //at this point we have 13 customers in database
    void getAllCustomers(){
        assertEquals(13, customerService.getAllCustomers().size());
    }

    @Test
    //Method tests if list returned from repository contains all customers which fit searching criteria
    void findAllMatchingCustomersTest(){
        SearchForm searchForm = new SearchForm();
        searchForm.setAttribute("last_name");
        searchForm.setValue("Przygocka");
        assertEquals(2, customerService.findAllMatchingCustomers(searchForm).size());
    }

 //TESTS FOR UPDATE CUSTOMER USE CASE//

    @Test
    //Method tests if the right customer is returned based on given customer_id
    void getCustomer(){
        int id = 1;
        assertEquals("Kasia", customerService.getCustomer(id).getFirst_name());
    }

    @Test
    //Method tests if information for specified customer are updated in database
    void updateCustomerTest(){
        Customer customer = new Customer();
        customer.setCustomer_id(8);
        customer.setFirst_name("Alex");
        customer.setLast_name("Hansen");
        customer.setDate_of_birth("1988-02-01");
        customer.setPhone_number("10203040");
        customer.setEmail("a@hansen.com");
        customer.setDriver_licence_number("AE1234");
        customerService.updateCustomer(customer);
        assertEquals(customer.getFirst_name(), customerService.getCustomer(8).getFirst_name());
    }

}
