package tank; /**
 * Created by jinghuihuang on 4/15/17.
 */
import tank.game.Map;
import tank.game.Player;
import tank.game.Tank;
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
  private BufferedImage bufferedImage;
  private ArrayList<Wall> walls;
  private ArrayList<Player> players;
  private Thread thread;
  private boolean gameOver;

  private Map map;

  public HashMap<String, Image> sprites;

  private GameWorld() {
    this.setFocusable(true);
    walls = new ArrayList<Wall>();
    sprites = new HashMap<String,Image>();
    players = new ArrayList<Player>();
  }

  public static GameWorld getInstance() {
    return game;
  }

  public void init(String file) {
    setBackground( Color.black);
    loadSprites();
    map = new Map(file);
    map.load();
  }

  public void loadSprites() {
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

  public void addWall(Wall wall) {
    walls.add(wall);
  }
  public void addPlayer(Player player) { players.add(player); }

  public void update( Observable observable, Object object) {
  }

  public boolean isGameOver() { return gameOver; }
}
