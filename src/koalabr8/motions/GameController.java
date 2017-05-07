package koalabr8.motions;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.lang.reflect.Method;
import java.lang.reflect.Field;
import java.util.Observable;

import koalabr8.game.Koala;
import koalabr8.GameWorld;

public class GameController extends Observable implements KeyListener {
  Field field;
  Method action;
  int moveState;
  int[] keys;
  String direction;
  int turn;
  boolean fire;

  public GameController(Koala koala, int[] keys ) {
    this.addObserver( koala );
    this.action = null;
    this.field = null;
    this.keys = keys;
    GameWorld world = GameWorld.getInstance();
    world.addKeyListener( this );
  }

  public String getMove() {
    return direction;
  }

  private void setMove( String direction ) {
    moveState = 1;
    this.direction = direction;
    notifyObservers();
  }

  public int getMoveState() {
    return moveState;
  }

  private void unsetMove( String direction ) {
    moveState = 0;
    this.direction = direction;
    notifyObservers();
  }

  public void clearChanged() {
    super.clearChanged();
  }


  public void keyPressed( KeyEvent e ) {
    int code = e.getKeyCode();
    // left
    if( code == keys[ 0 ] ) {
      this.setMove( "left" );
    }
    // up
    else if( code == keys[ 1 ] ) {
      this.setMove( "up" );
    }
    // right
    else if( code == keys[ 2 ] ) {
      this.setMove( "right" );
    }
    // down
    else if( code == keys[ 3 ] ) {
      this.setMove( "down" );
    }
    setChanged();
    this.notifyObservers();
  }

  public void keyReleased( KeyEvent e ) {
    int code = e.getKeyCode();
    if( code == keys[ 0 ] ) {
      this.unsetMove( "left" );
    } else if( code == keys[ 1 ] ) {
      this.unsetMove( "up" );
    } else if( code == keys[ 2 ] ) {
      this.unsetMove( "right" );
    } else if( code == keys[ 3 ] ) {
      this.unsetMove( "down" );
    }
    setChanged();
    this.notifyObservers();
  }

  @Override
  public void keyTyped( KeyEvent e ) {

  }
}
