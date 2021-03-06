package display.dialog;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.util.Optional;

public class ConfirmDialog {

  Alert notification = new Alert(Alert.AlertType.CONFIRMATION);

  public boolean show(String title, String message) {
    notification.setHeaderText(null);
    notification.setContentText(message);
    notification.setTitle(title);
    Optional<ButtonType> option = notification.showAndWait();
    return option.filter(buttonType -> buttonType == ButtonType.OK).isPresent();
  }
}
