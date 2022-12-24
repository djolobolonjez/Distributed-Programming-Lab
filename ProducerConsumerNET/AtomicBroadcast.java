package ProducerConsumerNET;

public interface AtomicBroadcast<T> {
	void put (T data);
	T get (int id);
}
