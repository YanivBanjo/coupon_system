package couponjo.beans;

import couponjo.utils.PasswordUtils;
import java.util.ArrayList;
import java.util.List;

public class Company {
    private int id;
    private String name;
    private String email;
    private String password;
    private List<Coupon> coupons;

    public Company(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
        coupons = new ArrayList<>();
    }

    public Company(int id, String name, String email, String password) {
        this(name, email, password);
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
        return "Company{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", coupons=" + coupons +
                '}';
    }

    public static Company createCompany(String name) {
        return new Company(name, name + "@gmail.co.il", PasswordUtils.generate(10));
    }
    public static void printCompanyHeader() {
        System.out.println(String.format("%10s %10s %10s %10s %20s %15s %10s %10s %10s", "Id", "|", "Name", "|", "Email", "|", "Password", "|", "coupons"));
        System.out.println(String.format("%s", "---------------------------------------------------------------------------------------------------------------------"));
    }
    public static void printCompanyDetails(Company c) {
        System.out.println(String.format("%10s %10s %10s %10s %20s %15s %10s %10s %10s", c.getId(), "|", c.getName(), "|", c.getEmail(), "|", c.getPassword(), "|", c.getCoupons()));
    }

    public static void printCompany (Company company) {
        printCompanyHeader();
        printCompanyDetails(company);
    }

    public static void printCompanies(List<Company> companies) {
        printCompanyHeader();
        companies.forEach(company -> printCompanyDetails(company));
    }
}
