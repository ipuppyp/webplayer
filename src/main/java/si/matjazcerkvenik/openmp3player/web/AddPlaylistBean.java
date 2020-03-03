package si.matjazcerkvenik.openmp3player.web;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import si.matjazcerkvenik.openmp3player.backend.DAO;
import si.matjazcerkvenik.openmp3player.player.Mp3Files;
import si.matjazcerkvenik.openmp3player.player.Playlist;
import si.matjazcerkvenik.openmp3player.player.jlayer.JLayerPlayer;

@ManagedBean
@SessionScoped
public class AddPlaylistBean {
	
	
	private static Logger logger = LoggerFactory.getLogger(JLayerPlayer.class);
	
	private String name = null;
	private String source = null;
	
	

	public String getName() {
		return name;
	}



	public void setName(String name) {
		this.name = name;
	}



	public String getSource() {
		return source;
	}



	public void setSource(String source) {
		this.source = source;
	}



	
	public String addPlaylist() {
		
		logger.info("AddPlaylistBean:addPlaylist(): " + source);
		Playlist p = new Playlist();
		p.setName(name);
		p.setSource(source);
		p.setFile(name + ".xml");
		
		Mp3Files m = new Mp3Files();
		m.setFiles(DAO.getInstance().loadFromDirectory(source));
		p.setMp3files(m);
		
		DAO.getInstance().addPlaylist(p);
		return "playlists";
		
	}
}
