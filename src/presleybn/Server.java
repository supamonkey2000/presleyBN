package presleybn;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.Scanner;

@SuppressWarnings("resource")
public class Server {
	
	private int PORT = 65530;
	private int maxClients = Integer.MAX_VALUE;
	private int numberOfClients = 0;
	private ServerSocket serverSocket = null;
	private ArrayList<Socket>clientSockets;
	private ArrayList<ClientThread>clientThreads;
	
	private void start() {
		try {
			System.out.println("***************************");
			System.out.println("*                         *");
			System.out.println("*        PresleyBN        *");
			System.out.println("*                         *");
			System.out.println("***************************");
			
			serverSocket = new ServerSocket(PORT);
			clientSockets = new ArrayList<>();
			clientThreads = new ArrayList<>();
			
			Thread messageThread = new Thread() {
				public void run() {
					while(true) {
						System.out.print("> ");
						Scanner scan = new Scanner(System.in);
						String command = scan.nextLine();
						parseCommand(command);
					}
				}
			};
			messageThread.start();
			acceptClients();
		}catch(Exception ex) {ex.printStackTrace();}
	}
	
	private void parseCommand(String command) {
		if(command.startsWith("status")) {
			System.out.println("Connected clients: " + Integer.toString(numberOfClients));
		}else if(command.startsWith("stop")) {
			Scanner scan = new Scanner(System.in);
			System.out.print("Please enter \'stop\' to confirm: ");
			if(scan.nextLine().equals("stop")) {
				System.out.println("INFO: Stopping server!");
				for(ClientThread cl : clientThreads) {
					try {
						cl.socket.close();
					}catch(Exception ex) {}
					clientThreads.remove(cl);
					numberOfClients--;
				}
				System.out.println("INFO: Closed all connections");
				System.exit(0);
			}else {
				System.out.println("Why even bother then?");
			}
		}else if(command.startsWith("execute")) {
			for(ClientThread cl : clientThreads) {
				if(!cl.sendInstruction(command)) {
					clientThreads.remove(cl);
					numberOfClients--;
				}
			}
			System.out.println("INFO: Sent Message");
		}
	}
	
	private void acceptClients() {
		System.out.println("INFO: Started accepting clients");
		while(numberOfClients < maxClients) {
			try {
				Socket newSocket = serverSocket.accept();
				System.out.println("INFO: New Client connected at "+newSocket.getInetAddress().toString());
				clientSockets.add(newSocket);
				ClientThread cl = new ClientThread(newSocket);
				cl.start();
				clientThreads.add(cl);
				numberOfClients++;
			}catch(Exception ex) {ex.printStackTrace();}
		}
	}
	
	class ClientThread extends Thread {
		Socket socket;
		ObjectInputStream sInput;
		ObjectOutputStream sOutput;
		
		ClientThread(Socket theSocket) {
			try{
				socket = theSocket;
				sOutput = new ObjectOutputStream(socket.getOutputStream());
				sInput = new ObjectInputStream(socket.getInputStream());
			}catch(Exception ex) {ex.printStackTrace();}
			
		}
		
		public void run() {
			try {
				
			}catch(Exception ex) {ex.printStackTrace();}
		}
		
		public boolean sendInstruction(String command) {
			try {
				sOutput.writeObject("COMMAND: " + command);
				sOutput.flush();
				return true;
			}catch(Exception ex) {
				System.out.println("WARN: A Client has disconnected!");
				return false;
			}
		}
	}
	
	
	public static void main(String[] args) {
		Server server = new Server();
		server.start();
	}
}