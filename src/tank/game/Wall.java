package tank.game;

import java.awt.Image;

/**
 * Created by jinghuihuang on 4/15/17.
 */
public class Wall extends GameObject {
  private boolean destructible;

  public Wall ( int x, int y, Image img, boolean destructible) {
    super( x, y, img );
    this.destructible = destructible;

  }

  public boolean collision(GameObject object) {
    if ( location.intersects( object.getLocation() ) ) {
      return true;
    }
    return false;
  }
}