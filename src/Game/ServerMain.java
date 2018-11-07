package Game;

import java.io.IOException;


public class ServerMain {
	
	private static final int DEFAULT_PORT = 25565;
	
	public static void main(String[] args) {
		try {
			new ServerController(DEFAULT_PORT);
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		
	}

}
