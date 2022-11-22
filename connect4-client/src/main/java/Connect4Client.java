import javafx.application.*;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.*;
import javafx.geometry.*;
import javafx.event.*;
import javafx.scene.image.*;

public class Connect4Client extends Application {
	
	public static void main(String[] args) {
		launch(args);
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		
		// Player turn and notification status panel with controls defined and styled
		Label playerTurnLbl = new Label("Player Turn: ");
		playerTurnLbl.setStyle("-fx-text-fill: #ffffff;");
		VBox playerTurnLblBox = new VBox();
		playerTurnLblBox.setAlignment(Pos.CENTER);
		playerTurnLblBox.getChildren().addAll(playerTurnLbl);
		TextField playerTurn = new TextField();
		playerTurn.setPrefWidth(25);
		playerTurn.setText("2");
		playerTurn.setEditable(false);
		Label spacer = new Label("     ");
		Label moveInfoLbl = new Label("Status: ");
		moveInfoLbl.setStyle("-fx-text-fill: #ffffff;");
		VBox moveLblBox = new VBox();
		moveLblBox.setAlignment(Pos.CENTER);
		moveLblBox.getChildren().addAll(moveInfoLbl);
		TextField moveInfo = new TextField();
		moveInfo.setPrefWidth(352);
		moveInfo.setEditable(false);
		moveInfo.setText("Player one moved to 3,3. Player two, your turn.");
		HBox notificationsBox = new HBox();
		notificationsBox.setPadding(new Insets(10,10,10,10));
		notificationsBox.getChildren().addAll(playerTurnLblBox, playerTurn, spacer, moveLblBox, moveInfo);
		
		// Create gameboard and buttons for it
		GridPane connect4Board = new GridPane();
		connect4Board.setPadding(new Insets(10,10,10,10));
		connect4Board.setHgap(7);
        	connect4Board.setVgap(7);
		connect4Board.setGridLinesVisible(false);
		for (int i = 0; i < 6; i++){
			for (int j = 0; j < 7; j++){
				GameButton button = new GameButton(i, j, 0);
				button.setPadding(Insets.EMPTY);
				button.setStyle("-fx-shadow-highlight-color: transparent;");
				connect4Board.add(button, j, i);
			}
		}
		
		
		// Combine gameboard with notification panel
		VBox game = new VBox();
		game.setStyle("-fx-background-image: url(\"/images/background.jpg\");");
		game.getChildren().addAll(notificationsBox, connect4Board);
		Scene scene = new Scene(game, 555, 520);
		connect4Board.getChildren().get(35).requestFocus();

		
		primaryStage.setTitle("Connect 4: Halo Edition");
		primaryStage.setScene(scene);
		primaryStage.setResizable(false);
		primaryStage.show();
	}

}
