package tank.Modifiers;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.lang.reflect.Method;
import java.lang.reflect.Field;
import java.util.Observable;

import tank.GameWorld;
import tank.game.Player;

/**
 * Created by jinghuihuang on 4/16/17.
 */
public class GameController extends Observable implements KeyListener {
  Field field;
  Method action;
  int moveState;
  int[] keys;
  String direction;
  int turn;
  boolean fire;

  public GameController( Player player, int[] keys ) {
    this.addObserver( player );
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

  public int getTurn() {
    return turn;
  }

  private void setTurn( int turn ) {
    this.turn = turn;
    notifyObservers();
  }

  public int getMoveState() {
    return moveState;
  }

  private void setFire() {
    fire = true;
    notifyObservers();
  }

  private void unsetMove( String direction ) {
    moveState = 0;
    this.direction = direction;
    notifyObservers();
  }

  private void unsetTurn() {
    turn = 0;
    notifyObservers();
  }

  private void unsetFire() {
    fire = false;
    notifyObservers();
  }

  public boolean getFire() {
    return fire;
  }

  public void clearChanged() {
    super.clearChanged();
  }


  public void keyPressed( KeyEvent e ) {
    int code = e.getKeyCode();
    // left
    if( code == keys[ 0 ] ) {
      this.setMove( "left" );
      this.setTurn( 1 );
    }
    // up
    else if( code == keys[ 1 ] ) {
      this.setMove( "up" );
    }
    // right
    else if( code == keys[ 2 ] ) {
      this.setTurn( -1 );
      this.setMove( "right" );
    }
    // down
    else if( code == keys[ 3 ] ) {
      this.setMove( "down" );
    }
    // fire
    else if( code == keys[ 4 ] ) {
      this.setFire();
    }
    setChanged();
    this.notifyObservers();
  }

  public void keyReleased( KeyEvent e ) {
    int code = e.getKeyCode();
    if( code == keys[ 0 ] ) {
      this.unsetTurn();
    } else if( code == keys[ 1 ] ) {
      this.unsetMove( "up" );
    } else if( code == keys[ 2 ] ) {
      this.unsetTurn();
    } else if( code == keys[ 3 ] ) {
      this.unsetMove( "down" );
    } else if( code == keys[ 4 ] ) {
      this.unsetFire();
    }
    setChanged();
    this.notifyObservers();
  }

  @Override
  public void keyTyped( KeyEvent e ) {
    // TODO Auto-generated method stub

  }
}
