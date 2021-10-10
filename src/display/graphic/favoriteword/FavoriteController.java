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
  JFXListView<String> listFavoriteWords;

  public void setBackButton() throws IOException {
    ConfirmDialog cancelNotification = new ConfirmDialog();
    boolean checkNoti = cancelNotification.show("Add New Word",
        "Are you sure want to back?");
    if (checkNoti) {
      ProjectConfig.introStage.setScene(IntroController.getScene());
    }
  }

  public void getFavorites() {
    listFavoriteWords.getItems().clear();
    ResultSet resultSet = myDictionary.getFavorite();
    while (true) {
      try {
        if (!resultSet.next()) break;
        listFavoriteWords.getItems().add(resultSet.getString("word"));
      } catch (SQLException e) {
        e.printStackTrace();
      }
    }
  }

  public void getFavoriteInList() throws IOException {
    ObservableList<String> selectedIndices = listFavoriteWords.getSelectionModel().getSelectedItems();
    currentWord = selectedIndices.get(0);
    ProjectConfig.introStage.setScene(IntroController.getScene());
  }

  public static Scene getScene() throws IOException {
    Parent root = FXMLLoader.load(FavoriteController.class.getResource("Favorite.fxml"));
    return new Scene(root);
  }

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    getFavorites();
  }
}