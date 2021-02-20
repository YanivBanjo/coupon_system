package couponjo.utils;

import couponjo.db.ConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DBUtils {
    public static void runQuery(String sql) throws SQLException {
        Connection connection = null;
        try {
            // STEP 2 - taking Connection from connection pool
            connection = ConnectionPool.getInstance().getConnection();
            // STEP 3 - Run your SQL instruction
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.executeUpdate();
        } catch (Exception e) {
            Print.exception(e.getMessage());

        } finally {
            // STEP 5 - return Connection to connection pool
            ConnectionPool.getInstance().returnConnection(connection);
        }
    }
}
