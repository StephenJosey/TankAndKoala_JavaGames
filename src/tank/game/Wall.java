package tank.game;

import java.awt.Image;


public class Wall extends GameObject {
  private boolean destructible;

  public Wall( int x, int y, Image img, boolean destructible ) {
    super( x, y, img );
    this.destructible = destructible;
  }

  public boolean isDestructible() {
    return destructible;
  }

  public void setShow( boolean show ) {
    this.show = show;
  }
}