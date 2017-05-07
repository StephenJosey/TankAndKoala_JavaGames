package koalabr8.game;


import koalabr8.GameWorld;

import java.awt.*;

public class Saw extends GameObject{
  private boolean horizontal;
  private int frame;
  private Rectangle collide;
  private int up, down, left, right;
  static final int speed = 1;
  public Saw( int x, int y, Image img, boolean horizontal ) {
    super( x, y, img );
    this.horizontal = horizontal;
    frame = 0;
    collide = new Rectangle( location.x, location.y, width, height );
    up = left = down = right = 0;
    if ( horizontal ) {
      right = 1;
    } else {
      up = 1;
    }
  }

  @Override
  public boolean collision(GameObject object) {
    if( this.location.intersects(object.location) ) {
      return true;
    }
    return false;
  }

  @Override
  public void repaint(Graphics graphics) {
    if ( horizontal ) {
      graphics.drawImage(GameWorld.getInstance().spriteSheets.get( "saw_horizontal").getSprites()[ frame ], location.x, location.y, null );
    } else {
      graphics.drawImage(GameWorld.getInstance().spriteSheets.get( "saw_vertical").getSprites()[ frame ], location.x, location.y, null );
    }
  }

  public boolean collide(GameObject object) {
    if ( object instanceof Wall ) {
      if ( collide.intersects(object.getLocation() ) ){
        return true;
      }
    }
    return false;
  }

  public int getLeft() { return left; }
  public int getRight() { return right; }
  public int getUp() { return up; }
  public int getDown() { return down; }

  public void setLeft( int left ) {
    this.left = left;
  }
  public void setRight( int right ) {
    this.right = right;
  }
  public void setUp( int up ) {
    this.up = up;
  }
  public void setDown( int down ) {
    this.down = down;
  }

  public void update() {
    if( up == 1 ) {
      location.y -= speed;
      collide.y -= speed;
      frame++;
    } else if( down == 1 ) {
      location.y += speed;
      collide.y += speed;
      frame++;
    } else if( left == 1 ) {
      location.x -= speed;
      collide.x -= speed;
      frame++;
    } else if( right == 1 ) {
      location.x += speed;
      collide.x += speed;
      frame++;
    }
    if ( frame > 1 ) {
      frame = 0;
    }
  }
}
