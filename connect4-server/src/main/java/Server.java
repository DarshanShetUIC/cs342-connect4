import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.lang.Thread;
import java.util.HashMap;
import java.util.function.Consumer;
import java.util.*;

public class Server{
	
	int playerCount;
	HashMap<Integer, ClientInstance> clients = new HashMap<Integer, ClientInstance>();
	
	int port;
	ServerInstance server;
	
	CFourInfo data;
	
	private Consumer<CFourInfo> callback;
	
	public Server(Consumer<CFourInfo> call){
		callback = call;
	}
	
	public void createServerInstance(int input_port){
		playerCount = 1;
		port = input_port;
		data = new CFourInfo();
		server = new ServerInstance();
		server.start();
	}
	
	public class ServerInstance extends Thread{
		public void run() {
			try{
				// Create socket for players to connect to server
				ServerSocket mysocket = new ServerSocket(port);
				System.out.println("[Server] Server waiting for P1 and P2...");
				data.gameStatus = "Server waiting for P1 and P2...";
				callback.accept(data);
				
				// Check if players joined game and allow them to connect. If full, reject.
				while(true){
					ClientInstance c = new ClientInstance(mysocket.accept(), playerCount);
					System.out.println("[Server] P" + playerCount + " has joined the game...");
					data.gameStatus = "P" + playerCount + " has joined the game...";
					callback.accept(data);
					clients.put(playerCount, c);
					System.out.println("[Server] Number of players: " + clients.size());
					c.start();
					playerCount++;
					if(playerCount >= 4){playerCount = 3;}
				}
			}
			catch(Exception e){
				System.out.println("[Server] Socket failed...");
			}
		}
	}
	
	public class ClientInstance extends Thread{
		
		int id;
		Socket connection;
		ObjectInputStream in;
		ObjectOutputStream out;
		
		ClientInstance(Socket s, int id){
			this.connection = s;
			this.id = id;
		}
		
		public void run(){
			try{
				in = new ObjectInputStream(connection.getInputStream());
				out = new ObjectOutputStream(connection.getOutputStream());
				connection.setTcpNoDelay(true);
				
				
				if(clients.size() >= 3){
					data.gameStatus = "Server is full. P3 will be kicked out...";
					callback.accept(data);
					out.writeObject(data);
				}
				else{
					// Assign player ID to new player
					data.gameStatus = "Your Player ID is " + id + ". Waiting for other players...";
					out.writeObject(data);
				}
				
				if(clients.size() == 2){
					data.gameStatus = "2 players connected, P1 goes first...";
					data.playerTurn = 1;
					Timer timer = new Timer();
					timer.schedule(new TimerTask(){
						public void run()
						{
							callback.accept(data);
							out.writeObject(data);
						}
					}, 1000);
				}
			}
			catch(Exception e){
				System.out.println("[Server] IO stream could not open...");
			}
			while(true){
				try{
					data = (CFourInfo) in.readObject();
					callback.accept(data);
					if(id == 1){
						if(clients.containsKey(2)){
							clients.get(2).out.writeObject(data);
						}
					}
					else if(id == 2){
						if(clients.containsKey(1)){
							clients.get(1).out.writeObject(data);
						}
					}
				}
				catch(Exception e){
					System.out.println("[Server] P" + id + " has left the server");
					clients.remove(id);
					playerCount = id;
					data.gameStatus = "P" + id + " has left the server...";
					callback.accept(data);
					break;
				}
			}
		}
	}
}
