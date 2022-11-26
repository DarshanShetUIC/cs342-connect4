import javafx.application.*;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.*;
import javafx.geometry.*;
import javafx.event.*;
import javafx.scene.image.*;
import java.util.*;

public class Connect4Client extends Application {
	
	Client client;
	
	public static void main(String[] args) {
		launch(args);
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
	
		// Welcome screen for client
		Spinner<Integer> portSpinner = new Spinner<Integer>(1024,49151,5555);
		portSpinner.setEditable(true);
		Label portInfoLbl = new Label("Port:");
		portInfoLbl.setStyle("-fx-text-fill: #ffffff;");
		HBox portBox = new HBox();
		portBox.setAlignment(Pos.CENTER);
		portBox.setSpacing(5);
		portBox.getChildren().addAll(portInfoLbl, portSpinner);
		TextField ipInput = new TextField("127.0.0.1");
		Label ipInputLbl = new Label("   IP:");
		ipInputLbl.setStyle("-fx-text-fill: #ffffff;");
		HBox ipBox = new HBox();
		ipBox.setAlignment(Pos.CENTER);
		ipBox.setSpacing(5);
		ipBox.getChildren().addAll(ipInputLbl, ipInput);
		Button connectToServer = new Button("Connect");
		VBox controlsBox = new VBox();
		controlsBox.setAlignment(Pos.CENTER);
		controlsBox.setSpacing(5);
		controlsBox.getChildren().addAll(ipBox, portBox, connectToServer);
		controlsBox.setStyle("-fx-background-image: url(\"/images/background.jpg\");");
		Scene welcomeScreen = new Scene(controlsBox, 555, 520);
		
		// End of game scene
		Label endOfGameLbl = new Label("Player [] won!");
		endOfGameLbl.setStyle("-fx-text-fill: #ffffff; -fx-font-size: 30");
		Label blankLbl = new Label(" ");
		blankLbl.setStyle("-fx-text-fill: #ffffff; -fx-font-size: 30");
		Button restartBtn = new Button("Restart");
		Button quitBtn = new Button("Quit");
		HBox endGameControlsBox = new HBox();
		endGameControlsBox.setAlignment(Pos.CENTER);
		endGameControlsBox.setSpacing(5);
		endGameControlsBox.getChildren().addAll(restartBtn, quitBtn);
		VBox endGameBox = new VBox();
		endGameBox.setAlignment(Pos.CENTER);
		endGameBox.setSpacing(5);
		endGameBox.getChildren().addAll(endOfGameLbl, blankLbl, endGameControlsBox);
		endGameBox.setStyle("-fx-background-image: url(\"/images/background.jpg\");");
		Scene endGameScreen = new Scene(endGameBox, 555, 520);
		
		// Actual game scene defined below
		// Player turn and notification status panel with controls defined and styled, also create client for referencing
		Label playerTurnLbl = new Label("Player Turn: ");
		playerTurnLbl.setStyle("-fx-text-fill: #ffffff;");
		VBox playerTurnLblBox = new VBox();
		playerTurnLblBox.setAlignment(Pos.CENTER);
		playerTurnLblBox.getChildren().addAll(playerTurnLbl);
		TextField playerTurn = new TextField();
		playerTurn.setPrefWidth(25);
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
					@Override
					public void handle(ActionEvent e){
						// TODO: Update client's internal CFourInfo data object and then send to server
						// Layman's terms:
						// When a player presses a valid button on the game board,
						// update the game board matrix, then update the game status.
						// If the player wins the game logically, check for it,
						// and mark the board, set the status.
						// Send that info over to the server to pass on to other players
						
						
						client.data.boardMatrix[button.r][button.c] = client.playerID;
						client.data.gameStatus = "P " + client.playerID 
							+ " made move at " + button.r + "," + button.c;
						refreshGameBoardGUI(connect4Board, client.data);
						//client.send();
						if(client.playerID == 1){
							client.data.gameStatus = "Waiting for P2 to make a move...";
							moveInfo.setText(client.data.gameStatus);
						}
						else{
							client.data.gameStatus = "Waiting for P1 to make a move...";
							moveInfo.setText(client.data.gameStatus);
						}
						disableGameBoardGUI(connect4Board, true);
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
			@Override
			public void handle(ActionEvent e){
				primaryStage.setScene(gameScene);
				primaryStage.show();

				// Create client that communicates with server
				client = new Client(data -> {
					// When response received from server, update the game board
					Platform.runLater(() -> {
						// TODO: CLIENT SIDE GAME LOGIC
						// Update client's game board with values received from server
						// Also, enable or disable board depending on who is playing
						
						// Display message sent by server / other players
						moveInfo.setText(data.gameStatus);
						
						// Set player id of client once client connects to server
						// Server determines player's ID
						if(data.gameStatus.substring(0,4).equals("Your")){
							System.out.println("[Connect4Client] Case 1");
							client.updatePlayerID(Integer.parseInt(data.gameStatus.substring(18, 19)));
							primaryStage.setTitle("Player " + Integer.toString(client.playerID));
							disableGameBoardGUI(connect4Board, true);
						}
						// If server is full, exit because this is Player 3
						else if(data.gameStatus.substring(0,6).equals("Server")){
							System.out.println("[Connect4Client] Case 2");
							moveInfo.setText(data.gameStatus);
							disableGameBoardGUI(connect4Board, true);
							Timer timer = new Timer();
							timer.schedule(new TimerTask(){
								public void run()
								{
									Platform.exit();
									System.exit(0);
								}
							}, 3000);
						}
						// If error, display error and quit after 3 seconds
						// If server is full, exit because this is Player 3
						else if(data.gameStatus.substring(0,6).equals("Error:")){
							System.out.println("[Connect4Client] Case 3");
							moveInfo.setText(data.gameStatus);
							disableGameBoardGUI(connect4Board, true);
							Timer timer = new Timer();
							timer.schedule(new TimerTask(){
								public void run()
								{
									Platform.exit();
									System.exit(0);
								}
							}, 3000);
						}
						// Determine if game has started
						else if(data.gameStatus.substring(0,9).equals("2 players")){
							System.out.println("[Connect4Client] Case 4");
							moveInfo.setText(data.gameStatus);
							playerTurn.setText(Integer.toString(data.playerTurn));
							if(data.playerTurn == client.playerID){
								refreshGameBoardGUI(connect4Board, data);
							}
						}
					});
				});
				client.configureClient(ipInput.getText(), portSpinner.getValue());
				client.start();
			}
		});
		
		restartBtn.setOnAction(new EventHandler<ActionEvent>(){
			@Override public void handle(ActionEvent e){
				client.end();
				primaryStage.setScene(welcomeScreen);
			}
		});
		
		quitBtn.setOnAction(new EventHandler<ActionEvent>(){
			@Override public void handle(ActionEvent e){
				Platform.exit();
				System.exit(0);
			}
		});
		
		
	}
	
	public void disableGameBoardGUI(GridPane connect4Board, boolean state){
		for (int i = 0; i < 6; i++){
			for (int j = 0; j < 7; j++){
				connect4Board.getChildren().get(i*7+j).setDisable(state);
			}
		}
	}
	
	public void refreshGameBoardGUI(GridPane connect4Board, CFourInfo data){
		System.out.println("[Connect4Client] Refreshing GUI...");
		GameButton temp;
		for (int i = 0; i < 6; i++){
			for (int j = 0; j < 7; j++){
				if(data.boardMatrix[i][j] == 0){
					temp = (GameButton) connect4Board.getChildren().get(i*7+j);
					temp.setDisable(false);
					temp.setPlayer(0);
				}
				else if(data.boardMatrix[i][j] == 1){
					temp = (GameButton) connect4Board.getChildren().get(i*7+j);
					temp.setDisable(false);
					temp.setPlayer(1);
				}
				else if(data.boardMatrix[i][j] == 2){
					temp = (GameButton) connect4Board.getChildren().get(i*7+j);
					temp.setDisable(false);
					temp.setPlayer(2);
				}
				else if(data.boardMatrix[i][j] == 10){
					temp = (GameButton) connect4Board.getChildren().get(i*7+j);
					temp.setDisable(false);
					temp.setPlayer(10);
				}
				else if(data.boardMatrix[i][j] == 20){
					temp = (GameButton) connect4Board.getChildren().get(i*7+j);
					temp.setDisable(false);
					temp.setPlayer(20);
				}
				else{
					connect4Board.getChildren().get(i*7+j).setDisable(true);
				}					
			}
		}
	}
	
	@Override
	public void stop(){
		Platform.exit();
		System.exit(0);
	}
}
