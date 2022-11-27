import java.io.Serializable;

public class CFourInfo implements Serializable{
	// Message to be displayed about game status
	String gameStatus;
	// Remember which player goes next, 1 for P1 and 2 for P2
	int playerTurn;
	// If game is in play, record true otherwise false
	boolean gameInProgress;
	// game board in matrix format
	// -1 for unfillable spot
	// 0 for fillable spot
	// 1 for P1 coin
	// 2 for P2 coin
	// 11 for P1 winning coin
	// 12 for P2 winning coin
	int boardMatrix[][];
	// remember last move
	int lastChangedRow;
	int lastChangedCol;
	
	public CFourInfo(){
		lastChangedRow = -999;
		lastChangedCol = -999;
		gameStatus = "";
		playerTurn = 0;
		gameInProgress = false;
		boardMatrix = new int[6][7];
		// Set the board so player can click on any spot on bottom row;
		boardMatrix = GameLogic.resetBoardMatrix(boardMatrix);
	}
	
	public void reset(){
		lastChangedRow = -999;
		lastChangedCol = -999;
		gameStatus = "";
		playerTurn = 0;
		gameInProgress = false;
		// Set the board so player can click on any spot on bottom row;
		boardMatrix = GameLogic.resetBoardMatrix(boardMatrix);
	}
}
