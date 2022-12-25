package ProducerConsumerRMI;

import java.util.Map;

public class AtomicBroadcastRemoteImpl<K, V> implements AtomicBroadcastRemote<K, V> {

	private Map<K, AtomicBroadcast<V>> remoteMap;
	
	public AtomicBroadcastRemoteImpl(Map<K, AtomicBroadcast<V>> remoteMap) {
		this.remoteMap = remoteMap;
	}
	
	@Override
	public void put(K name, V goods) {
		
		AtomicBroadcast<V> buffer;
		if ((buffer = remoteMap.putIfAbsent(name, new MonitorAtomicBroadcast<>())) == null) {
			buffer = remoteMap.get(name);
		}
		
		buffer.put(goods);
	}

	@Override
	public V get(K name) {
		
		AtomicBroadcast<V> buffer;
		if ((buffer = remoteMap.putIfAbsent(name, new MonitorAtomicBroadcast<>())) == null) {
			buffer = remoteMap.get(name);
		}
	
		V data = buffer.get();
		
		return data;
	}

}
