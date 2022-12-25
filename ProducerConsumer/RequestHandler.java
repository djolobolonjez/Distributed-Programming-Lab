package ProducerConsumer;

import java.io.IOException;
import java.net.Socket;
import java.util.Map;

public class RequestHandler extends Thread {

	private Socket socket;
	private Service service;
	private Map<String, AtomicBroadcast<Goods>> goodsMap;
	
	public RequestHandler(Socket socket, Map<String, AtomicBroadcast<Goods>> goodsMap) throws IOException {
		this.socket = socket;
		this.goodsMap = goodsMap;
		service = new Service(this.socket);
	}
	
	public void run() {
		
		while (true) {
			try {
				String name = (String) service.receive();
				Goods goods = (Goods) service.receive();
				
				AtomicBroadcast<Goods> buffer;
				if ((buffer = goodsMap.putIfAbsent(name, new MonitorAtomicBroadcast<>())) == null) {
					buffer = goodsMap.get(name);
				}
				
				buffer.put(goods);
				
			} catch (Exception e) { }
		}
	}
}
