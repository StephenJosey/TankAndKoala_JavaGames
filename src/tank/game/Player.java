package tank.game;

import java.awt.*;
import java.util.*;

import tank.Modifiers.*;

/**
 * Created by Stephen on 4/15/2017.
 */
public class Player extends Tank implements Observer {
    private MotionController controller;
    int direction;
    public int up, down, left, right;
    public Player( int x, int y, Image img, int [] keys ) {
        super(x, y, img);
        controller = new GameController(this, keys);
        direction = 0;
        up = down = left = right = 0;
    }


    public void update(Observable o, Object arg) {
        AbstractGameModifier modifier = (AbstractGameModifier) o;
        modifier.read(this);

        if (up == 1) {
            location.y--;
        } else if (down == 1) {
            location.y++;
        } else if (left == 1) {
            location.x--;
        } else if (right == 1) {
            location.x++;
        }
    }
}
