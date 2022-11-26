import java.io.Serializable;

public class CFourInfo implements Serializable{
	// Message to be displayed about game status
	String gameStatus;
	// Remember which player goes next, 1 for P1 and 2 for P2
	int playerTurn;
	// Each player will get their own copy of CFourInfo from server
	// The server decides whose board should be enabled/disabled
	// To do this, the server must inform each player what their id is
	int playerID;
	// If game is in play, record true otherwise false
	boolean gameInProgress;
	// game board in matrix format
	// -1 for unfillable spot
	// 0 for fillable spot
	// 1 for P1 coin
	// 2 for P2 coin
	// 10 for P1 winning coin
	// 20 for P2 winning coin
	int boardMatrix[][];
	// remember last move
	int lastChangedRow;
	int lastChangedCol;
	
	public CFourInfo(){
		gameStatus = "";
		playerTurn = 0;
		gameInProgress = false;
		boardMatrix = new int[6][7];
		// Set the board so player can click on any spot on bottom row;
		for(int i = 0; i < 5; i++){
			for(int j = 0; j < 7; j++){
				boardMatrix[i][j] = -1;
			}
		}
		for(int j = 0; j < 7; j++){
			boardMatrix[5][j] = 0;
		}
	}
}
