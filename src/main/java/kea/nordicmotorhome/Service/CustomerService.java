package kea.nordicmotorhome.Service;

import kea.nordicmotorhome.Model.Booking;
import kea.nordicmotorhome.Model.Customer;
import kea.nordicmotorhome.Model.SearchForm;
import kea.nordicmotorhome.Repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {
    @Autowired
    CustomerRepository customerRepository;

 //FIND CUSTOMER USE CASE

    //Method returning list of all existing customers from repository
    public List<Customer> getAllCustomers() {
        return customerRepository.getAllCustomers();
    }

    //Method for returning from repository list of matching customers with criteria from searchForm
    public List<Customer> findAllMatchingCustomers(SearchForm searchForm){
        return customerRepository.findAllMatchingCustomers(searchForm);
    }

 //UPDATE CUSTOMER USE CASE

    //Method for calling repository to update existing customers with new information
    public void updateCustomer(Customer customer){
        if(customerRepository.getCustomerId(customer.getEmail()) == customer.getCustomer_id()) {
            customerRepository.updateCustomer(customer);
        }else if(customerRepository.getCustomerId(customer.getEmail()) == 0){
            customerRepository.updateCustomer(customer);
        }
    }

    //Method that returns from repository specified by ID customer
    public Customer getCustomer(int customer_id) {
        return customerRepository.getCustomer(customer_id);
    }
}
