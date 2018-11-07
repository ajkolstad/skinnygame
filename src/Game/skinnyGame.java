package Game;

import java.io.Serializable;

import org.lwjgl.util.vector.Vector3f;


@SuppressWarnings("serial")
public class skinnyGame implements Serializable{
	private Vector3f locationOne;
	private float rotationOne;
	private boolean playerOneSlenderman;
	private Vector3f locationTwo;
	private float rotationTwo;
	private boolean[] pages;
	private int sanity;
	private int idPlayer1;
	private int idPlayer2;
	private boolean gameover;
	private boolean slendermanwin;
	private boolean gameinprogress;
	private int slendermanid;
	private int normalid;
	private int winner;
	public int idCurrentPlayer;
	private boolean tiegame;
	
	public boolean isTiegame() {
		return tiegame;
	}

	public skinnyGame(int idPlayer1, int idPlayer2) {
		this.idPlayer1 = idPlayer1;
		this.idPlayer2 = idPlayer2;
	}

	public int getIdCurrentPlayer() {
		return idCurrentPlayer;
	}

	public void startGame() {
		if(Math.random() < 0.5) {
			playerOneSlenderman = true;
		} else {
			playerOneSlenderman = false;
		}
		if(playerOneSlenderman == true) {
			slendermanid = idPlayer1;
			idCurrentPlayer = idPlayer1;
			normalid = idPlayer2;
			locationOne = new Vector3f(86, 0, -712);
			rotationOne = 0;
			locationTwo = new Vector3f(327, 0, -309);
			rotationTwo = 180;
		} else {
			idCurrentPlayer = idPlayer1;
			normalid = idPlayer1;
			slendermanid = idPlayer2;
			locationOne = new Vector3f(327, 0, -309);
			rotationOne = 180;
			locationTwo = new Vector3f(86, 0, -712);
			rotationTwo = 0;
		}
		gameover = false;
		pages = new boolean[8];
		for(int i = 0; i < pages.length; i++) {
			pages[i] = true;
		}
		sanity = 100;
		gameinprogress = true;
	}
	
	public int getIdPlayer1() {
		return idPlayer1;
	}

	public int getIdPlayer2() {
		return idPlayer2;
	}

	public boolean isGameover() {
		return gameover;
	}

	public boolean isSlendermanwin() {
		return slendermanwin;
	}

	public boolean isGameinprogress() {
		return gameinprogress;
	}

	public boolean isWinner() {
		if(sanity == 0 || (pages[0] == false && pages[1] == false && pages[2] == false && pages[3] == false && pages[4] == false && pages[5] == false&& pages[6] == false && pages[7] == false)) {
			if(sanity == 0) {
				slendermanwin = true;
				winner = slendermanid;
				return true;
			} else {
				slendermanwin = false;
				winner = normalid;
				return true;
			}
		} else {
			return false;
		}

	}
	
	public boolean isTie() {
		return(sanity == 0 && (pages[0] == false && pages[1] == false && pages[2] == false && pages[3] == false && pages[4] == false && pages[5] == false&& pages[6] == false && pages[7] == false));
	}
	
	public int getOtherPlayerID(int playerID) {
		if (playerID == idPlayer1)
			return idPlayer2;
		else if (playerID == idPlayer2)
			return idPlayer1;
		else
			return -1;
	}
	
	public boolean makeMove(Vector3f coordinates, float rotation) {
		if(idCurrentPlayer == idPlayer1) {
			locationOne = coordinates;
			rotationOne = rotation;
		} else {
			locationTwo = coordinates;
			rotationTwo = rotation;
		}
		if (isWinner()) {
			gameinprogress = false;
			winner = idCurrentPlayer;
		} else if (isTie()) { 
			gameinprogress = false;
			tiegame = true;
		} else { 
			idCurrentPlayer = (idCurrentPlayer == idPlayer1) ? idPlayer2
					: idPlayer1;
		}
		return true;
	}
	
	public int getSlendermanid() {
		return slendermanid;
	}

	public int getNormalid() {
		return normalid;
	}

	public int getWinner() {
		return winner;
	}

	public Vector3f getLocationOne() {
		return locationOne;
	}

	public float getRotationOne() {
		return rotationOne;
	}

	public Vector3f getLocationTwo() {
		return locationTwo;
	}

	public float getRotationTwo() {
		return rotationTwo;
	}

	public boolean[] getPages() {
		return pages;
	}

	public int getSanity() {
		return sanity;
	}
	
	public boolean getPlayerOneSlenderman() {
		return playerOneSlenderman;
	}
	
	public String toString() {
		String str = "Player One Location:\nX: " + getLocationOne().x + "\nY: " + getLocationOne().y + "\nZ: " + getLocationOne().z + "\nPlayer One Rotation: " + getRotationOne() + "\nPlayer Two Location:\nX: " + getLocationTwo().x + "\nY: " + getLocationTwo().y + "\nZ: " + getLocationTwo().z + "\nPlayer Two Rotation: " + getRotationTwo();
		return str;
	}

	public void setLocationOne(Vector3f locationOne) {
		this.locationOne = locationOne;
	}

	public void setRotationOne(float rotationOne) {
		this.rotationOne = rotationOne;
	}

	public void setPlayerOneSlenderman(boolean playerOneSlenderman) {
		this.playerOneSlenderman = playerOneSlenderman;
	}

	public void setLocationTwo(Vector3f locationTwo) {
		this.locationTwo = locationTwo;
	}

	public void setRotationTwo(float rotationTwo) {
		this.rotationTwo = rotationTwo;
	}

	public void setPages(boolean[] pages) {
		this.pages = pages;
	}

	public void setSanity(int sanity) {
		this.sanity = sanity;
	}

	public void setIdPlayer1(int idPlayer1) {
		this.idPlayer1 = idPlayer1;
	}

	public void setIdPlayer2(int idPlayer2) {
		this.idPlayer2 = idPlayer2;
	}

	public void setGameover(boolean gameover) {
		this.gameover = gameover;
	}

	public void setSlendermanwin(boolean slendermanwin) {
		this.slendermanwin = slendermanwin;
	}

	public void setGameinprogress(boolean gameinprogress) {
		this.gameinprogress = gameinprogress;
	}

	public void setSlendermanid(int slendermanid) {
		this.slendermanid = slendermanid;
	}

	public void setNormalid(int normalid) {
		this.normalid = normalid;
	}

	public void setWinner(int winner) {
		this.winner = winner;
	}

	public void setIdCurrentPlayer(int idCurrentPlayer) {
		this.idCurrentPlayer = idCurrentPlayer;
	}

	public void setTiegame(boolean tiegame) {
		this.tiegame = tiegame;
	}
	
	
}
