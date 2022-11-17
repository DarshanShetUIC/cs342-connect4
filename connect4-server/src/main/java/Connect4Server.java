import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.*;
import javafx.geometry.*;

public class Connect4Server extends Application {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		launch(args);
	}

	//feel free to remove the starter code from this method
	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		Spinner portSpinner = new Spinner(1024,49151,5555);
		portSpinner.setEditable(true);
		Button serverOnButton = new Button("Turn On Server");
		HBox serverControls = new HBox();
		serverControls.setAlignment(Pos.CENTER);
		serverControls.getChildren().addAll(portSpinner, serverOnButton);
		
		
		TextArea notificationPanel = new TextArea();
		VBox serverPanel = new VBox();
		serverPanel.getChildren().addAll(serverControls,notificationPanel);
		
		Scene scene = new Scene(serverPanel, 700,400);
		primaryStage.setScene(scene);
		primaryStage.setTitle("Welcome to Connect 4");
		primaryStage.show();
	}
}
