package tank.game;

import java.awt.*;
import java.lang.reflect.Field;
import java.util.*;
import java.lang.Math;

import tank.Modifiers.*;
import tank.SpriteSheet;

/**
* Created by Stephen on 4/15/2017.
*/
public class Player extends Tank implements Observer {
    static final int RELOAD_TIME = 30;
    static final int SPEED = 2;

    public int up, down, left, right;
    int direction;
    int speed;
    int reload;
    boolean isReloading;
    SpriteSheet spriteSheet;
    private GameController controller;
    Rectangle respawn;
    //GameSound shoot;
    public Player( int x, int y, int direction, SpriteSheet spriteSheet, int [] keys ) {
        super(x / 2, y / 2, spriteSheet);
        this.spriteSheet = spriteSheet;
        controller = new GameController(this, keys);
        this.direction = direction ;
        up = down = left = right = 0;
        speed = SPEED;
        reload = RELOAD_TIME;
        isReloading = false;
        respawn = new Rectangle(x * width, y * height);
    }

    public void update(Observable o, Object arg) {
        String direction = controller.getMove();
        int moveState = controller.getMoveState();
        int turn = controller.getTurn();
        boolean fire = controller.getFire();
        Field thisDirection = null;
        try {
            thisDirection = this.getClass().getDeclaredField(direction);
            thisDirection.setInt(this, moveState);
        } catch (Exception e) {
        }
        isFiring = fire;
        turn( turn );

    }
    public void update() {
        if ( isDead() ) {
            location = respawn;
        }
        if ( up == 1 || down == 1 ) {
          int dy = (int) (5 * Math.cos(Math.toRadians(this.direction + 90)));
          int dx = (int) (5 * Math.sin(Math.toRadians(this.direction + 90)));
          location.y += dy * (up - down) / speed;
          location.x += dx * (up - down) / speed;
        }
        if (isFiring) {
          fire();
        }
        for (int i= 0; i < bullets.size(); i++) {
            if ( bullets.get(i).show ) {
                bullets.get(i).update();
            } else {
                bullets.remove( i );
            }
        }
        if (isReloading) {
            reload();
        }
    }

    public void turn(int dir) {
        direction += 6*dir;
        if ( direction < 0 ) {
            direction = 359;
        } else if ( direction > 359 ) {
            direction = 0;
        }
    }

    public void fire() {
        if (! isReloading ) {
            int dy = (int) (height/2 * Math.cos(Math.toRadians(this.direction + 90)));
            int dx = (int) (width/2 * Math.sin(Math.toRadians(this.direction + 90)));
            bullets.add(new Bullet(getX() + width/2, getY() + height/2, direction, this));
            isReloading = true;
            //GameSound.play("Resources/TankFiring.wav");
        }
    }

    public void reload() {
        reload--;
        if ( reload < 0 ) {
            isReloading = false;
            reload = RELOAD_TIME;
        }
    }

    public void damage(int damage) {
        health -= damage;
    }

    public boolean isDead() {
        return health < 0;
    }

    public void collide( GameObject object ) {
        if ( object instanceof Wall || object instanceof Player  && ( this != object )) {
            while ( this.location.intersects(object.getLocation())) {
                if ( this.getY() < object.getY() ) {
                    location.y -= 2;
                }
                if ( this.getY() > object.getY() ) {
                    location.y += 2;
                }
                if ( this.getX() > object.getX() ) {
                    location.x += 2;
                }
                if ( this.getX() < object.getX() ) {
                    location.x -= 2;
                }
            }
        }
    }

    public ArrayList<Bullet> getBullets() {
        return bullets;
    }

    public void drawHealth(Graphics graphics) {
      if( health < 50 ) {
        graphics.setColor( Color.RED );
      } else {
        graphics.setColor( Color.GREEN );

      }
      graphics.fillRect(location.x + 5, location.y - 10, health / 2, 4);
    }

    @Override
    public void repaint(Graphics graphics) {
      int turn = 0;
      if ( direction > 0 ) {
          turn = direction / 6;
      }
      for (int i = 0; i < bullets.size(); i++) {
        bullets.get( i ).draw(graphics);
      }
      graphics.drawImage( spriteSheet.getSprites()[ turn ], (location.x), (location.y), width, height, observer );
      drawHealth( graphics );
    }
}
