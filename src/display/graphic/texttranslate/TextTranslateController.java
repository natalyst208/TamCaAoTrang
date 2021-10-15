package display.graphic.texttranslate;

import com.jfoenix.controls.JFXTextArea;
import display.dialog.ConfirmDialog;
import display.graphic.intro.IntroController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import module.APIGoogleTranslate;
import module.Language;
import module.TextToSpeech;
import utility.ProjectConfig;

import java.io.IOException;
import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;

public class TextTranslateController extends IntroController {

  Language language = Language.getLanguage();

  @FXML
  private JFXTextArea EngText;

  @FXML
  private JFXTextArea beTranslatedText;

  @FXML
  private ChoiceBox<String> choiceEngBox;

  @FXML
  private ChoiceBox<String> choiceTransBox;

  public void setButtonBack() throws IOException {
    ConfirmDialog cancelNotification = new ConfirmDialog();
    boolean checkNoti = cancelNotification.show("Add Word",
        "Do you want to back?");
    if (checkNoti) {
      ProjectConfig.introStage.setScene(IntroController.getScene());
    }
  }

  public void translate() {
    String text = EngText.getText();
    String targetLanguage = choiceTransBox.getValue();
    String sourceLanguage = choiceEngBox.getValue();
    targetLanguage = language.getWantLanguage(targetLanguage);
    sourceLanguage = language.getWantLanguage(sourceLanguage);
    StringBuilder translated = APIGoogleTranslate.translate(sourceLanguage, targetLanguage, text);
    beTranslatedText.setText(String.valueOf(translated));
  }


  public void translatedSpeak() {
    String text = beTranslatedText.getText();
    TextToSpeech.speak(text);
  }
  public void englishSpeak() {
    String text = EngText.getText();
    TextToSpeech.speak(text);
  }


  public static Scene getScene() throws IOException {
    Parent root = FXMLLoader.load(TextTranslateController.class.getResource("texttranslate.fxml"));
    return new Scene(root);
  }

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    for (Map.Entry<String, String> entry : language.mapLang.entrySet()) {
      choiceTransBox.getItems().add(entry.getValue());
      choiceEngBox.getItems().add(entry.getValue());
    }
    choiceEngBox.setValue("ENGLISH");
    choiceTransBox.setValue("VIETNAMESE");
  }
}