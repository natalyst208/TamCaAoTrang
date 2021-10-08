package display.graphic.texttranslate;

import com.jfoenix.controls.JFXTextArea;
import display.dialog.ConfirmDialog;
import display.graphic.intro.PrimaryController;
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

public class GoogleTranslateController extends PrimaryController {

  Language language = Language.getLanguage();

  @FXML
  private JFXTextArea englishText;

  @FXML
  private JFXTextArea translatedText;

  @FXML
  private ChoiceBox<String> choiceBoxEnglish;

  @FXML
  private ChoiceBox<String> choiceBoxTranslated;

  public void setBackButton() throws IOException {
    ConfirmDialog cancelConfirm = new ConfirmDialog();
    boolean isConfirm = cancelConfirm.show("Add Word",
        "Are you sure want to back?");
    if (isConfirm) {
      ProjectConfig.primaryStage.setScene(PrimaryController.getScene());
    }
  }

  public void translate() {
    String text = englishText.getText();
    String targetLanguage = choiceBoxTranslated.getValue();
    String sourceLanguage = choiceBoxEnglish.getValue();
    targetLanguage = language.getABBRLanguage(targetLanguage);
    sourceLanguage = language.getABBRLanguage(sourceLanguage);
    StringBuilder translated = APIGoogleTranslate.translate(sourceLanguage, targetLanguage, text);
    translatedText.setText(String.valueOf(translated));
  }

  public void englishSpeak() {
    String text = englishText.getText();
    TextToSpeech.speak(text);
  }

  public void translatedSpeak() {
    String text = translatedText.getText();
    TextToSpeech.speak(text);
  }

  public static Scene getScene() throws IOException {
    Parent root = FXMLLoader.load(GoogleTranslateController.class.getResource("texttranslate.fxml"));
    return new Scene(root);
  }

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    for (Map.Entry<String, String> entry : language.mapLang.entrySet()) {
      choiceBoxTranslated.getItems().add(entry.getValue());
      choiceBoxEnglish.getItems().add(entry.getValue());
    }
    choiceBoxEnglish.setValue("ENGLISH");
    choiceBoxTranslated.setValue("VIETNAMESE");
  }
}