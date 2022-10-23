package buba.main.Sounds;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineListener;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class SoundManager{
	
	private Clip[] sounds;
	
	private int id;
	
	public SoundManager(){
		sounds = new Clip[10];
		
		try {
			sounds[0] = AudioSystem.getClip();
			sounds[0].open(AudioSystem.getAudioInputStream(new File("res/Sounds/select.wav")));
			
			sounds[1] = AudioSystem.getClip();
			sounds[1].open(AudioSystem.getAudioInputStream(new File("res/Sounds/startGame.wav")));

			sounds[2] = AudioSystem.getClip();
			sounds[2].open(AudioSystem.getAudioInputStream(new File("res/Sounds/jump.wav")));
			
			sounds[3] = AudioSystem.getClip();
			sounds[3].open(AudioSystem.getAudioInputStream(new File("res/Sounds/pickup.wav")));
			
			sounds[4] = AudioSystem.getClip();
			sounds[4].open(AudioSystem.getAudioInputStream(new File("res/Sounds/hit.wav")));
			
			sounds[5] = AudioSystem.getClip();
			sounds[5].open(AudioSystem.getAudioInputStream(new File("res/Sounds/blip.wav")));
			
			sounds[6] = AudioSystem.getClip();
			sounds[6].open(AudioSystem.getAudioInputStream(new File("res/Sounds/win.wav")));
			
			sounds[7] = AudioSystem.getClip();
			sounds[7].open(AudioSystem.getAudioInputStream(new File("res/Sounds/loss.wav")));
			
			sounds[8] = AudioSystem.getClip();
			sounds[8].open(AudioSystem.getAudioInputStream(new File("res/Sounds/shoot.wav")));
			
			sounds[9] = AudioSystem.getClip();
			sounds[9].open(AudioSystem.getAudioInputStream(new File("res/Sounds/explosion.wav")));
		} catch (LineUnavailableException | IOException | UnsupportedAudioFileException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void playSound(int id) {
		sounds[id].setFramePosition(0);
		sounds[id].start();
	}
	
	public void setVolume(float volume) {
	    for(Clip c : sounds) {
	    	if (volume < 0f || volume > 1f)
		        throw new IllegalArgumentException("Volume not valid: " + volume);
		    FloatControl gainControl = (FloatControl) c.getControl(FloatControl.Type.MASTER_GAIN);        
		    gainControl.setValue(20f * (float) Math.log10(volume));
	    }
	}
}
