package display.graphic.editword;

import display.dialog.ConfirmDialog;
import display.dialog.InformationDialog;
import display.graphic.intro.IntroController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.web.HTMLEditor;
import utility.ProjectConfig;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class EditWordController extends IntroController implements Initializable {
  @FXML
  private HTMLEditor htmlEditor;

  public void setintroStage() throws IOException {
    ProjectConfig.introStage.setScene(IntroController.getScene());
  }
  public void setButtonBack() throws IOException {
    ConfirmDialog cancelNotification = new ConfirmDialog();
    boolean checkNoti = cancelNotification.show("Add New Word",
        "Do you want to back?");
    if (checkNoti) {
      setintroStage();
    }
  }

  public void setButtonSave() throws IOException {
    ConfirmDialog editWordConfirm = new ConfirmDialog();
    boolean checkNoti = editWordConfirm.show("Edit Word",
        "Do you want to edit this word?");
    if (checkNoti) {
      myDictionary.editWord(currentWord, htmlEditor.getHtmlText());
      InformationDialog saveNoti = new InformationDialog();
      saveNoti.show("Edit Word", "Edited successfully");
      setintroStage();
    }
  }

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    String html = myDictionary.LookupDic(currentWord);
    htmlEditor.setHtmlText(html);
  }

  public static Scene getScene() throws IOException {
    Parent root = FXMLLoader.load(EditWordController.class.getResource("EditWord.fxml"));
    return new Scene(root);
  }
}