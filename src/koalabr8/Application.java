package koalabr8;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * Created by jinghuihuang on 4/15/17.
 */

public class Application {
  public static void main( String[] args ) {
    final koalabr8.GameWorld game = koalabr8.GameWorld.getInstance();
    JFrame frame = new JFrame( "Koala Maze" );
    frame.addWindowListener( new WindowAdapter() {
      public void windowGainedFocus( WindowEvent e ) {
        game.requestFocusInWindow();
      }
    } );
    frame.getContentPane().add( game );
    frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
    String file = "level.txt";
    game.init( file );
    frame.setSize( game.getDimension() );
    frame.setVisible( true );
    game.start();
  }
}
