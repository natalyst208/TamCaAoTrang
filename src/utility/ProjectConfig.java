package utility;

import graphic.dialog.ConfirmDialog;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class ProjectConfig {
    public static String databasePath = "jdbc:sqlite:resource/data/dict_hh.db";
    public static String databaseName = "av";

    public static String dictionaryIconPath = "./resource/images/dictionary_icon.png";
    public static int numberDidYouMeanWord = 3;
    public static Stage primaryStage = new Stage();
    static {
        primaryStage.setTitle("Dictionary");
        Image dictionary_icon = Utils.loadImage(ProjectConfig.dictionaryIconPath);
        primaryStage.getIcons().add(dictionary_icon);
        primaryStage.setOnCloseRequest(close -> {
            close.consume();
            ConfirmDialog confirmClose = new ConfirmDialog();
            boolean isConfirm = confirmClose.show("Close", "Are you sure want to exit?");
            if (isConfirm) {
                primaryStage.close();
            } else {
                primaryStage.show();
            }
        });
    }
}
