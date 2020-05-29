package kea.nordicmotorhome.Repository;

import kea.nordicmotorhome.Model.Employee;
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

    public Employee auth(Employee employee) {
        String sql = "SELECT employee_id, employee_first_name, employee_last_name, employee_type FROM employees WHERE employee_login = ? AND employee_password = ?";
        RowMapper<Employee> rowMapper = new BeanPropertyRowMapper<>(Employee.class);
        List<Employee> employees = template.query(sql, rowMapper, employee.getEmployee_login(), employee.getEmployee_password());

        if(employees.size() > 0){
            return employees.get(0);
        }
        return null;
    }
}
