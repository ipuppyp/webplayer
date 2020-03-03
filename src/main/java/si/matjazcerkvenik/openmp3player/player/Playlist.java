package si.matjazcerkvenik.openmp3player.player;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;

public class Playlist {

	private String name;
	private String file;
	private String source;
	private Mp3Files mp3files = new Mp3Files();

	/**
	 * Get name of xml file
	 * @return file
	 */
	public String getFile() {
		return file;
	}

	/**
	 * Set name of xml file
	 * @param file
	 */
	@XmlElement
	public void setFile(String file) {
		this.file = file;
	}

	/**
	 * Get name of playlist
	 * @return name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Set name of playlist. Xml file with the same name is created.
	 * @param name
	 */
	@XmlElement
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * Get origin of playlist. Used for directory type of playlist, which 
	 * can be reloaded.
	 * @return source
	 */
	public String getSource() {
		return source;
	}

	/**
	 * Original source of playlist - directory.
	 * @param source
	 */
	@XmlElement
	public void setSource(String source) {
		this.source = source;
	}

	/**
	 * Add mp3 to playlist
	 */
	public void addMp3File(Mp3File mp3) {
		mp3files.add(mp3);
	}
	
	@XmlTransient
	public Mp3Files getMp3files() {
		return mp3files;
	}

	public void setMp3files(Mp3Files mp3files) {
		this.mp3files = mp3files;
	}

	/**
	 * Return true if this playlist is queue
	 * @return true/false
	 */
	public boolean isQueue() {
		if (file.equals("queue")) {
			return true;
		}
		return false;
	}
	
	/**
	 * Return true if directory is the source.
	 * @return true/false
	 */
	public boolean isSourceDirectory() {
		if (source != null) {
			return true;
		}
		return false;
	}
	

	@Override
	public String toString() {
		return "PLAYLIST: " + name + ", SOURCE: " + file;
	}

}
