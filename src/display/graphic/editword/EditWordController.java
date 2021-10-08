package display.graphic.editword;

import display.dialog.ConfirmDialog;
import display.dialog.InformationDialog;
import display.graphic.intro.PrimaryController;
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

public class EditWordController extends PrimaryController implements Initializable {
  @FXML
  private HTMLEditor htmlEditor;

  public void setPrimaryStage() throws IOException {
    ProjectConfig.primaryStage.setScene(PrimaryController.getScene());
  }

  public void setBackButton() throws IOException {
    ConfirmDialog cancelConfirm = new ConfirmDialog();
    boolean isConfirm = cancelConfirm.show("Add New Word",
        "Are you sure want to back?");
    if (isConfirm) {
      setPrimaryStage();
    }
  }

  public void setSaveButton() throws IOException {
    ConfirmDialog editWordConfirm = new ConfirmDialog();
    boolean isConfirm = editWordConfirm.show("Edit Word",
        "Are you sure want to edit this word?");
    if (isConfirm) {
      myDictionary.editWord(currentWord, htmlEditor.getHtmlText());
      InformationDialog savedDialog = new InformationDialog();
      savedDialog.show("Edit Word", "Edited successfully");
      setPrimaryStage();
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