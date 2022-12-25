package ProducerConsumerNET;

import java.io.IOException;
import java.net.Socket;

public class NetAB implements AB {
	
	private Socket clientSocket;
	private Service service;
	
	@Override
	public boolean init(String host, int port) {
		boolean ret = true;
		
		try {
			clientSocket = new Socket(host, port);
			service = new Service(clientSocket);
			System.out.println("Uspesna konekcija sa serverom!");
		}  catch (IOException e) {
			ret = false;
			System.out.println("Neuspesna konekcija sa serverom!");
		}
		
		return ret;
	}

	@Override
	public boolean close() {
		boolean ret = true;
		
		try {
			service.close();
		} catch (IOException e) {
			ret = false;
			System.out.println("Greska pri zatvaranju konekcije sa serverom!");
		}
		
		return ret;
	}

	@Override
	public void putGoods(String name, Goods goods) {
		try {
			service.send("put");
			service.send(name);
			service.send(goods);
			
		} catch (IOException e) { }
		
	}

	@Override
	public Goods getGoods(String name) {
		Goods goods = null;
		try {
			service.send("get");
			service.send(name);
			goods = (Goods)service.receive();
			
		} catch (Exception e) { }
		
		return goods;
	}

}
