import javafx.application.*;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.*;
import javafx.geometry.*;
import javafx.event.*;

public class Connect4Client extends Application {
	
	public static void main(String[] args) {
		launch(args);
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		
		// Player turn and notification status panel with controls defined and styled
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
		
		
		
		// Create gameboard and buttons for it
		GridPane connect4Board = new GridPane();
		connect4Board.setPadding(new Insets(10,10,10,10));
		connect4Board.setHgap(5);
        	connect4Board.setVgap(5);
		connect4Board.setGridLinesVisible(false);
		for (int i = 0; i < 6; i++){
			for (int j = 0; j < 7; j++){
				Button button = new Button();
				button.setPrefWidth(75);
				button.setPrefHeight(75);
				connect4Board.add(button, j, i);
			}
		}
		
		
		// Combine gameboard with notification panel
		VBox game = new VBox();
		game.getChildren().addAll(notificationsBox, connect4Board);
		Scene scene = new Scene(game, 550,500);
		connect4Board.getChildren().get(20).requestFocus();

		
		primaryStage.setTitle("Connect 4: Halo Edition");
		primaryStage.setScene(scene);
		primaryStage.setResizable(false);
		primaryStage.show();
	}

}
