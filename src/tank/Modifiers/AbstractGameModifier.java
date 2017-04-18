package tank.Modifiers;

import java.util.Observable;

/**
 * Created by jinghuihuang on 4/16/17.
 */
public abstract class AbstractGameModifier extends Observable {

  public AbstractGameModifier(){}

  /* read is used to send messages from game observables to game observers */
  public abstract void read(Object theObject);
}