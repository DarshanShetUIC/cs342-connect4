import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;
import java.util.function.Consumer;

public class CFourInfo implements Serializable{
	// Message to be displayed about P1's move
	String p1Plays;
	// Message to be displayed about P2's move
	String p2Plays;
	// For server to determine if two players exist
	boolean have2Players;
	// Remember which player goes next, 1 for P1 and 2 for P2
	int playerTurn;
	// If game is in play, record truw otherwise false
	boolean gameInProgress;
	
}
