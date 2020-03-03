package si.matjazcerkvenik.openmp3player.web;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

import si.matjazcerkvenik.openmp3player.backend.OContext;
import si.matjazcerkvenik.openmp3player.player.Mp3File;
import si.matjazcerkvenik.openmp3player.player.Mp3Player;
import si.matjazcerkvenik.openmp3player.player.SoundControl;

/**
 * This class handles all user interactions with player, including play/stop/next/prev buttons,
 * currently selected playlist, volume up/down buttons, repeat button...
 * 
 * @author matjaz
 *
 */
@ManagedBean
@ApplicationScoped
public class PlayerBean {
	
	
	private Mp3Player mp3Player = null;
	
	
	public PlayerBean() {
		mp3Player = new Mp3Player();
		OContext.getInstance().startCli(mp3Player);
	}
	
	
	
	/**
	 * Access mp3Player from other beans
	 * @return mp3Player
	 */
	public Mp3Player getMp3Player() {
		return mp3Player;
	}
	
	
	
	
	

	
	/**
	 * Return name of active playlist
	 * @return playlist name
	 */
	public String getActivePlaylistName() {
		return mp3Player.getActivePlaylist().getName();
	}
	
	

	/**
	 * Return title of currently playing song. If no song is playing "null" is returned.
	 * @return title or "null"
	 */
	public String getCurrentlyPlaying() {
		Mp3File m = mp3Player.getCurrentlyPlaying();
		if (m == null) {
			return "Stopped";
		}
		String s = m.getTitle();
		if (m.getArtist() != null && m.getArtist().length() > 0) {
			s = m.getArtist() + " - " + s;
		}
		return "Playing: " + s;
	}
	
	
	
	/**
	 * Increase sound volume by 1.
	 */
	public void volumeUp() {
		SoundControl.volumeUp();
	}
	
	
	
	/**
	 * Decrease sound volume by 1.
	 */
	public void volumeDown() {
		SoundControl.volumeDown();
	}
	
	

	/**
	 * Start playing (first song in the playlist)
	 * @return title of the song
	 */
	public void play() {
		mp3Player.play(0);
	}
	
	

	/**
	 * Stop playing
	 * @return "null"
	 */
	public void stop() {
		mp3Player.stop();
	}
	
	
	// TODO replace play/stop?
	public void togglePlayStop() {
		if (isPlaying()) {
			stop();
		} else {
			play();
		}
	}
	
	
	
	/**
	 * Play next
	 * @return title
	 */
	public void next() {
		mp3Player.next();
	}
	
	
	
	/**
	 * Play previous
	 * @return title
	 */
	public void prev() {
		mp3Player.prev();
	}
	
	
	
	/**
	 * Return true if song is currently playing
	 * @return true if playing
	 */
	public boolean isPlaying() {
		return mp3Player.isPlaying();
	}
	
	
	
	/**
	 * Return true if repeat button is turned on.
	 * @return true if on
	 */
	public boolean isRepeatOn() {
		// TODO why is this method called so many times???
//		logger.info("PlayerBean:isRepeatOn(): " + Mp3Player.getInstance().isRepeatOn());
		return mp3Player.isRepeatOn();
	}
	
	
	
	public void toggleRepeat() {
		mp3Player.setRepeatOn(!mp3Player.isRepeatOn());
	}
	
	
	
}
