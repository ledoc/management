package fr.treeptik.model;

import java.util.List;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class Ouvrage {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	protected Integer id;

	// Nom de la nappe / cours d'eau / réservoir / bassin
	protected String nom;
	// Si l'ouvrage est "lié" à un autre
	protected Boolean asservissement;
	// Ouvrage auquel il est asservi
	protected Ouvrage ouvrageMaitre;
	// Côte repère NGF : à indiquer
	protected String coteRepereNGF;
	// Mesure 3 (Niveau Manuel) : à indiquer dernier NM + date + accès
	// historique NM
	protected String niveauManuel;
	// Mesure Enregistreur : dernière mesure relevé avec date et heure
	protected String mesure_Enregistreur;
	protected String photo;
	protected String code;
	protected String codeSite;
	// suivi de nappe souterraine, suivi d’eaux de surface (Vanne / Pompe :
	// version 2) ;
	protected String type;
	protected Enregistreur enregistreur;
	protected List<Alerte> alertes;
	protected String croquisDynamique;
	protected String commentaire;

	
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
