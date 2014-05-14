package jumpypig;

import java.io.BufferedInputStream;
import java.io.IOException;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class Sound {
	private String soundURL;
	
	public Sound(String url) {
		soundURL = url;
	}
	
	public void play() {
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				try {
					BufferedInputStream buffin = new BufferedInputStream(
							SoundManager.class.getResourceAsStream(soundURL));
					AudioInputStream stream = AudioSystem.getAudioInputStream(buffin);
					
					
					AudioFormat format = stream.getFormat();
					DataLine.Info info = new DataLine.Info(Clip.class,format);
					Clip clip = (Clip) AudioSystem.getLine(info);
					clip.open(stream);
					clip.start();
					
					
				} catch (LineUnavailableException | UnsupportedAudioFileException | IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				
			}
		}).start();
	}
	
}
