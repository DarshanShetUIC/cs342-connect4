import javafx.application.*;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.*;
import javafx.geometry.*;
import javafx.event.*;

public class Connect4Server extends Application {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		// An intro screen that allows the user to input the port number and start the server
		Spinner portSpinner = new Spinner(1024,49151,5555);
		portSpinner.setEditable(true);
		Button serverOnButton = new Button("Turn On Server");
		HBox serverControls = new HBox();
		serverControls.setSpacing(10);
		serverControls.setPadding(new Insets(10,10,10,10));
		serverControls.setAlignment(Pos.CENTER);
		serverControls.getChildren().addAll(portSpinner, serverOnButton);
		Scene serverControlsScene = new Scene(serverControls, 325,50);
		
		// A screen that displays the state of the game information with a button to turn off the server
		ListView<String> notificationPanel = new ListView<String>();
		//notificationPanel.getItems().add("A");
		Button serverOffButton = new Button("Turn Off Server");
		VBox serverPanel = new VBox();
		serverPanel.setPadding(new Insets(10,10,10,10));
		serverPanel.setAlignment(Pos.CENTER);
		serverPanel.setSpacing(10);
		serverPanel.getChildren().addAll(serverOffButton, notificationPanel);
		Scene notificationScene = new Scene(serverPanel, 700,400);
		
		// Show the intro screen first
		primaryStage.setScene(serverControlsScene);
		primaryStage.setTitle("Connect 4: Halo Server");
		primaryStage.show();
		
		serverOnButton.setOnAction(new EventHandler<ActionEvent>(){
			@Override public void handle(ActionEvent e){
				primaryStage.setScene(notificationScene);
				primaryStage.show();
			}
		});
		
		serverOffButton.setOnAction(new EventHandler<ActionEvent>(){
			@Override public void handle(ActionEvent e){
				Platform.exit();
				System.exit(0);
			}
		});
	}
}
