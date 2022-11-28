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
		final int HEIGHT = 6;
		final int WIDTH = 7;
		for (int r = 0; r < HEIGHT; r++)
		{
			for (int c = 0; c < WIDTH; c++)
			{
				if(data.boardMatrix[r][c] == -1
				|| data.boardMatrix[r][c] == 0){return false;}
			}
		}
		return true;
	}
	
	public static boolean checkWin(CFourInfo data)
	{	
		final int HEIGHT = 6;
		final int WIDTH = 7;
		int PLAYER = data.boardMatrix[data.lastChangedRow][data.lastChangedCol];
		
		// Check for horizontal win
		for (int r = 0; r < HEIGHT; r++)
		{
			for (int c = 0; c < 4; c++)
			{
				if(data.boardMatrix[r][c+0] == PLAYER
				&& data.boardMatrix[r][c+1] == PLAYER
				&& data.boardMatrix[r][c+2] == PLAYER
				&& data.boardMatrix[r][c+3] == PLAYER){return true;}
			}
		}
		
		// Check for vertical win
		for (int c = 0; c < WIDTH; c++)
		{
			for (int r = 0; r < 3; r++)
			{
				if(data.boardMatrix[r+0][c] == PLAYER
				&& data.boardMatrix[r+1][c] == PLAYER
				&& data.boardMatrix[r+1][c] == PLAYER
				&& data.boardMatrix[r+3][c] == PLAYER){return true;}
			}
		}
		
		// Check for right diagonal win
		for (int c = 0; c <= 3; c++)
		{ 
			for (int r = 3; r < HEIGHT; r++)
			{
				if(data.boardMatrix[r][c] == PLAYER
				&& data.boardMatrix[r-1][c+1] == PLAYER
				&& data.boardMatrix[r-2][c+2] == PLAYER
				&& data.boardMatrix[r-3][c+3] == PLAYER){return true;}
			}
		}
		
		// Check for left diagonal win
		for (int c = 3; c < WIDTH; c++)
		{ 
			for (int r = 3; r < HEIGHT; r++)
			{
				if(data.boardMatrix[r][c] == PLAYER
				&& data.boardMatrix[r-1][c-1] == PLAYER
				&& data.boardMatrix[r-2][c-2] == PLAYER
				&& data.boardMatrix[r-3][c-3] == PLAYER){return true;}
			}
		}
		
		return false;
	}
	
	public static ArrayList<Pair<Integer, Integer>> getWinningCoordinates(CFourInfo data){
		ArrayList<Pair<Integer, Integer>> res = new ArrayList<Pair<Integer, Integer>>();
		final int HEIGHT = 6;
		final int WIDTH = 7;
		int PLAYER = data.boardMatrix[data.lastChangedRow][data.lastChangedCol];
		
		// Check for horizontal win
		for (int r = 0; r < HEIGHT; r++)
		{
			for (int c = 0; c < 4; c++)
			{
				if(data.boardMatrix[r][c+0] == PLAYER
				&& data.boardMatrix[r][c+1] == PLAYER
				&& data.boardMatrix[r][c+2] == PLAYER
				&& data.boardMatrix[r][c+3] == PLAYER)
				{
					res.add(new Pair<>(r,c+0));
					res.add(new Pair<>(r,c+1));
					res.add(new Pair<>(r,c+2));
					res.add(new Pair<>(r,c+3));
					return res;
				}
			}
		}
		
		// Check for vertical win
		for (int c = 0; c < WIDTH; c++)
		{
			for (int r = 0; r < 3; r++)
			{
				if(data.boardMatrix[r+0][c] == PLAYER
				&& data.boardMatrix[r+1][c] == PLAYER
				&& data.boardMatrix[r+1][c] == PLAYER
				&& data.boardMatrix[r+3][c] == PLAYER)
				{
					res.add(new Pair<>(r+0,c));
					res.add(new Pair<>(r+1,c));
					res.add(new Pair<>(r+2,c));
					res.add(new Pair<>(r+3,c));
					return res;
				}
			}
		}
		
		// Check for right diagonal win
		for (int c = 0; c <= 3; c++)
		{ 
			for (int r = 3; r < HEIGHT; r++)
			{
				if(data.boardMatrix[r][c] == PLAYER
				&& data.boardMatrix[r-1][c+1] == PLAYER
				&& data.boardMatrix[r-2][c+2] == PLAYER
				&& data.boardMatrix[r-3][c+3] == PLAYER)
				{
					res.add(new Pair<>(r,c));
					res.add(new Pair<>(r-1,c+1));
					res.add(new Pair<>(r-2,c+2));
					res.add(new Pair<>(r-3,c+3));
					return res;
				}
			}
		}
		
		// Check for left diagonal win
		for (int c = 3; c < WIDTH; c++)
		{ 
			for (int r = 3; r < HEIGHT; r++)
			{
				if(data.boardMatrix[r][c] == PLAYER
				&& data.boardMatrix[r-1][c-1] == PLAYER
				&& data.boardMatrix[r-2][c-2] == PLAYER
				&& data.boardMatrix[r-3][c-3] == PLAYER)
				{
					res.add(new Pair<>(r,c));
					res.add(new Pair<>(r-1,c-1));
					res.add(new Pair<>(r-2,c-2));
					res.add(new Pair<>(r-3,c-3));
					return res;
				}
			}
		}
		
		return res;
	}
}
