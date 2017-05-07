package koalabr8.game;


import java.awt.*;

/**
 * Created by jinghuihuang on 5/6/17.
 */
public class TNT extends GameObject{
  public TNT( int x, int y, Image img ) {
    super( x, y, img );
  }

  @Override
  public boolean collision(GameObject object) {
    if( this.location.intersects(object.location) ) {
      return true;
    }
    return false;
  }
}
