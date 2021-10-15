package display.graphic.addword;

import dictionary.Word;
import display.dialog.ConfirmDialog;
import display.dialog.ErrorDialog;
import display.dialog.InformationDialog;
import display.graphic.intro.IntroController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.web.HTMLEditor;
import utility.ProjectConfig;
import utility.Utils;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AddWordController extends IntroController {
  @FXML
  private HTMLEditor htmlEditor;

  public boolean addWord() {
    String html = htmlEditor.getHtmlText();
    String word = Utils.getHTMLTextWord(html);
    Word newWord = new Word(word, html);
    return myDictionary.saveWord(newWord);
  }
  public void setButtonSave() throws IOException {
    ConfirmDialog addWordConfirm = new ConfirmDialog();
    boolean checkNoti = addWordConfirm.show("Add New Word",
        "Are you sure want to add this word?");
    if (checkNoti) {
      boolean beSaved = addWord();
      if (beSaved) {
        InformationDialog addNoti = new InformationDialog();
        addNoti.show("Add New Word", "Added successfully");
        setintroStage();
      } else {
        ErrorDialog addNoti = new ErrorDialog();
        addNoti.show("Add New Word", "Error: The word was saved");
      }
    }
  }
  public void setButtonBack() throws IOException {
    ConfirmDialog cancelNotification = new ConfirmDialog();
    boolean checkNoti = cancelNotification.show("Add New Word",
        "Do you want to back?");
    if (checkNoti) {
      setintroStage();
    }
  }

  public void setintroStage() throws IOException {
    ProjectConfig.introStage.setScene(IntroController.getScene());
  }

  public static Scene getScene() throws IOException {
    Parent root = FXMLLoader.load(AddWordController.class.getResource("AddWord.fxml"));
    return new Scene(root);
  }

  @Override
  public void initialize(URL location, ResourceBundle resources) {
  }
}