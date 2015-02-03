package fr.treeptik.model;

public enum TypeMesure {
	NIVEAUDEAU("niveau d'eau"), PLUVIOMETRIE("pluviométrie"), NIVEAUMANUEL("niveau manuel");
	
	private String description;

	private TypeMesure(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
