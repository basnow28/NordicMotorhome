package kea.nordicmotorhome.model;

import org.springframework.stereotype.Component;


import javax.persistence.Id;

@Component
public class Employee extends Person{
    @Id
    private int employee_id;
    private String employee_type;
    private String employee_login;
    private String employee_password;

    public void setAllAttributesToEmpty(){
        this.employee_id = 0;
        super.setFirst_name(null);
        super.setLast_name(null);
        this.employee_type=null;
        this.employee_login=null;
        this.employee_password=null;
    }

    public int getEmployee_id() {
        return employee_id;
    }

    public void setEmployee_id(int employee_id) {
        this.employee_id = employee_id;
    }

    public String getEmployee_type() {
        return employee_type;
    }

    public void setEmployee_type(String employee_type) {
        this.employee_type = employee_type;
    }

    public String getEmployee_login() {
        return employee_login;
    }

    public void setEmployee_login(String employee_login) {
        this.employee_login = employee_login;
    }

    public String getEmployee_password() {
        return employee_password;
    }

    public void setEmployee_password(String employee_password) {
        this.employee_password = employee_password;
    }
}
