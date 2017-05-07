package koalabr8.game;

import java.awt.*;
import java.awt.image.ImageObserver;

public abstract class GameObject {

  protected int height;
  protected int width;
  public Rectangle location;
  protected boolean show;
  int mapX, mapY;

  Image image;
  ImageObserver observer;

  public GameObject() {

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
    this.location = new Rectangle( ( x * width ), ( y * height ), width, height );
  }

  public int getX() {
    return location.x;
  }

  public int getY() {
    return location.y;
  }

  public boolean getShow() {
    return show;
  }

  public int getWidth() {
    return this.image.getWidth( observer );
  }

  public int getHeight() {
    return this.image.getHeight( observer );
  }

  public Image getImage() {
    return image;
  }

  public Rectangle getLocation() {
    return location;
  }

  public void drawScaled( Graphics graphics, int scale ) {
    if( show ) {
      graphics.drawImage( image, ( location.x / scale ), ( location.y / scale ), width / scale, height / scale, observer );
    }
  }

  public boolean collision(GameObject object) {
    if( this.location.intersects(object.location) ) {
      return true;
    }
    return false;
  }

  public void repaint( Graphics graphics ) {
    if( show ) {
      graphics.drawImage( image, ( location.x ), ( location.y ), width, height, observer );
    }
  }
}
