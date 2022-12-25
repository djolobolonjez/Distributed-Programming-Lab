package ProducerConsumer;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class ServerThreadRMI {

	private int port;
	private AtomicBroadcastRemote<String, Goods> remoteMap;
	
	public ServerThreadRMI(int port, AtomicBroadcastRemote<String, Goods> remoteMap) {
		this.port = port;
		this.remoteMap = remoteMap;
	}
	
	public void run() {
		
		if (System.getSecurityManager() == null) {
			System.setSecurityManager(new SecurityManager());
		}
		
		try {
			Registry registry = LocateRegistry.createRegistry(port);
			AtomicBroadcastRemote<String, Goods> stub = (AtomicBroadcastRemote<String, Goods>) UnicastRemoteObject.exportObject(remoteMap, 0);
			registry.rebind("/buffer", stub);
			
		} catch (RemoteException e) { }
	}

	
}
