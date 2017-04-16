package tank.game;

import java.awt.*;

/**
 * Created by Stephen on 4/15/2017.
 */
public class Tank extends GameObject {
    int health;
    int speed;
    boolean isFiring;
    boolean moveUp, moveDown, moveLeft, moveRight;

    public Tank( int x, int y, Image img ) {
        super(x, y, img);
        health = 100;
        speed = 10;
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
