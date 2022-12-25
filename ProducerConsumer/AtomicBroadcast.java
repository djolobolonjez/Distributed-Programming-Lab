package ProducerConsumer;

public interface AtomicBroadcast<T> {
	void put (T data);
	T get ();
}
