package ProducerConsumer;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Server {

	private Map<String, AtomicBroadcast<Goods>> goodsMap = new ConcurrentHashMap<>();
	private AtomicBroadcastRemote<String, Goods> remoteMap = new AtomicBroadcastRemoteImpl<>(goodsMap);
	
	private int port;
	
	public Server(int port) {
		this.port = port;
	}
	
	public void run() {
		new ServerThreadRMI(port + 1, remoteMap).run();
		
		try (ServerSocket serverSocket = new ServerSocket(port)){
			while (true) {
				Socket clientSocket = serverSocket.accept();
				new RequestHandler(clientSocket, goodsMap).start();
			}
		} catch (IOException e) { }
	}
	
	public static void main(String[] args) {
		int port = Integer.parseInt(args[0]);
		new Server(port).run();
	}
}
