package display.dialog;

import javafx.scene.control.Alert;

public class InformationDialog {

  Alert notification = new Alert(Alert.AlertType.INFORMATION);

  public void show(String title, String message) {
    notification.setTitle(title);
    notification.setHeaderText(null);
    notification.setContentText(message);
    notification.showAndWait();
  }
}
