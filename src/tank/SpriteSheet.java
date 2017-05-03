package tank;

import java.awt.image.BufferedImage;

/**
 * Created by jinghuihuang on 4/17/2017.
 */
public class SpriteSheet {
  BufferedImage[] sprites;
  String name;
  public SpriteSheet( int rows, String name ) {
    sprites = new BufferedImage[ rows ];
    this.name = name;
  }

  @Override
  public int hashCode() {
    return name.hashCode();
  }

  public BufferedImage[] getSprites() {
    return sprites;
  }
}

