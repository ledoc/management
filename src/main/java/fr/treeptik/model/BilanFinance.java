package fr.treeptik.model;

public class BilanFinance {

	private String categorie;
	private Double somme;

	public BilanFinance(String categorie, Double somme) {
		super();
		this.categorie = categorie;
		this.somme = somme;
	}

	public String getCategorie() {
		return categorie;
	}

	public void setCategorie(String categorie) {
		this.categorie = categorie;
	}

	public Double getSomme() {
		return somme;
	}

	public void setSomme(Double somme) {
		this.somme = somme;
	}

}
