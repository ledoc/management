package fr.treeptik.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Etablissement {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	private String nom;
	// applicatif : par défaut le nom ou l'entité
	private String codeEtablissement;
	private String coordonneesGeographique;
	private String formeJuridique;
	private String siret;
	private String telephone;
	private String mail;
	private String siteWeb;
	@OneToMany
	private List<Site> sites;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getCodeEtablissement() {
		return codeEtablissement;
	}

	public void setCodeEtablissement(String codeEtablissement) {
		this.codeEtablissement = codeEtablissement;
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
		if(this.sites == null){
			this.sites = new ArrayList<Site>();
		}
		return sites;
	}

	public void setSites(List<Site> sites) {
		this.sites = sites;
	}

	@Override
	public String toString() {
		return "Etablissement [id=" + id + ", nom=" + nom + ", codeEtablissement=" + codeEtablissement
				+ ", coordonneesGeographique=" + coordonneesGeographique + ", formeJuridique=" + formeJuridique
				+ ", siret=" + siret + ", telephone=" + telephone + ", mail=" + mail + ", siteWeb=" + siteWeb
				+ ", sites=" + sites + "]";
	}

}
