package banking;

import org.sqlite.SQLiteDataSource;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class Database {
    static String fullUrl;
    static String tableName;
    static List<String> databases = new ArrayList<>();


    public void addIncome(String cardNumber, double income) {
        SQLiteDataSource dataSource = new SQLiteDataSource();
        dataSource.setUrl(fullUrl);
        String updateStatement = "UPDATE " + tableName + " SET balance = balance + " +
                income + " WHERE number = " + cardNumber;

        try (Connection connection = dataSource.getConnection()) {
            try (Statement statement = connection.createStatement()) {
                statement.executeUpdate(updateStatement);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public boolean checkIfExists(String cardNumber) {
        SQLiteDataSource dataSource = new SQLiteDataSource();
        dataSource.setUrl(fullUrl);
        String queryStatement = "SELECT 1 FROM " + tableName + " WHERE number = " +
                cardNumber;

        try (Connection connection = dataSource.getConnection()) {
            try (Statement statement = connection.createStatement()) {
                try (ResultSet resultSet = statement.executeQuery(queryStatement)) {
                    if (resultSet.next()) {
                        return true;
                    }
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }


    public String doTransfer(String cardNumberOut, String cardNumberIn, double amount) {
        if (!checkIfExists(cardNumberIn)) return "card not found";

        if (getBalanceByCard(cardNumberOut) > amount) {
            SQLiteDataSource dataSource = new SQLiteDataSource();
            dataSource.setUrl(fullUrl);
            String updateStatementIn = "UPDATE " + tableName + " SET balance = balance + " +
                    amount + " WHERE number = " + cardNumberIn;
            String updateStatementOut = "UPDATE " + tableName + " SET balance = balance - " +
                    amount + " WHERE number = " + cardNumberOut;

            try (Connection connection = dataSource.getConnection()) {
                try (Statement statement = connection.createStatement()) {
                    statement.executeUpdate(updateStatementIn);
                    statement.executeUpdate(updateStatementOut);
                    return "success";
                }

            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return "not enough money";
    }

    public void closeAccount(String cardNumber) {
        SQLiteDataSource dataSource = new SQLiteDataSource();
        dataSource.setUrl(fullUrl);
        String updateStatement = "DELETE FROM " + tableName + " WHERE number = " + cardNumber;

        try (Connection connection = dataSource.getConnection()) {
            try (Statement statement = connection.createStatement()) {
                statement.executeUpdate(updateStatement);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }


    public String getPinByCard(String cardNumber) {

        SQLiteDataSource dataSource = new SQLiteDataSource();
        dataSource.setUrl(fullUrl);
        String queryStatement = "SELECT pin FROM " + tableName + " WHERE number=" +
                cardNumber;

        try (Connection connection = dataSource.getConnection()) {
            try (Statement statement = connection.createStatement()) {
                try (ResultSet resultSet = statement.executeQuery(queryStatement)) {
                    while (resultSet.next()) {
                        return resultSet.getString("pin");
                    }
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return "not found";
    }


    public int getBalanceByCard(String cardNumber) {

        SQLiteDataSource dataSource = new SQLiteDataSource();
        dataSource.setUrl(fullUrl);
        String queryStatement = "SELECT * FROM " + tableName + "  WHERE number=" +
                cardNumber;

        try (Connection connection = dataSource.getConnection()) {
            try (Statement statement = connection.createStatement()) {
                try (ResultSet resultSet = statement.executeQuery(queryStatement)) {
                    while (resultSet.next()) {
                        return resultSet.getInt("balance");
                    }
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return 0;
    }

    public void insertRow(String cardNumber, String pin) {
        SQLiteDataSource dataSource = new SQLiteDataSource();
        dataSource.setUrl(fullUrl);
        String updateStatement = "INSERT INTO " + tableName + "(number, pin) VALUES(" +
                cardNumber + ", " + pin + ")";

        try (Connection connection = dataSource.getConnection()) {
            try (Statement statement = connection.createStatement()) {
                statement.executeUpdate(updateStatement);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }


    public static void createTable() {
        SQLiteDataSource dataSource = new SQLiteDataSource();
        dataSource.setUrl(fullUrl);

        try (Connection con = dataSource.getConnection()) {
            try (Statement statement = con.createStatement()) {

                statement.executeUpdate("CREATE TABLE IF NOT EXISTS card(" +
                        "id INTEGER PRIMARY KEY AUTOINCREMENT, number TEXT, pin TEXT, balance INTEGER DEFAULT 0)");

                tableName = "card";
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }


    public static void createDatabase(String fileName) {
        if (!databases.contains(fileName)) {
            fullUrl = "jdbc:sqlite:" + fileName;

            try (Connection con = DriverManager.getConnection(fullUrl)) {
                DatabaseMetaData meta = con.getMetaData();
                databases.add(fileName);
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        } else {
            fullUrl = "jdbc:sqlite:" + fileName;
        }
        createTable();
    }

}
