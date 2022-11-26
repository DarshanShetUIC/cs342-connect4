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
	
	public static boolean checkWin(CFourInfo data) {
	    final int HEIGHT = 6;
	    final int WIDTH = 7;
	    final int EMPTY_SLOT = 0;
	    for (int r = 0; r < HEIGHT; r++) { // iterate rows, bottom to top
	        for (int c = 0; c < WIDTH; c++) { // iterate columns, left to right
	            int player = data.boardMatrix[r][c];
	            if (player == EMPTY_SLOT)
	                continue; // don't check empty slots

	            if (c + 3 < WIDTH &&
	                player == data.boardMatrix[r][c+1] && // look right
	                player == data.boardMatrix[r][c+2] &&
	                player == data.boardMatrix[r][c+3])
	                return true;
	            if (r + 3 < HEIGHT) {
	                if (player == data.boardMatrix[r+1][c] && // look up
	                    player == data.boardMatrix[r+2][c] &&
	                    player == data.boardMatrix[r+3][c])
	                    return true;
	                if (c + 3 < WIDTH &&
	                    player == data.boardMatrix[r+1][c+1] && // look up & right
	                    player == data.boardMatrix[r+2][c+2] &&
	                    player == data.boardMatrix[r+3][c+3])
	                    return true;
	                if (c - 3 >= 0 &&
	                    player == data.boardMatrix[r+1][c-1] && // look up & left
	                    player == data.boardMatrix[r+2][c-2] &&
	                    player == data.boardMatrix[r+3][c-3])
	                    return true;
	            }
	        }
	    }
	    return false; // no winner found
	}
}
