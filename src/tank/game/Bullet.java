package tank.game;

import tank.GameWorld;
import tank.SpriteSheet;

import java.awt.*;

/**
 * Created by Stephen on 4/20/2017.
 */
public class Bullet extends GameObject {
  int speed;
  int direction;
  int damage;
  SpriteSheet spriteSheet;

  public Bullet( int x, int y, int direction ) {
    image = GameWorld.getInstance().spriteSheets.get( "shell_basic" ).getSprites()[ 0 ];
    width = image.getWidth( observer );
    height = image.getHeight( observer );
    location = new Rectangle( x, y, width, height );
    spriteSheet = GameWorld.getInstance().spriteSheets.get( "shell_basic" );
    show = true;
    speed = 5;
    damage = 20;
    this.direction = direction;
  }

  public void update() {
    if( show ) {
      int dy = ( int ) ( 5 * Math.cos( Math.toRadians( this.direction + 90 ) ) );
      int dx = ( int ) ( 5 * Math.sin( Math.toRadians( this.direction + 90 ) ) );
      location.y += dy;
      location.x += dx;
    }
  }

  public void collide( GameObject object ) {
    if( show ) {
      if( object instanceof Wall ) {
        if( this.location.intersects( object.getLocation() ) ) {
          GameSound.explosion.play();
          this.show = false;
          if( ( ( Wall ) object ).isDestructible() ) {
            ( ( Wall ) object ).setShow( false );
          }
        }
      } else if( object instanceof Player && this != object ) {
        if( this.location.intersects( object.getLocation() ) ) {
          this.show = false;
          ( ( Player ) object ).damage( damage );
        }
      }
    }
  }

  public void draw( Graphics graphics ) {
    if( show ) {
      graphics.drawImage( spriteSheet.getSprites()[ direction / 6 ], location.x, location.y, observer );
    }
  }
}
