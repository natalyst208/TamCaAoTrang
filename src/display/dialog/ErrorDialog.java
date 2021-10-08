package display.dialog;

import javafx.scene.control.Alert;

public class ErrorDialog {

  Alert alert = new Alert(Alert.AlertType.ERROR);

  public void show(String title, String message) {
    alert.setTitle(title);
    alert.setHeaderText(null);
    alert.setContentText(message);
    alert.showAndWait();
  }
}
