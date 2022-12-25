package ProducerConsumer;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;

public class GoodsImpl implements Goods, Serializable {
	
	private static final long serialVersionUID = 1L;
	private String name;
	private ArrayList<String> buff;
	private int cursor;
	
	public GoodsImpl(String name) {
		this.name = name;
		this.buff = new ArrayList<>();
		this.cursor = 0;
	}

	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String[] getBody() {
		String[] body = buff.toArray(new String[buff.size()]);
		return body;
	}

	@Override
	public void setBody(String[] body) {
		for (int i = 0; i < body.length; i++) {
			buff.add(body[i]);
		}
	}

	@Override
	public String readLine() {
		String ret = null;
		
		if (buff.size() > 0 && cursor < buff.size()) {
			ret = buff.get(cursor++);
		}
		
		return ret;
	}

	@Override
	public void printLine(String body) {
		buff.add(body);
	}

	@Override
	public int getNumLines() {
		return (buff.size() > 0 ? buff.size() : -1);
	}

	@Override
	public void save(String name) {
		String path = name + ".txt";
		File f = new File(path);
		try {
			f.createNewFile();
		} catch (IOException e) { }
		
		try (FileWriter fWriter = new FileWriter(path)) {
			
			for (int i = 0; i < buff.size(); i++) {
				fWriter.write(buff.get(i));
			}
			
		} catch (IOException e) { }
		
	}

	@Override
	public void load(String name) {
		// TODO Auto-generated method stub
		
	}

}
