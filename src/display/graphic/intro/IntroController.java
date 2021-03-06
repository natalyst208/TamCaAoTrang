package display.graphic.intro;

import display.graphic.texttranslate.TextTranslateController;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import module.LevenshteinAlthogrim;
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
  public TextField TextFieldSearch;

  @FXML
  private WebView ViewWordExplain;

  @FXML
  private FontAwesomeIconView SpeakerButtonn;

  @FXML
  private ListView<String> listView;

  @FXML
  private VBox SceneWordExplain;

  @FXML
  private ToolBar toolBar;

  @FXML
  private FontAwesomeIconView favorite;

  public static String currentWord;

  @FXML

  public void searchWord() {
    currentWord = TextFieldSearch.getText();
    if (myDictionary.checkbeContained(currentWord)) {
      setSceneExplain();
    } else {
      setthesameMean();
    }
  }

  public void pronounceWord() {
    TextToSpeech.speak(currentWord);
  }

  public void changeFavoriteStatus() {
    if (favorite.getGlyphName().equals("HEART")) {
      favorite.setGlyphName("HEART_ALT");
      myDictionary.setFavorSituation(currentWord, 0);
    } else {
      favorite.setGlyphName("HEART");
      myDictionary.setFavorSituation(currentWord, 1);
    }
  }

  public void deleteWord() throws SQLException {
    ConfirmDialog deleteNoti = new ConfirmDialog();
    boolean checkNoti = deleteNoti.show("Delete", "Do you want to delete this word?");
    if (checkNoti) {
      ViewWordExplain.getEngine()
          .loadContent("<h1>Kh??ng t??m th???y d??? li???u.</h1>");
      myDictionary.deleteWord(currentWord);
      printSuggestedWords();
    }
  }

  public void printSuggestedWords() throws SQLException {
    listView.getItems().clear();
    ResultSet resultSet = myDictionary.SearchDic(TextFieldSearch.getText());
    while (resultSet.next()) {
      listView.getItems().add(resultSet.getString("word"));
    }
  }

  public void getSelectedWordInSuggestedList() {
    ObservableList<String> selectedIndices = listView.getSelectionModel().getSelectedItems();
    TextFieldSearch.setText(selectedIndices.get(0));
    searchWord();
  }

  private void setthesameMean() {
    StringBuilder html = new StringBuilder("<h1>Kh??ng t??m th???y d??? li???u.</h1>");
    html.append("<h1>C?? ph???i t??? b???n mu???n t??m ki???m l??: </h1>");
    String[] result = LevenshteinAlthogrim.getMostSimilar(currentWord);
    for (String word : result) {
      if (word == null) {
        continue;
      }
      html.append("<h1>").append(word).append("</h1>");
    }
    html = new StringBuilder(
        "<body style=" + "\"background-color:#FFFFFFFF;" + "\">" + html + "</body>");
    ViewWordExplain.getEngine().loadContent(html.toString());
    borderPane.setCenter(SceneWordExplain);
  }

  public void setSceneExplain() {
    String htmlOfSearchWord = myDictionary.LookupDic(currentWord);
    htmlOfSearchWord =
        "<body style=" + "\"background-color:#FFFFFFFF;" + "\">" + htmlOfSearchWord + "</body>";
    int checkbeFavorited = myDictionary.checkbeFavorited(currentWord);
    htmlOfSearchWord = Utils.setUneditable(htmlOfSearchWord);
    ViewWordExplain.getEngine().loadContent(htmlOfSearchWord);
    if (checkbeFavorited == 0) {
      favorite.setGlyphName("HEART_ALT");
    } else {
      favorite.setGlyphName("HEART");
    }
    SpeakerButtonn.setVisible(true);
    favorite.setVisible(true);
    borderPane.setCenter(SceneWordExplain);
  }

  public void setSceneAdd() throws IOException {
    ProjectConfig.introStage.setScene(AddWordController.getScene());
  }

  public void setSceneEdit() throws IOException {
    ProjectConfig.introStage.setScene(EditWordController.getScene());
  }

  public void setGGTransText() throws IOException {
    ProjectConfig.introStage.setScene(TextTranslateController.getScene());
  }

  public void setSceneFavor() throws IOException {
    ProjectConfig.introStage.setScene(FavoriteController.getScene());
  }

  public static Scene getScene() throws IOException {
    Parent root = FXMLLoader.load(IntroController.class.getResource("Intro.fxml"));
    return new Scene(root);
  }

  public void VerEngVie() throws IOException {
    ProjectConfig.databaseName = "av";
    ProjectConfig.introStage.setScene(IntroController.getScene());
  }

  public void VietAnhVersion() throws IOException {
    ProjectConfig.databaseName = "va";
    ProjectConfig.introStage.setScene(IntroController.getScene());
  }

  public void About(ActionEvent event)//h??m th??ng tin
  {
    Alert alert=new Alert(Alert.AlertType.INFORMATION);
    alert.setTitle("Information");
    alert.setHeaderText(null);
    alert.setContentText("This Dictionary was written by Nguyen Anh Tuan & Do Thuy Linh & Le Thai Son");
    alert.show();
  }

  public void Quit(ActionEvent event) {//ham ????ng ???ng d???ng
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
      setSceneExplain();
    }
  }
}