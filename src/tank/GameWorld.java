package tank; /**
 * Created by jinghuihuang on 4/15/17.
 */
import tank.Modifiers.AbstractGameModifier;
import tank.game.*;
import tank.game.Wall;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;

public final class GameWorld extends JPanel implements Observer, Runnable {
  private static final GameWorld game = new GameWorld();
  private static final GameClock clock = new GameClock(); // -tyler
  private BufferedImage bufferedImage;
  private ArrayList<Wall> walls;
  private ArrayList<Player> players;
  private Background background;
  private Thread thread;
  private boolean gameOver;
  private Dimension dimension;

  private int mapWidth, mapHeight;

  public Map map;

  public HashMap<String, Image> sprites;

  private GameWorld() {
    this.setFocusable(true);
    walls = new ArrayList<Wall>();
    sprites = new HashMap<String,Image>();
    players = new ArrayList<Player>();
    dimension = new Dimension(800, 600);
  }

  public static GameWorld getInstance() {
    return game;
  }

  public Dimension getDimension() { return dimension; }
  public void setDimension(int width, int height) {
    dimension.setSize(width, height);
  }

  public void init(String file) {
    setBackground( Color.black);
    loadSprites();
    map = new Map(file);
    map.load();
    mapWidth = map.getWidth();
    mapHeight = map.getHeight();
    background = new Background(mapWidth*32, mapHeight*32, sprites.get("background"));
    setDimension(mapWidth*16 + 16, mapHeight*17);
  }

  public void loadSprites() {
    sprites.put("background", getSprite("Resources/Background.png"));
    sprites.put("wall", getSprite("Resources/Blue_wall1.png"));
    sprites.put("wall_destroy", getSprite("Resources/Blue_wall2.png"));
    sprites.put("tank", getSprite("Resources/tank.png"));
  }

  public Image getSprite(String name) {
    URL url = GameWorld.class.getResource(name);
    Image img = java.awt.Toolkit.getDefaultToolkit().getImage(url);
    try {
      MediaTracker tracker = new MediaTracker(this);
      tracker.addImage(img, 0);
      tracker.waitForID(0);
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
    return img;
  }

  public void start() {
    thread = new Thread (this);
    thread.start();
  }

  public void run() {
    Thread me = Thread.currentThread();
    while (thread == me) {
      this.requestFocusInWindow();
      repaint();
    }
  }

  public void paint(Graphics graphic) {
    super.paintComponent(graphic);
    background.repaint(graphic);
    for (int i = 0; i < walls.size(); i++) {
      walls.get( i ).repaint( graphic );
    }
    for (int i = 0; i < players.size(); i++) {
      players.get( i ).repaint( graphic );
    }

  }

  /*public Graphics2D createGraphics2D(int width, int height) {
    Graphics2D pane = null;
    if (bufferedImage == null)
      bufferedImage = (BufferedImage) createImage(width, height);
    pane = bufferedImage.createGraphics();
    pane.setBackground(getBackground());
    return pane;
  }*/

// by Tyler:
  public void addClockObserver(Observer theObject){
    clock.addObserver(theObject);
  }

  public void removeClockObserver(Observer theObject){
    clock.deleteObserver(theObject);
  }

  public int getFrameNumber(){
    return clock.getFrame();
  }

  public int getTime(){
    return clock.getTime();
  }
// end by tyler
  public void addWall(Wall wall) {
    walls.add(wall);
  }
  public void addPlayer(Player player) { players.add(player); }

  @Override
  public void update(Observable o, Object arg) {
    AbstractGameModifier modifier = (AbstractGameModifier) o;
    modifier.read(this);
  }

  public boolean isGameOver() { return gameOver; }
}
