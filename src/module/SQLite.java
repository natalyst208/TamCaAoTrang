package module;

import utility.ProjectConfig;

import java.sql.*;

public class SQLite {
    private static SQLite mySQLite;
    public Connection connection = null;

    private SQLite() {

    }

    public static SQLite getSQLite() {
        if (mySQLite == null) {
            mySQLite = new SQLite();
        }
        return mySQLite;
    }

    public void connectDatabase(String filePath) {
        try {
            this.connection = DriverManager.getConnection(filePath);
        } catch (SQLException e) {
            System.out.println("Can't connect to database!");
            e.printStackTrace();
        }
    }

    public Statement creatStatement() {
        Statement statement  = null;
        try {
            statement = mySQLite.connection.createStatement();
        } catch (SQLException e) {
            System.out.println("Can't creat statement!");
            e.printStackTrace();
        }
        return statement;
    }

    public ResultSet executeQuery(String query) {
        Statement statement = mySQLite.creatStatement();
        ResultSet resultSet = null;
        try {
            resultSet = statement.executeQuery(query);
        } catch (SQLException e) {
            System.out.println("Can't execute query: " + query);
            e.printStackTrace();
        }
        return resultSet;
    }

    public int getMaxID() {
        String query = "SELECT MAX(id) AS max FROM " + ProjectConfig.databaseName;
        try {
            ResultSet resultSet = mySQLite.executeQuery(query);
            return resultSet.getInt("max");
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }
}
