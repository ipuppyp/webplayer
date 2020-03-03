package si.matjazcerkvenik.openmp3player.player.jlayer;

import java.net.URL;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javazoom.jl.decoder.JavaLayerException;
import si.matjazcerkvenik.openmp3player.backend.DAO;
import si.matjazcerkvenik.openmp3player.player.IPlayerCallback;


public class SoundJLayer extends Pausable.PlaybackListener implements Runnable {
	private static Logger logger = LoggerFactory.getLogger(SoundJLayer.class);
	
	private String filePath;
	private Pausable player;
	private Thread playerThread;
	private IPlayerCallback callback;
	private volatile Process process;

	public SoundJLayer(String filePath, IPlayerCallback callback) {
		this.filePath = filePath;
		this.callback = callback;
	}

	public void pause() {
		this.player.pause();

//		this.playerThread.stop();
//		this.playerThread.interrupt();
		this.playerThread = null;
	}

	public void pauseToggle() {
		if (this.player.isPaused == true) {
			this.play();
		} else {
			this.pause();
		}
	}

	public void play() {
		if (this.player == null) {
			this.playerInitialize();
		}

		this.playerThread = new Thread(this, "AudioPlayerThread: " + filePath);

		this.playerThread.start();
	}
	
	public void stop() {
		// FIXME to ne dela
		this.process.destroy();
		this.playerThread.interrupt();
		this.player.stop();
	}

	private void playerInitialize() {
		try {
//			String urlAsStringX = "file:///"
//					+ new java.io.File(".").getCanonicalPath() + "/"
//					+ this.filePath;
//			System.out.println("urlAsStringX: " + urlAsStringX);
			String urlAsString = "file:///" + this.filePath;
//			System.out.println("urlAsString: " + urlAsString);

			this.player = new Pausable(new URL(urlAsString), this);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	// PlaybackListener members

	public void playbackStarted(Pausable.PlaybackEvent playbackEvent) {
//		System.out.println("playbackStarted()");
	}

	public void playbackFinished(Pausable.PlaybackEvent playbackEvent) {
//		System.out.println("playbackEnded()");
		callback.playEnded();
	}

	// IRunnable members

	public void run() {
		try {
			//this.player.resume();
			logger.debug("start playing", filePath);
			process = Runtime.getRuntime().exec("omxplayer " + filePath);
			process.waitFor();
			
			logger.debug("ended ", filePath);
		//} catch (JavaLayerException ex) {
		//	ex.printStackTrace();
		//	callback.playEnded();
		} catch (Exception e) {
			e.printStackTrace();
			callback.playEnded();
		}

	}

}