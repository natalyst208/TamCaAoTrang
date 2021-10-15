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
      dictionaryManagement.loadDataFromDatabase();
    }
    return dictionaryManagement;
  }

  public void loadDataFromDatabase() {
    System.out.println("Load dictionary from ...");
    DicSQLite.connectDatabase(ProjectConfig.databasePath);
    System.out.println("Loaded!");
  }

  public String LookupDic(String word) {
    word = word.replaceAll("'", "''");
    String query =
        "SELECT * FROM " + ProjectConfig.databaseName + " WHERE word LIKE " + "'" + word + "'";
    ResultSet resultSet = DicSQLite.executeQuery(query);
    try {
      return resultSet.getString("html");
    } catch (SQLException e) {
      return "<h1>Không tìm thấy dữ liệu.</h1>";
    }
  }

  public ResultSet SearchDic(String word) {
    word = word.replaceAll("'", "''");
    String query =
        "SELECT * FROM " + ProjectConfig.databaseName + " WHERE word LIKE " + "'" + word + "%'";
    return DicSQLite.executeQuery(query);
  }

  public boolean saveWord(Word word) {
    if (checkbeContained(word.word)) {
      return false;
    }
    System.out.println(word.word);
    String query = "INSERT INTO " + ProjectConfig.databaseName + "(id, word, html, favorite)"
        + "VALUES(?,?,?, 0)";
    int numberRows = DicSQLite.getMaxID();
    try {
      PreparedStatement PreStatement;
      PreStatement = DicSQLite.connection.prepareStatement(query);
      PreStatement.setInt(1, numberRows + 1);
      PreStatement.setString(2, word.word);
      PreStatement.setString(3, word.html);
      PreStatement.executeUpdate();
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return true;
  }

  public void editWord(String word, String newHtml) {
    String query = "UPDATE " + ProjectConfig.databaseName + " SET html = ?" + " WHERE word = ?";
    try {
      PreparedStatement PreStatement;
      PreStatement = DicSQLite.connection.prepareStatement(query);
      PreStatement.setString(1, newHtml);
      PreStatement.setString(2, word);
      PreStatement.executeUpdate();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  public void deleteWord(String word) {
    word = word.replaceAll("'", "''");
    String query =
        "DELETE FROM " + ProjectConfig.databaseName + " WHERE word LIKE " + "'" + word + "'";
    try {
      PreparedStatement PreStatement;
      PreStatement = DicSQLite.connection.prepareStatement(query);
      /*
      thực hiện truy vấn
       */
      PreStatement.executeUpdate();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  public int checkbeFavorited(String word) {
    word = word.replaceAll("'", "''");
    String query =
        "SELECT favorite FROM " + ProjectConfig.databaseName + " WHERE word LIKE " + "'" + word
            + "'";
    try {
      return DicSQLite.executeQuery(query).getInt("favorite");
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return 0;
  }


  public void setFavorSituation(String word, int status) {
    word = word.replaceAll("'", "''");
    String query =
        "UPDATE " + ProjectConfig.databaseName + " SET favorite" + " = " + status + " WHERE word"
            + " IS " + "'" + word + "'";
    try {
      PreparedStatement PreStatement;
      PreStatement = DicSQLite.connection.prepareStatement(query);
      PreStatement.executeUpdate();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  public ResultSet getFavor() {
    String query = "SELECT word FROM " + ProjectConfig.databaseName + " WHERE favorite = 1";
    return DicSQLite.executeQuery(query);
  }

  public boolean checkbeContained(String word) {
    String result = LookupDic(word);
    return !result.equals("<h1>Không tìm thấy dữ liệu.</h1>");
  }

}
