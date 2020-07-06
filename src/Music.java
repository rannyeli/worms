
import java.io.IOException;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 * class for sound of buttons and backgrounds
 * 
 * @author 
 *
 */
public class Music {
	private Clip _sound;
	private AudioInputStream _ais;

	/**
	 * Ctor
	 * @param path: sound path
	 */
	public Music(String path) {
		try {
			_sound = AudioSystem.getClip();
			_ais = AudioSystem
					.getAudioInputStream(this.getClass().getClassLoader().getResourceAsStream(path));
			_sound.open(_ais);
			_sound.loop(0);
		} catch (LineUnavailableException e1) {
			System.out.print(e1.toString());
		} catch (IOException e) {
			System.out.print(e.toString());
		} catch (UnsupportedAudioFileException e) {
			System.out.print(e.toString());
		}
	}
	
	public void stop()
	{
		_sound.close();
	}
	
	public void Loop()
	{
		_sound.loop(Clip.LOOP_CONTINUOUSLY);
	}

}
