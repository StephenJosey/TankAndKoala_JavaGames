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
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;

public final class GameWorld extends JPanel implements Observer, Runnable, ActionListener {
  private static final GameWorld game = new GameWorld();
  private static final int FPS = 60;
  private static final int SCREEN_SIZE = 700;

  private ArrayList<Wall> walls;
  private ArrayList<Player> players;
  private Background background;
  private Thread thread;
  private boolean gameOver;
  private String winner;
  private Dimension dimension;
  private javax.swing.Timer timer;

  private BufferedImage screen;
  int viewXMin;
  int viewXMax;
  int viewYMin;
  int viewYMax;
  int camX;
  int camY;

  private int mapWidth, mapHeight;

  public Map map;

  public HashMap<String, Image> sprites;

  public HashMap<String, SpriteSheet> spriteSheets;

  private GameWorld() {
    this.setFocusable(true);
    this.setName("Tank Wars");
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
    //setBackground( Color.black);
    loadSprites();
    map = new Map(file);
    map.load();
    mapWidth = map.getWidth();
    mapHeight = map.getHeight();
    background = new Background(mapWidth*32, mapHeight*32, sprites.get("background"));
    setDimension(SCREEN_SIZE * 2, SCREEN_SIZE);
    screen = new BufferedImage(mapWidth*32 + SCREEN_SIZE, mapHeight*32 + SCREEN_SIZE, BufferedImage.TYPE_INT_RGB);
    viewXMin = 0;
    viewXMax = getMap().getWidth()*16;
    viewYMin = 0;
    viewYMax = getMap().getHeight()*16;
    this.setSize( game.getDimension() );
    this.setVisible(true);
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

    spriteSheets.put("explosion", getSpriteSheet( "Resources/Explosion_small_strip6.png", 6, 32, 32));
  }

  public SpriteSheet getSpriteSheet(String inPath, int size, int width, int height) {
    SpriteSheet s = new SpriteSheet(size, inPath);
    try {
      File f = new File(GameWorld.class.getResource(inPath).getFile());
      BufferedImage baseSheet = ImageIO.read(f);
      for (int col = 0; col < size; col++) {
        s.sprites[col] = baseSheet.getSubimage(col * width, 0, width, height);
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
        Thread.sleep(23);
      } catch (Exception e) {
      }
    }
  }

  public void updateCam( Player player ) {
    camX = player.getX() - SCREEN_SIZE/2;
    camY = player.getY() - SCREEN_SIZE/2;
    if (camX > viewXMax) {
      camX = viewXMax;
    } else if (camX < 0) {
      camX = 0;
    }
    if (camY > viewYMax) {
      camY = viewYMax;
    } else if (camY < 0) {
      camY = 0;
    }
  }

  public void drawScreens() {
    if (! isGameOver() ) {
      Graphics graphics = screen.getGraphics();
      background.repaint(graphics);
      for (int i = 0; i < walls.size(); i++) {
        walls.get(i).repaint(graphics);
      }
      for (int i = 0; i < players.size(); i++) {
        players.get(i).repaint(graphics);
      }
    }
  }

  @Override
  public void paintComponent(Graphics graphics) {
    if ( !isGameOver() ) {
      updateCam(players.get(0));
      graphics.drawImage(screen.getSubimage(camX, camY, SCREEN_SIZE, SCREEN_SIZE), 0, 0, null);
      updateCam(players.get(1));
      graphics.setColor(Color.black);
      graphics.drawLine(SCREEN_SIZE, 0, SCREEN_SIZE, SCREEN_SIZE);
      graphics.drawImage(screen.getSubimage(camX, camY, SCREEN_SIZE, SCREEN_SIZE), SCREEN_SIZE + 1, 0, null);
      drawMiniMap(graphics, 6);
    } else {
      for (int i = 0; i < players.size(); i++) {
        if (players.get(i).getLives() > 0) {
          graphics.setFont(new Font("TimesRoman", Font.PLAIN, 50));
          graphics.drawString("Player " + (i + 1) + " wins!", SCREEN_SIZE / 2, SCREEN_SIZE / 2);
        }
      }
    }
  }

  public void drawMiniMap(Graphics graphics, int scale) {
    graphics.translate(SCREEN_SIZE - mapWidth*scale/2, SCREEN_SIZE - (mapHeight+2)*scale);
    for (int i = 0; i < players.size(); i++) {
      players.get(i).drawScaled(graphics, scale);
    }
    for (int i = 0; i < walls.size(); i++) {
      walls.get( i ).drawScaled( graphics, scale );
    }
  }


  public void update() {
    if ( ! isGameOver() ) {
      for (int i = 0; i < walls.size(); i++) {
        if (walls.get(i).getShow()) {
          for (int j = 0; j < players.size(); j++) {
            players.get(j).collide(walls.get(i));
            for (int k = 0; k < players.get(j).getBullets().size(); k++) {
              players.get(j).getBullets().get(k).collide(walls.get(i));
            }
          }
        }
      }
      for (int i = 0; i < players.size(); i++) {
        for (int j = 0; j < players.size(); j++) {
          players.get(i).collide(players.get(j));
          for (int k = 0; k < players.get(i).getBullets().size(); k++) {
            players.get(i).getBullets().get(k).collide(players.get(j));
          }
        }
      }

      for (int i = 0; i < players.size(); i++) {
          players.get(i).update();
      }
    }
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    if ( !isGameOver() ) {
      update();
      drawScreens();
      repaint();
    }
  }

  public Map getMap() { return map; }

  public void addWall(Wall wall) {
    walls.add(wall);
  }
  public void addPlayer(Player player) { players.add(player); }

  public ArrayList<Player> getPlayers() { return players; }
  public ArrayList<Wall> getWalls() { return walls; }
  public Background getBackgroundComponent() { return background; }
  @Override
  public void update(Observable o, Object arg) {
  }

  public boolean isGameOver() { return gameOver; }
  public void setGameOver( boolean gameOver ) {
    this.gameOver = gameOver;
  }
}
