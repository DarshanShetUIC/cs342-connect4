import java.io.Serializable;

public class CFourInfo implements Serializable{
	// Message to be displayed about P1's move
	String gameStatus;
	// Message to be displayed about P2's move
	boolean have2Players;
	// Remember which player goes next, 1 for P1 and 2 for P2
	int playerTurn;
	// If game is in play, record truw otherwise false
	boolean gameInProgress;
	// game board in matrix format
	// 0 for unfilled
	// 1 for P1 coin
	// 2 for P2 coin
	int boardMatrix[][];
	
	public CFourInfo(){
		gameStatus = "";
		have2Players = false;
		playerTurn = 0;
		gameInProgress = false;
		boardMatrix = new int[6][7];
	}
	
	public void updateBoardMatrix(int row, int col, int coin){
		boardMatrix[row][col] = coin;
	}
}
