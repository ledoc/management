package fr.treeptik.model;

public enum TypeMesureOrTrame {
	NIVEAUDEAU("niveau d'eau"), PLUVIOMETRIE("pluviométrie"), NIVEAUMANUEL("niveau manuel"), CONDUCTIVITE("conductivité"), TEMPERATURE("température");
//	, VENT("vent"), PRESSIONAIR("pression air"), DEBIT("débit") ;
	
	private String description;

	private TypeMesureOrTrame(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
