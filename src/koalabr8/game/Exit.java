package koalabr8.game;

import java.awt.*;

/**
 * Created by Stephen on 5/5/2017.
 */
public class Exit extends GameObject {
  public Exit( int x, int y, Image image ) {
    super( x, y, image );
  }

  @Override
  public boolean collision( GameObject object ) {
    if( this.location.intersects( object.location ) ) {
      return true;
    }
    return false;
  }
}