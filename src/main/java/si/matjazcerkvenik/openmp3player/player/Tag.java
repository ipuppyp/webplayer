package si.matjazcerkvenik.openmp3player.player;

public class Tag {

	private String name = null;
	private String color = null;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}
	
	@Override
	public String toString() {
		return name;
	}

}
