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
		if (!remoteMap.containsKey(name)) {
			buffer = new MonitorAtomicBroadcast<>();
		} else {
			buffer = remoteMap.get(name);
		}
		remoteMap.put(name, buffer);
		
		buffer.put(goods);
	}

	@Override
	public V get(K name) {
		
		AtomicBroadcast<V> buffer;
		if (!remoteMap.containsKey(name)) {
			buffer = new MonitorAtomicBroadcast<>();
		} else {
			buffer = remoteMap.get(name);
		}
		
		remoteMap.put(name, buffer);
	
		V data = buffer.get();
		
		return data;
	}

}
