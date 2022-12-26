package ProducerConsumer;

import java.io.IOException;
import java.net.Socket;
import java.util.Map;

public class RequestHandler extends Thread {

	private Socket socket;
	private Service service;
	private Server server;
	
	public RequestHandler(Server server, Socket socket) throws IOException {
		this.socket = socket;
		this.server = server;
		service = new Service(this.socket);
	}
	
	public void run() {
		
		try(Socket client = this.socket) {
			while (true) {
				
				String name = (String) service.receive();
				Goods goods = (Goods) service.receive();
				
				server.put(name, goods);
				
			}	 
		}catch (Exception e) {}
	}
}
