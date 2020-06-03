package kea.nordicmotorhome.model;

import org.springframework.stereotype.Component;




@Component
public class Customer extends Person{
    private int customer_id;
    private String date_of_birth;
    private String phone_number;
    private String email;
    private String driver_licence_number;
    private String street_name;
    private String house_number;
    private String postcode;
    private String city;
    private String country;
    private int address_id;

    @Override
    public String toString() {
        return "Customer{" +
                "customer_id=" + customer_id +
                ", first_name='" + super.getFirst_name() + '\'' +
                ", last_name='" + super.getLast_name() + '\'' +
                ", date_of_birth='" + date_of_birth + '\'' +
                ", phone_number='" + phone_number + '\'' +
                ", email='" + email + '\'' +
                ", driver_licence_number='" + driver_licence_number + '\'' +
                ", street_name='" + street_name + '\'' +
                ", house_number='" + house_number + '\'' +
                ", postcode=" + postcode +
                ", city='" + city + '\'' +
                ", country='" + country + '\'' +
                '}';
    }

    public int getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(int customer_id) {
        this.customer_id = customer_id;
    }

    public String getDate_of_birth() {
        return date_of_birth;
    }

    public void setDate_of_birth(String date_of_birth) {
        this.date_of_birth = date_of_birth;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDriver_licence_number() {
        return driver_licence_number;
    }

    public void setDriver_licence_number(String driver_licence_number) {
        this.driver_licence_number = driver_licence_number;
    }

    public String getStreet_name() {
        return street_name;
    }

    public void setStreet_name(String street_name) {
        this.street_name = street_name;
    }

    public String getHouse_number() {
        return house_number;
    }

    public void setHouse_number(String house_number) {
        this.house_number = house_number;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public int getAddress_id() {
        return address_id;
    }

    public void setAddress_id(int address_id) {
        this.address_id = address_id;
    }
}
