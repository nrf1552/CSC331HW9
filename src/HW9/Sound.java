package HW9;

import java.io.File;
import javax.sound.sampled.*;

public class Sound {
	boolean playing = false;
	
	Clip cheerClip;
	Clip booClip;

	public Sound() {
		try {
			cheerClip = AudioSystem.getClip();
			booClip = AudioSystem.getClip();

			booClip.open(AudioSystem.getAudioInputStream(new File("boo.wav")));
			cheerClip.open(AudioSystem.getAudioInputStream(new File("cheer.wav")));

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void playCorrectSound() {
		playSound(cheerClip);
	}

	public void playIncorrectSound() {
		playSound(booClip);
	}

	private void playSound(Clip clip) {
		if (clip.isRunning()) {
			clip.stop();
		}
		clip.setFramePosition(0);
		clip.start();
		clip.setLoopPoints(0, clip.getFrameLength()-1);
	}
}