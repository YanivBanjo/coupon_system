package couponjo.db;

import couponjo.beans.Category;
import couponjo.utils.DBUtils;
import couponjo.utils.PasswordUtils;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Map;

public class DBInit {
    //read from json file
    //image should be BLOB

    private static String[] companies = {"Coca_Cola", "Tnuva", "Strauss", "IEC", "IKEA", "KFC", "BanjoPizza", "BanjoBurger", "BanjoTours"};
    private static int[] categoryId = {1, 1, 1, 2, 2, 3, 3, 3, 4};
    private static String[] title = {"1+1", "10% off", "free", "20% off", "sale", "kurkuriku", "XXL", "free potato", "Dubai all included"};


    public static void createCompanies() throws SQLException {
        for (int i = 0; i < companies.length; i++) {
            DBUtils.runQuery("INSERT INTO `couponjo`.`companies` (`name`, `email`, `password`) VALUES ('" + companies[i] + "','" + companies[i] + "@gmail.co.il','" + PasswordUtils.generate(10) + "')");
        }
    }

    public static void createCategories() throws SQLException {
        for (int i = 0; i < Category.values().length; i++) {
            DBUtils.runQuery("INSERT INTO `couponjo`.`categories` (`name`) VALUES ('" + Category.values()[i] + "')");
        }
    }

    public static void createCustomers() throws SQLException {
        Map<String, String> customers = Map.of("Hila", "Shapira", "Yaniv", "Banjo", "Shay", "Kozel", "Sami", "Levi", "Doron", "Choen", "Itzhak", "Zolan");
        for (Map.Entry m : customers.entrySet()) {
            DBUtils.runQuery("INSERT INTO `couponjo`.`customers` (`first_name`, `last_name`, `email`, `password`) VALUES ('" + m.getKey() + "', '" + m.getValue() + "', '" + m.getKey() + "." + m.getValue() + "@gmail.com', '" + PasswordUtils.generate(10) + "')");
        }
    }

    public static void createCoupons() throws SQLException {

        for (int i = 0; i < companies.length; i++) {
            int amount = (int) (Math.random() * 2) + 10;
            double price = Math.floor((Math.random() * 5 + 10) * 100) / 100;
            DBUtils.runQuery("INSERT INTO `couponjo`.`coupons` (`company_id`, `category_id`, `title`, `description`, `start_date`, `end_date`, `amount`, `price`, `image`) VALUES ('" + (i + 1) + "', '" + categoryId[i] + "', '" + title[i] + "', 'arrive quickly', '" + LocalDate.now().plusDays(2) + "', '" + LocalDate.now().plusDays(7) + "', '" + amount + "', '" + price + "', 'image')");
        }
    }

    public static void createCouponjoSystem() throws SQLException {
        DBManager.dropSchema();
        DBManager.createSchema();
        DBManager.createTables();
//        DBInit.createCompanies();
        DBInit.createCategories();
//        DBInit.createCustomers();
//        DBInit.createCoupons();
    }
}
