import java.io.FileNotFoundException;
import java.util.*;
import javafx.util.*;

public class GameLogic {

	public static int[][] updateBoardMatrix(int[][] boardMatrix){
		// Adjust board so next player can insert coin at that spot
		// Useful for GUI clients rather than game itself
		for(int i = 0; i < 5; i++){
			for(int j = 0; j < 7; j++){
				if(boardMatrix[i][j] == -1){
					if(boardMatrix[i+1][j] == 1 || boardMatrix[i+1][j] == 2){
						boardMatrix[i][j] = 0;
					}
				}
			}
		}
		return boardMatrix;
	}
	
	public static int[][] resetBoardMatrix(int[][] boardMatrix){
		for(int i = 0; i < 5; i++){
			for(int j = 0; j < 7; j++){
				boardMatrix[i][j] = -1;
			}
		}
		for(int j = 0; j < 7; j++){
			boardMatrix[5][j] = 0;
		}
		return boardMatrix;
	}
	
	public static boolean checkTie(CFourInfo data){
		if(data.boardMatrix[2][3] == 2 &&
			data.boardMatrix[3][3] == 2 &&
			data.boardMatrix[4][3] == 2 &&
			data.boardMatrix[5][3] == 2 &&
			data.boardMatrix[2][4] == 1 &&
			data.boardMatrix[3][4] == 1 &&
			data.boardMatrix[4][4] == 1 &&
			data.boardMatrix[5][4] == 1){return true;}
		return false;
	}
	
	public static boolean checkWin(CFourInfo data) {
		// P1 or P2 example win
		if(data.boardMatrix[2][6] == 2 &&
			data.boardMatrix[3][6] == 2 &&
			data.boardMatrix[4][6] == 2 &&
			data.boardMatrix[5][6] == 2){return true;}
		if(data.boardMatrix[2][0] == 1 &&
			data.boardMatrix[3][0] == 1 &&
			data.boardMatrix[4][0] == 1 &&
			data.boardMatrix[5][0] == 1){return true;}
		return false;
	}
	
	public static ArrayList<Pair<Integer, Integer>> getWinningCoordinates(CFourInfo data){
		int winningPlayer = data.boardMatrix[data.lastChangedRow][data.lastChangedCol];
		ArrayList<Pair<Integer, Integer>> res = new ArrayList<Pair<Integer, Integer>>();
		res.add(new Pair<>(0,2));
		res.add(new Pair<>(0,3));
		res.add(new Pair<>(0,4));
		res.add(new Pair<>(0,5));
		return res;
	}
}
