package display.dialog;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.util.Optional;

public class ConfirmDialog {

  Alert alert = new Alert(Alert.AlertType.CONFIRMATION);

  public boolean show(String title, String message) {
    alert.setTitle(title);
    alert.setHeaderText(null);
    alert.setContentText(message);
    Optional<ButtonType> option = alert.showAndWait();
    return option.filter(buttonType -> buttonType == ButtonType.OK).isPresent();
  }
}
