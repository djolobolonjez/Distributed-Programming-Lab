package ProducerConsumer;

import java.rmi.RemoteException;
import java.util.Map;

public class AtomicBroadcastRemoteImpl<K, V> implements AtomicBroadcastRemote<K, V> {

	private Map<K, AtomicBroadcast<V>> data;
	
	public AtomicBroadcastRemoteImpl(Map<K, AtomicBroadcast<V>> data) {
		this.data = data;
	}
	
	@Override
	public void put(K name, V goods) throws RemoteException {
		
		AtomicBroadcast<V> buffer;
		if ((buffer = data.putIfAbsent(name, new MonitorAtomicBroadcast<>())) == null) {
			buffer = data.get(name);
		}
		
		buffer.put(goods);
		
	}

	@Override
	public V get(K name) throws RemoteException {	
		AtomicBroadcast<V> buffer;
		if ((buffer = data.putIfAbsent(name, new MonitorAtomicBroadcast<>())) == null) {
			buffer = data.get(name);
		}
		
		V goods = buffer.get();
		return goods;
	}

	
}
