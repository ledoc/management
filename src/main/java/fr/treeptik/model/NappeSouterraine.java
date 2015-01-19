package fr.treeptik.model;


public class NappeSouterraine extends Ouvrage {
	private String numeroBSS;

	// Mesure 1 (Repère NGF / Sol) : à indiquer
	private String mesure_RepereNGF_Sol;
	// Mesure 2 (Profondeur) : à indiquer
	private String mesure_Profondeur;

	// Côte Sol NGF : à calculer = côte repère NGF – Mesure 1
	private String cote_Sol_NGF;

	public String getNumeroBSS() {
		return numeroBSS;
	}

	public void setNumeroBSS(String numeroBSS) {
		this.numeroBSS = numeroBSS;
	}

	public String getMesure_RepereNGF_Sol() {
		return mesure_RepereNGF_Sol;
	}

	public void setMesure_RepereNGF_Sol(String mesure_RepereNGF_Sol) {
		this.mesure_RepereNGF_Sol = mesure_RepereNGF_Sol;
	}

	public String getMesure_Profondeur() {
		return mesure_Profondeur;
	}

	public void setMesure_Profondeur(String mesure_Profondeur) {
		this.mesure_Profondeur = mesure_Profondeur;
	}

	public String getCote_Sol_NGF() {
		return cote_Sol_NGF;
	}

	public void setCote_Sol_NGF(String cote_Sol_NGF) {
		this.cote_Sol_NGF = cote_Sol_NGF;
	}

}
