package koalabr8;

import koalabr8.game.*;

import java.awt.event.ActionEvent;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;

public final class GameWorld extends JPanel implements Observer, Runnable, ActionListener {
  private static final GameWorld game = new GameWorld();
  private static final int FPS = 60;
  private static final int DEAD_COUNTER = 100;
  public Map map;
  public HashMap< String, Image > sprites;
  public HashMap< String, SpriteSheet > spriteSheets;
  int deadCounter;
  String level;
  private ArrayList< Wall > walls;
  private ArrayList< Koala > koalas;
  private ArrayList< TNT > tnts;
  private ArrayList< Saw > saws;
  private ArrayList< GameObject > objects;
  private Background background;
  private Thread thread;
  private boolean gameOver;
  private boolean lost;
  private Dimension dimension;
  private javax.swing.Timer timer;
  private int mapWidth, mapHeight;
  private int rescued;

  private GameWorld() {
    this.setFocusable( true );
    this.setName( "Tank Wars" );
    walls = new ArrayList<>();
    sprites = new HashMap<>();
    spriteSheets = new HashMap<>();
    koalas = new ArrayList<>();
    tnts = new ArrayList<>();
    dimension = new Dimension( 800, 600 );
    timer = new Timer( 1000 / ( FPS ), this );
    objects = new ArrayList<>();
    saws = new ArrayList<>();
  }

  public static GameWorld getInstance() {
    return game;
  }

  public Dimension getDimension() {
    return dimension;
  }

  public void setDimension( int width, int height ) {
    dimension.setSize( width, height );
  }

  public void init( String file ) {
    loadSprites();
    map = new Map( file );
    map.load();
    mapWidth = map.getWidth();
    mapHeight = map.getHeight();
    rescued = 0;
    background = new Background( mapWidth * 32, mapHeight * 32, sprites.get( "background" ) );
    setDimension( mapWidth * 41, mapHeight * 48 );
    level = file;
    gameOver = false;
    lost = false;
    deadCounter = 0;
    GameSound.music.play();
    this.setSize( game.getDimension() );
    this.setVisible( true );
    timer.start();
  }

  public void loadSprites() {

    sprites.put( "background", getSprite( "Resources/Background.png" ) );
    sprites.put( "help", getSprite( "Resources/Button_help.png" ) );
    sprites.put( "load", getSprite( "Resources/Button_load.png" ) );
    sprites.put( "start", getSprite( "Resources/Button_start.png" ) );
    sprites.put( "quit", getSprite( "Resources/Button_quit.png" ) );
    sprites.put( "koala_dead", getSprite( "Resources/Koala_dead.png" ) );
    sprites.put( "koala_stand", getSprite( "Resources/Koala_stand.png" ) );
    sprites.put( "wall_tiles", getSprite( "Resources/wall_tiles.png" ) );
    sprites.put( "rescued", getSprite( "Resources/Rescued.png" ) );
    sprites.put( "red_exit", getSprite( "Resources/Exit1.png" ) );
    sprites.put( "tnt", getSprite( "Resources/TNT.png" ) );
    sprites.put( "congratulation", getSprite( "Resources/Congratulation.png" ) );

    spriteSheets.put( "koala_down", getSpriteSheet( "Resources/Koala_down_strip8.png", 8, 40, 40 ) );
    spriteSheets.put( "koala_up", getSpriteSheet( "Resources/Koala_up_strip8.png", 8, 40, 40 ) );
    spriteSheets.put( "koala_left", getSpriteSheet( "Resources/Koala_left_strip8.png", 8, 40, 40 ) );
    spriteSheets.put( "koala_right", getSpriteSheet( "Resources/Koala_right_strip8.png", 8, 40, 40 ) );
    spriteSheets.put( "saw_horizontal", getSpriteSheet( "Resources/Saw_horizontal_strip2.png", 2, 40, 40 ) );
    spriteSheets.put( "saw_vertical", getSpriteSheet( "Resources/Saw_vertical_strip2.png", 2, 40, 40 ) );
  }

  public SpriteSheet getSpriteSheet( String inPath, int size, int width, int height ) {
    SpriteSheet s = new SpriteSheet( size, inPath );
    try {
      File f = new File( GameWorld.class.getResource( inPath ).getFile() );
      BufferedImage baseSheet = ImageIO.read( f );
      for( int col = 0; col < size; col++ ) {
        s.sprites[ col ] = baseSheet.getSubimage( col * width, 0, width, height );
      }

    } catch( Exception e ) {
      System.out.println( e.getMessage() );
    }

    return s;
  }

  public Image getSprite( String name ) {
    URL url = GameWorld.class.getResource( name );
    Image img = java.awt.Toolkit.getDefaultToolkit().getImage( url );
    try {
      MediaTracker tracker = new MediaTracker( this );
      tracker.addImage( img, 0 );
      tracker.waitForID( 0 );
    } catch( Exception e ) {
      System.out.println( e.getMessage() );
    }
    return img;
  }

  public void start() {
    thread = new Thread( this );
    thread.start();
  }

  public void run() {
    Thread me = Thread.currentThread();
    while( thread == me ) {
      this.requestFocusInWindow();
      try {
        Thread.sleep( 23 );
      } catch( Exception e ) {
      }
    }
  }

  @Override
  public void paintComponent( Graphics graphics ) {
    if( !isGameOver() || lost ) {
      background.repaint( graphics );
      graphics.drawImage( sprites.get( "rescued" ), 0, 0, null );
      graphics.setFont( new Font( "TimesRoman", Font.BOLD, 50 ) );
      graphics.setColor( Color.WHITE );
      graphics.drawString( String.valueOf( rescued ), 150, 40 );
      graphics.translate( 0, sprites.get( "rescued" ).getHeight( null ) );
      for( int i = 0; i < walls.size(); i++ ) {
        walls.get( i ).repaint( graphics );
      }
      for( int i = 0; i < koalas.size(); i++ ) {
        koalas.get( i ).repaint( graphics );
      }
      for( int i = 0; i < tnts.size(); i++ ) {
        tnts.get( i ).repaint( graphics );
      }
      for( int i = 0; i < saws.size(); i++ ) {
        saws.get( i ).repaint( graphics );
      }
      for( int i = 0; i < objects.size(); i++ ) {
        objects.get( i ).repaint( graphics );
      }
      if( lost ) {
        deadCounter++;
        if( deadCounter > DEAD_COUNTER ) {
          restart();
        }
      }
    } else if( gameOver && !lost ) {
      background.repaint( graphics );
      graphics.drawImage( sprites.get( "congratulation" ), 0, 0, null );
    }
  }

  public void wallCollision( Koala koala, Wall wall ) {
    if( koala.collide( wall ) ) {
      if( koala.getY() > wall.getY() ) {
        koala.up = 0;
      }
      if( koala.getY() < wall.getY() ) {
        koala.down = 0;
      }
      if( koala.getX() > wall.getX() ) {
        koala.left = 0;
      }
      if( koala.getX() < wall.getX() ) {
        koala.right = 0;
      }
    }
  }

  public void koalaCollision( Koala koala1, Koala koala2 ) {
    if( koala1.isMoving() ) {
      if( koala1.collide( koala2 ) ) {
        if( koala1.getY() > koala2.getY() ) {
          koala1.up = 0;
        }
        if( koala1.getY() < koala2.getY() ) {
          koala1.down = 0;
        }
        if( koala1.getX() > koala2.getX() ) {
          koala1.left = 0;
        }
        if( koala1.getX() < koala2.getX() ) {
          koala1.right = 0;
        }
      }
    }
  }

  public void tntCollision( Koala koala, TNT tnt ) {
    if( koala.collide( tnt ) ) {
      koala.setDead( true );
      GameSound.tnt.play();
      lost = true;
      gameOver = true;
    }
  }

  public void sawCollision( Koala koala, Saw saw ) {
    if( koala.collide( saw ) ) {
      koala.setDead( true );
      GameSound.saw.play();
      lost = true;
      gameOver = true;
    }
  }

  public void sawCollision( Wall wall, Saw saw ) {
    if( saw.collide( wall ) ) {
      if( saw.getLeft() == 1 ) {
        saw.setRight( 1 );
        saw.setLeft( 0 );
      } else if( saw.getRight() == 1 ) {
        saw.setLeft( 1 );
        saw.setRight( 0 );
      } else if( saw.getUp() == 1 ) {
        saw.setDown( 1 );
        saw.setUp( 0 );
      } else if( saw.getDown() == 1 ) {
        saw.setUp( 1 );
        saw.setDown( 0 );
      }
    }
  }

  public void update() {
    if( !isGameOver() ) {
      for( int i = 0; i < walls.size(); i++ ) {
        if( walls.get( i ).getShow() ) {
          for( int j = 0; j < koalas.size(); j++ ) {
            wallCollision( koalas.get( j ), walls.get( i ) );
          }
        }
      }
      for( int i = 0; i < walls.size(); i++ ) {
        if( walls.get( i ).getShow() ) {
          for( int j = 0; j < saws.size(); j++ ) {
            sawCollision( walls.get( i ), saws.get( j ) );
          }
        }
      }
      for( int i = 0; i < koalas.size(); i++ ) {
        for( int j = 0; j < koalas.size(); j++ ) {
          koalaCollision( koalas.get( i ), koalas.get( j ) );
        }
      }
      for( int i = 0; i < tnts.size(); i++ ) {
        if( tnts.get( i ).getShow() ) {
          for( int j = 0; j < koalas.size(); j++ ) {
            tntCollision( koalas.get( j ), tnts.get( i ) );
          }
        }
      }
      for( int i = 0; i < saws.size(); i++ ) {
        if( saws.get( i ).getShow() ) {
          for( int j = 0; j < koalas.size(); j++ ) {
            sawCollision( koalas.get( j ), saws.get( i ) );
          }
        }
      }
      for( int i = 0; i < objects.size(); i++ ) {
        for( int j = 0; j < koalas.size(); j++ ) {
          if( objects.get( i ) instanceof Exit && objects.get( i ).collision( koalas.get( j ) ) ) {
            koalas.remove( j );
            GameSound.saved.play();
            rescued++;
          }
        }
      }
      for( int i = 0; i < koalas.size(); i++ ) {
        koalas.get( i ).update();
      }
      for( int i = 0; i < saws.size(); i++ ) {
        saws.get( i ).update();
      }
    }
  }

  @Override
  public void actionPerformed( ActionEvent e ) {
    if( !isGameOver() || lost ) {
      update();
      repaint();
    }
  }

  public void restart() {
    koalas.clear();
    objects.clear();
    walls.clear();
    tnts.clear();
    saws.clear();
    init( level );
  }

  public void addWall( Wall wall ) {
    walls.add( wall );
  }

  public void addKoala( Koala koala ) {
    koalas.add( koala );
  }

  public void addTNT( TNT tnt ) {
    tnts.add( tnt );
  }

  public void addSaw( Saw saw ) {
    saws.add( saw );
  }

  public void addObject( GameObject object ) {
    objects.add( object );
  }

  @Override
  public void update( Observable o, Object arg ) {
  }

  public boolean isGameOver() {
    if( rescued >= 3 ) {
      gameOver = true;
    }
    return gameOver;
  }

  public void setGameOver( boolean gameOver ) {
    this.gameOver = gameOver;
  }
}