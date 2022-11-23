import java.io.FileNotFoundException;
import java.util.*;

public class GameLogic {
	
	public static boolean win(CFourInfo data) {
		// horizontalCheck 
	    for (int j = 0; j< 6-3; j++ ){
	        for (int i = 0; i<7; i++){
	            if (data.boardMatrix[i][j] == data.playerTurn && data.boardMatrix[i][j+1] == data.playerTurn && data.boardMatrix[i][j+2] == data.playerTurn && data.boardMatrix[i][j+3] == data.playerTurn){
	                return true;
	            }           
	        }
	    }
	    // verticalCheck
	    for (int i = 0; i<6-3; i++ ){
	        for (int j = 0; j<7; j++){
	            if (data.boardMatrix[i][j] == data.playerTurn && data.boardMatrix[i+1][j] == data.playerTurn && data.boardMatrix[i+2][j] == data.playerTurn && data.boardMatrix[i+3][j] == data.playerTurn){
	                return true;
	            }           
	        }
	    }
	    // ascendingDiagonalCheck 
	    for (int i=3; i<6; i++){
	        for (int j=0; j<7-3; j++){
	            if (data.boardMatrix[i][j] == data.playerTurn && data.boardMatrix[i-1][j+1] == data.playerTurn && data.boardMatrix[i-2][j+2] == data.playerTurn && data.boardMatrix[i-3][j+3] == data.playerTurn)
	                return true;
	        }
	    }
	    // descendingDiagonalCheck
	    for (int i=3; i<6; i++){
	        for (int j=3; j<7; j++){
	            if (data.boardMatrix[i][j] == data.playerTurn && data.boardMatrix[i-1][j-1] == data.playerTurn && data.boardMatrix[i-2][j-2] == data.playerTurn && data.boardMatrix[i-3][j-3] == data.playerTurn)
	                return true;
	        }
	    }
	    return false;
	}	
}
