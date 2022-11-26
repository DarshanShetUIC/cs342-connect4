import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.lang.Thread;
import java.util.HashMap;
import java.util.function.Consumer;
import java.util.*;

public class Server{
	
	int newPlayerID;
	HashMap<Integer, ClientInstance> clients = new HashMap<Integer, ClientInstance>();
	
	int port;
	ServerInstance server;
	
	CFourInfo data;
	
	private Consumer<CFourInfo> callback;
	
	public Server(Consumer<CFourInfo> call){
		callback = call;
	}
	
	public void createServerInstance(int input_port){
		newPlayerID = 1;
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
					ClientInstance c = new ClientInstance(mysocket.accept(), newPlayerID);
					System.out.println("[Server] P" + newPlayerID + " has joined the game...");
					data.gameStatus = "P" + newPlayerID + " has joined the game...";
					callback.accept(data);
					clients.put(newPlayerID, c);
					System.out.println("[Server] Number of players: " + clients.size());
					c.start();
					newPlayerID++;
					if(newPlayerID == 4){newPlayerID = 3;}
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
					Timer timer = new Timer();
					timer.schedule(new TimerTask(){
						public void run()
						{
							try{
								CFourInfo temp = new CFourInfo();
								temp.gameStatus = "2 players connected, P1 goes first...";
								temp.playerTurn = 1;
								callback.accept(temp);
								updateClients(temp);
							}
							catch(Exception f){
								System.out.println("[Server] Error starting game...");
							}
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
					newPlayerID = id;
					CFourInfo temp = new CFourInfo();
					temp.gameStatus = "P" + id + " has left the server...";;
					callback.accept(temp);
					updateClients(temp);
					System.out.println("[Server] Number of players: " + clients.size());
					data.gameStatus = "Number of players: " + clients.size();
					callback.accept(temp);
					break;
				}
			}
		}
		
		public void updateClients(CFourInfo msg){
			try{
				if(clients.size() == 2){
					clients.get(1).out.writeObject(msg);
					clients.get(2).out.writeObject(msg);
				}
				else if(clients.size() == 1){
					if(clients.containsKey(2)){
						clients.get(2).out.writeObject(msg);
					}
					else if(clients.containsKey(1)){
						clients.get(1).out.writeObject(msg);
					}
				}
			}
			catch(Exception e){
				System.out.println("[Server] Could not update clients...");
			}
		}
	}
}
