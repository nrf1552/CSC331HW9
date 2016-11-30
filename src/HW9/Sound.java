package HW9;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import javax.swing.*;
import javax.sound.sampled.*;
import javax.sound.sampled.LineEvent.Type;

public class Sound {
	boolean playing = false;
	Clip cheerClip;
	Clip booClip;
	AudioInputStream cheerStream;
	AudioInputStream booStream;

	public static void main(String[] args) throws Exception {
		Sound s = new Sound();
		s.playCorrectSound();
		s.playCorrectSound();
		s.playIncorrectSound();
		s.playIncorrectSound();
	}

	public Sound() {
		try {
			cheerClip = AudioSystem.getClip();
			booClip = AudioSystem.getClip();
			cheerStream = AudioSystem.getAudioInputStream(new File("cheer.wav"));
			booStream = AudioSystem.getAudioInputStream(new File("boo.wav"));

			booClip.open(booStream);
			cheerClip.open(cheerStream);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void playCorrectSound() {
		playSound(cheerClip);
		// playSound(new File("cheer.wav"));
	}

	public void playIncorrectSound() {
		playSound(booClip);
		// playSound(new File("boo.wav"));
	}

	private void playSound(Clip clip) {
		if (clip.isRunning()) {
			clip.stop();
		}
		clip.setFramePosition(0);
		clip.start();
		clip.setLoopPoints(0, clip.getFrameLength()-1);
		
		while(clip.getFramePosition() < clip.getFrameLength()-1){
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		
		
		//clip.stop();
		clip.setFramePosition(0);
	}
}