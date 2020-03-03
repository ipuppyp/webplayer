package si.matjazcerkvenik.openmp3player.web.validators;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import si.matjazcerkvenik.openmp3player.backend.DAO;
import si.matjazcerkvenik.openmp3player.player.Playlist;
import si.matjazcerkvenik.openmp3player.player.Playlists;

@FacesValidator(value="playlistValidator")
public class PlaylistValidator implements Validator {
	
	private static Logger logger = LoggerFactory.getLogger(PlaylistValidator.class);
	
	public void validate(FacesContext ctx, UIComponent comp, Object value)
			throws ValidatorException {
		
		String pName = (String) value;
		logger.info("PlaylistValidator:validate(): new name: " + pName);
		
		if (pName.toLowerCase().equals("queue")) {
			logger.warn("PlaylistValidator:validate(): not allowed!");
			FacesMessage message = new FacesMessage();
			message.setDetail("Playlist name " + pName + " is not allowed");
			message.setSummary("Error");
			message.setSeverity(FacesMessage.SEVERITY_ERROR);
			throw new ValidatorException(message);
		}
		
		Playlists playlists = DAO.getInstance().getPlaylists();
		
		for (Playlist p : playlists.getPlist()) {
			if (p.getName().equals(pName)) {
				
				logger.warn("PlaylistValidator:validate(): already exist!");
				
				FacesMessage message = new FacesMessage();
				message.setDetail("Playlist with name " + pName + " already exist");
				message.setSummary("Error");
				message.setSeverity(FacesMessage.SEVERITY_ERROR);
				throw new ValidatorException(message);
				
			}
		}
		
	}
	
}
