package ProducerConsumerNET;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Service {

	private Socket socket;
	private ObjectOutputStream out;
	private ObjectInputStream in;
	
	public Service(Socket clientSocket) throws IOException {
		this.socket = clientSocket;
		out = new ObjectOutputStream(socket.getOutputStream());
		in = new ObjectInputStream(socket.getInputStream());
	}
	
	public void send(Object obj) throws IOException {
		out.writeObject(obj);
	}
	
	public Object receive() throws ClassNotFoundException, IOException {
		Object obj = (Object) in.readObject();
		return obj;
	}
	
	public void close() throws IOException {
		socket.close();
		out.close();
		in.close();
	}

}
