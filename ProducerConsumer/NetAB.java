package ProducerConsumer;

import java.io.IOException;
import java.net.Socket;

public class NetAB implements AB {
	
	private Service service;

	@Override
	public boolean init(String host, int port) {
		
		Socket clientSocket;
		try {
			clientSocket = new Socket(host, port);
			service = new Service(clientSocket);
		} catch (Exception e) {
			return false;
		}
		
		return true;
	}

	@Override
	public boolean close() {
		try {
			service.close();
		} catch (IOException e) {
			return false;
		}
		
		return true;
	}

	@Override
	public void putGoods(String name, Goods goods) {
		
		try {
			service.send(name);
			service.send(goods);
		} catch (IOException e) { }
		
	}

	@Override
	public Goods getGoods(String name) {
		Goods goods = null;
		
		try {
			service.send(name);
			goods = (Goods) service.receive();
		} catch (Exception e) { }
		
		return goods;
	}

}
