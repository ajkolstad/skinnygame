package Game;
import javax.swing.*;

import java.awt.event.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import models.TexturedModel;

import org.lwjgl.util.vector.Vector3f;

public class ClientController {

	static skinnyGame model;

	static int myID;

	private static ClientConnection connection;
	
	private static Vector3f opponentLocation;
	private static float opponentRotation;
	private static Vector3f playerLocation;
	private static float playerRotation;
	public static boolean opponentIsSlenderman;
	public int count = 0;

	public ClientController(String host, int port)  throws IOException {
		connection = new ClientConnection(host, port);
		myID = connection.getID();
	}

	private class ClientConnection extends Client {
		

		public ClientConnection(String host, int port)
				throws IOException {
			super(host, port);
		}

		protected void messageReceived(final Object message) {
			if (message instanceof skinnyGame) {
				SwingUtilities.invokeLater(new Runnable() {
					public void run() { 
						updateModel((skinnyGame) message);
					}
				});
			}
			if (message instanceof tinyMessage) {
				SwingUtilities.invokeLater(new Runnable() {
					public void run() { 
						setLocation((tinyMessage) message);
					}
				});
			}
			if (message instanceof eventBroadcast) {
				SwingUtilities.invokeLater(new Runnable() {
					public void run() { 
						setEvent((eventBroadcast) message);
					}
				});
			}
			if (message instanceof String) {
				String command = (String) message;
				if (command.equals("waiting")) {
					ClientMain.setGameState(16);
				}
			}
		}

		protected void serverShutdown(String message) {
			SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					ClientMain.setGameState(9);
					System.exit(0);
				}
			});
		}

		protected void extraHandshake(ObjectInputStream in,
				ObjectOutputStream out) throws IOException {
			String userName = JOptionPane
					.showInputDialog("Enter that name that you want\nto use in the game.");
			try {
				out.writeObject(userName);
				Boolean userNameAvailable = (Boolean) in.readObject();
				while (!userNameAvailable) {
					String name = JOptionPane
							.showInputDialog("Username already in use. Choose another.");
					out.writeObject(name);
					userNameAvailable = (Boolean) in.readObject();
					ClientMain.setGameState(13);
				}
			} catch (IOException e) {
				e.printStackTrace();
				ClientMain.setGameState(8);
			} catch (ClassNotFoundException e ) {
				e.printStackTrace();
				ClientMain.setGameState(8);
			}
		}

	}

	
	private void setEvent(eventBroadcast event) {
		if(myID == model.getIdPlayer1()) {
			if(event.isPlayerOneWin() == true && event.isPlayerTwoWin() == false) {
				ClientMain.setGameState(10);
			} else if(event.isPlayerOneWin() == false && event.isPlayerTwoWin() == true) {
				ClientMain.setGameState(11);
			}
		} else {
			if(event.isPlayerOneWin() == true && event.isPlayerTwoWin() == false) {
				ClientMain.setGameState(11);
			} else if(event.isPlayerOneWin() == false && event.isPlayerTwoWin() == true) {
				ClientMain.setGameState(10);
			}
		}
		
	}
	
	private void updateModel(skinnyGame model) {
		this.model = model;
		if(myID == model.getIdPlayer1()) {
			opponentLocation = model.getLocationTwo();
			opponentRotation = model.getRotationTwo();
			this.model.setLocationTwo(opponentLocation);
			this.model.setRotationTwo(opponentRotation);
			if(View.isInRightPosition == false) {
				playerLocation = model.getLocationOne();
				playerRotation = model.getRotationOne();
			} else {
				model.setLocationOne(View.player.getPosition());
				model.setRotationOne(View.player.getRotY());
			}
			if(myID == model.getSlendermanid()) {
				opponentIsSlenderman = false;
			} else {
				opponentIsSlenderman = true;
			}
		} else {
			opponentLocation = model.getLocationOne();
			opponentRotation = model.getRotationOne();
			this.model.setLocationOne(opponentLocation);
			this.model.setRotationOne(opponentRotation);
			if(View.isInRightPosition == false) {
				playerLocation = model.getLocationTwo();
				playerRotation = model.getRotationTwo();
			} else {
				model.setLocationTwo(View.player.getPosition());
				model.setRotationTwo(View.player.getRotY());
			}
			if(myID == model.getSlendermanid()) {
				opponentIsSlenderman = false;
			} else {
				opponentIsSlenderman = true;
			}
		}
		if (model.getLocationOne() == null) {
			return; // haven't started yet -- waiting for another player
		} else if (!model.isGameinprogress()) {
			ClientMain.setGameState(16);
			if (myID == model.getWinner())
				ClientMain.setGameState(10);
			else
				ClientMain.setGameState(11);
		} else {
			if (myID == model.getIdPlayer1())
				ClientMain.setGameState(14);
			else 
				ClientMain.setGameState(15);
		}
	}
	
	public static float setSanity() {
		if(opponentIsSlenderman) {
		double d = Math.sqrt(Math.pow((View.player.getPosition().x - ClientController.opponentLocation.x), 2) + (Math.pow((View.player.getPosition().z - ClientController.opponentLocation.z), 2)));
		if(d <= 50) {
			if ( d <= 40) {
				if(d <= 30) {
					if(d <= 20) {
						if(d <= 10) {
							return .0005f;
						}
						return .0004f;
					}
					return .0003f;
				}
				return .0002f;
			}
			return .0001f;
		}
		return 0;
		}
		return 0;
	}
	
	public static float setSlenderSanity() {
		if(!opponentIsSlenderman) {
		double d = Math.sqrt(Math.pow((View.player.getPosition().x - ClientController.opponentLocation.x), 2) + (Math.pow((View.player.getPosition().z - ClientController.opponentLocation.z), 2)));
		if(d >= 50) {
			if ( d >= 40) {
				if(d >= 30) {
					if(d >= 20) {
						if(d >= 10) {
							return .0001f;
						}
						return .0001f;
					}
					return .0001f;
				}

				return .0002f;
			}
			
			return .0003f;
		}
		return 0;
		}
		return 0;
	}

	public static void startNew() {
		connection.send(model);
		connection.send("newgame"); 
		return;
	}
	
	public static void keyPressed(float rotation, float X, float Y, float Z) {
		if (model == null || model.getLocationOne() == null) {
			return;
		}
		if (!model.isGameinprogress()) {
			connection.send(model);
				connection.send("newgame"); 
			return;
		}
		tinyMessage message = new tinyMessage(rotation, X, Y, Z);
		connection.send(message);
		
	}
	
	public static void setLocation(tinyMessage message) {
		setOpponentLocation(new Vector3f(message.getX(), message.getY(), message.getZ()));
		setOpponentRotation(message.getRotation());
	}
	
	public static Vector3f getOpponentLocation() {
		return opponentLocation;
	}
	
	public static Vector3f getPlayerLocation() {
		return playerLocation;
	}

	public static void setOpponentLocation(Vector3f opponentLocation) {
		ClientController.opponentLocation = opponentLocation;
	}

	public static TexturedModel getOpponentModel() {
		if(opponentIsSlenderman == true) {
			return View.slenderman;
		} else {
			return View.playerModel;
		}
	}

	public static TexturedModel getPlayerModel() {
		if(opponentIsSlenderman == true) {
			return View.playerModel;
		} else {
			return View.slenderman;
		}
	}

	public static float getOpponentRotation() {
		return opponentRotation;
	}
	
	public static float getPlayerRotation() {
		return playerRotation;
	}

	public static void setOpponentRotation(float opponentRotation) {
		ClientController.opponentRotation = opponentRotation;
	}

	public skinnyGame getModel() {
		return model;
	}

	public void setModel(skinnyGame model) {
		ClientController.model = model;
	}

	public static void broadcastEvent(boolean goodbad) {
		System.out.println("sending event");
		if(myID == model.getIdPlayer1()) {
			if(goodbad == true) {
				eventBroadcast event = new eventBroadcast(true, false);
				connection.send(event);
				return;
			} else {
				eventBroadcast event = new eventBroadcast(false, true);
				connection.send(event);
				return;
			}
		} else {
			if(goodbad == true) {
				eventBroadcast event = new eventBroadcast(false, true);
				connection.send(event);
				return;
			} else {
				eventBroadcast event = new eventBroadcast(true, false);
				connection.send(event);
				return;
			}
		}

	}

	public static void windowClosing() {
		connection.disconnect(); // Send a disconnect message to the hub.
		try {
			Thread.sleep(500); // Wait one-half second to allow the message to
								// be sent.
		} catch (InterruptedException e) {
		}
		System.exit(0);
	}
	
}
