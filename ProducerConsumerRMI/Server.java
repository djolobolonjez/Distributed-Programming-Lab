package ProducerConsumerRMI;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Server {
	
	private Map<String, AtomicBroadcast<Goods>> data = new ConcurrentHashMap<String, AtomicBroadcast<Goods>>();
	private AtomicBroadcastRemote<String, Goods> remoteMap = new AtomicBroadcastRemoteImpl<>(data);
	
	private int port;
	
	public Server(int port) {
		this.port = port;
	}

	@SuppressWarnings({ "removal", "deprecation" })
	public void run() {
		if (System.getSecurityManager() == null) {
			System.setSecurityManager(new SecurityManager());
		}
		
		try {
			Registry registry = LocateRegistry.createRegistry(port);
			AtomicBroadcastRemote<String, Goods> stub = (AtomicBroadcastRemote<String, Goods>) UnicastRemoteObject.exportObject(remoteMap, 0);
			registry.rebind("/buffer", stub);
		} catch (Exception e) { }
	}
	
	public static void main(String[] args) {
		int port = Integer.parseInt(args[0]);
		Server server = new Server(port);
		server.run();
	}
	
	
}
