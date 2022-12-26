package ProducerConsumer;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class RmiAB implements AB {
	
	AtomicBroadcastRemote stub;

	@SuppressWarnings("unchecked")
	@Override
	public boolean init(String host, int port) {
		if (System.getSecurityManager() == null) {
			System.setSecurityManager(new SecurityManager());
		}
		
		try {
			Registry reg = LocateRegistry.getRegistry(host, port);
			stub = (AtomicBroadcastRemote) reg.lookup("/buffer");
		} catch (Exception e) { 
			return false;
		}
		
		return true;
	}

	@Override
	public boolean close() {
		return true;
	}

	@Override
	public void putGoods(String name, Goods goods) {
		try {
			stub.put(name, goods);
		} catch (RemoteException e) { }
		
	}

	@Override
	public Goods getGoods(String name) {
		Goods goods = null;
		
		try {
			goods = stub.get(name);
		} catch (RemoteException e) { }
		
		return goods;
	}

}
