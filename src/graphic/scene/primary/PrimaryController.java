package graphic.scene.primary;

import graphic.scene.api.GoogleTranslateController;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import module.LevenshteinDistance;
import module.TextToSpeech;
import com.jfoenix.controls.JFXColorPicker;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import dictionary.DictionaryManagement;
import graphic.dialog.ConfirmDialog;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.web.WebView;
import graphic.scene.add.AddWordController;
import graphic.scene.favorite.FavoriteController;
import graphic.scene.edit.EditWordController;
import utility.ProjectConfig;
import utility.Utils;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class PrimaryController implements Initializable  {

    protected DictionaryManagement myDictionary = DictionaryManagement.getDictionaryManagement();

    @FXML
    private BorderPane borderPane;

    @FXML
    public TextField searchTextField;

    @FXML
    private JFXColorPicker colorPicker;

    @FXML
    private WebView wordExplainView;

    @FXML
    private FontAwesomeIconView speakerIcon;

    @FXML
    private ListView<String> listView;

    @FXML
    private VBox wordExplainScene;

    @FXML
    private ToolBar toolBar;

    @FXML
    private FontAwesomeIconView favorite;

    public static String currentWord;

    @FXML
    public void searchWord() {
        currentWord = searchTextField.getText();
        if (myDictionary.isContain(currentWord)) {
            setWordExplainScene();
        } else {
            setDidYouMeanScene();
        }
    }

    public void pronounceWord() {
        TextToSpeech.speak(currentWord);
    }

    public void changeFavoriteStatus() {
        if (favorite.getGlyphName().equals("STAR")) {
            favorite.setGlyphName("STAR_ALT");
            myDictionary.setFavoriteStatus(currentWord, 0);
        } else {
            favorite.setGlyphName("STAR");
            myDictionary.setFavoriteStatus(currentWord, 1);
        }
    }

    public void deleteWord() throws SQLException {
        ConfirmDialog deleteConfirm = new ConfirmDialog();
        boolean isConfirm = deleteConfirm.show("Delete", "Are you sure want to delete this word?");
        if (isConfirm) {
            wordExplainView.getEngine().loadContent("<h1>Chúng tôi không tìm thấy từ mà bạn yêu cầu.</h1>");
            myDictionary.deleteWord(currentWord);
            printSuggestedWords();
        }
    }

    public void printSuggestedWords() throws SQLException {
        listView.getItems().clear();
        ResultSet resultSet = myDictionary.dictionarySearch(searchTextField.getText());
        while (resultSet.next()) {
            listView.getItems().add(resultSet.getString("word"));
        }
    }

    public void getSelectedWordInSuggestedList() {
        ObservableList<String> selectedIndices = listView.getSelectionModel().getSelectedItems();
        searchTextField.setText(selectedIndices.get(0));
        searchWord();
    }

    private void setDidYouMeanScene() {
        StringBuilder html = new StringBuilder("<h1>Chúng tôi không tìm thấy từ mà bạn yêu cầu.</h1>");
        html.append("<h1>Có phải từ bạn muốn tìm kiếm là: </h1>");
        String[] result = LevenshteinDistance.getTopScore(currentWord);
        for (String word: result) {
            if (word == null){
                continue;
            }
            html.append("<h1>").append(word).append("</h1>");
        }
        html = new StringBuilder("<body style=" + "\"background-color:#FFFFFFFF;" + "\">" + html + "</body>");
        wordExplainView.getEngine().loadContent(html.toString());
        borderPane.setCenter(wordExplainScene);
    }

    public void setWordExplainScene() {
        String htmlOfSearchWord = myDictionary.dictionaryLookup(currentWord);
        htmlOfSearchWord = "<body style=" + "\"background-color:#FFFFFFFF;" + "\">" + htmlOfSearchWord + "</body>";
        int isFavorite = myDictionary.isFavorite(currentWord);
        htmlOfSearchWord = Utils.setNotEditable(htmlOfSearchWord);
        wordExplainView.getEngine().loadContent(htmlOfSearchWord);
        if (isFavorite == 0) {
            favorite.setGlyphName("STAR_ALT");
        } else {
            favorite.setGlyphName("STAR");
        }
        speakerIcon.setVisible(true);
        favorite.setVisible(true);
        borderPane.setCenter(wordExplainScene);
    }

    public void setAddWordScene() throws IOException {
        ProjectConfig.primaryStage.setScene(AddWordController.getScene());
    }

    public void setEditWordScene() throws IOException {
        ProjectConfig.primaryStage.setScene(EditWordController.getScene());
    }

    public void setGoogleTranslateScene() throws IOException {
        ProjectConfig.primaryStage.setScene(GoogleTranslateController.getScene());
    }

    public void setFavoriteWordsScene() throws IOException {
        ProjectConfig.primaryStage.setScene(FavoriteController.getScene());
    }

    public static Scene getScene() throws IOException {
        Parent root = FXMLLoader.load(PrimaryController.class.getResource("PrimaryScene.fxml"));
        return new Scene(root);
    }

    public void EnglishVietnameseVersion() throws IOException {
        ProjectConfig.databaseName = "av";
        ProjectConfig.primaryStage.setScene(PrimaryController.getScene());
    }

    public void AnhVietVersion() throws IOException {
        ProjectConfig.databaseName = "va";
        ProjectConfig.primaryStage.setScene(PrimaryController.getScene());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            printSuggestedWords();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (currentWord == null) {
            String html = "<h1> Ứng dụng từ điển Anh - Việt </h1>";
            html = html + "<h1> Tác giả: </h1>";
            html = html + "<h1> Nguyễn Anh Tuấn đẹp trai - 20020219  </h1>";
            html = html + "<h1> Đỗ Thùy Linh ahihi - 20020000 </h1>";
            wordExplainView.getEngine().loadContent(html);
        } else {
            setWordExplainScene();
        }
    }
}
