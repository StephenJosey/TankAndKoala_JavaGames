package tank.game;

import tank.GameWorld;

import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by Stephen on 4/15/2017.
 */
public class Map implements Observer {
    int start;
    Integer position;
    String filename;
    BufferedReader level;
    int w, h;
    int endgameDelay = 100;	// don't immediately end on game end

    /*Constructor sets up arrays of enemies in a LinkedHashMap*/
    public Map(String filename){
        this.filename = filename;
        String line;
        try {
            level = new BufferedReader(new InputStreamReader(GameWorld.class.getResource("Resources/" +filename).openStream()));
            line = level.readLine();
            w = line.length();
            h=0;
            while(line!=null){
                h++;
                line = level.readLine();
            }
            level.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    public void load(){
        GameWorld world = GameWorld.getInstance();
        try {
            level = new BufferedReader(new InputStreamReader(GameWorld.class.getResource("Resources/" +filename).openStream()));
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }

        String line;
        try {
            line = level.readLine();
            w = line.length();
            h=0;
            while(line!=null){
                for(int i = 0, n = line.length() ; i < n ; i++) {
                    char c = line.charAt(i);

                    if(c=='1'){
                        Wall wall = new Wall(i ,h , world.sprites.get("wall"), false);
                        world.addWall(wall);
                    }

                    if(c=='2'){
                        Wall wall = new Wall(i,h, world.sprites.get("wall_destroy"), true);
                        world.addWall(wall);
                    }

                    if(c=='3'){
                        int[] controls = {KeyEvent.VK_A,KeyEvent.VK_W, KeyEvent.VK_D, KeyEvent.VK_S, KeyEvent.VK_SPACE};
                        world.addPlayer(new Player(i, h, 180, world.spriteSheets.get("tank_blue_base"), controls));
                    }

                    if(c=='4'){
                        int[] controls = new int[] {KeyEvent.VK_LEFT,KeyEvent.VK_UP, KeyEvent.VK_RIGHT, KeyEvent.VK_DOWN, KeyEvent.VK_ENTER};
                        world.addPlayer(new Player(i, h, 0, world.spriteSheets.get("tank_red_base"), controls));
                    }

                }
                h++;
                line = level.readLine();
            }
            level.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int getWidth() { return w; }
    public int getHeight() { return h; }

    /*Level observes GameClock and updates on every tick*/
    @Override
    public void update(Observable o, Object arg) {
        GameWorld world = GameWorld.getInstance();
        if(world.isGameOver()){
            if(endgameDelay<=0){
                //world.removeClockObserver(this);
                //world.finishGame();
            } else endgameDelay--;
        }
    }
}