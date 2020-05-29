package kea.nordicmotorhome.controller;

import kea.nordicmotorhome.Model.Customer;
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

    @GetMapping("/customers")
    public String findCustomer(Model model){
        SearchForm searchForm = new SearchForm();
        model.addAttribute("searchForm", searchForm);
        return "/customers";
    }
    @PostMapping("/findCustomer")
    public String findCustomer(@ModelAttribute SearchForm searchForm, Model model){
        ArrayList<Customer> customersList = (ArrayList<Customer>) customerService.findAllMatchingCustomer(searchForm);
        model.addAttribute("customersList", customersList);
        model.addAttribute("searchForm", searchForm);
        return "/customers";
    }
    @GetMapping("/customerDetails/{id}")
    public String viewCustomerDetails(@PathVariable int id, Model model){
        Customer customer = customerService.getCustomer(id);
        model.addAttribute("customer", customer);
        model.addAttribute("title", "Customer " + customer.getCustomer_id());
        return "customerDetails";
    }
    @PostMapping("/saveCustomerDetails")
    public String viewCustomerDetails(@ModelAttribute Customer customer){
        customerService.updateCustomer(customer);
        customerService.updateAddress(customer);
        return "redirect:/customerDetails/"+customer.getCustomer_id();
    }

}
