//package TankGame;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.IOException;
import java.util.Observer;

public abstract class GameObject {

  private int x;
  private int y;
  private int height;
  private int width;

//  private BufferedImage image;\
  private Image image;
  private ImageObserver observer;

  public GameObject () {

  }

  public GameObject( Image img ) {
    this( 0, 0, img );
  }

  public GameObject( int x, int y, Image img ) {
    this.x = x;
    this.y = y;
//    try {
      this.image = img;


//    image = ImageIO.read( new File( resourceLocation ));
//    this.observer = observer;
      height = image.getHeight( observer );
      width = image.getWidth( observer );
//    } catch( Exception e ) {}

  }

  public void setX( int x ) {
    this.x = x;
  }

  public int getX() {
    return this.x;
  }

  public void setY( int y ) {
    this.y = y;
  }

  public int getY() {
    return this.y;
  }

  public int getWidth() {
    return this.image.getWidth( observer );
  }

  public int getHeight() {
    return this.image.getHeight( observer );
  }

  public Image getImage() { return image; }

  public void repaint( Graphics graphics ) {
    graphics.drawImage( image, x, y, observer );
  }
}
