package fr.treeptik.model;

public enum NiveauAlerte {
	PREALERTE("pré-alerte"), ALERTE("alerte");
	
	private String description;

	private NiveauAlerte(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
