package si.matjazcerkvenik.openmp3player.web;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.model.SelectItem;

import si.matjazcerkvenik.openmp3player.backend.DAO;
import si.matjazcerkvenik.openmp3player.player.Tag;
import si.matjazcerkvenik.openmp3player.resources.Colors;

@ManagedBean
@SessionScoped
public class AddTagBean {
	
	private String name = null;
	private String color = null;
	
	/**
	 * Return tag name
	 * @return name
	 */
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * Return color name
	 * @return color name
	 */
	public String getColor() {
		return color;
	}
	
	public void setColor(String color) {
		this.color = color;
	}
	
	
	
	/**
	 * Create new tag and add to list of tags
	 */
	public void addNew() {
		
		Tag t = new Tag();
		t.setName(name);
		t.setColor(color);
		
		DAO.getInstance().addTag(t);
		
		name = null;
		color = null;
		
	}
	
	
	
	/**
	 * Delete given tag from list of tags
	 * @param t
	 */
	public void deleteTag(Tag t) {
		DAO.getInstance().deleteTag(t);
	}
	
	
	
	/**
	 * Return all tags from the list
	 * @return list
	 */
	public List<Tag> getAllTags() {
		return DAO.getInstance().getTags().getTagList();
	}
	
	
	
	/**
	 * Drop down items
	 * @return list
	 */
	public List<SelectItem> getAllColors() {
		
		Map<String, String> cList = Colors.getTagColors();
		
		List<SelectItem> list = new ArrayList<SelectItem>();
		
		for (Iterator<String> it = cList.keySet().iterator(); it.hasNext();) {
			String name = it.next();
			list.add(new SelectItem(name, name));
		}
		
		return list;
		
	}
	
	
	
}
