package couponjo.beans;

import java.time.LocalDate;
import java.util.Date;

public class Coupon {
    private int id;
    private int companyId;
    private Category category;
    private String title;
    private String description;
    private Date start_date;
    private Date end_date;
    private int amount;
    private double price;
    private String image;


    public Coupon(int companyId, Category category, String title, String description, Date start_date, Date end_date, int amount, double price, String image) {
        this.companyId = companyId;
        this.category = category;
        this.title = title;
        this.description = description;
        this.start_date = start_date;
        this.end_date = end_date;
        this.amount = amount;
        this.price = price;
        this.image = image;
    }
    public Coupon(int id, int companyId, Category category, String title, String description, Date start_date, Date end_date, int amount, double price, String image) {
        this(companyId,category,title,description,start_date,end_date,amount,price,image);
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public int getCompanyId() {
        return companyId;
    }

    public Category getCategory() {
        return category;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public Date getStart_date() {
        return start_date;
    }

    public Date getEnd_date() {
        return end_date;
    }

    public int getAmount() {
        return amount;
    }

    public double getPrice() {
        return price;
    }

    public String getImage() {
        return image;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setCompanyId(int companyId) {
        this.companyId = companyId;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setStart_date(Date start_date) {
        this.start_date = start_date;
    }

    public void setEnd_date(Date end_date) {
        this.end_date = end_date;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "Coupon{" +
                "id=" + id +
                ", companyId=" + companyId +
                ", category=" + category +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", start_date=" + start_date +
                ", end_date=" + end_date +
                ", amount=" + amount +
                ", price=" + price +
                ", image='" + image + '\'' +
                '}';
    }

    public static Coupon createCoupon(int companyid){
        return new Coupon(companyid,Category.ELECTRICITY,"TestTitle"+(int)(Math.random()*40+1),"testDescription", java.sql.Date.valueOf(LocalDate.now().plusDays(2)),
                java.sql.Date.valueOf(LocalDate.now().plusDays(2)),30,45.7,"none");
    }
}
