package tank.game;//package TankGame;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.IOException;
import java.util.Observer;

public abstract class GameObject {

  private int height;
  private int width;
  protected Rectangle location;

//  private BufferedImage image;\
  private Image image;
  private ImageObserver observer;

  public GameObject () {

  }
  public GameObject( Image img ) {
    this( 0, 0, img );
  }

  public GameObject( int x, int y, Image img ) {
    this.image = img;
    height = image.getHeight( observer );
    width = image.getWidth( observer );
    this.location = new Rectangle(x, y, width, height);
  }

  public int getX() {
    return location.x;
  }

  public int getY() {
    return location.y;
  }

  public int getWidth() {
    return this.image.getWidth( observer );
  }

  public int getHeight() {
    return this.image.getHeight( observer );
  }

  public Image getImage() { return image; }

  public Rectangle getLocation() { return location; }

  public void repaint( Graphics graphics ) {
    graphics.drawImage( image, location.x, location.y, observer );
  }
}
