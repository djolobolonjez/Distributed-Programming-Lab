package ProducerConsumerRMI;

public interface AtomicBroadcast<T> {
	void put (T data);
	T get ();
}
