package si.matjazcerkvenik.openmp3player.web;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

import si.matjazcerkvenik.openmp3player.backend.DAO;
import si.matjazcerkvenik.openmp3player.player.Mp3File;
import si.matjazcerkvenik.openmp3player.player.Mp3Files;
import si.matjazcerkvenik.openmp3player.player.Playlist;

@ManagedBean
@SessionScoped
public class PlaylistsBean {
	
	
	@ManagedProperty(value="#{playlistBean}")
	private PlaylistBean playlistBean;
	
	
	
	public PlaylistsBean() {
		
	}
	
	
	
	public void setPlaylistBean(PlaylistBean playlistBean) {
		this.playlistBean = playlistBean;
	}
	
	
	
	public List<Playlist> getPlaylistsList() {
		return DAO.getInstance().getPlaylists().getPlist();
	}
	
	
	
	/**
	 * Action: go to playlist when goto button is pressed
	 * @return home page
	 */
	public String gotoPlaylist(Playlist p) {
		Playlist pList = DAO.getInstance().getPlaylist(p.getName());
		playlistBean.setPassivePlaylist(pList);
		playlistBean.setSelectedPlaylist(pList.getName());
		return "home";
	}
	
	
	/**
	 * Reload files from directory (add new files and remove non-existing). This 
	 * method can only be applied to directory playlists.
	 * @param p
	 * @return home page
	 */
	public String reloadPlaylist(Playlist p) {
		List<Mp3File> newFilesList = DAO.getInstance().loadFromDirectory(p.getSource());
		List<Mp3File> oldFilesList = DAO.getInstance().getPlaylist(p.getName()).getMp3files().getFiles();
		for (int i = 0; i < newFilesList.size(); i++) {
			Mp3File mNew = newFilesList.get(i);
			for (int j = 0; j < oldFilesList.size(); j++) {
				Mp3File mOld = oldFilesList.get(j);
				if (mNew.getHash().equals(mOld.getHash())) {
					// copy styles from old mp3 to new
					mNew.setTags(mOld.getTags());
					mNew.setBackgroundColor(mOld.getBackgroundColor());
					mNew.setStars(mOld.getStars());
				}
			}
		}
		Mp3Files ms = new Mp3Files();
		ms.setFiles(newFilesList);
		p.setMp3files(ms);
		DAO.getInstance().savePlaylist(p);
		return "playlists";
	}
	
	
	
	/**
	 * Action: delete playlist when delete button is pressed
	 * @return home page
	 */
	public String deletePlaylist(Playlist p) {
		DAO.getInstance().deletePlaylist(p);
		return "playlists";
	}
	
}
