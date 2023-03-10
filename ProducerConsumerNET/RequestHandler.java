package ProducerConsumerNET;

import java.io.IOException;
import java.net.Socket;
import java.util.Map;

public class RequestHandler extends Thread {

	private Map<String, AtomicBroadcast<Goods>> data;
	private Socket socket;
	private Service service;
	
	public RequestHandler(Map<String, AtomicBroadcast<Goods>> data,
						  Socket socket) {
		this.data = data;
		this.socket = socket;
	}
	
	@Override
	public void run() {
		try {
			service = new Service(socket);
			
			while (true) {
				
				String opr = (String) service.receive();
				String name = (String) service.receive();
				
				Goods goods = null;
				
				AtomicBroadcast<Goods> buffer;
				if ((buffer = data.putIfAbsent(name, new MonitorAtomicBroadcast<>())) == null) {
					buffer = data.get(name);
				}
				
				switch (opr) {
				case "put":
					goods = (Goods) service.receive();
					buffer.put(goods);
					
					break;
				case "get":
					goods = buffer.get();
					service.send(goods);
					
					break;
				}
			}
		} catch (Exception e) { }
		
		if (service != null) {
			try {
				service.close();
			} catch (IOException e) { }
		}
	}
}
