package display.graphic.intro;

import display.graphic.texttranslate.TextTranslateController;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import module.LevenshteinDistance;
import module.TextToSpeech;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import dictionary.DictionaryManagement;
import display.dialog.ConfirmDialog;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.*;
import javafx.scene.web.WebView;
import display.graphic.addword.AddWordController;
import display.graphic.favoriteword.FavoriteController;
import display.graphic.editword.EditWordController;
import utility.ProjectConfig;
import utility.Utils;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class IntroController implements Initializable {

  protected DictionaryManagement myDictionary = DictionaryManagement.getDictionaryManagement();

  @FXML
  private BorderPane borderPane;

  @FXML
  public TextField searchTextField;

  @FXML
  private WebView wordExplainView;

  @FXML
  private FontAwesomeIconView speakerIcon;

  @FXML
  private ListView<String> listView;

  @FXML
  private VBox wordExplainScene;

  @FXML
  private ToolBar toolBar;

  @FXML
  private FontAwesomeIconView favorite;

  public static String currentWord;

  @FXML

  public void searchWord() {
    currentWord = searchTextField.getText();
    if (myDictionary.checkbeContained(currentWord)) {
      setWordExplainScene();
    } else {
      setDidYouMeanScene();
    }
  }

  public void pronounceWord() {
    TextToSpeech.speak(currentWord);
  }

  public void changeFavoriteStatus() {
    if (favorite.getGlyphName().equals("HEART")) {
      favorite.setGlyphName("HEART_ALT");
      myDictionary.setFavoriteStatus(currentWord, 0);
    } else {
      favorite.setGlyphName("HEART");
      myDictionary.setFavoriteStatus(currentWord, 1);
    }
  }

  public void deleteWord() throws SQLException {
    ConfirmDialog deleteConfirm = new ConfirmDialog();
    boolean isConfirm = deleteConfirm.show("Delete", "Are you sure want to delete this word?");
    if (isConfirm) {
      wordExplainView.getEngine()
          .loadContent("<h1>Không tìm thấy dữ liệu.</h1>");
      myDictionary.deleteWord(currentWord);
      printSuggestedWords();
    }
  }

  public void printSuggestedWords() throws SQLException {
    listView.getItems().clear();
    ResultSet resultSet = myDictionary.SearchDic(searchTextField.getText());
    while (resultSet.next()) {
      listView.getItems().add(resultSet.getString("word"));
    }
  }

  public void getSelectedWordInSuggestedList() {
    ObservableList<String> selectedIndices = listView.getSelectionModel().getSelectedItems();
    searchTextField.setText(selectedIndices.get(0));
    searchWord();
  }

  private void setDidYouMeanScene() {
    StringBuilder html = new StringBuilder("<h1>Không tìm thấy dữ liệu.</h1>");
    html.append("<h1>Có phải từ bạn muốn tìm kiếm là: </h1>");
    String[] result = LevenshteinDistance.getTopScore(currentWord);
    for (String word : result) {
      if (word == null) {
        continue;
      }
      html.append("<h1>").append(word).append("</h1>");
    }
    html = new StringBuilder(
        "<body style=" + "\"background-color:#FFFFFFFF;" + "\">" + html + "</body>");
    wordExplainView.getEngine().loadContent(html.toString());
    borderPane.setCenter(wordExplainScene);
  }

  public void setWordExplainScene() {
    String htmlOfSearchWord = myDictionary.LookupDic(currentWord);
    htmlOfSearchWord =
        "<body style=" + "\"background-color:#FFFFFFFF;" + "\">" + htmlOfSearchWord + "</body>";
    int checkbeFavorited = myDictionary.checkbeFavorited(currentWord);
    htmlOfSearchWord = Utils.setNotEditable(htmlOfSearchWord);
    wordExplainView.getEngine().loadContent(htmlOfSearchWord);
    if (checkbeFavorited == 0) {
      favorite.setGlyphName("HEART_ALT");
    } else {
      favorite.setGlyphName("HEART");
    }
    speakerIcon.setVisible(true);
    favorite.setVisible(true);
    borderPane.setCenter(wordExplainScene);
  }

  public void setAddWordScene() throws IOException {
    ProjectConfig.introStage.setScene(AddWordController.getScene());
  }

  public void setEditWordScene() throws IOException {
    ProjectConfig.introStage.setScene(EditWordController.getScene());
  }

  public void setGoogleTranslateScene() throws IOException {
    ProjectConfig.introStage.setScene(TextTranslateController.getScene());
  }

  public void setFavoriteWordsScene() throws IOException {
    ProjectConfig.introStage.setScene(FavoriteController.getScene());
  }

  public static Scene getScene() throws IOException {
    Parent root = FXMLLoader.load(IntroController.class.getResource("Intro.fxml"));
    return new Scene(root);
  }

  public void EnglishVietnameseVersion() throws IOException {
    ProjectConfig.databaseName = "av";
    ProjectConfig.introStage.setScene(IntroController.getScene());
  }

  public void AnhVietVersion() throws IOException {
    ProjectConfig.databaseName = "va";
    ProjectConfig.introStage.setScene(IntroController.getScene());
  }

  public void About(ActionEvent event)//hàm thông tin
  {
    Alert alert=new Alert(Alert.AlertType.INFORMATION);
    alert.setTitle("Information");
    alert.setHeaderText(null);
    alert.setContentText("This Dictionary was written by Nguyen Anh Tuan & Do Thuy Linh & Le Thai Son");
    alert.show();
  }

  public void Quit(ActionEvent event) {//ham đóng ứng dụng
    Platform.exit();
    System.exit(0);
  }

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    try {
      printSuggestedWords();
    } catch (SQLException e) {
      e.printStackTrace();
    }
    if (currentWord == null) {
    } else {
      setWordExplainScene();
    }
  }
}