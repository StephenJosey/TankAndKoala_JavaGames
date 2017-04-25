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
  protected boolean draw;
  int mapX, mapY;

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
    mapX = x;
    mapY = y;
    this.location = new Rectangle((x * width), (y * height), width, height);
    draw = true;
  }

  public int getX() {
    return location.x;
  }

  public int getY() {
    return location.y;
  }

  public int getMapX() { return mapX; }
  public int getMapY() { return mapY; }

  public boolean getShow() { return show; }

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
    if (show && draw) {
      graphics.drawImage(image, (location.x), (location.y), width , height , observer);
    }
    //draw = false;
  }
  public void draw(int x, int y, Graphics graphics ) {
    if (show) {
      graphics.drawImage(image, x, y, width , height , observer);
    }

  }
}
