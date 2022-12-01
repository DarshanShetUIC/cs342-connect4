import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.FileNotFoundException;
import java.util.*;
import javafx.util.*;

class MyTest {
	
	CFourInfo data = new CFourInfo ();
	int player = 1;
	
	@Test
	public void start_board() {
		data.boardMatrix = GameLogic.resetBoardMatrix(data.boardMatrix);
		for(int i = 0; i < 5; i++){
			for(int j = 0; j < 7; j++){
				Assertions.assertEquals(-1, data.boardMatrix[i][j]);
			}
		}
		// enable row at bottom
		for(int j = 0; j < 7; j++){
			Assertions.assertEquals(0, data.boardMatrix[5][j]);  // able to move
		}
	}
	
	@Test
	public void horizontal_test() {
		data.boardMatrix[5][0] = player; data.boardMatrix[5][1] = player;
		data.boardMatrix[5][2] = player; data.boardMatrix[5][3] = player;
		data.lastChangedRow = 5;
		data.lastChangedCol = 0;
		Assertions.assertEquals(true, GameLogic.checkWin(data));
	}
	
	@Test
	public void get_coords_test1() {
		data.boardMatrix[5][0] = player; data.boardMatrix[5][1] = player;
		data.boardMatrix[5][2] = player; data.boardMatrix[5][3] = player;
		data.lastChangedRow = 5;
		data.lastChangedCol = 0;
		Assertions.assertEquals(true, GameLogic.checkWin(data));
		ArrayList<Pair<Integer, Integer>> res = new ArrayList<Pair<Integer, Integer>>();
		for (int i = 0; i < 4; i++) {
			Pair<Integer, Integer> p = new Pair<Integer, Integer> (5, i);
			res.add(p);
		}
		Assertions.assertEquals(5, res.get(0).getKey());
		Assertions.assertEquals(0, res.get(0).getValue());
		Assertions.assertEquals(5, res.get(1).getKey());
		Assertions.assertEquals(1, res.get(1).getValue());
		Assertions.assertEquals(5, res.get(2).getKey());
		Assertions.assertEquals(2, res.get(2).getValue());
		Assertions.assertEquals(5, res.get(3).getKey());
		Assertions.assertEquals(3, res.get(3).getValue());
		
		Assertions.assertEquals(res, GameLogic.getWinningCoordinates(data));
	}
	
	@Test
	public void vertical_test() {
		data.boardMatrix[0][5] = player; data.boardMatrix[1][5] = player;
		data.boardMatrix[2][5] = player; data.boardMatrix[3][5] = player;
		data.lastChangedRow = 0;
		data.lastChangedCol = 5;
		Assertions.assertEquals(true, GameLogic.checkWin(data));
	}
	
	@Test
	public void get_coords_test2() {
		data.boardMatrix[0][5] = player; data.boardMatrix[1][5] = player;
		data.boardMatrix[2][5] = player; data.boardMatrix[3][5] = player;
		data.lastChangedRow = 0;
		data.lastChangedCol = 5;
		Assertions.assertEquals(true, GameLogic.checkWin(data));
		ArrayList<Pair<Integer, Integer>> res = new ArrayList<Pair<Integer, Integer>>();
		for (int i = 0; i < 4; i++) {
			Pair<Integer, Integer> p = new Pair<Integer, Integer> (i, 5);
			res.add(p);
		}
		Assertions.assertEquals(0, res.get(0).getKey());
		Assertions.assertEquals(5, res.get(0).getValue());
		Assertions.assertEquals(1, res.get(1).getKey());
		Assertions.assertEquals(5, res.get(1).getValue());
		Assertions.assertEquals(2, res.get(2).getKey());
		Assertions.assertEquals(5, res.get(2).getValue());
		Assertions.assertEquals(3, res.get(3).getKey());
		Assertions.assertEquals(5, res.get(3).getValue());
		
		Assertions.assertEquals(res, GameLogic.getWinningCoordinates(data));
	}
	
	@Test
	public void right_diagonal_test() {
		data.boardMatrix[0][0] = player; data.boardMatrix[1][1] = player;
		data.boardMatrix[2][2] = player; data.boardMatrix[3][3] = player;
		data.lastChangedRow = 2;
		data.lastChangedCol = 2;
		Assertions.assertEquals(true, GameLogic.checkWin(data));
	}
	
	@Test
	public void left_diagonal_test() {
		data.boardMatrix[0][5] = player; data.boardMatrix[1][4] = player;
		data.boardMatrix[2][3] = player; data.boardMatrix[3][2] = player;
		data.lastChangedRow = 1;
		data.lastChangedCol = 4;
		Assertions.assertEquals(true, GameLogic.checkWin(data));
	}
	
	

}
