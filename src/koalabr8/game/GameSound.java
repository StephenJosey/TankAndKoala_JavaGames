package koalabr8.game;

import koalabr8.GameWorld;

import java.applet.Applet;
import java.applet.AudioClip;


public class GameSound {
  public static GameSound music = new GameSound( "Resources/music.mid" );
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