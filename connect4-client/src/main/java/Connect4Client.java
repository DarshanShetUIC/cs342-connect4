import javafx.application.*;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.*;
import javafx.geometry.*;
import javafx.event.*;
import javafx.scene.image.*;
import javafx.util.*;
import java.util.*;
import java.io.*;

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
						/*****************************************************
							START OF GAME LOGIC PART ONE
						*****************************************************/
						
						// Add player input to gameboard and update board
						client.data.lastChangedRow = button.r;
						client.data.lastChangedCol = button.c;
						client.data.boardMatrix[button.r][button.c] = client.playerID;
						client.data.boardMatrix = GameLogic.updateBoardMatrix(client.data.boardMatrix);
						
						// Check if move is a win
						// If win, send to client about it
						
						if(GameLogic.checkTie(client.data)){
							
							client.data.playerTurn = 0;
							client.data.gameStatus = "P"+client.playerID+" made move at "
								+button.r+","+button.c+". Tie between P1 and P2";
							client.data.gameInProgress = false;
							client.send();
							
							moveInfo.setText(client.data.gameStatus);
							playerTurn.setText("0");
							refreshGameBoardGUI(connect4Board, client.data);
							disableGameBoardGUI(connect4Board, true);
							System.out.println("[Connect4Client] Switching to end screen for P" 
								+ client.playerID);
							Timer timer = new Timer();
							timer.schedule(new TimerTask(){
								public void run()
								{
									Platform.runLater(() -> {
										endOfGameLbl.setText("P1 P2 Tie");
										primaryStage.setScene(endGameScreen);
									});
								}
							}, 3000);
						}
						
						else if(GameLogic.checkWin(client.data)){
							
							/* GET WIN COORDINATES HERE */
							
							client.data.playerTurn = 0;
							client.data.gameStatus = "P"+client.playerID+" made move at "
								+button.r+","+button.c+". P" + client.playerID + " wins...";
							client.data.gameInProgress = false;
							client.send();
							
							moveInfo.setText(client.data.gameStatus);
							playerTurn.setText("0");
							refreshGameBoardGUI(connect4Board, client.data);
							disableGameBoardGUI(connect4Board, true);
							System.out.println("[Connect4Client] Switching to end screen for P" 
								+ client.playerID);
							Timer timer = new Timer();
							timer.schedule(new TimerTask(){
								public void run()
								{
									Platform.runLater(() -> {
										if(client.playerID == 1){
											endOfGameLbl.setText("P1 Wins");
										}
										else{
											endOfGameLbl.setText("P2 Wins");
										}
										primaryStage.setScene(endGameScreen);
									});
								}
							}, 3000);
						}
						else{
							if(client.playerID == 1){
								client.data.playerTurn = 2;
							}
							else{
								client.data.playerTurn = 1;
							}
							client.data.gameStatus = "P"+client.playerID+" made move at "
								+button.r+","+button.c+". Waiting for P"+client.data.playerTurn+"...";
							client.data.gameInProgress = true;
							client.send();
							moveInfo.setText(client.data.gameStatus);
							playerTurn.setText(Integer.toString(client.data.playerTurn));
							refreshGameBoardGUI(connect4Board, client.data);
							disableGameBoardGUI(connect4Board, true);
						}
						/*****************************************************
							END OF GAME LOGIC PART ONE
						*****************************************************/
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
						/*****************************************************
							START OF GAME LOGIC PART TWO
						*****************************************************/
						// Set player id once client connects to server
						// Server determines player's ID
						if(data.gameStatus.substring(0,4).equals("Your")){
							System.out.println("[Connect4Client] Case 1");
							client.updatePlayerID(Integer.parseInt(data.gameStatus.substring(18,19)));
							primaryStage.setTitle("Player " + Integer.toString(client.playerID));
							moveInfo.setText(data.gameStatus);
							playerTurn.setText("0");
							disableGameBoardGUI(connect4Board, true);
						}
						// If server is full, exit because this is Player 3
						else if(data.gameStatus.substring(0,6).equals("Server")){
							System.out.println("[Connect4Client] Case 2");
							moveInfo.setText(data.gameStatus);
							playerTurn.setText("0");
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
						// If connection error, display error and quit after 3 seconds
						else if(data.gameStatus.substring(0,6).equals("Error:")){
							System.out.println("[Connect4Client] Case 3");
							moveInfo.setText(data.gameStatus);
							playerTurn.setText("0");
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
							data.gameInProgress = true;
							playerTurn.setText(Integer.toString(data.playerTurn));
							if(data.playerTurn == client.playerID){
								refreshGameBoardGUI(connect4Board, data);
							}
						}
						// Received players move
						else if(data.gameStatus.substring(0,1).equals("P") 
							&& data.gameStatus.substring(3,7).equals("made")
							&& data.gameInProgress == true){
							System.out.println("[Connect4Client] Case 5");
							moveInfo.setText(data.gameStatus);
							playerTurn.setText(Integer.toString(data.playerTurn));
							if(data.playerTurn == client.playerID){
								refreshGameBoardGUI(connect4Board, data);
							}
						}
						// Received players move
						else if(data.gameStatus.substring(0,1).equals("P") 
							&& data.gameStatus.substring(21,24).equals("Tie")
							&& data.gameInProgress == false){
							System.out.println("[Connect4Client] Case 8");
							moveInfo.setText(data.gameStatus);
							playerTurn.setText("0");
							refreshGameBoardGUI(connect4Board, data);
							disableGameBoardGUI(connect4Board, true);
							System.out.println("[Connect4Client] Switching to end screen for P" 
								+ client.playerID);
							Timer timer = new Timer();
							timer.schedule(new TimerTask(){
								public void run()
								{
									Platform.runLater(() -> {
										endOfGameLbl.setText("P1 P2 Tie");
										primaryStage.setScene(endGameScreen);
									});
								}
							}, 3000);
						}
						// If another player left the server
						// Server will notify this player
						// With no game to play, this player will quit
						else if(data.gameStatus.substring(0,1).equals("P") 
							&& data.gameStatus.substring(3,6).equals("has")){
							System.out.println("[Connect4Client] Case 6");
							moveInfo.setText(data.gameStatus.substring(0,22)+"...");
							playerTurn.setText("0");
							disableGameBoardGUI(connect4Board, true);
							Timer timer = new Timer();
							timer.schedule(new TimerTask(){
								public void run()
								{
									Platform.runLater(() -> {
										endOfGameLbl.setText("Game Ended");
										primaryStage.setScene(endGameScreen);
									});
								}
							}, 3000);
						}
						// If other player wins, stop game and disable board
						// Show winning coins and switch to end game screen after 3 seconds
						else if(data.gameStatus.substring(0,1).equals("P")
							&& data.gameStatus.substring(24,28).equals("wins")
							&& data.gameInProgress == false){
							System.out.println("[Connect4Client] Case 7");
							moveInfo.setText(data.gameStatus);
							playerTurn.setText(Integer.toString(data.playerTurn));
							refreshGameBoardGUI(connect4Board, data);
							disableGameBoardGUI(connect4Board, true);
							System.out.println("[Connect4Client] Switching to end screen for P" 
								+ client.playerID);
							Timer timer = new Timer();
							timer.schedule(new TimerTask(){
								public void run()
								{
									Platform.runLater(() -> {
										if(client.playerID == 1){
											endOfGameLbl.setText("P2 Wins");
										}
										else{
											endOfGameLbl.setText("P1 Wins");
										}
										primaryStage.setScene(endGameScreen);
									});
								}
							}, 3000);
						}
						/*****************************************************
							END OF GAME LOGIC PART TWO
						*****************************************************/
					});
				});
				client.configureClient(ipInput.getText(), portSpinner.getValue());
				client.start();
			}
		});
		
		restartBtn.setOnAction(new EventHandler<ActionEvent>(){
			@Override public void handle(ActionEvent e){
				try{
					Process runtime = Runtime.getRuntime().exec("mvn exec:java");
				}
				catch(Exception f){
					System.out.println("[Connect4Client] Could not restart...");
				}
				Platform.exit();
				System.exit(0);
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
				else if(data.boardMatrix[i][j] == -1){
					temp = (GameButton) connect4Board.getChildren().get(i*7+j);
					temp.setDisable(true);
					temp.setPlayer(0);
				}
				else if(data.boardMatrix[i][j] == 1){
					temp = (GameButton) connect4Board.getChildren().get(i*7+j);
					temp.setDisable(true);
					temp.setPlayer(1);
				}
				else if(data.boardMatrix[i][j] == 2){
					temp = (GameButton) connect4Board.getChildren().get(i*7+j);
					temp.setDisable(true);
					temp.setPlayer(2);
				}
				else if(data.boardMatrix[i][j] == 11){
					temp = (GameButton) connect4Board.getChildren().get(i*7+j);
					temp.setDisable(true);
					temp.setPlayer(11);
				}
				else if(data.boardMatrix[i][j] == 12){
					temp = (GameButton) connect4Board.getChildren().get(i*7+j);
					temp.setDisable(true);
					temp.setPlayer(12);
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
