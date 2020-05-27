package kea.nordicmotorhome.Service;

import kea.nordicmotorhome.Model.Employee;
import kea.nordicmotorhome.Repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmployeeService {

    @Autowired
    EmployeeRepository employeeRepository;

    public Employee auth(Employee employee) {
        return employeeRepository.auth(employee);
    }
}
