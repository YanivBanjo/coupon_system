package couponjo.beans;

import couponjo.utils.PasswordUtils;

import java.util.List;

public class Customer {
    private int id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private List<Coupon> coupons;


    public Customer(String firstName, String lastName, String email, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
    }

    public Customer(int id, String firstName, String lastName, String email, String password) {
        this(firstName, lastName, email, password);
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Coupon> getCoupons() {
        return coupons;
    }

    public void setCoupons(List<Coupon> coupons) {
        this.coupons = coupons;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", coupons=" + coupons +
                '}';
    }

    public static Customer createCustomer(String name, String family) {
        return new Customer(name, family, name + "." + family + "@gmail.com", PasswordUtils.generate(10));
    }
    public static void printCustomerHeader() {
        System.out.println(String.format("%5s %5s %10s %10s %10s %10s %22s %15s %10s %10s %10s", "Id", "|", "First Name", "|", "Last Name", "|", "Email", "|", "Password", "|", "Coupons"));
        System.out.println(String.format("%s", "----------------------------------------------------------------------------------------------------------------------------------------"));
    }
    public static void printCustomerDetails(Customer c) {
        System.out.println(String.format("%5s %5s %10s %10s %10s %10s %22s %15s %10s %10s %10s", c.getId(), "|", c.getFirstName(), "|", c.getLastName(), "|", c.getEmail(), "|", c.getPassword(), "|", c.getCoupons()));
    }

    public static void printCustomer (Customer customer) {
        printCustomerHeader();
        printCustomerDetails(customer);
    }

    public static void printCustomers (List<Customer> customers) {
        printCustomerHeader();
        customers.forEach(customer -> printCustomerDetails(customer));
    }
}
