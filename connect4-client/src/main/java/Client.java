import java.net.Socket;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.Thread;
import java.util.Random;
import java.util.function.Consumer;

public class Client extends Thread{
	
	Socket socket;
	ObjectOutputStream out;
	ObjectInputStream in;
	
	String IP_Address;
	int port;
	CFourInfo data;
	
	int playerID;
	
	private Consumer<CFourInfo> callback;
	
	public Client(Consumer<CFourInfo> call){
		callback = call;
	}
	
	public void updatePlayerID(int id){
		playerID = id;
		System.out.println("[Client] Player ID set to: " + playerID);
	}
	
	public void configureClient(String addy, int input_port){
		IP_Address = addy;
		port = input_port;
		data = new CFourInfo();
		System.out.println("[Client] Configuring Client-->Server Connection: " + addy + ":" + input_port);
	}
	
	public void run(){
		// attempt to connect to server
		try{
			socket = new Socket(IP_Address, port);
			out = new ObjectOutputStream(socket.getOutputStream());
			in = new ObjectInputStream(socket.getInputStream());
			socket.setTcpNoDelay(true);
			
			// read data from server and pass onto UI to update gameboard and other UI elements
			while(true){
				data = (CFourInfo) in.readObject();
				System.out.println("[Client] Server sent a data packet");
				System.out.println("[Client] Message: " + data.gameStatus);
				callback.accept(data);
			}
		}
		catch(Exception e){
			System.out.println("[Client] Could not connect to server / Socket issues...");
			System.exit(0);
		}
	}
	
	public void send(){
		// send current board info to server / other player
		try{
			out.writeObject(data);
		}
		catch(Exception e){
			System.out.println("[Client] Could not send data...");
		}
	}
	
	public void end(){
		System.exit(0);
	}
}
