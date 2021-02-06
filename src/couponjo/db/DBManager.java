package couponjo.db;

import couponjo.utils.DBUtils;

import java.sql.SQLException;

public class DBManager {

    public static final String url = "jdbc:mysql://localhost:3306?createDatabaseIfNotExist=FALSE&useTimezone=TRUE&serverTimezone=UTC";
    public static final String username = "root";
    public static final String password = "Anutk120!";

    private static final String CREATE_SCHEMA = "CREATE SCHEMA `couponjo`";
    private static final String DROP_SCHEMA = "DROP SCHEMA `couponjo`";
    private static final String CREATE_TABLE_COMPANIES = "CREATE TABLE `couponjo`.`companies` (\n" +
            "  `id` INT NOT NULL AUTO_INCREMENT,\n" +
            "  `name` VARCHAR(45) NOT NULL,\n" +
            "  `email` VARCHAR(45) NOT NULL,\n" +
            "  `password` VARCHAR(45) NOT NULL,\n" +
            "   PRIMARY KEY (`id`))";
    private static final String CREATE_TABLE_CUSTOMERS = "CREATE TABLE `couponjo`.`customers` (\n" +
            "  `id` INT NOT NULL AUTO_INCREMENT,\n" +
            "  `first_name` VARCHAR(45) NOT NULL,\n" +
            "  `last_name` VARCHAR(45) NOT NULL,\n" +
            "  `email` VARCHAR(45) NOT NULL,\n" +
            "  `password` VARCHAR(45) NOT NULL,\n" +
            "   PRIMARY KEY (`id`))";

    private static final String CREATE_TABLE_CATEGORIES =  "CREATE TABLE `couponjo`.`categories` (\n" +
            "  `id` INT NOT NULL AUTO_INCREMENT,\n" +
            "  `name` VARCHAR(45) NOT NULL,\n" +
            "   PRIMARY KEY (`id`));";

    private static final String CREATE_TABLE_COUPONS =  "CREATE TABLE `couponjo`.`coupons` (\n" +
            "  `id` INT NOT NULL AUTO_INCREMENT,\n" +
            "  `company_id` INT NOT NULL,\n" +
            "  `category_id` INT NOT NULL,\n" +
            "  `title` VARCHAR(45) NOT NULL,\n" +
            "  `description` VARCHAR(45) NOT NULL,\n" +
            "  `start_date` DATE NOT NULL,\n" +
            "  `end_date` DATE NOT NULL,\n" +
            "  `amount` INT NOT NULL,\n" +
            "  `price` DOUBLE NOT NULL,\n" +
            "  `image` VARCHAR(45) NOT NULL,\n" +
            "   PRIMARY KEY (`id`),\n" +
            "   INDEX `id_idx` (`company_id` ASC) VISIBLE,\n" +
            "   INDEX `id_idx1` (`category_id` ASC) VISIBLE,\n" +
            "   CONSTRAINT `company_id`\n" +
            "   FOREIGN KEY (`company_id`)\n" +
            "   REFERENCES `couponjo`.`companies` (`id`)\n" +
            "   ON DELETE NO ACTION\n" +
            "   ON UPDATE NO ACTION,\n" +
            "   CONSTRAINT `category_id`\n" +
            "   FOREIGN KEY (`category_id`)\n" +
            "   REFERENCES `couponjo`.`categories` (`id`)\n" +
            "   ON DELETE NO ACTION\n" +
            "   ON UPDATE NO ACTION);";

    private static final String CREATE_TABLE_CUSTOMERS_COUPONS =  "CREATE TABLE `couponjo`.`customers_coupons` (\n" +
            "   `customer_id` INT NOT NULL,\n" +
            "   `coupon_id` INT NOT NULL,\n" +
            "    PRIMARY KEY (`customer_id`, `coupon_id`),\n" +
            "    INDEX `coupon_id_idx` (`coupon_id` ASC) VISIBLE,\n" +
            "    CONSTRAINT `customer_id`\n" +
            "    FOREIGN KEY (`customer_id`)\n" +
            "    REFERENCES `couponjo`.`customers` (`id`)\n" +
            "    ON DELETE NO ACTION\n" +
            "    ON UPDATE NO ACTION,\n" +
            "    CONSTRAINT `coupon_id`\n" +
            "    FOREIGN KEY (`coupon_id`)\n" +
            "    REFERENCES `couponjo`.`coupons` (`id`)\n" +
            "    ON DELETE NO ACTION\n" +
            "    ON UPDATE NO ACTION);";

    public static void createSchema() throws SQLException {
        DBUtils.runQuery(CREATE_SCHEMA);
    }

    public static void dropSchema() throws SQLException {
        DBUtils.runQuery(DROP_SCHEMA);
    }

    public static void createTables() throws SQLException {
        DBUtils.runQuery(CREATE_TABLE_CATEGORIES);
        DBUtils.runQuery(CREATE_TABLE_COMPANIES);
        DBUtils.runQuery(CREATE_TABLE_COUPONS);
        DBUtils.runQuery(CREATE_TABLE_CUSTOMERS);
        DBUtils.runQuery(CREATE_TABLE_CUSTOMERS_COUPONS);
    }

}
