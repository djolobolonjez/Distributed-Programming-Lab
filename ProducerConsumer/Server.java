package ProducerConsumer;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Server {

	private Map<String, AtomicBroadcast<Goods>> goodsMap = new ConcurrentHashMap<>();
	private AtomicBroadcastRemote remoteMap;
	
	private int port;
	
	public Server(int port) {
		this.port = port;
		this.remoteMap = new AtomicBroadcastRemoteImpl(this);
	}
	
	public void run() {
		new ServerThreadRMI(port + 1, remoteMap).run();
		
		try (ServerSocket serverSocket = new ServerSocket(port)){
			while (true) {
				Socket clientSocket = serverSocket.accept();
				new RequestHandler(this, clientSocket).start();
			}
		} catch (IOException e) { }
	}
	
	public void put (String name, Goods goods) {
		AtomicBroadcast<Goods> buffer;
		if ((buffer = goodsMap.putIfAbsent(name, new MonitorAtomicBroadcast<>())) == null) {
			buffer = goodsMap.get(name);
		}
		
		buffer.put(goods);
	}
	
	public Goods get (String name) {
		AtomicBroadcast<Goods> buffer;
		if ((buffer = goodsMap.putIfAbsent(name, new MonitorAtomicBroadcast<>())) == null) {
			buffer = goodsMap.get(name);
		}
		
		return buffer.get();
	}
	
	public static void main(String[] args) {
		int port = Integer.parseInt(args[0]);
		new Server(port).run();
	}
}
