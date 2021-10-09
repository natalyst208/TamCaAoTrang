package dictionary;

import utility.ProjectConfig;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DictionaryManagement extends Dictionary {

  private static DictionaryManagement dictionaryManagement;

  private DictionaryManagement() {

  }

  public static DictionaryManagement getDictionaryManagement() {
    if (dictionaryManagement == null) {
      dictionaryManagement = new DictionaryManagement();
      dictionaryManagement.loadWordFromDatabase();
    }
    return dictionaryManagement;
  }

  public void loadWordFromDatabase() {
    System.out.println("Load dictionary from ...");
    DicSQLite.connectDatabase(ProjectConfig.databasePath);
    System.out.println("Loaded!");
  }

  public String LookupDic(String word) {
    word = word.replaceAll("'", "''");
    String query = "SELECT * FROM " + ProjectConfig.databaseName
        + " WHERE word LIKE " + "'" + word + "'";
    ResultSet resultSet = DicSQLite.executeQuery(query);
    try {
      return resultSet.getString("html");
    } catch (SQLException e) {
      return "<h1>Không tìm thấy dữ liệu.</h1>";
    }
  }

  public ResultSet SearchDic(String word) {
    word = word.replaceAll("'", "''");
    String query = "SELECT * FROM " + ProjectConfig.databaseName
        + " WHERE word LIKE " + "'" + word + "%'";
    return DicSQLite.executeQuery(query);
  }

  public boolean saveWord(Word word) {
    if (checkbeContained(word.word)) {
      return false;
    }
    System.out.println(word.word);
    String query = "INSERT INTO " + ProjectConfig.databaseName
        + "(id, word, html, favorite)"
        + "VALUES(?,?,?, 0)";
    int numberRows = DicSQLite.getMaxID();
    try {
      PreparedStatement preparedStatement;
      preparedStatement = DicSQLite.connection.prepareStatement(query);
      preparedStatement.setInt(1, numberRows + 1);
      preparedStatement.setString(2, word.word);
      preparedStatement.setString(3, word.html);
      preparedStatement.executeUpdate();
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return true;
  }

  public void editWord(String word, String newHtml) {
    String query = "UPDATE " + ProjectConfig.databaseName
        + " SET html = ?"
        + " WHERE word = ?";
    try {
      PreparedStatement preparedStatement;
      preparedStatement = DicSQLite.connection.prepareStatement(query);
      preparedStatement.setString(1, newHtml);
      preparedStatement.setString(2, word);
      preparedStatement.executeUpdate();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  public void deleteWord(String word) {
    word = word.replaceAll("'", "''");
    String query = "DELETE FROM " + ProjectConfig.databaseName
        + " WHERE word LIKE " + "'" + word + "'";
    try {
      PreparedStatement preparedStatement;
      preparedStatement = DicSQLite.connection.prepareStatement(query);
      preparedStatement.executeUpdate();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  public int checkbeFavorited(String word) {
    word = word.replaceAll("'", "''");
    String query = "SELECT favorite FROM " + ProjectConfig.databaseName
        + " WHERE word LIKE " + "'" + word + "'";
    try {
      return DicSQLite.executeQuery(query).getInt("favorite");
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return 0;
  }


  public void setFavoriteStatus(String word, int status) {
    word = word.replaceAll("'", "''");
    String query = "UPDATE " + ProjectConfig.databaseName
        + " SET favorite" + " = " + status
        + " WHERE word" + " IS " + "'" + word + "'";
    try {
      PreparedStatement preparedStatement;
      preparedStatement = DicSQLite.connection.prepareStatement(query);
      preparedStatement.executeUpdate();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  public ResultSet getFavorite() {
    String query = "SELECT word FROM " + ProjectConfig.databaseName
        + " WHERE favorite = 1";
    return DicSQLite.executeQuery(query);
  }

  public boolean checkbeContained(String word) {
    String result = LookupDic(word);
    return !result.equals("<h1>Không tìm thấy dữ liệu.</h1>");
  }

}
