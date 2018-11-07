package Game;

import java.util.Scanner;
import javax.swing.JOptionPane;


public class ClientMain{

	public static final int mainMenuPlay = 1, mainMenuSettings = 2, settingsLow = 3, settingsMedium = 4, settingsHigh = 5, play = 6,  connect = 7, error = 8, disconnect = 9, win = 10, lose = 11, gameover = 12, GAMEISPLAYING = 13, youareslenderman = 14, findalleightpages = 15, searching = 16;
	public static int menuState = 1;
	public static int graphicsSettings = 4;
	public static boolean displayup = true;
	public static Scanner scan;
	public static String computer;
	
	private static final int DEFAULT_PORT = 25565;
	
	public static void main(String[] args) {
		
		View view = new View();
		Thread thread = new Thread(view);
		thread.start();
		while(displayup) {
			System.out.print("");
			if(getGameState() == 6) {
				skinnyClientMain();
				break;
			}
		}
	}
	
	public static void skinnyClientMain() {
		String host = JOptionPane
				.showInputDialog("Enter the name of the\nhost computer");
		setGameState(7);
		if (host == null || host.trim().length() == 0)
			return;
		try {
			new ClientController(host, DEFAULT_PORT);
		} catch (Exception e) {
			ClientMain.setGameState(8);
			e.printStackTrace();
		}
		
	}
	
	public static int getGameState() {
		return menuState;
	}
	public static void setGameState(int menuState) {
		ClientMain.menuState = menuState;
	}
	
}
