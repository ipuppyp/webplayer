package si.matjazcerkvenik.openmp3player.web;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import si.matjazcerkvenik.openmp3player.backend.DAO;
import si.matjazcerkvenik.openmp3player.backend.Digester;
import si.matjazcerkvenik.openmp3player.backend.ID3Tag;
import si.matjazcerkvenik.openmp3player.backend.OContext;
import si.matjazcerkvenik.openmp3player.player.Mp3File;
import si.matjazcerkvenik.openmp3player.player.Playlist;
import si.matjazcerkvenik.openmp3player.player.Tag;
import si.matjazcerkvenik.openmp3player.player.Tags;
import si.matjazcerkvenik.openmp3player.resources.Colors;

@ManagedBean
@RequestScoped
public class SongBean {
	
	private static Logger logger = LoggerFactory.getLogger(SongBean.class);
	
	private Mp3File mp3File = null;
	private Playlist playlist = null;
	
	private String selectedTag = "- Select tag -";
	private String selectedBackgroundColor = "- Select color -";
	private String selectedBackgroundColorValue = null;
	private String numberOfStars = "0";
		
	@ManagedProperty(value="#{playerBean}")
	private PlayerBean playerBean;
	
	
	
	@PostConstruct
	public void init() {
		Map<String, Object> requestParameterMap = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
		mp3File = (Mp3File) requestParameterMap.get("mp3");
		playlist = (Playlist) requestParameterMap.get("playlist");
		String hash = Digester.getSha1(mp3File.getPath());
		if (!mp3File.getHash().equals(hash)) {
			mp3File.setHash(hash);
			mp3File = ID3Tag.getMetadata(mp3File);
			DAO.getInstance().savePlaylist(playlist);
		}
	}
	

	/**
	 * You need setter to successfully inject object
	 * @param playerBean
	 */
	public void setPlayerBean(PlayerBean playerBean) {
		this.playerBean = playerBean;
	}
	
	
	
	/**
	 * Get selected mp3file from the session
	 * @return mp3File
	 */
	public Mp3File getMp3File() {
		return mp3File;
	}
	
	

	public void setMp3File(Mp3File mp3File) {
		this.mp3File = mp3File;
	}
	
	
	
	
	/* TAGS */
	
	
	/**
	 * Get tags for dropdown menu
	 * @return list
	 */
	public List<SelectItem> getTagItems() {
		
		Tags tags = DAO.getInstance().getTags();
		
		List<SelectItem> list = new ArrayList<SelectItem>();
		list.add(new SelectItem("- Select tag -", "- Select tag -"));
		
		if (tags.getTagList() == null) {
			return list;
		}
		
		for (int i = 0; i < tags.getTagList().size(); i++) {
			String name = tags.getTagList().get(i).getName();
			list.add(new SelectItem(name, name));
		}
		
		return list;
		
	}
	
	
	
	/**
	 * Event: new tag selected in dropdown menu
	 * @param e
	 */
	public void tagSelected(ValueChangeEvent e) {
		
		selectedTag = e.getNewValue().toString();
		logger.info("SongBean:tagSelected(): event - selected tag: " + selectedTag);
		if (selectedTag.equals("- Select tag -")) {
			return;
		}
		Tag t = new Tag();
		t.setName(selectedTag);
		mp3File.addTag(t);
		DAO.getInstance().savePlaylist(playlist);
		
		selectedTag = "- Select tag -";
				
	}
	
	
	
	public String getSelectedTag() {
		if (selectedTag == null) {
			selectedTag = "- Select tag -";
		}
		return selectedTag;
	}
	
	

	public void setSelectedTag(String selectedTag) {
		this.selectedTag = selectedTag;
	}
	
	
	
	public List<Tag> getTagList() {
		return mp3File.getTags().getTagList();
	}
	
	
	
	/**
	 * Remove tag from the song
	 */
	public void removeTag(Tag t) {
		mp3File.getTags().removeTag(t);
		DAO.getInstance().savePlaylist(playlist);
	}
	
	
	
	
	
	/* BACKGROUND */
	
	

	public String getSelectedBackgroundColor() {
		selectedBackgroundColor = mp3File.getBackgroundColor();
		if (selectedBackgroundColor == null) {
			selectedBackgroundColor  = "- Select color -";
		}
		return selectedBackgroundColor;
	}
	
	

	public void setSelectedBackgroundColor(String selectedBackgroundColor) {
		this.selectedBackgroundColor = selectedBackgroundColor;
	}
	
	
	
	public String getSelectedBackgroundColorValue() {
		if (selectedBackgroundColorValue == null) {
			selectedBackgroundColorValue = Colors.getBgColors().get(mp3File.getBackgroundColor());
		}
		return selectedBackgroundColorValue;
	}


	public void setSelectedBackgroundColorValue(String selectedBackgroundColorValue) {
		this.selectedBackgroundColorValue = selectedBackgroundColorValue;
	}


	/**
	 * Get background colors for dropdown menu
	 * @return list
	 */
	public List<SelectItem> getBackgroundColorItems() {
		
		Map<String, String> bgColorList = Colors.getBgColors();
		List<SelectItem> list = new ArrayList<SelectItem>();
		list.add(new SelectItem("- Select color -", "- Select color -"));
		
		for (Iterator<String> it = bgColorList.keySet().iterator(); it.hasNext();) {
			String name = it.next();
			list.add(new SelectItem(name, name));
		}
		
		return list;
		
	}
	
	
	
	/**
	 * Event: new tag selected in dropdown menu
	 * @param e
	 */
	public void backgroundColorSelected(ValueChangeEvent e) {
		
		selectedBackgroundColor = e.getNewValue().toString();
		if (selectedBackgroundColor.equals("- Select color -")) {
			return;
		}
		logger.info("SongBean:backgroundColorSelected(): event - selected color: " + selectedBackgroundColor);
		mp3File.setBackgroundColor(selectedBackgroundColor);
		DAO.getInstance().savePlaylist(playlist);
		selectedBackgroundColorValue = Colors.getBgColors().get(selectedBackgroundColor);
		selectedBackgroundColor = "- Select color -";
		
	}
	
	
	/**
	 * Remove background color. Default color will be used (#FFF or #EEE).
	 */
	public void removeBackgroundColor() {
		mp3File.setBackgroundColor(null);
		DAO.getInstance().savePlaylist(playlist);
		selectedBackgroundColor = "- Select color -";
		selectedBackgroundColorValue = null;
	}


	
	
	
	
	/* STARS */
	
	
	public String getNumberOfStars() {
		return mp3File.getStars() + "";
	}


	public void setNumberOfStars(String numberOfStars) {
		this.numberOfStars = numberOfStars;
	}
	
	public Map<String,Object> getStarsMap() {
		Map<String,Object> color2Value = new LinkedHashMap<String,Object>();
		color2Value.put("0", "0");
		color2Value.put("1", "1");
		color2Value.put("2", "2");
		color2Value.put("3", "3");
		color2Value.put("4", "4");
		color2Value.put("5", "5");
		return color2Value;
	}
	
	public void starsSelected(ValueChangeEvent e) {
		
		numberOfStars = e.getNewValue().toString();
		logger.info("SongBean:starsSelected(): event - selected stars: " + numberOfStars);
		mp3File.setStars(Integer.parseInt(numberOfStars));
		DAO.getInstance().savePlaylist(playlist);
		
	}
	
	
}
