package koalabr8.game;

import java.awt.*;
import java.lang.reflect.Field;
import java.util.*;

import koalabr8.GameWorld;
import koalabr8.motions.*;
import koalabr8.SpriteSheet;

/**
 * Created by Stephen on 4/15/2017.
 */
public class Koala extends GameObject implements Observer {
  static final int SPEED = 2;

  public int up, down, left, right;
  int speed;
  int frame;
  String imageDir;
  Rectangle collide;
  SpriteSheet explosion;
  int explodeFrame;
  boolean isExploding;
  boolean dead;
  private GameController controller;

  public Koala( int x, int y, Image image, int[] keys ) {
    super( x, y, image );
    controller = new GameController( this, keys );
    up = down = left = right = 0;
    speed = SPEED;
    frame = 0;
    imageDir = "koala_stand";
    collide = new Rectangle( location.x + 4, location.y + 4, width - 4, height - 4 );
    explosion = GameWorld.getInstance().spriteSheets.get( "explosion" );
    isExploding = false;
    explodeFrame = 0;
    dead = false;
  }

  public void update( Observable o, Object arg ) {
    String direction = controller.getMove();
    int moveState = controller.getMoveState();
    Field thisDirection = null;
    try {
      thisDirection = this.getClass().getDeclaredField( direction );
      thisDirection.setInt( this, moveState );
    } catch( Exception e ) {
    }
  }


  public void update() {
    if( isDead() ) {
      imageDir = "koala_dead";
      return;
    }
    if( up == 1 ) {
      location.y -= speed;
      collide.y -= speed;
      imageDir = "koala_up";
      frame++;
      down = left = right = 0;
    } else if( down == 1 ) {
      location.y += speed;
      collide.y += speed;
      imageDir = "koala_down";
      frame++;
      up = left = right = 0;
    } else if( left == 1 ) {
      location.x -= speed;
      collide.x -= speed;
      imageDir = "koala_left";
      frame++;
      down = up = right = 0;
    } else if( right == 1 ) {
      location.x += speed;
      collide.x += speed;
      imageDir = "koala_right";
      frame++;
      down = left = up = 0;
    }
    if( up == 0 && down == 0 && left == 0 && right == 0 ) {
      imageDir = "koala_stand";
    }
    if( frame >= 8 ) {
      frame = 0;
    }

    // Call the controller again in case collision turned off movements
    String direction = controller.getMove();
    int moveState = controller.getMoveState();
    Field thisDirection = null;
    try {
      thisDirection = this.getClass().getDeclaredField( direction );
      thisDirection.setInt( this, moveState );
    } catch( Exception e ) {
    }
  }

  public boolean isDead() {
    return dead;
  }

  public void setDead( boolean dead ) {
    this.dead = dead;
  }

  public boolean collide( GameObject object ) {
    if( up == 1 ) {
      collide.y -= 1;
    }
    if( down == 1 ) {
      collide.y += 1;
    }
    if( left == 1 ) {
      collide.x -= 1;
    }
    if( right == 1 ) {
      collide.x += 1;
    }
    if( object instanceof Wall || object instanceof TNT || object instanceof Saw || object instanceof Koala && ( this != object ) ) {
      if( collide.intersects( object.getLocation() ) ) {
        resetCollide();
        return true;
      }
    }
    resetCollide();
    return false;
  }


  public boolean isMoving() {
    if( up == 1 || down == 1 || left == 1 || right == 1 ) {
      return true;
    }
    return false;
  }

  public void resetCollide() {
    collide = new Rectangle( location.x + 8, location.y + 8, location.width - 10, location.height - 10 );
  }

  @Override
  public void repaint( Graphics graphics ) {
    if( isDead() ) {
      graphics.drawImage( GameWorld.getInstance().sprites.get( imageDir ), ( location.x ), ( location.y ), width, height, observer );
    } else if( imageDir.equals( "koala_stand" ) ) {
      graphics.drawImage( GameWorld.getInstance().sprites.get( imageDir ), ( location.x ), ( location.y ), width, height, observer );
    } else {
      graphics.drawImage( GameWorld.getInstance().spriteSheets.get( imageDir ).getSprites()[ frame ], ( location.x ), ( location.y ), width, height, observer );
    }

  }
}