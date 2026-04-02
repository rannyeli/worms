package com.worms;

import java.io.InputStream;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class Music {
	private Clip _sound;

	public Music(String path) {
		try {
			InputStream is = getClass().getClassLoader().getResourceAsStream(path);
			if (is == null) {
				System.err.println("Audio resource not found: " + path);
				return;
			}
			_sound = AudioSystem.getClip();
			AudioInputStream ais = AudioSystem.getAudioInputStream(is);
			_sound.open(ais);
			_sound.loop(0);
		} catch (Exception e) {
			System.err.println("Audio unavailable (" + path + "): " + e.getMessage());
		}
	}

	public void stop() {
		if (_sound != null) {
			_sound.close();
		}
	}

	public void Loop() {
		if (_sound != null) {
			_sound.loop(Clip.LOOP_CONTINUOUSLY);
		}
	}
}
