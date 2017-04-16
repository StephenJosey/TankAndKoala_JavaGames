package tank;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * Created by jinghuihuang on 4/15/17.
 */

public class Application {
  public static void main(String[] args) {
    final GameWorld game = GameWorld.getInstance();
    JFrame frame = new JFrame("Test");
    frame.addWindowListener( new WindowAdapter() {
                               public void windowGainedFocus(WindowEvent e) {
                                 game.requestFocusInWindow();
                               }
                             });
    frame.setSize(800, 600);
    frame.getContentPane().add(game);
    String file = "level.txt";
    game.init(file);
    frame.setVisible(true);
    game.start();
  }
}
