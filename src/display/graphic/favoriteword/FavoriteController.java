package display.graphic.favoriteword;

import com.jfoenix.controls.JFXListView;
import display.dialog.ConfirmDialog;
import display.graphic.intro.IntroController;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import utility.ProjectConfig;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class FavoriteController extends IntroController implements Initializable {

  @FXML
  JFXListView<String> listFavorWord;

  public void getFavors() {
    listFavorWord.getItems().clear();
    ResultSet resultSet = myDictionary.getFavor();
    while (true) {
      try {
        if (!resultSet.next()) break;
        listFavorWord.getItems().add(resultSet.getString("word"));
      } catch (SQLException e) {
        e.printStackTrace();
      }
    }
  }

  public void getFavorWordinList() throws IOException {
    ObservableList<String> selectedIndices = listFavorWord.getSelectionModel().getSelectedItems();
    currentWord = selectedIndices.get(0);
    ProjectConfig.introStage.setScene(IntroController.getScene());
  }

  public static Scene getScene() throws IOException {
    Parent root = FXMLLoader.load(FavoriteController.class.getResource("Favorite.fxml"));
    return new Scene(root);
  }

  public void setButtonBack() throws IOException {
    ConfirmDialog cancelNotification = new ConfirmDialog();
    boolean checkNoti = cancelNotification.show("Add New Word",
        "Do you want to back?");
    if (checkNoti) {
      ProjectConfig.introStage.setScene(IntroController.getScene());
    }
  }

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    getFavors();
  }
}