package si.matjazcerkvenik.openmp3player.player;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import si.matjazcerkvenik.openmp3player.backend.OContext;
import si.matjazcerkvenik.openmp3player.backend.OperatingSystem;
import si.matjazcerkvenik.openmp3player.backend.Utils;

public class SoundControl {
	
	private static Logger logger = LoggerFactory.getLogger(Utils.class);
	
	public static int CURRENT_VOLUME_LEVEL = 2;
	
	/**
	 * Increase volume level
	 */
	public static void volumeUp() {
		
		// to change volume on os x:
		// osascript -e "set Volume 1"
		
		// on linux:
		// amixer set Master 10%
		
		if (CURRENT_VOLUME_LEVEL >= 10) {
			return;
		}
		CURRENT_VOLUME_LEVEL++;
		logger.info("Utils:volumeUp(): " + CURRENT_VOLUME_LEVEL);	
		
		setVolume(CURRENT_VOLUME_LEVEL);
		
	}
	
	/**
	 * Decrease volume level
	 */
	public static void volumeDown() {
		
		if (CURRENT_VOLUME_LEVEL <= 0) {
			return;
		}
		CURRENT_VOLUME_LEVEL--;
		logger.info("Utils:volumeDown(): " + CURRENT_VOLUME_LEVEL);	
		
		setVolume(CURRENT_VOLUME_LEVEL);
		
	}
	
	/**
	 * Set volume level in range from 0 to 10.
	 * @param level
	 */
	public static void setVolume(int level) {
		logger.info("Utils:setVolume(): " + level);	
		String[] vol = { "" + level };
		
		if (Utils.VOLUME_CUSTOM_SCRIPT != null && Utils.VOLUME_CUSTOM_SCRIPT.length() > 0) {
			Utils.runScript(Utils.VOLUME_CUSTOM_SCRIPT, vol);
			return;
		}
		
		OperatingSystem os = Utils.getOsType();
		switch (os) {
		case LINUX:
			Utils.runScript("setVolumeLinux.sh", vol);
			break;
		case OSX:
			Utils.runScript("setVolumeOSX.sh", vol);
			break;
		case WINDOWS:
			Utils.runScript("setVolume.bat", vol);
			break;
		default:
			break;
		}
		
		
	}
	
}
