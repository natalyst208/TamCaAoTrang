import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import utility.ProjectConfig;

import java.io.IOException;

public class Run extends Application {
    @Override
    public void start(Stage primaryStage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource(
            "display/graphic/intro/Primary.fxml"));
        Scene scene = new Scene(root);
        ProjectConfig.primaryStage.setScene(scene);
        ProjectConfig.primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
