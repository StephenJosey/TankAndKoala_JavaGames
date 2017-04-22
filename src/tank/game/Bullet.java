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
  SpriteSheet spriteSheet;
  public Bullet(int x, int y, int direction) {
    //super(x, y, GameWorld.getInstance().spriteSheets.get("shell_basic").getSprites()[0]);
    image = GameWorld.getInstance().spriteSheets.get("shell_basic").getSprites()[0];
    width = image.getWidth(observer);
    height = image.getHeight(observer);
    location = new Rectangle (x, y, width, height);
    spriteSheet = GameWorld.getInstance().spriteSheets.get("shell_basic");
    show = true;
    speed = 5;
    this.direction = direction;
  }

  public void update() {
    int dy = (int) (5 * Math.cos(Math.toRadians(this.direction + 90)));
    int dx = (int) (5 * Math.sin(Math.toRadians(this.direction + 90)));
    location.y += dy;
    location.x += dx;
  }

  public void draw(Graphics graphics) {
    graphics.drawImage(spriteSheet.getSprites()[direction / 6],location.x, location.y, observer);
  }
}
