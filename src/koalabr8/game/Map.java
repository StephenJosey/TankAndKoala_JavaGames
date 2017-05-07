package koalabr8.game;

import koalabr8.GameWorld;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by Stephen on 4/15/2017.
 */
public class Map {
  String filename;
  BufferedReader level;
  int width, height;
  GameObject map[][];
  final int WALL_WIDTH = 40;
  final int WALL_HEIGHT = 40;
  BufferedImage wallImage = toBufferedImage( GameWorld.getInstance().sprites.get("wall_tiles" ) );

  public static BufferedImage toBufferedImage(Image img)
  {
    if (img instanceof BufferedImage)
    {
      return (BufferedImage) img;
    }
    BufferedImage bimage = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);

    Graphics2D bGr = bimage.createGraphics();
    bGr.drawImage(img, 0, 0, null);
    bGr.dispose();
    return bimage;
  }
  public Map( String filename ) {
    this.filename = filename;
    String line;
    try {
      level = new BufferedReader( new InputStreamReader( GameWorld.class.getResource( "Resources/" + filename ).openStream() ) );
      line = level.readLine();
      width = line.length();
      height = 0;
      while( line != null ) {
        height++;
        line = level.readLine();
      }
      map = new GameObject[ height ][ width + 1 ];
      level.close();
    } catch( IOException e ) {
      e.printStackTrace();
      System.exit( 1 );
    }
  }

  public void load() {
    GameWorld world = GameWorld.getInstance();
    try {
      level = new BufferedReader( new InputStreamReader( GameWorld.class.getResource( "Resources/" + filename ).openStream() ) );
    } catch( IOException e ) {
      e.printStackTrace();
      System.exit( 1 );
    }

    String line;
    try {
      line = level.readLine();
      width = line.length();
      height = 0;
      while( line != null ) {
        for( int i = 0, n = line.length(); i < n; i++ ) {
          char mapCode = line.charAt( i );
          int hex = Character.getNumericValue(mapCode);
          // Map codes 0-15 are wall tiles
          // Grabbing the subimage due to it being a tilesheet instead of a spritesheet
          // k = Koala
          // z = red exit
          if( hex >= 0 && hex < 16 ) {
            Wall wall = new Wall( i, height, wallImage.getSubimage( (hex % 4) * WALL_WIDTH, (hex / 4) * WALL_HEIGHT, WALL_WIDTH, WALL_HEIGHT ) );
            world.addWall( wall );
          } else if (mapCode == 'k' ) {
            int[] controls = { KeyEvent.VK_A, KeyEvent.VK_W, KeyEvent.VK_D, KeyEvent.VK_S};
            Koala koala = new Koala( i, height, world.sprites.get( "koala_stand" ), controls );
            world.addKoala( koala );
          } else if (mapCode == 'z' ) {
            Exit exit = new Exit(i, height, world.sprites.get( "red_exit") );
            world.addObject( exit );
          } else if (mapCode == 't') {
            TNT tnt = new TNT( i, height, world.sprites.get( "tnt" ) );
            world.addTNT( tnt );
          } else if (mapCode == 'h') {
            Saw saw = new Saw(i, height, world.spriteSheets.get( "saw_horizontal").getSprites()[0], true  );
            world.addSaw( saw );
          } else if (mapCode == 'v') {
            Saw saw = new Saw(i, height, world.spriteSheets.get( "saw_vertical").getSprites()[0], false  );
            world.addSaw( saw );
          }
        }
        height++;
        line = level.readLine();
      }
      level.close();
    } catch( IOException e ) {
      e.printStackTrace();
    }
  }

  public int getWidth() {
    return width;
  }

  public int getHeight() {
    return height;
  }
}