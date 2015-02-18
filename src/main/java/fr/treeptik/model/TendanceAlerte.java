package fr.treeptik.model;

public enum TendanceAlerte {

	INFERIEUR("inférieur à"), SUPERIEUR("supérieur à"), EGAL("égal à"), DIFFERENT_DE(
			"différent de");
	
	private String description;

	private TendanceAlerte(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
