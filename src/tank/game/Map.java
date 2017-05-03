package tank.game;

import tank.GameWorld;

import java.awt.event.KeyEvent;
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

          if( mapCode == '1' ) {
            Wall wall = new Wall( i, height, world.sprites.get( "wall" ), false );
            world.addWall( wall );
            map[ height ][ i ] = wall;
          } else if( mapCode == '2' ) {
            Wall wall = new Wall( i, height, world.sprites.get( "wall_destroy" ), true );
            world.addWall( wall );
            map[ height ][ i ] = wall;
          } else if( mapCode == '3' ) {
            int[] controls = { KeyEvent.VK_A, KeyEvent.VK_W, KeyEvent.VK_D, KeyEvent.VK_S, KeyEvent.VK_SPACE };
            Player player = new Player( i, height, 180, world.spriteSheets.get( "tank_blue_basic" ), controls );
            world.addPlayer( player );
            map[ height ][ i ] = player;
          } else if( mapCode == '4' ) {
            int[] controls = new int[]{ KeyEvent.VK_LEFT, KeyEvent.VK_UP, KeyEvent.VK_RIGHT, KeyEvent.VK_DOWN, KeyEvent.VK_ENTER };
            Player player = new Player( i, height, 0, world.spriteSheets.get( "tank_red_basic" ), controls );
            world.addPlayer( player );
            map[ height ][ i ] = player;
          } else {
            map[ height ][ i ] = null;
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