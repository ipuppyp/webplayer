package si.matjazcerkvenik.openmp3player.player;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="files")
public class Mp3Files {
	
	private List<Mp3File> files = new ArrayList<Mp3File>();
	
	public void add(Mp3File m) {
		files.add(m);
	}
	

	public List<Mp3File> getFiles() {
		return files;
	}

	@XmlElement(name="file")
	public void setFiles(List<Mp3File> files) {
		this.files = files;
	}
	
	
	
	@Override
	public String toString() {
		String s = "";
		for (int i = 0; i < files.size(); i++) {
			System.out.println("File: " + files.get(i));
		}
		return s;
	}
	
}
