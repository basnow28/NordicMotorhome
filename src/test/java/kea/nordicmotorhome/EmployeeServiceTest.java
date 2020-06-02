package kea.nordicmotorhome;


import kea.nordicmotorhome.Model.Employee;
import kea.nordicmotorhome.Service.EmployeeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class EmployeeServiceTest {
    @Autowired
    EmployeeService employeeService;

    ////////Testing the Login feature
    @Test
    void authTest(){
        Employee mechanic = new Employee();
        mechanic.setEmployee_login("mhansen");
        mechanic.setEmployee_password("llsik3078");

        Employee mechanicAuthorized = employeeService.auth(mechanic);
        assertEquals("MECHANIC",mechanicAuthorized.getEmployee_type().toUpperCase());
    }
}
