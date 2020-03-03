package si.matjazcerkvenik.openmp3player.player;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import si.matjazcerkvenik.openmp3player.backend.DAO;
import si.matjazcerkvenik.openmp3player.backend.Utils;
import si.matjazcerkvenik.openmp3player.player.jlayer.JLayerPlayer;

public class Mp3Player {
	
	private static Logger logger = LoggerFactory.getLogger(Mp3Player.class);
	
	private IPlayer player = null;
	private boolean repeatOn = false;
	private Mp3File currentlyPlaying = null;
	private Playlist activePlaylist = null;
	private Watchdog watchdog = null;
	
	
	
	public Mp3Player() {
		
		
		player = new JLayerPlayer();
		
		Playlists p = DAO.getInstance().getPlaylists();
		if (p.getPlist().size() > 0) {
			activePlaylist = DAO.getInstance().getPlaylist(p.getPlist().get(0).getName());
		} else {
			activePlaylist = new Playlist();
		}
		
		watchdog = new Watchdog(this);
		watchdog.start();
		
		logger.info("Mp3Player: #" + this.hashCode());
		
	}
	
	
	
	/**
	 * Return currently playing mp3 file.
	 * @return mp3
	 */
	public Mp3File getCurrentlyPlaying() {
		return currentlyPlaying;
	}
	
	
	
	/**
	 * Start playing song with index i in active playlist
	 * @param i
	 * @return title of the song
	 */
	public void play(int i) {
		
		if (currentlyPlaying != null) {
			stop();
		}
		
		if (activePlaylist.getMp3files().getFiles().isEmpty()) {
			return;
		}
		
		currentlyPlaying = activePlaylist.getMp3files().getFiles().get(i);
		logger.info("Mp3Player:play(): playlist: "
				+ activePlaylist.getName() + ", MP3: "
				+ currentlyPlaying.toString());
		player.play(currentlyPlaying.getPath());
				
	}
	
	
	
	/**
	 * Stop playing song
	 * @return 'null'
	 */
	public void stop() {
		if (currentlyPlaying == null) {
			return;
		}
		
		logger.info("Mp3Player:stop(): playlist: " + activePlaylist.getName() 
				+ ", MP3: " + currentlyPlaying.toString());
		player.stop();
		currentlyPlaying = null;
		
	}
	
	
	
	/**
	 * Play next song
	 * @return title
	 */
	public void next() {
		if (currentlyPlaying == null) {
			return;
		}
		
		if (currentlyPlaying.getIndex() 
				== activePlaylist.getMp3files().getFiles().size() - 1) {
			play(0);
		} else {
			play(currentlyPlaying.getIndex() + 1);
		}
	}
	
	
	
	/**
	 * Play previous song
	 * @return title
	 */
	public void prev() {
		if (currentlyPlaying == null) {
			return;
		}
		
		if (currentlyPlaying.getIndex() == 0) {
			play(activePlaylist.getMp3files().getFiles().size() - 1);
		} else {
			play(currentlyPlaying.getIndex() - 1);
		}
	}
	
	
	
	/**
	 * Return true if song is currently playing
	 * @return true if playing
	 */
	public boolean isPlaying() {
		return currentlyPlaying != null;
	}
	
	
	
	public PlayerStatus getPlayerStatus() {
		return player.getStatus();
	}
	
	
	
	/**
	 * Return active playlist
	 * @return size
	 */
	public Playlist getActivePlaylist() {
		logger.debug("Mp3Player:getActivePlaylist(): " + activePlaylist.getName());
		return activePlaylist;
	}
	
	
	/**
	 * Set active playlist
	 * @param p
	 */
	public void setActivePlaylist(Playlist p) {
		logger.debug("Mp3Player:setActivePlaylist(): " + p.getName());
		activePlaylist = p;
	}
	
	
	/**
	 * Return true if repeat is on.
	 * @return
	 */
	public boolean isRepeatOn() {
		return repeatOn;
	}
	
	
	
	/**
	 * Set repeat on/off
	 * @param repeatOn
	 */
	public void setRepeatOn(boolean repeatOn) {
		this.repeatOn = repeatOn;
		logger.info("Mp3Player:setRepeatOn(): repeat is now: " + this.repeatOn);
	}
	
	
	/**
	 * Increment counter of currently playing song and set current time for last played time.
	 */
	public void updateCurrentlyPlaying() {
		
		currentlyPlaying.setLastPlayedTime(Utils.getNow());
		currentlyPlaying.setCount(currentlyPlaying.getCount() + 1);
		DAO.getInstance().savePlaylist(activePlaylist);
		
	}
	
}
