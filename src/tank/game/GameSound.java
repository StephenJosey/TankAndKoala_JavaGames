package tank.game;

import tank.GameWorld;

import java.applet.Applet;
import java.applet.AudioClip;

/**
 * Created by jinghuihuang on 4/25/17.
 */

public class GameSound {
  public static GameSound explosion = new GameSound( "Resources/Explosion.wav" );
  public static GameSound firing = new GameSound( "Resources/TankFiring.wav" );
  private AudioClip clip;

  public GameSound( String name ) {
    try {
      clip = Applet.newAudioClip( GameWorld.class.getResource( name ) );
    } catch( Throwable e ) {
      e.printStackTrace();
    }
  }

  public void play() {
    try {
      new Thread() {
        public void run() {
          clip.play();
        }
      }.start();
    } catch( Throwable e ) {
      e.printStackTrace();
    }
  }
}