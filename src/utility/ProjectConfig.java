package utility;

import display.dialog.ConfirmDialog;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class ProjectConfig {
    public static String databasePath = "jdbc:sqlite:resource/database/dict_hh.db";
    public static String databaseName = "av";

    public static String dictionaryIconPath = "./resource/logo/dictionary_icon.png";
    public static int numberDidYouMeanWord = 5;
    public static Stage introStage = new Stage();
    static {
        introStage.setTitle("Dictionary");
        Image dictionary_icon = Utils.loadImage(ProjectConfig.dictionaryIconPath);
        introStage.getIcons().add(dictionary_icon);
        introStage.setOnCloseRequest(close -> {
            close.consume();
            ConfirmDialog confirmClose = new ConfirmDialog();
            boolean checkNoti = confirmClose.show("Close", "Are you sure want to exit?");
            if (checkNoti) {
                introStage.close();
            } else {
                introStage.show();
            }
        });
    }
}
