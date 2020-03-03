package si.matjazcerkvenik.openmp3player.web;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

import si.matjazcerkvenik.openmp3player.player.Playlist;
import si.matjazcerkvenik.openmp3player.player.PlaylistMng;

@ManagedBean
@SessionScoped
public class QueueBean {
	
	private String newQueueName = null;
	
	@ManagedProperty(value="#{playerBean}")
	private PlayerBean playerBean;
	
	@ManagedProperty(value="#{playlistBean}")
	private PlaylistBean playlistBean;
	
	private PlaylistMng playlistMng = null;
	
	@PostConstruct
	public void init() {
		
		playlistMng = new PlaylistMng();
		
	}

	public void setPlayerBean(PlayerBean playerBean) {
		this.playerBean = playerBean;
	}
	
	public void setPlaylistBean(PlaylistBean playlistBean) {
		this.playlistBean = playlistBean;
	}
	
	public String getNewQueueName() {
		return newQueueName;
	}

	public void setNewQueueName(String newQueueName) {
		this.newQueueName = newQueueName;
	}
	
	public String saveQueue() {
		playlistMng.saveQueue(newQueueName);
		playlistMng.emptyQueue();
		Playlist pList = playlistMng.getPlaylist(newQueueName);
		playlistBean.setPassivePlaylist(pList);
		playlistBean.setSelectedPlaylist(newQueueName);
		return "home";
	}
	
	public void emptyQueue() {
		playlistMng.emptyQueue();
	}
	
	
	
	
}
