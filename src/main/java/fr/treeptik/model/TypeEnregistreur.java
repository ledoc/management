package fr.treeptik.model;

public enum TypeEnregistreur {
	ANALOGIQUE("analogique"), NUMERIQUE("numérique");
	
	private String description;

	private TypeEnregistreur(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
