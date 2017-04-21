package tank; /**
 * Created by jinghuihuang on 4/15/17.
 */
import java.awt.event.ActionEvent;
import tank.game.*;
import tank.game.Wall;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.Buffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;

public final class GameWorld extends JPanel implements Observer, Runnable, ActionListener {
  private static final GameWorld game = new GameWorld();
  private static final int FPS = 60;

  private ArrayList<Wall> walls;
  private ArrayList<Player> players;
  private Background background;
  private Thread thread;
  private boolean gameOver;
  private Dimension dimension;
  private javax.swing.Timer timer;

  private int mapWidth, mapHeight;

  public Map map;

  public HashMap<String, Image> sprites;

  public HashMap<String, SpriteSheet> spriteSheets;

  private GameWorld() {
    this.setFocusable(true);
    walls = new ArrayList<Wall>();
    sprites = new HashMap<String,Image>();
    spriteSheets = new HashMap<>();
    players = new ArrayList<Player>();
    dimension = new Dimension(800, 600);
    timer = new Timer(1000 / (FPS), this);
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
    timer.start();
  }

  public void loadSprites()  {

    sprites.put("background", getSprite("Resources/Background.png"));
    sprites.put("wall", getSprite("Resources/Blue_wall1.png"));
    sprites.put("wall_destroy", getSprite("Resources/Blue_wall2.png"));
    sprites.put("tank", getSprite("Resources/tank.png"));

    spriteSheets.put("tank_blue_base", getSpriteSheet("Resources/Tank_blue_base_strip60.png", 60, 64, 64));
    spriteSheets.put("tank_blue_basic", getSpriteSheet("Resources/Tank_blue_basic_strip60.png", 60, 64, 64));
    spriteSheets.put("tank_blue_light", getSpriteSheet("Resources/Tank_blue_light_strip60.png", 60, 64, 64));
    spriteSheets.put("tank_blue_heavy", getSpriteSheet("Resources/Tank_blue_heavy_strip60.png", 60, 64, 64));


    spriteSheets.put("tank_red_base", getSpriteSheet("Resources/Tank_red_base_strip60.png", 60, 64, 64));
    spriteSheets.put("tank_red_basic", getSpriteSheet("Resources/Tank_red_basic_strip60.png", 60, 64, 64));
    spriteSheets.put("tank_red_light", getSpriteSheet("Resources/Tank_red_light_strip60.png", 60, 64, 64));
    spriteSheets.put("tank_red_heavy", getSpriteSheet("Resources/Tank_red_heavy_strip60.png", 60, 64, 64));

    spriteSheets.put("shell_basic", getSpriteSheet("Resources/Shell_basic_strip60.png", 60, 24, 24));
    spriteSheets.put("shell_heavy", getSpriteSheet("Resources/Shell_heavy_strip60.png", 60, 24, 24));
    spriteSheets.put("shell_light", getSpriteSheet("Resources/Shell_light_strip60.png", 60, 24, 24));
  }

  public SpriteSheet getSpriteSheet(String inPath, int size, int width, int height) {
    SpriteSheet s = new SpriteSheet(size, inPath);
    try {
      File f = new File(GameWorld.class.getResource(inPath).getFile());
      BufferedImage baseSheet = ImageIO.read(f);
      for (int col = 0; col < size; col++) {
        s.sprites[col] = baseSheet.getSubimage(col * width, 0 * height, width, height);
      }

    } catch (Exception e) {
      System.out.println(e.getMessage());
    }

    return s;
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
      try {
        thread.sleep(23);
      } catch (Exception e ) {}
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

  public void update() {
    for (int i = 0; i < walls.size(); i++) {
      for (int j = 0; j < players.size(); j++) {
         players.get( j ).collide( walls.get( i ));
      }
    }
    for (int i = 0; i < players.size(); i++) {
      for (int j = 1; j < players.size(); j++) {
        players.get( i ).collide( players.get( j ));
      }
    }
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    repaint();
    update();
    for (int i = 0; i < players.size(); i++) {
      players.get( i ).update();
    }
  }
  public void addWall(Wall wall) {
    walls.add(wall);
  }
  public void addPlayer(Player player) { players.add(player); }

  @Override
  public void update(Observable o, Object arg) {
  }

  public boolean isGameOver() { return gameOver; }
}
