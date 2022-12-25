package ProducerConsumerNET;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Server {

	private Map<String, AtomicBroadcast<Goods>> data = new ConcurrentHashMap<>();
	private int port;
	
	public Server(int port) {
		this.port = port;
	}
	
	public void run() {
		try (ServerSocket serverSocket = new ServerSocket(port)) {
			
			while (true) {
				Socket client = serverSocket.accept();
				new RequestHandler(data, client).start();
			}
			
		} catch (IOException e) { } 
		
	}


	public static void main(String[] args) {
		int port = Integer.parseInt(args[0]);
		Server server = new Server(port);
		server.run();
	}


	
	
}
