package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 *
 * @author Lishan
 */
public class MySQL {

    private static Connection connection;
    private static final String USERNAME = "root";
    private static final String PASSWORD = "Lishan#12345";
    private static final String DATABASE = "c&senterprises";

    private static Statement createConnection() throws Exception {
        if (connection == null) {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/" + DATABASE, USERNAME, PASSWORD);
        }

        return connection.createStatement();
    }

    //insert, update
    public static void iud(String query) {
        try {
            createConnection().executeUpdate(query);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //search
    public static ResultSet search(String query) throws Exception {
        return createConnection().executeQuery(query);
    }

    public static Connection getConnection() {
        if (connection == null) {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/" + DATABASE, USERNAME, PASSWORD);
            } catch (Exception e) {
            }
        }
        return connection;
    }

}
