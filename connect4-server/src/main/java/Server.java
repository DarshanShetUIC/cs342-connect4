import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.lang.Thread;
import java.util.ArrayList;

public class Server{
	int count = 1;
	ArrayList<ClientInstance> clients = new ArrayList<ClientInstance>();
	
	ServerInstance server;
	
	public Server(){
		server = new ServerInstance();
		server.start(); 
	}
	
	public class ServerInstance extends Thread{
		public void run() {
			try{
				// TODO: getText of Port # text box
				ServerSocket mysocket = new ServerSocket(5555);
				System.out.println("Server is waiting for a client!");
				
				while(true){
					ClientInstance c = new ClientInstance(mysocket.accept(), count);
					System.out.println("[Server] New client has joined the server");
					clients.add(c);
					c.start();
					// start user interface
					count++;
				}
			}
			catch(Exception e){
				System.out.println("[Server] Socket failed...");
			}
		}
	}
	
	public class ClientInstance extends Thread{
		Socket connection;
		int count;
		ObjectInputStream in;
		ObjectOutputStream out;
		
		ClientInstance(Socket s, int count){
			this.connection = s;
			this.count = count;	
		}
		
		
		
		public void updateClients(CFourInfo data){ // update the board, heavy changes
			for(int i = 0; i < clients.size(); i++) {
				ClientInstance t = clients.get(i);
				try{
					t.out.writeObject(data);
				}
				catch(Exception e){
					System.out.println("[ClientThread] Could not update clients...");
				}
			}
		}
		
		public void run(){
			try{
				in = new ObjectInputStream(connection.getInputStream());
				out = new ObjectOutputStream(connection.getOutputStream());
				connection.setTcpNoDelay(true);
			}
			catch(Exception e){
				System.out.println("[ClientThread] IO stream could not open...");
			}
			
			while(true){
				try{
					CFourInfo data = (CFourInfo) in.readObject();
					// TODO: check if the player who last moved won, make function that is
					// supported by C4Logic
					updateClients(data);
				}
				catch(Exception e){
					System.out.println("[ClientThread] A client has left the server");
					clients.remove(this);
					break;
				}
			}
		}
	}
}
