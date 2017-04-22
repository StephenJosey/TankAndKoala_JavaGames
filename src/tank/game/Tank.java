package tank.game;

import tank.SpriteSheet;

import java.awt.*;
import java.util.ArrayList;

/**
 * Created by Stephen on 4/15/2017.
 */
public class Tank extends GameObject {
    int health;
    int speed;
    boolean isFiring;
    ArrayList<Bullet> bullets;
    SpriteSheet spriteSheet;

    public Tank( int x, int y, SpriteSheet spriteSheet ) {
        super(x, y, spriteSheet.getSprites()[0]);
        this.spriteSheet = spriteSheet;
        health = 100;
        speed = 10;
        bullets = new ArrayList<Bullet>();
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }
}
