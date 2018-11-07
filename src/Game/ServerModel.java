package Game;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;

import org.lwjgl.util.vector.Vector3f;

public class ServerModel {

	private ConcurrentHashMap<Integer, skinnyGame> currentGames 
		= new ConcurrentHashMap<Integer, skinnyGame>();
	private ConcurrentHashMap<Integer, PlayerProfile> playerProfiles 
		= new ConcurrentHashMap<Integer, PlayerProfile>();
	private LinkedBlockingQueue<Integer> waitingPlayers = new LinkedBlockingQueue<Integer>(50);
	
	public void addPlayerToQueue(int playerID) throws InterruptedException {
		if (!playerProfiles.containsKey(playerID))
			playerProfiles.put(playerID, new PlayerProfile(playerID));
		currentGames.remove(playerID);
		try {
			waitingPlayers.put(playerID);
		}
		catch (InterruptedException e) {
			
		}
	}
	
	public skinnyGame startGame(int firstPlayer, int secondPlayer) {
		if (!playerProfiles.containsKey(firstPlayer))
			playerProfiles.put(firstPlayer, new PlayerProfile(firstPlayer));
		if (!playerProfiles.containsKey(secondPlayer))
			playerProfiles.put(secondPlayer, new PlayerProfile(secondPlayer));
		skinnyGame game = new skinnyGame(firstPlayer, secondPlayer);
		currentGames.put(firstPlayer, game);
		currentGames.put(secondPlayer, game);
		playerProfiles.get(firstPlayer).numGamesPlayed++;
		playerProfiles.get(secondPlayer).numGamesPlayed++;
		playerProfiles.get(firstPlayer).idOfMostRecentOpponent = secondPlayer;
		playerProfiles.get(secondPlayer).idOfMostRecentOpponent = firstPlayer;
		game.startGame();
		return game;
	}
	
	public boolean makeMove(float X, float Y, float Z, float rotation, int playerID) {
		Vector3f coordinates = new Vector3f(X, Y, Z);
		skinnyGame game = currentGames.get(playerID);
		if (game == null)
			return false;
		if (!isPlayersTurn(playerID))
			return false;
		boolean successfulMove = game.makeMove(coordinates, rotation);
		if (!successfulMove)
			return false;
		if (!game.isGameinprogress()) {
			if (game.isWinner())
				playerProfiles.get(playerID).numGamesWon++;
		}
		return true;
	}
	
	public boolean isWinner(int playerID) {
		skinnyGame game = currentGames.get(playerID);
		if (game == null)
			return false;
		return game.isWinner() && (playerID == game.idCurrentPlayer);
	}
	
	public void incrementsWins(int playerID) {
		if (!playerProfiles.containsKey(playerID))
			playerProfiles.put(playerID, new PlayerProfile(playerID));
		playerProfiles.get(playerID).numGamesWon++;
	}
	
	public boolean isTie(int playerID) {
		skinnyGame game = currentGames.get(playerID);
		if (game == null)
			return false;
		return game.isTie();
	}
	
	public boolean isPlayersTurn(int playerID) {
		skinnyGame game = currentGames.get(playerID);
		if (game == null)
			return false;
		return game.idCurrentPlayer == playerID;
	}
	
	public int getNextPlayer() throws InterruptedException {
		return waitingPlayers.take();
	}
	
	public int getMostRecentOpponent(int playerID) {
		if (!playerProfiles.containsKey(playerID))
			return -1;
		return playerProfiles.get(playerID).idOfMostRecentOpponent;
	}
	
	public void setPlayerName(int playerID, String name) {
		if (!playerProfiles.containsKey(playerID))
			playerProfiles.put(playerID, new PlayerProfile(playerID));
		playerProfiles.get(playerID).name = name;
	}
	
	public String getPlayerName(int playerID) {
		return playerProfiles.get(playerID).name;
	}
	
	public int getOpponent(int playerID) {
		skinnyGame game = currentGames.get(playerID);
		if (game != null)
			return game.getOtherPlayerID(playerID);
		return -1;
	}
	
	public skinnyGame getGame(int playerID) {
		return currentGames.get(playerID);
	}
	
	public void remove(int playerID) {
		skinnyGame game = currentGames.get(playerID);
		if (game != null && game.isGameinprogress()) {
			int opponentID = game.getOtherPlayerID(playerID);
			playerProfiles.get(opponentID).numGamesWon++;
			currentGames.remove(playerID);
			currentGames.remove(opponentID);
			waitingPlayers.add(opponentID);
		}
		playerProfiles.remove(playerID);
		waitingPlayers.remove(playerID);
	}
	
	private static class PlayerProfile {
		@SuppressWarnings("unused")
		public int playerID;
		@SuppressWarnings("unused")
		public int numGamesPlayed = 0;
		@SuppressWarnings("unused")
		public int numGamesWon = 0;
		public int idOfMostRecentOpponent = -1;
		public String name = "";
		
		public PlayerProfile(int playerId) {
			this.playerID = playerId;
		}
	}
}