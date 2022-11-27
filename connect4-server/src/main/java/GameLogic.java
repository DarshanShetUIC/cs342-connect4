import java.io.FileNotFoundException;
import java.util.*;

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
	
	public static boolean checkWin(CFourInfo data) {
		// P1 example win
		if(data.boardMatrix[2][6] == 2 &&
			data.boardMatrix[3][6] == 2 &&
			data.boardMatrix[4][6] == 2 &&
			data.boardMatrix[5][6] == 2){return true;}
		if(data.boardMatrix[5][0] == 1 &&
			data.boardMatrix[5][1] == 1 &&
			data.boardMatrix[5][2] == 1 &&
			data.boardMatrix[5][3] == 1){return true;}
		return false;
	}
}
