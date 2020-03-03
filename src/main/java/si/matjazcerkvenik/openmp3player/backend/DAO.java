package si.matjazcerkvenik.openmp3player.backend;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import si.matjazcerkvenik.openmp3player.player.Mp3File;
import si.matjazcerkvenik.openmp3player.player.Mp3Files;
import si.matjazcerkvenik.openmp3player.player.Playlist;
import si.matjazcerkvenik.openmp3player.player.Playlists;
import si.matjazcerkvenik.openmp3player.player.Tag;
import si.matjazcerkvenik.openmp3player.player.Tags;


/**
 * This class provides access to xml files (read/write).
 * 
 * @author matjaz
 *
 */
public class DAO {
	
	private static Logger logger = LoggerFactory.getLogger(DAO.class);
	
	private static DAO instance = null;
	
	private Playlists playlists = null;
	private Playlist queue = null;
	private Tags tags = null;
	
	private DAO() {
		
		if (queue == null) {
			queue = new Playlist();
			queue.setName("Queue");
			queue.setFile("queue");
		}
		
		// load tags and playlists
		getTags();
		getPlaylists();
		
	}
	
	
	/**
	 * Return instance of DAO (singleton)
	 * @return dao
	 */
	public static DAO getInstance() {
		if (instance == null) {
			instance = new DAO();
		}
		return instance;
	}
	
	
	
	/**
	 * Get all playlists 
	 * @return playlists
	 */
	public Playlists getPlaylists() {
		
		if (playlists == null) {
			loadPlaylists();
			playlists.add(queue);
		}
		
		return playlists;
	}
	
	/**
	 * Read from playlists.xml file. If playlists.xml does not exist, create new one.
	 */
	private void loadPlaylists() {
		try {

			File file = new File(OContext.PLAYLISTS_DIR + "/playlists.xml");
			if (!file.exists()) {
				logger.warn("DAO:loadPlaylists(): playlists.xml not found; creating new");
				playlists = new Playlists();
				JAXBContext jaxbContext = JAXBContext.newInstance(Playlists.class);
				Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
				jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
				jaxbMarshaller.marshal(playlists, file);
				
			}
			logger.info("DAO:loadPlaylists(): playlists.xml");
			JAXBContext jaxbContext = JAXBContext.newInstance(Playlists.class);
			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			playlists = (Playlists) jaxbUnmarshaller.unmarshal(file);
			if (playlists.getPlist() == null) {
				playlists.setPlist(new ArrayList<Playlist>());
			}
			logger.trace("DAO:loadPlaylists(): " + playlists.toString());

		} catch (JAXBException e) {
			logger.error("JAXBException", e);
		}
	}
	
	
	
	/**
	 * Save playlists to playlists.xml file
	 */
	private void savePlaylists() {
		logger.info("DAO:savePlaylists(): saving...");
		
		// temporarly remove queue
		Playlist q = null;
		for (int i = 0; i < playlists.getPlist().size(); i++) {
			if (playlists.getPlist().get(i).getName().equalsIgnoreCase("queue")) {
				q = playlists.getPlist().remove(i);
				break;
			}
		}
		
		try {

			File file = new File(OContext.PLAYLISTS_DIR + "/playlists.xml");
			JAXBContext jaxbContext = JAXBContext.newInstance(Playlists.class);
			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

			jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			
			 jaxbMarshaller.marshal(playlists, file);
//			jaxbMarshaller.marshal(p, System.out);
			

		} catch (JAXBException e) {
			logger.error("JAXBException", e);
		}
		
		// add queue back to playlists
		playlists.add(q);
		
	}
	
	
	/**
	 * Get playlist with name <code>pName</code> containing mp3 files
	 * @param pName
	 * @return playlist
	 */
	public Playlist getPlaylist(String pName) {
		
		if (pName.equals("Queue")) {
			return queue;
		}
		
		// search for playlist
		Playlist playlist = null;
		for (int i = 0; i < playlists.getPlist().size(); i++) {
			if (playlists.getPlist().get(i).getName().equals(pName)) {
				playlist = playlists.getPlist().get(i);
			}
		}
		
		// load mp3 files
		List<Mp3File> files = null;
		// FIXME always load from file, check hash, load id3...
		if (playlist.getFile().endsWith(".xml")) {
			files = loadFromXml(playlist.getFile()).getFiles();
		} else {
			files = loadFromDirectory(playlist.getFile());
		}
		
		logger.info("DAO:getPlaylist(): loaded " + files.size() + " mp3s from playlist " + pName);
		
		// set indexes
		for (int i = 0; i < files.size(); i++) {
			files.get(i).setIndex(i);
		}
		
		Mp3Files mp3files = new Mp3Files();
		mp3files.setFiles(files);
		playlist.setMp3files(mp3files);
		
		return playlist;
	}
	
	
	
	/**
	 * Add new playlist. Save playlists xml and playlist xml file.
	 * @param p
	 */
	public void addPlaylist(Playlist p) {
		playlists.add(p);
		savePlaylists();
		savePlaylist(p);
	}
	
	
	
	/**
	 * Remove playlist p
	 * @param p
	 */
	public void deletePlaylist(Playlist p) {
		
		logger.info("DAO:deletePlaylist(): " + p.getName());
		
		// delete xml file
		for (Playlist plist : playlists.getPlist()) {
			
			if (plist.getName().equals(p.getName())) {
				if (plist.getFile().endsWith(".xml")) {
					File f = new File(OContext.PLAYLISTS_DIR + "/" + plist.getFile());
					logger.debug("DAO:deletePlaylist(): delete: " + f.getAbsolutePath());
					f.delete();
				}
			}
			
		}
		
		playlists.getPlist().remove(p);
		savePlaylists();
		
	}
	
	
	/**
	 * Add mp3 file to the end of given playlist
	 * @param m
	 * @param p
	 */
	public void addMp3ToPlaylist(Mp3File m, Playlist p) {
		
		m.setIndex(p.getMp3files().getFiles().size());
		p.addMp3File(m);
		// currently mp3s can only be added to queue which cannot be saved
		savePlaylist(p);
		
	}
	
	
	/**
	 * Read playlist xml file 
	 * @param filename
	 * @return mp3Files
	 */
	private Mp3Files loadFromXml(String filename) {
		Mp3Files mp3Files = null;
		try {

			File file = new File(OContext.PLAYLISTS_DIR + "/" + filename);
			if (!file.exists()) {
				logger.warn("DAO:unmarshall(): playlist not found: " + OContext.PLAYLISTS_DIR + "/" + filename);
				return new Mp3Files();
			}
			logger.debug("DAO:unmarshall(): playlist found: " + OContext.PLAYLISTS_DIR + "/" + filename);
			JAXBContext jaxbContext = JAXBContext.newInstance(Mp3Files.class);
			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			mp3Files = (Mp3Files) jaxbUnmarshaller.unmarshal(file);

		} catch (JAXBException e) {
			e.printStackTrace();
		}
		return mp3Files;
	}
	
	
	
	/**
	 * Save playlist to xml file. Queue cannot be saved!
	 * @param p
	 */
	public void savePlaylist(Playlist p) {
		
		if (p.getName().equals("Queue")) {
			return;
		}
		
		logger.info("DAO:savePlaylist(): saving...");
		
		try {

			File file = new File(OContext.PLAYLISTS_DIR + "/" + p.getFile());
			JAXBContext jaxbContext = JAXBContext.newInstance(Mp3Files.class);
			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

			jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			
			Mp3Files m = p.getMp3files();

			jaxbMarshaller.marshal(m, file);
//			jaxbMarshaller.marshal(m, System.out);

		} catch (JAXBException e) {
			e.printStackTrace();
		}

	}
	
	
	
	/**
	 * Load mp3 files from directory
	 * @param directory
	 * @return
	 */
	public List<Mp3File> loadFromDirectory(String directory) {
		
		File dir = new File(directory);
		File[] files = dir.listFiles(new FileFilter() {
			
			public boolean accept(File pathname) {
				return pathname.isFile() && (pathname.getAbsolutePath().endsWith(".mp3")
						|| pathname.getAbsolutePath().endsWith(".MP3")
						|| pathname.getAbsolutePath().endsWith(".Mp3"));
			}
		});
		
		List<Mp3File> list = new ArrayList<Mp3File>();
		
		if (files == null) {
			return list;
		}
		
		for (int i = 0; i < files.length; i++) {
			Mp3File mp3 = new Mp3File();
			mp3.setIndex(i);
			mp3.setPath(files[i].getAbsolutePath());
			mp3.setHash(Digester.getSha1(files[i].getAbsolutePath()));
			mp3 = ID3Tag.getMetadata(mp3);
			list.add(mp3);
		}
				
		return list;
	}
	
	
	
	
	
	/* TAGS */
	
	
	/**
	 * Return all tags
	 */
	public Tags getTags() {

		if (tags == null) {
			loadTags();
		}
		
		return tags;

	}
	
	/**
	 * Return tag according to given name
	 * @param name
	 * @return tag
	 */
	public Tag getTag(String name) {
		
		for (Tag t : tags.getTagList()) {
			if (t.getName().equals(name)) {
				return t;
			}
		}
		return null;
	}
	
	/**
	 * Add new tag and save tags.xml
	 * @param t
	 */
	public void addTag(Tag t) {
		tags.addTag(t);
		saveTags();
	}
	
	/**
	 * Delete tag and save tags.xml
	 * @param t
	 */
	public void deleteTag(Tag t) {
		tags.removeTag(t);
		saveTags();
		logger.info("DAO:deleteTag(): delete " + t.getName());
	}
	
	
	
	
	/**
	 * Read from tags.xml file
	 */
	private void loadTags() {
		try {

			File file = new File(OContext.CFG_DIR + "/tags.xml");
			if (!file.exists()) {
				logger.warn("DAO:loadTags(): tags.xml not found; creating new");
				tags = new Tags();
				JAXBContext jaxbContext = JAXBContext.newInstance(Tags.class);
				Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
				jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
				jaxbMarshaller.marshal(tags, file);

			}
			logger.info("DAO:loadTags(): tags.xml");
			JAXBContext jaxbContext = JAXBContext.newInstance(Tags.class);
			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			tags = (Tags) jaxbUnmarshaller.unmarshal(file);
			logger.trace("DAO:loadTags(): " + tags.toString());

		} catch (JAXBException e) {
			logger.error("JAXBException", e);
		}
	}
	

	/**
	 * Save to file tags.xml
	 */
	private void saveTags() {
		logger.info("DAO:saveTags(): saving...");
		try {

			File file = new File(OContext.CFG_DIR + "/tags.xml");
			JAXBContext jaxbContext = JAXBContext.newInstance(Tags.class);
			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
			jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			jaxbMarshaller.marshal(tags, file);

		} catch (JAXBException e) {
			logger.error("JAXBException", e);
		}
	}
	
	
	
}
