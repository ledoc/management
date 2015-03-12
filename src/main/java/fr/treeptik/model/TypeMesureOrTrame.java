package fr.treeptik.model;

public enum TypeMesureOrTrame {
	NIVEAUDEAU("niveau d'eau"), NIVEAUMANUEL("niveau manuel"), CONDUCTIVITE("conductivité"), TEMPERATURE("température");
//	, PLUVIOMETRIE("pluviométrie"), DEBIT("débit") ;
//	, VENT("vent"), PRESSIONAIR("pression air"), 
	
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
