package fr.treeptik.model;

import java.util.List;

public class Etablissement {

	// Nom ou entite
    private String nom;
    private String coordonneesGeographique;
    private String formeJuridique;
    private String siret;
    private String telephone;
    private String mail;
    private String siteWeb;
	private List<Site> sites;
	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	public String getCoordonneesGeographique() {
		return coordonneesGeographique;
	}
	public void setCoordonneesGeographique(String coordonneesGeographique) {
		this.coordonneesGeographique = coordonneesGeographique;
	}
	public String getFormeJuridique() {
		return formeJuridique;
	}
	public void setFormeJuridique(String formeJuridique) {
		this.formeJuridique = formeJuridique;
	}
	public String getSiret() {
		return siret;
	}
	public void setSiret(String siret) {
		this.siret = siret;
	}
	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	public String getMail() {
		return mail;
	}
	public void setMail(String mail) {
		this.mail = mail;
	}
	public String getSiteWeb() {
		return siteWeb;
	}
	public void setSiteWeb(String siteWeb) {
		this.siteWeb = siteWeb;
	}
	public List<Site> getSites() {
		return sites;
	}
	public void setSites(List<Site> sites) {
		this.sites = sites;
	}
	
	
	
	
}
