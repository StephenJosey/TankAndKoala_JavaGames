package koalabr8.game;

import koalabr8.GameWorld;

import java.awt.*;

public class Background extends GameObject {

  public Background( int width, int height, Image img ) {
    super( 0, 0, img );
  }

  @Override
  public void repaint( Graphics graphic ) {
    GameWorld game = GameWorld.getInstance();
    for( int y = 0; y < game.map.getHeight() * 32; y += getHeight() ) {
      for( int x = 0; x < game.map.getWidth() * 32; x += getWidth() ) {
        graphic.drawImage( getImage(), x, y, observer );
      }
    }
  }
}