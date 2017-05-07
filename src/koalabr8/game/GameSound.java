package koalabr8.game;

import koalabr8.GameWorld;

import java.applet.Applet;
import java.applet.AudioClip;


public class GameSound {
  public static GameSound music = new GameSound( "Resources/music.mid" );
  public static GameSound tnt = new GameSound( "Resources/Explosion.wav" );
  public static GameSound saved = new GameSound( "Resources/Saved.wav" );
  public static GameSound saw = new GameSound( "Resources/Saw.wav" );

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