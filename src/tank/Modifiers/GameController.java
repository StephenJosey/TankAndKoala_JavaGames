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
public class GameController extends MotionController implements KeyListener{
  Field field;
  Method action;
  int moveState;
  int[] keys;
  boolean player;
  String direction;

  public GameController(Player player){
    this(player, new int[] {KeyEvent.VK_LEFT,KeyEvent.VK_UP, KeyEvent.VK_RIGHT, KeyEvent.VK_DOWN, KeyEvent.VK_SPACE});
    moveState = 0;
    this.player = true;
  }

  public GameController(Player player, int[] keys){
    this.addObserver(player);
    this.action = null;
    this.field = null;
    this.keys = keys;
    GameWorld world = GameWorld.getInstance();
    world.addKeyListener(this);
  }

  public void signalKeyPress(KeyEvent e){

  }

  private void setMove(String direction) {
    /*try{
      field = Player.class.getDeclaredField(direction);
      moveState=1;
      this.setChanged();
    } catch (Exception e){e.printStackTrace();}*/
    moveState = 1;
    this.direction = direction;
    notifyObservers();
  }

  public String getMove() { return direction; }
  public int getMoveState() { return moveState; }

  private void setFire(){
    field = null;
    try{
      action = Player.class.getMethod("startFiring");
      this.setChanged();
    } catch(NoSuchMethodException e){e.printStackTrace();}
    notifyObservers();
  }

  private void unsetMove(String direction) {
    /*try{
      field = Player.class.getDeclaredField(direction);
      moveState = 0;
      this.setChanged();
    } catch (Exception e){e.printStackTrace();}*/
    moveState = 0;
    this.direction = direction;
    notifyObservers();
  }

  private void unsetFire(){
    field = null;
    try{
      action = Player.class.getMethod("stopFiring");
      this.setChanged();
    } catch(NoSuchMethodException e){e.printStackTrace();}
    notifyObservers();
  }

  public void read(Object theObject) {
    Player player = (Player) theObject;

    try{
      field.setInt(player, moveState);
    } catch (Exception e) {
      //e.printStackTrace();
      try {
        action.invoke(player);
      } catch (Exception e2) {}
    }
  }

  public void clearChanged(){
    super.clearChanged();
  }

  @Override
  public void update(Observable o, Object arg) {
  }

  public void keyPressed(KeyEvent e) {
    int code = e.getKeyCode();
    // left
    if(code==keys[0]) {
      this.setMove("left");
    }
    // up
    else if(code==keys[1]) {
      this.setMove("up");
    }
    // right
    else if(code==keys[2]) {
      this.setMove("right");
    }
    // down
    else if(code==keys[3]) {
      this.setMove("down");
    }
    // fire
    else if(code==keys[4]){
      this.setFire();
    }
    setChanged();
    this.notifyObservers();
  }

  public void keyReleased(KeyEvent e) {
    int code = e.getKeyCode();
    if(code==keys[0]) {		//
      this.unsetMove("left");
    }
    else if(code==keys[1]) {
      this.unsetMove("up");
    }
    else if(code==keys[2]) {
      this.unsetMove("right");
    }
    else if(code==keys[3]) {
      this.unsetMove("down");
    }
    else if(code==keys[4]){
      this.unsetFire();
    }
    setChanged();
    this.notifyObservers();
  }

  @Override
  public void keyTyped(KeyEvent e) {
    // TODO Auto-generated method stub

  }
}
