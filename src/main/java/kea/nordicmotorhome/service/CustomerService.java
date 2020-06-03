package kea.nordicmotorhome.service;

import kea.nordicmotorhome.model.Customer;
import kea.nordicmotorhome.model.SearchForm;
import kea.nordicmotorhome.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {
    @Autowired
    CustomerRepository customerRepository;

 //FIND CUSTOMER USE CASE
    ///////////////////********* DAGMARA ************///////////////////
    //Method returning list of all existing customers from repository
    public List<Customer> getAllCustomers() {
        return customerRepository.getAllCustomers();
    }
    ///////////////////********* DAGMARA ************///////////////////
    //Method for returning from repository list of matching customers with criteria from searchForm
    public List<Customer> findAllMatchingCustomers(SearchForm searchForm){
        return customerRepository.findAllMatchingCustomers(searchForm);
    }

 //UPDATE CUSTOMER USE CASE
///////////////////********* BARBARA ************///////////////////
    //Method for calling repository to update existing customers with new information
    public void updateCustomer(Customer customer){
        if(customerRepository.getCustomerId(customer.getEmail()) == customer.getCustomer_id()) {
            customerRepository.updateCustomer(customer);
        }else if(customerRepository.getCustomerId(customer.getEmail()) == 0){
            customerRepository.updateCustomer(customer);
        }
    }
    ///////////////////********* BARBARA ************///////////////////
    //Method that returns from repository specified by ID customer
    public Customer getCustomer(int customer_id) {
        return customerRepository.getCustomer(customer_id);
    }
}
