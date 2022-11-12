import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;

public class Connect4Client extends Application {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		launch(args);
		
	}

	//feel free to remove the starter code from this method
	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		
		//Create notifcation text fields for player to know what's going on
		// Create section for text alerts
		Label playerTurnLbl = new Label("Bruh Move: ");
		TextField playerTurn = new TextField();
		Label moveInfoLbl = new Label("Notifications Bruh: ");
		TextField moveInfo = new TextField();
		HBox notificationsBox = new HBox();
		notificationsBox.getChildren().addAll(playerTurnLbl, playerTurn, moveInfoLbl, moveInfo);
		
		
		
		// Create gameboard and all the spots for it
		GridPane connect4Board = new GridPane();
		connect4Board.setGridLinesVisible(true);
		for (int i = 0; i < 6; i++){
			for (int j = 0; j < 7; j++){
				Button button = new Button(i + " " + j);
				connect4Board.add(button, j, i);
			}
		}
		
		
		
		// Combine gameboard with notification panel
		VBox game = new VBox();
		game.getChildren().addAll(notificationsBox, connect4Board);
		

		Scene scene = new Scene(game, 700,600);
		primaryStage.setTitle("Connect 4: Bruh Edition");
		primaryStage.setScene(scene);
		primaryStage.show();
	}

}
