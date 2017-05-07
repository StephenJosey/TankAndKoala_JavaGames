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
  static final int SPEED = 1;

  public int up, down, left, right;
  int direction;
  int speed;
  Rectangle respawn;
  int respawnDirection;
  int lives;
  int frame;
  private GameController controller;
  String imageDir;
  Rectangle collide;

  public Koala( int x, int y, Image image, int[] keys ) {
    super( x, y, image );
    controller = new GameController( this, keys );
    up = down = left = right = 0;
    speed = SPEED;
    respawn = new Rectangle( location.x, location.y, width, height );
    respawnDirection = direction;
    lives = 3;
    frame = 0;
    imageDir = "koala_stand";
    collide = new Rectangle(location.x, location.y, width, height);
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
    if( up == 1  ) {
      location.y -= speed;
      collide.y += speed;
      imageDir = "koala_up";
      frame++;
      down = left = right = 0;
    } else if( down == 1  ) {
      location.y += speed;
      collide.y -= speed;
      imageDir = "koala_down";
      frame++;
      up = left = right = 0;
    } else if( left == 1  ) {
      location.x -= speed;
      collide.x -= speed;
      imageDir = "koala_left";
      frame++;
      down = up = right = 0;
    } else if( right == 1  ) {
      location.x += speed;
      collide.x += speed;
      imageDir = "koala_right";
      frame++;
      down = left = up = 0;
    }
    if ( up == 0 && down == 0 && left == 0 && right == 0 ) {
      imageDir = "koala_stand";
    }
    if ( frame >= 8 ) {
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


  public boolean collide( GameObject object ) {
    if ( up == 1 ) {
      collide.y -= 1;
    }
    if ( down == 1 ) {
      collide.y += 1;
    }
    if ( left == 1 ) {
      collide.x -= 1;
    }
    if ( right == 1 ) {
      collide.x += 1;
    }
    if( object instanceof Wall || object instanceof Koala && ( this != object ) ) {
      if( collide.intersects( object.getLocation() ) ) {
        resetCollide();
        return true;
      }
    }
    resetCollide();
    return false;
  }

  public int getSpeed() { return speed; }
  public boolean isMoving() {
    if ( up == 1 || down == 1 || left == 1 || right == 1 ) {
      return true;
    }
    return false;
  }

  public void resetCollide() {
    collide = new Rectangle (location.x, location.y, width, height);
  }

  @Override
  public void repaint( Graphics graphics ) {
    if ( imageDir.equals( "koala_stand" ) ) {
      graphics.drawImage( GameWorld.getInstance().sprites.get( imageDir ), ( location.x ), ( location.y ), width, height, observer );
    } else {
      graphics.drawImage(GameWorld.getInstance().spriteSheets.get(imageDir).getSprites()[frame], (location.x), (location.y), width, height, observer);
    }
  }
}
