package fr.treeptik.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
public class Ouvrage {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	protected Integer id;

	// Nom de la nappe / cours d'eau / réservoir / bassin
	protected String nom;
	// Si l'ouvrage est "lié" à un autre
	protected Boolean asservissement;
	// Ouvrage auquel il est asservi
	@OneToOne
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
	// D-type
	// suivi de nappe souterraine, suivi d’eaux de surface (Vanne / Pompe :
	// version 2) ;
	@Enumerated(EnumType.STRING)
	protected TypeOuvrage typeOuvrage;
	@OneToOne
	protected Enregistreur enregistreur;

	protected String croquisDynamique;
	protected String commentaire;
	@OneToMany
	protected List<Alerte> alertes;

	@OneToMany
	protected List<Document> documents;
	@OneToMany
	protected List<Rapport> rapports;

	/**
	 * Spécifique À EAU DE SURFACE TODO : comprendre :( Côte Sol « berge » : à
	 * indiquer
	 */
	private String coteSol;

	/**
	 * Spécifique à NAPPE SOUTERRAINE
	 * 
	 * @return
	 */
	private String numeroBSS;

	// Mesure 1 (Repère NGF / Sol) : à indiquer
	private String mesure_RepereNGF_Sol;
	// Mesure 2 (Profondeur) : à indiquer
	private String mesure_Profondeur;

	// Côte Sol NGF : à calculer = côte repère NGF – Mesure 1
	private String cote_Sol_NGF;

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

	public List<Document> getDocuments() {
		return documents;
	}

	public void setDocuments(List<Document> documents) {
		this.documents = documents;
	}

	public List<Rapport> getRapports() {
		return rapports;
	}

	public void setRapports(List<Rapport> rapports) {
		this.rapports = rapports;
	}

	public TypeOuvrage getTypeOuvrage() {
		return typeOuvrage;
	}

	public void setTypeOuvrage(TypeOuvrage typeOuvrage) {
		this.typeOuvrage = typeOuvrage;
	}

	public String getCoteSol() {
		return coteSol;
	}

	public void setCoteSol(String coteSol) {
		this.coteSol = coteSol;
	}

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
