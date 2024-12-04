import javafx.animation.*;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Duration;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class MainController implements Initializable {
    private static MainController instance;

    public MainController() {
        instance = this;
    }

    public static MainController getInstance() {
        return instance;
    }
    @FXML
    private Button playButton;
    @FXML
    private Button quitButton;
    @FXML
    private BorderPane mainMenu;
    @FXML
    private VBox mainMenu_center;
    public MediaPlayer mediaPlayer;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        addHoverEffect(playButton);
        addHoverEffect(quitButton);
    }
    //Highlight each button when the mouse hovers over them
    private void addHoverEffect(Button button) {
        DropShadow shadow = new DropShadow();
        shadow.setColor(Color.GRAY);
        button.addEventHandler(MouseEvent.MOUSE_ENTERED, e -> button.setEffect(shadow));
        button.addEventHandler(MouseEvent.MOUSE_EXITED, e -> button.setEffect(null));
    }

    @FXML
    private void setQuitButton() {
        System.exit(0);
    }
    @FXML
    private void setPlayButton()  throws Exception {
        //set button unclickable
        playButton.setDisable(true);
        //get current root of the scene
        Parent root = playButton.getScene().getRoot();
        //play an animation where the screen fades to black
        //then fades back in with the game screen
        FadeTransition fadeOut = new FadeTransition(Duration.seconds(2), root);
        playButton.getScene().setFill(Color.BLACK);
        fadeOut.setFromValue(1);
        fadeOut.setToValue(0);

        fadeOut.setOnFinished(e -> {
            //load the game screen
            loadNewScene();
        });
        fadeOut.play();
    }
    private void loadNewScene() {
        try {
            //create fade in transition for new scene
            Parent newRoot = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("GamePage.fxml")));
            Scene newScene = new Scene(newRoot, 1000, 600);
            Stage primaryStage = (Stage) playButton.getScene().getWindow();

            newScene.setFill(Color.BLACK);
            primaryStage.setScene(newScene);
            newScene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("Styles.css")).toExternalForm());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
