package display.dialog;

import javafx.scene.control.Alert;

public class ErrorDialog {

  Alert notification = new Alert(Alert.AlertType.ERROR);

  public void show(String title, String message) {
    notification.setHeaderText(null);
    notification.setContentText(message);
    notification.setTitle(title);
    notification.showAndWait();
  }
}
