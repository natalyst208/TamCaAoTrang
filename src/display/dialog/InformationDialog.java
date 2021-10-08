package display.dialog;

import javafx.scene.control.Alert;

public class InformationDialog {

  Alert alert = new Alert(Alert.AlertType.INFORMATION);

  public void show(String title, String message) {
    alert.setTitle(title);
    alert.setHeaderText(null);
    alert.setContentText(message);
    alert.showAndWait();
  }
}
