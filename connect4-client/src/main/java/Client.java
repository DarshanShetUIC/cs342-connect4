import java.net.Socket;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.Thread;
import java.util.Random;


public class Client extends Thread{
	Socket socket;
	ObjectOutputStream out;
	ObjectInputStream in;
	
	CFourInfo data;
	
	public Client(){
		data = new CFourInfo();
		data.gameStatus = ("Everything is awesome!");
		//Random rand = new Random();
		// data.setVal(rand.nextInt(50));
	}
	
	public void run(){
		try{
			// TODO: first parameter is IP address entered, and second is port # entered
			socket = new Socket("127.0.0.1",5555);
			out = new ObjectOutputStream(socket.getOutputStream());
			in = new ObjectInputStream(socket.getInputStream());
			socket.setTcpNoDelay(true);
			
			while(true){
				CFourInfo temp = (CFourInfo) in.readObject();
				// TODO: 
				//this is where we update game board
				System.out.println(temp.gameStatus + " ");
			}
		}
		catch(Exception e){
			System.out.println("[Client] Could not connect to server / Socket issues...");
			System.exit(0);
		}
	}
	
	public void send(){  // send a move onto the board
		try{
			out.writeObject(data);
			// TODO: send moves to server for other player to see
		}
		catch(Exception e){
			System.out.println("[Client] Could not send data...");
		}
	}
}
