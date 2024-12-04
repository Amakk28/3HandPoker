import javafx.animation.FadeTransition;
import javafx.animation.PauseTransition;
import javafx.animation.RotateTransition;
import javafx.animation.SequentialTransition;
import javafx.application.Application;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.AudioClip;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.layout.BorderPane;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import java.util.Objects;

public class JavaFXTemplate extends Application {
	private AudioClip backgroundMusic;
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		launch(args);
	}

	//feel free to remove the starter code from this method
	@Override
	public void start(Stage primaryStage) throws Exception {
		// Load the FXML file
		backgroundMusic = new AudioClip(getClass().getResource("LadyGaga_PFinstr_.mp3").toExternalForm());
		backgroundMusic.setCycleCount(AudioClip.INDEFINITE);
		backgroundMusic.setVolume(0.25);  // 50% volume
		backgroundMusic.play();
		primaryStage.setOnCloseRequest(event -> {
			backgroundMusic.stop();
		});
		FXMLLoader loader = new FXMLLoader(getClass().getResource("FrontPage.fxml"));
		Parent root = loader.load();

		// Set the scene
		Scene Main_menu = new Scene(root, 1000, 600);
		Main_menu.getStylesheets().add(Objects.requireNonNull(getClass().getResource("Styles.css")).toExternalForm());

		primaryStage.setTitle("3 Hand Poker");
		primaryStage.setScene(Main_menu);
		primaryStage.resizableProperty().setValue(Boolean.FALSE);
		primaryStage.show();
	}
}
