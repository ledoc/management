package fr.treeptik.model;

import java.util.List;

public class Ouvrage {

	// Nom de la nappe / cours d'eau / réservoir / bassin
	private String nom;
	// Si l'ouvrage est "lié" à un autre
	private Boolean asservissement;
	// Ouvrage auquel il est asservi
	private Ouvrage ouvrageMaitre;
	// Côte repère NGF : à indiquer
	private String coteRepereNGF;
	// Mesure 3 (Niveau Manuel) : à indiquer dernier NM + date + accès
	// historique NM
	private String niveauManuel;
	// Mesure Enregistreur : dernière mesure relevé avec date et heure
	private String mesure_Enregistreur;
	private String photo;
	private String code;
	private String codeSite;
	// suivi de nappe souterraine, suivi d’eaux de surface (Vanne / Pompe :
	// version 2) ;
	private String type;
	private Enregistreur enregistreur;
	private List<Alerte> alertes;
	private String croquisDynamique;
	private String commentaire;
	
	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public Boolean getAsservissement() {
		return asservissement;
	}

	public void setAsservissement(Boolean asservissement) {
		this.asservissement = asservissement;
	}

	public Ouvrage getOuvrageMaitre() {
		return ouvrageMaitre;
	}

	public void setOuvrageMaitre(Ouvrage ouvrageMaitre) {
		this.ouvrageMaitre = ouvrageMaitre;
	}

	public String getCoteRepereNGF() {
		return coteRepereNGF;
	}

	public void setCoteRepereNGF(String coteRepereNGF) {
		this.coteRepereNGF = coteRepereNGF;
	}

	public String getNiveauManuel() {
		return niveauManuel;
	}

	public void setNiveauManuel(String niveauManuel) {
		this.niveauManuel = niveauManuel;
	}

	public String getMesure_Enregistreur() {
		return mesure_Enregistreur;
	}

	public void setMesure_Enregistreur(String mesure_Enregistreur) {
		this.mesure_Enregistreur = mesure_Enregistreur;
	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getCodeSite() {
		return codeSite;
	}

	public void setCodeSite(String codeSite) {
		this.codeSite = codeSite;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Enregistreur getEnregistreur() {
		return enregistreur;
	}

	public void setEnregistreur(Enregistreur enregistreur) {
		this.enregistreur = enregistreur;
	}

	public List<Alerte> getAlertes() {
		return alertes;
	}

	public void setAlertes(List<Alerte> alertes) {
		this.alertes = alertes;
	}

	public String getCroquisDynamique() {
		return croquisDynamique;
	}

	public void setCroquisDynamique(String croquisDynamique) {
		this.croquisDynamique = croquisDynamique;
	}

	public String getCommentaire() {
		return commentaire;
	}

	public void setCommentaire(String commentaire) {
		this.commentaire = commentaire;
	}

}
