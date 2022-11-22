import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.geometry.*;

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
		Label playerTurnLbl = new Label("Player Turn: ");
		VBox playerTurnLblBox = new VBox();
		playerTurnLblBox.setAlignment(Pos.CENTER);
		playerTurnLblBox.getChildren().addAll(playerTurnLbl);
		TextField playerTurn = new TextField();
		playerTurn.setPrefWidth(25);
		playerTurn.setText("2");
		playerTurn.setEditable(false);
		Label spacer = new Label("     ");
		Label moveInfoLbl = new Label("Status: ");
		VBox moveLblBox = new VBox();
		moveLblBox.setAlignment(Pos.CENTER);
		moveLblBox.getChildren().addAll(moveInfoLbl);
		TextField moveInfo = new TextField();
		moveInfo.setPrefWidth(350);
		HBox notificationsBox = new HBox();
		notificationsBox.setPadding(new Insets(10,10,10,10));
		notificationsBox.getChildren().addAll(playerTurnLblBox, playerTurn, spacer, moveLblBox, moveInfo);
		
		
		
		// Create gameboard and all the spots for it
		GridPane connect4Board = new GridPane();
		connect4Board.setPadding(new Insets(10,10,10,10));
		connect4Board.setHgap(2);
        	connect4Board.setVgap(2);
		connect4Board.setGridLinesVisible(false);
		for (int i = 0; i < 6; i++){
			for (int j = 0; j < 7; j++){
				Button button = new Button(i + " " + j);
				button.setPrefWidth(75);
				button.setPrefHeight(75);
				//button.setPadding(new Insets(10,10,10,10));
				connect4Board.add(button, j, i);
			}
		}
		
		
		// Combine gameboard with notification panel
		VBox game = new VBox();
		game.getChildren().addAll(notificationsBox, connect4Board);
		

		Scene scene = new Scene(game, 550,500);
		connect4Board.getChildren().get(0).requestFocus();
		primaryStage.setTitle("Connect 4: Bruh Edition");
		primaryStage.setScene(scene);
		primaryStage.setResizable(false);
		primaryStage.show();
	}

}
