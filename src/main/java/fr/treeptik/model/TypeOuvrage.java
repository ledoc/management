package fr.treeptik.model;

public enum TypeOuvrage {
	NAPPE_SOUTERRAINE("Nappe souterraine"), EAU_DE_SURFACE("Eau de surface");
	
	private String description;

	private TypeOuvrage(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	
}
