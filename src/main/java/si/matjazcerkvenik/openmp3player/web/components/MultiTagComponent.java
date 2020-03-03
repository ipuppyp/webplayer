package si.matjazcerkvenik.openmp3player.web.components;

import javax.faces.component.FacesComponent;
import javax.faces.component.UIComponentBase;

@FacesComponent(value="MultiTag")
public class MultiTagComponent extends UIComponentBase {

	@Override
	public String getFamily() {
		return "omp3p.multiTag";
	}

}
