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
import si.matjazcerkvenik.openmp3player.player.Tag;
import si.matjazcerkvenik.openmp3player.player.Tags;

@FacesValidator(value="tagValidator")
public class TagValidator implements Validator {
	
	private static Logger logger = LoggerFactory.getLogger(TagValidator.class);
	
	//@Override
	public void validate(FacesContext ctx, UIComponent comp, Object value)
			throws ValidatorException {
		
		String tName = (String) value;
		logger.info("TagValidator:validate(): new name: " + tName);
		
		Tags tags = DAO.getInstance().getTags();
		
		for (Tag t : tags.getTagList()) {
			if (t.getName().equals(tName)) {
				
				logger.warn("TagValidator:validate(): already exist!");
				
				FacesMessage message = new FacesMessage();
				message.setDetail("Tag with name " + tName + " already exist");
				message.setSummary("Forbidden");
				message.setSeverity(FacesMessage.SEVERITY_ERROR);
				throw new ValidatorException(message);
				
			}
		}
		
	}
	
}
