package fr.treeptik.model;

public enum TypeRepas {
	
	PETITDEJEUNER("petit déjeuner"), MIDI("midi"), SOIR("soir"),AUTRE("autre");
	
	private String description;

	private TypeRepas(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
