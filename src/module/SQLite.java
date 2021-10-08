package module;

import utility.ProjectConfig;

import java.sql.*;

/*
https://www.tutorialspoint.com/sqlite/sqlite_java.htm
 */
public class SQLite {
    private static SQLite DicSQLite;
    public Connection connection = null;

    private SQLite() {

    }

    public static SQLite getSQLite() {
        if (DicSQLite == null) {
            DicSQLite = new SQLite();
        }
        return DicSQLite;
    }

    /*
    https://www.sqlitetutorial.net/sqlite-java/sqlite-jdbc-driver/
     */
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
            statement = DicSQLite.connection.createStatement();
        } catch (SQLException e) {
            System.out.println("Creat statement unsucessfully");
            e.printStackTrace();
        }
        return statement;
    }

    public ResultSet executeQuery(String query) {
        Statement statement = DicSQLite.creatStatement();
        ResultSet resultSet = null;
        try {
            resultSet = statement.executeQuery(query);
        } catch (SQLException e) {
            System.out.println("Execute query unsuccessfully: " + query);
            e.printStackTrace();
        }
        return resultSet;
    }

    public int getMaxID() {
        String query = "SELECT MAX(id) AS max FROM " + ProjectConfig.databaseName;
        try {
            ResultSet resultSet = DicSQLite.executeQuery(query);
            return resultSet.getInt("max");
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }
}
