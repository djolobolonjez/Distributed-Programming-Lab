package ProducerConsumer;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface AtomicBroadcastRemote extends Remote {

	void put (String name, Goods goods) throws RemoteException;
	Goods get (String name) throws RemoteException;
}
