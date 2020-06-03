package kea.nordicmotorhome.controller;
import kea.nordicmotorhome.Model.Customer;
import kea.nordicmotorhome.NordicmotorhomeApplication;
import kea.nordicmotorhome.Model.SearchForm;
import kea.nordicmotorhome.Service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class CustomerController {
    @Autowired
    CustomerService customerService;

//FIND CUSTOMER USE CASE//
///////////////////********* DAGMARA ************///////////////////
    //Method for modeling List of customers which fit searching criteria
    @PostMapping("/findCustomer") //@get mapping should be
    public String findCustomer(@ModelAttribute SearchForm searchForm, Model model){
        List<Customer> customersList = customerService.findAllMatchingCustomers(searchForm);
        model.addAttribute("customersList", customersList);
        model.addAttribute("searchForm", searchForm);
        return "/customers";
    }

//UPDATE CUSTOMER USE CASE//
///////////////////********* DAGMARA ************///////////////////
    //Method for displaying selected customer details page
    @GetMapping("/customerDetails/{id}")
    public String viewCustomerDetails(@PathVariable int id, Model model){
        if(!NordicmotorhomeApplication.isAuthorized()){
            return "redirect:/";
        }
        Customer customer = customerService.getCustomer(id);
        model.addAttribute("customer", customer);
        model.addAttribute("title", "Customer " + customer.getCustomer_id());
        model.addAttribute("employee_type", NordicmotorhomeApplication.getEmployee().getEmployee_type().toUpperCase());
        return "customerDetails";
    }
    ///////////////////********* DAGMARA ************///////////////////
    //Method for updating selected customer details and modeling new information
    @PostMapping("/saveCustomerDetails")
    public String viewCustomerDetails(@ModelAttribute Customer customer){
        customerService.updateCustomer(customer);
        return "redirect:/customerDetails/"+customer.getCustomer_id();
    }

}
