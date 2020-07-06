
import CommandsProcessing.Commands;
import PersonData.AllException;
import CommandsProcessing.Receiver;
import java.io.IOException;
import java.sql.*;

public class Server {

    public static void main(String args[]) throws SQLException {

        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("PostgreSQL JDBC Driver is not found. Include it in your library path ");
            e.printStackTrace();
            return;
        }
        System.out.println("PostgreSQL JDBC Driver successfully connected");
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(Receiver.URL, Receiver.USERNAME, Receiver.PASSWORD);
        } catch (SQLException e) {
            System.out.println("Connection Failed");
            e.printStackTrace();
            return;
        }
        if (connection != null) {
            System.out.println("You successfully connected to database now");
        } else {
            System.out.println("Failed to make connection to database");
        }
        Statement statement = connection.createStatement();
        String sqlNewTable = "CREATE TABLE users " +
                "(login VARCHAR(50), " +
                "password VARCHAR(50));";
        String sqlCommand = "CREATE TABLE PersonLists " +
                "(id  SERIAL PRIMARY KEY NOT NULL, " +
                "creationDate DATE, " +
                "name VARCHAR(50), " +
                "area FLOAT, " +
                "rooms INTEGER, " +
                "furnish VARCHAR(50), " +
                "transport VARCHAR(50), " +
                "view VARCHAR(50), " +
                "x FLOAT, " +
                "y FLOAT, " +
                "house_name VARCHAR(50), " +
                "house_age INTEGER, " +
                "house_lifts INTEGER, " +
                "login VARCHAR(50), " +
                "password VARCHAR(50));";
        String delete = "DROP TABLE PersonLists;";
        String delete1 = "DROP TABLE users;";
        //statement.executeUpdate(delete);
        //statement.executeUpdate(delete1);
       // statement.executeUpdate(sqlNewTable);
       // statement.executeUpdate(sqlCommand);
        Commands commands = new Commands();
        commands.loadDB();
        new Receiver();
}}