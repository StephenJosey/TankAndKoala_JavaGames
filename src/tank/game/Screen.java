package tank.game;

import tank.GameWorld;

import javax.swing.*;
import java.awt.*;
import java.awt.image.ImageObserver;
import java.util.ArrayList;

/**
 * Created by Stephen on 4/23/2017.
 */
public class Screen extends JPanel {
    ArrayList<Wall> walls;
    GameObject[][] map;
    int width;
    int height;
    int viewXMin;
    int viewXMax;
    int viewYMin;
    int viewYMax;
    int camX;
    int camY;
    Player player;
    Background background;
    public Screen(int width, int height, Player player) {
        this.setFocusable(true);
        this.width = width;
        this.height = height;
        walls = GameWorld.getInstance().getWalls();
        map = GameWorld.getInstance().getMap().getMap();
        this.player = player;
        viewXMin = 0;
        viewXMax = GameWorld.getInstance().getMap().getWidth()*16;
        viewYMin = 0;
        viewYMax = GameWorld.getInstance().getMap().getHeight()*16;
        background = GameWorld.getInstance().getBackgroundComponent();
    }

    public void update() {
        camX = player.getX() - width/2;
        camY = player.getY() - height/2;
        if (camX > viewXMax) {
            camX = viewXMax;
        } else if (camX < 0) {
            camX = 0;
        }
        if (camY > viewYMax) {
            camY = viewYMax;
        } else if (camY < 0) {
            camY = 0;
        }

    }

    public void drawMiniMapLeft(Graphics graphics, int scale) {
      graphics.translate(camX + width - viewXMax/scale - 25, camY + height - viewYMax/scale - 175);
        //background.drawScaled(graphics, );
      for (int i = 0; i < walls.size(); i++) {
        walls.get( i ).drawScaled( graphics, scale );
      }
      for (int i = 0; i < GameWorld.getInstance().getPlayers().size(); i++) {
        GameWorld.getInstance().getPlayers().get(i).drawScaled(graphics, scale);
      }

    }
  public void drawMiniMapRight(Graphics graphics, int scale) {
    graphics.translate(camX - width + viewXMax/scale, camY + height - viewYMax/scale - 175);
    //background.drawScaled(graphics, );
    for (int i = 0; i < GameWorld.getInstance().getPlayers().size(); i++) {
      GameWorld.getInstance().getPlayers().get(i).drawScaled(graphics, scale);
    }
    for (int i = 0; i < walls.size(); i++) {
      walls.get( i ).drawScaled( graphics, scale );
    }
  }

    @Override
    public void paintComponent(Graphics graphics) {
      super.paintComponent(graphics);
      graphics.translate(camX * -1, camY * -1);
      background.repaint(graphics);
      for (int i = 0; i < walls.size(); i++) {
        walls.get( i ).repaint( graphics );
      }
      for (int i = 0; i < GameWorld.getInstance().getPlayers().size(); i++) {
          GameWorld.getInstance().getPlayers().get(i).repaint(graphics);
      }

      //drawMiniMap( graphics, 6 );
    }
}
