package si.matjazcerkvenik.openmp3player.player.jlayer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import si.matjazcerkvenik.openmp3player.player.IPlayer;
import si.matjazcerkvenik.openmp3player.player.IPlayerCallback;
import si.matjazcerkvenik.openmp3player.player.PlayerStatus;

public class JLayerPlayer implements IPlayer, IPlayerCallback {
	
	private static Logger logger = LoggerFactory.getLogger(JLayerPlayer.class);
	
	
	private SoundJLayer player = null;
	private PlayerStatus status = PlayerStatus.STOPPED;
	


	public void play(String filepath) {
		
		player = new SoundJLayer(filepath, this);
		player.play();
		status = PlayerStatus.PLAYING;
		
	}
	
	public void pause() {
		// no implementation
	}

	public void stop() {
		player.pauseToggle();
		status = PlayerStatus.STOPPED;
	}
	
	public void resume() {
		// no implementation
	}
	
	
	public PlayerStatus getStatus() {
		return status;
	}

	public void playEnded() {
		status = PlayerStatus.PLAY_ENDED;
		logger.info(status.toString());
	}
	
	
}
