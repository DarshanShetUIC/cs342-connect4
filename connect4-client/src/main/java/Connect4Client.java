import javafx.application.*;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.*;
import javafx.geometry.*;
import javafx.event.*;
import javafx.scene.image.*;

public class Connect4Client extends Application {
	
	Client client;
	
	public static void main(String[] args) {
		launch(args);
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
	
		// Welcome screen for client
		Spinner portSpinner = new Spinner(1024,49151,5555);
		portSpinner.setEditable(true);
		Label portInfoLbl = new Label("Port:");
		portInfoLbl.setStyle("-fx-text-fill: #ffffff;");
		Button connectToServer = new Button("Connect");
		HBox portBox = new HBox();
		portBox.setAlignment(Pos.CENTER);
		portBox.setSpacing(5);
		portBox.getChildren().addAll(portInfoLbl, portSpinner, connectToServer);
		portBox.setStyle("-fx-background-image: url(\"/images/background.jpg\");");
		Scene welcomeScreen = new Scene(portBox, 555, 520);
		
		// Player turn and notification status panel with controls defined and styled, also create client for referencing
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
				
				button.setOnAction(new EventHandler<ActionEvent>(){
					@Override public void handle(ActionEvent e){
						// TODO: Update client's internal CFourInfo data object and then send to server
						// Layman's terms:
						// When a player presses a valid button on the game board,
						// update the game board matrix, then update the game status.
						// Send that info over to the server to process
					}
				});
				connect4Board.add(button, j, i);
			}
		}
		// Combine gameboard with notification panel
		VBox game = new VBox();
		game.setStyle("-fx-background-image: url(\"/images/background.jpg\");");
		game.getChildren().addAll(notificationsBox, connect4Board);
		Scene gameScene = new Scene(game, 555, 520);
		connect4Board.getChildren().get(35).requestFocus();
		
		
		
		primaryStage.setScene(welcomeScreen);
		primaryStage.setTitle("Connect 4: Halo Edition");
		primaryStage.setResizable(false);
		primaryStage.show();
		
		
		
		connectToServer.setOnAction(new EventHandler<ActionEvent>(){
			@Override public void handle(ActionEvent e){
				primaryStage.setScene(gameScene);
				primaryStage.show();
				
				// Create client that communicates with server
				client = new Client(data -> {
					Platform.runLater(() -> {
						for (int i = 0; i < 6; i++){
							for (int j = 0; j < 7; j++){
								// TODO: update client's game board with values received from server
							}
						}
					});
				});
			}
		});
	}
}
