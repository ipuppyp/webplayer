package si.matjazcerkvenik.openmp3player.web.components;

import java.io.IOException;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.render.FacesRenderer;
import javax.faces.render.Renderer;

import si.matjazcerkvenik.openmp3player.backend.DAO;
import si.matjazcerkvenik.openmp3player.player.Tag;
import si.matjazcerkvenik.openmp3player.player.Tags;
import si.matjazcerkvenik.openmp3player.resources.Colors;

@FacesRenderer(componentFamily = "omp3p.multiTag", rendererType = "omp3p.MultiTagRenderer")
public class MultiTagRenderer extends Renderer {
	
	@Override
	public void encodeBegin(FacesContext context, UIComponent component)
			throws IOException {
		
		super.encodeBegin(context, component);
		
		ResponseWriter rw = context.getResponseWriter();
		
		Tags tags = (Tags) component.getAttributes().get("value");
				
		rw.startElement("div", component);
		rw.writeAttribute("style", "float: right;", null);
		
		for (int i = 0; i < tags.getTagList().size(); i++) {
			
			Tag mp3Tag = tags.getTagList().get(i);
			
			// default values (if tag definition does not exist)
			String backgroundColor = "Black";
			String textColor = "White";
			
			// search tag definition from tags.xml
			Tag tagDefinition = DAO.getInstance().getTag(mp3Tag.getName());
			
			if (tagDefinition != null) {
				String c = DAO.getInstance().getTag(mp3Tag.getName()).getColor();
				// search tagColors.properties
				backgroundColor = Colors.getTagColors().get(c);
				if (backgroundColor == null) backgroundColor = "Black";
			}
			
			rw.startElement("div", component);
			rw.writeAttribute("class", "tagBorder", null);
			rw.writeAttribute("style", "background-color: " + backgroundColor + "; 	color: " + textColor + "; float: right;", null);
			rw.write(mp3Tag.getName());
			rw.endElement("div");
		}
		
		rw.endElement("div");
		
		rw.startElement("div", component);
		rw.writeAttribute("style", "clear: both;", null);
		rw.endElement("div");
		
	}
	
	
}
