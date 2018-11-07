package Game;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import models.TexturedModel;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;
import entities.Camera;
import entities.Entity;
import entities.Light;
import entities.Player;
import guis.GuiRenderer;
import guis.GuiTexture;
import renderEngine.DisplayManager;
import renderEngine.Loader;
import renderEngine.MasterRenderer;
import renderEngine.OBJLoader;
import terrain.Terrain;
import textures.ModelTexture;
import textures.TerrainTexture;
import textures.TerrainTexturePack;

public class View implements Runnable {
	public static boolean isMouseDown;
	public static int MouseX;
	public static int MouseY;
	public static boolean isSlenderman;
	public static Player player;
	public static Entity Opponent;
	public static int playerType = 1;
	public static TexturedModel slenderman;
	public static TexturedModel playerModel;
	public static boolean isInRightPosition = false;

	public View() {

	}

	public void run() {
		DisplayManager.createDisplay();
		Loader loader = new Loader();
		TerrainTexture blendMap = new TerrainTexture(loader.loadTexture("slenderblendmap"));
		TerrainTexture backgroundTexture = new TerrainTexture(loader.loadTexture("grass"));
		TerrainTexture rTexture = new TerrainTexture(loader.loadTexture("mud"));
		TerrainTexture gTexture = new TerrainTexture(loader.loadTexture("pinkFlowers"));
		TerrainTexture bTexture = new TerrainTexture(loader.loadTexture("gravel"));
		slenderman = new TexturedModel(OBJLoader.loadOBJModel("slenderman", loader), new ModelTexture(loader.loadTexture("black")));
		TexturedModel wall = new TexturedModel((OBJLoader.loadOBJModel("wall",loader)), new ModelTexture(loader.loadTexture("brickTexture")));
		TexturedModel tree = new TexturedModel((OBJLoader.loadOBJModel("tree",loader)), new ModelTexture(loader.loadTexture("treebark")));
		TexturedModel spookytree = new TexturedModel((OBJLoader.loadOBJModel("spookytree", loader)), new ModelTexture(loader.loadTexture("treebark")));
		TexturedModel silo = new TexturedModel((OBJLoader.loadOBJModel("silo",loader)), new ModelTexture(loader.loadTexture("truckTexture")));
		TexturedModel camper = new TexturedModel((OBJLoader.loadOBJModel("camper", loader)), new ModelTexture(loader.loadTexture("camperTexture")));
		TexturedModel truck1 = new TexturedModel((OBJLoader.loadOBJModel("truckpart1", loader)), new ModelTexture(loader.loadTexture("truckTexture")));
		TexturedModel truck2 = new TexturedModel((OBJLoader.loadOBJModel("truckpart2", loader)), new ModelTexture(loader.loadTexture("truckTexture")));
		TexturedModel cutTree = new TexturedModel((OBJLoader.loadOBJModel("cutTree", loader)), new ModelTexture(loader.loadTexture("treebark")));
		TexturedModel rock = new TexturedModel((OBJLoader.loadOBJModel("rock",loader)), new ModelTexture(loader.loadTexture("rockTexture")));
		TexturedModel tanker = new TexturedModel((OBJLoader.loadOBJModel("tanker", loader)), new ModelTexture(loader.loadTexture("truckTexture")));
		TexturedModel building = new TexturedModel((OBJLoader.loadOBJModel("building", loader)), new ModelTexture(loader.loadTexture("brickTexture")));
		TexturedModel building2 = new TexturedModel((OBJLoader.loadOBJModel("building2", loader)), new ModelTexture(loader.loadTexture("brickTexture")));
		playerModel = new TexturedModel((OBJLoader.loadOBJModel("player", loader)), new ModelTexture(loader.loadTexture("white")));
		TerrainTexturePack texturePack = new TerrainTexturePack(backgroundTexture, rTexture, gTexture, bTexture);
		player = new Player(slenderman, new Vector3f(327, 0, -309), 0, 180, 0,1);
		List<GuiTexture> mainMenuPlay = new ArrayList<GuiTexture>();
		GuiTexture mainMenuPlayGui = new GuiTexture(loader.loadTexture("mainMenuPlay"),new Vector2f(0.6f, -0.42f), new Vector2f(1.65f, 1.45f));
		mainMenuPlay.add(mainMenuPlayGui);
		List<GuiTexture> mainMenuSettings = new ArrayList<GuiTexture>();
		GuiTexture mainMenuSettingsGui = new GuiTexture(loader.loadTexture("mainMenuSettings"),new Vector2f(0.6f, -0.42f), new Vector2f(1.65f, 1.45f));
		mainMenuSettings.add(mainMenuSettingsGui);
		List<GuiTexture> host = new ArrayList<GuiTexture>();
		GuiTexture hostGui = new GuiTexture(loader.loadTexture("host"),new Vector2f(0.6f, -0.42f), new Vector2f(1.65f, 1.45f));
		host.add(hostGui);
		List<GuiTexture> join = new ArrayList<GuiTexture>();
		GuiTexture joinGui = new GuiTexture(loader.loadTexture("join"),new Vector2f(0.6f, -0.42f), new Vector2f(1.65f, 1.45f));
		join.add(joinGui);
		List<GuiTexture> settingsLow = new ArrayList<GuiTexture>();
		GuiTexture lowGui = new GuiTexture(loader.loadTexture("settingsLow"),new Vector2f(0.6f, -0.42f), new Vector2f(1.65f, 1.45f));
		settingsLow.add(lowGui);
		List<GuiTexture> settingsMedium = new ArrayList<GuiTexture>();
		GuiTexture mediumGui = new GuiTexture(loader.loadTexture("settingsMedium"),new Vector2f(0.6f, -0.42f), new Vector2f(1.65f, 1.45f));
		settingsMedium.add(mediumGui);
		List<GuiTexture> settingsHigh = new ArrayList<GuiTexture>();
		GuiTexture highGui = new GuiTexture(loader.loadTexture("settingsHigh"),new Vector2f(0.6f, -0.42f), new Vector2f(1.65f, 1.45f));
		settingsHigh.add(highGui);
		List<GuiTexture> play = new ArrayList<GuiTexture>();
		GuiTexture playGui = new GuiTexture(loader.loadTexture("play"),new Vector2f(0.6f, -0.42f), new Vector2f(1.65f, 1.45f));
		play.add(playGui);
		List<GuiTexture> skinnyman = new ArrayList<GuiTexture>();
		GuiTexture skinnyGUI = new GuiTexture(loader.loadTexture("skinnyman"),new Vector2f(0.6f, -0.9f), new Vector2f(1.6f, 0.3f));
		skinnyman.add(skinnyGUI);
		List<GuiTexture> playerinfo = new ArrayList<GuiTexture>();
		GuiTexture playerGUI = new GuiTexture(loader.loadTexture("player"),new Vector2f(0.6f, -0.9f), new Vector2f(1.6f, 0.3f));
		playerinfo.add(playerGUI);
		List<GuiTexture> disconnect = new ArrayList<GuiTexture>();
		GuiTexture disconnectGUI = new GuiTexture(loader.loadTexture("disconnect"),new Vector2f(0.6f, -0.42f), new Vector2f(1.65f, 1.45f));
		disconnect.add(disconnectGUI);
		List<GuiTexture> connect = new ArrayList<GuiTexture>();
		GuiTexture connectGUI = new GuiTexture(loader.loadTexture("connecting"),new Vector2f(0.6f, -0.42f),  new Vector2f(1.65f, 1.45f));
		connect.add(connectGUI);
		List<GuiTexture> win = new ArrayList<GuiTexture>();
		GuiTexture winGUI = new GuiTexture(loader.loadTexture("win"),new Vector2f(0.6f, -0.42f),  new Vector2f(1.65f, 1.45f));
		win.add(winGUI);
		List<GuiTexture> lose = new ArrayList<GuiTexture>();
		GuiTexture loseGUI = new GuiTexture(loader.loadTexture("lose"),new Vector2f(0.6f, -0.42f),  new Vector2f(1.65f, 1.45f));
		lose.add(loseGUI);
		List<GuiTexture> error = new ArrayList<GuiTexture>();
		GuiTexture errorGUI = new GuiTexture(loader.loadTexture("error"),new Vector2f(0.6f, -0.42f),  new Vector2f(1.65f, 1.45f));
		error.add(errorGUI);
		List<GuiTexture> searching = new ArrayList<GuiTexture>();
		GuiTexture searchingGUI = new GuiTexture(loader.loadTexture("searching"),new Vector2f(0.6f, -0.42f),  new Vector2f(1.65f, 1.45f));
		searching.add(searchingGUI);
		GuiRenderer guiRenderer = new GuiRenderer(loader);
		List<Entity> entities = new ArrayList<Entity>();
		Random random = new Random(676452);
		for (int i = 0; i < 2000; i++) {
			if (i % 7 == 0) {
				entities.add(new Entity(tree,new Vector3f(random.nextFloat() * 800, 0, random.nextFloat() * -800), 0, 0, 0, 2));
			}
			if (i % 3 == 0) {
				entities.add(new Entity(tree,new Vector3f(random.nextFloat() * 800, 0, random.nextFloat() * -800), 0,random.nextFloat() * 360, 0, 2));
			}
		}
		Light light = new Light(new Vector3f(3000,2000,2000),new Vector3f(1,1,1));
		entities.add(new Entity(spookytree,new Vector3f(238.52f,-1,-300.52f),0,0,0,2));
		entities.add(new Entity(silo, new Vector3f(412,-1,-323),0,0,0,15));
		entities.add(new Entity(camper, new Vector3f(80,0,-408),0,0,0,1.7f));
		entities.add(new Entity(truck1, new Vector3f(92,0,-672),0,0,0,1.7f));
		entities.add(new Entity(truck2, new Vector3f(92,0,-672),0,0,0,1.7f));
		Opponent = new Entity(playerModel, new Vector3f(300,0,-300), 0,0, 0, 1);
		entities.add(new Entity(cutTree, new Vector3f(410,0,-670),0,0,0,10));
		entities.add(new Entity(cutTree, new Vector3f(385,0,-664),0,0,0,10));
		entities.add(new Entity(cutTree, new Vector3f(385,0,-702),0,0,0,10));
		entities.add(new Entity(cutTree, new Vector3f(402,0,-703),0,0,0,10));
		entities.add(new Entity(rock, new Vector3f(501,0,-586),0,0,0,2));
		entities.add(new Entity(tanker, new Vector3f(370,0,-570),0,0,0,4.5f));
		entities.add(new Entity(tanker, new Vector3f(340,0,-480),0,0,0,4.5f));
		entities.add(new Entity(tanker, new Vector3f(350,0,-530),0,0,0,4.5f));
		entities.add(new Entity(tanker, new Vector3f(293,0,-480),0,0,0,4.5f));
		entities.add(new Entity(tanker, new Vector3f(305,0,-565),0,0,0,4.5f));
		entities.add(new Entity(tanker, new Vector3f(300,0,-523),0,0,0,4.5f));
		entities.add(new Entity(building, new Vector3f(220,0,-500),0,90, 0,10));
		entities.add(new Entity(wall, new Vector3f(522,-3,-688),0,0, 0,10));
		entities.add(new Entity(wall, new Vector3f(523.2f,-3,-688),0, 90,0,10));
		entities.add(new Entity(wall, new Vector3f(523.5f,-3,-689),0,180,0,10));
		entities.add(new Entity(wall, new Vector3f(522,-3,-690),0,270,0,10));
		entities.add(new Entity(building2, new Vector3f(220, 0, -500),0,90,0,10));
		Terrain terrain = new Terrain(0, -1, loader, texturePack, blendMap);
		Terrain terrain2 = new Terrain(-1,-1, loader, texturePack, blendMap);
		Camera camera = new Camera(player);
		MasterRenderer renderer = new MasterRenderer();
		while (!Display.isCloseRequested()) {
			if (Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)) {
				ClientMain.displayup = false;
				break;
			}
			if(ClientMain.getGameState() < 13) {
				if(Mouse.isGrabbed() == true) {
					Mouse.setGrabbed(false);
				}
			}
			if(ClientMain.getGameState() == 13 || ClientMain.getGameState() == 14 || ClientMain.getGameState() == 15) {
				if(Mouse.isGrabbed() == false) {
					Mouse.setGrabbed(true);
				}
			}
			if(Keyboard.isKeyDown(Keyboard.KEY_BACK)) {
				if(ClientMain.getGameState() == 1 || ClientMain.getGameState() == 2 || ClientMain.getGameState() == 3 || ClientMain.getGameState() == 4 || ClientMain.getGameState() == 5) {
				ClientMain.setGameState(1);
				}
			}
			if(ClientMain.getGameState() < 13) {
				if(Keyboard.isKeyDown(Keyboard.KEY_W)) {
					if(ClientMain.getGameState() == 1 || ClientMain.getGameState() == 2) {
						ClientMain.setGameState(1);	
					}
				}
				if(Keyboard.isKeyDown(Keyboard.KEY_S)) {
					if(ClientMain.getGameState() == 1 || ClientMain.getGameState() == 2) {
						ClientMain.setGameState(2);
					}
					if(ClientMain.getGameState() == 3 || ClientMain.getGameState() == 4 || ClientMain.getGameState() == 5) {
						ClientMain.setGameState(4);
						ClientMain.graphicsSettings = 4;
					}
				}
				if(Keyboard.isKeyDown(Keyboard.KEY_A)) {
					if(ClientMain.getGameState() == 3 || ClientMain.getGameState() == 4 || ClientMain.getGameState() == 5) {
						ClientMain.setGameState(3);
						ClientMain.graphicsSettings = 3;
					}
				}
				if(Keyboard.isKeyDown(Keyboard.KEY_D)) {
					if(ClientMain.getGameState() == 3 || ClientMain.getGameState() == 4 || ClientMain.getGameState() == 5) {
						ClientMain.setGameState(5);
						ClientMain.graphicsSettings = 5;
					}
				}
				if(Keyboard.isKeyDown(Keyboard.KEY_RETURN)) {
					if(ClientMain.getGameState() == 1) {
						ClientMain.setGameState(6);
					}
					if(ClientMain.getGameState() == 2) {
						ClientMain.setGameState(ClientMain.graphicsSettings);
					}
				}
			}
			if (ClientMain.getGameState() == 1) {
				guiRenderer.render(mainMenuPlay);
			} else if (ClientMain.getGameState() == 2) {
				guiRenderer.render(mainMenuSettings);
			}else if (ClientMain.getGameState() == 3) {
				guiRenderer.render(settingsLow);
			} else if (ClientMain.getGameState() == 4) {
				guiRenderer.render(settingsMedium);
			} else if (ClientMain.getGameState() == 5) {
				guiRenderer.render(settingsHigh);
			} else if (ClientMain.getGameState() == 6) {
				guiRenderer.render(play);
			} else if (ClientMain.getGameState() == 7) {
				guiRenderer.render(connect);
			} else if (ClientMain.getGameState() == 8) {
				guiRenderer.render(error);
			} else if (ClientMain.getGameState() == 9) {
				guiRenderer.render(disconnect);
			} else if (ClientMain.getGameState() == 16) {
				guiRenderer.render(disconnect);
			} else if (ClientMain.getGameState() == 10) {
				guiRenderer.render(win);
				if(Keyboard.isKeyDown(Keyboard.KEY_RETURN)) {
					try {
						ClientController.windowClosing();
						} catch(NullPointerException e) {
							
						}
				}
			} else if (ClientMain.getGameState() == 11) {
				guiRenderer.render(lose);
				if(Keyboard.isKeyDown(Keyboard.KEY_RETURN)) {
					try {
						ClientController.windowClosing();
						} catch(NullPointerException e) {
							
						}
				}
			} else {
				if (Keyboard.isKeyDown(Keyboard.KEY_G)) {
					System.out.println("X: " + player.getPosition().x);
					System.out.println("Z: " + player.getPosition().z);
				}
				if(isInRightPosition == false) {
					player.setPosition(ClientController.getPlayerLocation());
					player.setRotation(0, ClientController.getPlayerRotation(), 0);
					isInRightPosition = true;
				}
				player.setModel(ClientController.getPlayerModel());
				camera.move();
				player.move();
				Opponent.setModel(ClientController.getOpponentModel());
				Opponent.setPosition(ClientController.getOpponentLocation());
				Opponent.setRotation(0,ClientController.getOpponentRotation(),0);
				renderer.processEntity(player);
				renderer.processEntity(Opponent);
				for (Entity entity : entities) {
					renderer.processEntity(entity);
				}
				renderer.processTerrain(terrain);
				renderer.processTerrain(terrain2);
				light.setPosition(new Vector3f(player.getPosition().x, player.getPosition().y + 10, player.getPosition().z));
				if(ClientController.opponentIsSlenderman == true) {
				light.setColor(1, light.getColor().y - ClientController.setSanity(), light.getColor().z - ClientController.setSanity());
				if(light.getColor().y < 0) {
					ClientController.broadcastEvent(false);
				}
				} else {
					light.setColor(1, light.getColor().y - ClientController.setSlenderSanity(), light.getColor().z - ClientController.setSlenderSanity());
					if(light.getColor().y < 0) {
						ClientController.broadcastEvent(false);
					}
				}
				renderer.render(light, camera);
				if(ClientController.myID == ClientController.model.getSlendermanid()) {
					guiRenderer.render(skinnyman);
				} else {
					guiRenderer.render(playerinfo);
				}
			}

			DisplayManager.updateDisplay();
		}
		try {
		ClientController.windowClosing();
		} catch(NullPointerException e) {
			
		}
		guiRenderer.cleanUp();
		renderer.cleanUp();
		loader.cleanUp();
		ClientMain.displayup = false;

		DisplayManager.closeDisplay();
	}

	public static Vector3f getPlayerPosition() {
		return player.getPosition();
	}
	

}
