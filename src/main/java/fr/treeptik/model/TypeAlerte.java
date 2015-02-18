package fr.treeptik.model;

public enum TypeAlerte {
	NIVEAUDEAU("niveau d'eau"), CONDUCTIVITE("conductivité");
	
	private String description;

	private TypeAlerte(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
