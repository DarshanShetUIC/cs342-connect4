import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.lang.Thread;
import java.util.ArrayList;
import java.util.function.Consumer;
import java.util.LinkedList;

public class Server{
	
	LinkedList<Integer> playerSlots = new LinkedList<Integer>();
	ArrayList<ClientInstance> clients = new ArrayList<ClientInstance>();
	
	int port;
	ServerInstance server;
	
	CFourInfo data;
	
	private Consumer<CFourInfo> callback;
	
	public Server(Consumer<CFourInfo> call){
		callback = call;
	}
	
	public void createServerInstance(int input_port){
		playerSlots.add(1);
		playerSlots.add(2);
		printPlayerSlots(playerSlots);
		port = input_port;
		data = new CFourInfo();
		server = new ServerInstance();
		server.start();
	}
	
	public void printPlayerSlots(LinkedList<Integer> list){
		System.out.print("Slots available: ");
		for(Integer slot : list){
			System.out.print(slot + " ");
		}
		System.out.println("");
	}
	
	public class ServerInstance extends Thread{
		public void run() {
			try{
				// Create socket for players to connect to server
				ServerSocket mysocket = new ServerSocket(port);
				System.out.println("Server waiting for P1 and P2...");
				data.gameStatus = "Server waiting for P1 and P2...";
				callback.accept(data);
				// Check if players joined game and allow them to connect. If full, quietly reject.
				while(true){
					if(playerSlots.size() != 0){
						int newPlayerID = playerSlots.remove();
						ClientInstance c = new ClientInstance(mysocket.accept(), newPlayerID, true);
						System.out.println("[Server] P" + newPlayerID + " has joined the game...");
						data.gameStatus = "P" + newPlayerID + " has joined the game...";
						printPlayerSlots(playerSlots);
						callback.accept(data);
						clients.add(c);
						c.start();
					}
					else{
						// Since two players are currently playing
						// Allow the other player to connect but reject it later on
						ClientInstance c = new ClientInstance(mysocket.accept(), 0, false);
						System.out.println("[Server] Another player connecting. Rejecting...");
						data.gameStatus = "Another player connecting. Rejecting...";
						callback.accept(data);
						break;
					}
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
		boolean canJoin;
		
		ClientInstance(Socket s, int count, boolean joinable){
			this.connection = s;
			this.count = count;	
			canJoin = joinable;
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
				// If server has two players already and a third one tries to join,
				// connect with that third player but send it an info message
				// stating server is full
				if(!canJoin){
					CFourInfo temp = new CFourInfo();
					temp.gameStatus = "Server full...";
					out.writeObject(temp);
					break;
				}
				// Let new player know what their ID is
				data.gameStatus = "Your Player ID is " + count + ". Waiting for others to join...";
				out.writeObject(data);
				// Wait for a few seconds for player to read message
				// Check if two players are in server, then start game
				Thread.sleep(3000);
				if(playerSlots.size() == 0){
					data.gameStatus = "Go Player " + clients.get(0).count + ". You start first!";
					data.playerTurn = clients.get(0).count;
					out.writeObject(data);
				}
			}
			catch(Exception e){
				System.out.println("[ClientThread] IO stream could not open...");
			}
			while(true){
				try{
					CFourInfo temp = (CFourInfo) in.readObject();
					callback.accept(temp);
					updateClients(data);
				}
				catch(Exception e){
					System.out.println("[ClientThread] A client has left the server");
					clients.remove(this);
					playerSlots.add(count);
					printPlayerSlots(playerSlots);
					data.gameStatus = "Error: P" + count + " has left the server...";
					callback.accept(data);
					updateClients(data);
					break;
				}
			}
		}
	}
}
