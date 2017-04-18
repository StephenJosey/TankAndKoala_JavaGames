package tank.game;

import java.awt.*;
import java.lang.reflect.Field;
import java.util.*;

import tank.Modifiers.*;

/**
* Created by Stephen on 4/15/2017.
*/
public class Player extends Tank implements Observer {
    public int up, down, left, right;
    int direction;
    int speed;
    private GameController controller;
    public Player( int x, int y, Image img, int [] keys ) {
        super(x, y, img);
        controller = new GameController(this, keys);
        direction = 0;
        up = down = left = right = 0;
        speed = 5;
    }


    public void update(Observable o, Object arg) {
        String direction = controller.getMove();
        int moveState = controller.getMoveState();
        Field thisDirection = null;
        try {
            thisDirection = this.getClass().getDeclaredField(direction);
            thisDirection.setInt(this, moveState);
        } catch (Exception e) {
        }

        if (up == 1) {
            location.y -= speed;
        } else if (down == 1) {
            location.y += speed;
        } else if (left == 1) {
            location.x -= speed;
        } else if (right == 1) {
            location.x += speed;
        }
    }
}
