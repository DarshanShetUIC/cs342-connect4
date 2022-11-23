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
	
	private Consumer<CFourInfo> callback;
	
	public Client(Consumer<CFourInfo> call){
		callback = call;
	}
	
	public void configureClient(String addy, int input_port){
		IP_Address = addy;
		port = input_port;
		data = new CFourInfo();
		System.out.println("[Client] Client-->Server Connection: " + addy + ":" + input_port);
	}
	
	public void run(){
		try{
			socket = new Socket(IP_Address, port);
			out = new ObjectOutputStream(socket.getOutputStream());
			in = new ObjectInputStream(socket.getInputStream());
			socket.setTcpNoDelay(true);
			
			while(true){
				CFourInfo temp = (CFourInfo) in.readObject();
				callback.accept(temp);
				data = temp;
			}
		}
		catch(Exception e){
			System.out.println("[Client] Could not connect to server / Socket issues...");
			System.exit(0);
		}
	}
	
	public void send(){
		// send a move onto the board
		try{
			out.writeObject(data);
		}
		catch(Exception e){
			System.out.println("[Client] Could not send data...");
		}
	}
}
