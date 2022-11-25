import javafx.application.*;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.*;
import javafx.geometry.*;
import javafx.event.*;
import java.lang.Integer;
import javafx.scene.media.*;
import javafx.util.Duration;

public class Connect4Server extends Application {

	int port = 5555;
	Server server;
	
	MediaPlayer mediaPlayer;

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
	
		// An intro screen that allows the user to input the port number and start the server
		Spinner<Integer> portSpinner = new Spinner<Integer>(1024,49151,5555);
		portSpinner.setEditable(true);
		Button serverOnButton = new Button("Turn On Server");
		HBox serverControls = new HBox();
		serverControls.setSpacing(10);
		serverControls.setPadding(new Insets(10,10,10,10));
		serverControls.setAlignment(Pos.CENTER);
		serverControls.getChildren().addAll(portSpinner, serverOnButton);
		serverControls.setStyle("-fx-background-image: url(\"/images/background.jpg\");");
		Scene serverControlsScene = new Scene(serverControls, 325,50);
		
		// A screen that displays the state of the game information with a button to turn off the server
		ListView<String> notificationPanel = new ListView<String>();
		Button serverOffButton = new Button("Turn Off Server");
		VBox serverPanel = new VBox();
		serverPanel.setPadding(new Insets(10,10,10,10));
		serverPanel.setAlignment(Pos.TOP_CENTER);
		serverPanel.setSpacing(10);
		serverPanel.getChildren().addAll(serverOffButton, notificationPanel);
		serverPanel.setStyle("-fx-background-image: url(\"/images/background.jpg\");");
		Scene notificationScene = new Scene(serverPanel, 555, 520);
		
		// Show the intro screen first
		primaryStage.setScene(serverControlsScene);
		primaryStage.setTitle("Connect 4: Halo Server");
		primaryStage.show();
		
		serverOnButton.setOnAction(new EventHandler<ActionEvent>(){
			@Override public void handle(ActionEvent e){
				// Get the port from the value entered by user
				port = portSpinner.getValue();
				// Create the server with that port
				server = new Server(data -> {
					// Using callback feature, whenever the server gets a response
					// from any player, add the game status to notifications
					Platform.runLater(() -> {
						notificationPanel.getItems().add(data.gameStatus);
					});
				});
				server.createServerInstance(port);
				primaryStage.setScene(notificationScene);
				primaryStage.show();
				Media theme = new Media(getClass().getResource("/audio/halo.mp3").toExternalForm());
				mediaPlayer = new MediaPlayer(theme);
				//mediaPlayer.play();
				mediaPlayer.setOnEndOfMedia(() -> {
					mediaPlayer.seek(Duration.ZERO);
					mediaPlayer.play();
				});
			}
		});
		
		serverOffButton.setOnAction(new EventHandler<ActionEvent>(){
			@Override public void handle(ActionEvent e){
				mediaPlayer.stop();
				Platform.exit();
				System.exit(0);
			}
		});
	}
	
	@Override
	public void stop(){
		mediaPlayer.stop();
		Platform.exit();
		System.exit(0);
	}
}
