package si.matjazcerkvenik.openmp3player.web.validators;

import java.io.File;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@FacesValidator(value="directoryValidator")
public class DirectoryValidator implements Validator {
	
	private static Logger logger = LoggerFactory.getLogger(DirectoryValidator.class);
	
	public void validate(FacesContext ctx, UIComponent comp, Object value)
			throws ValidatorException {
		
		String source = (String) value;
		logger.info("DirectoryValidator:validate(): source: " + source);
		
		File dir = new File(source);
		if (!dir.isDirectory()) {
			
			logger.warn("DirectoryValidator:validate(): not a directory!");
			
			FacesMessage message = new FacesMessage();
			message.setDetail("Source " + source + " is not a directory");
			message.setSummary("Error");
			message.setSeverity(FacesMessage.SEVERITY_ERROR);
			throw new ValidatorException(message);
			
		}
		
		
	}
	
}
