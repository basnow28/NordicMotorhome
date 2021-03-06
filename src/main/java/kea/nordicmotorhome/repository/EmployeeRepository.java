package kea.nordicmotorhome.repository;

import kea.nordicmotorhome.model.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class EmployeeRepository {

    @Autowired
    JdbcTemplate template;
    ////Authorizing Employee using password and login
    ///////////////////********* BARBARA ************///////////////////
    public Employee auth(Employee employee) {
        String sql = "SELECT employee_id, first_name, last_name, employee_type FROM employees WHERE employee_login = ? AND employee_password = ?";
        RowMapper<Employee> rowMapper = new BeanPropertyRowMapper<>(Employee.class);
        List<Employee> employees = template.query(sql, rowMapper, employee.getEmployee_login(), employee.getEmployee_password());

        if(employees.size() > 0){
            return employees.get(0);
        }
        return null;
    }
}
