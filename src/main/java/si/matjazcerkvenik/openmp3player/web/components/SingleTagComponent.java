package si.matjazcerkvenik.openmp3player.web.components;

import java.io.IOException;

import javax.faces.component.FacesComponent;
import javax.faces.component.UIComponentBase;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

import si.matjazcerkvenik.openmp3player.backend.DAO;
import si.matjazcerkvenik.openmp3player.player.Tag;
import si.matjazcerkvenik.openmp3player.resources.Colors;

@FacesComponent(value="SingleTag")
public class SingleTagComponent extends UIComponentBase {
	
	@Override
	public void encodeBegin(FacesContext ctx) throws IOException {
		super.encodeBegin(ctx);
				
		ResponseWriter rw = ctx.getResponseWriter();
		
		Tag mp3Tag = (Tag) getAttributes().get("value");
		
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
		
		rw.startElement("div", this);
		rw.writeAttribute("class", "tagBorder", null);
		rw.writeAttribute("style", "background-color: " + backgroundColor + "; color: " + textColor + ";", null);
		rw.write(mp3Tag.getName());
		rw.endElement("div");
		
	}
	
	@Override
	public String getFamily() {
		return "omp3p.singleTag";
	}

}
