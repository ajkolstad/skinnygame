package Game;

import java.io.Serializable;

@SuppressWarnings("serial")
public class eventBroadcast implements Serializable{

	private boolean playerOneWin;
	private boolean playerTwoWin;
	public eventBroadcast(boolean playerOneWin, boolean playerTwoWin) {
		super();
		this.playerOneWin = playerOneWin;
		this.playerTwoWin = playerTwoWin;
	}
	public boolean isPlayerOneWin() {
		return playerOneWin;
	}
	public void setPlayerOneWin(boolean playerOneWin) {
		this.playerOneWin = playerOneWin;
	}
	public boolean isPlayerTwoWin() {
		return playerTwoWin;
	}
	public void setPlayerTwoWin(boolean playerTwoWin) {
		this.playerTwoWin = playerTwoWin;
	}
	
	
}
