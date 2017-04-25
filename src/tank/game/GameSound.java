package tank.game;

import java.io.IOException;

import tank.GameWorld;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;


/**
 * Created by jinghuihuang on 4/25/17.
 */
public class GameSound {
  public static final GameSound sound = new GameSound();

  public static void play(final String filename)
  {
    try {
      // Play now.
      new Thread() {
        public void run() {
          try {
            AudioInputStream in= AudioSystem.getAudioInputStream(GameWorld.class.getResource(filename));
            AudioInputStream din = null;
            AudioFormat baseFormat = in.getFormat();
            AudioFormat decodedFormat = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED,
                    baseFormat.getSampleRate(),
                    16,
                    baseFormat.getChannels(),
                    baseFormat.getChannels() * 2,
                    baseFormat.getSampleRate(),
                    false);
            din = AudioSystem.getAudioInputStream(decodedFormat, in);
            rawplay(decodedFormat, din);
            in.close();}
          catch (Exception e) { e.printStackTrace(); }
        }
      }.start();
    } catch (Exception e)
    {
      //Handle exception.
    }
  }

  private static void rawplay(AudioFormat targetFormat, AudioInputStream din) throws IOException,                                                                                                LineUnavailableException
  {
    byte[] data = new byte[4096];
    SourceDataLine line = getLine(targetFormat);
    if (line != null)
    {
      // Start
      line.start();
      int nBytesRead = 0, nBytesWritten = 0;
      while (nBytesRead != -1)
      {
        nBytesRead = din.read(data, 0, data.length);
        if (nBytesRead != -1) nBytesWritten = line.write(data, 0, nBytesRead);
      }
      // Stop
      line.drain();
      line.stop();
      line.close();
      din.close();
    }
  }

  private static SourceDataLine getLine(AudioFormat audioFormat) throws LineUnavailableException
  {
    SourceDataLine res = null;
    DataLine.Info info = new DataLine.Info(SourceDataLine.class, audioFormat);
    res = (SourceDataLine) AudioSystem.getLine(info);
    res.open(audioFormat);
    return res;
  }
}
