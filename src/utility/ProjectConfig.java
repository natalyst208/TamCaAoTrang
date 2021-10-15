package utility;


import javafx.scene.image.Image;
import javafx.stage.Stage;
import display.dialog.ConfirmDialog;

public class ProjectConfig {
    public static String dictionaryIconPath = "./resource/logo/dictionary_icon.png";
    public static int numberSimilarWord = 5;
    public static String databasePath = "jdbc:sqlite:resource/database/dict_hh.db";
    public static String databaseName = "av";


    public static Stage introStage = new Stage();
    static {
        introStage.setTitle("Dictionary");
        Image dictionary_icon = Utils.loadImage(ProjectConfig.dictionaryIconPath);
        introStage.getIcons().add(dictionary_icon);
        introStage.setOnCloseRequest(close -> {
            close.consume();
            ConfirmDialog CloseNoticeConFirm = new ConfirmDialog();
            boolean checkNoti = CloseNoticeConFirm.show("Close", "Are you sure want to exit?");
            if (checkNoti) {
                introStage.close();
            } else {
                introStage.show();
            }
        });
    }
}
