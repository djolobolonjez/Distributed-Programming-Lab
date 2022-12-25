package ProducerConsumerRMI;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface AtomicBroadcastRemote<K, V> extends Remote {

	void put (K name, V goods) throws RemoteException;
	V get (K name) throws RemoteException;
}
