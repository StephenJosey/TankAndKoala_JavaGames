package tank.game;//package TankGame;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.IOException;
import java.util.Observer;

public abstract class GameObject {

  protected int height;
  protected int width;
  protected Rectangle location;
  protected boolean show;

//  private BufferedImage image;\
  Image image;
  ImageObserver observer;

  public GameObject () {

  }
  public GameObject( Image img ) {
    this( 0, 0, img );
  }

  public GameObject( int x, int y, Image img ) {
    this.image = img;
    height = image.getHeight( observer );
    width = image.getWidth( observer );
    show = true;
    this.location = new Rectangle((x * width)/2, (y * height)/2, width/2, height/2);
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

  public void setWidth(int width) {
    this.width = width;
  }

  public int getHeight() {
    return this.image.getHeight( observer );
  }

  public void setHeight(int height) {
    this.height = height;
  }

  public Image getImage() { return image; }

  public Rectangle getLocation() { return location; }

  public void setLocation(int x, int y) {
    location.x = x;
    location.y = y;
  }

  public void repaint( Graphics graphics ) {
    if (show) {
      graphics.drawImage(image, (location.x), (location.y), width / 2, height / 2, observer);
    }
  }
}
