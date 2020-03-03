package si.matjazcerkvenik.openmp3player.player;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import si.matjazcerkvenik.openmp3player.backend.DAO;

public class PlaylistMng {
	
	private static Logger logger = LoggerFactory.getLogger(PlaylistMng.class);
	
	public static final String QUEUE_NAME = "Queue";
	
	
	/**
	 * Return list of all playlists (including queue)
	 * @return
	 */
	public List<Playlist> getAllPlaylists() {
		List<Playlist> pList = DAO.getInstance().getPlaylists().getPlist();
		return pList;
	}
	
	
	
	/**
	 * Return playlist by name
	 * @param name
	 * @return playlist
	 */
	public Playlist getPlaylist(String name) {
		return DAO.getInstance().getPlaylist(name);
	}
	
	

	/**
	 * Remove mp3 <code>m</code> from playlist <code>p</code> and save to file.
	 * Also mp3 indexes are recalculated.
	 * @param m
	 * @param p
	 * @return playlist
	 */
	public Playlist removeMp3FromPlaylist(Mp3File m, Playlist p) {
		
		logger.info("PlaylistMng:removeMp3FromTheList(): " + m.getIndex());
		
		p.getMp3files().getFiles().remove(m);
		
		// set new indexes
		for (int i = 0; i < p.getMp3files().getFiles().size(); i++) {
			p.getMp3files().getFiles().get(i).setIndex(i);
		}
		// save playlist
		DAO.getInstance().savePlaylist(p);
		
		return p;
	}
	
	
	/**
	 * Put mp3 file to queue
	 * @param mp3
	 */
	public void putToQueue(Mp3File mp3) {
		
		logger.info("PlaylistMng:putToQueue(): " + mp3.getIndex());
		DAO.getInstance().addMp3ToPlaylist(mp3.makeClone(), getPlaylist(QUEUE_NAME));
		
	}
	
	
	
	/**
	 * Save queue as playlist with name <code>name</code>
	 * @param name
	 */
	public void saveQueue(String name) {
		
		if (name == null || name.trim().length() == 0) {
			return;
		}
		
		Playlist p = new Playlist();
		p.setName(name);
		p.setFile(name + ".xml");
		
		Playlist queue = DAO.getInstance().getPlaylist(QUEUE_NAME);
		
		for (int i = 0; i < queue.getMp3files().getFiles().size(); i++) {
			
			Mp3File mp3 = queue.getMp3files().getFiles().get(i).makeClone();
			mp3.setIndex(i);
			p.addMp3File(mp3);
			
		}
		
		DAO.getInstance().addPlaylist(p);
	}
	
	
	
	/**
	 * Clear all songs from queue.
	 */
	public void emptyQueue() {
		DAO.getInstance().getPlaylist(QUEUE_NAME).getMp3files().getFiles().clear();
	}
	
}
