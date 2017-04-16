package tank.game;

import java.awt.*;
import java.util.*;

import tank.modifiers.GameController;
import tank.modifiers.AbstractGameModifier;

/**
 * Created by Stephen on 4/15/2017.
 */
public class Player extends Tank implements Observer {
    public Player( int x, int y, Image img ) {
        super(x, y, img);
    }

    public void update(Observable o, Object arg) {
        AbstractGameModifier modifier = (AbstractGameModifier) o;
        modifier.read(this);
    }
}
