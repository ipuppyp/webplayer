package si.matjazcerkvenik.openmp3player.web;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import si.matjazcerkvenik.openmp3player.player.Mp3File;
import si.matjazcerkvenik.openmp3player.player.Playlist;
import si.matjazcerkvenik.openmp3player.player.PlaylistMng;

@ManagedBean
@SessionScoped
public class PlaylistBean {
	
	
	private static Logger logger = LoggerFactory.getLogger(PlaylistBean.class);
	
	private String selectedPlaylist = null;
		
	@ManagedProperty(value="#{playerBean}")
	private PlayerBean playerBean;
	
	private PlaylistMng playlistMng = null;
	
	private Playlist passivePlaylist = null;
	
	
	public PlaylistBean() {
		playlistMng = new PlaylistMng();
		passivePlaylist = playlistMng.getAllPlaylists().get(0);
	}
	
	
	public void setPlayerBean(PlayerBean playerBean) {
		this.playerBean = playerBean;
	}
	
	
	/* DROPDOWN MENU */
	
	/**
	 * Get playlists for dropdown menu
	 * @return list
	 */
	public List<SelectItem> getPlaylistsDropdown() {
		
		List<Playlist> pList = playlistMng.getAllPlaylists();
		
		List<SelectItem> list = new ArrayList<SelectItem>();
		
		for (int i = 0; i < pList.size(); i++) {
			String s = pList.get(i).getName();
			list.add(new SelectItem(s, s));
		}
		
		return list;
		
	}
	
	/**
	 * This method is fired when another playlist is selected from dropdown menu.
	 * @param e
	 */
	public void playlistChanged(ValueChangeEvent e) {
		selectedPlaylist = e.getNewValue().toString();
		logger.info("PlayerBean:playlistChanged(): event - selected playlist: " + selectedPlaylist);
		passivePlaylist = playlistMng.getPlaylist(selectedPlaylist);
	}
	
	/**
	 * Get selected playlist (dropdown menu)
	 * @return selectedPlaylist
	 */
	public String getSelectedPlaylist() {
		return selectedPlaylist;
	}
	
	
	/**
	 * Set selected playlist (dropdown menu)
	 * @param selectedPlaylist
	 */
	public void setSelectedPlaylist(String selectedPlaylist) {
		this.selectedPlaylist = selectedPlaylist;
	}
	
	
	
	/**
	 * This method is called from toolbar
	 * @return navigation to queue
	 */
	public String gotoQueue() {
		selectedPlaylist = PlaylistMng.QUEUE_NAME;
		passivePlaylist = playlistMng.getPlaylist(PlaylistMng.QUEUE_NAME);
		return "queue";
	}
	
	
	
	
	/* PLAYLIST */
	
	public List<Mp3File> getMp3List() {
		return passivePlaylist.getMp3files().getFiles();
	}
	
	public int getPlaylistSize() {
		return passivePlaylist.getMp3files().getFiles().size();
	}
	
	public boolean isQueue() {
		return passivePlaylist.isQueue();
	}
	
	
	
	
	public Playlist getPassivePlaylist() {
		return passivePlaylist;
	}


	public void setPassivePlaylist(Playlist passivePlaylist) {
		this.passivePlaylist = passivePlaylist;
	}
	
	
	
	public void play(Mp3File mp3) {
		playerBean.getMp3Player().setActivePlaylist(passivePlaylist);
		playerBean.getMp3Player().play(mp3.getIndex());
	}
	
	
	
	public void putToQueue(Mp3File mp3) {
		playlistMng.putToQueue(mp3);
	}
	
	
	
	public String showSongDetails(Mp3File mp3) {
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("mp3", mp3);
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("playlist", passivePlaylist);
		return "song";
	}
	
	
	
	public void removeMp3FromTheList(Mp3File mp3) {
		passivePlaylist = playlistMng.removeMp3FromPlaylist(mp3, passivePlaylist);
	}
	
	/**
	 * Return coma-separated list of row classes.
	 * @return
	 */
	public String getBackgroundColorsArray() {
		// FIXME this should be done differently in JSF 2 - style for each row
		StringBuilder rowClasses = new StringBuilder();
		List<Mp3File> list = getMp3List();

	    for (int i = 0; i < list.size(); i++) {
	    	Mp3File m = list.get(i);
	    	if (m.getBackgroundColor() != null && m.getBackgroundColor().length() > 0) {
	    		rowClasses.append("bgColor-" + m.getBackgroundColor());
			} else {
				
				if (i % 2 == 0) {
					rowClasses.append("table-even-row");
				} else {
					rowClasses.append("table-odd-row");
				}
				
			}
	        if (i < list.size() - 1) rowClasses.append(",");
	    }

	    return rowClasses.toString();
		
	}
	
	
}
