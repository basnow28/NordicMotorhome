package kea.nordicmotorhome;

import kea.nordicmotorhome.Model.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class NordicmotorhomeApplication {
    ///Singleton Employee throughout the program.
    @Autowired
    private static Employee employee;

    public static void main(String[] args) {
        employee = new Employee();
        SpringApplication.run(NordicmotorhomeApplication.class, args);
    }

    ///////////////////********* BARBARA ************///////////////////
    public static Employee getEmployee() {
        return employee;
    }

    public static boolean isAuthorized(){
        return employee.getFirst_name() != null && employee.getLast_name() != null;
    }
    public static void setEmployee(Employee employee) {
        NordicmotorhomeApplication.employee = employee;
    }
}
