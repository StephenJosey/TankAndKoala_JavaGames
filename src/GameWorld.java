/**
 * Created by jinghuihuang on 4/15/17.
 */
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
  private Thread thread;

  public HashMap<String, Image> sprites;

  private GameWorld() {
    this.setFocusable(true);
    walls = new ArrayList<Wall>();
  }

  public static GameWorld getInstance() {
    return game;
  }

  public void init() {
    setBackground( Color.black);
    URL url = GameWorld.class.getResource( "Resources/tank.png" );
    Image img = java.awt.Toolkit.getDefaultToolkit().getImage( url );
    Wall wall = new Wall(0, 0, img, false);
    addWall(wall);
  }

  public void start() {
    thread = new Thread (this);
    thread.start();
  }

  public void run() {
    Thread me = Thread.currentThread();
    while (thread == me) {
      this.requestFocusInWindow();
      this.repaint(createGraphics2D(50,50));
    }
  }

  public void repaint(Graphics2D graphic) {
    //Graphics2D graphic = createGraphics2D(50, 50);
    for (int i = 0; i < walls.size(); i++) {
      walls.get( i ).repaint( graphic );
    }
  }

  public Graphics2D createGraphics2D(int width, int height) {
    Graphics2D pane = null;
    if (bufferedImage == null)
      bufferedImage = (BufferedImage) createImage(width, height);
    pane = bufferedImage.createGraphics();
    pane.setBackground(getBackground());
    return pane;
  }

  public void paintComponent(Graphics2D graphics) {

  }

  public void addWall(Wall wall) {
    walls.add(wall);
  }

  public void update( Observable observable, Object object) {
  }
}
