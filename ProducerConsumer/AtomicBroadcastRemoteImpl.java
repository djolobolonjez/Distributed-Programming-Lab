package ProducerConsumer;

import java.rmi.RemoteException;
import java.util.Map;

public class AtomicBroadcastRemoteImpl implements AtomicBroadcastRemote {

	private Server server;
	
	public AtomicBroadcastRemoteImpl(Server server) {
		this.server = server;
	}
	
	@Override
	public void put(String name, Goods goods) throws RemoteException {
		
		server.put(name, goods);
	}

	@Override
	public Goods get(String name) throws RemoteException {	
		return server.get(name);
	}

	
}
