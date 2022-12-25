package ProducerConsumer;

import java.util.ArrayList;

public class MonitorAtomicBroadcast<T> implements AtomicBroadcast<T> {

	private int wi = 0;
	private ThreadLocal<Integer> ri = ThreadLocal.withInitial(() -> 0);
	
	private ArrayList<T> buffer = new ArrayList<>();
	
	@Override
	public synchronized void put(T data) {
		
		buffer.add(data);
		
		wi++;
		
		notifyAll();
		
	}

	@Override
	public synchronized T get() {
		T data = null;
		int readIndex = ri.get();
		
		while (readIndex == wi) {
			try {
				wait();
			} catch (InterruptedException e) { }
		}
		
		data = buffer.get(readIndex);
		ri.set(readIndex + 1);
		
		return data;
	}

}
