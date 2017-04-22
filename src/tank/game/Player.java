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
    public int up, down, left, right;
    int direction;
    int speed;
    SpriteSheet spriteSheet;
    private GameController controller;
    public Player( int x, int y, int direction, SpriteSheet spriteSheet, int [] keys ) {
        super(x / 2, y / 2, spriteSheet);
        this.spriteSheet = spriteSheet;
        controller = new GameController(this, keys);
        this.direction = direction ;
        up = down = left = right = 0;
        speed = 2;
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
        //update();

    }
    public void update() {
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
          bullets.get( i ).update();
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
      bullets.add(new Bullet( getX() - 6, getY(), direction ) );
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

    @Override
    public void repaint(Graphics graphics) {
        int turn = 0;
        if ( direction > 0 ) {
            turn = direction / 6;
        }
        //graphics.setColor(Color.RED);
        //graphics.drawRect( location.x, location.y, location.width, location.height);
        graphics.drawImage( spriteSheet.getSprites()[ turn ], (location.x), (location.y), width/2, height/2, observer );
        for (int i = 0; i < bullets.size(); i++) {
          bullets.get( i ).draw(graphics);
        }
    }
}
