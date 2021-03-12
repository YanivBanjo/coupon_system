package couponjo.utils;

import couponjo.db.ConnectionPool;

import java.sql.*;
import java.util.Map;

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

    public static void runQuery(String sql, Map<Integer, Object> map) throws SQLException {
        Connection connection = null;
        try {
            // STEP 2 - taking Connection from connection pool
            connection = ConnectionPool.getInstance().getConnection();
            // STEP 3 - Run your SQL instruction
            PreparedStatement statement = connection.prepareStatement(sql);

            for (Map.Entry<Integer, Object> entry : map.entrySet()) {
                int key = entry.getKey();
                Object value = entry.getValue();

                if (entry.getValue() instanceof Integer) {
                    statement.setInt(key, (int) value);
                } else if (entry.getValue() instanceof String) {
                    statement.setString(key, String.valueOf(value));
                } else if (entry.getValue() instanceof Date) {
                    statement.setDate(key, (Date) value);
                } else if (entry.getValue() instanceof Float) {
                    statement.setFloat(key, (Float) value);
                } else if (entry.getValue() instanceof Double) {
                    statement.setDouble(key, (Double) value);
                }
            }
            statement.execute();
        } catch (Exception e) {
            Print.exception(e.getMessage());

        } finally {
            // STEP 5 - return Connection to connection pool
            ConnectionPool.getInstance().returnConnection(connection);
        }
    }

    public static ResultSet runQueryWithResult(String sql, Map<Integer, Object> map) throws SQLException {
        Connection connection = null;
        ResultSet resultSet = null;
        try {
            // STEP 2 - taking Connection from connection pool
            connection = ConnectionPool.getInstance().getConnection();
            // STEP 3 - Run your SQL instruction
            PreparedStatement statement = connection.prepareStatement(sql);
            for (Map.Entry<Integer, Object> entry : map.entrySet()) {
                int key = entry.getKey();
                Object value = entry.getValue();

                if (entry.getValue() instanceof Integer) {
                    statement.setInt(key, (int) value);
                } else if (entry.getValue() instanceof String) {
                    statement.setString(key, String.valueOf(value));
                } else if (entry.getValue() instanceof Date) {
                    statement.setDate(key, (Date) value);
                } else if (entry.getValue() instanceof Float) {
                    statement.setFloat(key, (Float) value);
                } else if (entry.getValue() instanceof Double) {
                    statement.setDouble(key, (Double) value);
                }
            }
            resultSet = statement.executeQuery();
        } catch (Exception e) {
            Print.exception(e.getMessage());

        } finally {
            // STEP 5 - return Connection to connection pool
            ConnectionPool.getInstance().returnConnection(connection);
        }
        return resultSet;
    }
}
