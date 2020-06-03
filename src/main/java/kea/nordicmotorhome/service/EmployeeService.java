package kea.nordicmotorhome.service;

import kea.nordicmotorhome.model.Employee;
import kea.nordicmotorhome.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmployeeService {

    @Autowired
    EmployeeRepository employeeRepository;
    ///////////////////********* BARBARA ************///////////////////
    ////Authorizing employee when logging in
    public Employee auth(Employee employee) {
        return employeeRepository.auth(employee);
    }
}
