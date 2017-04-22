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
public class GameController extends Observable implements  KeyListener{
  Field field;
  Method action;
  int moveState;
  int[] keys;
  boolean player;
  String direction;
  int turn;
  boolean fire;

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
    moveState = 1;
    this.direction = direction;
    notifyObservers();
  }

  private void setTurn(int turn) {
    this.turn = turn;
    notifyObservers();
  }

  public String getMove() { return direction; }
  public int getTurn() { return turn; }
  public int getMoveState() { return moveState; }

  private void setFire(){
    fire = true;
    notifyObservers();
  }

  private void unsetMove(String direction) {
    moveState = 0;
    this.direction = direction;
    notifyObservers();
  }

  private void unsetTurn() {
    turn = 0;
    notifyObservers();
  }

  private void unsetFire(){
    fire = false;
    notifyObservers();
  }

  public boolean getFire() {
    return fire;
  }
  public void clearChanged(){
    super.clearChanged();
  }


  public void keyPressed(KeyEvent e) {
    int code = e.getKeyCode();
    // left
    if(code==keys[0]) {
      this.setMove("left");
      this.setTurn(1);
    }
    // up
    else if(code==keys[1]) {
      this.setMove("up");
    }
    // right
    else if(code==keys[2]) {
      this.setTurn(-1);
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
      this.unsetTurn();
      //this.unsetMove("left");
    }
    else if(code==keys[1]) {
      this.unsetMove("up");
    }
    else if(code==keys[2]) {
      this.unsetTurn();
      //this.unsetMove("right");
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
