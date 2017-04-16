package tank;

import java.util.*;
/**
 * Created by jinghuihuang on 4/16/17.
 */
class GameClock extends Observable {
  private int startTime;
  private int frame;

  public GameClock(){
    startTime = (int) System.currentTimeMillis();
    frame = 0;
  }

  public void tick(){
    frame++;
    setChanged();
    this.notifyObservers();
  }

  public int getFrame(){
    return this.frame;
  }

  public int getTime(){
    return (int)System.currentTimeMillis()-startTime;
  }
}
