package kea.nordicmotorhome.Service;

import kea.nordicmotorhome.Model.Customer;
import kea.nordicmotorhome.Repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {
    @Autowired
    CustomerRepository customerRepository;

    public Customer getCustomer(int customer_id) {
        return customerRepository.getCustomer(customer_id);
    }
    public List<Customer> findAllMatchingCustomer(String first_name){
        return customerRepository.findAllMatchingCustomer(first_name);
    }
    public void updateCustomer(Customer customer){
        customerRepository.updateCustomer(customer);
    }
    public void updateAddress(Customer customer){
        customerRepository.updateAddress(customer);
    }
}