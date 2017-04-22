package tank.game;

import tank.SpriteSheet;

/**
 * Created by jinghuihuang on 4/20/17.
 */
public class Bullets extends GameObject  {
  SpriteSheet spriteSheet;
  private int strength;
  private int speed;

  public int getStrength() {
    return strength;
  }

  public void setStrength( int strength ) {
    this.strength = strength;
  }

  public int getSpeed() {
    return speed;
  }

  public void setSpeed( int speed ) {
    this.speed = speed;
  }

  public Bullets( int x, int y, SpriteSheet spriteSheet ) { // x and y are the location of the tank
    super( x, y, spriteSheet.getSprites()[ 0 ] );
    this.spriteSheet = spriteSheet;
    speed = 10;
    strength = 10;
  }
}