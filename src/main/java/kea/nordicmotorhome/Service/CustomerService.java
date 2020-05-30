package kea.nordicmotorhome.Service;

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
//??????????????????????????????????????????????????????????????????????????????????????????????????????????????????/
    public Customer getCustomer(int customer_id) {
        return customerRepository.getCustomer(customer_id);
    }

 //FIND CUSTOMER USE CASE

    //Method for returning from repository list of matching customers with criteria from searchForm
    public List<Customer> findAllMatchingCustomer(SearchForm searchForm){
        return customerRepository.findAllMatchingCustomer(searchForm);
    }

 //UPDATE CUSTOMER USE CASE

    //Method for calling repository to update existing customers with new information
    public void updateCustomer(Customer customer){
        customerRepository.updateCustomer(customer);
    }

    //Method for calling repository to update existing address
    public void updateAddress(Customer customer){
        customerRepository.updateAddress(customer);
    }
}
