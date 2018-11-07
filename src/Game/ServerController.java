package Game;


import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashSet;


public class ServerController extends Hub {

	private ServerModel model; // Records the state of the game.
	private HashSet<String> userNames = new HashSet<String>();
	private boolean running;

	public ServerController(int port) throws IOException {
		super(port);
		model = new ServerModel();
		setAutoreset(true);
		new Thread() {
			public void start() {
				running = true;
				while (running) {
					try {
						int firstPlayer = model.getNextPlayer();
						int secondPlayer = model.getNextPlayer();
						skinnyGame game = model.startGame(firstPlayer,
								secondPlayer);
						ServerController.this.sendToOne(firstPlayer,
								game);
						ServerController.this.sendToOne(secondPlayer,
								game);
					} catch (InterruptedException e) {

					}
				}
			}
		}.start();
	}

	protected void messageReceived(int playerID, Object message) {
		if (message instanceof skinnyGame) {
				int opponentID = model.getOpponent(playerID);
				this.sendToOne(playerID, message);
				this.sendToOne(opponentID, message);
			} else 
		if (message instanceof tinyMessage) {
			if (model.makeMove(((tinyMessage) message).getX(), ((tinyMessage) message).getY(),((tinyMessage) message).getZ(), ((tinyMessage) message).getRotation() , playerID)) {
				int opponentID = model.getOpponent(playerID);
				this.sendToOne(opponentID, message);
			}
		} else if (message instanceof eventBroadcast) {
				int opponentID = model.getOpponent(playerID);
				this.sendToOne(playerID, message);
				this.sendToOne(opponentID, message);
		} else {
			String command = (String) message;
			if (command.equals("newgame")) {
				try {
					this.sendToOne(playerID, "waiting");
					model.addPlayerToQueue(playerID);
				}
				catch (InterruptedException e) {
					
				}
			}
		}

	}

	protected void playerConnected(int playerID) {

	}

	protected void playerDisconnected(int playerID) {
		userNames.remove(model.getPlayerName(playerID));
		int opponentID = model.getOpponent(playerID);
		model.remove(playerID);
		this.sendToOne(opponentID, "waiting");

	}

	protected void extraHandshake(int playerID, ObjectInputStream in,
			ObjectOutputStream out) throws IOException {
		String name;
		try {
			name = (String) in.readObject();
			while (userNames.contains(name)) {
				out.writeObject(false);
				name = (String) in.readObject();
			}
			userNames.add(name);
			out.writeObject(true);
			model.addPlayerToQueue(playerID);
			model.setPlayerName(playerID, name);
			ClientMain.setGameState(13);
		} catch (ClassNotFoundException e) {
			return;
		} catch (InterruptedException e) {
			return;
		}

	}

}